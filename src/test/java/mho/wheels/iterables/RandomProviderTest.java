package mho.wheels.iterables;

import mho.wheels.io.Readers;
import mho.wheels.math.BinaryFraction;
import mho.wheels.numberUtils.FloatingPointUtils;
import mho.wheels.random.IsaacPRNG;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
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
                "[47968091191, 209, 348, 117, 1719440537, 956748, 1, 60, 1, 131900, 437219, 1, 566, 245, 6, 8, 2," +
                " 13, 30, 3272]",
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
                1.3550214E36f
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
                -3.9935304E36f
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
                8.791807689509865E304
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
                -2.6552885265154105E305
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
        nextFromRangeUpUniform_float_helper(1.0f, 9.929094E37f);
        nextFromRangeUpUniform_float_helper(1.0E20f, 9.929094E37f);
        nextFromRangeUpUniform_float_helper(-1.0f, 9.929094E37f);
        nextFromRangeUpUniform_float_helper(-1.0E20f, 9.929094E37f);
        nextFromRangeUpUniform_float_helper((float) Math.PI, 9.929094E37f);
        nextFromRangeUpUniform_float_helper((float) Math.sqrt(2), 9.929094E37f);
        nextFromRangeUpUniform_float_helper((float) -Math.PI, 9.929094E37f);
        nextFromRangeUpUniform_float_helper((float) -Math.sqrt(2), 9.929094E37f);
        nextFromRangeUpUniform_float_helper(0.0f, 9.929094E37f);
        nextFromRangeUpUniform_float_helper(-0.0f, 9.929094E37f);
        nextFromRangeUpUniform_float_helper(Float.MIN_VALUE, 9.929094E37f);
        nextFromRangeUpUniform_float_helper(Float.MIN_NORMAL, 9.929094E37f);
        nextFromRangeUpUniform_float_helper(-Float.MIN_VALUE, 9.929094E37f);
        nextFromRangeUpUniform_float_helper(-Float.MIN_NORMAL, 9.929094E37f);
        nextFromRangeUpUniform_float_helper(Float.MAX_VALUE, 3.4028235E38f);
        nextFromRangeUpUniform_float_helper(-Float.MAX_VALUE, 9.929096E37f);
        nextFromRangeUpUniform_float_fail_helper(Float.POSITIVE_INFINITY);
        nextFromRangeUpUniform_float_fail_helper(Float.NEGATIVE_INFINITY);
        nextFromRangeUpUniform_float_fail_helper(Float.NaN);
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
        rangeUpUniform_float_helper(
                1.0f,
                "[9.929094E37, 3.3988664E38, 1.6264806E38, 2.9887745E38, 2.3484774E37, 2.0084857E38, 3.076517E38," +
                " 1.293602E38, 2.6551666E38, 3.3685456E38, 9.823196E37, 3.2878975E37, 1.6963695E38, 6.2983905E37," +
                " 2.767365E38, 8.4991095E37, 3.9025083E37, 1.1304424E38, 1.9060855E37, 1.4810831E38]",
                1.6998872E38f
        );
        rangeUpUniform_float_helper(
                1.0E20f,
                "[9.929094E37, 3.3988664E38, 1.6264806E38, 2.9887745E38, 2.3484774E37, 2.0084857E38, 3.076517E38," +
                " 1.293602E38, 2.6551666E38, 3.3685456E38, 9.823196E37, 3.2878975E37, 1.6963695E38, 6.2983905E37," +
                " 2.767365E38, 8.4991095E37, 3.9025083E37, 1.1304424E38, 1.9060855E37, 1.4810831E38]",
                1.6998872E38f
        );
        rangeUpUniform_float_helper(
                -1.0f,
                "[9.929094E37, 3.3988664E38, 1.6264806E38, 2.9887745E38, 2.3484774E37, 2.0084857E38, 3.076517E38," +
                " 1.293602E38, 2.6551666E38, 3.3685456E38, 9.823196E37, 3.2878975E37, 1.6963695E38, 6.2983905E37," +
                " 2.767365E38, 8.4991095E37, 3.9025083E37, 1.1304424E38, 1.9060855E37, 1.4810831E38]",
                1.6998872E38f
        );
        rangeUpUniform_float_helper(
                -1.0E20f,
                "[9.929094E37, 3.3988664E38, 1.6264806E38, 2.9887745E38, 2.3484774E37, 2.0084857E38, 3.076517E38," +
                " 1.293602E38, 2.6551666E38, 3.3685456E38, 9.823196E37, 3.2878975E37, 1.6963695E38, 6.2983905E37," +
                " 2.767365E38, 8.4991095E37, 3.9025083E37, 1.1304424E38, 1.9060855E37, 1.4810831E38]",
                1.6998872E38f
        );
        rangeUpUniform_float_helper(
                (float) Math.PI,
                "[9.929094E37, 3.3988664E38, 1.6264806E38, 2.9887745E38, 2.3484774E37, 2.0084857E38, 3.076517E38," +
                " 1.293602E38, 2.6551666E38, 3.3685456E38, 9.823196E37, 3.2878975E37, 1.6963695E38, 6.2983905E37," +
                " 2.767365E38, 8.4991095E37, 3.9025083E37, 1.1304424E38, 1.9060855E37, 1.4810831E38]",
                1.6998872E38f
        );
        rangeUpUniform_float_helper(
                (float) Math.sqrt(2),
                "[9.929094E37, 3.3988664E38, 1.6264806E38, 2.9887745E38, 2.3484774E37, 2.0084857E38, 3.076517E38," +
                " 1.293602E38, 2.6551666E38, 3.3685456E38, 9.823196E37, 3.2878975E37, 1.6963695E38, 6.2983905E37," +
                " 2.767365E38, 8.4991095E37, 3.9025083E37, 1.1304424E38, 1.9060855E37, 1.4810831E38]",
                1.6998872E38f
        );
        rangeUpUniform_float_helper(
                (float) -Math.PI,
                "[9.929094E37, 3.3988664E38, 1.6264806E38, 2.9887745E38, 2.3484774E37, 2.0084857E38, 3.076517E38," +
                " 1.293602E38, 2.6551666E38, 3.3685456E38, 9.823196E37, 3.2878975E37, 1.6963695E38, 6.2983905E37," +
                " 2.767365E38, 8.4991095E37, 3.9025083E37, 1.1304424E38, 1.9060855E37, 1.4810831E38]",
                1.6998872E38f
        );
        rangeUpUniform_float_helper(
                (float) -Math.sqrt(2),
                "[9.929094E37, 3.3988664E38, 1.6264806E38, 2.9887745E38, 2.3484774E37, 2.0084857E38, 3.076517E38," +
                " 1.293602E38, 2.6551666E38, 3.3685456E38, 9.823196E37, 3.2878975E37, 1.6963695E38, 6.2983905E37," +
                " 2.767365E38, 8.4991095E37, 3.9025083E37, 1.1304424E38, 1.9060855E37, 1.4810831E38]",
                1.6998872E38f
        );
        rangeUpUniform_float_helper(
                0.0f,
                "[9.929094E37, 3.3988664E38, 1.6264806E38, 2.9887745E38, 2.3484774E37, 2.0084857E38, 3.076517E38," +
                " 1.293602E38, 2.6551666E38, 3.3685456E38, 9.823196E37, 3.2878975E37, 1.6963695E38, 6.2983905E37," +
                " 2.767365E38, 8.4991095E37, 3.9025083E37, 1.1304424E38, 1.9060855E37, 1.4810831E38]",
                1.6998872E38f
        );
        rangeUpUniform_float_helper(
                -0.0f,
                "[9.929094E37, 3.3988664E38, 1.6264806E38, 2.9887745E38, 2.3484774E37, 2.0084857E38, 3.076517E38," +
                " 1.293602E38, 2.6551666E38, 3.3685456E38, 9.823196E37, 3.2878975E37, 1.6963695E38, 6.2983905E37," +
                " 2.767365E38, 8.4991095E37, 3.9025083E37, 1.1304424E38, 1.9060855E37, 1.4810831E38]",
                1.6998872E38f
        );
        rangeUpUniform_float_helper(
                Float.MIN_VALUE,
                "[9.929094E37, 3.3988664E38, 1.6264806E38, 2.9887745E38, 2.3484774E37, 2.0084857E38, 3.076517E38," +
                " 1.293602E38, 2.6551666E38, 3.3685456E38, 9.823196E37, 3.2878975E37, 1.6963695E38, 6.2983905E37," +
                " 2.767365E38, 8.4991095E37, 3.9025083E37, 1.1304424E38, 1.9060855E37, 1.4810831E38]",
                1.6998872E38f
        );
        rangeUpUniform_float_helper(
                Float.MIN_NORMAL,
                "[9.929094E37, 3.3988664E38, 1.6264806E38, 2.9887745E38, 2.3484774E37, 2.0084857E38, 3.076517E38," +
                " 1.293602E38, 2.6551666E38, 3.3685456E38, 9.823196E37, 3.2878975E37, 1.6963695E38, 6.2983905E37," +
                " 2.767365E38, 8.4991095E37, 3.9025083E37, 1.1304424E38, 1.9060855E37, 1.4810831E38]",
                1.6998872E38f
        );
        rangeUpUniform_float_helper(
                -Float.MIN_VALUE,
                "[9.929094E37, 3.3988664E38, 1.6264806E38, 2.9887745E38, 2.3484774E37, 2.0084857E38, 3.076517E38," +
                " 1.293602E38, 2.6551666E38, 3.3685456E38, 9.823196E37, 3.2878975E37, 1.6963695E38, 6.2983905E37," +
                " 2.767365E38, 8.4991095E37, 3.9025083E37, 1.1304424E38, 1.9060855E37, 1.4810831E38]",
                1.6998872E38f
        );
        rangeUpUniform_float_helper(
                -Float.MIN_NORMAL,
                "[9.929094E37, 3.3988664E38, 1.6264806E38, 2.9887745E38, 2.3484774E37, 2.0084857E38, 3.076517E38," +
                " 1.293602E38, 2.6551666E38, 3.3685456E38, 9.823196E37, 3.2878975E37, 1.6963695E38, 6.2983905E37," +
                " 2.767365E38, 8.4991095E37, 3.9025083E37, 1.1304424E38, 1.9060855E37, 1.4810831E38]",
                1.6998872E38f
        );
        rangeUpUniform_float_helper(
                Float.MAX_VALUE,
                "[3.4028235E38, 3.4028235E38, 3.4028235E38, 3.4028235E38, 3.4028235E38, 3.4028235E38, 3.4028235E38," +
                " 3.4028235E38, 3.4028235E38, 3.4028235E38, 3.4028235E38, 3.4028235E38, 3.4028235E38, 3.4028235E38," +
                " 3.4028235E38, 3.4028235E38, 3.4028235E38, 3.4028235E38, 3.4028235E38, 3.4028235E38]",
                Float.POSITIVE_INFINITY
        );
        rangeUpUniform_float_helper(
                -Float.MAX_VALUE,
                "[9.929096E37, 3.3988666E38, -1.7763428E38, -4.1404879E37, -3.1679759E38, -1.3943378E38," +
                " -3.2630645E37, 1.2936022E38, 2.6551668E38, 3.3685458E38, -2.420504E38, -3.0740338E38," +
                " 1.6963697E38, -2.7729845E38, -6.354583E37, 8.4991115E37, -3.0125725E38, 1.1304426E38," +
                " 1.9060875E37, -1.9217403E38]",
                -1.1742675E35f
        );
        rangeUpUniform_float_fail_helper(Float.POSITIVE_INFINITY);
        rangeUpUniform_float_fail_helper(Float.NEGATIVE_INFINITY);
        rangeUpUniform_float_fail_helper(Float.NaN);
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
        nextFromRangeDownUniform_float_helper(1.0f, -9.929094E37f);
        nextFromRangeDownUniform_float_helper(1.0E20f, -9.929094E37f);
        nextFromRangeDownUniform_float_helper(-1.0f, -9.929094E37f);
        nextFromRangeDownUniform_float_helper(-1.0E20f, -9.929094E37f);
        nextFromRangeDownUniform_float_helper((float) Math.PI, -9.929094E37f);
        nextFromRangeDownUniform_float_helper((float) Math.sqrt(2), -9.929094E37f);
        nextFromRangeDownUniform_float_helper((float) -Math.PI, -9.929094E37f);
        nextFromRangeDownUniform_float_helper((float) -Math.sqrt(2), -9.929094E37f);
        nextFromRangeDownUniform_float_helper(0.0f, -9.929094E37f);
        nextFromRangeDownUniform_float_helper(-0.0f, -9.929094E37f);
        nextFromRangeDownUniform_float_helper(Float.MIN_VALUE, -9.929094E37f);
        nextFromRangeDownUniform_float_helper(Float.MIN_NORMAL, -9.929094E37f);
        nextFromRangeDownUniform_float_helper(-Float.MIN_VALUE, -9.929094E37f);
        nextFromRangeDownUniform_float_helper(-Float.MIN_NORMAL, -9.929094E37f);
        nextFromRangeDownUniform_float_helper(Float.MAX_VALUE, -9.929096E37f);
        nextFromRangeDownUniform_float_helper(-Float.MAX_VALUE, -3.4028235E38f);
        nextFromRangeDownUniform_float_fail_helper(Float.POSITIVE_INFINITY);
        nextFromRangeDownUniform_float_fail_helper(Float.NEGATIVE_INFINITY);
        nextFromRangeDownUniform_float_fail_helper(Float.NaN);
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
        rangeDownUniform_float_helper(
                1.0f,
                "[-9.929094E37, -3.3988664E38, -1.6264806E38, -2.9887745E38, -2.3484774E37, -2.0084857E38," +
                " -3.076517E38, -1.293602E38, -2.6551666E38, -3.3685456E38, -9.823196E37, -3.2878975E37," +
                " -1.6963695E38, -6.2983905E37, -2.767365E38, -8.4991095E37, -3.9025083E37, -1.1304424E38," +
                " -1.9060855E37, -1.4810831E38]",
                -1.6998872E38f
        );
        rangeDownUniform_float_helper(
                1.0E20f,
                "[-9.929094E37, -3.3988664E38, -1.6264806E38, -2.9887745E38, -2.3484774E37, -2.0084857E38," +
                " -3.076517E38, -1.293602E38, -2.6551666E38, -3.3685456E38, -9.823196E37, -3.2878975E37," +
                " -1.6963695E38, -6.2983905E37, -2.767365E38, -8.4991095E37, -3.9025083E37, -1.1304424E38," +
                " -1.9060855E37, -1.4810831E38]",
                -1.6998872E38f
        );
        rangeDownUniform_float_helper(
                -1.0f,
                "[-9.929094E37, -3.3988664E38, -1.6264806E38, -2.9887745E38, -2.3484774E37, -2.0084857E38," +
                " -3.076517E38, -1.293602E38, -2.6551666E38, -3.3685456E38, -9.823196E37, -3.2878975E37," +
                " -1.6963695E38, -6.2983905E37, -2.767365E38, -8.4991095E37, -3.9025083E37, -1.1304424E38," +
                " -1.9060855E37, -1.4810831E38]",
                -1.6998872E38f
        );
        rangeDownUniform_float_helper(
                -1.0E20f,
                "[-9.929094E37, -3.3988664E38, -1.6264806E38, -2.9887745E38, -2.3484774E37, -2.0084857E38," +
                " -3.076517E38, -1.293602E38, -2.6551666E38, -3.3685456E38, -9.823196E37, -3.2878975E37," +
                " -1.6963695E38, -6.2983905E37, -2.767365E38, -8.4991095E37, -3.9025083E37, -1.1304424E38," +
                " -1.9060855E37, -1.4810831E38]",
                -1.6998872E38f
        );
        rangeDownUniform_float_helper(
                (float) Math.PI,
                "[-9.929094E37, -3.3988664E38, -1.6264806E38, -2.9887745E38, -2.3484774E37, -2.0084857E38," +
                " -3.076517E38, -1.293602E38, -2.6551666E38, -3.3685456E38, -9.823196E37, -3.2878975E37," +
                " -1.6963695E38, -6.2983905E37, -2.767365E38, -8.4991095E37, -3.9025083E37, -1.1304424E38," +
                " -1.9060855E37, -1.4810831E38]",
                -1.6998872E38f
        );
        rangeDownUniform_float_helper(
                (float) Math.sqrt(2),
                "[-9.929094E37, -3.3988664E38, -1.6264806E38, -2.9887745E38, -2.3484774E37, -2.0084857E38," +
                " -3.076517E38, -1.293602E38, -2.6551666E38, -3.3685456E38, -9.823196E37, -3.2878975E37," +
                " -1.6963695E38, -6.2983905E37, -2.767365E38, -8.4991095E37, -3.9025083E37, -1.1304424E38," +
                " -1.9060855E37, -1.4810831E38]",
                -1.6998872E38f
        );
        rangeDownUniform_float_helper(
                (float) -Math.PI,
                "[-9.929094E37, -3.3988664E38, -1.6264806E38, -2.9887745E38, -2.3484774E37, -2.0084857E38," +
                " -3.076517E38, -1.293602E38, -2.6551666E38, -3.3685456E38, -9.823196E37, -3.2878975E37," +
                " -1.6963695E38, -6.2983905E37, -2.767365E38, -8.4991095E37, -3.9025083E37, -1.1304424E38," +
                " -1.9060855E37, -1.4810831E38]",
                -1.6998872E38f
        );
        rangeDownUniform_float_helper(
                (float) -Math.sqrt(2),
                "[-9.929094E37, -3.3988664E38, -1.6264806E38, -2.9887745E38, -2.3484774E37, -2.0084857E38," +
                " -3.076517E38, -1.293602E38, -2.6551666E38, -3.3685456E38, -9.823196E37, -3.2878975E37," +
                " -1.6963695E38, -6.2983905E37, -2.767365E38, -8.4991095E37, -3.9025083E37, -1.1304424E38," +
                " -1.9060855E37, -1.4810831E38]",
                -1.6998872E38f
        );
        rangeDownUniform_float_helper(
                0.0f,
                "[-9.929094E37, -3.3988664E38, -1.6264806E38, -2.9887745E38, -2.3484774E37, -2.0084857E38," +
                " -3.076517E38, -1.293602E38, -2.6551666E38, -3.3685456E38, -9.823196E37, -3.2878975E37," +
                " -1.6963695E38, -6.2983905E37, -2.767365E38, -8.4991095E37, -3.9025083E37, -1.1304424E38," +
                " -1.9060855E37, -1.4810831E38]",
                -1.6998872E38f
        );
        rangeDownUniform_float_helper(
                -0.0f,
                "[-9.929094E37, -3.3988664E38, -1.6264806E38, -2.9887745E38, -2.3484774E37, -2.0084857E38," +
                " -3.076517E38, -1.293602E38, -2.6551666E38, -3.3685456E38, -9.823196E37, -3.2878975E37," +
                " -1.6963695E38, -6.2983905E37, -2.767365E38, -8.4991095E37, -3.9025083E37, -1.1304424E38," +
                " -1.9060855E37, -1.4810831E38]",
                -1.6998872E38f
        );
        rangeDownUniform_float_helper(
                Float.MIN_VALUE,
                "[-9.929094E37, -3.3988664E38, -1.6264806E38, -2.9887745E38, -2.3484774E37, -2.0084857E38," +
                " -3.076517E38, -1.293602E38, -2.6551666E38, -3.3685456E38, -9.823196E37, -3.2878975E37," +
                " -1.6963695E38, -6.2983905E37, -2.767365E38, -8.4991095E37, -3.9025083E37, -1.1304424E38," +
                " -1.9060855E37, -1.4810831E38]",
                -1.6998872E38f
        );
        rangeDownUniform_float_helper(
                Float.MIN_NORMAL,
                "[-9.929094E37, -3.3988664E38, -1.6264806E38, -2.9887745E38, -2.3484774E37, -2.0084857E38," +
                " -3.076517E38, -1.293602E38, -2.6551666E38, -3.3685456E38, -9.823196E37, -3.2878975E37," +
                " -1.6963695E38, -6.2983905E37, -2.767365E38, -8.4991095E37, -3.9025083E37, -1.1304424E38," +
                " -1.9060855E37, -1.4810831E38]",
                -1.6998872E38f
        );
        rangeDownUniform_float_helper(
                -Float.MIN_VALUE,
                "[-9.929094E37, -3.3988664E38, -1.6264806E38, -2.9887745E38, -2.3484774E37, -2.0084857E38," +
                " -3.076517E38, -1.293602E38, -2.6551666E38, -3.3685456E38, -9.823196E37, -3.2878975E37," +
                " -1.6963695E38, -6.2983905E37, -2.767365E38, -8.4991095E37, -3.9025083E37, -1.1304424E38," +
                " -1.9060855E37, -1.4810831E38]",
                -1.6998872E38f
        );
        rangeDownUniform_float_helper(
                -Float.MIN_NORMAL,
                "[-9.929094E37, -3.3988664E38, -1.6264806E38, -2.9887745E38, -2.3484774E37, -2.0084857E38," +
                " -3.076517E38, -1.293602E38, -2.6551666E38, -3.3685456E38, -9.823196E37, -3.2878975E37," +
                " -1.6963695E38, -6.2983905E37, -2.767365E38, -8.4991095E37, -3.9025083E37, -1.1304424E38," +
                " -1.9060855E37, -1.4810831E38]",
                -1.6998872E38f
        );
        rangeDownUniform_float_helper(
                Float.MAX_VALUE,
                "[-9.929096E37, -3.3988666E38, 1.7763428E38, 4.1404879E37, 3.1679759E38, 1.3943378E38, 3.2630645E37," +
                " -1.2936022E38, -2.6551668E38, -3.3685458E38, 2.420504E38, 3.0740338E38, -1.6963697E38," +
                " 2.7729845E38, 6.354583E37, -8.4991115E37, 3.0125725E38, -1.1304426E38, -1.9060875E37, 1.9217403E38]",
                1.1742675E35f
        );
        rangeDownUniform_float_helper(
                -Float.MAX_VALUE,
                "[-3.4028235E38, -3.4028235E38, -3.4028235E38, -3.4028235E38, -3.4028235E38, -3.4028235E38," +
                " -3.4028235E38, -3.4028235E38, -3.4028235E38, -3.4028235E38, -3.4028235E38, -3.4028235E38," +
                " -3.4028235E38, -3.4028235E38, -3.4028235E38, -3.4028235E38, -3.4028235E38, -3.4028235E38," +
                " -3.4028235E38, -3.4028235E38]",
                Float.NEGATIVE_INFINITY
        );
        rangeDownUniform_float_fail_helper(Float.POSITIVE_INFINITY);
        rangeDownUniform_float_fail_helper(Float.NEGATIVE_INFINITY);
        rangeDownUniform_float_fail_helper(Float.NaN);
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
        nextFromRangeUniform_float_float_helper(1.0f, 2.0f, 1.4779798f);
        nextFromRangeUniform_float_float_helper(1.0f, 3.0f, 2.2917898f);
        nextFromRangeUniform_float_float_helper(1.0f, 4.0f, 2.2917898f);
        nextFromRangeUniform_float_float_helper(1.0f, 257.0f, 123.3628f);
        nextFromRangeUniform_float_float_helper(-257.0f, -1.0f, -134.63719f);
        nextFromRangeUniform_float_float_helper(1.0f, 1.0E20f, 4.9773216E19f);
        nextFromRangeUniform_float_float_helper(-1.0E20f, -1.0f, -5.0226786E19f);
        nextFromRangeUniform_float_float_helper((float) Math.sqrt(2), (float) Math.PI, 2.7060032f);
        nextFromRangeUniform_float_float_helper(
                (float) Math.PI,
                FloatingPointUtils.successor((float) Math.PI),
                3.1415927f
        );
        nextFromRangeUniform_float_float_helper(0.0f, 1.0f, 0.4779797f);
        nextFromRangeUniform_float_float_helper(-1.0f, 1.0f, 0.29178986f);
        nextFromRangeUniform_float_float_helper(1.0f, 1.0f, 1.0f);
        nextFromRangeUniform_float_float_fail_helper(Float.POSITIVE_INFINITY, 1.0f);
        nextFromRangeUniform_float_float_fail_helper(Float.NEGATIVE_INFINITY, 1.0f);
        nextFromRangeUniform_float_float_fail_helper(Float.NaN, 1.0f);
        nextFromRangeUniform_float_float_fail_helper(1.0f, Float.POSITIVE_INFINITY);
        nextFromRangeUniform_float_float_fail_helper(1.0f, Float.NEGATIVE_INFINITY);
        nextFromRangeUniform_float_float_fail_helper(1.0f, Float.NaN);
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
        rangeUniform_float_float_helper(
                1.0f,
                2.0f,
                "[1.4779798, 1.70171, 1.1755636, 1.212685, 1.1249284, 1.2886777, 1.4791377, 1.180959, 1.7496656," +
                " 1.5410224, 1.8132555, 1.0260291, 1.071269, 1.8903602, 1.3599277, 1.4352511, 1.4754711, 1.5650342," +
                " 1.1289092, 1.0106157]",
                1.5004492f
        );
        rangeUniform_float_float_helper(
                1.0f,
                3.0f,
                "[2.2917898, 2.7546365, 1.6833352, 2.3591893, 1.9012966, 2.748895, 2.5687912, 1.5946107, 2.5304637," +
                " 2.5909178, 1.6038429, 2.863659, 2.1885543, 2.732431, 2.2382114, 2.3627968, 2.8531759, 1.0102471," +
                " 1.1921239, 2.3838181]",
                1.9997782f
        );
        rangeUniform_float_float_helper(
                1.0f,
                4.0f,
                "[2.2917898, 2.7546365, 1.6833352, 3.8781257, 1.1406026, 3.0690157, 1.70171, 2.1750207, 1.212685," +
                " 2.130698, 3.2886777, 3.4791377, 1.180959, 3.7496655, 1.5410224, 1.8132555, 3.026029, 1.071269," +
                " 2.799311, 3.2146733]",
                2.4997394f
        );
        rangeUniform_float_float_helper(
                1.0f,
                257.0f,
                "[123.3628, 180.63776, 45.944298, 55.447342, 32.981674, 74.90151, 123.659225, 47.32549, 192.91441," +
                " 139.50175, 209.19342, 7.6634474, 19.244848, 228.93222, 93.14148, 112.4243, 122.72061, 145.64874," +
                " 34.000744, 3.717603]",
                129.06558f
        );
        rangeUniform_float_float_helper(
                -257.0f,
                -1.0f,
                "[-134.63719, -77.36224, -212.05571, -202.55266, -225.01833, -183.0985, -134.34077, -210.6745," +
                " -65.08559, -118.49824, -48.80658, -250.33655, -238.75514, -29.067768, -164.85852, -145.5757," +
                " -135.27939, -112.351265, -223.99925, -254.2824]",
                -128.92534f
        );
        rangeUniform_float_float_helper(
                1.0f,
                1.0E20f,
                "[4.9773216E19, 8.888516E19, 1.4761518E19, 1.9080083E19, 6.710465E19, 2.4857585E19, 8.376809E19," +
                " 2.4345974E19, 2.3135968E19, 6.7782996E19, 6.240156E19, 8.529099E19, 3.6669366E19, 7.639679E19," +
                " 1.9761569E19, 7.6550946E18, 1.8679647E19, 4.6447835E19, 2.1835817E19, 7.777299E19]",
                5.001496E19f
        );
        rangeUniform_float_float_helper(
                -1.0E20f,
                -1.0f,
                "[-5.0226786E19, -1.1114834E19, -8.523848E19, -8.091992E19, -3.2895352E19, -7.514241E19," +
                " -1.623191E19, -7.565403E19, -7.686403E19, -3.2217006E19, -3.7598442E19, -1.4709016E19," +
                " -6.333064E19, -2.360322E19, -8.023843E19, -9.23449E19, -8.1320355E19, -5.3552167E19," +
                " -7.8164185E19, -2.2227017E19]",
                -4.997858E19f
        );
        rangeUniform_float_float_helper(
                (float) Math.sqrt(2),
                (float) Math.PI,
                "[2.7060032, 1.9763035, 2.0975487, 2.2923393, 1.5548161, 1.4832292, 2.1159236, 2.5892344, 2.900004," +
                " 2.6741943, 2.9054244, 3.0993876, 2.2544162, 3.0841703, 1.5968397, 2.9816437, 2.2062478, 2.679206," +
                " 2.8621058, 3.0261319]",
                2.2780147f
        );
        rangeUniform_float_float_helper(
                (float) Math.PI,
                FloatingPointUtils.successor((float) Math.PI),
                "[3.1415927, 3.1415927, 3.1415927, 3.1415927, 3.1415927, 3.141593, 3.141593, 3.141593, 3.141593," +
                " 3.141593, 3.1415927, 3.141593, 3.1415927, 3.1415927, 3.1415927, 3.141593, 3.141593, 3.141593," +
                " 3.1415927, 3.141593]",
                3.1147525f
        );
        rangeUniform_float_float_helper(
                0.0f,
                1.0f,
                "[0.4779797, 0.70171, 0.17556366, 0.21268493, 0.12492842, 0.28867778, 0.4791376, 0.18095894," +
                " 0.7496657, 0.5410225, 0.81325555, 0.026029091, 0.07126894, 0.89036024, 0.35992765, 0.43525118," +
                " 0.47547114, 0.56503415, 0.12890916, 0.010615637]",
                0.50023586f
        );
        rangeUniform_float_float_helper(
                -1.0f,
                1.0f,
                "[0.29178986, 0.7546367, -0.31666487, 0.35918945, -0.09870329, 0.7488949, 0.56879103, -0.40538925," +
                " 0.53046364, 0.590918, -0.39615715, 0.8636589, 0.18855439, 0.73243093, 0.23821124, 0.3627968," +
                " 0.85317576, -0.98975295, -0.8078761, 0.38381794]",
                -3.822554E-4f
        );
        rangeUniform_float_float_helper(
                1.0f,
                1.0f,
                "[1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0]",
                1.0090389f
        );
        rangeUniform_float_float_helper(1.0f, -1.0f, "[]", 0.0f);
        rangeUniform_float_float_fail_helper(Float.POSITIVE_INFINITY, 1.0f);
        rangeUniform_float_float_fail_helper(Float.NEGATIVE_INFINITY, 1.0f);
        rangeUniform_float_float_fail_helper(Float.NaN, 1.0f);
        rangeUniform_float_float_fail_helper(1.0f, Float.POSITIVE_INFINITY);
        rangeUniform_float_float_fail_helper(1.0f, Float.NEGATIVE_INFINITY);
        rangeUniform_float_float_fail_helper(1.0f, Float.NaN);
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
        nextFromRangeUpUniform_double_helper(1.0, 6.010031716528839E307);
        nextFromRangeUpUniform_double_helper(1.0E20, 6.010031716528839E307);
        nextFromRangeUpUniform_double_helper(-1.0, 6.010031716528839E307);
        nextFromRangeUpUniform_double_helper(-1.0E20, 6.010031716528839E307);
        nextFromRangeUpUniform_double_helper(Math.PI, 6.010031716528839E307);
        nextFromRangeUpUniform_double_helper(Math.sqrt(2), 6.010031716528839E307);
        nextFromRangeUpUniform_double_helper(-Math.PI, 6.010031716528839E307);
        nextFromRangeUpUniform_double_helper(-Math.sqrt(2), 6.010031716528839E307);
        nextFromRangeUpUniform_double_helper(0.0, 6.010031716528839E307);
        nextFromRangeUpUniform_double_helper(-0.0, 6.010031716528839E307);
        nextFromRangeUpUniform_double_helper(Double.MIN_VALUE, 6.010031716528839E307);
        nextFromRangeUpUniform_double_helper(Double.MIN_NORMAL, 6.010031716528839E307);
        nextFromRangeUpUniform_double_helper(-Double.MIN_VALUE, 6.010031716528839E307);
        nextFromRangeUpUniform_double_helper(-Double.MIN_NORMAL, 6.010031716528839E307);
        nextFromRangeUpUniform_double_helper(Double.MAX_VALUE, 1.7976931348623157E308);
        nextFromRangeUpUniform_double_helper(-Double.MAX_VALUE, -1.1966899632094317E308);
        nextFromRangeUpUniform_double_fail_helper(Double.POSITIVE_INFINITY);
        nextFromRangeUpUniform_double_fail_helper(Double.NEGATIVE_INFINITY);
        nextFromRangeUpUniform_double_fail_helper(Double.NaN);
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
        rangeUpUniform_double_helper(
                1.0,
                "[6.010031716528839E307, 1.0138295960478667E308, 5.899671652551286E307, 1.126139741743928E307," +
                " 7.792941514562586E307, 7.116301123722935E307, 1.6486699798227538E308, 2.8116085611644773E307," +
                " 1.3981609323339555E308, 1.1533582639738811E308, 8.382602851978314E307, 1.2599937155037416E308," +
                " 1.3884097958031977E308, 1.2875997268467397E308, 5.684802572807711E307, 8.174351118522927E307," +
                " 2.5109586043466547E307, 1.3589111426555418E308, 1.093640071507309E308, 1.575071884935976E308]",
                8.995051139308303E307
        );
        rangeUpUniform_double_helper(
                1.0E20,
                "[6.010031716528839E307, 1.0138295960478667E308, 5.899671652551286E307, 1.126139741743928E307," +
                " 7.792941514562586E307, 7.116301123722935E307, 1.6486699798227538E308, 2.8116085611644773E307," +
                " 1.3981609323339555E308, 1.1533582639738811E308, 8.382602851978314E307, 1.2599937155037416E308," +
                " 1.3884097958031977E308, 1.2875997268467397E308, 5.684802572807711E307, 8.174351118522927E307," +
                " 2.5109586043466547E307, 1.3589111426555418E308, 1.093640071507309E308, 1.575071884935976E308]",
                8.995051139308303E307
        );
        rangeUpUniform_double_helper(
                -1.0,
                "[6.010031716528839E307, 1.0138295960478667E308, 5.899671652551286E307, 1.126139741743928E307," +
                " 7.792941514562586E307, 7.116301123722935E307, 1.6486699798227538E308, 2.8116085611644773E307," +
                " 1.3981609323339555E308, 1.1533582639738811E308, 8.382602851978314E307, 1.2599937155037416E308," +
                " 1.3884097958031977E308, 1.2875997268467397E308, 5.684802572807711E307, 8.174351118522927E307," +
                " 2.5109586043466547E307, 1.3589111426555418E308, 1.093640071507309E308, 1.575071884935976E308]",
                8.995051139308303E307
        );
        rangeUpUniform_double_helper(
                -1.0E20,
                "[6.010031716528839E307, 1.0138295960478667E308, 5.899671652551286E307, 1.126139741743928E307," +
                " 7.792941514562586E307, 7.116301123722935E307, 1.6486699798227538E308, 2.8116085611644773E307," +
                " 1.3981609323339555E308, 1.1533582639738811E308, 8.382602851978314E307, 1.2599937155037416E308," +
                " 1.3884097958031977E308, 1.2875997268467397E308, 5.684802572807711E307, 8.174351118522927E307," +
                " 2.5109586043466547E307, 1.3589111426555418E308, 1.093640071507309E308, 1.575071884935976E308]",
                8.995051139308303E307
        );
        rangeUpUniform_double_helper(
                Math.PI,
                "[6.010031716528839E307, 1.0138295960478667E308, 5.899671652551286E307, 1.126139741743928E307," +
                " 7.792941514562586E307, 7.116301123722935E307, 1.6486699798227538E308, 2.8116085611644773E307," +
                " 1.3981609323339555E308, 1.1533582639738811E308, 8.382602851978314E307, 1.2599937155037416E308," +
                " 1.3884097958031977E308, 1.2875997268467397E308, 5.684802572807711E307, 8.174351118522927E307," +
                " 2.5109586043466547E307, 1.3589111426555418E308, 1.093640071507309E308, 1.575071884935976E308]",
                8.995051139308303E307
        );
        rangeUpUniform_double_helper(
                Math.sqrt(2),
                "[6.010031716528839E307, 1.0138295960478667E308, 5.899671652551286E307, 1.126139741743928E307," +
                " 7.792941514562586E307, 7.116301123722935E307, 1.6486699798227538E308, 2.8116085611644773E307," +
                " 1.3981609323339555E308, 1.1533582639738811E308, 8.382602851978314E307, 1.2599937155037416E308," +
                " 1.3884097958031977E308, 1.2875997268467397E308, 5.684802572807711E307, 8.174351118522927E307," +
                " 2.5109586043466547E307, 1.3589111426555418E308, 1.093640071507309E308, 1.575071884935976E308]",
                8.995051139308303E307
        );
        rangeUpUniform_double_helper(
                -Math.PI,
                "[6.010031716528839E307, 1.0138295960478667E308, 5.899671652551286E307, 1.126139741743928E307," +
                " 7.792941514562586E307, 7.116301123722935E307, 1.6486699798227538E308, 2.8116085611644773E307," +
                " 1.3981609323339555E308, 1.1533582639738811E308, 8.382602851978314E307, 1.2599937155037416E308," +
                " 1.3884097958031977E308, 1.2875997268467397E308, 5.684802572807711E307, 8.174351118522927E307," +
                " 2.5109586043466547E307, 1.3589111426555418E308, 1.093640071507309E308, 1.575071884935976E308]",
                8.995051139308303E307
        );
        rangeUpUniform_double_helper(
                -Math.sqrt(2),
                "[6.010031716528839E307, 1.0138295960478667E308, 5.899671652551286E307, 1.126139741743928E307," +
                " 7.792941514562586E307, 7.116301123722935E307, 1.6486699798227538E308, 2.8116085611644773E307," +
                " 1.3981609323339555E308, 1.1533582639738811E308, 8.382602851978314E307, 1.2599937155037416E308," +
                " 1.3884097958031977E308, 1.2875997268467397E308, 5.684802572807711E307, 8.174351118522927E307," +
                " 2.5109586043466547E307, 1.3589111426555418E308, 1.093640071507309E308, 1.575071884935976E308]",
                8.995051139308303E307
        );
        rangeUpUniform_double_helper(
                0.0,
                "[6.010031716528839E307, 1.0138295960478667E308, 5.899671652551286E307, 1.126139741743928E307," +
                " 7.792941514562586E307, 7.116301123722935E307, 1.6486699798227538E308, 2.8116085611644773E307," +
                " 1.3981609323339555E308, 1.1533582639738811E308, 8.382602851978314E307, 1.2599937155037416E308," +
                " 1.3884097958031977E308, 1.2875997268467397E308, 5.684802572807711E307, 8.174351118522927E307," +
                " 2.5109586043466547E307, 1.3589111426555418E308, 1.093640071507309E308, 1.575071884935976E308]",
                8.995051139308303E307
        );
        rangeUpUniform_double_helper(
                -0.0,
                "[6.010031716528839E307, 1.0138295960478667E308, 5.899671652551286E307, 1.126139741743928E307," +
                " 7.792941514562586E307, 7.116301123722935E307, 1.6486699798227538E308, 2.8116085611644773E307," +
                " 1.3981609323339555E308, 1.1533582639738811E308, 8.382602851978314E307, 1.2599937155037416E308," +
                " 1.3884097958031977E308, 1.2875997268467397E308, 5.684802572807711E307, 8.174351118522927E307," +
                " 2.5109586043466547E307, 1.3589111426555418E308, 1.093640071507309E308, 1.575071884935976E308]",
                8.995051139308303E307
        );
        rangeUpUniform_double_helper(
                Double.MIN_VALUE,
                "[6.010031716528839E307, 1.0138295960478667E308, 5.899671652551286E307, 1.126139741743928E307," +
                " 7.792941514562586E307, 7.116301123722935E307, 1.6486699798227538E308, 2.8116085611644773E307," +
                " 1.3981609323339555E308, 1.1533582639738811E308, 8.382602851978314E307, 1.2599937155037416E308," +
                " 1.3884097958031977E308, 1.2875997268467397E308, 5.684802572807711E307, 8.174351118522927E307," +
                " 2.5109586043466547E307, 1.3589111426555418E308, 1.093640071507309E308, 1.575071884935976E308]",
                8.995051139308303E307
        );
        rangeUpUniform_double_helper(
                Double.MIN_NORMAL,
                "[6.010031716528839E307, 1.0138295960478667E308, 5.899671652551286E307, 1.126139741743928E307," +
                " 7.792941514562586E307, 7.116301123722935E307, 1.6486699798227538E308, 2.8116085611644773E307," +
                " 1.3981609323339555E308, 1.1533582639738811E308, 8.382602851978314E307, 1.2599937155037416E308," +
                " 1.3884097958031977E308, 1.2875997268467397E308, 5.684802572807711E307, 8.174351118522927E307," +
                " 2.5109586043466547E307, 1.3589111426555418E308, 1.093640071507309E308, 1.575071884935976E308]",
                8.995051139308303E307
        );
        rangeUpUniform_double_helper(
                -Double.MIN_VALUE,
                "[6.010031716528839E307, 1.0138295960478667E308, 5.899671652551286E307, 1.126139741743928E307," +
                " 7.792941514562586E307, 7.116301123722935E307, 1.6486699798227538E308, 2.8116085611644773E307," +
                " 1.3981609323339555E308, 1.1533582639738811E308, 8.382602851978314E307, 1.2599937155037416E308," +
                " 1.3884097958031977E308, 1.2875997268467397E308, 5.684802572807711E307, 8.174351118522927E307," +
                " 2.5109586043466547E307, 1.3589111426555418E308, 1.093640071507309E308, 1.575071884935976E308]",
                8.995051139308303E307
        );
        rangeUpUniform_double_helper(
                -Double.MIN_NORMAL,
                "[6.010031716528839E307, 1.0138295960478667E308, 5.899671652551286E307, 1.126139741743928E307," +
                " 7.792941514562586E307, 7.116301123722935E307, 1.6486699798227538E308, 2.8116085611644773E307," +
                " 1.3981609323339555E308, 1.1533582639738811E308, 8.382602851978314E307, 1.2599937155037416E308," +
                " 1.3884097958031977E308, 1.2875997268467397E308, 5.684802572807711E307, 8.174351118522927E307," +
                " 2.5109586043466547E307, 1.3589111426555418E308, 1.093640071507309E308, 1.575071884935976E308]",
                8.995051139308303E307
        );
        rangeUpUniform_double_helper(
                Double.MAX_VALUE,
                "[1.7976931348623157E308, 1.7976931348623157E308, 1.7976931348623157E308, 1.7976931348623157E308," +
                " 1.7976931348623157E308, 1.7976931348623157E308, 1.7976931348623157E308, 1.7976931348623157E308," +
                " 1.7976931348623157E308, 1.7976931348623157E308, 1.7976931348623157E308, 1.7976931348623157E308," +
                " 1.7976931348623157E308, 1.7976931348623157E308, 1.7976931348623157E308, 1.7976931348623157E308," +
                " 1.7976931348623157E308, 1.7976931348623157E308, 1.7976931348623157E308, 1.7976931348623157E308]",
                Double.POSITIVE_INFINITY
        );
        rangeUpUniform_double_helper(
                -Double.MAX_VALUE,
                "[-1.1966899632094317E308, -7.838635388144489E307, -1.207725969607187E308, 1.12613974174393E307," +
                " -1.0183989834060572E308, -1.0860630224900222E308, 1.648669979822754E308, -1.516532278745868E308," +
                " 1.3981609323339557E308, -6.443348708884346E307, -9.594328496644843E307, -5.37699419358574E307," +
                " -4.0928333905911806E307, 1.2875997268467399E308, -1.2292128775815446E308, 8.174351118522929E307," +
                " 2.5109586043466566E307, 1.358911142655542E308, 1.0936400715073092E308, -2.226212499263397E307]",
                5.32707980219756E304
        );
        rangeUpUniform_double_fail_helper(Double.POSITIVE_INFINITY);
        rangeUpUniform_double_fail_helper(Double.NEGATIVE_INFINITY);
        rangeUpUniform_double_fail_helper(Double.NaN);
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
        nextFromRangeDownUniform_double_helper(1.0, -6.010031716528839E307);
        nextFromRangeDownUniform_double_helper(1.0E20, -6.010031716528839E307);
        nextFromRangeDownUniform_double_helper(-1.0, -6.010031716528839E307);
        nextFromRangeDownUniform_double_helper(-1.0E20, -6.010031716528839E307);
        nextFromRangeDownUniform_double_helper(Math.PI, -6.010031716528839E307);
        nextFromRangeDownUniform_double_helper(Math.sqrt(2), -6.010031716528839E307);
        nextFromRangeDownUniform_double_helper(-Math.PI, -6.010031716528839E307);
        nextFromRangeDownUniform_double_helper(-Math.sqrt(2), -6.010031716528839E307);
        nextFromRangeDownUniform_double_helper(0.0, -6.010031716528839E307);
        nextFromRangeDownUniform_double_helper(-0.0, -6.010031716528839E307);
        nextFromRangeDownUniform_double_helper(Double.MIN_VALUE, -6.010031716528839E307);
        nextFromRangeDownUniform_double_helper(Double.MIN_NORMAL, -6.010031716528839E307);
        nextFromRangeDownUniform_double_helper(-Double.MIN_VALUE, -6.010031716528839E307);
        nextFromRangeDownUniform_double_helper(-Double.MIN_NORMAL, -6.010031716528839E307);
        nextFromRangeDownUniform_double_helper(Double.MAX_VALUE, 1.1966899632094317E308);
        nextFromRangeDownUniform_double_helper(-Double.MAX_VALUE, -1.7976931348623157E308);
        nextFromRangeDownUniform_double_fail_helper(Double.POSITIVE_INFINITY);
        nextFromRangeDownUniform_double_fail_helper(Double.NEGATIVE_INFINITY);
        nextFromRangeDownUniform_double_fail_helper(Double.NaN);
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
        rangeDownUniform_double_helper(
                1.0,
                "[-6.010031716528839E307, -1.0138295960478667E308, -5.899671652551286E307, -1.126139741743928E307," +
                " -7.792941514562586E307, -7.116301123722935E307, -1.6486699798227538E308, -2.8116085611644773E307," +
                " -1.3981609323339555E308, -1.1533582639738811E308, -8.382602851978314E307, -1.2599937155037416E308," +
                " -1.3884097958031977E308, -1.2875997268467397E308, -5.684802572807711E307, -8.174351118522927E307," +
                " -2.5109586043466547E307, -1.3589111426555418E308, -1.093640071507309E308, -1.575071884935976E308]",
                -8.995051139308303E307
        );
        rangeDownUniform_double_helper(
                1.0E20,
                "[-6.010031716528839E307, -1.0138295960478667E308, -5.899671652551286E307, -1.126139741743928E307," +
                " -7.792941514562586E307, -7.116301123722935E307, -1.6486699798227538E308, -2.8116085611644773E307," +
                " -1.3981609323339555E308, -1.1533582639738811E308, -8.382602851978314E307, -1.2599937155037416E308," +
                " -1.3884097958031977E308, -1.2875997268467397E308, -5.684802572807711E307, -8.174351118522927E307," +
                " -2.5109586043466547E307, -1.3589111426555418E308, -1.093640071507309E308, -1.575071884935976E308]",
                -8.995051139308303E307
        );
        rangeDownUniform_double_helper(
                -1.0,
                "[-6.010031716528839E307, -1.0138295960478667E308, -5.899671652551286E307, -1.126139741743928E307," +
                " -7.792941514562586E307, -7.116301123722935E307, -1.6486699798227538E308, -2.8116085611644773E307," +
                " -1.3981609323339555E308, -1.1533582639738811E308, -8.382602851978314E307, -1.2599937155037416E308," +
                " -1.3884097958031977E308, -1.2875997268467397E308, -5.684802572807711E307, -8.174351118522927E307," +
                " -2.5109586043466547E307, -1.3589111426555418E308, -1.093640071507309E308, -1.575071884935976E308]",
                -8.995051139308303E307
        );
        rangeDownUniform_double_helper(
                -1.0E20,
                "[-6.010031716528839E307, -1.0138295960478667E308, -5.899671652551286E307, -1.126139741743928E307," +
                " -7.792941514562586E307, -7.116301123722935E307, -1.6486699798227538E308, -2.8116085611644773E307," +
                " -1.3981609323339555E308, -1.1533582639738811E308, -8.382602851978314E307, -1.2599937155037416E308," +
                " -1.3884097958031977E308, -1.2875997268467397E308, -5.684802572807711E307, -8.174351118522927E307," +
                " -2.5109586043466547E307, -1.3589111426555418E308, -1.093640071507309E308, -1.575071884935976E308]",
                -8.995051139308303E307
        );
        rangeDownUniform_double_helper(
                Math.PI,
                "[-6.010031716528839E307, -1.0138295960478667E308, -5.899671652551286E307, -1.126139741743928E307," +
                " -7.792941514562586E307, -7.116301123722935E307, -1.6486699798227538E308, -2.8116085611644773E307," +
                " -1.3981609323339555E308, -1.1533582639738811E308, -8.382602851978314E307, -1.2599937155037416E308," +
                " -1.3884097958031977E308, -1.2875997268467397E308, -5.684802572807711E307, -8.174351118522927E307," +
                " -2.5109586043466547E307, -1.3589111426555418E308, -1.093640071507309E308, -1.575071884935976E308]",
                -8.995051139308303E307
        );
        rangeDownUniform_double_helper(
                Math.sqrt(2),
                "[-6.010031716528839E307, -1.0138295960478667E308, -5.899671652551286E307, -1.126139741743928E307," +
                " -7.792941514562586E307, -7.116301123722935E307, -1.6486699798227538E308, -2.8116085611644773E307," +
                " -1.3981609323339555E308, -1.1533582639738811E308, -8.382602851978314E307, -1.2599937155037416E308," +
                " -1.3884097958031977E308, -1.2875997268467397E308, -5.684802572807711E307, -8.174351118522927E307," +
                " -2.5109586043466547E307, -1.3589111426555418E308, -1.093640071507309E308, -1.575071884935976E308]",
                -8.995051139308303E307
        );
        rangeDownUniform_double_helper(
                -Math.PI,
                "[-6.010031716528839E307, -1.0138295960478667E308, -5.899671652551286E307, -1.126139741743928E307," +
                " -7.792941514562586E307, -7.116301123722935E307, -1.6486699798227538E308, -2.8116085611644773E307," +
                " -1.3981609323339555E308, -1.1533582639738811E308, -8.382602851978314E307, -1.2599937155037416E308," +
                " -1.3884097958031977E308, -1.2875997268467397E308, -5.684802572807711E307, -8.174351118522927E307," +
                " -2.5109586043466547E307, -1.3589111426555418E308, -1.093640071507309E308, -1.575071884935976E308]",
                -8.995051139308303E307
        );
        rangeDownUniform_double_helper(
                -Math.sqrt(2),
                "[-6.010031716528839E307, -1.0138295960478667E308, -5.899671652551286E307, -1.126139741743928E307," +
                " -7.792941514562586E307, -7.116301123722935E307, -1.6486699798227538E308, -2.8116085611644773E307," +
                " -1.3981609323339555E308, -1.1533582639738811E308, -8.382602851978314E307, -1.2599937155037416E308," +
                " -1.3884097958031977E308, -1.2875997268467397E308, -5.684802572807711E307, -8.174351118522927E307," +
                " -2.5109586043466547E307, -1.3589111426555418E308, -1.093640071507309E308, -1.575071884935976E308]",
                -8.995051139308303E307
        );
        rangeDownUniform_double_helper(
                0.0,
                "[-6.010031716528839E307, -1.0138295960478667E308, -5.899671652551286E307, -1.126139741743928E307," +
                " -7.792941514562586E307, -7.116301123722935E307, -1.6486699798227538E308, -2.8116085611644773E307," +
                " -1.3981609323339555E308, -1.1533582639738811E308, -8.382602851978314E307, -1.2599937155037416E308," +
                " -1.3884097958031977E308, -1.2875997268467397E308, -5.684802572807711E307, -8.174351118522927E307," +
                " -2.5109586043466547E307, -1.3589111426555418E308, -1.093640071507309E308, -1.575071884935976E308]",
                -8.995051139308303E307
        );
        rangeDownUniform_double_helper(
                -0.0,
                "[-6.010031716528839E307, -1.0138295960478667E308, -5.899671652551286E307, -1.126139741743928E307," +
                " -7.792941514562586E307, -7.116301123722935E307, -1.6486699798227538E308, -2.8116085611644773E307," +
                " -1.3981609323339555E308, -1.1533582639738811E308, -8.382602851978314E307, -1.2599937155037416E308," +
                " -1.3884097958031977E308, -1.2875997268467397E308, -5.684802572807711E307, -8.174351118522927E307," +
                " -2.5109586043466547E307, -1.3589111426555418E308, -1.093640071507309E308, -1.575071884935976E308]",
                -8.995051139308303E307
        );
        rangeDownUniform_double_helper(
                Double.MIN_VALUE,
                "[-6.010031716528839E307, -1.0138295960478667E308, -5.899671652551286E307, -1.126139741743928E307," +
                " -7.792941514562586E307, -7.116301123722935E307, -1.6486699798227538E308, -2.8116085611644773E307," +
                " -1.3981609323339555E308, -1.1533582639738811E308, -8.382602851978314E307, -1.2599937155037416E308," +
                " -1.3884097958031977E308, -1.2875997268467397E308, -5.684802572807711E307, -8.174351118522927E307," +
                " -2.5109586043466547E307, -1.3589111426555418E308, -1.093640071507309E308, -1.575071884935976E308]",
                -8.995051139308303E307
        );
        rangeDownUniform_double_helper(
                Double.MIN_NORMAL,
                "[-6.010031716528839E307, -1.0138295960478667E308, -5.899671652551286E307, -1.126139741743928E307," +
                " -7.792941514562586E307, -7.116301123722935E307, -1.6486699798227538E308, -2.8116085611644773E307," +
                " -1.3981609323339555E308, -1.1533582639738811E308, -8.382602851978314E307, -1.2599937155037416E308," +
                " -1.3884097958031977E308, -1.2875997268467397E308, -5.684802572807711E307, -8.174351118522927E307," +
                " -2.5109586043466547E307, -1.3589111426555418E308, -1.093640071507309E308, -1.575071884935976E308]",
                -8.995051139308303E307
        );
        rangeDownUniform_double_helper(
                -Double.MIN_VALUE,
                "[-6.010031716528839E307, -1.0138295960478667E308, -5.899671652551286E307, -1.126139741743928E307," +
                " -7.792941514562586E307, -7.116301123722935E307, -1.6486699798227538E308, -2.8116085611644773E307," +
                " -1.3981609323339555E308, -1.1533582639738811E308, -8.382602851978314E307, -1.2599937155037416E308," +
                " -1.3884097958031977E308, -1.2875997268467397E308, -5.684802572807711E307, -8.174351118522927E307," +
                " -2.5109586043466547E307, -1.3589111426555418E308, -1.093640071507309E308, -1.575071884935976E308]",
                -8.995051139308303E307
        );
        rangeDownUniform_double_helper(
                -Double.MIN_NORMAL,
                "[-6.010031716528839E307, -1.0138295960478667E308, -5.899671652551286E307, -1.126139741743928E307," +
                " -7.792941514562586E307, -7.116301123722935E307, -1.6486699798227538E308, -2.8116085611644773E307," +
                " -1.3981609323339555E308, -1.1533582639738811E308, -8.382602851978314E307, -1.2599937155037416E308," +
                " -1.3884097958031977E308, -1.2875997268467397E308, -5.684802572807711E307, -8.174351118522927E307," +
                " -2.5109586043466547E307, -1.3589111426555418E308, -1.093640071507309E308, -1.575071884935976E308]",
                -8.995051139308303E307
        );
        rangeDownUniform_double_helper(
                Double.MAX_VALUE,
                "[1.1966899632094317E308, 7.838635388144489E307, 1.207725969607187E308, -1.12613974174393E307," +
                " 1.0183989834060572E308, 1.0860630224900222E308, -1.648669979822754E308, 1.516532278745868E308," +
                " -1.3981609323339557E308, 6.443348708884346E307, 9.594328496644843E307, 5.37699419358574E307," +
                " 4.0928333905911806E307, -1.2875997268467399E308, 1.2292128775815446E308, -8.174351118522929E307," +
                " -2.5109586043466566E307, -1.358911142655542E308, -1.0936400715073092E308, 2.226212499263397E307]",
                -5.32707980219756E304
        );
        rangeDownUniform_double_helper(
                -Double.MAX_VALUE,
                "[-1.7976931348623157E308, -1.7976931348623157E308, -1.7976931348623157E308," +
                " -1.7976931348623157E308, -1.7976931348623157E308, -1.7976931348623157E308," +
                " -1.7976931348623157E308, -1.7976931348623157E308, -1.7976931348623157E308," +
                " -1.7976931348623157E308, -1.7976931348623157E308, -1.7976931348623157E308," +
                " -1.7976931348623157E308, -1.7976931348623157E308, -1.7976931348623157E308," +
                " -1.7976931348623157E308, -1.7976931348623157E308, -1.7976931348623157E308," +
                " -1.7976931348623157E308, -1.7976931348623157E308]",
                Double.NEGATIVE_INFINITY
        );
        rangeDownUniform_double_fail_helper(Double.POSITIVE_INFINITY);
        rangeDownUniform_double_fail_helper(Double.NEGATIVE_INFINITY);
        rangeDownUniform_double_fail_helper(Double.NaN);
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
        nextFromRangeUniform_double_double_helper(1.0, 2.0, 1.3343191115311872);
        nextFromRangeUniform_double_double_helper(1.0, 3.0, 2.124820775773226);
        nextFromRangeUniform_double_double_helper(1.0, 4.0, 3.334319111531187);
        nextFromRangeUniform_double_double_helper(1.0, 257.0, 86.58569255198388);
        nextFromRangeUniform_double_double_helper(-257.0, -1.0, -171.4143074480161);
        nextFromRangeUniform_double_double_helper(1.0, 1.0E20, 4.3060587236784865E19);
        nextFromRangeUniform_double_double_helper(-1.0E20, -1.0, -5.6939412763215135E19);
        nextFromRangeUniform_double_double_helper(Math.sqrt(2), Math.PI, 1.7485326739042824);
        nextFromRangeUniform_double_double_helper(Math.PI, FloatingPointUtils.successor(Math.PI), 3.141592653589793);
        nextFromRangeUniform_double_double_helper(0.0, 1.0, 0.33431911153118704);
        nextFromRangeUniform_double_double_helper(-1.0, 1.0, 0.12482077577322558);
        nextFromRangeUniform_double_double_helper(1.0, 1.0, 1.0);
        nextFromRangeUniform_double_double_fail_helper(Double.POSITIVE_INFINITY, 1.0);
        nextFromRangeUniform_double_double_fail_helper(Double.NEGATIVE_INFINITY, 1.0);
        nextFromRangeUniform_double_double_fail_helper(Double.NaN, 1.0);
        nextFromRangeUniform_double_double_fail_helper(1.0, Double.POSITIVE_INFINITY);
        nextFromRangeUniform_double_double_fail_helper(1.0, Double.NEGATIVE_INFINITY);
        nextFromRangeUniform_double_double_fail_helper(1.0, Double.NaN);
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
        rangeUniform_double_double_helper(
                1.0,
                2.0,
                "[1.3343191115311872, 1.7164537038091727, 1.629689898170449, 1.0620499078907901, 1.0070295178320794," +
                " 1.1055886056335171, 1.7912897203519784, 1.1192866540796487, 1.7308179810186062, 1.063764379699292," +
                " 1.782308560677746, 1.4041074043452362, 1.5365871735809418, 1.4106654220987198, 1.9790983240333448," +
                " 1.8761628191101756, 1.3189165198381456, 1.8840415617290818, 1.3217374561233834, 1.496719847980412]",
                1.4998662259843438
        );
        rangeUniform_double_double_helper(
                1.0,
                3.0,
                "[2.124820775773226, 2.3596563026544994, 2.0090952290454784, 2.526430611842989, 2.110756631919464," +
                " 1.049153375764955, 2.7629038029823683, 1.792423878244365, 2.713094190543024, 1.3445094143093301," +
                " 2.531261912919952, 2.06678185534416, 2.7063760258321636, 1.2961270385521826, 2.7459676067821013," +
                " 2.5094978232121363, 1.5040769030924148, 2.4326118565222328, 2.5811492973948575, 1.129113909749645]",
                2.00069067968375
        );
        rangeUniform_double_double_helper(
                1.0,
                4.0,
                "[3.334319111531187, 2.8073529339848506, 2.7013519566577724, 2.9056898966678517, 3.879421179826264," +
                " 2.7278146309731004, 2.3885143769812416, 2.537444496215219, 2.842514934938691, 1.0048197521726072," +
                " 3.63301610358896, 2.6015919747731266, 1.57775549528454, 2.9963239068862135, 3.32840224280665," +
                " 2.9342485414923685, 1.2784411059656287, 3.4431106883316653, 2.358446488603696, 3.4484420591313727]",
                2.5002819717191107
        );
        rangeUniform_double_double_helper(
                1.0,
                257.0,
                "[86.58569255198388, 184.4121481751482, 162.20061393163493, 16.88477642004231, 2.799556565012354," +
                " 28.030683042180375, 203.5701684101065, 31.537383444390034, 188.08940314076318, 17.323681203018765," +
                " 201.27099153350298, 104.45149551238049, 138.3663164367211, 106.13034805727231, 251.64917095253625," +
                " 225.29768169220497, 82.64262907856532, 227.31463980264493, 83.36478876758619, 128.1602810829855]",
                128.96575385200788
        );
        rangeUniform_double_double_helper(
                -257.0,
                -1.0,
                "[-171.4143074480161, -73.58785182485182, -95.79938606836508, -241.1152235799577," +
                " -255.20044343498765, -229.96931695781961, -54.429831589893475, -226.46261655560994," +
                " -69.9105968592368, -240.67631879698123, -56.72900846649702, -153.5485044876195," +
                " -119.63368356327891, -151.8696519427277, -6.35082904746375, -32.70231830779502," +
                " -175.3573709214347, -30.685360197355052, -174.6352112324138, -129.8397189170145]",
                -129.03424614799135
        );
        rangeUniform_double_double_helper(
                1.0,
                1.0E20,
                "[4.3060587236784865E19, 7.68628703560362E19, 1.4258982999090563E19, 2.9914358412105785E19," +
                " 1.5286552884045974E19, 6.447454776058684E19, 7.736125310679278E19, 8.4165193575587809E18," +
                " 1.6178674013524316E19, 8.966123992848844E19, 6.147834669126863E19, 3.884125413264044E19," +
                " 2.695977612548968E19, 1.9678651868010762E19, 7.462487392097021E19, 1.2673621558366882E19," +
                " 2.259839286271006E19, 4.33318538326881E19, 2.7559717446606E19, 2.5100758968504177E19]",
                5.002316023213162E19
        );
        rangeUniform_double_double_helper(
                -1.0E20,
                -1.0,
                "[-5.6939412763215135E19, -2.3137129643963793E19, -8.574101700090942E19, -7.0085641587894215E19," +
                " -8.471344711595403E19, -3.5525452239413158E19, -2.263874689320722E19, -9.158348064244122E19," +
                " -8.382132598647569E19, -1.033876007151155E19, -3.852165330873137E19, -6.115874586735956E19," +
                " -7.304022387451032E19, -8.032134813198924E19, -2.537512607902979E19, -8.73263784416331E19," +
                " -7.740160713728993E19, -5.66681461673119E19, -7.2440282553394E19, -7.489924103149583E19]",
                -4.997683976786839E19
        );
        rangeUniform_double_double_helper(
                Math.sqrt(2),
                Math.PI,
                "[1.7485326739042824, 2.8921446285660712, 3.1155655190308678, 2.7141745935267587," +
                " 2.8931234985776046, 2.795966123935251, 2.8027279393543365, 2.9516580585883143, 3.0432481886718703," +
                " 2.764091929394239, 3.0369005004129677, 2.170143584427775, 2.600848989437877, 2.745595052009334," +
                " 1.7134506878068994, 2.201827088699896, 1.4596360837017337, 2.9603358085516382, 1.5877395691882152," +
                " 1.8047048716932887]",
                2.2773915219111456
        );
        rangeUniform_double_double_helper(
                Math.PI,
                FloatingPointUtils.successor(Math.PI),
                "[3.141592653589793, 3.1415926535897936, 3.1415926535897936, 3.1415926535897936, 3.1415926535897936," +
                " 3.141592653589793, 3.141592653589793, 3.1415926535897936, 3.141592653589793, 3.1415926535897936," +
                " 3.141592653589793, 3.141592653589793, 3.141592653589793, 3.141592653589793, 3.141592653589793," +
                " 3.141592653589793, 3.1415926535897936, 3.141592653589793, 3.1415926535897936, 3.1415926535897936]",
                3.1415926535777086
        );
        rangeUniform_double_double_helper(
                0.0,
                1.0,
                "[0.33431911153118704, 0.7164537038091726, 0.629689898170449, 0.06204990789079028," +
                " 0.007029517832079509, 0.10558860563351709, 0.7912897203519785, 0.11928665407964857," +
                " 0.7308179810186062, 0.06376437969929205, 0.782308560677746, 0.4041074043452363," +
                " 0.5365871735809418, 0.41066542209872, 0.9790983240333447, 0.8761628191101757, 0.3189165198381458," +
                " 0.8840415617290818, 0.32173745612338356, 0.49671984798041213]",
                0.4998662259843977
        );
        rangeUniform_double_double_helper(
                -1.0,
                1.0,
                "[0.12482077577322558, 0.3596563026544992, 0.009095229045478867, 0.5264306118429893," +
                " 0.1107566319194642, -0.9508466242350451, 0.762903802982368, -0.20757612175563478," +
                " 0.7130941905430236, -0.6554905856906699, 0.5312619129199516, 0.06678185534415995," +
                " 0.7063760258321635, -0.7038729614478173, 0.745967606782101, 0.5094978232121363," +
                " -0.4959230969075852, 0.4326118565222328, 0.5811492973948575, -0.870886090250355]",
                6.906796837564503E-4
        );
        rangeUniform_double_double_helper(
                1.0,
                1.0,
                "[1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0]",
                1.000000000007918
        );
        rangeUniform_double_double_helper(1.0, -1.0, "[]", 0.0);
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
            @NotNull String topSampleCount,
            double sampleMean,
            double unscaledBitSizeMean,
            double scaleMean
    ) {
        List<BigDecimal> sample = toList(take(DEFAULT_SAMPLE_SIZE, xs));
        aeqit(take(TINY_LIMIT, sample), output);
        aeq(topSampleCount(DEFAULT_TOP_COUNT, sample), topSampleCount);
        aeq(meanOfBigDecimals(sample), sampleMean);
        aeq(meanOfIntegers(toList(map(x -> x.unscaledValue().abs().bitLength(), sample))), unscaledBitSizeMean);
        aeq(meanOfIntegers(toList(map(x -> Math.abs(x.scale()), sample))), scaleMean);
    }

    private static void nextPositiveBigDecimal_helper(
            int unscaledMeanBitSize,
            int meanScaleSize,
            @NotNull String output
    ) {
        aeq(
                P.withScale(unscaledMeanBitSize).withSecondaryScale(meanScaleSize).nextPositiveBigDecimal(),
                output
        );
        P.reset();
    }

    private static void nextPositiveBigDecimal_fail_helper(int unscaledMeanBitSize, int meanScaleSize) {
        try {
            P.withScale(unscaledMeanBitSize).withSecondaryScale(meanScaleSize).nextPositiveBigDecimal();
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testNextPositiveBigDecimal() {
        nextPositiveBigDecimal_helper(2, 1, "1.3");
        nextPositiveBigDecimal_helper(5, 3, "2.1E-7");
        nextPositiveBigDecimal_helper(32, 8, "9.899036265412338E+22");
        nextPositiveBigDecimal_helper(100, 10, "9.4790976865653102300816908025048767680216168E+58");
        nextPositiveBigDecimal_fail_helper(1, 1);
        nextPositiveBigDecimal_fail_helper(2, 0);
    }

    private static void positiveBigDecimals_helper(
            int meanUnscaledBitSize,
            int meanScaleSize,
            @NotNull String output,
            @NotNull String topSampleCount,
            double sampleMean,
            double unscaledBitSizeMean,
            double scaleMean
    ) {
        bigDecimalHelper(
                P.withScale(meanUnscaledBitSize).withSecondaryScale(meanScaleSize).positiveBigDecimals(),
                output,
                topSampleCount,
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
                "[1.3, 47.7, 0.2, 4, 0.3, 2E+2, 1, 0.1, 3E+1, 1, 2, 1, 1, 1, 0.003, 10, 1E+2, 0.00003, 5, 0.19]",
                "{1=250428, 0.1=62680, 1E+1=62588, 3=62216, 2=62123, 0.01=31370, 1E+2=31080, 0.3=15830, 6=15720," +
                " 0.2=15679}",
                1.0009534254522994E16,
                2.0010259999798934,
                0.9987319999976897
        );
        positiveBigDecimals_helper(
                5,
                3,
                "[2.1E-7, 2.07646E+10, 13, 1E+1, 0.000002, 5.3E-12, 3.44E+6, 6E-11, 9881, 192, 1.2E+5, 6, 4701, 46," +
                " 1.4E+3, 2E+5, 1E+2, 0.8, 0.0002, 0.62]",
                "{1=49801, 3=20202, 2=20107, 1E+1=18795, 0.1=18688, 1E+2=14052, 0.01=13960, 1E+3=10567, 0.001=10524," +
                " 4=8101}",
                1.6850709563045524E40,
                5.00820000000873,
                3.0015849999914943
        );
        positiveBigDecimals_helper(
                32,
                8,
                "[9.899036265412338E+22, 2.25462717E+36, 1.65780717136E+16, 1E+17, 2.883801664E+19, 4.45551E+6," +
                " 1.5973E+14, 4.372E+5, 8.040, 10, 6483542254218470125815.323736137, 3E+6, 3.238, 1.05164088156E+30," +
                " 6.261899171E+17, 1E+14, 1.388001050110E+14, 1.0161242E-22, 3.7E-30, 289]",
                "{1=3367, 2=1761, 3=1685, 1E+1=1549, 0.1=1527, 1E+2=1381, 0.01=1363, 0.001=1235, 1E+3=1142," +
                " 0.0001=1094}",
                1.2572425193762146E145,
                32.00852100002276,
                7.997832000016788
        );
        positiveBigDecimals_helper(
                100,
                10,
                "[9.4790976865653102300816908025048767680216168E+58, 4.630900152605E+18, 1.07302372E+20, 1.300E+6," +
                " 110207667890702717891682993862216378208126.1, 1.42361763E+31, 2.7934853437353795793120507739E+30," +
                " 1.121036E-34, 1307879802.6233, 2.378861914519634593E+23, 1.6636E+6," +
                " 1.1780491659396320304092245748596897246965E+49, 1.01342E+35," +
                " 4281825112654693651459071466596876043334654508762035987794871586.861109396215," +
                " 87894315763.404085565781671205346, 7.802322497623E+39, 3.50516300835593753E+38, 75.2585," +
                " 6.57125105137779066251367372137060396099924931317E+50," +
                " 1.5713218602914447431492283897880756550610073314504466517671827791587317575567925917482809548E+125]",
                "{1=923, 0.1=439, 3=438, 2=431, 1E+2=419, 0.01=391, 1E+1=356, 0.001=337, 1E+3=320, 1E+4=317}",
                Double.POSITIVE_INFINITY,
                99.91674399999913,
                10.005905000005157
        );
        positiveBigDecimals_fail_helper(1, 1);
        positiveBigDecimals_fail_helper(2, 0);
    }

    private static void nextNegativeBigDecimal_helper(
            int unscaledMeanBitSize,
            int meanScaleSize,
            @NotNull String output
    ) {
        aeq(
                P.withScale(unscaledMeanBitSize).withSecondaryScale(meanScaleSize).nextNegativeBigDecimal(),
                output
        );
        P.reset();
    }

    private static void nextNegativeBigDecimal_fail_helper(int unscaledMeanBitSize, int meanScaleSize) {
        try {
            P.withScale(unscaledMeanBitSize).withSecondaryScale(meanScaleSize).nextNegativeBigDecimal();
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testNextNegativeBigDecimal() {
        nextNegativeBigDecimal_helper(2, 1, "-1.3");
        nextNegativeBigDecimal_helper(5, 3, "-2.1E-7");
        nextNegativeBigDecimal_helper(32, 8, "-9.899036265412338E+22");
        nextNegativeBigDecimal_helper(100, 10, "-9.4790976865653102300816908025048767680216168E+58");
        nextNegativeBigDecimal_fail_helper(1, 1);
        nextNegativeBigDecimal_fail_helper(2, 0);
    }

    private static void negativeBigDecimals_helper(
            int meanUnscaledBitSize,
            int meanScaleSize,
            @NotNull String output,
            @NotNull String topSampleCount,
            double sampleMean,
            double unscaledBitSizeMean,
            double scaleMean
    ) {
        bigDecimalHelper(
                P.withScale(meanUnscaledBitSize).withSecondaryScale(meanScaleSize).negativeBigDecimals(),
                output,
                topSampleCount,
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
                "[-1.3, -47.7, -0.2, -4, -0.3, -2E+2, -1, -0.1, -3E+1, -1, -2, -1, -1, -1, -0.003, -10, -1E+2," +
                " -0.00003, -5, -0.19]",
                "{-1=250428, -0.1=62680, -1E+1=62588, -3=62216, -2=62123, -0.01=31370, -1E+2=31080, -0.3=15830," +
                " -6=15720, -0.2=15679}",
                -1.0009534254522994E16,
                2.0010259999798934,
                0.9987319999976897
        );
        negativeBigDecimals_helper(
                5,
                3,
                "[-2.1E-7, -2.07646E+10, -13, -1E+1, -0.000002, -5.3E-12, -3.44E+6, -6E-11, -9881, -192, -1.2E+5," +
                " -6, -4701, -46, -1.4E+3, -2E+5, -1E+2, -0.8, -0.0002, -0.62]",
                "{-1=49801, -3=20202, -2=20107, -1E+1=18795, -0.1=18688, -1E+2=14052, -0.01=13960, -1E+3=10567," +
                " -0.001=10524, -4=8101}",
                -1.6850709563045524E40,
                5.00820000000873,
                3.0015849999914943
        );
        negativeBigDecimals_helper(
                32,
                8,
                "[-9.899036265412338E+22, -2.25462717E+36, -1.65780717136E+16, -1E+17, -2.883801664E+19," +
                " -4.45551E+6, -1.5973E+14, -4.372E+5, -8.040, -10, -6483542254218470125815.323736137, -3E+6, " +
                "-3.238, -1.05164088156E+30, -6.261899171E+17, -1E+14, -1.388001050110E+14, -1.0161242E-22," +
                " -3.7E-30, -289]",
                "{-1=3367, -2=1761, -3=1685, -1E+1=1549, -0.1=1527, -1E+2=1381, -0.01=1363, -0.001=1235, -1E+3=1142," +
                " -0.0001=1094}",
                -1.2572425193762146E145,
                32.00852100002276,
                7.997832000016788
        );
        negativeBigDecimals_helper(
                100,
                10,
                "[-9.4790976865653102300816908025048767680216168E+58, -4.630900152605E+18, -1.07302372E+20," +
                " -1.300E+6, -110207667890702717891682993862216378208126.1, -1.42361763E+31," +
                " -2.7934853437353795793120507739E+30, -1.121036E-34, -1307879802.6233, -2.378861914519634593E+23," +
                " -1.6636E+6, -1.1780491659396320304092245748596897246965E+49, -1.01342E+35," +
                " -4281825112654693651459071466596876043334654508762035987794871586.861109396215," +
                " -87894315763.404085565781671205346, -7.802322497623E+39, -3.50516300835593753E+38, -75.2585," +
                " -6.57125105137779066251367372137060396099924931317E+50," +
                " -1.5713218602914447431492283897880756550610073314504466517671827791587317575567925917482809548" +
                "E+125]",
                "{-1=923, -0.1=439, -3=438, -2=431, -1E+2=419, -0.01=391, -1E+1=356, -0.001=337, -1E+3=320," +
                " -1E+4=317}",
                Double.NEGATIVE_INFINITY,
                99.91674399999913,
                10.005905000005157
        );
        negativeBigDecimals_fail_helper(1, 1);
        negativeBigDecimals_fail_helper(2, 0);
    }

    private static void nextNonzeroBigDecimal_helper(
            int unscaledMeanBitSize,
            int meanScaleSize,
            @NotNull String output
    ) {
        aeq(
                P.withScale(unscaledMeanBitSize).withSecondaryScale(meanScaleSize).nextNonzeroBigDecimal(),
                output
        );
        P.reset();
    }

    private static void nextNonzeroBigDecimal_fail_helper(int unscaledMeanBitSize, int meanScaleSize) {
        try {
            P.withScale(unscaledMeanBitSize).withSecondaryScale(meanScaleSize).nextNonzeroBigDecimal();
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testNextNonzeroBigDecimal() {
        nextNonzeroBigDecimal_helper(2, 1, "13");
        nextNonzeroBigDecimal_helper(5, 3, "0.0000021");
        nextNonzeroBigDecimal_helper(32, 8, "-9.899036265412338E+21");
        nextNonzeroBigDecimal_helper(100, 10, "9.4790976865653102300816908025048767680216168E+58");
        nextNonzeroBigDecimal_fail_helper(1, 1);
        nextNonzeroBigDecimal_fail_helper(2, 0);
    }

    private static void nonzeroBigDecimals_helper(
            int meanUnscaledBitSize,
            int meanScaleSize,
            @NotNull String output,
            @NotNull String topSampleCount,
            double sampleMean,
            double unscaledBitSizeMean,
            double scaleMean
    ) {
        bigDecimalHelper(
                P.withScale(meanUnscaledBitSize).withSecondaryScale(meanScaleSize).nonzeroBigDecimals(),
                output,
                topSampleCount,
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
                "[13, 477, 2, -0.04, 3, -0.4, 1, 3, -1, -1E+1, -1, -1, 0.03, -1.0E+2, 4, 3.7, 0.001, -0.7, -5E+2, -1]",
                "{1=125504, -1=124734, 3=31556, 0.1=31392, -3=31366, -0.1=31338, -1E+1=31314, 1E+1=31220, -2=31116," +
                " 2=30955}",
                -3.0002088848452557E17,
                1.99926999997992,
                0.9998329999977443
        );
        nonzeroBigDecimals_helper(
                5,
                3,
                "[0.0000021, 2.07646E+9, -1.3E+3, -2, 4.1E+3, -5E-12, -3.44E+5, -6E-10, -9.881E+7, -1.2E+10, -1E+3," +
                " -6E+4, -3.49E+13, -1, 1E+1, 2E+4, 1E+1, -8, 0.002, 6.2]",
                "{-1=25120, 1=24967, -3=10194, 3=9982, -2=9947, 2=9926, -0.1=9452, 1E+1=9348, -1E+1=9325, 0.1=9264}",
                -3.2241232349714534E39,
                5.007370000008868,
                2.996820999991485
        );
        nonzeroBigDecimals_helper(
                32,
                8,
                "[-9.899036265412338E+21, -2.25462717E+35, -1.65780717136E+16, -1E+16, 2.883801664E+18, -4.45551E+6," +
                " 1.5973E+13, 4.372E+5, -80.40, 10, -64835422542184701258153.23736137, -3E+5, 32.38," +
                " 1.05164088156E+29, 6.261899171E+16, -1E+14, -1.388001050110E+13, 1.0161242E-21, 3.7E-29, 289]",
                "{-1=1735, 1=1665, -2=876, 2=867, 3=825, -3=807, -1E+1=786, 0.1=785, 1E+1=768, -0.1=737}",
                1.2572425193762147E144,
                32.00133300002266,
                8.001691000016947
        );
        nonzeroBigDecimals_helper(
                100,
                10,
                "[9.4790976865653102300816908025048767680216168E+58, 4.630900152605E+17, 1.07302372E+19, 1.300E+5," +
                " 1102076678907027178916829938622163782081261, -1.42361763E+31, -2.7934853437353795793120507739E+29," +
                " 1.121036E-33, 1307879802.6233, -2.378861914519634593E+23, -1.6636E+5," +
                " -1.1780491659396320304092245748596897246965E+49, 1.01342E+34," +
                " 4281825112654693651459071466596876043334654508762035987794871586.861109396215," +
                " -87894315763.404085565781671205346, 7.802322497623E+38, -3.50516300835593753E+37, 752.585," +
                " 6.57125105137779066251367372137060396099924931317E+50," +
                " 1.5713218602914447431492283897880756550610073314504466517671827791587317575567925917482809548E+124]",
                "{-1=456, 1=424, 0.1=228, 3=228, -0.1=225, 1E+1=222, -1E+1=213, -3=209, -2=209, 2=207}",
                Double.NaN,
                99.9163349999983,
                10.004244000004174
        );
        nonzeroBigDecimals_fail_helper(1, 1);
        nonzeroBigDecimals_fail_helper(2, 0);
    }

    private static void nextBigDecimal_helper(
            int unscaledMeanBitSize,
            int meanScaleSize,
            @NotNull String output
    ) {
        aeq(
                P.withScale(unscaledMeanBitSize).withSecondaryScale(meanScaleSize).nextBigDecimal(),
                output
        );
        P.reset();
    }

    private static void nextBigDecimal_fail_helper(int unscaledMeanBitSize, int meanScaleSize) {
        try {
            P.withScale(unscaledMeanBitSize).withSecondaryScale(meanScaleSize).nextBigDecimal();
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testNextBigDecimal() {
        nextBigDecimal_helper(2, 1, "0.0000013");
        nextBigDecimal_helper(5, 3, "0.0000021");
        nextBigDecimal_helper(32, 8, "4.69790E+12");
        nextBigDecimal_helper(100, 10, "6.30008861630388057697674146568609443823746152E+59");
        nextBigDecimal_fail_helper(0, 1);
        nextBigDecimal_fail_helper(2, 0);
    }

    private static void bigDecimals_helper(
            int meanUnscaledBitSize,
            int meanScaleSize,
            @NotNull String output,
            @NotNull String topSampleCount,
            double sampleMean,
            double unscaledBitSizeMean,
            double scaleMean
    ) {
        bigDecimalHelper(
                P.withScale(meanUnscaledBitSize).withSecondaryScale(meanScaleSize).bigDecimals(),
                output,
                topSampleCount,
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
                "[0.0000013, -1.5E+4, 3, -1.2, -0.01, 1, -5, 0, 0, 5E+2, -0.000053, 0.003, -8.9E+3, 0, 0, -0.019, 0," +
                " 0E+3, 0, -1]",
                "{0=166632, 1=55897, -1=55494, 0.0=41797, 0E+1=41557, 0E+2=20993, 0.00=20867, -3=18540, 3=18457," +
                " 2=18432}",
                -6.179780535515134E14,
                2.0001599999890485,
                1.0009719999977271
        );
        bigDecimals_helper(
                5,
                3,
                "[0.0000021, 2.304798E+10, -1.3E+3, -1, 2.5E+3, -5E-12, -8.56E+5, -2E-10, -1.04780E+9, -1.2E+10," +
                " 0E+4, -6E+4, -6.05E+13, 0, 2E+1, 2E+4, 0E+2, -8, 0.001, 6.2]",
                "{0=41643, -1=17502, 1=17490, 0.0=15636, 0E+1=15578, 0E+2=11658, 0.00=11597, 0.000=8873, 0E+3=8747," +
                " 2=7280}",
                -1.606745484001922E40,
                5.010166000005216,
                2.995944999991484
        );
        bigDecimals_helper(
                32,
                8,
                "[4.69790E+12, 1.65298870766E+28, -2.8E+18, -1.128758E+14, -7.884E+4, 9.4E+10, 1.48E+4, -0.47," +
                " -1E+6, -97124357730709467551.3, -6.4580E+23, -2.5406601196076549E+17, 1.2571E-24, 6534.8892," +
                " -0.5007894, 5.0E+10, -1.466398006247E+14, -1.3562E+6, -25246, 1.674206E+27]",
                "{0=3373, -1=1609, 1=1562, 0.0=1484, 0E+1=1409, 0E+2=1376, 0.00=1326, 0.000=1235, 0E+3=1229," +
                " 0.0000=999}",
                -1.0670887202897772E136,
                32.02927300002175,
                8.000781000016627
        );
        bigDecimals_helper(
                100,
                10,
                "[6.30008861630388057697674146568609443823746152E+59, 4.630900152605E+17, 4.0193508E+18, 7.88E+4," +
                " 1102076678907027178916829938622163782081261, -7.5252899E+30, -1.8031333123070753593927513947E+29," +
                " 5.96748E-34, 868075151.5129, -2.378861914519634593E+23, -8.444E+4," +
                " -3.3558563142336381965748220624230062780149E+49, 2.32414E+34," +
                " 2472573718321628097965774825836127483127310998361402174678346836.737466745591," +
                " -87894315763.404085565781671205346, 7.802322497623E+38, -3.50516300835593753E+37, 1801.161," +
                " 2.91749695805053336700446163957989641185941795573E+50," +
                " 2.9993448730505813375871128575092481295300716927908377277152633911765951809249090756140387330E+124]",
                "{0=909, 0.0=439, 1=429, -1=412, 0E+2=402, 0.00=394, 0E+1=355, 0.000=339, 0E+3=325, 0E+4=313}",
                Double.NaN,
                99.91854699999466,
                10.00440500000418
        );
        bigDecimals_fail_helper(0, 1);
        bigDecimals_fail_helper(2, 0);
    }

    private static void nextPositiveCanonicalBigDecimal_helper(
            int unscaledMeanBitSize,
            int meanScaleSize,
            @NotNull String output
    ) {
        aeq(
                P.withScale(unscaledMeanBitSize).withSecondaryScale(meanScaleSize).nextPositiveCanonicalBigDecimal(),
                output
        );
        P.reset();
    }

    private static void nextPositiveCanonicalBigDecimal_fail_helper(int unscaledMeanBitSize, int meanScaleSize) {
        try {
            P.withScale(unscaledMeanBitSize).withSecondaryScale(meanScaleSize).nextPositiveCanonicalBigDecimal();
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testNextPositiveCanonicalBigDecimal() {
        nextPositiveCanonicalBigDecimal_helper(2, 1, "0.005");
        nextPositiveCanonicalBigDecimal_helper(5, 3, "0.000013");
        nextPositiveCanonicalBigDecimal_helper(32, 8, "4.04936997654063E-17");
        nextPositiveCanonicalBigDecimal_helper(100, 10, "0.000572989721722831798340552401503927");
        nextPositiveCanonicalBigDecimal_fail_helper(1, 1);
        nextPositiveCanonicalBigDecimal_fail_helper(2, 0);
    }

    private static void positiveCanonicalBigDecimals_helper(
            int meanUnscaledBitSize,
            int meanScaleSize,
            @NotNull String output,
            @NotNull String topSampleCount,
            double sampleMean,
            double unscaledBitSizeMean,
            double scaleMean
    ) {
        bigDecimalHelper(
                P.withScale(meanUnscaledBitSize).withSecondaryScale(meanScaleSize).positiveCanonicalBigDecimals(),
                output,
                topSampleCount,
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
                "[0.005, 7E-8, 0.1, 12, 7, 0.6, 0.01, 0.2, 0.5, 0.1, 0.1, 1, 0.1, 1, 1, 1, 2.5, 0.001, 0.1, 0.1]",
                "{1=249991, 0.1=124728, 2=62606, 3=62591, 0.01=62142, 0.001=31606, 0.3=31288, 0.2=31004, 5=15796," +
                " 7=15770}",
                6.995086292023604,
                1.9993349999798273,
                1.0016879999976924
        );
        positiveCanonicalBigDecimals_helper(
                5,
                3,
                "[0.000013, 0.25387, 0.00029, 2, 0.1, 1, 0.000117, 3.44E-11, 0.0001, 2, 1.8073E-7, 320, 28, 0.0014," +
                " 8797, 46, 30, 0.04, 0.00002, 0.01]",
                "{1=49969, 0.1=37361, 0.01=28100, 0.001=21268, 2=20017, 3=19806, 0.0001=15587, 0.3=15037, 0.2=14924," +
                " 0.00001=11705}",
                5.0163406598608475E10,
                5.010096000008702,
                2.9996349999914838
        );
        positiveCanonicalBigDecimals_helper(
                32,
                8,
                "[4.04936997654063E-17, 3.03219670608E-16, 0.00003, 5.254848612E-8, 0.00969839, 2416.5," +
                " 0.0000012564, 121.36, 0.018, 16911639184143047868995522284006, 5E-9, 0.005286, 242603041.628," +
                " 1.4851833763E-9, 3E-8, 0.02487512677886, 235204.68, 6.9E-27, 8.01E-29, 39]",
                "{1=3543, 0.1=3098, 0.01=2697, 0.001=2426, 0.0001=2127, 0.00001=2023, 2=1696, 3=1678, 0.000001=1641," +
                " 1E-7=1589}",
                2.1875960862631944E131,
                32.00952900002345,
                7.992489000016789
        );
        positiveCanonicalBigDecimals_helper(
                100,
                10,
                "[0.000572989721722831798340552401503927, 0.0004630900152605, 187.100459, 1.391E-8," +
                " 11020766789070271789168299386221637820812.61, 41079721.9, 604019.34935913508416666426308," +
                " 321818.8, 3.0670984070649E-27, 699054793294702.2497, 0.64652," +
                " 335585631423363819657482206242300627801.49, 0.000232414," +
                " 7900327901320824758445664748118373163749341529.563303614027921087108394697463," +
                " 250153592592617448957.359681493474, 7.802322497623E-9, 6.38746676987305497E-10, 1.801161E-15," +
                " 88208944088305682602813392107305565097650624.6571," +
                " 15713218602914447431492283897880756550610073314504466517671827791587317575567925917482809.548]",
                "{1=948, 0.01=768, 0.1=753, 0.001=692, 0.0001=623, 0.00001=515, 0.000001=515, 1E-7=479, 2=472, 3=456}",
                Double.POSITIVE_INFINITY,
                99.93904899999788,
                10.005566000005233
        );
        positiveCanonicalBigDecimals_fail_helper(1, 1);
        positiveCanonicalBigDecimals_fail_helper(2, 0);
    }

    private static void nextNegativeCanonicalBigDecimal_helper(
            int unscaledMeanBitSize,
            int meanScaleSize,
            @NotNull String output
    ) {
        aeq(
                P.withScale(unscaledMeanBitSize).withSecondaryScale(meanScaleSize).nextNegativeCanonicalBigDecimal(),
                output
        );
        P.reset();
    }

    private static void nextNegativeCanonicalBigDecimal_fail_helper(int unscaledMeanBitSize, int meanScaleSize) {
        try {
            P.withScale(unscaledMeanBitSize).withSecondaryScale(meanScaleSize).nextNegativeCanonicalBigDecimal();
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testNextNegativeCanonicalBigDecimal() {
        nextNegativeCanonicalBigDecimal_helper(2, 1, "-0.005");
        nextNegativeCanonicalBigDecimal_helper(5, 3, "-0.000013");
        nextNegativeCanonicalBigDecimal_helper(32, 8, "-4.04936997654063E-17");
        nextNegativeCanonicalBigDecimal_helper(100, 10, "-0.000572989721722831798340552401503927");
        nextNegativeCanonicalBigDecimal_fail_helper(1, 1);
        nextNegativeCanonicalBigDecimal_fail_helper(2, 0);
    }

    private static void negativeCanonicalBigDecimals_helper(
            int meanUnscaledBitSize,
            int meanScaleSize,
            @NotNull String output,
            @NotNull String topSampleCount,
            double sampleMean,
            double unscaledBitSizeMean,
            double scaleMean
    ) {
        bigDecimalHelper(
                P.withScale(meanUnscaledBitSize).withSecondaryScale(meanScaleSize).negativeCanonicalBigDecimals(),
                output,
                topSampleCount,
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
                "[-0.005, -7E-8, -0.1, -12, -7, -0.6, -0.01, -0.2, -0.5, -0.1, -0.1, -1, -0.1, -1, -1, -1, -2.5," +
                " -0.001, -0.1, -0.1]",
                "{-1=249991, -0.1=124728, -2=62606, -3=62591, -0.01=62142, -0.001=31606, -0.3=31288, -0.2=31004," +
                " -5=15796, -7=15770}",
                -6.995086292023604,
                1.9993349999798273,
                1.0016879999976924
        );
        negativeCanonicalBigDecimals_helper(
                5,
                3,
                "[-0.000013, -0.25387, -0.00029, -2, -0.1, -1, -0.000117, -3.44E-11, -0.0001, -2, -1.8073E-7, -320," +
                " -28, -0.0014, -8797, -46, -30, -0.04, -0.00002, -0.01]",
                "{-1=49969, -0.1=37361, -0.01=28100, -0.001=21268, -2=20017, -3=19806, -0.0001=15587, -0.3=15037," +
                " -0.2=14924, -0.00001=11705}",
                -5.0163406598608475E10,
                5.010096000008702,
                2.9996349999914838
        );
        negativeCanonicalBigDecimals_helper(
                32,
                8,
                "[-4.04936997654063E-17, -3.03219670608E-16, -0.00003, -5.254848612E-8, -0.00969839, -2416.5," +
                " -0.0000012564, -121.36, -0.018, -16911639184143047868995522284006, -5E-9, -0.005286," +
                " -242603041.628, -1.4851833763E-9, -3E-8, -0.02487512677886, -235204.68, -6.9E-27, -8.01E-29, -39]",
                "{-1=3543, -0.1=3098, -0.01=2697, -0.001=2426, -0.0001=2127, -0.00001=2023, -2=1696, -3=1678," +
                " -0.000001=1641, -1E-7=1589}",
                -2.1875960862631944E131,
                32.00952900002345,
                7.992489000016789
        );
        negativeCanonicalBigDecimals_helper(
                100,
                10,
                "[-0.000572989721722831798340552401503927, -0.0004630900152605, -187.100459, -1.391E-8," +
                " -11020766789070271789168299386221637820812.61, -41079721.9, -604019.34935913508416666426308," +
                " -321818.8, -3.0670984070649E-27, -699054793294702.2497, -0.64652," +
                " -335585631423363819657482206242300627801.49, -0.000232414," +
                " -7900327901320824758445664748118373163749341529.563303614027921087108394697463," +
                " -250153592592617448957.359681493474, -7.802322497623E-9, -6.38746676987305497E-10, -1.801161E-15," +
                " -88208944088305682602813392107305565097650624.6571," +
                " -15713218602914447431492283897880756550610073314504466517671827791587317575567925917482809.548]",
                "{-1=948, -0.01=768, -0.1=753, -0.001=692, -0.0001=623, -0.00001=515, -0.000001=515, -1E-7=479," +
                " -2=472, -3=456}",
                Double.NEGATIVE_INFINITY,
                99.93904899999788,
                10.005566000005233
        );
        negativeCanonicalBigDecimals_fail_helper(1, 1);
        negativeCanonicalBigDecimals_fail_helper(2, 0);
    }

    private static void nextNonzeroCanonicalBigDecimal_helper(
            int unscaledMeanBitSize,
            int meanScaleSize,
            @NotNull String output
    ) {
        aeq(
                P.withScale(unscaledMeanBitSize).withSecondaryScale(meanScaleSize).nextNonzeroCanonicalBigDecimal(),
                output
        );
        P.reset();
    }

    private static void nextNonzeroCanonicalBigDecimal_fail_helper(int unscaledMeanBitSize, int meanScaleSize) {
        try {
            P.withScale(unscaledMeanBitSize).withSecondaryScale(meanScaleSize).nextNonzeroCanonicalBigDecimal();
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testNextNonzeroCanonicalBigDecimal() {
        nextNonzeroCanonicalBigDecimal_helper(2, 1, "0.05");
        nextNonzeroCanonicalBigDecimal_helper(5, 3, "0.00013");
        nextNonzeroCanonicalBigDecimal_helper(32, 8, "4.04936997654063E-17");
        nextNonzeroCanonicalBigDecimal_helper(100, 10, "0.00572989721722831798340552401503927");
        nextNonzeroCanonicalBigDecimal_fail_helper(1, 1);
        nextNonzeroCanonicalBigDecimal_fail_helper(2, 0);
    }

    private static void nonzeroCanonicalBigDecimals_helper(
            int meanUnscaledBitSize,
            int meanScaleSize,
            @NotNull String output,
            @NotNull String topSampleCount,
            double sampleMean,
            double unscaledBitSizeMean,
            double scaleMean
    ) {
        bigDecimalHelper(
                P.withScale(meanUnscaledBitSize).withSecondaryScale(meanScaleSize).nonzeroCanonicalBigDecimals(),
                output,
                topSampleCount,
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
                "[0.05, 7E-7, 1, -0.001, 0.7, 1, 1, 2, 5, 1, 1, -1, -1, -1, -1, 25, 0.01, 1, 1, -0.000009]",
                "{1=124850, -1=124579, -0.1=62675, 0.1=62491, 3=31558, 0.01=31399, 2=31278, -2=31169, -3=31120," +
                " -0.01=31065}",
                -4.360432485051689,
                2.0000579999799557,
                1.0015299999976932
        );
        nonzeroCanonicalBigDecimals_helper(
                5,
                3,
                "[0.00013, 2.5387, 0.0029, -0.01, -2, 0.00117, -3.44E-10, -0.001, -1, -0.0000018073, -0.0028, -1E-9," +
                " -0.014, -0.0605, -1E-11, -2, 0.4, 0.0002, 0.1, 0.01]",
                "{-1=24836, 1=24785, -0.1=18867, 0.1=18657, 0.01=14289, -0.01=13967, -0.001=10501, 0.001=10362," +
                " -2=10068, 2=10010}",
                7.150490952200898E13,
                5.008832000008888,
                2.9996659999914486
        );
        nonzeroCanonicalBigDecimals_helper(
                32,
                8,
                "[4.04936997654063E-17, 3.03219670608E-15, -0.00003, -5.254848612E-7, -0.0969839, -2416.5," +
                " 0.000012564, 121.36, -0.18, 16911639184143047868995522284006, -5E-8, -0.05286, 2426030416.28," +
                " 1.4851833763E-8, 3E-7, -0.02487512677886, -2352046.8, 6.9E-27, 8.01E-28, 39]",
                "{1=1783, -1=1782, 0.1=1565, -0.1=1417, 0.01=1405, -0.01=1326, 0.001=1184, -0.001=1175," +
                " -0.0001=1128, 0.0001=1113}",
                -2.1875960862631944E131,
                32.006803000023154,
                7.998565000016982
        );
        nonzeroCanonicalBigDecimals_helper(
                100,
                10,
                "[0.00572989721722831798340552401503927, 0.004630900152605, 1871.00459, -1.391E-8," +
                " 110207667890702717891682993862216378208126.1, 410797219, -604019.34935913508416666426308," +
                " -321818.8, 3.0670984070649E-26, 699054793294702.2497, -0.64652," +
                " -3355856314233638196574822062423006278014.9, -0.000232414," +
                " 79003279013208247584456647481183731637493415295.63303614027921087108394697463," +
                " 250153592592617448957.359681493474, -7.802322497623E-9, 6.38746676987305497E-9, -1.801161E-14," +
                " 882089440883056826028133921073055650976506246.571," +
                " 157132186029144474314922838978807565506100733145044665176718277915873175755679259174828095.48]",
                "{1=453, -1=424, -0.1=423, 0.1=407, -0.01=405, 0.001=358, 0.01=352, -0.001=341, 0.0001=305," +
                " 0.00001=288}",
                Double.NaN,
                99.93122599999683,
                10.008304000004127
        );
        nonzeroCanonicalBigDecimals_fail_helper(1, 1);
        nonzeroCanonicalBigDecimals_fail_helper(2, 0);
    }

    private static void nextCanonicalBigDecimal_helper(
            int unscaledMeanBitSize,
            int meanScaleSize,
            @NotNull String output
    ) {
        aeq(
                P.withScale(unscaledMeanBitSize).withSecondaryScale(meanScaleSize).nextCanonicalBigDecimal(),
                output
        );
        P.reset();
    }

    private static void nextCanonicalBigDecimal_fail_helper(int unscaledMeanBitSize, int meanScaleSize) {
        try {
            P.withScale(unscaledMeanBitSize).withSecondaryScale(meanScaleSize).nextCanonicalBigDecimal();
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testNextCanonicalBigDecimal() {
        nextCanonicalBigDecimal_helper(2, 1, "0.005");
        nextCanonicalBigDecimal_helper(5, 3, "0.000013");
        nextCanonicalBigDecimal_helper(32, 8, "4.04936997654063E-17");
        nextCanonicalBigDecimal_helper(100, 10, "0.000572989721722831798340552401503927");
        nextCanonicalBigDecimal_fail_helper(1, 1);
        nextCanonicalBigDecimal_fail_helper(2, 0);
    }

    private static void canonicalBigDecimals_helper(
            int meanUnscaledBitSize,
            int meanScaleSize,
            @NotNull String output,
            @NotNull String topSampleCount,
            double sampleMean,
            double unscaledBitSizeMean,
            double scaleMean
    ) {
        bigDecimalHelper(
                P.withScale(meanUnscaledBitSize).withSecondaryScale(meanScaleSize).canonicalBigDecimals(),
                output,
                topSampleCount,
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
                "[0.005, 7E-7, 0, 6, 0.7, -2, 0.2, 6, -13, -1, 0, 0, 2.5, 0.01, -5, -0.000009, -0.00015, -0.9," +
                " -0.01, 0]",
                "{0=166144, -0.1=62544, 0.1=62440, 1=55648, -1=55389, 0.01=31366, -0.01=31309, 3=18600, 2=18525," +
                " -2=18445}",
                -763.4558908243097,
                2.0009919999845023,
                1.0021219999976965
        );
        canonicalBigDecimals_helper(
                5,
                3,
                "[0.000013, 2.5387, -0.0029, -0.01, 1, -0.00117, -3.44E-10, -0.001, 0, -1.8073E-7, -0.0028, -1E-9," +
                " -0.014, -0.0605, -1E-11, 2, 0.4, 0.0002, 0.1, -0.01]",
                "{0=41405, 0.1=18877, -0.1=18614, 1=17329, -1=17242, -0.01=14144, 0.01=14105, 0.001=10507," +
                " -0.001=10435, -0.0001=7929}",
                1.0252835941140206E14,
                5.008561000007895,
                3.000018999991451
        );
        canonicalBigDecimals_helper(
                32,
                8,
                "[4.04936997654063E-17, -3.03219670608E-15, -0.00003, -5.254848612E-7, -0.0969839, 2416.5," +
                " 0.000012564, -121.36, 0.18, -97522628338787590093079, 24260304162.8, 1.4851833763E-8, -3E-7," +
                " -0.02487512677886, 2352046.8, 6.9E-27, 8.01E-28, -2910742, 0.0003541, 1E-8]",
                "{0=3437, 1=1624, -1=1604, -0.1=1524, 0.1=1473, 0.01=1378, -0.01=1336, -0.001=1203, 0.001=1162," +
                " -0.0001=1119}",
                -6.239390326218754E113,
                32.01280800002317,
                8.001077000016947
        );
        canonicalBigDecimals_helper(
                100,
                10,
                "[0.000572989721722831798340552401503927, 0.004630900152605, -1871.00459, 1.391E-8," +
                " 110207667890702717891682993862216378208126.1, -142361763, -604019.34935913508416666426308," +
                " 321818.8, 3.0670984070649E-26, -699054793294702.2497, -0.64652," +
                " -3355856314233638196574822062423006278014.9, 0.000232414," +
                " 79003279013208247584456647481183731637493415295.63303614027921087108394697463," +
                " -250153592592617448957.359681493474, 7.802322497623E-9, -6.38746676987305497E-9, 1.801161E-14," +
                " 882089440883056826028133921073055650976506246.571," +
                " 157132186029144474314922838978807565506100733145044665176718277915873175755679259174828095.48]",
                "{0=872, -1=492, 1=469, 0.1=423, -0.1=413, -0.01=397, 0.01=357, -0.001=352, 0.001=344, -0.0001=298}",
                Double.NaN,
                99.93086299999682,
                10.008105000004125
        );
        canonicalBigDecimals_fail_helper(1, 1);
        canonicalBigDecimals_fail_helper(2, 0);
    }

    private static void nextFromRangeUp_BigDecimal_helper(
            int scale,
            int secondaryScale,
            @NotNull String a,
            @NotNull String output
    ) {
        aeq(
                P.withScale(scale).withSecondaryScale(secondaryScale).nextFromRangeUp(Readers.readBigDecimal(a).get()),
                output
        );
        P.reset();
    }

    private static void nextFromRangeUp_BigDecimal_fail_helper(
            int scale,
            int secondaryScale,
            @NotNull String a
    ) {
        try {
            P.withScale(scale).withSecondaryScale(secondaryScale).nextFromRangeUp(Readers.readBigDecimal(a).get());
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testNextFromRangeUp_BigDecimal() {
        nextFromRangeUp_BigDecimal_helper(2, 1, "0", "0.0050000000");
        nextFromRangeUp_BigDecimal_helper(5, 3, "0", "0.0000130000");
        nextFromRangeUp_BigDecimal_helper(32, 8, "0", "4.0493699765406300000000000000000000000000E-17");
        nextFromRangeUp_BigDecimal_helper(2, 1, "0.0", "0.01300");
        nextFromRangeUp_BigDecimal_helper(5, 3, "0.0", "0.000021000000000");
        nextFromRangeUp_BigDecimal_helper(32, 8, "0.0", "58756.389791438000000000");
        nextFromRangeUp_BigDecimal_helper(2, 1, "1", "1.01300");
        nextFromRangeUp_BigDecimal_helper(5, 3, "1", "1.000021000000000");
        nextFromRangeUp_BigDecimal_helper(32, 8, "1", "58757.389791438000000000");
        nextFromRangeUp_BigDecimal_helper(2, 1, "1.0", "1.01300");
        nextFromRangeUp_BigDecimal_helper(5, 3, "1.0", "1.000021000000000");
        nextFromRangeUp_BigDecimal_helper(32, 8, "1.0", "58757.389791438000000000");
        nextFromRangeUp_BigDecimal_helper(2, 1, "-1.0", "-0.98700");
        nextFromRangeUp_BigDecimal_helper(5, 3, "-1.0", "-0.999979000000000");
        nextFromRangeUp_BigDecimal_helper(32, 8, "-1.0", "58755.389791438000000000");
        nextFromRangeUp_BigDecimal_helper(2, 1, "9", "9.01300");
        nextFromRangeUp_BigDecimal_helper(5, 3, "9", "9.000021000000000");
        nextFromRangeUp_BigDecimal_helper(32, 8, "9", "58765.389791438000000000");
        nextFromRangeUp_BigDecimal_helper(2, 1, "-9", "-8.98700");
        nextFromRangeUp_BigDecimal_helper(5, 3, "-9", "-8.999979000000000");
        nextFromRangeUp_BigDecimal_helper(32, 8, "-9", "58747.389791438000000000");
        nextFromRangeUp_BigDecimal_helper(2, 1, "10", "10.01300");
        nextFromRangeUp_BigDecimal_helper(5, 3, "10", "10.000021000000000");
        nextFromRangeUp_BigDecimal_helper(32, 8, "10", "58766.389791438000000000");
        nextFromRangeUp_BigDecimal_helper(2, 1, "-10", "-9.98700");
        nextFromRangeUp_BigDecimal_helper(5, 3, "-10", "-9.999979000000000");
        nextFromRangeUp_BigDecimal_helper(32, 8, "-10", "58746.389791438000000000");
        nextFromRangeUp_BigDecimal_helper(2, 1, "101", "101.01300");
        nextFromRangeUp_BigDecimal_helper(5, 3, "101", "101.000021000000000");
        nextFromRangeUp_BigDecimal_helper(32, 8, "101", "58857.389791438000000000");
        nextFromRangeUp_BigDecimal_helper(2, 1, "-101", "-100.98700");
        nextFromRangeUp_BigDecimal_helper(5, 3, "-101", "-100.999979000000000");
        nextFromRangeUp_BigDecimal_helper(32, 8, "-101", "58655.389791438000000000");
        nextFromRangeUp_BigDecimal_helper(2, 1, "1234567", "1234567.01300");
        nextFromRangeUp_BigDecimal_helper(5, 3, "1234567", "1234567.000021000000000");
        nextFromRangeUp_BigDecimal_helper(32, 8, "1234567", "1293323.389791438000000000");
        nextFromRangeUp_BigDecimal_helper(2, 1, "-1234567", "-1234566.98700");
        nextFromRangeUp_BigDecimal_helper(5, 3, "-1234567", "-1234566.999979000000000");
        nextFromRangeUp_BigDecimal_helper(32, 8, "-1234567", "-1175810.610208562000000000");
        nextFromRangeUp_BigDecimal_helper(2, 1, "0.09", "0.10300");
        nextFromRangeUp_BigDecimal_helper(5, 3, "0.09", "0.090021000000000");
        nextFromRangeUp_BigDecimal_helper(32, 8, "0.09", "58756.479791438000000000");
        nextFromRangeUp_BigDecimal_helper(2, 1, "-0.09", "-0.07700");
        nextFromRangeUp_BigDecimal_helper(5, 3, "-0.09", "-0.089979000000000");
        nextFromRangeUp_BigDecimal_helper(32, 8, "-0.09", "58756.299791438000000000");
        nextFromRangeUp_BigDecimal_helper(2, 1, "1E-12", "0.01300000000100");
        nextFromRangeUp_BigDecimal_helper(5, 3, "1E-12", "0.000021000001000000000");
        nextFromRangeUp_BigDecimal_helper(32, 8, "1E-12", "58756.389791438001000000000");
        nextFromRangeUp_BigDecimal_helper(2, 1, "1E+12", "1000000000000.01300");
        nextFromRangeUp_BigDecimal_helper(5, 3, "1E+12", "1000000000000.000021000000000");
        nextFromRangeUp_BigDecimal_helper(32, 8, "1E+12", "1000000058756.389791438000000000");
        nextFromRangeUp_BigDecimal_fail_helper(1, 1, "0");
        nextFromRangeUp_BigDecimal_fail_helper(2, 0, "0");
    }

    private static void rangeUp_BigDecimal_helper(
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
                P.withScale(scale).withSecondaryScale(secondaryScale).rangeUp(Readers.readBigDecimal(a).get()),
                output,
                topSampleCount,
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
                "[0.0050000000, 0.04, 0.0010, 0.060, 1, 0.01, 0, 3.00, 0, 0.010, 0E+3, 0.0, 0E+3, 1.00, 1.0, 0E+1," +
                " 0, 0.010, 0, 0.02]",
                "{0=142898, 0.1=53939, 1=47402, 0.0=35927, 0E+1=35743, 0.10=26605, 0.01=26502, 1.0=24115," +
                " 0E+2=17760, 0.00=17699}",
                4494.6230398148555,
                3.9558340000009817,
                1.8595289999882512
        );
        rangeUp_BigDecimal_helper(
                5,
                3,
                "0",
                "[0.0000130000, 0.0090030000, 0, 0E-11, 2.0, 2.0000, 0.1, 1.000, 0.00140, 0.000800000000," +
                " 4.56500000000000000, 2, 0.9, 2.00000000000000000E-13, 0.000100000, 0.30000, 12.6000, 0.00060," +
                " 0.0100, 0.25000000]",
                "{0=20179, 0.1=8947, 1=8287, 0E+1=7546, 0.0=7379, 0.01=6731, 0.10=6717, 1.0=6191, 0.00=5575," +
                " 0E+2=5513}",
                1.3254633226393647E14,
                13.827922999990745,
                5.872869000007163
        );
        rangeUp_BigDecimal_helper(
                32,
                8,
                "0",
                "[4.0493699765406300000000000000000000000000E-17, 2416.5000000000, 0.18," +
                " 180547618411867370588.2748088000000000, 76448.5400, 0.00000148518337630000000," +
                " 2352046.80000000000000000000000000000, 22222723878.730960000, 1.60161406000000000000000000000," +
                " 4.16666200000E-8, 109590.1908685744217951578289, 0.0000050000000000000000, 784415066.00," +
                " 284001.000000000000000000, 1.70605244111048790E-14, 0.00075000, 2.900000000E-18," +
                " 0.00142577220000000000, 8.5730000000000000000000000000000000000, 13908417840098258750.12656]",
                "{0=761, 0.0=367, 1=362, 0E+1=333, 1.0=331, 0.1=329, 0.10=317, 0.01=313, 1.00=286, 0E+2=279}",
                4.18021886093211E113,
                58.30837600001352,
                15.959809999995937
        );
        rangeUp_BigDecimal_helper(
                2,
                1,
                "0.0",
                "[0.01300, 1.00E-9, 2.21, 0.1000, 6.00, 0.0300, 0, 0E+1, 0.300, 0E+1, 5.0, 31, 1, 0, 0.00060000," +
                " 0.1, 0.1000000, 0.300000, 0.01, 2]",
                "{0=142481, 0.1=53528, 1=47698, 0E+1=35920, 0.0=35916, 0.01=26810, 0.10=26679, 1.0=23645," +
                " 0E+2=17761, 0.00=17592}",
                15176.838793123277,
                3.953823000000856,
                1.858848999988317
        );
        rangeUp_BigDecimal_helper(
                5,
                3,
                "0.0",
                "[0.000021000000000, 0.000013000000, 76.574000000000, 0.2000000, 0.00000250, 0.00003390000, 0E+1," +
                " 2.000000000000, 0.000060, 62.00000, 0, 0.01, 9, 0.0020, 0.0020000, 0.30, 0.012000, 54644.00, 0E+5," +
                " 0]",
                "{0=20086, 0.1=8998, 1=8268, 0E+1=7502, 0.0=7496, 0.01=6750, 0.10=6627, 1.0=6229, 0E+2=5615," +
                " 0.00=5504}",
                2.3187079784448434E14,
                13.856063999991001,
                5.882033000007064
        );
        rangeUp_BigDecimal_helper(
                32,
                8,
                "0.0",
                "[58756.389791438000000000, 16345806.8846900000000000000000000000000000, 1.33660000000000000E-13," +
                " 6.000000E-10, 7.781000, 201836.0, 0.004370000000000, 1353.39800, 932982052377.20000000," +
                " 2.12266986803635465200000000000E-30, 23.520468, 272169139986910.73520000," +
                " 2.5194755089850400000000000000000000E-7, 8.30E-15, 7.1200E-19, 502509200784.40000," +
                " 0.010209340000000, 15.00, 0.00001000000, 0.05000000]",
                "{0=682, 0.1=348, 0E+1=332, 1=329, 0.0=326, 1.0=325, 0.10=319, 0.00=300, 0E+2=300, 0.100=298}",
                1.3351838891443716E131,
                58.282749000012764,
                15.963111999996999
        );
        rangeUp_BigDecimal_helper(
                2,
                1,
                "1",
                "[1.01300, 1.00000000100, 3.21, 1.1000, 7.00, 1.0300, 1, 1.10, 7.0, 1.0100, 1.0, 6, 32, 2, 1, 1," +
                " 1.10000, 1.0001, 2.5, 2.0]",
                "{1=143281, 1.0=71426, 1.1=53586, 2=47215, 1.00=35657, 1.10=26715, 1.01=26478, 2.0=23935," +
                " 1.000=17786, 3=15972}",
                17081.149420358004,
                7.613145999998498,
                1.8572279999882735
        );
        rangeUp_BigDecimal_helper(
                5,
                3,
                "1",
                "[1.000021000000000, 1.000013000000, 77.574000000000, 1.2000000, 1.00000250, 1.00003390000, 1.0, 3," +
                " 1.00000600000, 1.0062, 1.00001, 2.000000000, 10.000, 2, 1.0002, 1.300000000000, 2.2, 2.493000, 7," +
                " 6.0000]",
                "{1=19722, 1.0=15111, 1.00=11254, 1.1=9026, 2=8481, 1.000=8455, 1.10=6731, 1.01=6680, 1.0000=6314," +
                " 2.0=6282}",
                3.600790347058631E14,
                21.833742999973637,
                5.877492000007097
        );
        rangeUp_BigDecimal_helper(
                32,
                8,
                "1",
                "[58757.389791438000000000, 16345807.8846900000000000000000000000000000," +
                " 1.000000000000133660000000000000, 1.0000000006000000, 8.781000, 201837.0, 1.004370000000000," +
                " 1354.39800, 932982052378.20000000, 1.00000000000000000000000000000212266986803635465200000000000," +
                " 24.520468, 272169139986911.73520000, 1.00000025194755089850400000000000000000000," +
                " 1.00000000000000830, 1.00000000000000000071200, 502509200785.40000, 1.010209340000000, 16.00," +
                " 1.00001000000, 1.05000000]",
                "{1=712, 1.0=667, 1.00=596, 1.000=555, 1.0000=443, 1.00000=426, 1.000000=399, 1.1=351, 2=346," +
                " 1.10=337}",
                1.3351838891443716E131,
                71.03443700002789,
                15.957633999996364
        );
        rangeUp_BigDecimal_helper(
                2,
                1,
                "1.0",
                "[1.01300, 1.00000000100, 3.21, 1.1000, 7.00, 1.0300, 1, 1.10, 7.0, 1.0100, 1.0, 6, 32, 2, 1, 1," +
                " 1.10000, 1.0001, 2.5, 2.0]",
                "{1=143281, 1.0=71426, 1.1=53586, 2=47215, 1.00=35657, 1.10=26715, 1.01=26478, 2.0=23935," +
                " 1.000=17786, 3=15972}",
                17081.149420358004,
                7.613145999998498,
                1.8572279999882735
        );
        rangeUp_BigDecimal_helper(
                5,
                3,
                "1.0",
                "[1.000021000000000, 1.000013000000, 77.574000000000, 1.2000000, 1.00000250, 1.00003390000, 1.0, 3," +
                " 1.00000600000, 1.0062, 1.00001, 2.000000000, 10.000, 2, 1.0002, 1.300000000000, 2.2, 2.493000, 7," +
                " 6.0000]",
                "{1=19722, 1.0=15111, 1.00=11254, 1.1=9026, 2=8481, 1.000=8455, 1.10=6731, 1.01=6680, 1.0000=6314," +
                " 2.0=6282}",
                3.600790347058631E14,
                21.833742999973637,
                5.877492000007097
        );
        rangeUp_BigDecimal_helper(
                32,
                8,
                "1.0",
                "[58757.389791438000000000, 16345807.8846900000000000000000000000000000," +
                " 1.000000000000133660000000000000, 1.0000000006000000, 8.781000, 201837.0, 1.004370000000000," +
                " 1354.39800, 932982052378.20000000, 1.00000000000000000000000000000212266986803635465200000000000," +
                " 24.520468, 272169139986911.73520000, 1.00000025194755089850400000000000000000000," +
                " 1.00000000000000830, 1.00000000000000000071200, 502509200785.40000, 1.010209340000000, 16.00," +
                " 1.00001000000, 1.05000000]",
                "{1=712, 1.0=667, 1.00=596, 1.000=555, 1.0000=443, 1.00000=426, 1.000000=399, 1.1=351, 2=346," +
                " 1.10=337}",
                1.3351838891443716E131,
                71.03443700002789,
                15.957633999996364
        );
        rangeUp_BigDecimal_helper(
                2,
                1,
                "-1.0",
                "[-0.98700, -0.99999999900, 1.21, -0.9000, 5.00, -0.9700, -1, -0.90, 5.0, -0.9900, -1.0, 4, 3E+1, 0," +
                " -1, -1.0, -0.99990000, -0.9, -0.9000000, -0.700000]",
                "{-1=142722, -1.0=71169, -0.9=53492, 0=47240, -1.00=35604, -0.90=27179, -0.99=26529, -1.000=17985," +
                " 1=15792, 2=15754}",
                3359.7733766968877,
                6.862306000003966,
                1.860141999988257
        );
        rangeUp_BigDecimal_helper(
                5,
                3,
                "-1.0",
                "[-0.999979000000000, -0.999987000000, 75.574000000000, -0.8000000, -0.99999750, -0.99996610000," +
                " -1.0, 1, -0.99999400000, -0.9938, -0.99999, 0E+9, 8.0000, 0, -0.9998, -0.700000000000, 0.2," +
                " 0.493000, 5, 4.0000]",
                "{-1=20008, -1.0=14991, -1.00=11209, -0.9=8932, -1.000=8467, 0=8184, -0.99=6709, -0.90=6685," +
                " -1.0000=6349, -0.900=5106}",
                5.440539815089212E16,
                21.13045599993307,
                5.88278100000704
        );
        rangeUp_BigDecimal_helper(
                32,
                8,
                "-1.0",
                "[58755.389791438000000000, 16345805.8846900000000000000000000000000000," +
                " -0.999999999999866340000000000000, -0.9999999994000000, 6.781000, 201835.0, -0.995630000000000," +
                " 1352.39800, 932982052376.20000000, -0.99999999999999999999999999999787733013196364534800000000000," +
                " 22.520468, 272169139986909.73520000, -0.99999974805244910149600000000000000000000," +
                " -0.99999999999999170, -0.99999999999999999928800, 502509200783.40000, -0.989790660000000, 14.00," +
                " -0.99999000000, -0.95000000]",
                "{-1=656, -1.0=639, -1.00=574, -1.000=518, -1.0000=493, -1.00000=453, -1.000000=375, -0.9=344," +
                " -0.90=334, -1.0000000=318}",
                1.3351838891443716E131,
                70.79962300002984,
                15.954450999996084
        );
        rangeUp_BigDecimal_helper(
                2,
                1,
                "9",
                "[9.01300, 9.00000000100, 11.21, 9.1000, 15.00, 9.0300, 9, 9.10, 15.0, 9.0100, 9.0, 14, 4E+1, 1E+1," +
                " 9, 9, 9.10000, 9.0001, 10.5, 10]",
                "{9=143281, 9.0=71426, 9.1=53586, 1E+1=47215, 9.00=35657, 9.10=26715, 9.01=26478, 10=23935," +
                " 9.000=17786, 11=15972}",
                17089.149420142032,
                9.871688999978813,
                1.8567259999871537
        );
        rangeUp_BigDecimal_helper(
                5,
                3,
                "9",
                "[9.000021000000000, 9.000013000000, 85.574000000000, 9.2000000, 9.00000250, 9.00003390000, 9.0, 11," +
                " 9.00000600000, 9.0062, 9.00001, 10.00000000, 18.0000, 1E+1, 9.0002, 9.300000000000, 10.2," +
                " 10.493000, 15, 14.0000]",
                "{9=19722, 9.0=15111, 9.00=11254, 9.1=9026, 1E+1=8481, 9.000=8455, 9.10=6731, 9.01=6680," +
                " 9.0000=6314, 10=6282}",
                3.600790347058637E14,
                24.127075999930288,
                5.860798000007545
        );
        rangeUp_BigDecimal_helper(
                32,
                8,
                "9",
                "[58765.389791438000000000, 16345815.8846900000000000000000000000000000," +
                " 9.000000000000133660000000000000, 9.0000000006000000, 16.781000, 201845.0, 9.004370000000000," +
                " 1362.39800, 932982052386.20000000, 9.00000000000000000000000000000212266986803635465200000000000," +
                " 32.520468, 272169139986919.73520000, 9.00000025194755089850400000000000000000000," +
                " 9.00000000000000830, 9.00000000000000000071200, 502509200793.40000, 9.010209340000000, 24.00," +
                " 9.00001000000, 9.05000000]",
                "{9=712, 9.0=667, 9.00=596, 9.000=555, 9.0000=443, 9.00000=426, 9.000000=399, 9.1=351, 1E+1=346," +
                " 9.10=337}",
                1.3351838891443716E131,
                72.53076300001797,
                15.955102999996141
        );
        rangeUp_BigDecimal_helper(
                2,
                1,
                "-9",
                "[-8.98700, -8.99999999900, -6.79, -8.9000, -3.00, -8.9700, -9, -8.90, -3.0, -8.9900, -9.0, -4, 22," +
                " -8, -9, -9, -8.90000, -8.9999, -7.5, -8.0]",
                "{-9=143078, -9.0=71278, -8.9=53788, -8=47617, -9.00=35377, -8.90=26798, -8.99=26435, -8.0=23666," +
                " -9.000=17974, -7=16240}",
                8674.466684684434,
                9.867668999983753,
                1.8575879999883904
        );
        rangeUp_BigDecimal_helper(
                5,
                3,
                "-9",
                "[-8.999979000000000, -8.999987000000, 67.574000000000, -8.8000000, -8.99999750, -8.99996610000," +
                " -9.0, -7, -8.99999400000, -8.9938, -8.99999, -8.000000000, 0E+4, -8.000, -8.000, -8.999999999970," +
                " -5.000, -7.507, -7.00000, -8.9995]",
                "{-9=20227, -9.0=14822, -9.00=11209, -8.9=9156, -8=8402, -9.000=8379, -8.99=6807, -8.90=6569," +
                " -8.0=6356, -9.0000=6272}",
                3.0961163986879456E16,
                23.915657999891422,
                5.886006000007389
        );
        rangeUp_BigDecimal_helper(
                32,
                8,
                "-9",
                "[58747.389791438000000000, 16345797.8846900000000000000000000000000000," +
                " -8.999999999999866340000000000000, -8.9999999994000000, -1.219000, 201827.0, -8.995630000000000," +
                " 1344.39800, 932982052368.20000000, -8.99999999999999999999999999999787733013196364534800000000000," +
                " 14.520468, 272169139986901.73520000, -8.99999974805244910149600000000000000000000," +
                " -8.99999999999999170, -8.99999999999999999928800, 502509200775.40000, -8.989790660000000, 6.00," +
                " -8.99999000000, -8.95000000]",
                "{-9=715, -9.0=677, -9.00=604, -9.000=546, -9.0000=450, -9.00000=427, -9.000000=390, -8.9=359," +
                " -8=345, -8.90=341}",
                1.3351838891443716E131,
                72.37528700001447,
                15.956287999996425
        );
        rangeUp_BigDecimal_helper(
                2,
                1,
                "10",
                "[10.01300, 10.00000000100, 12.21, 10.1000, 16.00, 10.0300, 1E+1, 10.10, 16.0, 10.0100, 10, 15, 41," +
                " 11, 1E+1, 1E+1, 10.10000, 10.0001, 11.5, 11.0]",
                "{1E+1=143281, 10=71426, 10.1=53586, 11=47215, 10.0=35657, 10.10=26715, 10.01=26478, 11.0=23935," +
                " 10.00=17786, 12=15972}",
                17090.14942033172,
                9.335517999982333,
                1.8578319999848212
        );
        rangeUp_BigDecimal_helper(
                5,
                3,
                "10",
                "[10.000021000000000, 10.000013000000, 86.574000000000, 10.2000000, 10.00000250, 10.00003390000, 10," +
                " 12, 10.00000600000, 10.0062, 10.00001, 11.000000000, 19.0000, 11, 10.0002, 10.300000000000, 11.2," +
                " 11.493000, 16, 15.0000]",
                "{1E+1=19722, 10=15111, 10.0=11254, 10.1=9026, 11=8481, 10.00=8455, 10.10=6731, 10.01=6680," +
                " 10.000=6314, 11.0=6282}",
                3.6007903470586375E14,
                24.043575999941655,
                5.8371170000079715
        );
        rangeUp_BigDecimal_helper(
                32,
                8,
                "10",
                "[58766.389791438000000000, 16345816.8846900000000000000000000000000000," +
                " 10.000000000000133660000000000000, 10.0000000006000000, 17.781000, 201846.0, 10.004370000000000," +
                " 1363.39800, 932982052387.20000000, 10.00000000000000000000000000000212266986803635465200000000000," +
                " 33.520468, 272169139986920.73520000, 10.00000025194755089850400000000000000000000," +
                " 10.00000000000000830, 10.00000000000000000071200, 502509200794.40000, 10.010209340000000, 25.00," +
                " 10.00001000000, 10.05000000]",
                "{1E+1=712, 10=667, 10.0=596, 10.00=555, 10.000=443, 10.0000=426, 10.00000=399, 10.1=351, 11=346," +
                " 10.10=337}",
                1.3351838891443716E131,
                72.59872000002758,
                15.952337999995954
        );
        rangeUp_BigDecimal_helper(
                2,
                1,
                "-10",
                "[-9.98700, -9.99999999900, -7.79, -9.9000, -4.00, -9.9700, -1E+1, -9.90, -4.0, -9.9900, -10, -5," +
                " 21, -9, -1E+1, -1E+1, -9.90000, -9.9999, -8.5, -9.0]",
                "{-1E+1=142714, -10=71220, -9.9=53482, -9=47527, -10.0=35404, -9.99=27035, -9.90=26841, -9.0=23545," +
                " -10.00=17774, -8=16161}",
                4130.524965262865,
                9.038669999984773,
                1.8590959999849515
        );
        rangeUp_BigDecimal_helper(
                5,
                3,
                "-10",
                "[-9.999979000000000, -9.999987000000, 66.574000000000, -9.8000000, -9.99999750, -9.99996610000," +
                " -10, -8, -9.99999400000, -9.9938, -9.99999, -9.000000000, -1.0000, -9, -9.9998, -9.700000000000," +
                " -8.8, -8.507000, -4, -5.0000]",
                "{-1E+1=20117, -10=14899, -10.0=11307, -9.9=9035, -10.00=8450, -9=8322, -9.99=6770, -9.90=6666," +
                " -10.000=6393, -9.0=6274}",
                5.4598570292533712E16,
                23.705340999889525,
                5.843246000008216
        );
        rangeUp_BigDecimal_helper(
                32,
                8,
                "-10",
                "[58746.389791438000000000, 16345796.8846900000000000000000000000000000," +
                " -9.999999999999866340000000000000, -9.9999999994000000, -2.219000, 201826.0, -9.995630000000000," +
                " 1343.39800, 932982052367.20000000, -9.99999999999999999999999999999787733013196364534800000000000," +
                " 13.520468, 272169139986900.73520000, -9.99999974805244910149600000000000000000000," +
                " -9.99999999999999170, -9.99999999999999999928800, 502509200774.40000, -9.989790660000000, 5.00," +
                " -9.99999000000, -9.95000000]",
                "{-1E+1=710, -10=667, -10.0=604, -10.00=554, -10.000=445, -10.0000=434, -10.00000=399, -9.9=351," +
                " -9=348, -9.0=332}",
                1.3351838891443716E131,
                72.42762400002742,
                15.9503629999961
        );
        rangeUp_BigDecimal_helper(
                2,
                1,
                "101",
                "[101.01300, 101.00000000100, 103.21, 101.1000, 107.00, 101.0300, 101, 101.10, 107.0, 101.0100," +
                " 101.0, 106, 132, 102, 101, 101, 101.10000, 101.0001, 102.5, 102.0]",
                "{101=143281, 101.0=71426, 101.1=53586, 102=47215, 101.00=35657, 101.10=26715, 101.01=26478," +
                " 102.0=23935, 101.000=17786, 103=15972}",
                17181.149420272803,
                13.252588999963535,
                1.8572289999882734
        );
        rangeUp_BigDecimal_helper(
                5,
                3,
                "101",
                "[101.000021000000000, 101.000013000000, 177.574000000000, 101.2000000, 101.00000250," +
                " 101.00003390000, 101.0, 103, 101.00000600000, 101.0062, 101.00001, 102.000000000, 110.000, 102," +
                " 101.0002, 101.300000000000, 102.2, 102.493000, 107, 106.0000]",
                "{101=19722, 101.0=15111, 101.00=11254, 101.1=9026, 102=8481, 101.000=8455, 101.10=6731," +
                " 101.01=6680, 101.0000=6314, 102.0=6282}",
                3.600790347058687E14,
                27.13694799986988,
                5.877491000007099
        );
        rangeUp_BigDecimal_helper(
                32,
                8,
                "101",
                "[58857.389791438000000000, 16345907.8846900000000000000000000000000000," +
                " 101.000000000000133660000000000000, 101.0000000006000000, 108.781000, 201937.0," +
                " 101.004370000000000, 1454.39800, 932982052478.20000000," +
                " 101.00000000000000000000000000000212266986803635465200000000000, 124.520468," +
                " 272169139987011.73520000, 101.00000025194755089850400000000000000000000, 101.00000000000000830," +
                " 101.00000000000000000071200, 502509200885.40000, 101.010209340000000, 116.00, 101.00001000000," +
                " 101.05000000]",
                "{101=712, 101.0=667, 101.00=596, 101.000=555, 101.0000=443, 101.00000=426, 101.000000=399," +
                " 101.1=351, 102=346, 101.10=337}",
                1.3351838891443716E131,
                74.37684700003099,
                15.95764299999636
        );
        rangeUp_BigDecimal_helper(
                2,
                1,
                "-101",
                "[-100.98700, -100.99999999900, -98.79, -100.9000, -95.00, -100.9700, -101, -100.90, -95.0," +
                " -100.9900, -101.0, -96, -7E+1, -1E+2, -101, -101, -100.90000, -100.9999, -99.5, -1.0E+2]",
                "{-101=143374, -101.0=71312, -100.9=53654, -1E+2=47453, -101.00=35593, -100.90=26726, -100.99=26706," +
                " -1.0E+2=24037, -101.000=17792, -99=15963}",
                17223.395295633098,
                12.53793799994125,
                1.9045099999886295
        );
        rangeUp_BigDecimal_helper(
                5,
                3,
                "-101",
                "[-100.999979000000000, -100.999987000000, -24.426000000000, -100.8000000, -100.99999750," +
                " -100.99996610000, -101.0, -99, -100.99999400000, -100.9938, -100.99999, -100.0000000, -92.0000," +
                " -1E+2, -100.9998, -100.700000000000, -99.8, -99.507000, -95, -96.0000]",
                "{-101=19726, -101.0=15095, -101.00=11259, -100.9=9045, -1E+2=8493, -101.000=8456, -100.90=6721," +
                " -100.99=6673, -101.0000=6289, -1.0E+2=6275}",
                3.601517212028887E14,
                26.719896999833427,
                5.857195000007548
        );
        rangeUp_BigDecimal_helper(
                32,
                8,
                "-101",
                "[58655.389791438000000000, 16345705.8846900000000000000000000000000000," +
                " -100.999999999999866340000000000000, -100.9999999994000000, -93.219000, 201735.0," +
                " -100.995630000000000, 1252.39800, 932982052276.20000000," +
                " -100.99999999999999999999999999999787733013196364534800000000000, -77.479532," +
                " 272169139986809.73520000, -100.99999974805244910149600000000000000000000, -100.99999999999999170," +
                " -100.99999999999999999928800, 502509200683.40000, -100.989790660000000, -86.00, -100.99999000000," +
                " -100.95000000]",
                "{-101=655, -101.0=639, -101.00=594, -101.000=531, -101.0000=479, -101.00000=461, -101.000000=364," +
                " -100.9=348, -1.0E+2=337, -1E+2=330}",
                1.3351838891443716E131,
                74.19559500003348,
                15.946289999996068
        );
        rangeUp_BigDecimal_helper(
                2,
                1,
                "1234567",
                "[1234567.01300, 1234567.00000000100, 1234569.21, 1234567.1000, 1234573.00, 1234567.0300, 1234567," +
                " 1234567.10, 1234573.0, 1234567.0100, 1234567.0, 1234572, 1234598, 1234568, 1234567, 1234567," +
                " 1234567.10000, 1234567.0001, 1234568.5, 1234568.0]",
                "{1234567=143281, 1234567.0=71426, 1234567.1=53586, 1234568=47215, 1234567.00=35657," +
                " 1234567.10=26715, 1234567.01=26478, 1234568.0=23935, 1234567.000=17786, 1234569=15972}",
                1251647.149412802,
                26.777904999874952,
                1.8572819999879322
        );
        rangeUp_BigDecimal_helper(
                5,
                3,
                "1234567",
                "[1234567.000021000000000, 1234567.000013000000, 1234643.574000000000, 1234567.2000000," +
                " 1234567.00000250, 1234567.00003390000, 1234567.0, 1234569, 1234567.00000600000, 1234567.0062," +
                " 1234567.00001, 1234568.000000000, 1234576.0000, 1234568, 1234567.0002, 1234567.300000000000," +
                " 1234568.2, 1234568.493000, 1234573, 1234572.0000]",
                "{1234567=19722, 1234567.0=15111, 1234567.00=11254, 1234567.1=9026, 1234568=8481, 1234567.000=8455," +
                " 1234567.10=6731, 1234567.01=6680, 1234567.0000=6314, 1234568.0=6282}",
                3.6007903595507794E14,
                40.23135399994643,
                5.87069100000723
        );
        rangeUp_BigDecimal_helper(
                32,
                8,
                "1234567",
                "[1293323.389791438000000000, 17580373.8846900000000000000000000000000000," +
                " 1234567.000000000000133660000000000000, 1234567.0000000006000000, 1234574.781000, 1436403.0," +
                " 1234567.004370000000000, 1235920.39800, 932983286944.20000000," +
                " 1234567.00000000000000000000000000000212266986803635465200000000000, 1234590.520468," +
                " 272169141221477.73520000, 1234567.00000025194755089850400000000000000000000," +
                " 1234567.00000000000000830, 1234567.00000000000000000071200, 502510435351.40000," +
                " 1234567.010209340000000, 1234582.00, 1234567.00001000000, 1234567.05000000]",
                "{1234567=712, 1234567.0=667, 1234567.00=596, 1234567.000=555, 1234567.0000=443, 1234567.00000=426," +
                " 1234567.000000=399, 1234567.1=351, 1234568=346, 1234567.10=337}",
                1.3351838891443716E131,
                83.00933000010086,
                15.956358999996281
        );
        rangeUp_BigDecimal_helper(
                2,
                1,
                "-1234567",
                "[-1234566.98700, -1234566.99999999900, -1234564.79, -1234566.9000, -1234561.00, -1234566.9700," +
                " -1234567, -1234566.90, -1234561.0, -1234566.9900, -1234567.0, -1234562, -1234536, -1234566," +
                " -1234567, -1234567, -1234566.90000, -1234566.9999, -1234565.5, -1234566.0]",
                "{-1234567=143281, -1234567.0=71426, -1234566.9=53586, -1234566=47215, -1234567.00=35657," +
                " -1234566.90=26715, -1234566.99=26478, -1234566.0=23935, -1234567.000=17786, -1234565=15972}",
                -1217486.8505791144,
                26.863176999877073,
                1.8573169999882084
        );
        rangeUp_BigDecimal_helper(
                5,
                3,
                "-1234567",
                "[-1234566.999979000000000, -1234566.999987000000, -1234490.426000000000, -1234566.8000000," +
                " -1234566.99999750, -1234566.99996610000, -1234567.0, -1234565, -1234566.99999400000," +
                " -1234566.9938, -1234566.99999, -1234566.000000000, -1234558.0000, -1234566, -1234566.9998," +
                " -1234566.700000000000, -1234565.8, -1234565.507000, -1234561, -1234562.0000]",
                "{-1234567=19722, -1234567.0=15111, -1234567.00=11254, -1234566.9=9026, -1234566=8481," +
                " -1234567.000=8455, -1234566.90=6731, -1234566.99=6680, -1234567.0000=6314, -1234566.0=6282}",
                3.6007903345751275E14,
                40.25396599994677,
                5.876076000007106
        );
        rangeUp_BigDecimal_helper(
                32,
                8,
                "-1234567",
                "[-1175810.610208562000000000, 15111239.8846900000000000000000000000000000," +
                " -1234566.999999999999866340000000000000, -1234566.9999999994000000, -1234559.219000, -1032731.0," +
                " -1234566.995630000000000, -1233213.60200, 932980817810.20000000," +
                " -1234566.99999999999999999999999999999787733013196364534800000000000, -1234543.479532," +
                " 272169138752343.73520000, -1234566.99999974805244910149600000000000000000000," +
                " -1234566.99999999999999170, -1234566.99999999999999999928800, 502507966217.40000," +
                " -1234566.989790660000000, -1234552.00, -1234566.99999000000, -1234566.95000000]",
                "{-1234567=712, -1234567.0=667, -1234567.00=596, -1234567.000=555, -1234567.0000=443," +
                " -1234567.00000=426, -1234567.000000=399, -1234566.9=351, -1234566=346, -1234566.90=337}",
                1.3351838891443716E131,
                82.9216260000985,
                15.95727499999627
        );
        rangeUp_BigDecimal_helper(
                2,
                1,
                "0.09",
                "[0.10300, 0.09000000100, 2.3, 0.19000, 6.0900, 0.1200, 0.09, 0.190, 6.090, 0.100, 0.090, 5.09," +
                " 31.09, 1.09, 0.09, 0.09, 0.190000, 0.0901, 1.59, 1.090]",
                "{0.09=143281, 0.090=71426, 0.19=53586, 1.09=47215, 0.0900=35657, 0.190=26715, 0.1=26478," +
                " 1.090=23935, 0.09000=17786, 2.09=15972}",
                17080.239420429345,
                9.71032099998803,
                3.15831599996924
        );
        rangeUp_BigDecimal_helper(
                5,
                3,
                "0.09",
                "[0.090021000000000, 0.090013000000, 76.664000000000, 0.29000000, 0.09000250, 0.09003390000, 0.090," +
                " 2.09, 0.09000600000, 0.0962, 0.09001, 1.09000000000, 9.090000, 1.09, 0.0902, 0.3900000000000," +
                " 1.29, 1.583000, 6.09, 5.090000]",
                "{0.09=19722, 0.090=15111, 0.0900=11254, 0.19=9026, 1.09=8481, 0.09000=8455, 0.190=6731, 0.1=6680," +
                " 0.090000=6314, 1.090=6282}",
                3.6007903470586306E14,
                22.135698999984868,
                6.58885400000676
        );
        rangeUp_BigDecimal_helper(
                32,
                8,
                "0.09",
                "[58756.479791438000000000, 16345806.9746900000000000000000000000000000," +
                " 0.090000000000133660000000000000, 0.0900000006000000, 7.871000, 201836.090, 0.094370000000000," +
                " 1353.48800, 932982052377.290000000, 0.09000000000000000000000000000212266986803635465200000000000," +
                " 23.610468, 272169139986910.82520000, 0.09000025194755089850400000000000000000000," +
                " 0.09000000000000830, 0.09000000000000000071200, 502509200784.490000, 0.100209340000000, 15.0900," +
                " 0.09001000000, 0.14000000]",
                "{0.09=712, 0.090=667, 0.0900=596, 0.09000=555, 0.090000=443, 0.0900000=426, 0.09000000=399," +
                " 0.19=351, 1.09=346, 0.190=337}",
                1.3351838891443716E131,
                70.63211600002026,
                16.280249000005202
        );
        rangeUp_BigDecimal_helper(
                2,
                1,
                "-0.09",
                "[-0.07700, -0.08999999900, 2.12, 0.01000, 5.9100, -0.0600, -0.09, 0.010, 5.910, -0.0800, -0.090," +
                " 4.91, 30.91, 0.91, -0.09, -0.09, 0.010000, -0.0899, 1.41, 0.910]",
                "{-0.09=143126, -0.090=70890, 0.01=53642, 0.91=47553, -0.0900=35738, 0.010=26668, -0.08=26588," +
                " 0.910=23820, -0.09000=17974, 1.91=16125}",
                17216.000148206775,
                9.245889999983286,
                3.2128019999667363
        );
        rangeUp_BigDecimal_helper(
                5,
                3,
                "-0.09",
                "[-0.089979000000000, -0.089987000000, 76.484000000000, 0.11000000, -0.08999750, -0.08996610000," +
                " -0.090, 1.91, -0.08999400000, -0.0838, -0.08999, 0.91000000000, 8.910000, 0.91, -0.0898," +
                " 0.2100000000000, 1.11, 1.403000, 5.91, 4.910000]",
                "{-0.09=19885, -0.090=15020, -0.0900=11189, 0.01=8897, 0.91=8435, -0.09000=8349, -0.08=6654," +
                " 0.010=6588, 0.910=6325, -0.090000=6307}",
                6.1125822064191512E16,
                21.796880999950623,
                6.619824000006346
        );
        rangeUp_BigDecimal_helper(
                32,
                8,
                "-0.09",
                "[58756.299791438000000000, 16345806.7946900000000000000000000000000000," +
                " -0.089999999999866340000000000000, -0.0899999994000000, 7.691000, 201835.910, -0.085630000000000," +
                " 1353.30800, 932982052377.110000000," +
                " -0.08999999999999999999999999999787733013196364534800000000000, 23.430468," +
                " 272169139986910.64520000, -0.08999974805244910149600000000000000000000, -0.08999999999999170," +
                " -0.08999999999999999928800, 502509200784.310000, -0.079790660000000, 14.9100, -0.08999000000," +
                " -0.04000000]",
                "{-0.09=721, -0.090=677, -0.0900=601, -0.09000=553, -0.090000=444, -0.0900000=422, -0.09000000=400," +
                " 0.01=354, 0.91=348, 0.010=343}",
                1.3351838891443716E131,
                70.47999600001833,
                16.28069500000543
        );
        rangeUp_BigDecimal_helper(
                2,
                1,
                "1E-12",
                "[0.01300000000100, 1.00100E-9, 2.210000000001, 0.100000000001000, 6.00000000000100," +
                " 0.03000000000100, 1E-12, 0.1000000000010, 6.0000000000010, 0.01000000000100, 1.0E-12," +
                " 5.000000000001, 31.000000000001, 1.000000000001, 1E-12, 1E-12, 0.1000000000010000, 0.000100000001," +
                " 1.500000000001, 1.0000000000010]",
                "{1E-12=143281, 1.0E-12=71426, 0.100000000001=53586, 1.000000000001=47215, 1.00E-12=35657," +
                " 0.1000000000010=26715, 0.010000000001=26478, 1.0000000000010=23935, 1.000E-12=17786," +
                " 2.000000000001=15972}",
                17080.14942043894,
                30.648412999986057,
                12.999112999889352
        );
        rangeUp_BigDecimal_helper(
                5,
                3,
                "1E-12",
                "[0.000021000001000000000, 0.000013000001000000, 76.574000000001000000000, 0.200000000001000000," +
                " 0.0000025000010, 0.0000339000010000, 1.0E-12, 2.000000000001, 0.00000600000100000, 0.006200000001," +
                " 0.000010000001, 1.000000000001000000000, 9.0000000000010000, 1.000000000001, 0.000200000001," +
                " 0.30000000000100000000000, 1.200000000001, 1.493000000001000, 6.000000000001, 5.0000000000010000]",
                "{1E-12=19722, 1.0E-12=15111, 1.00E-12=11254, 0.100000000001=9026, 1.000000000001=8481," +
                " 1.000E-12=8455, 0.1000000000010=6731, 0.010000000001=6680, 1.0000E-12=6314, 1.0000000000010=6282}",
                3.6007903470586306E14,
                42.36227399999556,
                15.09517099991432
        );
        rangeUp_BigDecimal_helper(
                32,
                8,
                "1E-12",
                "[58756.389791438001000000000, 16345806.88469000000100000000000000000000000000000," +
                " 1.133660000000000000E-12, 6.01000000E-10, 7.781000000001000, 201836.0000000000010," +
                " 0.0043700000010000000000, 1353.39800000000100, 932982052377.2000000000010000000," +
                " 1.00000000000000000212266986803635465200000000000E-12, 23.520468000001," +
                " 272169139986910.7352000000010000, 2.5194855089850400000000000000000000E-7, 1.00830E-12," +
                " 1.00000071200E-12, 502509200784.4000000000010000, 0.0102093400010000000, 15.00000000000100," +
                " 0.000010000001000000, 0.050000000001000000]",
                "{1E-12=712, 1.0E-12=667, 1.00E-12=596, 1.000E-12=555, 1.0000E-12=443, 1.00000E-12=426," +
                " 1.000000E-12=399, 0.100000000001=351, 1.000000000001=346, 0.1000000000010=337}",
                1.3351838891443716E131,
                81.10178100002003,
                21.932627000013873
        );
        rangeUp_BigDecimal_helper(
                2,
                1,
                "1E+12",
                "[1000000000000.01300, 1000000000000.00000000100, 1000000000002.21, 1000000000000.1000," +
                " 1000000000006.00, 1000000000000.0300, 1E+12, 1000000000000.10, 1000000000006.0," +
                " 1000000000000.0100, 1.0E+12, 1000000000005, 1000000000031, 1000000000001, 1E+12, 1E+12," +
                " 1000000000000.10000, 1000000000000.0001, 1000000000001.5, 1000000000001.0]",
                "{1E+12=143281, 1.0E+12=71426, 1000000000000.1=53586, 1000000000001=47215, 1.00E+12=35657," +
                " 1000000000000.10=26715, 1000000000000.01=26478, 1000000000001.0=23935, 1.000E+12=17786," +
                " 1000000000002=15972}",
                1.0000000170789236E12,
                35.16661899992838,
                4.718633999998911
        );
        rangeUp_BigDecimal_helper(
                5,
                3,
                "1E+12",
                "[1000000000000.000021000000000, 1000000000000.000013000000, 1000000000076.574000000000," +
                " 1000000000000.2000000, 1000000000000.00000250, 1000000000000.00003390000, 1.0E+12, 1000000000002," +
                " 1000000000000.00000600000, 1000000000000.0062, 1000000000000.00001, 1000000000001.000000000," +
                " 1000000000009.0000, 1000000000001, 1000000000000.0002, 1000000000000.300000000000," +
                " 1000000000001.2, 1000000000001.493000, 1000000000006, 1000000000005.0000]",
                "{1E+12=19722, 1.0E+12=15111, 1.00E+12=11254, 1000000000000.1=9026, 1000000000001=8481," +
                " 1.000E+12=8455, 1000000000000.10=6731, 1000000000000.01=6680, 1.0000E+12=6314," +
                " 1000000000001.0=6282}",
                3.6107903470586306E14,
                56.71348500003783,
                6.3694550000059476
        );
        rangeUp_BigDecimal_helper(
                32,
                8,
                "1E+12",
                "[1000000058756.389791438000000000, 1000016345806.8846900000000000000000000000000000," +
                " 1000000000000.000000000000133660000000000000, 1000000000000.0000000006000000," +
                " 1000000000007.781000, 1000000201836.0, 1000000000000.004370000000000, 1000000001353.39800," +
                " 1932982052377.20000000, 1000000000000.00000000000000000000000000000212266986803635465200000000000," +
                " 1000000000023.520468, 273169139986910.73520000," +
                " 1000000000000.00000025194755089850400000000000000000000, 1000000000000.00000000000000830," +
                " 1000000000000.00000000000000000071200, 1502509200784.40000, 1000000000000.010209340000000," +
                " 1000000000015.00, 1000000000000.00001000000, 1000000000000.05000000]",
                "{1E+12=712, 1.0E+12=667, 1.00E+12=596, 1.000E+12=555, 1.0000E+12=443, 1.00000E+12=426," +
                " 1.000000E+12=399, 1000000000000.1=351, 1000000000001=346, 1000000000000.10=337}",
                1.3351838891443716E131,
                98.10678399995989,
                15.956677999996389
        );
        rangeUp_BigDecimal_fail_helper(1, 1, "0");
        rangeUp_BigDecimal_fail_helper(2, 0, "0");
    }

    private static void nextFromRangeDown_BigDecimal_helper(
            int scale,
            int secondaryScale,
            @NotNull String a,
            @NotNull String output
    ) {
        aeq(
                P.withScale(scale).withSecondaryScale(secondaryScale)
                        .nextFromRangeDown(Readers.readBigDecimal(a).get()),
                output
        );
        P.reset();
    }

    private static void nextFromRangeDown_BigDecimal_fail_helper(
            int scale,
            int secondaryScale,
            @NotNull String a
    ) {
        try {
            P.withScale(scale).withSecondaryScale(secondaryScale).nextFromRangeDown(Readers.readBigDecimal(a).get());
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testNextFromRangeDown_BigDecimal() {
        nextFromRangeDown_BigDecimal_helper(2, 1, "0", "-0.0050000000");
        nextFromRangeDown_BigDecimal_helper(5, 3, "0", "-0.0000130000");
        nextFromRangeDown_BigDecimal_helper(32, 8, "0", "-4.0493699765406300000000000000000000000000E-17");
        nextFromRangeDown_BigDecimal_helper(2, 1, "0.0", "-0.01300");
        nextFromRangeDown_BigDecimal_helper(5, 3, "0.0", "-0.000021000000000");
        nextFromRangeDown_BigDecimal_helper(32, 8, "0.0", "-58756.389791438000000000");
        nextFromRangeDown_BigDecimal_helper(2, 1, "1", "0.98700");
        nextFromRangeDown_BigDecimal_helper(5, 3, "1", "0.999979000000000");
        nextFromRangeDown_BigDecimal_helper(32, 8, "1", "-58755.389791438000000000");
        nextFromRangeDown_BigDecimal_helper(2, 1, "1.0", "0.98700");
        nextFromRangeDown_BigDecimal_helper(5, 3, "1.0", "0.999979000000000");
        nextFromRangeDown_BigDecimal_helper(32, 8, "1.0", "-58755.389791438000000000");
        nextFromRangeDown_BigDecimal_helper(2, 1, "-1.0", "-1.01300");
        nextFromRangeDown_BigDecimal_helper(5, 3, "-1.0", "-1.000021000000000");
        nextFromRangeDown_BigDecimal_helper(32, 8, "-1.0", "-58757.389791438000000000");
        nextFromRangeDown_BigDecimal_helper(2, 1, "9", "8.98700");
        nextFromRangeDown_BigDecimal_helper(5, 3, "9", "8.999979000000000");
        nextFromRangeDown_BigDecimal_helper(32, 8, "9", "-58747.389791438000000000");
        nextFromRangeDown_BigDecimal_helper(2, 1, "-9", "-9.01300");
        nextFromRangeDown_BigDecimal_helper(5, 3, "-9", "-9.000021000000000");
        nextFromRangeDown_BigDecimal_helper(32, 8, "-9", "-58765.389791438000000000");
        nextFromRangeDown_BigDecimal_helper(2, 1, "10", "9.98700");
        nextFromRangeDown_BigDecimal_helper(5, 3, "10", "9.999979000000000");
        nextFromRangeDown_BigDecimal_helper(32, 8, "10", "-58746.389791438000000000");
        nextFromRangeDown_BigDecimal_helper(2, 1, "-10", "-10.01300");
        nextFromRangeDown_BigDecimal_helper(5, 3, "-10", "-10.000021000000000");
        nextFromRangeDown_BigDecimal_helper(32, 8, "-10", "-58766.389791438000000000");
        nextFromRangeDown_BigDecimal_helper(2, 1, "101", "100.98700");
        nextFromRangeDown_BigDecimal_helper(5, 3, "101", "100.999979000000000");
        nextFromRangeDown_BigDecimal_helper(32, 8, "101", "-58655.389791438000000000");
        nextFromRangeDown_BigDecimal_helper(2, 1, "-101", "-101.01300");
        nextFromRangeDown_BigDecimal_helper(5, 3, "-101", "-101.000021000000000");
        nextFromRangeDown_BigDecimal_helper(32, 8, "-101", "-58857.389791438000000000");
        nextFromRangeDown_BigDecimal_helper(2, 1, "1234567", "1234566.98700");
        nextFromRangeDown_BigDecimal_helper(5, 3, "1234567", "1234566.999979000000000");
        nextFromRangeDown_BigDecimal_helper(32, 8, "1234567", "1175810.610208562000000000");
        nextFromRangeDown_BigDecimal_helper(2, 1, "-1234567", "-1234567.01300");
        nextFromRangeDown_BigDecimal_helper(5, 3, "-1234567", "-1234567.000021000000000");
        nextFromRangeDown_BigDecimal_helper(32, 8, "-1234567", "-1293323.389791438000000000");
        nextFromRangeDown_BigDecimal_helper(2, 1, "0.09", "0.07700");
        nextFromRangeDown_BigDecimal_helper(5, 3, "0.09", "0.089979000000000");
        nextFromRangeDown_BigDecimal_helper(32, 8, "0.09", "-58756.299791438000000000");
        nextFromRangeDown_BigDecimal_helper(2, 1, "-0.09", "-0.10300");
        nextFromRangeDown_BigDecimal_helper(5, 3, "-0.09", "-0.090021000000000");
        nextFromRangeDown_BigDecimal_helper(32, 8, "-0.09", "-58756.479791438000000000");
        nextFromRangeDown_BigDecimal_helper(2, 1, "1E-12", "-0.01299999999900");
        nextFromRangeDown_BigDecimal_helper(5, 3, "1E-12", "-0.000020999999000000000");
        nextFromRangeDown_BigDecimal_helper(32, 8, "1E-12", "-58756.389791437999000000000");
        nextFromRangeDown_BigDecimal_helper(2, 1, "1E+12", "999999999999.98700");
        nextFromRangeDown_BigDecimal_helper(5, 3, "1E+12", "999999999999.999979000000000");
        nextFromRangeDown_BigDecimal_helper(32, 8, "1E+12", "999999941243.610208562000000000");
        nextFromRangeDown_BigDecimal_fail_helper(1, 1, "0");
        nextFromRangeDown_BigDecimal_fail_helper(2, 0, "0");
    }

    private static void rangeDown_BigDecimal_helper(
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
                P.withScale(scale).withSecondaryScale(secondaryScale).rangeDown(Readers.readBigDecimal(a).get()),
                output,
                topSampleCount,
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
                "[-0.0050000000, -0.04, -0.0010, -0.060, -1, -0.01, 0, -3.00, 0, -0.010, 0E+3, 0.0, 0E+3, -1.00," +
                " -1.0, 0E+1, 0, -0.010, 0, -0.02]",
                "{0=142898, -0.1=53939, -1=47402, 0.0=35927, 0E+1=35743, -0.10=26605, -0.01=26502, -1.0=24115," +
                " 0E+2=17760, 0.00=17699}",
                -4494.6230398148555,
                3.9558340000009817,
                1.8595289999882512
        );
        rangeDown_BigDecimal_helper(
                5,
                3,
                "0",
                "[-0.0000130000, -0.0090030000, 0, 0E-11, -2.0, -2.0000, -0.1, -1.000, -0.00140, -0.000800000000," +
                " -4.56500000000000000, -2, -0.9, -2.00000000000000000E-13, -0.000100000, -0.30000, -12.6000," +
                " -0.00060, -0.0100, -0.25000000]",
                "{0=20179, -0.1=8947, -1=8287, 0E+1=7546, 0.0=7379, -0.01=6731, -0.10=6717, -1.0=6191, 0.00=5575," +
                " 0E+2=5513}",
                -1.3254633226393647E14,
                13.827922999990745,
                5.872869000007163
        );
        rangeDown_BigDecimal_helper(
                32,
                8,
                "0",
                "[-4.0493699765406300000000000000000000000000E-17, -2416.5000000000, -0.18," +
                " -180547618411867370588.2748088000000000, -76448.5400, -0.00000148518337630000000," +
                " -2352046.80000000000000000000000000000, -22222723878.730960000, -1.60161406000000000000000000000," +
                " -4.16666200000E-8, -109590.1908685744217951578289, -0.0000050000000000000000, -784415066.00," +
                " -284001.000000000000000000, -1.70605244111048790E-14, -0.00075000, -2.900000000E-18," +
                " -0.00142577220000000000, -8.5730000000000000000000000000000000000, -13908417840098258750.12656]",
                "{0=761, 0.0=367, -1=362, 0E+1=333, -1.0=331, -0.1=329, -0.10=317, -0.01=313, -1.00=286, 0E+2=279}",
                -4.18021886093211E113,
                58.30837600001352,
                15.959809999995937
        );
        rangeDown_BigDecimal_helper(
                2,
                1,
                "0.0",
                "[-0.01300, -1.00E-9, -2.21, -0.1000, -6.00, -0.0300, 0, 0E+1, -0.300, 0E+1, -5.0, -31, -1, 0," +
                " -0.00060000, -0.1, -0.1000000, -0.300000, -0.01, -2]",
                "{0=142481, -0.1=53528, -1=47698, 0E+1=35920, 0.0=35916, -0.01=26810, -0.10=26679, -1.0=23645," +
                " 0E+2=17761, 0.00=17592}",
                -15176.838793123277,
                3.953823000000856,
                1.858848999988317
        );
        rangeDown_BigDecimal_helper(
                5,
                3,
                "0.0",
                "[-0.000021000000000, -0.000013000000, -76.574000000000, -0.2000000, -0.00000250, -0.00003390000," +
                " 0E+1, -2.000000000000, -0.000060, -62.00000, 0, -0.01, -9, -0.0020, -0.0020000, -0.30, -0.012000," +
                " -54644.00, 0E+5, 0]",
                "{0=20086, -0.1=8998, -1=8268, 0E+1=7502, 0.0=7496, -0.01=6750, -0.10=6627, -1.0=6229, 0E+2=5615," +
                " 0.00=5504}",
                -2.3187079784448434E14,
                13.856063999991001,
                5.882033000007064
        );
        rangeDown_BigDecimal_helper(
                32,
                8,
                "0.0",
                "[-58756.389791438000000000, -16345806.8846900000000000000000000000000000, -1.33660000000000000E-13," +
                " -6.000000E-10, -7.781000, -201836.0, -0.004370000000000, -1353.39800, -932982052377.20000000," +
                " -2.12266986803635465200000000000E-30, -23.520468, -272169139986910.73520000," +
                " -2.5194755089850400000000000000000000E-7, -8.30E-15, -7.1200E-19, -502509200784.40000," +
                " -0.010209340000000, -15.00, -0.00001000000, -0.05000000]",
                "{0=682, -0.1=348, 0E+1=332, -1=329, 0.0=326, -1.0=325, -0.10=319, 0.00=300, 0E+2=300, -0.100=298}",
                -1.3351838891443716E131,
                58.282749000012764,
                15.963111999996999
        );
        rangeDown_BigDecimal_helper(
                2,
                1,
                "1",
                "[0.98700, 0.99999999900, -1.21, 0.9000, -5.00, 0.9700, 1, 0.90, -5.0, 0.9900, 1.0, -4, -3E+1, 0, 1," +
                " 1.0, 0.99990000, 0.9, 0.9000000, 0.700000]",
                "{1=142722, 1.0=71169, 0.9=53492, 0=47240, 1.00=35604, 0.90=27179, 0.99=26529, 1.000=17985," +
                " -1=15792, -2=15754}",
                -3359.7733766968877,
                6.862306000003966,
                1.860141999988257
        );
        rangeDown_BigDecimal_helper(
                5,
                3,
                "1",
                "[0.999979000000000, 0.999987000000, -75.574000000000, 0.8000000, 0.99999750, 0.99996610000, 1.0," +
                " -1, 0.99999400000, 0.9938, 0.99999, 0E+9, -8.0000, 0, 0.9998, 0.700000000000, -0.2, -0.493000, -5," +
                " -4.0000]",
                "{1=20008, 1.0=14991, 1.00=11209, 0.9=8932, 1.000=8467, 0=8184, 0.99=6709, 0.90=6685, 1.0000=6349," +
                " 0.900=5106}",
                -5.440539815089212E16,
                21.13045599993307,
                5.88278100000704
        );
        rangeDown_BigDecimal_helper(
                32,
                8,
                "1",
                "[-58755.389791438000000000, -16345805.8846900000000000000000000000000000," +
                " 0.999999999999866340000000000000, 0.9999999994000000, -6.781000, -201835.0, 0.995630000000000," +
                " -1352.39800, -932982052376.20000000," +
                " 0.99999999999999999999999999999787733013196364534800000000000, -22.520468," +
                " -272169139986909.73520000, 0.99999974805244910149600000000000000000000, 0.99999999999999170," +
                " 0.99999999999999999928800, -502509200783.40000, 0.989790660000000, -14.00, 0.99999000000," +
                " 0.95000000]",
                "{1=656, 1.0=639, 1.00=574, 1.000=518, 1.0000=493, 1.00000=453, 1.000000=375, 0.9=344, 0.90=334," +
                " 1.0000000=318}",
                -1.3351838891443716E131,
                70.79962300002984,
                15.954450999996084
        );
        rangeDown_BigDecimal_helper(
                2,
                1,
                "1.0",
                "[0.98700, 0.99999999900, -1.21, 0.9000, -5.00, 0.9700, 1, 0.90, -5.0, 0.9900, 1.0, -4, -3E+1, 0, 1," +
                " 1.0, 0.99990000, 0.9, 0.9000000, 0.700000]",
                "{1=142722, 1.0=71169, 0.9=53492, 0=47240, 1.00=35604, 0.90=27179, 0.99=26529, 1.000=17985," +
                " -1=15792, -2=15754}",
                -3359.7733766968877,
                6.862306000003966,
                1.860141999988257
        );
        rangeDown_BigDecimal_helper(
                5,
                3,
                "1.0",
                "[0.999979000000000, 0.999987000000, -75.574000000000, 0.8000000, 0.99999750, 0.99996610000, 1.0," +
                " -1, 0.99999400000, 0.9938, 0.99999, 0E+9, -8.0000, 0, 0.9998, 0.700000000000, -0.2, -0.493000, -5," +
                " -4.0000]",
                "{1=20008, 1.0=14991, 1.00=11209, 0.9=8932, 1.000=8467, 0=8184, 0.99=6709, 0.90=6685, 1.0000=6349," +
                " 0.900=5106}",
                -5.440539815089212E16,
                21.13045599993307,
                5.88278100000704
        );
        rangeDown_BigDecimal_helper(
                32,
                8,
                "1.0",
                "[-58755.389791438000000000, -16345805.8846900000000000000000000000000000," +
                " 0.999999999999866340000000000000, 0.9999999994000000, -6.781000, -201835.0, 0.995630000000000," +
                " -1352.39800, -932982052376.20000000," +
                " 0.99999999999999999999999999999787733013196364534800000000000, -22.520468," +
                " -272169139986909.73520000, 0.99999974805244910149600000000000000000000, 0.99999999999999170," +
                " 0.99999999999999999928800, -502509200783.40000, 0.989790660000000, -14.00, 0.99999000000," +
                " 0.95000000]",
                "{1=656, 1.0=639, 1.00=574, 1.000=518, 1.0000=493, 1.00000=453, 1.000000=375, 0.9=344, 0.90=334," +
                " 1.0000000=318}",
                -1.3351838891443716E131,
                70.79962300002984,
                15.954450999996084
        );
        rangeDown_BigDecimal_helper(
                2,
                1,
                "-1.0",
                "[-1.01300, -1.00000000100, -3.21, -1.1000, -7.00, -1.0300, -1, -1.10, -7.0, -1.0100, -1.0, -6, -32," +
                " -2, -1, -1, -1.10000, -1.0001, -2.5, -2.0]",
                "{-1=143281, -1.0=71426, -1.1=53586, -2=47215, -1.00=35657, -1.10=26715, -1.01=26478, -2.0=23935," +
                " -1.000=17786, -3=15972}",
                -17081.149420358004,
                7.613145999998498,
                1.8572279999882735
        );
        rangeDown_BigDecimal_helper(
                5,
                3,
                "-1.0",
                "[-1.000021000000000, -1.000013000000, -77.574000000000, -1.2000000, -1.00000250, -1.00003390000," +
                " -1.0, -3, -1.00000600000, -1.0062, -1.00001, -2.000000000, -10.000, -2, -1.0002, -1.300000000000," +
                " -2.2, -2.493000, -7, -6.0000]",
                "{-1=19722, -1.0=15111, -1.00=11254, -1.1=9026, -2=8481, -1.000=8455, -1.10=6731, -1.01=6680," +
                " -1.0000=6314, -2.0=6282}",
                -3.600790347058631E14,
                21.833742999973637,
                5.877492000007097
        );
        rangeDown_BigDecimal_helper(
                32,
                8,
                "-1.0",
                "[-58757.389791438000000000, -16345807.8846900000000000000000000000000000," +
                " -1.000000000000133660000000000000, -1.0000000006000000, -8.781000, -201837.0, -1.004370000000000," +
                " -1354.39800, -932982052378.20000000," +
                " -1.00000000000000000000000000000212266986803635465200000000000, -24.520468," +
                " -272169139986911.73520000, -1.00000025194755089850400000000000000000000, -1.00000000000000830," +
                " -1.00000000000000000071200, -502509200785.40000, -1.010209340000000, -16.00, -1.00001000000," +
                " -1.05000000]",
                "{-1=712, -1.0=667, -1.00=596, -1.000=555, -1.0000=443, -1.00000=426, -1.000000=399, -1.1=351," +
                " -2=346, -1.10=337}",
                -1.3351838891443716E131,
                71.03443700002789,
                15.957633999996364
        );
        rangeDown_BigDecimal_helper(
                2,
                1,
                "9",
                "[8.98700, 8.99999999900, 6.79, 8.9000, 3.00, 8.9700, 9, 8.90, 3.0, 8.9900, 9.0, 4, -22, 8, 9, 9," +
                " 8.90000, 8.9999, 7.5, 8.0]",
                "{9=143078, 9.0=71278, 8.9=53788, 8=47617, 9.00=35377, 8.90=26798, 8.99=26435, 8.0=23666," +
                " 9.000=17974, 7=16240}",
                -8674.466684684434,
                9.867668999983753,
                1.8575879999883904
        );
        rangeDown_BigDecimal_helper(
                5,
                3,
                "9",
                "[8.999979000000000, 8.999987000000, -67.574000000000, 8.8000000, 8.99999750, 8.99996610000, 9.0, 7," +
                " 8.99999400000, 8.9938, 8.99999, 8.000000000, 0E+4, 8.000, 8.000, 8.999999999970, 5.000, 7.507," +
                " 7.00000, 8.9995]",
                "{9=20227, 9.0=14822, 9.00=11209, 8.9=9156, 8=8402, 9.000=8379, 8.99=6807, 8.90=6569, 8.0=6356," +
                " 9.0000=6272}",
                -3.0961163986879456E16,
                23.915657999891422,
                5.886006000007389
        );
        rangeDown_BigDecimal_helper(
                32,
                8,
                "9",
                "[-58747.389791438000000000, -16345797.8846900000000000000000000000000000," +
                " 8.999999999999866340000000000000, 8.9999999994000000, 1.219000, -201827.0, 8.995630000000000," +
                " -1344.39800, -932982052368.20000000," +
                " 8.99999999999999999999999999999787733013196364534800000000000, -14.520468," +
                " -272169139986901.73520000, 8.99999974805244910149600000000000000000000, 8.99999999999999170," +
                " 8.99999999999999999928800, -502509200775.40000, 8.989790660000000, -6.00, 8.99999000000," +
                " 8.95000000]",
                "{9=715, 9.0=677, 9.00=604, 9.000=546, 9.0000=450, 9.00000=427, 9.000000=390, 8.9=359, 8=345," +
                " 8.90=341}",
                -1.3351838891443716E131,
                72.37528700001447,
                15.956287999996425
        );
        rangeDown_BigDecimal_helper(
                2,
                1,
                "-9",
                "[-9.01300, -9.00000000100, -11.21, -9.1000, -15.00, -9.0300, -9, -9.10, -15.0, -9.0100, -9.0, -14," +
                " -4E+1, -1E+1, -9, -9, -9.10000, -9.0001, -10.5, -10]",
                "{-9=143281, -9.0=71426, -9.1=53586, -1E+1=47215, -9.00=35657, -9.10=26715, -9.01=26478, -10=23935," +
                " -9.000=17786, -11=15972}",
                -17089.149420142032,
                9.871688999978813,
                1.8567259999871537
        );
        rangeDown_BigDecimal_helper(
                5,
                3,
                "-9",
                "[-9.000021000000000, -9.000013000000, -85.574000000000, -9.2000000, -9.00000250, -9.00003390000," +
                " -9.0, -11, -9.00000600000, -9.0062, -9.00001, -10.00000000, -18.0000, -1E+1, -9.0002," +
                " -9.300000000000, -10.2, -10.493000, -15, -14.0000]",
                "{-9=19722, -9.0=15111, -9.00=11254, -9.1=9026, -1E+1=8481, -9.000=8455, -9.10=6731, -9.01=6680," +
                " -9.0000=6314, -10=6282}",
                -3.600790347058637E14,
                24.127075999930288,
                5.860798000007545
        );
        rangeDown_BigDecimal_helper(
                32,
                8,
                "-9",
                "[-58765.389791438000000000, -16345815.8846900000000000000000000000000000," +
                " -9.000000000000133660000000000000, -9.0000000006000000, -16.781000, -201845.0, -9.004370000000000," +
                " -1362.39800, -932982052386.20000000," +
                " -9.00000000000000000000000000000212266986803635465200000000000, -32.520468," +
                " -272169139986919.73520000, -9.00000025194755089850400000000000000000000, -9.00000000000000830," +
                " -9.00000000000000000071200, -502509200793.40000, -9.010209340000000, -24.00, -9.00001000000," +
                " -9.05000000]",
                "{-9=712, -9.0=667, -9.00=596, -9.000=555, -9.0000=443, -9.00000=426, -9.000000=399, -9.1=351," +
                " -1E+1=346, -9.10=337}",
                -1.3351838891443716E131,
                72.53076300001797,
                15.955102999996141
        );
        rangeDown_BigDecimal_helper(
                2,
                1,
                "10",
                "[9.98700, 9.99999999900, 7.79, 9.9000, 4.00, 9.9700, 1E+1, 9.90, 4.0, 9.9900, 10, 5, -21, 9, 1E+1," +
                " 1E+1, 9.90000, 9.9999, 8.5, 9.0]",
                "{1E+1=142714, 10=71220, 9.9=53482, 9=47527, 10.0=35404, 9.99=27035, 9.90=26841, 9.0=23545," +
                " 10.00=17774, 8=16161}",
                -4130.524965262865,
                9.038669999984773,
                1.8590959999849515
        );
        rangeDown_BigDecimal_helper(
                5,
                3,
                "10",
                "[9.999979000000000, 9.999987000000, -66.574000000000, 9.8000000, 9.99999750, 9.99996610000, 10, 8," +
                " 9.99999400000, 9.9938, 9.99999, 9.000000000, 1.0000, 9, 9.9998, 9.700000000000, 8.8, 8.507000, 4," +
                " 5.0000]",
                "{1E+1=20117, 10=14899, 10.0=11307, 9.9=9035, 10.00=8450, 9=8322, 9.99=6770, 9.90=6666, 10.000=6393," +
                " 9.0=6274}",
                -5.4598570292533712E16,
                23.705340999889525,
                5.843246000008216
        );
        rangeDown_BigDecimal_helper(
                32,
                8,
                "10",
                "[-58746.389791438000000000, -16345796.8846900000000000000000000000000000," +
                " 9.999999999999866340000000000000, 9.9999999994000000, 2.219000, -201826.0, 9.995630000000000," +
                " -1343.39800, -932982052367.20000000," +
                " 9.99999999999999999999999999999787733013196364534800000000000, -13.520468," +
                " -272169139986900.73520000, 9.99999974805244910149600000000000000000000, 9.99999999999999170," +
                " 9.99999999999999999928800, -502509200774.40000, 9.989790660000000, -5.00, 9.99999000000," +
                " 9.95000000]",
                "{1E+1=710, 10=667, 10.0=604, 10.00=554, 10.000=445, 10.0000=434, 10.00000=399, 9.9=351, 9=348," +
                " 9.0=332}",
                -1.3351838891443716E131,
                72.42762400002742,
                15.9503629999961
        );
        rangeDown_BigDecimal_helper(
                2,
                1,
                "-10",
                "[-10.01300, -10.00000000100, -12.21, -10.1000, -16.00, -10.0300, -1E+1, -10.10, -16.0, -10.0100," +
                " -10, -15, -41, -11, -1E+1, -1E+1, -10.10000, -10.0001, -11.5, -11.0]",
                "{-1E+1=143281, -10=71426, -10.1=53586, -11=47215, -10.0=35657, -10.10=26715, -10.01=26478," +
                " -11.0=23935, -10.00=17786, -12=15972}",
                -17090.14942033172,
                9.335517999982333,
                1.8578319999848212
        );
        rangeDown_BigDecimal_helper(
                5,
                3,
                "-10",
                "[-10.000021000000000, -10.000013000000, -86.574000000000, -10.2000000, -10.00000250," +
                " -10.00003390000, -10, -12, -10.00000600000, -10.0062, -10.00001, -11.000000000, -19.0000, -11," +
                " -10.0002, -10.300000000000, -11.2, -11.493000, -16, -15.0000]",
                "{-1E+1=19722, -10=15111, -10.0=11254, -10.1=9026, -11=8481, -10.00=8455, -10.10=6731, -10.01=6680," +
                " -10.000=6314, -11.0=6282}",
                -3.6007903470586375E14,
                24.043575999941655,
                5.8371170000079715
        );
        rangeDown_BigDecimal_helper(
                32,
                8,
                "-10",
                "[-58766.389791438000000000, -16345816.8846900000000000000000000000000000," +
                " -10.000000000000133660000000000000, -10.0000000006000000, -17.781000, -201846.0," +
                " -10.004370000000000, -1363.39800, -932982052387.20000000," +
                " -10.00000000000000000000000000000212266986803635465200000000000, -33.520468," +
                " -272169139986920.73520000, -10.00000025194755089850400000000000000000000, -10.00000000000000830," +
                " -10.00000000000000000071200, -502509200794.40000, -10.010209340000000, -25.00, -10.00001000000," +
                " -10.05000000]",
                "{-1E+1=712, -10=667, -10.0=596, -10.00=555, -10.000=443, -10.0000=426, -10.00000=399, -10.1=351," +
                " -11=346, -10.10=337}",
                -1.3351838891443716E131,
                72.59872000002758,
                15.952337999995954
        );
        rangeDown_BigDecimal_helper(
                2,
                1,
                "101",
                "[100.98700, 100.99999999900, 98.79, 100.9000, 95.00, 100.9700, 101, 100.90, 95.0, 100.9900, 101.0," +
                " 96, 7E+1, 1E+2, 101, 101, 100.90000, 100.9999, 99.5, 1.0E+2]",
                "{101=143374, 101.0=71312, 100.9=53654, 1E+2=47453, 101.00=35593, 100.90=26726, 100.99=26706," +
                " 1.0E+2=24037, 101.000=17792, 99=15963}",
                -17223.395295633098,
                12.53793799994125,
                1.9045099999886295
        );
        rangeDown_BigDecimal_helper(
                5,
                3,
                "101",
                "[100.999979000000000, 100.999987000000, 24.426000000000, 100.8000000, 100.99999750," +
                " 100.99996610000, 101.0, 99, 100.99999400000, 100.9938, 100.99999, 100.0000000, 92.0000, 1E+2," +
                " 100.9998, 100.700000000000, 99.8, 99.507000, 95, 96.0000]",
                "{101=19726, 101.0=15095, 101.00=11259, 100.9=9045, 1E+2=8493, 101.000=8456, 100.90=6721," +
                " 100.99=6673, 101.0000=6289, 1.0E+2=6275}",
                -3.601517212028887E14,
                26.719896999833427,
                5.857195000007548
        );
        rangeDown_BigDecimal_helper(
                32,
                8,
                "101",
                "[-58655.389791438000000000, -16345705.8846900000000000000000000000000000," +
                " 100.999999999999866340000000000000, 100.9999999994000000, 93.219000, -201735.0," +
                " 100.995630000000000, -1252.39800, -932982052276.20000000," +
                " 100.99999999999999999999999999999787733013196364534800000000000, 77.479532, " +
                "-272169139986809.73520000, 100.99999974805244910149600000000000000000000, 100.99999999999999170," +
                " 100.99999999999999999928800, -502509200683.40000, 100.989790660000000, 86.00, 100.99999000000," +
                " 100.95000000]",
                "{101=655, 101.0=639, 101.00=594, 101.000=531, 101.0000=479, 101.00000=461, 101.000000=364," +
                " 100.9=348, 1.0E+2=337, 1E+2=330}",
                -1.3351838891443716E131,
                74.19559500003348,
                15.946289999996068
        );
        rangeDown_BigDecimal_helper(
                2,
                1,
                "-101",
                "[-101.01300, -101.00000000100, -103.21, -101.1000, -107.00, -101.0300, -101, -101.10, -107.0," +
                " -101.0100, -101.0, -106, -132, -102, -101, -101, -101.10000, -101.0001, -102.5, -102.0]",
                "{-101=143281, -101.0=71426, -101.1=53586, -102=47215, -101.00=35657, -101.10=26715, -101.01=26478," +
                " -102.0=23935, -101.000=17786, -103=15972}",
                -17181.149420272803,
                13.252588999963535,
                1.8572289999882734
        );
        rangeDown_BigDecimal_helper(
                5,
                3,
                "-101",
                "[-101.000021000000000, -101.000013000000, -177.574000000000, -101.2000000, -101.00000250," +
                " -101.00003390000, -101.0, -103, -101.00000600000, -101.0062, -101.00001, -102.000000000, -110.000," +
                " -102, -101.0002, -101.300000000000, -102.2, -102.493000, -107, -106.0000]",
                "{-101=19722, -101.0=15111, -101.00=11254, -101.1=9026, -102=8481, -101.000=8455, -101.10=6731," +
                " -101.01=6680, -101.0000=6314, -102.0=6282}",
                -3.600790347058687E14,
                27.13694799986988,
                5.877491000007099
        );
        rangeDown_BigDecimal_helper(
                32,
                8,
                "-101",
                "[-58857.389791438000000000, -16345907.8846900000000000000000000000000000," +
                " -101.000000000000133660000000000000, -101.0000000006000000, -108.781000, -201937.0," +
                " -101.004370000000000, -1454.39800, -932982052478.20000000," +
                " -101.00000000000000000000000000000212266986803635465200000000000, -124.520468," +
                " -272169139987011.73520000, -101.00000025194755089850400000000000000000000, -101.00000000000000830," +
                " -101.00000000000000000071200, -502509200885.40000, -101.010209340000000, -116.00," +
                " -101.00001000000, -101.05000000]",
                "{-101=712, -101.0=667, -101.00=596, -101.000=555, -101.0000=443, -101.00000=426, -101.000000=399," +
                " -101.1=351, -102=346, -101.10=337}",
                -1.3351838891443716E131,
                74.37684700003099,
                15.95764299999636
        );
        rangeDown_BigDecimal_helper(
                2,
                1,
                "1234567",
                "[1234566.98700, 1234566.99999999900, 1234564.79, 1234566.9000, 1234561.00, 1234566.9700, 1234567," +
                " 1234566.90, 1234561.0, 1234566.9900, 1234567.0, 1234562, 1234536, 1234566, 1234567, 1234567," +
                " 1234566.90000, 1234566.9999, 1234565.5, 1234566.0]",
                "{1234567=143281, 1234567.0=71426, 1234566.9=53586, 1234566=47215, 1234567.00=35657," +
                " 1234566.90=26715, 1234566.99=26478, 1234566.0=23935, 1234567.000=17786, 1234565=15972}",
                1217486.8505791144,
                26.863176999877073,
                1.8573169999882084
        );
        rangeDown_BigDecimal_helper(
                5,
                3,
                "1234567",
                "[1234566.999979000000000, 1234566.999987000000, 1234490.426000000000, 1234566.8000000," +
                " 1234566.99999750, 1234566.99996610000, 1234567.0, 1234565, 1234566.99999400000, 1234566.9938," +
                " 1234566.99999, 1234566.000000000, 1234558.0000, 1234566, 1234566.9998, 1234566.700000000000," +
                " 1234565.8, 1234565.507000, 1234561, 1234562.0000]",
                "{1234567=19722, 1234567.0=15111, 1234567.00=11254, 1234566.9=9026, 1234566=8481, 1234567.000=8455," +
                " 1234566.90=6731, 1234566.99=6680, 1234567.0000=6314, 1234566.0=6282}",
                -3.6007903345751275E14,
                40.25396599994677,
                5.876076000007106
        );
        rangeDown_BigDecimal_helper(
                32,
                8,
                "1234567",
                "[1175810.610208562000000000, -15111239.8846900000000000000000000000000000," +
                " 1234566.999999999999866340000000000000, 1234566.9999999994000000, 1234559.219000, 1032731.0," +
                " 1234566.995630000000000, 1233213.60200, -932980817810.20000000," +
                " 1234566.99999999999999999999999999999787733013196364534800000000000, 1234543.479532," +
                " -272169138752343.73520000, 1234566.99999974805244910149600000000000000000000," +
                " 1234566.99999999999999170, 1234566.99999999999999999928800, -502507966217.40000," +
                " 1234566.989790660000000, 1234552.00, 1234566.99999000000, 1234566.95000000]",
                "{1234567=712, 1234567.0=667, 1234567.00=596, 1234567.000=555, 1234567.0000=443, 1234567.00000=426," +
                " 1234567.000000=399, 1234566.9=351, 1234566=346, 1234566.90=337}",
                -1.3351838891443716E131,
                82.9216260000985,
                15.95727499999627
        );
        rangeDown_BigDecimal_helper(
                2,
                1,
                "-1234567",
                "[-1234567.01300, -1234567.00000000100, -1234569.21, -1234567.1000, -1234573.00, -1234567.0300," +
                " -1234567, -1234567.10, -1234573.0, -1234567.0100, -1234567.0, -1234572, -1234598, -1234568," +
                " -1234567, -1234567, -1234567.10000, -1234567.0001, -1234568.5, -1234568.0]",
                "{-1234567=143281, -1234567.0=71426, -1234567.1=53586, -1234568=47215, -1234567.00=35657," +
                " -1234567.10=26715, -1234567.01=26478, -1234568.0=23935, -1234567.000=17786, -1234569=15972}",
                -1251647.149412802,
                26.777904999874952,
                1.8572819999879322
        );
        rangeDown_BigDecimal_helper(
                5,
                3,
                "-1234567",
                "[-1234567.000021000000000, -1234567.000013000000, -1234643.574000000000, -1234567.2000000," +
                " -1234567.00000250, -1234567.00003390000, -1234567.0, -1234569, -1234567.00000600000," +
                " -1234567.0062, -1234567.00001, -1234568.000000000, -1234576.0000, -1234568, -1234567.0002," +
                " -1234567.300000000000, -1234568.2, -1234568.493000, -1234573, -1234572.0000]",
                "{-1234567=19722, -1234567.0=15111, -1234567.00=11254, -1234567.1=9026, -1234568=8481," +
                " -1234567.000=8455, -1234567.10=6731, -1234567.01=6680, -1234567.0000=6314, -1234568.0=6282}",
                -3.6007903595507794E14,
                40.23135399994643,
                5.87069100000723
        );
        rangeDown_BigDecimal_helper(
                32,
                8,
                "-1234567",
                "[-1293323.389791438000000000, -17580373.8846900000000000000000000000000000," +
                " -1234567.000000000000133660000000000000, -1234567.0000000006000000, -1234574.781000, -1436403.0," +
                " -1234567.004370000000000, -1235920.39800, -932983286944.20000000," +
                " -1234567.00000000000000000000000000000212266986803635465200000000000, -1234590.520468," +
                " -272169141221477.73520000, -1234567.00000025194755089850400000000000000000000," +
                " -1234567.00000000000000830, -1234567.00000000000000000071200, -502510435351.40000," +
                " -1234567.010209340000000, -1234582.00, -1234567.00001000000, -1234567.05000000]",
                "{-1234567=712, -1234567.0=667, -1234567.00=596, -1234567.000=555, -1234567.0000=443," +
                " -1234567.00000=426, -1234567.000000=399, -1234567.1=351, -1234568=346, -1234567.10=337}",
                -1.3351838891443716E131,
                83.00933000010086,
                15.956358999996281
        );
        rangeDown_BigDecimal_helper(
                2,
                1,
                "0.09",
                "[0.07700, 0.08999999900, -2.12, -0.01000, -5.9100, 0.0600, 0.09, -0.010, -5.910, 0.0800, 0.090," +
                " -4.91, -30.91, -0.91, 0.09, 0.09, -0.010000, 0.0899, -1.41, -0.910]",
                "{0.09=143126, 0.090=70890, -0.01=53642, -0.91=47553, 0.0900=35738, -0.010=26668, 0.08=26588," +
                " -0.910=23820, 0.09000=17974, -1.91=16125}",
                -17216.000148206775,
                9.245889999983286,
                3.2128019999667363
        );
        rangeDown_BigDecimal_helper(
                5,
                3,
                "0.09",
                "[0.089979000000000, 0.089987000000, -76.484000000000, -0.11000000, 0.08999750, 0.08996610000," +
                " 0.090, -1.91, 0.08999400000, 0.0838, 0.08999, -0.91000000000, -8.910000, -0.91, 0.0898," +
                " -0.2100000000000, -1.11, -1.403000, -5.91, -4.910000]",
                "{0.09=19885, 0.090=15020, 0.0900=11189, -0.01=8897, -0.91=8435, 0.09000=8349, 0.08=6654," +
                " -0.010=6588, -0.910=6325, 0.090000=6307}",
                -6.1125822064191512E16,
                21.796880999950623,
                6.619824000006346
        );
        rangeDown_BigDecimal_helper(
                32,
                8,
                "0.09",
                "[-58756.299791438000000000, -16345806.7946900000000000000000000000000000," +
                " 0.089999999999866340000000000000, 0.0899999994000000, -7.691000, -201835.910, 0.085630000000000," +
                " -1353.30800, -932982052377.110000000," +
                " 0.08999999999999999999999999999787733013196364534800000000000, -23.430468," +
                " -272169139986910.64520000, 0.08999974805244910149600000000000000000000, 0.08999999999999170," +
                " 0.08999999999999999928800, -502509200784.310000, 0.079790660000000, -14.9100, 0.08999000000," +
                " 0.04000000]",
                "{0.09=721, 0.090=677, 0.0900=601, 0.09000=553, 0.090000=444, 0.0900000=422, 0.09000000=400, " +
                "-0.01=354, -0.91=348, -0.010=343}",
                -1.3351838891443716E131,
                70.47999600001833,
                16.28069500000543
        );
        rangeDown_BigDecimal_helper(
                2,
                1,
                "-0.09",
                "[-0.10300, -0.09000000100, -2.3, -0.19000, -6.0900, -0.1200, -0.09, -0.190, -6.090, -0.100, -0.090," +
                " -5.09, -31.09, -1.09, -0.09, -0.09, -0.190000, -0.0901, -1.59, -1.090]",
                "{-0.09=143281, -0.090=71426, -0.19=53586, -1.09=47215, -0.0900=35657, -0.190=26715, -0.1=26478," +
                " -1.090=23935, -0.09000=17786, -2.09=15972}",
                -17080.239420429345,
                9.71032099998803,
                3.15831599996924
        );
        rangeDown_BigDecimal_helper(
                5,
                3,
                "-0.09",
                "[-0.090021000000000, -0.090013000000, -76.664000000000, -0.29000000, -0.09000250, -0.09003390000," +
                " -0.090, -2.09, -0.09000600000, -0.0962, -0.09001, -1.09000000000, -9.090000, -1.09, -0.0902," +
                " -0.3900000000000, -1.29, -1.583000, -6.09, -5.090000]",
                "{-0.09=19722, -0.090=15111, -0.0900=11254, -0.19=9026, -1.09=8481, -0.09000=8455, -0.190=6731," +
                " -0.1=6680, -0.090000=6314, -1.090=6282}",
                -3.6007903470586306E14,
                22.135698999984868,
                6.58885400000676
        );
        rangeDown_BigDecimal_helper(
                32,
                8,
                "-0.09",
                "[-58756.479791438000000000, -16345806.9746900000000000000000000000000000," +
                " -0.090000000000133660000000000000, -0.0900000006000000, -7.871000, -201836.090," +
                " -0.094370000000000, -1353.48800, -932982052377.290000000," +
                " -0.09000000000000000000000000000212266986803635465200000000000, -23.610468," +
                " -272169139986910.82520000, -0.09000025194755089850400000000000000000000, -0.09000000000000830," +
                " -0.09000000000000000071200, -502509200784.490000, -0.100209340000000, -15.0900, -0.09001000000," +
                " -0.14000000]",
                "{-0.09=712, -0.090=667, -0.0900=596, -0.09000=555, -0.090000=443, -0.0900000=426, -0.09000000=399," +
                " -0.19=351, -1.09=346, -0.190=337}",
                -1.3351838891443716E131,
                70.63211600002026,
                16.280249000005202
        );
        rangeDown_BigDecimal_helper(
                2,
                1,
                "1E-12",
                "[-0.01299999999900, -9.9900E-10, -2.209999999999, -0.099999999999000, -5.99999999999900," +
                " -0.02999999999900, 1E-12, -0.0999999999990, -5.9999999999990, -0.00999999999900, 1.0E-12," +
                " -4.999999999999, -30.999999999999, -0.999999999999, 1E-12, 1E-12, -0.0999999999990000," +
                " -0.000099999999, -1.499999999999, -0.9999999999990]",
                "{1E-12=143270, 1.0E-12=71462, -0.099999999999=53556, -0.999999999999=47205, 1.00E-12=35639," +
                " -0.0999999999990=26726, -0.009999999999=26473, -0.9999999999990=23943, 1.000E-12=17783," +
                " -1.999999999999=15983}",
                -17080.23379780748,
                30.64786699998604,
                12.998404999889338
        );
        rangeDown_BigDecimal_helper(
                5,
                3,
                "1E-12",
                "[-0.000020999999000000000, -0.000012999999000000, -76.573999999999000000000, -0.199999999999000000," +
                " -0.0000024999990, -0.0000338999990000, 1.0E-12, -1.999999999999, -0.00000599999900000," +
                " -0.006199999999, -0.000009999999, -0.999999999999000000000, -8.9999999999990000, -0.999999999999," +
                " -0.000199999999, -0.29999999999900000000000, -1.199999999999, -1.492999999999000, -5.999999999999," +
                " -4.9999999999990000]",
                "{1E-12=19875, 1.0E-12=14948, 1.00E-12=11089, -0.099999999999=9025, 1.000E-12=8519," +
                " -0.999999999999=8425, -0.009999999999=6721, -0.0999999999990=6596, 1.0000E-12=6388," +
                " -0.9999999999990=6285}",
                -5.5138569482701824E16,
                42.32738699999434,
                15.077496999915066
        );
        rangeDown_BigDecimal_helper(
                32,
                8,
                "1E-12",
                "[-58756.389791437999000000000, -16345806.88468999999900000000000000000000000000000," +
                " 8.66340000000000000E-13, -5.99000000E-10, -7.780999999999000, -201835.9999999999990," +
                " -0.0043699999990000000000, -1353.39799999999900, -932982052377.1999999999990000000," +
                " 9.9999999999999999787733013196364534800000000000E-13, -23.520467999999," +
                " -272169139986910.7351999999990000, -2.5194655089850400000000000000000000E-7, 9.9170E-13," +
                " 9.9999928800E-13, -502509200784.3999999999990000, -0.0102093399990000000, -14.99999999999900," +
                " -0.000009999999000000, -0.049999999999000000]",
                "{1E-12=725, 1.0E-12=676, 1.00E-12=594, 1.000E-12=550, 1.0000E-12=443, 1.00000E-12=422," +
                " 1.000000E-12=397, -0.999999999999=352, -0.099999999999=352, -0.9999999999990=333}",
                -1.3351838891443716E131,
                81.05990200001985,
                21.92486600001394
        );
        rangeDown_BigDecimal_helper(
                2,
                1,
                "1E+12",
                "[999999999999.98700, 999999999999.99999999900, 999999999997.79, 999999999999.9000, 999999999994.00," +
                " 999999999999.9700, 1E+12, 999999999999.90, 999999999994.0, 999999999999.9900, 1.0E+12," +
                " 999999999995, 999999999969, 999999999999, 1E+12, 1E+12, 999999999999.90000, 999999999999.9999," +
                " 999999999998.5, 999999999999.0]",
                "{1E+12=143281, 1.0E+12=71426, 999999999999.9=53586, 999999999999=47215, 1.00E+12=35657," +
                " 999999999999.90=26715, 999999999999.99=26478, 999999999999.0=23935, 1.000E+12=17786," +
                " 999999999998=15972}",
                9.999999829210764E11,
                35.16661899992838,
                4.718633999998911
        );
        rangeDown_BigDecimal_helper(
                5,
                3,
                "1E+12",
                "[999999999999.999979000000000, 999999999999.999987000000, 999999999923.426000000000," +
                " 999999999999.8000000, 999999999999.99999750, 999999999999.99996610000, 1.0E+12, 999999999998," +
                " 999999999999.99999400000, 999999999999.9938, 999999999999.99999, 999999999999.000000000," +
                " 999999999991.0000, 999999999999, 999999999999.9998, 999999999999.700000000000, 999999999998.8," +
                " 999999999998.507000, 999999999994, 999999999995.0000]",
                "{1E+12=19722, 1.0E+12=15111, 1.00E+12=11254, 999999999999.9=9026, 999999999999=8481," +
                " 1.000E+12=8455, 999999999999.90=6731, 999999999999.99=6680, 1.0000E+12=6314, 999999999999.0=6282}",
                -3.5907903470586306E14,
                56.71322200003782,
                6.3694550000059476
        );
        rangeDown_BigDecimal_helper(
                32,
                8,
                "1E+12",
                "[999999941243.610208562000000000, 999983654193.1153100000000000000000000000000000," +
                " 999999999999.999999999999866340000000000000, 999999999999.9999999994000000, 999999999992.219000," +
                " 999999798164.0, 999999999999.995630000000000, 999999998646.60200, 67017947622.80000000," +
                " 999999999999.99999999999999999999999999999787733013196364534800000000000, 999999999976.479532," +
                " -271169139986910.73520000, 999999999999.99999974805244910149600000000000000000000," +
                " 999999999999.99999999999999170, 999999999999.99999999999999999928800, 497490799215.60000," +
                " 999999999999.989790660000000, 999999999985.00, 999999999999.99999000000, 999999999999.95000000]",
                "{1E+12=712, 1.0E+12=667, 1.00E+12=596, 1.000E+12=555, 1.0000E+12=443, 1.00000E+12=426," +
                " 1.000000E+12=399, 999999999999.9=351, 999999999999=346, 999999999999.90=337}",
                -1.3351838891443716E131,
                98.05552899996057,
                15.956677999996389
        );
        rangeDown_BigDecimal_fail_helper(1, 1, "0");
        rangeDown_BigDecimal_fail_helper(2, 0, "0");
    }

    private static void nextFromRange_BigDecimal_BigDecimal_helper(
            int scale,
            int secondaryScale,
            @NotNull String a,
            @NotNull String b,
            @NotNull String output
    ) {
        aeq(
                P.withScale(scale).withSecondaryScale(secondaryScale)
                        .nextFromRange(Readers.readBigDecimal(a).get(), Readers.readBigDecimal(b).get()),
                output
        );
        P.reset();
    }

    private static void nextFromRange_BigDecimal_BigDecimal_fail_helper(
            int scale,
            int secondaryScale,
            @NotNull String a,
            @NotNull String b
    ) {
        try {
            P.withScale(scale).withSecondaryScale(secondaryScale)
                    .nextFromRange(Readers.readBigDecimal(a).get(), Readers.readBigDecimal(b).get());
            fail();
        } catch (IllegalStateException | IllegalArgumentException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testNextFromRange_BigDecimal_BigDecimal() {
        nextFromRange_BigDecimal_BigDecimal_helper(1, 1, "0", "1", "0.835");
        nextFromRange_BigDecimal_BigDecimal_helper(5, 3, "0", "1", "0.34025400000000");
        nextFromRange_BigDecimal_BigDecimal_helper(32, 8, "0", "1", "0.785693340317779469094037902976600000");
        nextFromRange_BigDecimal_BigDecimal_helper(1, 1, "0", "3", "2");
        nextFromRange_BigDecimal_BigDecimal_helper(5, 3, "0", "3", "0.847400");
        nextFromRange_BigDecimal_BigDecimal_helper(32, 8, "0", "3", "2.093200000000");
        nextFromRange_BigDecimal_BigDecimal_helper(1, 1, "0", "1E+6", "8.35E+5");
        nextFromRange_BigDecimal_BigDecimal_helper(5, 3, "0", "1E+6", "340254.00000000");
        nextFromRange_BigDecimal_BigDecimal_helper(32, 8, "0", "1E+6", "785693.340317779469094037902976600000");
        nextFromRange_BigDecimal_BigDecimal_helper(1, 1, "0", "0.000001", "8.35E-7");
        nextFromRange_BigDecimal_BigDecimal_helper(5, 3, "0", "0.000001", "3.4025400000000E-7");
        nextFromRange_BigDecimal_BigDecimal_helper(32, 8, "0", "0.000001", "7.85693340317779469094037902976600000E-7");
        nextFromRange_BigDecimal_BigDecimal_helper(1, 1, "1", "3", "3");
        nextFromRange_BigDecimal_BigDecimal_helper(5, 3, "1", "3", "1.847400");
        nextFromRange_BigDecimal_BigDecimal_helper(32, 8, "1", "3", "2.6422");
        nextFromRange_BigDecimal_BigDecimal_helper(1, 1, "1", "1E+6", "835001");
        nextFromRange_BigDecimal_BigDecimal_helper(5, 3, "1", "1E+6", "340255.00000000");
        nextFromRange_BigDecimal_BigDecimal_helper(32, 8, "1", "1E+6", "785694.340317779469094037902976600000");
        nextFromRange_BigDecimal_BigDecimal_helper(1, 1, "-1", "0", "-0.165");
        nextFromRange_BigDecimal_BigDecimal_helper(5, 3, "-1", "0", "-0.65974600000000");
        nextFromRange_BigDecimal_BigDecimal_helper(32, 8, "-1", "0", "-0.214306659682220530905962097023400000");
        nextFromRange_BigDecimal_BigDecimal_helper(1, 1, "-3", "0", "-1");
        nextFromRange_BigDecimal_BigDecimal_helper(5, 3, "-3", "0", "-2.152600");
        nextFromRange_BigDecimal_BigDecimal_helper(32, 8, "-3", "0", "-0.906800000000");
        nextFromRange_BigDecimal_BigDecimal_helper(1, 1, "-1E+6", "0", "-1.65E+5");
        nextFromRange_BigDecimal_BigDecimal_helper(5, 3, "-1E+6", "0", "-659746.00000000");
        nextFromRange_BigDecimal_BigDecimal_helper(32, 8, "-1E+6", "0", "-214306.659682220530905962097023400000");
        nextFromRange_BigDecimal_BigDecimal_helper(1, 1, "-0.000001", "0", "-1.65E-7");
        nextFromRange_BigDecimal_BigDecimal_helper(5, 3, "-0.000001", "0", "-6.5974600000000E-7");
        nextFromRange_BigDecimal_BigDecimal_helper(32, 8, "-0.000001", "0",
                "-2.14306659682220530905962097023400000E-7");
        nextFromRange_BigDecimal_BigDecimal_helper(1, 1, "-3", "-1", "-1");
        nextFromRange_BigDecimal_BigDecimal_helper(5, 3, "-3", "-1", "-2.152600");
        nextFromRange_BigDecimal_BigDecimal_helper(32, 8, "-3", "-1", "-1.3578");
        nextFromRange_BigDecimal_BigDecimal_helper(1, 1, "-1E+6", "-1", "-1.65E+5");
        nextFromRange_BigDecimal_BigDecimal_helper(5, 3, "-1E+6", "-1", "-659746.00000000");
        nextFromRange_BigDecimal_BigDecimal_helper(32, 8, "-1E+6", "-1", "-214306.659682220530905962097023400000");
        nextFromRange_BigDecimal_BigDecimal_helper(1, 1, "100", "101", "100.835");
        nextFromRange_BigDecimal_BigDecimal_helper(5, 3, "100", "101", "100.34025400000000");
        nextFromRange_BigDecimal_BigDecimal_helper(32, 8, "100", "101", "100.785693340317779469094037902976600000");
        nextFromRange_BigDecimal_BigDecimal_helper(1, 1, "2.7183", "3.1416", "2.9183");
        nextFromRange_BigDecimal_BigDecimal_helper(5, 3, "2.7183", "3.1416", "3.05855400000000");
        nextFromRange_BigDecimal_BigDecimal_helper(32, 8, "2.7183", "3.1416", "2.9276200000000");
        nextFromRange_BigDecimal_BigDecimal_helper(1, 1, "-3.1416", "2.7183", "-1.1416");
        nextFromRange_BigDecimal_BigDecimal_helper(5, 3, "-3.1416", "2.7183", "0.2609400000000");
        nextFromRange_BigDecimal_BigDecimal_helper(32, 8, "-3.1416", "2.7183", "-1.048400000000");
        nextFromRange_BigDecimal_BigDecimal_helper(1, 1, "0", "0", "0.000");
        nextFromRange_BigDecimal_BigDecimal_helper(5, 3, "0", "0", "0.000000");
        nextFromRange_BigDecimal_BigDecimal_helper(32, 8, "0", "0", "0E-31");
        nextFromRange_BigDecimal_BigDecimal_helper(1, 1, "0", "0.0", "0.000");
        nextFromRange_BigDecimal_BigDecimal_helper(5, 3, "0", "0.0", "0.000000");
        nextFromRange_BigDecimal_BigDecimal_helper(32, 8, "0", "0.0", "0E-31");
        nextFromRange_BigDecimal_BigDecimal_helper(1, 1, "0.0", "0", "0.000");
        nextFromRange_BigDecimal_BigDecimal_helper(5, 3, "0.0", "0", "0.000000");
        nextFromRange_BigDecimal_BigDecimal_helper(32, 8, "0.0", "0", "0E-31");
        nextFromRange_BigDecimal_BigDecimal_helper(1, 1, "0.0", "0.0", "0.000");
        nextFromRange_BigDecimal_BigDecimal_helper(5, 3, "0.0", "0.0", "0.000000");
        nextFromRange_BigDecimal_BigDecimal_helper(32, 8, "0.0", "0.0", "0E-31");
        nextFromRange_BigDecimal_BigDecimal_helper(1, 1, "1", "1", "1.000");
        nextFromRange_BigDecimal_BigDecimal_helper(5, 3, "1", "1", "1.000000");
        nextFromRange_BigDecimal_BigDecimal_helper(32, 8, "1", "1", "1.0000000000000000000000000000000");
        nextFromRange_BigDecimal_BigDecimal_fail_helper(1, 1, "5", "3");
        nextFromRange_BigDecimal_BigDecimal_fail_helper(0, 1, "0", "1");
        nextFromRange_BigDecimal_BigDecimal_fail_helper(1, 0, "0", "1");
    }

    private static void nextFromRangeUpCanonical_BigDecimal_helper(
            int scale,
            int secondaryScale,
            @NotNull String a,
            @NotNull String output
    ) {
        aeq(
                P.withScale(scale).withSecondaryScale(secondaryScale)
                        .nextFromRangeUpCanonical(Readers.readBigDecimal(a).get()),
                output
        );
        P.reset();
    }

    private static void nextFromRangeUpCanonical_BigDecimal_fail_helper(
            int scale,
            int secondaryScale,
            @NotNull String a
    ) {
        try {
            P.withScale(scale).withSecondaryScale(secondaryScale)
                    .nextFromRangeUpCanonical(Readers.readBigDecimal(a).get());
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    private static void range_BigDecimal_BigDecimal_helper(
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
                        .range(Readers.readBigDecimal(a).get(), Readers.readBigDecimal(b).get()),
                output,
                topSampleCount,
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
                "[0.835, 0.3668627520, 0.930, 0.028, 0.64, 0.080, 0.0, 0.0, 0.60, 0E+1, 0.90, 0, 0, 0, 1.000," +
                " 0.1471, 1.0, 1, 0.37949900, 0.2]",
                "{1=125223, 0=124880, 1.0=61822, 1.00=31428, 0.0=31408, 0E+1=31268, 0.00=15688, 1.000=15604," +
                " 0E+2=15584, 0.4=14225}",
                0.49974181014590885,
                5.59269400000979,
                1.9986289999875098
        );
        range_BigDecimal_BigDecimal_helper(
                5,
                3,
                "0",
                "1",
                "[0.34025400000000, 0.9300620000, 0.82700000000, 0.0, 0.6, 0.144266000000, 0.1, 0.90, 0.000000," +
                " 0.7450000000, 0.0171211820157700000000000, 0.314420, 0E-12, 0.0610371000000, 0.39245, 0.522600000," +
                " 0, 0.05164631, 0.3283, 0.0870000]",
                "{1=31394, 0=31302, 1.0=23292, 1.00=17533, 1.000=12994, 0E+1=11709, 0.0=11702, 1.0000=9880," +
                " 0.00=8832, 0E+2=8770}",
                0.49994173214022963,
                18.1187839999679,
                6.007439000007458
        );
        range_BigDecimal_BigDecimal_helper(
                32,
                8,
                "0",
                "1",
                "[0.785693340317779469094037902976600000, 0.69391141900000," +
                " 0.685943636832238796031981604360000000000000, 0.40000, 1.00000000000000000," +
                " 0.134524124471900000000, 0.5166270, 0.2150, 0.540000, 0.3357467208000000, 0.94100000, 0.2130," +
                " 0.3000000, 0.3762251310192458010260689857798878599420706722260, 0.41457400000, 0.000," +
                " 0.15400000000000000000000, 0.7078719408522190373200000000000, 0.400000000, 1.00000000000000]",
                "{0=6192, 1=6029, 1.0=5544, 1.00=4771, 1.000=4340, 1.0000=3863, 1.00000=3476, 1.000000=3070," +
                " 0.0=2772, 1.0000000=2738}",
                0.500371540682368,
                50.84739499999407,
                15.985023999996871
        );
        range_BigDecimal_BigDecimal_helper(
                1,
                1,
                "0",
                "3",
                "[2, 2.9, 3.00, 0.0, 0.0, 3.0, 0, 0, 0, 0, 0E+2, 2, 0.300, 0, 0, 0.3000, 0, 0.960, 1, 0E+3]",
                "{0=305715, 0.0=76846, 0E+1=76484, 0.00=38300, 0E+2=38001, 2=34118, 3=34056, 1=33807, 0E+3=19299," +
                " 0.000=19277}",
                0.6835993574261239,
                2.9379489999931696,
                1.3677679999925896
        );
        range_BigDecimal_BigDecimal_helper(
                5,
                3,
                "0",
                "3",
                "[0.847400, 1.3194000000, 1, 1.230, 0E-12, 0.610371000000, 0E+3, 2.8254, 0.5164631, 0.7, 0.3, 0.1," +
                " 0.9953, 1.0000, 0, 3.00, 2.3956700000, 2.0100000, 0.70765000, 1.0111768]",
                "{0=87823, 0.0=33102, 0E+1=32590, 0E+2=24592, 0.00=24481, 0.000=18814, 0E+3=18400, 3=14624, 1=14516," +
                " 2=14502}",
                1.062113959074216,
                13.332859999998846,
                4.897865000002074
        );
        range_BigDecimal_BigDecimal_helper(
                32,
                8,
                "0",
                "3",
                "[2.093200000000, 2.08332953832284170220810772700000000000, 0.08400000000000000000," +
                " 1.34524124471900000000, 3.0, 2.59340000000000, 1.811578800, 2.52790400, 0E+7, 2.300000, 0.000," +
                " 1.5400000000000000000000, 2.5272187617000000000, 2.8400000000000000000000000000000," +
                " 0.223823000000, 1.5000000, 2.55000000000000000000000000, 2.687062506957968000000000000000000000," +
                " 2.22700000000000000000000, 0E+35]",
                "{0=18846, 0.0=8430, 0E+1=8388, 0E+2=7545, 0.00=7535, 0E+3=6746, 0.000=6589, 0.0000=5894, 0E+4=5884," +
                " 0E+5=5348}",
                1.2962726292409759,
                44.45600600002838,
                14.560672999996417
        );
        range_BigDecimal_BigDecimal_helper(
                1,
                1,
                "0",
                "1E+6",
                "[8.35E+5, 366862.7520, 9.30E+5, 2.8E+4, 6.4E+5, 8.0E+4, 0.0, 0.0, 6.0E+5, 0E+1, 9.0E+5, 0, 0, 0," +
                " 1.000E+6, 1.471E+5, 1.0E+6, 1E+6, 379499.00, 2E+5]",
                "{1E+6=125223, 0=124880, 1.0E+6=61822, 1.00E+6=31428, 0.0=31408, 0E+1=31268, 0.00=15688," +
                " 1.000E+6=15604, 0E+2=15584, 4E+5=14225}",
                499741.81014750147,
                5.59269400000979,
                3.1484789999806573
        );
        range_BigDecimal_BigDecimal_helper(
                5,
                3,
                "0",
                "1E+6",
                "[340254.00000000, 930062.0000, 827000.00000, 0.0, 6E+5, 144266.000000, 1E+5, 9.0E+5, 0.000000," +
                " 745000.0000, 17121.1820157700000000000, 314420, 0E-12, 61037.1000000, 3.9245E+5, 522600.000, 0," +
                " 51646.31, 3.283E+5, 87000.0]",
                "{1E+6=31394, 0=31302, 1.0E+6=23292, 1.00E+6=17533, 1.000E+6=12994, 0E+1=11709, 0.0=11702," +
                " 1.0000E+6=9880, 0.00=8832, 0E+2=8770}",
                499941.7321406794,
                18.1187839999679,
                3.6066369999861645
        );
        range_BigDecimal_BigDecimal_helper(
                32,
                8,
                "0",
                "1E+6",
                "[785693.340317779469094037902976600000, 693911.41900000," +
                " 685943.636832238796031981604360000000000000, 4.0000E+5, 1000000.00000000000," +
                " 134524.124471900000000, 516627.0, 2.150E+5, 540000, 335746.7208000000, 941000.00, 2.130E+5," +
                " 300000.0, 376225.1310192458010260689857798878599420706722260, 414574.00000, 0.000," +
                " 154000.00000000000000000, 707871.9408522190373200000000000, 400000.000, 1000000.00000000]",
                "{0=6192, 1E+6=6029, 1.0E+6=5544, 1.00E+6=4771, 1.000E+6=4340, 1.0000E+6=3863, 1.00000E+6=3476," +
                " 1000000=3070, 0.0=2772, 1000000.0=2738}",
                500371.54068251094,
                50.84739499999407,
                11.149427999999784
        );
        range_BigDecimal_BigDecimal_helper(
                1,
                1,
                "0",
                "0.000001",
                "[8.35E-7, 3.668627520E-7, 9.30E-7, 2.8E-8, 6.4E-7, 8.0E-8, 0.0, 0.0, 6.0E-7, 0E+1, 9.0E-7, 0, 0, 0," +
                " 0.000001000, 1.471E-7, 0.0000010, 0.000001, 3.7949900E-7, 2E-7]",
                "{0.000001=125223, 0=124880, 0.0000010=61822, 0.00000100=31428, 0.0=31408, 0E+1=31268, 0.00=15688," +
                " 0.000001000=15604, 0E+2=15584, 4E-7=14225}",
                4.997418101445085E-7,
                5.59269400000979,
                6.496949000021773
        );
        range_BigDecimal_BigDecimal_helper(
                5,
                3,
                "0",
                "0.000001",
                "[3.4025400000000E-7, 9.300620000E-7, 8.2700000000E-7, 0.0, 6E-7, 1.44266000000E-7, 1E-7, 9.0E-7," +
                " 0.000000, 7.450000000E-7, 1.71211820157700000000000E-8, 3.14420E-7, 0E-12, 6.10371000000E-8," +
                " 3.9245E-7, 5.22600000E-7, 0, 5.164631E-8, 3.283E-7, 8.70000E-8]",
                "{0.000001=31394, 0=31302, 0.0000010=23292, 0.00000100=17533, 0.000001000=12994, 0E+1=11709," +
                " 0.0=11702, 0.0000010000=9880, 0.00=8832, 0E+2=8770}",
                4.999417321390705E-7,
                18.1187839999679,
                11.257294999992666
        );
        range_BigDecimal_BigDecimal_helper(
                32,
                8,
                "0",
                "0.000001",
                "[7.85693340317779469094037902976600000E-7, 6.9391141900000E-7," +
                " 6.85943636832238796031981604360000000000000E-7, 4.0000E-7, 0.00000100000000000000000," +
                " 1.34524124471900000000E-7, 5.166270E-7, 2.150E-7, 5.40000E-7, 3.357467208000000E-7, 9.4100000E-7," +
                " 2.130E-7, 3.000000E-7, 3.762251310192458010260689857798878599420706722260E-7, 4.1457400000E-7," +
                " 0.000, 1.5400000000000000000000E-7, 7.078719408522190373200000000000E-7, 4.00000000E-7," +
                " 0.00000100000000000000]",
                "{0=6192, 0.000001=6029, 0.0000010=5544, 0.00000100=4771, 0.000001000=4340, 0.0000010000=3863," +
                " 0.00000100000=3476, 0.000001000000=3070, 0.0=2772, 0.0000010000000=2738}",
                5.003715406817602E-7,
                50.84739499999407,
                21.652756000007802
        );
        range_BigDecimal_BigDecimal_helper(
                1,
                1,
                "1",
                "3",
                "[3, 1.0, 1.60, 1.0, 1, 2, 1, 1, 1.00, 3, 1.300, 1, 1, 1.00, 2.90, 1, 1.0000, 1.0, 2, 1.000]",
                "{1=350765, 1.0=176392, 1.00=87798, 1.000=43973, 3=38937, 2=38840, 1.0000=22084, 3.0=19540," +
                " 2.0=19515, 1.00000=10909}",
                1.3749136388746568,
                5.285133000014813,
                1.2841969999935254
        );
        range_BigDecimal_BigDecimal_helper(
                5,
                3,
                "1",
                "3",
                "[1.847400, 2.3194000000, 2, 2.230, 1.000000000000, 1.000000000, 1.7, 1.3, 1.1, 1.9953, 2.0000, 1," +
                " 2.57500, 2.0111768, 1.00, 1.00, 2.100000, 2.1810734569620763550000000, 2.0, 1.0]",
                "{1=111378, 1.0=84210, 1.00=62707, 1.000=47671, 1.0000=35170, 1.00000=26580, 1.000000=20029," +
                " 3=18714, 2=18547, 1.0000000=14959}",
                1.6272101053585095,
                16.43551499998298,
                4.613846999999211
        );
        range_BigDecimal_BigDecimal_helper(
                32,
                8,
                "1",
                "3",
                "[2.6422, 1.09114620894935190000000000000, 1.7076500, 1.0000000, 1.819000, 1.223823000000," +
                " 2.5000000, 2.48873020000000000000000, 1.822733391300000000000, 1.96743000000000000000000000," +
                " 1.00000000000000000000000000000000000, 1.762, 2.640801332000000000000, 1.000000000000, 3.00, 1," +
                " 1.3020000000000000, 1.00000000, 2.248000000000000, 3.00000000]",
                "{1=26218, 1.0=23251, 1.00=20651, 1.000=18390, 1.0000=16409, 1.00000=14597, 1.000000=12734," +
                " 1.0000000=11487, 1.00000000=10189, 1.000000000=9020}",
                1.8119114824740903,
                47.84639700000742,
                14.026318999996679
        );
        range_BigDecimal_BigDecimal_helper(
                1,
                1,
                "1",
                "1E+6",
                "[835001, 366863.7520, 930001.0, 28001, 640001, 80001.0, 1.0, 500001.0, 60001.0, 1.0, 1, 100001, 1," +
                " 1, 349001.000, 1.0, 100001.0, 379500, 200001, 636001.0]",
                "{1=166190, 1.0=83763, 1.00=41701, 1.000=20927, 100001=18699, 400001=18693, 600001=18690," +
                " 200001=18603, 900001=18550, 300001=18477}",
                333040.3334871308,
                16.332860999928386,
                1.0213529999973245
        );
        range_BigDecimal_BigDecimal_helper(
                5,
                3,
                "1",
                "1E+6",
                "[340255.00000000, 930063.0000, 827001.00000000, 1.0, 380001, 144267.000000, 100001, 900001.0," +
                " 1.000000, 147101.00000, 17122.1820157700000000000, 314421.0, 1.000000000000, 441347.380," +
                " 117473.0000, 400001.00, 1.0000, 1, 907349.6280, 1.000]",
                "{1=35884, 1.0=26965, 1.00=20035, 1.000=15153, 1.0000=11267, 1.00000=8378, 1.000000=6330," +
                " 900001=6025, 800001=5989, 400001=5970}",
                428881.9766954399,
                28.3818839999616,
                3.61354699999148
        );
        range_BigDecimal_BigDecimal_helper(
                32,
                8,
                "1",
                "1E+6",
                "[785694.340317779469094037902976600000, 693912.41900000," +
                " 685944.636832238796031981604360000000000000, 400001.0000, 9115.620894935190000000000000," +
                " 875969.29700000, 742001.000, 1.0, 540001.0000, 335747.7208000000, 941001.00000, 213001.0," +
                " 300001.000000, 376226.1310192458010260689857798878599420706722260, 414575.00000, 1.000," +
                " 468801.000000000000000000, 707872.9408522190373200000000000, 400001.00000000," +
                " 647965.03494407000000000000000000000]",
                "{1=6569, 1.0=5837, 1.00=5145, 1.000=4615, 1.0000=4107, 1.00000=3646, 1.000000=3278, 1.0000000=2781," +
                " 1.00000000=2510, 1.000000000=2194}",
                470615.7681893622,
                58.28940599998806,
                12.153703999997873
        );
        range_BigDecimal_BigDecimal_helper(
                1,
                1,
                "-1",
                "0",
                "[-0.165, -0.6331372480, -0.070, -0.972, -0.36, -0.920, -1.0, -0.50, -0.940, -1.0, 0, 0, -1, -1, -1," +
                " 0, -0.651000, -1.0, 0E+1, -0.600000]",
                "{0=124925, -1=124757, -1.0=62776, 0.0=31281, -1.00=31098, 0E+1=31007, -1.000=15681, 0E+2=15673," +
                " 0.00=15619, -0.6=14124}",
                -0.5003224338184838,
                5.6016160000096455,
                2.000491999987506
        );
        range_BigDecimal_BigDecimal_helper(
                5,
                3,
                "-1",
                "0",
                "[-0.65974600000000, -0.0699380000, -0.17300000000, -1.0, -0.62, -0.855734000000, -0.9, -0.10," +
                " -1.000000, -0.852900000, -0.9828788179842300000000000, -0.685580, -1.000000000000, -0.558653620," +
                " -0.8825280000, -0.600, -1.0000, -1, -0.0926513720, -1.000]",
                "{0=31322, -1=31174, -1.0=23762, -1.00=17797, -1.000=13154, 0.0=11623, 0E+1=11480, -1.0000=9911," +
                " 0E+2=8818, 0.00=8746}",
                -0.49979485012240055,
                18.1147109999674,
                6.007397000007255
        );
        range_BigDecimal_BigDecimal_helper(
                32,
                8,
                "-1",
                "0",
                "[-0.214306659682220530905962097023400000, -0.30608858100000," +
                " -0.314056363167761203968018395640000000000000, -0.60000, 0E+17, -0.865475875528100000000," +
                " -0.4833730, -0.7850, -0.460000, -0.6642532792000000, -0.05900000, -0.7870, -0.7000000," +
                " -0.6237748689807541989739310142201121400579293277740, -0.58542600000, -1.000," +
                " -0.5312000000000000000000, -0.2921280591477809626800000000000, -0.600000000, 0E+14]",
                "{-1=6211, 0=6042, -1.0=5431, -1.00=4850, -1.000=4334, -1.0000=3899, -1.00000=3424, -1.000000=3080," +
                " 0E+1=2794, 0.0=2775}",
                -0.4997172674593811,
                50.82451099999267,
                15.983411999996747
        );
        range_BigDecimal_BigDecimal_helper(
                1,
                1,
                "-3",
                "0",
                "[-1, -0.1, 0E+2, -3.0, -2.40, -3.0, -3, -2, -3, -3, -3.00, -1, -2.700, -3, -3, -3.00, -1.10, -3," +
                " -3.0000, -3.0]",
                "{-3=305718, -3.0=153639, -3.00=76393, -3.000=38257, 0=34112, -2=33897, -1=33879, -3.0000=19119," +
                " -1.0=17038, -2.0=16975}",
                -2.316612149630881,
                5.819886999996417,
                1.3681939999925739
        );
        range_BigDecimal_BigDecimal_helper(
                5,
                3,
                "-3",
                "0",
                "[-2.152600, -1.6806000000, -2, -1.770, -3.000000000000, -0.1746, -2.4835369, -2.3, -2.7, -2.9," +
                " -2.0047, -2.0000, -3, -1.42500, -1.9888232, -3.00, -3.00, -1.900000, -1.8189265430379236450000000," +
                " -2.0]",
                "{-3=87536, -3.0=65795, -3.00=48873, -3.000=37412, -3.0000=27657, -3.00000=20698, -3.000000=15713," +
                " 0=14678, -1=14509, -2=14507}",
                -1.938844730320883,
                16.847756000016705,
                4.902132000002184
        );
        range_BigDecimal_BigDecimal_helper(
                32,
                8,
                "-3",
                "0",
                "[-0.906800000000, -0.91667046167715829779189227300000000000, -2.91600000000000000000," +
                " -1.65475875528100000000, 0.0, -2.2923500, -0.47209600, -3.0000000, -0.864200000, -3.000," +
                " -0.7402489985431079300000000000000000, -1.343761912376224000000000," +
                " -0.1600000000000000000000000000000, -2.776177000000, -1.5000000, -0.45000000000000000000000000," +
                " -0.312937493042032000000000000000000000, -0.77300000000000000000000," +
                " -3.00000000000000000000000000000000000, -2.238]",
                "{-3=18852, -3.0=16881, -3.00=15082, -3.000=13343, -3.0000=11750, -3.00000=10563, -3.000000=9300," +
                " -3.0000000=8363, -3.00000000=7429, -3.000000000=6566}",
                -1.7039183609959918,
                48.36847900004126,
                14.560999999996431
        );
        range_BigDecimal_BigDecimal_helper(
                1,
                1,
                "-1E+6",
                "0",
                "[-1.65E+5, -633137.2480, -7.0E+4, -9.72E+5, -3.6E+5, -9.20E+5, -1.0E+6, -5.0E+5, -9.40E+5, -1.0E+6," +
                " 0, 0, -1E+6, -1E+6, -1E+6, 0, -651000, -1.0E+6, 0E+1, -600000]",
                "{0=124925, -1E+6=124757, -1.0E+6=62776, 0.0=31281, -1.00E+6=31098, 0E+1=31007, -1.000E+6=15681," +
                " 0E+2=15673, 0.00=15619, -6E+5=14124}",
                -500322.43382010004,
                5.6016160000096455,
                3.1490699999805405
        );
        range_BigDecimal_BigDecimal_helper(
                5,
                3,
                "-1E+6",
                "0",
                "[-659746.00000000, -69938.0000, -173000.00000, -1.0E+6, -6.2E+5, -855734.000000, -9E+5, -1.0E+5," +
                " -1000000, -852900.000, -982878.8179842300000000000, -685580, -1000000.000000, -558653.620," +
                " -882528.0000, -6.00E+5, -1.0000E+6, -1E+6, -92651.3720, -1.000E+6]",
                "{0=31322, -1E+6=31174, -1.0E+6=23762, -1.00E+6=17797, -1.000E+6=13154, 0.0=11623, 0E+1=11480," +
                " -1.0000E+6=9911, 0E+2=8818, 0.00=8746}",
                -499794.8501228811,
                18.1147109999674,
                3.608310999986109
        );
        range_BigDecimal_BigDecimal_helper(
                32,
                8,
                "-1E+6",
                "0",
                "[-214306.659682220530905962097023400000, -306088.58100000," +
                " -314056.363167761203968018395640000000000000, -6.0000E+5, 0E+17, -865475.875528100000000," +
                " -483373.0, -7.850E+5, -460000, -664253.2792000000, -59000.00, -7.870E+5, -700000.0," +
                " -623774.8689807541989739310142201121400579293277740, -585426.00000, -1.000E+6," +
                " -531200.0000000000000000, -292128.0591477809626800000000000, -600000.000, 0E+14]",
                "{-1E+6=6211, 0=6042, -1.0E+6=5431, -1.00E+6=4850, -1.000E+6=4334, -1.0000E+6=3899," +
                " -1.00000E+6=3424, -1000000=3080, 0E+1=2794, 0.0=2775}",
                -499717.26745949313,
                50.82451099999267,
                11.151406000000007
        );
        range_BigDecimal_BigDecimal_helper(
                1,
                1,
                "-0.000001",
                "0",
                "[-1.65E-7, -6.331372480E-7, -7.0E-8, -9.72E-7, -3.6E-7, -9.20E-7, -0.0000010, -5.0E-7, -9.40E-7," +
                " -0.0000010, 0, 0, -0.000001, -0.000001, -0.000001, 0, -6.51000E-7, -0.0000010, 0E+1, -6.00000E-7]",
                "{0=124925, -0.000001=124757, -0.0000010=62776, 0.0=31281, -0.00000100=31098, 0E+1=31007," +
                " -0.000001000=15681, 0E+2=15673, 0.00=15619, -6E-7=14124}",
                -5.003224338171066E-7,
                5.6016160000096455,
                6.5015840000220155
        );
        range_BigDecimal_BigDecimal_helper(
                5,
                3,
                "-0.000001",
                "0",
                "[-6.5974600000000E-7, -6.99380000E-8, -1.7300000000E-7, -0.0000010, -6.2E-7, -8.55734000000E-7," +
                " -9E-7, -1.0E-7, -0.000001000000, -8.52900000E-7, -9.828788179842300000000000E-7, -6.85580E-7," +
                " -0.000001000000000000, -5.58653620E-7, -8.825280000E-7, -6.00E-7, -0.0000010000, -0.000001," +
                " -9.26513720E-8, -0.000001000]",
                "{0=31322, -0.000001=31174, -0.0000010=23762, -0.00000100=17797, -0.000001000=13154, 0.0=11623," +
                " 0E+1=11480, -0.0000010000=9911, 0E+2=8818, 0.00=8746}",
                -4.99794850121267E-7,
                18.1147109999674,
                11.25785899999279
        );
        range_BigDecimal_BigDecimal_helper(
                32,
                8,
                "-0.000001",
                "0",
                "[-2.14306659682220530905962097023400000E-7, -3.0608858100000E-7," +
                " -3.14056363167761203968018395640000000000000E-7, -6.0000E-7, 0E+17, -8.65475875528100000000E-7," +
                " -4.833730E-7, -7.850E-7, -4.60000E-7, -6.642532792000000E-7, -5.900000E-8, -7.870E-7," +
                " -7.000000E-7, -6.237748689807541989739310142201121400579293277740E-7, -5.8542600000E-7," +
                " -0.000001000, -5.312000000000000000000E-7, -2.921280591477809626800000000000E-7, -6.00000000E-7," +
                " 0E+14]",
                "{-0.000001=6211, 0=6042, -0.0000010=5431, -0.00000100=4850, -0.000001000=4334, -0.0000010000=3899," +
                " -0.00000100000=3424, -0.000001000000=3080, 0E+1=2794, 0.0=2775}",
                -4.997172674587391E-7,
                50.82451099999267,
                21.648954000007993
        );
        range_BigDecimal_BigDecimal_helper(
                1,
                1,
                "-3",
                "-1",
                "[-1, -3.0, -2.40, -3.0, -3, -2, -3, -3, -3.00, -1, -2.700, -3, -3, -3.00, -1.10, -3, -3.0000, -3.0," +
                " -2, -3.000]",
                "{-3=350765, -3.0=176392, -3.00=87798, -3.000=43973, -1=38937, -2=38840, -3.0000=22084, -1.0=19540," +
                " -2.0=19515, -3.00000=10909}",
                -2.6250863610944726,
                6.044352999999744,
                1.2841969999935254
        );
        range_BigDecimal_BigDecimal_helper(
                5,
                3,
                "-3",
                "-1",
                "[-2.152600, -1.6806000000, -2, -1.770, -3.000000000000, -3.000000000, -2.3, -2.7, -2.9, -2.0047," +
                " -2.0000, -3, -1.42500, -1.9888232, -3.00, -3.00, -1.900000, -1.8189265430379236450000000, -2.0," +
                " -3.0]",
                "{-3=111378, -3.0=84210, -3.00=62707, -3.000=47671, -3.0000=35170, -3.00000=26580, -3.000000=20029," +
                " -1=18714, -2=18547, -3.0000000=14959}",
                -2.372789894616791,
                16.967438000029702,
                4.613846999999211
        );
        range_BigDecimal_BigDecimal_helper(
                32,
                8,
                "-3",
                "-1",
                "[-1.3578, -2.90885379105064810000000000000, -2.2923500, -3.0000000, -2.181000, -2.776177000000," +
                " -1.5000000, -1.51126980000000000000000, -2.177266608700000000000, -2.03257000000000000000000000," +
                " -3.00000000000000000000000000000000000, -2.238, -1.359198668000000000000, -3.000000000000, -1.00," +
                " -3, -2.6980000000000000, -3.00000000, -1.752000000000000, -1.00000000]",
                "{-3=26218, -3.0=23251, -3.00=20651, -3.000=18390, -3.0000=16409, -3.00000=14597, -3.000000=12734," +
                " -3.0000000=11487, -3.00000000=10189, -3.000000000=9020}",
                -2.1880885175115052,
                48.136073000057394,
                14.026318999996679
        );
        range_BigDecimal_BigDecimal_helper(
                1,
                1,
                "-1E+6",
                "-1",
                "[-1.65E+5, -633137.2480, -7.0E+4, -9.72E+5, -3.6E+5, -9.20E+5, -1.0E+6, -5.0E+5, -9.40E+5, -1.0E+6," +
                " -1E+6, -9E+5, -1E+6, -1E+6, -651000, -1.0E+6, -9.0E+5, -620501.00, -8E+5, -3.640E+5]",
                "{-1E+6=166190, -1.0E+6=83763, -1.00E+6=41701, -1.000E+6=20927, -9E+5=18699, -6E+5=18693," +
                " -4E+5=18690, -8E+5=18603, -1E+5=18550, -7E+5=18477}",
                -666960.6665137309,
                7.471091000009681,
                3.863571999971566
        );
        range_BigDecimal_BigDecimal_helper(
                5,
                3,
                "-1E+6",
                "-1",
                "[-659746.00000000, -69938.0000, -173000.00000, -1.0E+6, -6.2E+5, -855734.000000, -9E+5, -1.0E+5," +
                " -1000000, -852900.000, -982878.8179842300000000000, -685580, -1000000.000000, -558653.620," +
                " -882528.0000, -6.00E+5, -1.0000E+6, -1E+6, -92651.3720, -1.000E+6]",
                "{-1E+6=35884, -1.0E+6=26965, -1.00E+6=20035, -1.000E+6=15153, -1.0000E+6=11267, -1.00000E+6=8378," +
                " -1000000=6330, -1E+5=6025, -2E+5=5989, -6E+5=5970}",
                -571119.023304855,
                20.706134999956284,
                3.6956279999854234
        );
        range_BigDecimal_BigDecimal_helper(
                32,
                8,
                "-1E+6",
                "-1",
                "[-214306.659682220530905962097023400000, -306088.58100000," +
                " -314056.363167761203968018395640000000000000, -6.0000E+5, -990885.379105064810000000000000," +
                " -124031.70300000, -258000, -1.0E+6, -460000, -664253.2792000000, -59000.00, -7.870E+5, -700000.0," +
                " -623774.8689807541989739310142201121400579293277740, -585426.00000, -1.000E+6," +
                " -531200.0000000000000000, -292128.0591477809626800000000000, -600000.000," +
                " -352035.96505593000000000000000000000]",
                "{-1E+6=6569, -1.0E+6=5837, -1.00E+6=5145, -1.000E+6=4615, -1.0000E+6=4107, -1.00000E+6=3646," +
                " -1000000=3278, -1000000.0=2781, -1000000.00=2510, -1000000.000=2194}",
                -529385.2318107232,
                53.81033699999196,
                11.33299799999915
        );
        range_BigDecimal_BigDecimal_helper(
                1,
                1,
                "100",
                "101",
                "[100.835, 100.3668627520, 100.930, 100.028, 100.64, 100.080, 1.0E+2, 100.50, 100.060, 1.0E+2, 101," +
                " 1.0E+2, 1E+2, 1E+2, 1E+2, 1.0E+2, 100.7286, 101, 101.0, 101]",
                "{1E+2=124856, 101=124803, 1.0E+2=62583, 101.0=62048, 101.00=31346, 100=31235, 100.0=15760," +
                " 101.000=15441, 100.4=14299, 100.5=14020}",
                100.49956052399139,
                12.080524999929484,
                2.125093999988825
        );
        range_BigDecimal_BigDecimal_helper(
                5,
                3,
                "100",
                "101",
                "[100.34025400000000, 100.9300620000, 100.82700000000, 1.0E+2, 100.38, 100.144266000000, 100.1," +
                " 100.90, 100.0000, 100.147100000, 100.0171211820157700000000000, 100.314420, 100.0000000000," +
                " 100.441346380, 100.1174720000, 100.400, 100.00, 1E+2, 100.9073486280, 100.0]",
                "{101=31296, 1E+2=31161, 1.0E+2=23576, 101.0=23159, 100=17727, 101.00=17542, 101.000=13229," +
                " 100.0=13153, 101.0000=9921, 100.00=9849}",
                100.50017215152612,
                26.214576999832683,
                5.9297770000088414
        );
        range_BigDecimal_BigDecimal_helper(
                32,
                8,
                "100",
                "101",
                "[100.785693340317779469094037902976600000, 100.69391141900000," +
                " 100.685943636832238796031981604360000000000000, 100.40000, 101.00000000000000000," +
                " 100.134524124471900000000, 100.5166270, 100.2150, 100.540000, 100.3357467208000000, 100.94100000," +
                " 100.2130, 100.3000000, 100.3762251310192458010260689857798878599420706722260, 100.41457400000," +
                " 100.0, 100.4688000000000000000000, 100.7078719408522190373200000000000, 100.400000000," +
                " 101.00000000000000]",
                "{1E+2=6183, 101=6066, 101.0=5576, 1.0E+2=5472, 100=4850, 101.00=4787, 100.0=4326, 101.000=4307," +
                " 100.00=3879, 101.0000=3867}",
                100.50033153589463,
                59.864782000072,
                15.909656999996354
        );
        range_BigDecimal_BigDecimal_helper(
                1,
                1,
                "2.7183",
                "3.1416",
                "[2.9183, 3.0083, 3.018300, 2.71830, 3.11830, 3.0083, 2.71830, 2.7183, 2.7183, 2.7183, 2.71830," +
                " 2.71830, 2.81830, 3.09779900, 2.9183, 2.748300, 2.7183, 2.7183, 2.718300, 2.90830]",
                "{2.7183=267665, 2.71830=133894, 2.718300=66690, 2.7183000=33405, 2.8183=29912, 3.1183=29843," +
                " 3.0183=29824, 2.9183=29698, 2.71830000=16610, 3.11830=15121}",
                2.825842387956162,
                18.745504000095565,
                5.0517519999509375
        );
        range_BigDecimal_BigDecimal_helper(
                5,
                3,
                "2.7183",
                "3.1416",
                "[3.05855400000000, 3.10850, 2.85024000000, 2.8183, 3.11830000000000, 3.032720, 2.7183000000000000," +
                " 3.05830000, 3.118300, 2.71830000, 2.7183, 3.0183000, 2.7183000, 3.018300, 3.0183, 2.7283, 2.81783," +
                " 2.81830000, 2.7183, 2.875800]",
                "{2.7183=70293, 2.71830=52612, 2.718300=39293, 2.7183000=29791, 2.71830000=22061, 2.718300000=16508," +
                " 2.7183000000=12546, 2.8183=11702, 3.0183=11517, 3.1183=11507}",
                2.877667574471862,
                28.275335000400293,
                7.899351999956035
        );
        range_BigDecimal_BigDecimal_helper(
                32,
                8,
                "2.7183",
                "3.1416",
                "[2.9276200000000, 2.926632953832284170220810772700000000000, 3.11830000," +
                " 2.727414620894935190000000000000, 2.93330, 3.0540467208000000, 2.93130, 3.0183000000," +
                " 3.0945251310192458010260689857798878599420706722260, 3.13287400000, 2.7183000," +
                " 2.94427510014568920700000000000000000, 2.8839238087623776000000000," +
                " 3.002300000000000000000000000000000, 2.7406823000000, 2.8683000000," +
                " 2.9733000000000000000000000000, 2.9870062506957968000000000000000000000," +
                " 3.0912056957045900000000000, 2.815043000000000000000000000]",
                "{2.7183=14169, 2.71830=12571, 2.718300=11272, 2.7183000=10004, 2.71830000=8833, 2.718300000=7930," +
                " 2.7183000000=7075, 2.71830000000=6322, 2.718300000000=5500, 2.7183000000000=4858}",
                2.9066625149504075,
                58.079359000018094,
                16.867250000012355
        );
        range_BigDecimal_BigDecimal_helper(
                1,
                1,
                "-3.1416",
                "2.7183",
                "[-1.1416, -0.2416, -0.141600, -3.14160, 1.85840, -2.54160, -3.14160, -3.1416, -2.1416, -3.1416," +
                " -3.1416, 0.3484000, -3.14160, -2.14160, 0.6533900, -1.1416, -2.841600, -3.1416, -3.1416, -3.141600]",
                "{-3.1416=233391, -3.14160=117263, -3.141600=58309, -3.1416000=29125, 0.8584=26116, -0.1416=25975," +
                " -1.1416=25926, 1.8584=25915, -2.1416=25774, -3.14160000=14501}",
                -1.5598804219080757,
                17.89980300004773,
                5.028742999950965
        );
        range_BigDecimal_BigDecimal_helper(
                5,
                3,
                "-3.1416",
                "2.7183",
                "[0.2609400000000, 0.76040, -1.8222000000, -2.1416, 2.27696700000000000, -2.92040000000, 0.00260," +
                " -3.1416000000000000, 1.27186380, -1.966880000, 0.858400, -3.14160000, -3.1416, -0.1416000," +
                " -3.1416000, -0.141600, -0.1416, 1.8584000, -2.001600, -3.141600000]",
                "{-3.1416=56028, -3.14160=41974, -3.141600=31473, -3.1416000=23767, -3.14160000=17581," +
                " -3.141600000=13132, -3.1416000000=10012, 1.8584=9418, 0.8584=9305, -2.1416=9285}",
                -0.8501281134081722,
                26.622813000158892,
                7.745819999954518
        );
        range_BigDecimal_BigDecimal_helper(
                32,
                8,
                "-3.1416",
                "2.7183",
                "[-1.048400000000, -1.05827046167715829779189227300000000000, 0.85840000," +
                " -3.05045379105064810000000000000, -0.99160, 2.25840000, 0.215867208000000, -1.01160," +
                " -0.1416000000, 0.620651310192458010260689857798878599420706722260, 1.0041400000, -3.1416000," +
                " 1.5464000000000000000000, -0.6143812383000000000, 1.702953952686614629470, -1.4291152000," +
                " 1.479574892263310636373561892250, 1.414887978330000000, 2.15215900000000, -3.14160]",
                "{-3.1416=10682, -3.14160=9348, -3.141600=8542, -3.1416000=7514, -3.14160000=6756," +
                " -3.141600000=5985, -3.1416000000=5319, -3.14160000000=4777, -3.141600000000=4200," +
                " -3.1416000000000=3656}",
                -0.4897316229434775,
                55.57982800002947,
                16.511090000011794
        );
        range_BigDecimal_BigDecimal_helper(
                1,
                1,
                "0",
                "0",
                "[0.000, 0.0, 0E-8, 0.0, 0E+1, 0.0, 0E+2, 0, 0.0, 0.0, 0E+1, 0E+2, 0, 0, 0, 0.0, 0.0, 0E+1, 0, 0]",
                "{0=500069, 0.0=125042, 0E+1=125020, 0.00=62554, 0E+2=62266, 0E+3=31431, 0.000=31320, 0.0000=15667," +
                " 0E+4=15448, 0E+5=7835}",
                0.0,
                0.0,
                0.9994539999976759
        );
        range_BigDecimal_BigDecimal_helper(
                5,
                3,
                "0",
                "0",
                "[0.000000, 0E-8, 0.00000, 0E+5, 0.00, 0E+8, 0.00, 0.0, 0, 0E+5, 0.000000, 0, 0E+1, 0E+1, 0E+1," +
                " 0E-7, 0E+3, 0E-7, 0E-13, 0.0000]",
                "{0=249778, 0E+1=93626, 0.0=93467, 0E+2=70571, 0.00=70056, 0.000=53060, 0E+3=52604, 0E+4=39246," +
                " 0.0000=39217, 0.00000=29959}",
                0.0,
                0.0,
                3.0055569999915344
        );
        range_BigDecimal_BigDecimal_helper(
                32,
                8,
                "0",
                "0",
                "[0E-31, 0E+8, 0E-8, 0.00000, 0E+28, 0E+17, 0, 0E+5, 0, 0E+17, 0E+13, 0, 0E+9, 0E+5, 0E+2, 0E+3," +
                " 0E+1, 0.00, 0E+4, 0E+10]",
                "{0=110920, 0E+1=49476, 0.0=49456, 0.00=44126, 0E+2=43730, 0.000=39444, 0E+3=38954, 0.0000=34701," +
                " 0E+4=34320, 0.00000=31042}",
                0.0,
                0.0,
                7.998792000016782
        );
        range_BigDecimal_BigDecimal_helper(
                1,
                1,
                "0",
                "0.0",
                "[0.000, 0.0, 0E-8, 0.0, 0E+1, 0.0, 0E+2, 0, 0.0, 0.0, 0E+1, 0E+2, 0, 0, 0, 0.0, 0.0, 0E+1, 0, 0]",
                "{0=500069, 0.0=125042, 0E+1=125020, 0.00=62554, 0E+2=62266, 0E+3=31431, 0.000=31320, 0.0000=15667," +
                " 0E+4=15448, 0E+5=7835}",
                0.0,
                0.0,
                0.9994539999976759
        );
        range_BigDecimal_BigDecimal_helper(
                5,
                3,
                "0",
                "0.0",
                "[0.000000, 0E-8, 0.00000, 0E+5, 0.00, 0E+8, 0.00, 0.0, 0, 0E+5, 0.000000, 0, 0E+1, 0E+1, 0E+1," +
                " 0E-7, 0E+3, 0E-7, 0E-13, 0.0000]",
                "{0=249778, 0E+1=93626, 0.0=93467, 0E+2=70571, 0.00=70056, 0.000=53060, 0E+3=52604, 0E+4=39246," +
                " 0.0000=39217, 0.00000=29959}",
                0.0,
                0.0,
                3.0055569999915344
        );
        range_BigDecimal_BigDecimal_helper(
                32,
                8,
                "0",
                "0.0",
                "[0E-31, 0E+8, 0E-8, 0.00000, 0E+28, 0E+17, 0, 0E+5, 0, 0E+17, 0E+13, 0, 0E+9, 0E+5, 0E+2, 0E+3," +
                " 0E+1, 0.00, 0E+4, 0E+10]",
                "{0=110920, 0E+1=49476, 0.0=49456, 0.00=44126, 0E+2=43730, 0.000=39444, 0E+3=38954, 0.0000=34701," +
                " 0E+4=34320, 0.00000=31042}",
                0.0,
                0.0,
                7.998792000016782
        );
        range_BigDecimal_BigDecimal_helper(
                1,
                1,
                "0.0",
                "0",
                "[0.000, 0.0, 0E-8, 0.0, 0E+1, 0.0, 0E+2, 0, 0.0, 0.0, 0E+1, 0E+2, 0, 0, 0, 0.0, 0.0, 0E+1, 0, 0]",
                "{0=500069, 0.0=125042, 0E+1=125020, 0.00=62554, 0E+2=62266, 0E+3=31431, 0.000=31320, 0.0000=15667," +
                " 0E+4=15448, 0E+5=7835}",
                0.0,
                0.0,
                0.9994539999976759
        );
        range_BigDecimal_BigDecimal_helper(
                5,
                3,
                "0.0",
                "0",
                "[0.000000, 0E-8, 0.00000, 0E+5, 0.00, 0E+8, 0.00, 0.0, 0, 0E+5, 0.000000, 0, 0E+1, 0E+1, 0E+1," +
                " 0E-7, 0E+3, 0E-7, 0E-13, 0.0000]",
                "{0=249778, 0E+1=93626, 0.0=93467, 0E+2=70571, 0.00=70056, 0.000=53060, 0E+3=52604, 0E+4=39246," +
                " 0.0000=39217, 0.00000=29959}",
                0.0,
                0.0,
                3.0055569999915344
        );
        range_BigDecimal_BigDecimal_helper(
                32,
                8,
                "0.0",
                "0",
                "[0E-31, 0E+8, 0E-8, 0.00000, 0E+28, 0E+17, 0, 0E+5, 0, 0E+17, 0E+13, 0, 0E+9, 0E+5, 0E+2, 0E+3," +
                " 0E+1, 0.00, 0E+4, 0E+10]",
                "{0=110920, 0E+1=49476, 0.0=49456, 0.00=44126, 0E+2=43730, 0.000=39444, 0E+3=38954, 0.0000=34701," +
                " 0E+4=34320, 0.00000=31042}",
                0.0,
                0.0,
                7.998792000016782
        );
        range_BigDecimal_BigDecimal_helper(
                1,
                1,
                "0.0",
                "0.0",
                "[0.000, 0.0, 0E-8, 0.0, 0E+1, 0.0, 0E+2, 0, 0.0, 0.0, 0E+1, 0E+2, 0, 0, 0, 0.0, 0.0, 0E+1, 0, 0]",
                "{0=500069, 0.0=125042, 0E+1=125020, 0.00=62554, 0E+2=62266, 0E+3=31431, 0.000=31320, 0.0000=15667," +
                " 0E+4=15448, 0E+5=7835}",
                0.0,
                0.0,
                0.9994539999976759
        );
        range_BigDecimal_BigDecimal_helper(
                5,
                3,
                "0.0",
                "0.0",
                "[0.000000, 0E-8, 0.00000, 0E+5, 0.00, 0E+8, 0.00, 0.0, 0, 0E+5, 0.000000, 0, 0E+1, 0E+1, 0E+1," +
                " 0E-7, 0E+3, 0E-7, 0E-13, 0.0000]",
                "{0=249778, 0E+1=93626, 0.0=93467, 0E+2=70571, 0.00=70056, 0.000=53060, 0E+3=52604, 0E+4=39246," +
                " 0.0000=39217, 0.00000=29959}",
                0.0,
                0.0,
                3.0055569999915344
        );
        range_BigDecimal_BigDecimal_helper(
                32,
                8,
                "0.0",
                "0.0",
                "[0E-31, 0E+8, 0E-8, 0.00000, 0E+28, 0E+17, 0, 0E+5, 0, 0E+17, 0E+13, 0, 0E+9, 0E+5, 0E+2, 0E+3," +
                " 0E+1, 0.00, 0E+4, 0E+10]",
                "{0=110920, 0E+1=49476, 0.0=49456, 0.00=44126, 0E+2=43730, 0.000=39444, 0E+3=38954, 0.0000=34701," +
                " 0E+4=34320, 0.00000=31042}",
                0.0,
                0.0,
                7.998792000016782
        );
        range_BigDecimal_BigDecimal_helper(
                1,
                1,
                "1",
                "1",
                "[1.000, 1.00, 1.000000000, 1.00, 1.00, 1, 1.0, 1.000, 1, 1, 1.00, 1.00, 1.00, 1, 1.00, 1, 1, 1.0," +
                " 1.0, 1]",
                "{1=499125, 1.0=250897, 1.00=124849, 1.000=62518, 1.0000=31407, 1.00000=15634, 1.000000=7825," +
                " 1.0000000=3926, 1.00000000=1896, 1.000000000=956}",
                1.000000000007918,
                4.073980000046605,
                1.0008359999977228
        );
        range_BigDecimal_BigDecimal_helper(
                5,
                3,
                "1",
                "1",
                "[1.000000, 1.000000000, 1.000000, 1.000000, 1.000, 1.000000000, 1, 1.00, 1.00, 1.0, 1.000000," +
                " 1.0000000, 1.0, 1.00, 1, 1.0, 1, 1.0, 1, 1.0000000]",
                "{1=250407, 1.0=187060, 1.00=139994, 1.000=105560, 1.0000=79154, 1.00000=58963, 1.000000=44920," +
                " 1.0000000=33524, 1.00000000=25356, 1.000000000=18834}",
                1.000000000007918,
                10.558456999961003,
                3.003367999991497
        );
        range_BigDecimal_BigDecimal_helper(
                32,
                8,
                "1",
                "1",
                "[1.0000000000000000000000000000000, 1.000000000, 1.000000000, 1.00000," +
                " 1.00000000000000000000000000000, 1.000000000000000000, 1.0, 1.00000, 1, 1.00000000000000000," +
                " 1.0000000000000, 1, 1.0000000000, 1.000000, 1.000, 1.000, 1, 1.0, 1.00, 1.00000]",
                "{1=110949, 1.0=98973, 1.00=87810, 1.000=78512, 1.0000=69401, 1.00000=61358, 1.000000=54691," +
                " 1.0000000=48553, 1.00000000=43415, 1.000000000=38254}",
                1.000000000007918,
                27.0739779999554,
                7.996049000016875
        );
        range_BigDecimal_BigDecimal_helper(1, 1, "5", "3", "[]", "{}", 0.0, 0.0, 0.0);
        range_BigDecimal_BigDecimal_helper(5, 3, "5", "3", "[]", "{}", 0.0, 0.0, 0.0);
        range_BigDecimal_BigDecimal_helper(32, 8, "5", "3", "[]", "{}", 0.0, 0.0, 0.0);
        range_BigDecimal_BigDecimal_fail_helper(0, 1, "0", "1");
        range_BigDecimal_BigDecimal_fail_helper(1, 0, "0", "1");
    }

    @Test
    public void testNextFromRangeUpCanonical_BigDecimal() {
        nextFromRangeUpCanonical_BigDecimal_helper(2, 1, "0", "0.005");
        nextFromRangeUpCanonical_BigDecimal_helper(5, 3, "0", "0.000013");
        nextFromRangeUpCanonical_BigDecimal_helper(32, 8, "0", "4.04936997654063E-17");
        nextFromRangeUpCanonical_BigDecimal_helper(2, 1, "0.0", "0.013");
        nextFromRangeUpCanonical_BigDecimal_helper(5, 3, "0.0", "0.000021");
        nextFromRangeUpCanonical_BigDecimal_helper(32, 8, "0.0", "58756.389791438");
        nextFromRangeUpCanonical_BigDecimal_helper(2, 1, "1", "1.013");
        nextFromRangeUpCanonical_BigDecimal_helper(5, 3, "1", "1.000021");
        nextFromRangeUpCanonical_BigDecimal_helper(32, 8, "1", "58757.389791438");
        nextFromRangeUpCanonical_BigDecimal_helper(2, 1, "1.0", "1.013");
        nextFromRangeUpCanonical_BigDecimal_helper(5, 3, "1.0", "1.000021");
        nextFromRangeUpCanonical_BigDecimal_helper(32, 8, "1.0", "58757.389791438");
        nextFromRangeUpCanonical_BigDecimal_helper(2, 1, "-1.0", "-0.987");
        nextFromRangeUpCanonical_BigDecimal_helper(5, 3, "-1.0", "-0.999979");
        nextFromRangeUpCanonical_BigDecimal_helper(32, 8, "-1.0", "58755.389791438");
        nextFromRangeUpCanonical_BigDecimal_helper(2, 1, "9", "9.013");
        nextFromRangeUpCanonical_BigDecimal_helper(5, 3, "9", "9.000021");
        nextFromRangeUpCanonical_BigDecimal_helper(32, 8, "9", "58765.389791438");
        nextFromRangeUpCanonical_BigDecimal_helper(2, 1, "-9", "-8.987");
        nextFromRangeUpCanonical_BigDecimal_helper(5, 3, "-9", "-8.999979");
        nextFromRangeUpCanonical_BigDecimal_helper(32, 8, "-9", "58747.389791438");
        nextFromRangeUpCanonical_BigDecimal_helper(2, 1, "10", "10.013");
        nextFromRangeUpCanonical_BigDecimal_helper(5, 3, "10", "10.000021");
        nextFromRangeUpCanonical_BigDecimal_helper(32, 8, "10", "58766.389791438");
        nextFromRangeUpCanonical_BigDecimal_helper(2, 1, "-10", "-9.987");
        nextFromRangeUpCanonical_BigDecimal_helper(5, 3, "-10", "-9.999979");
        nextFromRangeUpCanonical_BigDecimal_helper(32, 8, "-10", "58746.389791438");
        nextFromRangeUpCanonical_BigDecimal_helper(2, 1, "101", "101.013");
        nextFromRangeUpCanonical_BigDecimal_helper(5, 3, "101", "101.000021");
        nextFromRangeUpCanonical_BigDecimal_helper(32, 8, "101", "58857.389791438");
        nextFromRangeUpCanonical_BigDecimal_helper(2, 1, "-101", "-100.987");
        nextFromRangeUpCanonical_BigDecimal_helper(5, 3, "-101", "-100.999979");
        nextFromRangeUpCanonical_BigDecimal_helper(32, 8, "-101", "58655.389791438");
        nextFromRangeUpCanonical_BigDecimal_helper(2, 1, "1234567", "1234567.013");
        nextFromRangeUpCanonical_BigDecimal_helper(5, 3, "1234567", "1234567.000021");
        nextFromRangeUpCanonical_BigDecimal_helper(32, 8, "1234567", "1293323.389791438");
        nextFromRangeUpCanonical_BigDecimal_helper(2, 1, "-1234567", "-1234566.987");
        nextFromRangeUpCanonical_BigDecimal_helper(5, 3, "-1234567", "-1234566.999979");
        nextFromRangeUpCanonical_BigDecimal_helper(32, 8, "-1234567", "-1175810.610208562");
        nextFromRangeUpCanonical_BigDecimal_helper(2, 1, "0.09", "0.103");
        nextFromRangeUpCanonical_BigDecimal_helper(5, 3, "0.09", "0.090021");
        nextFromRangeUpCanonical_BigDecimal_helper(32, 8, "0.09", "58756.479791438");
        nextFromRangeUpCanonical_BigDecimal_helper(2, 1, "-0.09", "-0.077");
        nextFromRangeUpCanonical_BigDecimal_helper(5, 3, "-0.09", "-0.089979");
        nextFromRangeUpCanonical_BigDecimal_helper(32, 8, "-0.09", "58756.299791438");
        nextFromRangeUpCanonical_BigDecimal_helper(2, 1, "1E-12", "0.013000000001");
        nextFromRangeUpCanonical_BigDecimal_helper(5, 3, "1E-12", "0.000021000001");
        nextFromRangeUpCanonical_BigDecimal_helper(32, 8, "1E-12", "58756.389791438001");
        nextFromRangeUpCanonical_BigDecimal_helper(2, 1, "1E+12", "1000000000000.013");
        nextFromRangeUpCanonical_BigDecimal_helper(5, 3, "1E+12", "1000000000000.000021");
        nextFromRangeUpCanonical_BigDecimal_helper(32, 8, "1E+12", "1000000058756.389791438");
        nextFromRangeUpCanonical_BigDecimal_fail_helper(1, 1, "0");
        nextFromRangeUpCanonical_BigDecimal_fail_helper(2, 0, "0");
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
                topSampleCount,
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
                "[0.005, 7E-7, 0, 6, 0.7, 0.2, 6, 0, 0, 2.5, 0.01, 0, 0, 0.001, 0, 1, 0, 0.1, 0, 0.01]",
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
                " 0.0001, 0.3, 0.0002]",
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
                " 0.005375094279919512, 3, 0.00148719799, 0.00075]",
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
                "[0.013, 0.01, 2.21E-7, 0.01, 0.01, 10, 0.3, 0.001, 0, 0.01, 0.01, 0.01, 1, 0.01, 3, 0.02, 0.02, 47," +
                " 1, 1.5]",
                "{0=285456, 0.1=107903, 1=95521, 0.01=53436, 2=32057, 3=31423, 0.2=26790, 0.3=26779, 0.001=26585," +
                " 0.02=13319}",
                2707.4813529248736,
                1.7140359999883126,
                0.85546299999781
        );
        rangeUpCanonical_BigDecimal_helper(
                5,
                3,
                "0.0",
                "[0.000021, 1.3E-8, 0.076574, 1, 0.25, 0.0001, 2E-8, 0.00006, 6.2, 0, 1E-12, 0.005, 0.000002," +
                " 0.0002, 1, 1.2E-8, 14.93, 6, 5, 0.041]",
                "{0=79948, 0.1=36209, 1=33212, 0.01=26729, 0.001=20229, 0.0001=15310, 0.3=14469, 0.2=14308, 3=13995," +
                " 2=13866}",
                6.34339490399912E13,
                4.805518000004578,
                2.8847469999916115
        );
        rangeUpCanonical_BigDecimal_helper(
                32,
                8,
                "0.0",
                "[58756.389791438, 1634.580688469, 107841461.3, 0.22813, 6E-17, 7.781E-10, 201836, 0.000437, 108214," +
                " 932982052377.2, 21226698680363546.52, 0.23520468, 2721691399869107.352, 25194755.0898504, 0.083," +
                " 7.12E-46, 0.0001, 5.025092007844E-9, 1.020934E-10, 1E-9]",
                "{0=6733, 1=3213, 0.1=3152, 0.01=2732, 0.001=2418, 0.0001=2140, 0.00001=1961, 0.000001=1705, 2=1611," +
                " 3=1563}",
                5.909010473233115E121,
                31.902074000021894,
                7.96111800001629
        );
        rangeUpCanonical_BigDecimal_helper(
                2,
                1,
                "1",
                "[1.013, 1.01, 1.000000221, 1.01, 1.01, 11, 1.3, 1.001, 1, 1.01, 1.01, 1.01, 2, 1.01, 4, 1.02, 1.02," +
                " 48, 2, 2.5]",
                "{1=285456, 1.1=107903, 2=95521, 1.01=53436, 3=32057, 4=31423, 1.2=26790, 1.3=26779, 1.001=26585," +
                " 1.02=13319}",
                2708.481352935786,
                4.407680000017143,
                0.85546299999781
        );
        rangeUpCanonical_BigDecimal_helper(
                5,
                3,
                "1",
                "[1.000021, 1.000000013, 1.076574, 2, 1.25, 1.0001, 1.00000002, 1.00006, 7.2, 1, 1.000000000001," +
                " 1.005, 1.000002, 1.0002, 2, 1.000000012, 15.93, 7, 6, 1.041]",
                "{1=79948, 1.1=36209, 2=33212, 1.01=26729, 1.001=20229, 1.0001=15310, 1.3=14469, 1.2=14308, 4=13995," +
                " 3=13866}",
                6.343394903999134E13,
                11.937791999983835,
                2.8847469999916115
        );
        rangeUpCanonical_BigDecimal_helper(
                32,
                8,
                "1",
                "[58757.389791438, 1635.580688469, 107841462.3, 1.22813, 1.00000000000000006, 1.0000000007781," +
                " 201837, 1.000437, 108215, 932982052378.2, 21226698680363547.52, 1.23520468, 2721691399869108.352," +
                " 25194756.0898504, 1.083, 1.000000000000000000000000000000000000000000000712, 1.0001," +
                " 1.000000005025092007844, 1.0000000001020934, 1.000000001]",
                "{1=6733, 2=3213, 1.1=3152, 1.01=2732, 1.001=2418, 1.0001=2140, 1.00001=1961, 1.000001=1705, 3=1611," +
                " 4=1563}",
                5.909010473233115E121,
                44.45722600001309,
                7.96111800001629
        );
        rangeUpCanonical_BigDecimal_helper(
                2,
                1,
                "1.0",
                "[1.013, 1.01, 1.000000221, 1.01, 1.01, 11, 1.3, 1.001, 1, 1.01, 1.01, 1.01, 2, 1.01, 4, 1.02, 1.02," +
                " 48, 2, 2.5]",
                "{1=285456, 1.1=107903, 2=95521, 1.01=53436, 3=32057, 4=31423, 1.2=26790, 1.3=26779, 1.001=26585," +
                " 1.02=13319}",
                2708.481352935786,
                4.407680000017143,
                0.85546299999781
        );
        rangeUpCanonical_BigDecimal_helper(
                5,
                3,
                "1.0",
                "[1.000021, 1.000000013, 1.076574, 2, 1.25, 1.0001, 1.00000002, 1.00006, 7.2, 1, 1.000000000001," +
                " 1.005, 1.000002, 1.0002, 2, 1.000000012, 15.93, 7, 6, 1.041]",
                "{1=79948, 1.1=36209, 2=33212, 1.01=26729, 1.001=20229, 1.0001=15310, 1.3=14469, 1.2=14308, 4=13995," +
                " 3=13866}",
                6.343394903999134E13,
                11.937791999983835,
                2.8847469999916115
        );
        rangeUpCanonical_BigDecimal_helper(
                32,
                8,
                "1.0",
                "[58757.389791438, 1635.580688469, 107841462.3, 1.22813, 1.00000000000000006, 1.0000000007781," +
                " 201837, 1.000437, 108215, 932982052378.2, 21226698680363547.52, 1.23520468, 2721691399869108.352," +
                " 25194756.0898504, 1.083, 1.000000000000000000000000000000000000000000000712, 1.0001," +
                " 1.000000005025092007844, 1.0000000001020934, 1.000000001]",
                "{1=6733, 2=3213, 1.1=3152, 1.01=2732, 1.001=2418, 1.0001=2140, 1.00001=1961, 1.000001=1705, 3=1611," +
                " 4=1563}",
                5.909010473233115E121,
                44.45722600001309,
                7.96111800001629
        );
        rangeUpCanonical_BigDecimal_helper(
                2,
                1,
                "-1.0",
                "[-0.987, -0.99, -0.999999779, -0.99, -0.99, 9, -0.7, -0.999, -1, -0.99, -0.99, -0.99, 0, -0.99, 2," +
                " -0.98, -0.98, 46, 0, 0.5]",
                "{-1=285456, -0.9=107903, 0=95521, -0.99=53436, 1=32057, 2=31423, -0.8=26790, -0.7=26779," +
                " -0.999=26585, -0.98=13319}",
                2706.4813529360126,
                3.9844060000250554,
                0.85546299999781
        );
        rangeUpCanonical_BigDecimal_helper(
                5,
                3,
                "-1.0",
                "[-0.999979, -0.999999987, -0.923426, 0, -0.75, -0.9999, -0.99999998, -0.99994, 5.2, -1," +
                " -0.999999999999, -0.995, -0.999998, -0.9998, 0, -0.999999988, 13.93, 5, 4, -0.959]",
                "{-1=79948, -0.9=36209, 0=33212, -0.99=26729, -0.999=20229, -0.9999=15310, -0.7=14469, -0.8=14308," +
                " 2=13995, 1=13866}",
                6.343394903999108E13,
                11.564370999974305,
                2.8847469999916115
        );
        rangeUpCanonical_BigDecimal_helper(
                32,
                8,
                "-1.0",
                "[58755.389791438, 1633.580688469, 107841460.3, -0.77187, -0.99999999999999994, -0.9999999992219," +
                " 201835, -0.999563, 108213, 932982052376.2, 21226698680363545.52, -0.76479532," +
                " 2721691399869106.352, 25194754.0898504, -0.917," +
                " -0.999999999999999999999999999999999999999999999288, -0.9999, -0.999999994974907992156," +
                " -0.9999999998979066, -0.999999999]",
                "{-1=6733, 0=3213, -0.9=3152, -0.99=2732, -0.999=2418, -0.9999=2140, -0.99999=1961, -0.999999=1705," +
                " 1=1611, 2=1563}",
                5.909010473233115E121,
                44.30726200000445,
                7.96111800001629
        );
        rangeUpCanonical_BigDecimal_helper(
                2,
                1,
                "9",
                "[9.013, 9.01, 9.000000221, 9.01, 9.01, 19, 9.3, 9.001, 9, 9.01, 9.01, 9.01, 10, 9.01, 12, 9.02," +
                " 9.02, 56, 10, 10.5]",
                "{9=285456, 9.1=107903, 10=95521, 9.01=53436, 11=32057, 12=31423, 9.2=26790, 9.3=26779, 9.001=26585," +
                " 9.02=13319}",
                2716.4813529376775,
                6.921292999942901,
                0.85546299999781
        );
        rangeUpCanonical_BigDecimal_helper(
                5,
                3,
                "9",
                "[9.000021, 9.000000013, 9.076574, 10, 9.25, 9.0001, 9.00000002, 9.00006, 15.2, 9, 9.000000000001," +
                " 9.005, 9.000002, 9.0002, 10, 9.000000012, 23.93, 15, 14, 9.041]",
                "{9=79948, 9.1=36209, 10=33212, 9.01=26729, 9.001=20229, 9.0001=15310, 9.3=14469, 9.2=14308," +
                " 12=13995, 11=13866}",
                6.343394903999254E13,
                14.319413999988011,
                2.8847469999916115
        );
        rangeUpCanonical_BigDecimal_helper(
                32,
                8,
                "9",
                "[58765.389791438, 1643.580688469, 107841470.3, 9.22813, 9.00000000000000006, 9.0000000007781," +
                " 201845, 9.000437, 108223, 932982052386.2, 21226698680363555.52, 9.23520468, 2721691399869116.352," +
                " 25194764.0898504, 9.083, 9.000000000000000000000000000000000000000000000712, 9.0001," +
                " 9.000000005025092007844, 9.0000000001020934, 9.000000001]",
                "{9=6733, 10=3213, 9.1=3152, 9.01=2732, 9.001=2418, 9.0001=2140, 9.00001=1961, 9.000001=1705," +
                " 11=1611, 12=1563}",
                5.909010473233115E121,
                45.97913000002949,
                7.96111800001629
        );
        rangeUpCanonical_BigDecimal_helper(
                2,
                1,
                "-9",
                "[-8.987, -8.99, -8.999999779, -8.99, -8.99, 1, -8.7, -8.999, -9, -8.99, -8.99, -8.99, -8, -8.99," +
                " -6, -8.98, -8.98, 38, -8, -7.5]",
                "{-9=285456, -8.9=107903, -8=95521, -8.99=53436, -7=32057, -6=31423, -8.8=26790, -8.7=26779," +
                " -8.999=26585, -8.98=13319}",
                2698.481352934637,
                6.6437579999790835,
                0.85546299999781
        );
        rangeUpCanonical_BigDecimal_helper(
                5,
                3,
                "-9",
                "[-8.999979, -8.999999987, -8.923426, -8, -8.75, -8.9999, -8.99999998, -8.99994, -2.8, -9," +
                " -8.999999999999, -8.995, -8.999998, -8.9998, -8, -8.999999988, 5.93, -3, -4, -8.959]",
                "{-9=79948, -8.9=36209, -8=33212, -8.99=26729, -8.999=20229, -8.9999=15310, -8.7=14469, -8.8=14308," +
                " -6=13995, -7=13866}",
                6.343394903998996E13,
                14.020057999981177,
                2.8847469999916115
        );
        rangeUpCanonical_BigDecimal_helper(
                32,
                8,
                "-9",
                "[58747.389791438, 1625.580688469, 107841452.3, -8.77187, -8.99999999999999994, -8.9999999992219," +
                " 201827, -8.999563, 108205, 932982052368.2, 21226698680363537.52, -8.76479532," +
                " 2721691399869098.352, 25194746.0898504, -8.917," +
                " -8.999999999999999999999999999999999999999999999288, -8.9999, -8.999999994974907992156," +
                " -8.9999999998979066, -8.999999999]",
                "{-9=6733, -8=3213, -8.9=3152, -8.99=2732, -8.999=2418, -8.9999=2140, -8.99999=1961, -8.999999=1705," +
                " -7=1611, -6=1563}",
                5.909010473233115E121,
                45.827458000024365,
                7.96111800001629
        );
        rangeUpCanonical_BigDecimal_helper(
                2,
                1,
                "10",
                "[10.013, 10.01, 10.000000221, 10.01, 10.01, 20, 10.3, 10.001, 10, 10.01, 10.01, 10.01, 11, 10.01," +
                " 13, 10.02, 10.02, 57, 11, 11.5]",
                "{10=285456, 10.1=107903, 11=95521, 10.01=53436, 12=32057, 13=31423, 10.2=26790, 10.3=26779," +
                " 10.001=26585, 10.02=13319}",
                2717.4813529716844,
                6.940084999942944,
                0.85546299999781
        );
        rangeUpCanonical_BigDecimal_helper(
                5,
                3,
                "10",
                "[10.000021, 10.000000013, 10.076574, 11, 10.25, 10.0001, 10.00000002, 10.00006, 16.2, 10," +
                " 10.000000000001, 10.005, 10.000002, 10.0002, 11, 10.000000012, 24.93, 16, 15, 10.041]",
                "{10=79948, 10.1=36209, 11=33212, 10.01=26729, 10.001=20229, 10.0001=15310, 10.3=14469, 10.2=14308," +
                " 13=13995, 12=13866}",
                6.343394903999273E13,
                14.361414999996748,
                2.8847469999916115
        );
        rangeUpCanonical_BigDecimal_helper(
                32,
                8,
                "10",
                "[58766.389791438, 1644.580688469, 107841471.3, 10.22813, 10.00000000000000006, 10.0000000007781," +
                " 201846, 10.000437, 108224, 932982052387.2, 21226698680363556.52, 10.23520468," +
                " 2721691399869117.352, 25194765.0898504, 10.083," +
                " 10.000000000000000000000000000000000000000000000712, 10.0001, 10.000000005025092007844," +
                " 10.0000000001020934, 10.000000001]",
                "{10=6733, 11=3213, 10.1=3152, 10.01=2732, 10.001=2418, 10.0001=2140, 10.00001=1961, 10.000001=1705," +
                " 12=1611, 13=1563}",
                5.909010473233115E121,
                46.03847400001957,
                7.96111800001629
        );
        rangeUpCanonical_BigDecimal_helper(
                2,
                1,
                "-10",
                "[-9.987, -9.99, -9.999999779, -9.99, -9.99, 0, -9.7, -9.999, -10, -9.99, -9.99, -9.99, -9, -9.99," +
                " -7, -9.98, -9.98, 37, -9, -8.5]",
                "{-10=285456, -9.9=107903, -9=95521, -9.99=53436, -8=32057, -7=31423, -9.8=26790, -9.7=26779," +
                " -9.999=26585, -9.98=13319}",
                2697.481352905077,
                6.682550999971169,
                0.85546299999781
        );
        rangeUpCanonical_BigDecimal_helper(
                5,
                3,
                "-10",
                "[-9.999979, -9.999999987, -9.923426, -9, -9.75, -9.9999, -9.99999998, -9.99994, -3.8, -10," +
                " -9.999999999999, -9.995, -9.999998, -9.9998, -9, -9.999999988, 4.93, -4, -5, -9.959]",
                "{-10=79948, -9.9=36209, -9=33212, -9.99=26729, -9.999=20229, -9.9999=15310, -9.7=14469, -9.8=14308," +
                " -7=13995, -8=13866}",
                6.343394903998982E13,
                14.051392999984342,
                2.8847469999916115
        );
        rangeUpCanonical_BigDecimal_helper(
                32,
                8,
                "-10",
                "[58746.389791438, 1624.580688469, 107841451.3, -9.77187, -9.99999999999999994, -9.9999999992219," +
                " 201826, -9.999563, 108204, 932982052367.2, 21226698680363536.52, -9.76479532," +
                " 2721691399869097.352, 25194745.0898504, -9.917," +
                " -9.999999999999999999999999999999999999999999999288, -9.9999, -9.999999994974907992156," +
                " -9.9999999998979066, -9.999999999]",
                "{-10=6733, -9=3213, -9.9=3152, -9.99=2732, -9.999=2418, -9.9999=2140, -9.99999=1961," +
                " -9.999999=1705, -8=1611, -7=1563}",
                5.909010473233115E121,
                45.884170000011494,
                7.96111800001629
        );
        rangeUpCanonical_BigDecimal_helper(
                2,
                1,
                "101",
                "[101.013, 101.01, 101.000000221, 101.01, 101.01, 111, 101.3, 101.001, 101, 101.01, 101.01, 101.01," +
                " 102, 101.01, 104, 101.02, 101.02, 148, 102, 102.5]",
                "{101=285456, 101.1=107903, 102=95521, 101.01=53436, 103=32057, 104=31423, 101.2=26790, 101.3=26779," +
                " 101.001=26585, 101.02=13319}",
                2808.481352944026,
                9.911032000003974,
                0.85546299999781
        );
        rangeUpCanonical_BigDecimal_helper(
                5,
                3,
                "101",
                "[101.000021, 101.000000013, 101.076574, 102, 101.25, 101.0001, 101.00000002, 101.00006, 107.2, 101," +
                " 101.000000000001, 101.005, 101.000002, 101.0002, 102, 101.000000012, 115.93, 107, 106, 101.041]",
                "{101=79948, 101.1=36209, 102=33212, 101.01=26729, 101.001=20229, 101.0001=15310, 101.3=14469," +
                " 101.2=14308, 104=13995, 103=13866}",
                6.343394904002908E13,
                17.185365999960027,
                2.8847469999916115
        );
        rangeUpCanonical_BigDecimal_helper(
                32,
                8,
                "101",
                "[58857.389791438, 1735.580688469, 107841562.3, 101.22813, 101.00000000000000006, 101.0000000007781," +
                " 201937, 101.000437, 108315, 932982052478.2, 21226698680363647.52, 101.23520468," +
                " 2721691399869208.352, 25194856.0898504, 101.083," +
                " 101.000000000000000000000000000000000000000000000712, 101.0001, 101.000000005025092007844," +
                " 101.0000000001020934, 101.000000001]",
                "{101=6733, 102=3213, 101.1=3152, 101.01=2732, 101.001=2418, 101.0001=2140, 101.00001=1961," +
                " 101.000001=1705, 103=1611, 104=1563}",
                5.909010473233115E121,
                47.79291900002582,
                7.96111800001629
        );
        rangeUpCanonical_BigDecimal_helper(
                2,
                1,
                "-101",
                "[-100.987, -100.99, -100.999999779, -100.99, -100.99, -91, -100.7, -100.999, -101, -100.99," +
                " -100.99, -100.99, -100, -100.99, -98, -100.98, -100.98, -54, -100, -99.5]",
                "{-101=285456, -100.9=107903, -100=95521, -100.99=53436, -99=32057, -98=31423, -100.8=26790," +
                " -100.7=26779, -100.999=26585, -100.98=13319}",
                2606.481352900283,
                9.818213000003182,
                0.85546299999781
        );
        rangeUpCanonical_BigDecimal_helper(
                5,
                3,
                "-101",
                "[-100.999979, -100.999999987, -100.923426, -100, -100.75, -100.9999, -100.99999998, -100.99994," +
                " -94.8, -101, -100.999999999999, -100.995, -100.999998, -100.9998, -100, -100.999999988, -86.07," +
                " -95, -96, -100.959]",
                "{-101=79948, -100.9=36209, -100=33212, -100.99=26729, -100.999=20229, -100.9999=15310," +
                " -100.7=14469, -100.8=14308, -98=13995, -99=13866}",
                6.3433949039954E13,
                16.9751399999362,
                2.8847469999916115
        );
        rangeUpCanonical_BigDecimal_helper(
                32,
                8,
                "-101",
                "[58655.389791438, 1533.580688469, 107841360.3, -100.77187, -100.99999999999999994," +
                " -100.9999999992219, 201735, -100.999563, 108113, 932982052276.2, 21226698680363445.52," +
                " -100.76479532, 2721691399869006.352, 25194654.0898504, -100.917," +
                " -100.999999999999999999999999999999999999999999999288, -100.9999, -100.999999994974907992156," +
                " -100.9999999998979066, -100.999999999]",
                "{-101=6733, -100=3213, -100.9=3152, -100.99=2732, -100.999=2418, -100.9999=2140, -100.99999=1961," +
                " -100.999999=1705, -99=1611, -98=1563}",
                5.909010473233115E121,
                47.64578400002272,
                7.96111800001629
        );
        rangeUpCanonical_BigDecimal_helper(
                2,
                1,
                "1234567",
                "[1234567.013, 1234567.01, 1234567.000000221, 1234567.01, 1234567.01, 1234577, 1234567.3," +
                " 1234567.001, 1234567, 1234567.01, 1234567.01, 1234567.01, 1234568, 1234567.01, 1234570," +
                " 1234567.02, 1234567.02, 1234614, 1234568, 1234568.5]",
                "{1234567=285456, 1234567.1=107903, 1234568=95521, 1234567.01=53436, 1234569=32057, 1234570=31423," +
                " 1234567.2=26790, 1234567.3=26779, 1234567.001=26585, 1234567.02=13319}",
                1237274.4813450961,
                23.6885350000229,
                0.85546299999781
        );
        rangeUpCanonical_BigDecimal_helper(
                5,
                3,
                "1234567",
                "[1234567.000021, 1234567.000000013, 1234567.076574, 1234568, 1234567.25, 1234567.0001," +
                " 1234567.00000002, 1234567.00006, 1234573.2, 1234567, 1234567.000000000001, 1234567.005," +
                " 1234567.000002, 1234567.0002, 1234568, 1234567.000000012, 1234581.93, 1234573, 1234572," +
                " 1234567.041]",
                "{1234567=79948, 1234567.1=36209, 1234568=33212, 1234567.01=26729, 1234567.001=20229," +
                " 1234567.0001=15310, 1234567.3=14469, 1234567.2=14308, 1234570=13995, 1234569=13866}",
                6.34339502744356E13,
                30.3918229998628,
                2.8847469999916115
        );
        rangeUpCanonical_BigDecimal_helper(
                32,
                8,
                "1234567",
                "[1293323.389791438, 1236201.580688469, 109076028.3, 1234567.22813, 1234567.00000000000000006," +
                " 1234567.0000000007781, 1436403, 1234567.000437, 1342781, 932983286944.2, 21226698681598113.52," +
                " 1234567.23520468, 2721691401103674.352, 26429322.0898504, 1234567.083," +
                " 1234567.000000000000000000000000000000000000000000000712, 1234567.0001," +
                " 1234567.000000005025092007844, 1234567.0000000001020934, 1234567.000000001]",
                "{1234567=6733, 1234568=3213, 1234567.1=3152, 1234567.01=2732, 1234567.001=2418, 1234567.0001=2140," +
                " 1234567.00001=1961, 1234567.000001=1705, 1234569=1611, 1234570=1563}",
                5.909010473233115E121,
                56.45197000006925,
                7.96111800001629
        );
        rangeUpCanonical_BigDecimal_helper(
                2,
                1,
                "-1234567",
                "[-1234566.987, -1234566.99, -1234566.999999779, -1234566.99, -1234566.99, -1234557, -1234566.7," +
                " -1234566.999, -1234567, -1234566.99, -1234566.99, -1234566.99, -1234566, -1234566.99, -1234564," +
                " -1234566.98, -1234566.98, -1234520, -1234566, -1234565.5]",
                "{-1234567=285456, -1234566.9=107903, -1234566=95521, -1234566.99=53436, -1234565=32057," +
                " -1234564=31423, -1234566.8=26790, -1234566.7=26779, -1234566.999=26585, -1234566.98=13319}",
                -1231859.5186467117,
                23.6881670000228,
                0.85546299999781
        );
        rangeUpCanonical_BigDecimal_helper(
                5,
                3,
                "-1234567",
                "[-1234566.999979, -1234566.999999987, -1234566.923426, -1234566, -1234566.75, -1234566.9999," +
                " -1234566.99999998, -1234566.99994, -1234560.8, -1234567, -1234566.999999999999, -1234566.995," +
                " -1234566.999998, -1234566.9998, -1234566, -1234566.999999988, -1234552.07, -1234561, -1234562," +
                " -1234566.959]",
                "{-1234567=79948, -1234566.9=36209, -1234566=33212, -1234566.99=26729, -1234566.999=20229," +
                " -1234566.9999=15310, -1234566.7=14469, -1234566.8=14308, -1234564=13995, -1234565=13866}",
                6.343394780554712E13,
                30.37944799986068,
                2.8847469999916115
        );
        rangeUpCanonical_BigDecimal_helper(
                32,
                8,
                "-1234567",
                "[-1175810.610208562, -1232932.419311531, 106606894.3, -1234566.77187, -1234566.99999999999999994," +
                " -1234566.9999999992219, -1032731, -1234566.999563, -1126353, 932980817810.2, 21226698679128979.52," +
                " -1234566.76479532, 2721691398634540.352, 23960188.0898504, -1234566.917," +
                " -1234566.999999999999999999999999999999999999999999999288, -1234566.9999," +
                " -1234566.999999994974907992156, -1234566.9999999998979066, -1234566.999999999]",
                "{-1234567=6733, -1234566=3213, -1234566.9=3152, -1234566.99=2732, -1234566.999=2418," +
                " -1234566.9999=2140, -1234566.99999=1961, -1234566.999999=1705, -1234565=1611, -1234564=1563}",
                5.909010473233115E121,
                56.36141300006903,
                7.96111800001629
        );
        rangeUpCanonical_BigDecimal_helper(
                2,
                1,
                "0.09",
                "[0.103, 0.1, 0.090000221, 0.1, 0.1, 10.09, 0.39, 0.091, 0.09, 0.1, 0.1, 0.1, 1.09, 0.1, 3.09, 0.11," +
                " 0.11, 47.09, 1.09, 1.59]",
                "{0.09=285456, 0.19=107903, 1.09=95521, 0.1=53436, 2.09=32057, 3.09=31423, 0.29=26790, 0.39=26779," +
                " 0.091=26585, 0.11=13319}",
                2707.5713529020204,
                6.446059999963675,
                2.157861999998554
        );
        rangeUpCanonical_BigDecimal_helper(
                5,
                3,
                "0.09",
                "[0.090021, 0.090000013, 0.166574, 1.09, 0.34, 0.0901, 0.09000002, 0.09006, 6.29, 0.09," +
                " 0.090000000001, 0.095, 0.090002, 0.0902, 1.09, 0.090000012, 15.02, 6.09, 5.09, 0.131]",
                "{0.09=79948, 0.19=36209, 1.09=33212, 0.1=26729, 0.091=20229, 0.0901=15310, 0.39=14469, 0.29=14308," +
                " 3.09=13995, 2.09=13866}",
                6.343394903999121E13,
                12.192118999986327,
                3.589238999959292
        );
        rangeUpCanonical_BigDecimal_helper(
                32,
                8,
                "0.09",
                "[58756.479791438, 1634.670688469, 107841461.39, 0.31813, 0.09000000000000006, 0.0900000007781," +
                " 201836.09, 0.090437, 108214.09, 932982052377.29, 21226698680363546.61, 0.32520468," +
                " 2721691399869107.442, 25194755.1798504, 0.173, 0.090000000000000000000000000000000000000000000712," +
                " 0.0901, 0.090000005025092007844, 0.0900000001020934, 0.090000001]",
                "{0.09=6733, 1.09=3213, 0.19=3152, 0.1=2732, 0.091=2418, 0.0901=2140, 0.09001=1961, 0.090001=1705," +
                " 2.09=1611, 3.09=1563}",
                5.909010473233115E121,
                44.04681600002423,
                8.275694000030649
        );
        rangeUpCanonical_BigDecimal_helper(
                2,
                1,
                "-0.09",
                "[-0.077, -0.08, -0.089999779, -0.08, -0.08, 9.91, 0.21, -0.089, -0.09, -0.08, -0.08, -0.08, 0.91," +
                " -0.08, 2.91, -0.07, -0.07, 46.91, 0.91, 1.41]",
                "{-0.09=285456, 0.01=107903, 0.91=95521, -0.08=53436, 1.91=32057, 2.91=31423, 0.11=26790," +
                " 0.21=26779, -0.089=26585, -0.07=13319}",
                2707.391352922042,
                6.018661999987186,
                2.210399999996495
        );
        rangeUpCanonical_BigDecimal_helper(
                5,
                3,
                "-0.09",
                "[-0.089979, -0.089999987, -0.013426, 0.91, 0.16, -0.0899, -0.08999998, -0.08994, 6.11, -0.09," +
                " -0.089999999999, -0.085, -0.089998, -0.0898, 0.91, -0.089999988, 14.84, 5.91, 4.91, -0.049]",
                "{-0.09=79948, 0.01=36209, 0.91=33212, -0.08=26729, -0.089=20229, -0.0899=15310, 0.21=14469," +
                " 0.11=14308, 2.91=13995, 1.91=13866}",
                6.34339490399912E13,
                11.887378999988956,
                3.614251999955982
        );
        rangeUpCanonical_BigDecimal_helper(
                32,
                8,
                "-0.09",
                "[58756.299791438, 1634.490688469, 107841461.21, 0.13813, -0.08999999999999994, -0.0899999992219," +
                " 201835.91, -0.089563, 108213.91, 932982052377.11, 21226698680363546.43, 0.14520468," +
                " 2721691399869107.262, 25194754.9998504, -0.007," +
                " -0.089999999999999999999999999999999999999999999288, -0.0899, -0.089999994974907992156," +
                " -0.0899999998979066, -0.089999999]",
                "{-0.09=6733, 0.91=3213, 0.01=3152, -0.08=2732, -0.089=2418, -0.0899=2140, -0.08999=1961," +
                " -0.089999=1705, 1.91=1611, 2.91=1563}",
                5.909010473233115E121,
                43.918455000014866,
                8.27842300003079
        );
        rangeUpCanonical_BigDecimal_helper(
                2,
                1,
                "1E-12",
                "[0.013000000001, 0.010000000001, 2.21001E-7, 0.010000000001, 0.010000000001, 10.000000000001," +
                " 0.300000000001, 0.001000000001, 1E-12, 0.010000000001, 0.010000000001, 0.010000000001," +
                " 1.000000000001, 0.010000000001, 3.000000000001, 0.020000000001, 0.020000000001, 47.000000000001," +
                " 1.000000000001, 1.500000000001]",
                "{1E-12=285456, 0.100000000001=107903, 1.000000000001=95521, 0.010000000001=53436," +
                " 2.000000000001=32057, 3.000000000001=31423, 0.200000000001=26790, 0.300000000001=26779," +
                " 0.001000000001=26585, 0.020000000001=13319}",
                2707.4813529248736,
                27.412007999985022,
                12.000224999910095
        );
        rangeUpCanonical_BigDecimal_helper(
                5,
                3,
                "1E-12",
                "[0.000021000001, 1.3001E-8, 0.076574000001, 1.000000000001, 0.250000000001, 0.000100000001," +
                " 2.0001E-8, 0.000060000001, 6.200000000001, 1E-12, 2E-12, 0.005000000001, 0.000002000001," +
                " 0.000200000001, 1.000000000001, 1.2001E-8, 14.930000000001, 6.000000000001, 5.000000000001," +
                " 0.041000000001]",
                "{1E-12=79948, 0.100000000001=36209, 1.000000000001=33212, 0.010000000001=26729," +
                " 0.001000000001=20229, 0.000100000001=15310, 0.300000000001=14469, 0.200000000001=14308," +
                " 3.000000000001=13995, 2.000000000001=13866}",
                6.34339490399912E13,
                32.38428999998984,
                12.090880999911114
        );
        rangeUpCanonical_BigDecimal_helper(
                32,
                8,
                "1E-12",
                "[58756.389791438001, 1634.580688469001, 107841461.300000000001, 0.228130000001, 1.00006E-12," +
                " 7.791E-10, 201836.000000000001, 0.000437000001, 108214.000000000001, 932982052377.200000000001," +
                " 21226698680363546.520000000001, 0.235204680001, 2721691399869107.352000000001," +
                " 25194755.089850400001, 0.083000000001, 1.000000000000000000000000000000000712E-12, 0.000100000001," +
                " 5.026092007844E-9, 1.030934E-10, 1.001E-9]",
                "{1E-12=6733, 1.000000000001=3213, 0.100000000001=3152, 0.010000000001=2732, 0.001000000001=2418," +
                " 0.000100000001=2140, 0.000010000001=1961, 0.000001000001=1705, 2.000000000001=1611," +
                " 3.000000000001=1563}",
                5.909010473233115E121,
                54.549446000020495,
                13.934181999922217
        );
        rangeUpCanonical_BigDecimal_helper(
                2,
                1,
                "1E+12",
                "[1000000000000.013, 1000000000000.01, 1000000000000.000000221, 1000000000000.01, 1000000000000.01," +
                " 1000000000010, 1000000000000.3, 1000000000000.001, 1000000000000, 1000000000000.01," +
                " 1000000000000.01, 1000000000000.01, 1000000000001, 1000000000000.01, 1000000000003," +
                " 1000000000000.02, 1000000000000.02, 1000000000047, 1000000000001, 1000000000001.5]",
                "{1000000000000=285456, 1000000000000.1=107903, 1000000000001=95521, 1000000000000.01=53436," +
                " 1000000000002=32057, 1000000000003=31423, 1000000000000.2=26790, 1000000000000.3=26779," +
                " 1000000000000.001=26585, 1000000000000.02=13319}",
                1.0000000027062606E12,
                43.055837999641426,
                0.85546299999781
        );
        rangeUpCanonical_BigDecimal_helper(
                5,
                3,
                "1E+12",
                "[1000000000000.000021, 1000000000000.000000013, 1000000000000.076574, 1000000000001," +
                " 1000000000000.25, 1000000000000.0001, 1000000000000.00000002, 1000000000000.00006," +
                " 1000000000006.2, 1000000000000, 1000000000000.000000000001, 1000000000000.005," +
                " 1000000000000.000002, 1000000000000.0002, 1000000000001, 1000000000000.000000012," +
                " 1000000000014.93, 1000000000006, 1000000000005, 1000000000000.041]",
                "{1000000000000=79948, 1000000000000.1=36209, 1000000000001=33212, 1000000000000.01=26729," +
                " 1000000000000.001=20229, 1000000000000.0001=15310, 1000000000000.3=14469, 1000000000000.2=14308," +
                " 1000000000003=13995, 1000000000002=13866}",
                6.4433949039991016E13,
                49.90051999995854,
                2.8847469999916115
        );
        rangeUpCanonical_BigDecimal_helper(
                32,
                8,
                "1E+12",
                "[1000000058756.389791438, 1000000001634.580688469, 1000107841461.3, 1000000000000.22813," +
                " 1000000000000.00000000000000006, 1000000000000.0000000007781, 1000000201836, 1000000000000.000437," +
                " 1000000108214, 1932982052377.2, 21227698680363546.52, 1000000000000.23520468," +
                " 2722691399869107.352, 1000025194755.0898504, 1000000000000.083," +
                " 1000000000000.000000000000000000000000000000000000000000000712, 1000000000000.0001," +
                " 1000000000000.000000005025092007844, 1000000000000.0000000001020934, 1000000000000.000000001]",
                "{1000000000000=6733, 1000000000001=3213, 1000000000000.1=3152, 1000000000000.01=2732," +
                " 1000000000000.001=2418, 1000000000000.0001=2140, 1000000000000.00001=1961," +
                " 1000000000000.000001=1705, 1000000000002=1611, 1000000000003=1563}",
                5.909010473233115E121,
                71.8105160000045,
                7.96111800001629
        );
        rangeUpCanonical_BigDecimal_fail_helper(1, 1, "0");
        rangeUpCanonical_BigDecimal_fail_helper(2, 0, "0");
    }

    private static void nextFromRangeDownCanonical_BigDecimal_helper(
            int scale,
            int secondaryScale,
            @NotNull String a,
            @NotNull String output
    ) {
        aeq(
                P.withScale(scale).withSecondaryScale(secondaryScale)
                        .nextFromRangeDownCanonical(Readers.readBigDecimal(a).get()),
                output
        );
        P.reset();
    }

    private static void nextFromRangeDownCanonical_BigDecimal_fail_helper(
            int scale,
            int secondaryScale,
            @NotNull String a
    ) {
        try {
            P.withScale(scale).withSecondaryScale(secondaryScale)
                    .nextFromRangeDownCanonical(Readers.readBigDecimal(a).get());
            fail();
        } catch (IllegalStateException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testNextFromRangeDownCanonical_BigDecimal() {
        nextFromRangeDownCanonical_BigDecimal_helper(2, 1, "0", "-0.005");
        nextFromRangeDownCanonical_BigDecimal_helper(5, 3, "0", "-0.000013");
        nextFromRangeDownCanonical_BigDecimal_helper(32, 8, "0", "-4.04936997654063E-17");
        nextFromRangeDownCanonical_BigDecimal_helper(2, 1, "0.0", "-0.013");
        nextFromRangeDownCanonical_BigDecimal_helper(5, 3, "0.0", "-0.000021");
        nextFromRangeDownCanonical_BigDecimal_helper(32, 8, "0.0", "-58756.389791438");
        nextFromRangeDownCanonical_BigDecimal_helper(2, 1, "1", "0.987");
        nextFromRangeDownCanonical_BigDecimal_helper(5, 3, "1", "0.999979");
        nextFromRangeDownCanonical_BigDecimal_helper(32, 8, "1", "-58755.389791438");
        nextFromRangeDownCanonical_BigDecimal_helper(2, 1, "1.0", "0.987");
        nextFromRangeDownCanonical_BigDecimal_helper(5, 3, "1.0", "0.999979");
        nextFromRangeDownCanonical_BigDecimal_helper(32, 8, "1.0", "-58755.389791438");
        nextFromRangeDownCanonical_BigDecimal_helper(2, 1, "-1.0", "-1.013");
        nextFromRangeDownCanonical_BigDecimal_helper(5, 3, "-1.0", "-1.000021");
        nextFromRangeDownCanonical_BigDecimal_helper(32, 8, "-1.0", "-58757.389791438");
        nextFromRangeDownCanonical_BigDecimal_helper(2, 1, "9", "8.987");
        nextFromRangeDownCanonical_BigDecimal_helper(5, 3, "9", "8.999979");
        nextFromRangeDownCanonical_BigDecimal_helper(32, 8, "9", "-58747.389791438");
        nextFromRangeDownCanonical_BigDecimal_helper(2, 1, "-9", "-9.013");
        nextFromRangeDownCanonical_BigDecimal_helper(5, 3, "-9", "-9.000021");
        nextFromRangeDownCanonical_BigDecimal_helper(32, 8, "-9", "-58765.389791438");
        nextFromRangeDownCanonical_BigDecimal_helper(2, 1, "10", "9.987");
        nextFromRangeDownCanonical_BigDecimal_helper(5, 3, "10", "9.999979");
        nextFromRangeDownCanonical_BigDecimal_helper(32, 8, "10", "-58746.389791438");
        nextFromRangeDownCanonical_BigDecimal_helper(2, 1, "-10", "-10.013");
        nextFromRangeDownCanonical_BigDecimal_helper(5, 3, "-10", "-10.000021");
        nextFromRangeDownCanonical_BigDecimal_helper(32, 8, "-10", "-58766.389791438");
        nextFromRangeDownCanonical_BigDecimal_helper(2, 1, "101", "100.987");
        nextFromRangeDownCanonical_BigDecimal_helper(5, 3, "101", "100.999979");
        nextFromRangeDownCanonical_BigDecimal_helper(32, 8, "101", "-58655.389791438");
        nextFromRangeDownCanonical_BigDecimal_helper(2, 1, "-101", "-101.013");
        nextFromRangeDownCanonical_BigDecimal_helper(5, 3, "-101", "-101.000021");
        nextFromRangeDownCanonical_BigDecimal_helper(32, 8, "-101", "-58857.389791438");
        nextFromRangeDownCanonical_BigDecimal_helper(2, 1, "1234567", "1234566.987");
        nextFromRangeDownCanonical_BigDecimal_helper(5, 3, "1234567", "1234566.999979");
        nextFromRangeDownCanonical_BigDecimal_helper(32, 8, "1234567", "1175810.610208562");
        nextFromRangeDownCanonical_BigDecimal_helper(2, 1, "-1234567", "-1234567.013");
        nextFromRangeDownCanonical_BigDecimal_helper(5, 3, "-1234567", "-1234567.000021");
        nextFromRangeDownCanonical_BigDecimal_helper(32, 8, "-1234567", "-1293323.389791438");
        nextFromRangeDownCanonical_BigDecimal_helper(2, 1, "0.09", "0.077");
        nextFromRangeDownCanonical_BigDecimal_helper(5, 3, "0.09", "0.089979");
        nextFromRangeDownCanonical_BigDecimal_helper(32, 8, "0.09", "-58756.299791438");
        nextFromRangeDownCanonical_BigDecimal_helper(2, 1, "-0.09", "-0.103");
        nextFromRangeDownCanonical_BigDecimal_helper(5, 3, "-0.09", "-0.090021");
        nextFromRangeDownCanonical_BigDecimal_helper(32, 8, "-0.09", "-58756.479791438");
        nextFromRangeDownCanonical_BigDecimal_helper(2, 1, "1E-12", "-0.012999999999");
        nextFromRangeDownCanonical_BigDecimal_helper(5, 3, "1E-12", "-0.000020999999");
        nextFromRangeDownCanonical_BigDecimal_helper(32, 8, "1E-12", "-58756.389791437999");
        nextFromRangeDownCanonical_BigDecimal_helper(2, 1, "1E+12", "999999999999.987");
        nextFromRangeDownCanonical_BigDecimal_helper(5, 3, "1E+12", "999999999999.999979");
        nextFromRangeDownCanonical_BigDecimal_helper(32, 8, "1E+12", "999999941243.610208562");
        nextFromRangeDownCanonical_BigDecimal_fail_helper(1, 1, "0");
        nextFromRangeDownCanonical_BigDecimal_fail_helper(2, 0, "0");
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
                topSampleCount,
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
                "[-0.005, -7E-7, 0, -6, -0.7, -0.2, -6, 0, 0, -2.5, -0.01, 0, 0, -0.001, 0, -1, 0, -0.1, 0, -0.01]",
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
                " -0.00001, -0.9, -3722, -0.0001, -0.3, -0.0002]",
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
                " -0.005375094279919512, -3, -0.00148719799, -0.00075]",
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
                "[-0.013, -0.01, -2.21E-7, -0.01, -0.01, -10, -0.3, -0.001, 0, -0.01, -0.01, -0.01, -1, -0.01, -3," +
                " -0.02, -0.02, -47, -1, -1.5]",
                "{0=285456, -0.1=107903, -1=95521, -0.01=53436, -2=32057, -3=31423, -0.2=26790, -0.3=26779," +
                " -0.001=26585, -0.02=13319}",
                -2707.4813529248736,
                1.7140359999883126,
                0.85546299999781
        );
        rangeDownCanonical_BigDecimal_helper(
                5,
                3,
                "0.0",
                "[-0.000021, -1.3E-8, -0.076574, -1, -0.25, -0.0001, -2E-8, -0.00006, -6.2, 0, -1E-12, -0.005," +
                " -0.000002, -0.0002, -1, -1.2E-8, -14.93, -6, -5, -0.041]",
                "{0=79948, -0.1=36209, -1=33212, -0.01=26729, -0.001=20229, -0.0001=15310, -0.3=14469, -0.2=14308," +
                " -3=13995, -2=13866}",
                -6.34339490399912E13,
                4.805518000004578,
                2.8847469999916115
        );
        rangeDownCanonical_BigDecimal_helper(
                32,
                8,
                "0.0",
                "[-58756.389791438, -1634.580688469, -107841461.3, -0.22813, -6E-17, -7.781E-10, -201836, -0.000437," +
                " -108214, -932982052377.2, -21226698680363546.52, -0.23520468, -2721691399869107.352," +
                " -25194755.0898504, -0.083, -7.12E-46, -0.0001, -5.025092007844E-9, -1.020934E-10, -1E-9]",
                "{0=6733, -1=3213, -0.1=3152, -0.01=2732, -0.001=2418, -0.0001=2140, -0.00001=1961, -0.000001=1705," +
                " -2=1611, -3=1563}",
                -5.909010473233115E121,
                31.902074000021894,
                7.96111800001629
        );
        rangeDownCanonical_BigDecimal_helper(
                2,
                1,
                "1",
                "[0.987, 0.99, 0.999999779, 0.99, 0.99, -9, 0.7, 0.999, 1, 0.99, 0.99, 0.99, 0, 0.99, -2, 0.98," +
                " 0.98, -46, 0, -0.5]",
                "{1=285456, 0.9=107903, 0=95521, 0.99=53436, -1=32057, -2=31423, 0.8=26790, 0.7=26779, 0.999=26585," +
                " 0.98=13319}",
                -2706.4813529360126,
                3.9844060000250554,
                0.85546299999781
        );
        rangeDownCanonical_BigDecimal_helper(
                5,
                3,
                "1",
                "[0.999979, 0.999999987, 0.923426, 0, 0.75, 0.9999, 0.99999998, 0.99994, -5.2, 1, 0.999999999999," +
                " 0.995, 0.999998, 0.9998, 0, 0.999999988, -13.93, -5, -4, 0.959]",
                "{1=79948, 0.9=36209, 0=33212, 0.99=26729, 0.999=20229, 0.9999=15310, 0.7=14469, 0.8=14308," +
                " -2=13995, -1=13866}",
                -6.343394903999108E13,
                11.564370999974305,
                2.8847469999916115
        );
        rangeDownCanonical_BigDecimal_helper(
                32,
                8,
                "1",
                "[-58755.389791438, -1633.580688469, -107841460.3, 0.77187, 0.99999999999999994, 0.9999999992219," +
                " -201835, 0.999563, -108213, -932982052376.2, -21226698680363545.52, 0.76479532," +
                " -2721691399869106.352, -25194754.0898504, 0.917," +
                " 0.999999999999999999999999999999999999999999999288, 0.9999, 0.999999994974907992156," +
                " 0.9999999998979066, 0.999999999]",
                "{1=6733, 0=3213, 0.9=3152, 0.99=2732, 0.999=2418, 0.9999=2140, 0.99999=1961, 0.999999=1705," +
                " -1=1611, -2=1563}",
                -5.909010473233115E121,
                44.30726200000445,
                7.96111800001629
        );
        rangeDownCanonical_BigDecimal_helper(
                2,
                1,
                "1.0",
                "[0.987, 0.99, 0.999999779, 0.99, 0.99, -9, 0.7, 0.999, 1, 0.99, 0.99, 0.99, 0, 0.99, -2, 0.98," +
                " 0.98, -46, 0, -0.5]",
                "{1=285456, 0.9=107903, 0=95521, 0.99=53436, -1=32057, -2=31423, 0.8=26790, 0.7=26779, 0.999=26585," +
                " 0.98=13319}",
                -2706.4813529360126,
                3.9844060000250554,
                0.85546299999781
        );
        rangeDownCanonical_BigDecimal_helper(
                5,
                3,
                "1.0",
                "[0.999979, 0.999999987, 0.923426, 0, 0.75, 0.9999, 0.99999998, 0.99994, -5.2, 1, 0.999999999999," +
                " 0.995, 0.999998, 0.9998, 0, 0.999999988, -13.93, -5, -4, 0.959]",
                "{1=79948, 0.9=36209, 0=33212, 0.99=26729, 0.999=20229, 0.9999=15310, 0.7=14469, 0.8=14308," +
                " -2=13995, -1=13866}",
                -6.343394903999108E13,
                11.564370999974305,
                2.8847469999916115
        );
        rangeDownCanonical_BigDecimal_helper(
                32,
                8,
                "1.0",
                "[-58755.389791438, -1633.580688469, -107841460.3, 0.77187, 0.99999999999999994, 0.9999999992219," +
                " -201835, 0.999563, -108213, -932982052376.2, -21226698680363545.52, 0.76479532," +
                " -2721691399869106.352, -25194754.0898504, 0.917," +
                " 0.999999999999999999999999999999999999999999999288, 0.9999, 0.999999994974907992156," +
                " 0.9999999998979066, 0.999999999]",
                "{1=6733, 0=3213, 0.9=3152, 0.99=2732, 0.999=2418, 0.9999=2140, 0.99999=1961, 0.999999=1705," +
                " -1=1611, -2=1563}",
                -5.909010473233115E121,
                44.30726200000445,
                7.96111800001629
        );
        rangeDownCanonical_BigDecimal_helper(
                2,
                1,
                "-1.0",
                "[-1.013, -1.01, -1.000000221, -1.01, -1.01, -11, -1.3, -1.001, -1, -1.01, -1.01, -1.01, -2, -1.01," +
                " -4, -1.02, -1.02, -48, -2, -2.5]",
                "{-1=285456, -1.1=107903, -2=95521, -1.01=53436, -3=32057, -4=31423, -1.2=26790, -1.3=26779," +
                " -1.001=26585, -1.02=13319}",
                -2708.481352935786,
                4.407680000017143,
                0.85546299999781
        );
        rangeDownCanonical_BigDecimal_helper(
                5,
                3,
                "-1.0",
                "[-1.000021, -1.000000013, -1.076574, -2, -1.25, -1.0001, -1.00000002, -1.00006, -7.2, -1," +
                " -1.000000000001, -1.005, -1.000002, -1.0002, -2, -1.000000012, -15.93, -7, -6, -1.041]",
                "{-1=79948, -1.1=36209, -2=33212, -1.01=26729, -1.001=20229, -1.0001=15310, -1.3=14469, -1.2=14308," +
                " -4=13995, -3=13866}",
                -6.343394903999134E13,
                11.937791999983835,
                2.8847469999916115
        );
        rangeDownCanonical_BigDecimal_helper(
                32,
                8,
                "-1.0",
                "[-58757.389791438, -1635.580688469, -107841462.3, -1.22813, -1.00000000000000006, -1.0000000007781," +
                " -201837, -1.000437, -108215, -932982052378.2, -21226698680363547.52, -1.23520468," +
                " -2721691399869108.352, -25194756.0898504, -1.083," +
                " -1.000000000000000000000000000000000000000000000712, -1.0001, -1.000000005025092007844," +
                " -1.0000000001020934, -1.000000001]",
                "{-1=6733, -2=3213, -1.1=3152, -1.01=2732, -1.001=2418, -1.0001=2140, -1.00001=1961, -1.000001=1705," +
                " -3=1611, -4=1563}",
                -5.909010473233115E121,
                44.45722600001309,
                7.96111800001629
        );
        rangeDownCanonical_BigDecimal_helper(
                2,
                1,
                "9",
                "[8.987, 8.99, 8.999999779, 8.99, 8.99, -1, 8.7, 8.999, 9, 8.99, 8.99, 8.99, 8, 8.99, 6, 8.98, 8.98," +
                " -38, 8, 7.5]",
                "{9=285456, 8.9=107903, 8=95521, 8.99=53436, 7=32057, 6=31423, 8.8=26790, 8.7=26779, 8.999=26585," +
                " 8.98=13319}",
                -2698.481352934637,
                6.6437579999790835,
                0.85546299999781
        );
        rangeDownCanonical_BigDecimal_helper(
                5,
                3,
                "9",
                "[8.999979, 8.999999987, 8.923426, 8, 8.75, 8.9999, 8.99999998, 8.99994, 2.8, 9, 8.999999999999," +
                " 8.995, 8.999998, 8.9998, 8, 8.999999988, -5.93, 3, 4, 8.959]",
                "{9=79948, 8.9=36209, 8=33212, 8.99=26729, 8.999=20229, 8.9999=15310, 8.7=14469, 8.8=14308, 6=13995," +
                " 7=13866}",
                -6.343394903998996E13,
                14.020057999981177,
                2.8847469999916115
        );
        rangeDownCanonical_BigDecimal_helper(
                32,
                8,
                "9",
                "[-58747.389791438, -1625.580688469, -107841452.3, 8.77187, 8.99999999999999994, 8.9999999992219," +
                " -201827, 8.999563, -108205, -932982052368.2, -21226698680363537.52, 8.76479532," +
                " -2721691399869098.352, -25194746.0898504, 8.917," +
                " 8.999999999999999999999999999999999999999999999288, 8.9999, 8.999999994974907992156," +
                " 8.9999999998979066, 8.999999999]",
                "{9=6733, 8=3213, 8.9=3152, 8.99=2732, 8.999=2418, 8.9999=2140, 8.99999=1961, 8.999999=1705, 7=1611," +
                " 6=1563}",
                -5.909010473233115E121,
                45.827458000024365,
                7.96111800001629
        );
        rangeDownCanonical_BigDecimal_helper(
                2,
                1,
                "-9",
                "[-9.013, -9.01, -9.000000221, -9.01, -9.01, -19, -9.3, -9.001, -9, -9.01, -9.01, -9.01, -10, -9.01," +
                " -12, -9.02, -9.02, -56, -10, -10.5]",
                "{-9=285456, -9.1=107903, -10=95521, -9.01=53436, -11=32057, -12=31423, -9.2=26790, -9.3=26779," +
                " -9.001=26585, -9.02=13319}",
                -2716.4813529376775,
                6.921292999942901,
                0.85546299999781
        );
        rangeDownCanonical_BigDecimal_helper(
                5,
                3,
                "-9",
                "[-9.000021, -9.000000013, -9.076574, -10, -9.25, -9.0001, -9.00000002, -9.00006, -15.2, -9," +
                " -9.000000000001, -9.005, -9.000002, -9.0002, -10, -9.000000012, -23.93, -15, -14, -9.041]",
                "{-9=79948, -9.1=36209, -10=33212, -9.01=26729, -9.001=20229, -9.0001=15310, -9.3=14469, -9.2=14308," +
                " -12=13995, -11=13866}",
                -6.343394903999254E13,
                14.319413999988011,
                2.8847469999916115
        );
        rangeDownCanonical_BigDecimal_helper(
                32,
                8,
                "-9",
                "[-58765.389791438, -1643.580688469, -107841470.3, -9.22813, -9.00000000000000006, -9.0000000007781," +
                " -201845, -9.000437, -108223, -932982052386.2, -21226698680363555.52, -9.23520468," +
                " -2721691399869116.352, -25194764.0898504, -9.083," +
                " -9.000000000000000000000000000000000000000000000712, -9.0001, -9.000000005025092007844," +
                " -9.0000000001020934, -9.000000001]",
                "{-9=6733, -10=3213, -9.1=3152, -9.01=2732, -9.001=2418, -9.0001=2140, -9.00001=1961," +
                " -9.000001=1705, -11=1611, -12=1563}",
                -5.909010473233115E121,
                45.97913000002949,
                7.96111800001629
        );
        rangeDownCanonical_BigDecimal_helper(
                2,
                1,
                "10",
                "[9.987, 9.99, 9.999999779, 9.99, 9.99, 0, 9.7, 9.999, 10, 9.99, 9.99, 9.99, 9, 9.99, 7, 9.98, 9.98," +
                " -37, 9, 8.5]",
                "{10=285456, 9.9=107903, 9=95521, 9.99=53436, 8=32057, 7=31423, 9.8=26790, 9.7=26779, 9.999=26585," +
                " 9.98=13319}",
                -2697.481352905077,
                6.682550999971169,
                0.85546299999781
        );
        rangeDownCanonical_BigDecimal_helper(
                5,
                3,
                "10",
                "[9.999979, 9.999999987, 9.923426, 9, 9.75, 9.9999, 9.99999998, 9.99994, 3.8, 10, 9.999999999999," +
                " 9.995, 9.999998, 9.9998, 9, 9.999999988, -4.93, 4, 5, 9.959]",
                "{10=79948, 9.9=36209, 9=33212, 9.99=26729, 9.999=20229, 9.9999=15310, 9.7=14469, 9.8=14308," +
                " 7=13995, 8=13866}",
                -6.343394903998982E13,
                14.051392999984342,
                2.8847469999916115
        );
        rangeDownCanonical_BigDecimal_helper(
                32,
                8,
                "10",
                "[-58746.389791438, -1624.580688469, -107841451.3, 9.77187, 9.99999999999999994, 9.9999999992219," +
                " -201826, 9.999563, -108204, -932982052367.2, -21226698680363536.52, 9.76479532," +
                " -2721691399869097.352, -25194745.0898504, 9.917," +
                " 9.999999999999999999999999999999999999999999999288, 9.9999, 9.999999994974907992156," +
                " 9.9999999998979066, 9.999999999]",
                "{10=6733, 9=3213, 9.9=3152, 9.99=2732, 9.999=2418, 9.9999=2140, 9.99999=1961, 9.999999=1705," +
                " 8=1611, 7=1563}",
                -5.909010473233115E121,
                45.884170000011494,
                7.96111800001629
        );
        rangeDownCanonical_BigDecimal_helper(
                2,
                1,
                "-10",
                "[-10.013, -10.01, -10.000000221, -10.01, -10.01, -20, -10.3, -10.001, -10, -10.01, -10.01, -10.01," +
                " -11, -10.01, -13, -10.02, -10.02, -57, -11, -11.5]",
                "{-10=285456, -10.1=107903, -11=95521, -10.01=53436, -12=32057, -13=31423, -10.2=26790, -10.3=26779," +
                " -10.001=26585, -10.02=13319}",
                -2717.4813529716844,
                6.940084999942944,
                0.85546299999781
        );
        rangeDownCanonical_BigDecimal_helper(
                5,
                3,
                "-10",
                "[-10.000021, -10.000000013, -10.076574, -11, -10.25, -10.0001, -10.00000002, -10.00006, -16.2, -10," +
                " -10.000000000001, -10.005, -10.000002, -10.0002, -11, -10.000000012, -24.93, -16, -15, -10.041]",
                "{-10=79948, -10.1=36209, -11=33212, -10.01=26729, -10.001=20229, -10.0001=15310, -10.3=14469," +
                " -10.2=14308, -13=13995, -12=13866}",
                -6.343394903999273E13,
                14.361414999996748,
                2.8847469999916115
        );
        rangeDownCanonical_BigDecimal_helper(
                32,
                8,
                "-10",
                "[-58766.389791438, -1644.580688469, -107841471.3, -10.22813, -10.00000000000000006," +
                " -10.0000000007781, -201846, -10.000437, -108224, -932982052387.2, -21226698680363556.52," +
                " -10.23520468, -2721691399869117.352, -25194765.0898504, -10.083," +
                " -10.000000000000000000000000000000000000000000000712, -10.0001, -10.000000005025092007844," +
                " -10.0000000001020934, -10.000000001]",
                "{-10=6733, -11=3213, -10.1=3152, -10.01=2732, -10.001=2418, -10.0001=2140, -10.00001=1961," +
                " -10.000001=1705, -12=1611, -13=1563}",
                -5.909010473233115E121,
                46.03847400001957,
                7.96111800001629
        );
        rangeDownCanonical_BigDecimal_helper(
                2,
                1,
                "101",
                "[100.987, 100.99, 100.999999779, 100.99, 100.99, 91, 100.7, 100.999, 101, 100.99, 100.99, 100.99," +
                " 100, 100.99, 98, 100.98, 100.98, 54, 100, 99.5]",
                "{101=285456, 100.9=107903, 100=95521, 100.99=53436, 99=32057, 98=31423, 100.8=26790, 100.7=26779," +
                " 100.999=26585, 100.98=13319}",
                -2606.481352900283,
                9.818213000003182,
                0.85546299999781
        );
        rangeDownCanonical_BigDecimal_helper(
                5,
                3,
                "101",
                "[100.999979, 100.999999987, 100.923426, 100, 100.75, 100.9999, 100.99999998, 100.99994, 94.8, 101," +
                " 100.999999999999, 100.995, 100.999998, 100.9998, 100, 100.999999988, 86.07, 95, 96, 100.959]",
                "{101=79948, 100.9=36209, 100=33212, 100.99=26729, 100.999=20229, 100.9999=15310, 100.7=14469," +
                " 100.8=14308, 98=13995, 99=13866}",
                -6.3433949039954E13,
                16.9751399999362,
                2.8847469999916115
        );
        rangeDownCanonical_BigDecimal_helper(
                32,
                8,
                "101",
                "[-58655.389791438, -1533.580688469, -107841360.3, 100.77187, 100.99999999999999994," +
                " 100.9999999992219, -201735, 100.999563, -108113, -932982052276.2, -21226698680363445.52," +
                " 100.76479532, -2721691399869006.352, -25194654.0898504, 100.917," +
                " 100.999999999999999999999999999999999999999999999288, 100.9999, 100.999999994974907992156," +
                " 100.9999999998979066, 100.999999999]",
                "{101=6733, 100=3213, 100.9=3152, 100.99=2732, 100.999=2418, 100.9999=2140, 100.99999=1961," +
                " 100.999999=1705, 99=1611, 98=1563}",
                -5.909010473233115E121,
                47.64578400002272,
                7.96111800001629
        );
        rangeDownCanonical_BigDecimal_helper(
                2,
                1,
                "-101",
                "[-101.013, -101.01, -101.000000221, -101.01, -101.01, -111, -101.3, -101.001, -101, -101.01," +
                " -101.01, -101.01, -102, -101.01, -104, -101.02, -101.02, -148, -102, -102.5]",
                "{-101=285456, -101.1=107903, -102=95521, -101.01=53436, -103=32057, -104=31423, -101.2=26790," +
                " -101.3=26779, -101.001=26585, -101.02=13319}",
                -2808.481352944026,
                9.911032000003974,
                0.85546299999781
        );
        rangeDownCanonical_BigDecimal_helper(
                5,
                3,
                "-101",
                "[-101.000021, -101.000000013, -101.076574, -102, -101.25, -101.0001, -101.00000002, -101.00006," +
                " -107.2, -101, -101.000000000001, -101.005, -101.000002, -101.0002, -102, -101.000000012, -115.93," +
                " -107, -106, -101.041]",
                "{-101=79948, -101.1=36209, -102=33212, -101.01=26729, -101.001=20229, -101.0001=15310," +
                " -101.3=14469, -101.2=14308, -104=13995, -103=13866}",
                -6.343394904002908E13,
                17.185365999960027,
                2.8847469999916115
        );
        rangeDownCanonical_BigDecimal_helper(
                32,
                8,
                "-101",
                "[-58857.389791438, -1735.580688469, -107841562.3, -101.22813, -101.00000000000000006," +
                " -101.0000000007781, -201937, -101.000437, -108315, -932982052478.2, -21226698680363647.52," +
                " -101.23520468, -2721691399869208.352, -25194856.0898504, -101.083," +
                " -101.000000000000000000000000000000000000000000000712, -101.0001, -101.000000005025092007844," +
                " -101.0000000001020934, -101.000000001]",
                "{-101=6733, -102=3213, -101.1=3152, -101.01=2732, -101.001=2418, -101.0001=2140, -101.00001=1961," +
                " -101.000001=1705, -103=1611, -104=1563}",
                -5.909010473233115E121,
                47.79291900002582,
                7.96111800001629
        );
        rangeDownCanonical_BigDecimal_helper(
                2,
                1,
                "1234567",
                "[1234566.987, 1234566.99, 1234566.999999779, 1234566.99, 1234566.99, 1234557, 1234566.7," +
                " 1234566.999, 1234567, 1234566.99, 1234566.99, 1234566.99, 1234566, 1234566.99, 1234564," +
                " 1234566.98, 1234566.98, 1234520, 1234566, 1234565.5]",
                "{1234567=285456, 1234566.9=107903, 1234566=95521, 1234566.99=53436, 1234565=32057, 1234564=31423," +
                " 1234566.8=26790, 1234566.7=26779, 1234566.999=26585, 1234566.98=13319}",
                1231859.5186467117,
                23.6881670000228,
                0.85546299999781
        );
        rangeDownCanonical_BigDecimal_helper(
                5,
                3,
                "1234567",
                "[1234566.999979, 1234566.999999987, 1234566.923426, 1234566, 1234566.75, 1234566.9999," +
                " 1234566.99999998, 1234566.99994, 1234560.8, 1234567, 1234566.999999999999, 1234566.995," +
                " 1234566.999998, 1234566.9998, 1234566, 1234566.999999988, 1234552.07, 1234561, 1234562," +
                " 1234566.959]",
                "{1234567=79948, 1234566.9=36209, 1234566=33212, 1234566.99=26729, 1234566.999=20229," +
                " 1234566.9999=15310, 1234566.7=14469, 1234566.8=14308, 1234564=13995, 1234565=13866}",
                -6.343394780554712E13,
                30.37944799986068,
                2.8847469999916115
        );
        rangeDownCanonical_BigDecimal_helper(
                32,
                8,
                "1234567",
                "[1175810.610208562, 1232932.419311531, -106606894.3, 1234566.77187, 1234566.99999999999999994," +
                " 1234566.9999999992219, 1032731, 1234566.999563, 1126353, -932980817810.2, -21226698679128979.52," +
                " 1234566.76479532, -2721691398634540.352, -23960188.0898504, 1234566.917," +
                " 1234566.999999999999999999999999999999999999999999999288, 1234566.9999," +
                " 1234566.999999994974907992156, 1234566.9999999998979066, 1234566.999999999]",
                "{1234567=6733, 1234566=3213, 1234566.9=3152, 1234566.99=2732, 1234566.999=2418, 1234566.9999=2140," +
                " 1234566.99999=1961, 1234566.999999=1705, 1234565=1611, 1234564=1563}",
                -5.909010473233115E121,
                56.36141300006903,
                7.96111800001629
        );
        rangeDownCanonical_BigDecimal_helper(
                2,
                1,
                "-1234567",
                "[-1234567.013, -1234567.01, -1234567.000000221, -1234567.01, -1234567.01, -1234577, -1234567.3," +
                " -1234567.001, -1234567, -1234567.01, -1234567.01, -1234567.01, -1234568, -1234567.01, -1234570," +
                " -1234567.02, -1234567.02, -1234614, -1234568, -1234568.5]",
                "{-1234567=285456, -1234567.1=107903, -1234568=95521, -1234567.01=53436, -1234569=32057," +
                " -1234570=31423, -1234567.2=26790, -1234567.3=26779, -1234567.001=26585, -1234567.02=13319}",
                -1237274.4813450961,
                23.6885350000229,
                0.85546299999781
        );
        rangeDownCanonical_BigDecimal_helper(
                5,
                3,
                "-1234567",
                "[-1234567.000021, -1234567.000000013, -1234567.076574, -1234568, -1234567.25, -1234567.0001," +
                " -1234567.00000002, -1234567.00006, -1234573.2, -1234567, -1234567.000000000001, -1234567.005," +
                " -1234567.000002, -1234567.0002, -1234568, -1234567.000000012, -1234581.93, -1234573, -1234572," +
                " -1234567.041]",
                "{-1234567=79948, -1234567.1=36209, -1234568=33212, -1234567.01=26729, -1234567.001=20229," +
                " -1234567.0001=15310, -1234567.3=14469, -1234567.2=14308, -1234570=13995, -1234569=13866}",
                -6.34339502744356E13,
                30.3918229998628,
                2.8847469999916115
        );
        rangeDownCanonical_BigDecimal_helper(
                32,
                8,
                "-1234567",
                "[-1293323.389791438, -1236201.580688469, -109076028.3, -1234567.22813, -1234567.00000000000000006," +
                " -1234567.0000000007781, -1436403, -1234567.000437, -1342781, -932983286944.2," +
                " -21226698681598113.52, -1234567.23520468, -2721691401103674.352, -26429322.0898504, -1234567.083," +
                " -1234567.000000000000000000000000000000000000000000000712, -1234567.0001," +
                " -1234567.000000005025092007844, -1234567.0000000001020934, -1234567.000000001]",
                "{-1234567=6733, -1234568=3213, -1234567.1=3152, -1234567.01=2732, -1234567.001=2418," +
                " -1234567.0001=2140, -1234567.00001=1961, -1234567.000001=1705, -1234569=1611, -1234570=1563}",
                -5.909010473233115E121,
                56.45197000006925,
                7.96111800001629
        );
        rangeDownCanonical_BigDecimal_helper(
                2,
                1,
                "0.09",
                "[0.077, 0.08, 0.089999779, 0.08, 0.08, -9.91, -0.21, 0.089, 0.09, 0.08, 0.08, 0.08, -0.91, 0.08," +
                " -2.91, 0.07, 0.07, -46.91, -0.91, -1.41]",
                "{0.09=285456, -0.01=107903, -0.91=95521, 0.08=53436, -1.91=32057, -2.91=31423, -0.11=26790," +
                " -0.21=26779, 0.089=26585, 0.07=13319}",
                -2707.391352922042,
                6.018661999987186,
                2.210399999996495
        );
        rangeDownCanonical_BigDecimal_helper(
                5,
                3,
                "0.09",
                "[0.089979, 0.089999987, 0.013426, -0.91, -0.16, 0.0899, 0.08999998, 0.08994, -6.11, 0.09," +
                " 0.089999999999, 0.085, 0.089998, 0.0898, -0.91, 0.089999988, -14.84, -5.91, -4.91, 0.049]",
                "{0.09=79948, -0.01=36209, -0.91=33212, 0.08=26729, 0.089=20229, 0.0899=15310, -0.21=14469," +
                " -0.11=14308, -2.91=13995, -1.91=13866}",
                -6.34339490399912E13,
                11.887378999988956,
                3.614251999955982
        );
        rangeDownCanonical_BigDecimal_helper(
                32,
                8,
                "0.09",
                "[-58756.299791438, -1634.490688469, -107841461.21, -0.13813, 0.08999999999999994, 0.0899999992219," +
                " -201835.91, 0.089563, -108213.91, -932982052377.11, -21226698680363546.43, -0.14520468," +
                " -2721691399869107.262, -25194754.9998504, 0.007," +
                " 0.089999999999999999999999999999999999999999999288, 0.0899, 0.089999994974907992156," +
                " 0.0899999998979066, 0.089999999]",
                "{0.09=6733, -0.91=3213, -0.01=3152, 0.08=2732, 0.089=2418, 0.0899=2140, 0.08999=1961," +
                " 0.089999=1705, -1.91=1611, -2.91=1563}",
                -5.909010473233115E121,
                43.918455000014866,
                8.27842300003079
        );
        rangeDownCanonical_BigDecimal_helper(
                2,
                1,
                "-0.09",
                "[-0.103, -0.1, -0.090000221, -0.1, -0.1, -10.09, -0.39, -0.091, -0.09, -0.1, -0.1, -0.1, -1.09," +
                " -0.1, -3.09, -0.11, -0.11, -47.09, -1.09, -1.59]",
                "{-0.09=285456, -0.19=107903, -1.09=95521, -0.1=53436, -2.09=32057, -3.09=31423, -0.29=26790," +
                " -0.39=26779, -0.091=26585, -0.11=13319}",
                -2707.5713529020204,
                6.446059999963675,
                2.157861999998554
        );
        rangeDownCanonical_BigDecimal_helper(
                5,
                3,
                "-0.09",
                "[-0.090021, -0.090000013, -0.166574, -1.09, -0.34, -0.0901, -0.09000002, -0.09006, -6.29, -0.09," +
                " -0.090000000001, -0.095, -0.090002, -0.0902, -1.09, -0.090000012, -15.02, -6.09, -5.09, -0.131]",
                "{-0.09=79948, -0.19=36209, -1.09=33212, -0.1=26729, -0.091=20229, -0.0901=15310, -0.39=14469," +
                " -0.29=14308, -3.09=13995, -2.09=13866}",
                -6.343394903999121E13,
                12.192118999986327,
                3.589238999959292
        );
        rangeDownCanonical_BigDecimal_helper(
                32,
                8,
                "-0.09",
                "[-58756.479791438, -1634.670688469, -107841461.39, -0.31813, -0.09000000000000006," +
                " -0.0900000007781, -201836.09, -0.090437, -108214.09, -932982052377.29, -21226698680363546.61," +
                " -0.32520468, -2721691399869107.442, -25194755.1798504, -0.173," +
                " -0.090000000000000000000000000000000000000000000712, -0.0901, -0.090000005025092007844," +
                " -0.0900000001020934, -0.090000001]",
                "{-0.09=6733, -1.09=3213, -0.19=3152, -0.1=2732, -0.091=2418, -0.0901=2140, -0.09001=1961," +
                " -0.090001=1705, -2.09=1611, -3.09=1563}",
                -5.909010473233115E121,
                44.04681600002423,
                8.275694000030649
        );
        rangeDownCanonical_BigDecimal_helper(
                2,
                1,
                "1E-12",
                "[-0.012999999999, -0.009999999999, -2.20999E-7, -0.009999999999, -0.009999999999, -9.999999999999," +
                " -0.299999999999, -0.000999999999, 1E-12, -0.009999999999, -0.009999999999, -0.009999999999," +
                " -0.999999999999, -0.009999999999, -2.999999999999, -0.019999999999, -0.019999999999," +
                " -46.999999999999, -0.999999999999, -1.499999999999]",
                "{1E-12=285456, -0.099999999999=107903, -0.999999999999=95521, -0.009999999999=53436," +
                " -1.999999999999=32057, -2.999999999999=31423, -0.199999999999=26790, -0.299999999999=26779," +
                " -0.000999999999=26585, -0.019999999999=13319}",
                -2707.4813529248736,
                27.41184099998503,
                11.999635999910101
        );
        rangeDownCanonical_BigDecimal_helper(
                5,
                3,
                "1E-12",
                "[-0.000020999999, -1.2999E-8, -0.076573999999, -0.999999999999, -0.249999999999, -0.000099999999," +
                " -1.9999E-8, -0.000059999999, -6.199999999999, 1E-12, 0, -0.004999999999, -0.000001999999," +
                " -0.000199999999, -0.999999999999, -1.1999E-8, -14.929999999999, -5.999999999999, -4.999999999999," +
                " -0.040999999999]",
                "{1E-12=79948, -0.099999999999=36209, -0.999999999999=33212, -0.009999999999=26729," +
                " -0.000999999999=20229, -0.000099999999=15310, -0.299999999999=14469, -0.199999999999=14308," +
                " -2.999999999999=13995, -1.999999999999=13866}",
                -6.34339490399912E13,
                32.37078799998856,
                12.072872999911292
        );
        rangeDownCanonical_BigDecimal_helper(
                32,
                8,
                "1E-12",
                "[-58756.389791437999, -1634.580688468999, -107841461.299999999999, -0.228129999999, 9.9994E-13," +
                " -7.771E-10, -201835.999999999999, -0.000436999999, -108213.999999999999," +
                " -932982052377.199999999999, -21226698680363546.519999999999, -0.235204679999," +
                " -2721691399869107.351999999999, -25194755.089850399999, -0.082999999999," +
                " 9.99999999999999999999999999999999288E-13, -0.000099999999, -5.024092007844E-9, -1.010934E-10," +
                " -9.99E-10]",
                "{1E-12=6733, -0.999999999999=3213, -0.099999999999=3152, -0.009999999999=2732," +
                " -0.000999999999=2418, -0.000099999999=2140, -0.000009999999=1961, -9.99999E-7=1705," +
                " -1.999999999999=1611, -2.999999999999=1563}",
                -5.909010473233115E121,
                54.5122820000195,
                13.924107999922354
        );
        rangeDownCanonical_BigDecimal_helper(
                2,
                1,
                "1E+12",
                "[999999999999.987, 999999999999.99, 999999999999.999999779, 999999999999.99, 999999999999.99," +
                " 999999999990, 999999999999.7, 999999999999.999, 1000000000000, 999999999999.99, 999999999999.99," +
                " 999999999999.99, 999999999999, 999999999999.99, 999999999997, 999999999999.98, 999999999999.98," +
                " 999999999953, 999999999999, 999999999998.5]",
                "{1000000000000=285456, 999999999999.9=107903, 999999999999=95521, 999999999999.99=53436," +
                " 999999999998=32057, 999999999997=31423, 999999999999.8=26790, 999999999999.7=26779," +
                " 999999999999.999=26585, 999999999999.98=13319}",
                9.999999972937394E11,
                43.055837999641426,
                0.85546299999781
        );
        rangeDownCanonical_BigDecimal_helper(
                5,
                3,
                "1E+12",
                "[999999999999.999979, 999999999999.999999987, 999999999999.923426, 999999999999, 999999999999.75," +
                " 999999999999.9999, 999999999999.99999998, 999999999999.99994, 999999999993.8, 1000000000000," +
                " 999999999999.999999999999, 999999999999.995, 999999999999.999998, 999999999999.9998, 999999999999," +
                " 999999999999.999999988, 999999999985.07, 999999999994, 999999999995, 999999999999.959]",
                "{1000000000000=79948, 999999999999.9=36209, 999999999999=33212, 999999999999.99=26729," +
                " 999999999999.999=20229, 999999999999.9999=15310, 999999999999.7=14469, 999999999999.8=14308," +
                " 999999999997=13995, 999999999998=13866}",
                -6.2433949039991195E13,
                49.90018099995837,
                2.8847469999916115
        );
        rangeDownCanonical_BigDecimal_helper(
                32,
                8,
                "1E+12",
                "[999999941243.610208562, 999999998365.419311531, 999892158538.7, 999999999999.77187," +
                " 999999999999.99999999999999994, 999999999999.9999999992219, 999999798164, 999999999999.999563," +
                " 999999891786, 67017947622.8, -21225698680363546.52, 999999999999.76479532, -2720691399869107.352," +
                " 999974805244.9101496, 999999999999.917," +
                " 999999999999.999999999999999999999999999999999999999999999288, 999999999999.9999," +
                " 999999999999.999999994974907992156, 999999999999.9999999998979066, 999999999999.999999999]",
                "{1000000000000=6733, 999999999999=3213, 999999999999.9=3152, 999999999999.99=2732," +
                " 999999999999.999=2418, 999999999999.9999=2140, 999999999999.99999=1961, 999999999999.999999=1705," +
                " 999999999998=1611, 999999999997=1563}",
                -5.909010473233115E121,
                71.7603299999986,
                7.96111800001629
        );
        rangeDownCanonical_BigDecimal_fail_helper(1, 1, "0");
        rangeDownCanonical_BigDecimal_fail_helper(2, 0, "0");
    }

    private static void nextFromRangeCanonical_BigDecimal_BigDecimal_helper(
            int scale,
            int secondaryScale,
            @NotNull String a,
            @NotNull String b,
            @NotNull String output
    ) {
        aeq(
                P.withScale(scale).withSecondaryScale(secondaryScale)
                        .nextFromRangeCanonical(Readers.readBigDecimal(a).get(), Readers.readBigDecimal(b).get()),
                output
        );
        P.reset();
    }

    private static void nextFromRangeCanonical_BigDecimal_BigDecimal_fail_helper(
            int scale,
            int secondaryScale,
            @NotNull String a,
            @NotNull String b
    ) {
        try {
            P.withScale(scale).withSecondaryScale(secondaryScale)
                    .nextFromRangeCanonical(Readers.readBigDecimal(a).get(), Readers.readBigDecimal(b).get());
            fail();
        } catch (IllegalStateException | IllegalArgumentException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testNextFromRangeCanonical_BigDecimal_BigDecimal() {
        nextFromRangeCanonical_BigDecimal_BigDecimal_helper(1, 1, "0", "1", "0.835");
        nextFromRangeCanonical_BigDecimal_BigDecimal_helper(5, 3, "0", "1", "0.340254");
        nextFromRangeCanonical_BigDecimal_BigDecimal_helper(32, 8, "0", "1", "0.7856933403177794690940379029766");
        nextFromRangeCanonical_BigDecimal_BigDecimal_helper(1, 1, "0", "3", "2");
        nextFromRangeCanonical_BigDecimal_BigDecimal_helper(5, 3, "0", "3", "0.8474");
        nextFromRangeCanonical_BigDecimal_BigDecimal_helper(32, 8, "0", "3", "2.0932");
        nextFromRangeCanonical_BigDecimal_BigDecimal_helper(1, 1, "0", "1E+6", "835000");
        nextFromRangeCanonical_BigDecimal_BigDecimal_helper(5, 3, "0", "1E+6", "340254");
        nextFromRangeCanonical_BigDecimal_BigDecimal_helper(32, 8, "0", "1E+6", "785693.3403177794690940379029766");
        nextFromRangeCanonical_BigDecimal_BigDecimal_helper(1, 1, "0", "0.000001", "8.35E-7");
        nextFromRangeCanonical_BigDecimal_BigDecimal_helper(5, 3, "0", "0.000001", "3.40254E-7");
        nextFromRangeCanonical_BigDecimal_BigDecimal_helper(32, 8, "0", "0.000001",
                "7.856933403177794690940379029766E-7");
        nextFromRangeCanonical_BigDecimal_BigDecimal_helper(1, 1, "1", "3", "3");
        nextFromRangeCanonical_BigDecimal_BigDecimal_helper(5, 3, "1", "3", "1.8474");
        nextFromRangeCanonical_BigDecimal_BigDecimal_helper(32, 8, "1", "3", "2.6422");
        nextFromRangeCanonical_BigDecimal_BigDecimal_helper(1, 1, "1", "1E+6", "835001");
        nextFromRangeCanonical_BigDecimal_BigDecimal_helper(5, 3, "1", "1E+6", "340255");
        nextFromRangeCanonical_BigDecimal_BigDecimal_helper(32, 8, "1", "1E+6", "785694.3403177794690940379029766");
        nextFromRangeCanonical_BigDecimal_BigDecimal_helper(1, 1, "-1", "0", "-0.165");
        nextFromRangeCanonical_BigDecimal_BigDecimal_helper(5, 3, "-1", "0", "-0.659746");
        nextFromRangeCanonical_BigDecimal_BigDecimal_helper(32, 8, "-1", "0", "-0.2143066596822205309059620970234");
        nextFromRangeCanonical_BigDecimal_BigDecimal_helper(1, 1, "-3", "0", "-1");
        nextFromRangeCanonical_BigDecimal_BigDecimal_helper(5, 3, "-3", "0", "-2.1526");
        nextFromRangeCanonical_BigDecimal_BigDecimal_helper(32, 8, "-3", "0", "-0.9068");
        nextFromRangeCanonical_BigDecimal_BigDecimal_helper(1, 1, "-1E+6", "0", "-165000");
        nextFromRangeCanonical_BigDecimal_BigDecimal_helper(5, 3, "-1E+6", "0", "-659746");
        nextFromRangeCanonical_BigDecimal_BigDecimal_helper(32, 8, "-1E+6", "0", "-214306.6596822205309059620970234");
        nextFromRangeCanonical_BigDecimal_BigDecimal_helper(1, 1, "-0.000001", "0", "-1.65E-7");
        nextFromRangeCanonical_BigDecimal_BigDecimal_helper(5, 3, "-0.000001", "0", "-6.59746E-7");
        nextFromRangeCanonical_BigDecimal_BigDecimal_helper(32, 8, "-0.000001", "0",
                "-2.143066596822205309059620970234E-7");
        nextFromRangeCanonical_BigDecimal_BigDecimal_helper(1, 1, "-3", "-1", "-1");
        nextFromRangeCanonical_BigDecimal_BigDecimal_helper(5, 3, "-3", "-1", "-2.1526");
        nextFromRangeCanonical_BigDecimal_BigDecimal_helper(32, 8, "-3", "-1", "-1.3578");
        nextFromRangeCanonical_BigDecimal_BigDecimal_helper(1, 1, "-1E+6", "-1", "-165000");
        nextFromRangeCanonical_BigDecimal_BigDecimal_helper(5, 3, "-1E+6", "-1", "-659746");
        nextFromRangeCanonical_BigDecimal_BigDecimal_helper(32, 8, "-1E+6", "-1", "-214306.6596822205309059620970234");
        nextFromRangeCanonical_BigDecimal_BigDecimal_helper(1, 1, "100", "101", "100.835");
        nextFromRangeCanonical_BigDecimal_BigDecimal_helper(5, 3, "100", "101", "100.340254");
        nextFromRangeCanonical_BigDecimal_BigDecimal_helper(32, 8, "100", "101",
                "100.7856933403177794690940379029766");
        nextFromRangeCanonical_BigDecimal_BigDecimal_helper(1, 1, "2.7183", "3.1416", "2.9183");
        nextFromRangeCanonical_BigDecimal_BigDecimal_helper(5, 3, "2.7183", "3.1416", "3.058554");
        nextFromRangeCanonical_BigDecimal_BigDecimal_helper(32, 8, "2.7183", "3.1416", "2.92762");
        nextFromRangeCanonical_BigDecimal_BigDecimal_helper(1, 1, "-3.1416", "2.7183", "-1.1416");
        nextFromRangeCanonical_BigDecimal_BigDecimal_helper(5, 3, "-3.1416", "2.7183", "0.26094");
        nextFromRangeCanonical_BigDecimal_BigDecimal_helper(32, 8, "-3.1416", "2.7183", "-1.0484");
        nextFromRangeCanonical_BigDecimal_BigDecimal_helper(1, 1, "0", "0", "0");
        nextFromRangeCanonical_BigDecimal_BigDecimal_helper(5, 3, "0", "0", "0");
        nextFromRangeCanonical_BigDecimal_BigDecimal_helper(32, 8, "0", "0", "0");
        nextFromRangeCanonical_BigDecimal_BigDecimal_helper(1, 1, "0", "0.0", "0");
        nextFromRangeCanonical_BigDecimal_BigDecimal_helper(5, 3, "0", "0.0", "0");
        nextFromRangeCanonical_BigDecimal_BigDecimal_helper(32, 8, "0", "0.0", "0");
        nextFromRangeCanonical_BigDecimal_BigDecimal_helper(1, 1, "0.0", "0", "0");
        nextFromRangeCanonical_BigDecimal_BigDecimal_helper(5, 3, "0.0", "0", "0");
        nextFromRangeCanonical_BigDecimal_BigDecimal_helper(32, 8, "0.0", "0", "0");
        nextFromRangeCanonical_BigDecimal_BigDecimal_helper(1, 1, "0.0", "0.0", "0");
        nextFromRangeCanonical_BigDecimal_BigDecimal_helper(5, 3, "0.0", "0.0", "0");
        nextFromRangeCanonical_BigDecimal_BigDecimal_helper(32, 8, "0.0", "0.0", "0");
        nextFromRangeCanonical_BigDecimal_BigDecimal_helper(1, 1, "1", "1", "1");
        nextFromRangeCanonical_BigDecimal_BigDecimal_helper(5, 3, "1", "1", "1");
        nextFromRangeCanonical_BigDecimal_BigDecimal_helper(32, 8, "1", "1", "1");
        nextFromRangeCanonical_BigDecimal_BigDecimal_fail_helper(1, 1, "5", "3");
        nextFromRangeCanonical_BigDecimal_BigDecimal_fail_helper(1, 0, "0", "1");
        nextFromRangeCanonical_BigDecimal_BigDecimal_fail_helper(0, 1, "0", "1");
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
                topSampleCount,
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
                "[0.835, 1, 0.98427296, 0.2, 0, 0.6, 0.29, 1, 0.8, 0.3, 1, 0.5, 1, 1, 0, 0.4, 0.6, 0.3, 0.7, 0]",
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
                " 0.9, 0.9, 0.6045563, 0.745, 0.5418567, 0.68515816508, 0.4]",
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
                " 0.3, 0.6, 0.7782, 0.3357467208, 0.070765, 0.08]",
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
                "[2, 0, 2.9, 3, 0, 3, 0, 1, 0, 0, 0, 0, 0, 2, 0.3, 2.1, 0, 0, 0, 3]",
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
                " 0.215, 0]",
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
                " 2.210077318906600384831704918232]",
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
                " 1000000, 1000000, 0, 400000, 600000, 300000, 700000, 0]",
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
                " 900000, 900000, 900000, 604556.3, 745000, 541856.7, 685158.16508, 400000]",
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
                " 215000, 300000, 600000, 778200, 335746.7208, 70765, 80000]",
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
                " 0.000001, 0.000001, 0, 4E-7, 6E-7, 3E-7, 7E-7, 0]",
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
                " 4E-7]",
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
                " 7.0765E-8, 8E-8]",
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
                "[3, 1, 1, 1, 2, 1, 1, 1, 1, 1, 3, 1.3, 1, 1, 1, 1, 1, 1.96, 1, 2.06]",
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
                " 2.0111768, 1]",
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
                " 1.223823, 2.82282, 1.16551102, 2.5, 2.4887302, 1.8227333913, 1, 3, 2.97633654, 1, 3]",
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
                " 700001, 1, 100001, 1, 1, 1, 1]",
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
                " 900001, 604557.3, 745001, 541857.7, 685159.16508, 400001, 577714.9075, 123001]",
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
                " 600001, 778201, 335747.7208, 70766, 80001, 922961]",
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
                " -0.3, -1]",
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
                " -1, -0.1, -0.1, -0.1, -0.3954437, -0.255, -0.4581433, -0.31484183492, -0.6]",
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
                " -0.929235, -0.92]",
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
                "[-1, -3, -0.1, 0, -3, 0, -3, -2, -3, -3, -3, -3, -3, -1, -2.7, -0.9, -3, -3, -3, 0]",
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
                " -2.7, -3, -2.9, -2, -2.785, -3]",
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
                " -0.16, -0.789922681093399615168295081768]",
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
                " -1000000, -600000, -400000, -700000, -300000, -1000000]",
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
                " -1000000, -100000, -100000, -100000, -395443.7, -255000, -458143.3, -314841.83492, -600000]",
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
                " -785000, -700000, -400000, -221800, -664253.2792, -929235, -920000]",
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
                " -0.000001, -6E-7, -4E-7, -7E-7, -3E-7, -0.000001]",
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
                " -3.1484183492E-7, -6E-7]",
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
                " -6.642532792E-7, -9.29235E-7, -9.2E-7]",
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
                "[-1, -3, -3, -3, -2, -3, -3, -3, -3, -3, -1, -2.7, -3, -3, -3, -3, -3, -2.04, -3, -1.94]",
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
                " -2.785, -3, -1.425, -1.9888232, -3]",
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
                " -1.02366346, -3, -1]",
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
                " -600000, -400000, -700000, -300000, -1000000, -900000, -1000000, -1000000, -1000000, -1000000]",
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
                " -877000]",
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
                " -785000, -700000, -400000, -221800, -664253.2792, -929235, -920000, -77040]",
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
                " 100, 100.4, 100.6, 100.3, 100.7, 100]",
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
                " 100.911719, 100, 100.9, 100.9, 100.9, 100.6045563, 100.745, 100.5418567, 100.68515816508, 100.4]",
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
                " 100.3357467208, 100.070765, 100.08]",
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
                " 2.7183, 3.1183, 3.0673, 2.7183, 3.1183, 2.9183, 2.7483, 2.9283]",
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
                " 2.7183, 3.0183, 2.9293, 2.7883, 2.7183, 2.7793, 2.7483, 2.7183, 2.7283, 2.8183]",
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
                " 2.8002, 2.8723, 2.8839238087623776, 3.0023]",
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
                " -3.1416, -3.1416, -3.1416, 0.8584, 0.3484, -3.1416, 0.8584, -1.1416, -2.8416]",
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
                " -3.1416, -1.5653895589, 1.36043, -1.9416, 1.1654, 2.0844, -0.3162, -3.1416, -0.1416, -1.0316]",
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
                " -0.8416, -2.3226, -1.6016, -1.485361912376224, 2.25160786]",
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
                "[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]",
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
                "[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]",
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
                "[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]",
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
                "[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]",
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
                "[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]",
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
                "[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]",
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
                "[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]",
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
                "[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]",
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
                "[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]",
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
                "[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]",
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
                "[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]",
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
                "[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]",
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
                "[1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]",
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
                "[1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]",
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
                "[1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]",
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

    @Test
    public void testEquals() {
        List<RandomProvider> xs = Arrays.asList(
                RandomProvider.example(),
                Q.withScale(3).withSecondaryScale(0),
                R.withScale(0).withSecondaryScale(10)
        );
        List<RandomProvider> ys = Arrays.asList(
                RandomProvider.example(),
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

    private static @NotNull List<Integer> readIntegerListWithNulls(@NotNull String s) {
        return Readers.readListWithNulls(Readers::readInteger).apply(s).get();
    }
}
// @formatter:on
