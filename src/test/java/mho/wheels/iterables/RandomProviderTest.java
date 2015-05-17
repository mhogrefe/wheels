package mho.wheels.iterables;

import mho.wheels.misc.Readers;
import mho.wheels.random.IsaacPRNG;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
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
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

// @formatter:off
public strictfp class RandomProviderTest {
    private static final RandomProvider P = RandomProvider.example();
    private static final RandomProvider Q = new RandomProvider(toList(replicate(IsaacPRNG.SIZE, 0)));
    private static final RandomProvider R = new RandomProvider(toList(IterableUtils.range(1, IsaacPRNG.SIZE)));
    private static final int DEFAULT_SAMPLE_SIZE = 1000000;
    private static final int DEFAULT_TOP_COUNT = 10;
    private static final int TINY_LIMIT = 20;

    @Before
    public void initialize() {
        P.reset();
        Q.reset();
        R.reset();
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
                new RandomProvider(toList(IterableUtils.rangeBy(-1, -1, -256))),
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
        RandomProvider original = P.deepCopy();
        P.nextInt();
        assertNotEquals(P, original);
        P.reset();
        assertEquals(P, original);
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
        aeqit(TINY_LIMIT, P.uniformSample(readIntegerListWithNulls(xs)), output);
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
    }

    private static void range_byte_byte_helper(int a, int b, @NotNull String output) {
        aeqit(TINY_LIMIT, P.range((byte) a, (byte) b), output);
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
    }

    private static void range_short_short_helper(int a, int b, @NotNull String output) {
        aeqit(TINY_LIMIT, P.range((short) a, (short) b), output);
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
    }

    private static void range_int_int_helper(int a, int b, @NotNull String output) {
        aeqit(TINY_LIMIT, P.range(a, b), output);
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
    }

    private static void range_long_long_helper(long a, long b, @NotNull String output) {
        P.reset();
        aeqit(TINY_LIMIT, P.range(a, b), output);
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
    }

    private static void range_BigInteger_BigInteger_helper(int a, int b, @NotNull String output) {
        aeqit(TINY_LIMIT, P.range(BigInteger.valueOf(a), BigInteger.valueOf(b)), output);
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
    }

    @Test
    public void testNegativeIntegersGeometric() {
        negativeIntegersGeometric_helper(
                2,
                "[-4, -2, -9, -2, -2, -2, -3, -1, -2, -2, -2, -3, -1, -1, -1, -2, -2, -2, -1, -1]",
                "{-1=500069, -2=250062, -3=124820, -4=62751, -5=31115, -6=15623, -7=7769, -8=3913, -9=1972, -10=942}",
                -1.9994539999798795
        );
        negativeIntegersGeometric_helper(
                3,
                "[-5, -5, -5, -4, -2, -5, -2, -2, -1, -3, -6, -1, -2, -2, -2, -4, -2, -7, -7, -1]",
                "{-1=332909, -2=221711, -3=148628, -4=98603, -5=66130, -6=43947, -7=29393, -8=19392, -9=12986," +
                " -10=8654}",
                -3.0040559999897853
        );
        negativeIntegersGeometric_helper(
                4,
                "[-7, -9, -6, -6, -3, -9, -3, -2, -1, -6, -7, -1, -2, -2, -2, -8, -4, -8, -14, -5]",
                "{-1=249778, -2=187093, -3=140627, -4=105664, -5=78463, -6=59650, -7=44726, -8=33553, -9=25145," +
                " -10=18873}",
                -4.00555699999019
        );
        negativeIntegersGeometric_helper(
                5,
                "[-5, -3, -17, -7, -2, -2, -2, -5, -6, -17, -5, -2, -7, -14, -9, -1, -4, -7, -14, -7]",
                "{-1=200326, -2=159467, -3=127321, -4=102809, -5=82261, -6=65599, -7=52283, -8=42004, -9=33342," +
                " -10=26961}",
                -5.005213000008396
        );
        negativeIntegersGeometric_helper(
                10,
                "[-36, -9, -9, -7, -31, -20, -1, -5, -18, -18, -11, -8, -3, -3, -2, -3, -5, -11, -8, -4]",
                "{-1=100339, -2=90055, -3=81066, -4=72764, -5=65530, -6=59229, -7=52655, -8=47976, -9=42544," +
                " -10=38858}",
                -9.995441000005417
        );
        negativeIntegersGeometric_helper(
                100,
                "[-147, -65, -33, -31, -143, -34, -116, -26, -84, -8, -62, -10, -15, -136, -34, -289, -125, -64," +
                " -89, -24]",
                "{-2=9935, -1=9911, -4=9758, -3=9734, -5=9572, -6=9536, -7=9468, -9=9249, -10=9200, -8=9118}",
                -99.96604400000099
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
    }

    @Test
    public void testNextNonzeroIntGeometric() {
        nextNonzeroIntGeometric_helper(2, 4);
        nextNonzeroIntGeometric_helper(3, 5);
        nextNonzeroIntGeometric_helper(4, 7);
        nextNonzeroIntGeometric_helper(5, 5);
        nextNonzeroIntGeometric_helper(10, 36);
        nextNonzeroIntGeometric_helper(100, 147);
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
    }

    @Test
    public void testNonzeroIntegersGeometric() {
        nonzeroIntegersGeometric_helper(
                2,
                "[4, 1, 8, 1, 1, 1, -2, 3, 1, 1, -2, -2, -1, 3, -1, 2, -1, -2, -1, -1]",
                "{1=250554, -1=249443, -2=125173, 2=124586, -3=63103, 3=62228, -4=31398, 4=31095, -5=15652, 5=15618}",
                -0.00324499999999998,
                1.9999089999798014
        );
        nonzeroIntegersGeometric_helper(
                3,
                "[5, 4, 4, 3, 2, -5, -1, -1, 4, -6, -3, -1, -1, 3, -2, -6, -6, -8, -2, -1]",
                "{1=166663, -1=166530, 2=111050, -2=110588, -3=74365, 3=74298, -4=49431, 4=49320, 5=33035, -5=32667}",
                -7.109999999999536E-4,
                3.002494999989948
        );
        nonzeroIntegersGeometric_helper(
                4,
                "[7, 8, -5, 5, 2, -8, -2, -1, 7, -6, -3, -1, -1, 7, -3, -7, 13, 4, -10, -4]",
                "{1=124981, -1=124610, 2=93584, -2=93352, 3=70403, -3=70322, -4=52499, 4=52349, -5=40059, 5=39837}",
                -0.005358000000000017,
                4.007443999990141
        );
        nonzeroIntegersGeometric_helper(
                5,
                "[5, -3, 17, -7, -1, -1, -2, 4, 6, -17, -4, 1, -7, -13, -8, -5, -6, -13, 6, -4]",
                "{-1=100151, 1=99484, -2=79830, 2=79510, 3=64369, -3=63659, -4=51353, 4=51159, -5=41216, 5=40646}",
                -0.006956000000000186,
                5.009958000008598
        );
        nonzeroIntegersGeometric_helper(
                10,
                "[36, -8, 9, -7, -30, -20, 6, -18, 18, 10, 7, 2, -2, -2, -2, -4, 11, 7, 3, -7]",
                "{-1=50348, 1=49807, -2=45114, 2=44941, -3=40613, 3=40283, -4=36388, 4=36352, 5=33144, -5=32784}",
                0.006026000000000106,
                9.997882000005676
        );
        nonzeroIntegersGeometric_helper(
                100,
                "[147, 64, 33, 30, 142, -33, 115, 25, 83, 7, -61, 9, -14, -136, 33, -288, 124, -64, 88, 24]",
                "{-1=5099, 3=5046, 1=5031, -4=4916, -2=4912, 2=4874, 4=4860, -5=4822, -3=4798, 6=4752}",
                -0.09851599999999745,
                99.95862999999811
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
    }

    @Test
    public void testNextIntGeometric() {
        nextIntGeometric_helper(1, 3);
        nextIntGeometric_helper(2, 4);
        nextIntGeometric_helper(3, 6);
        nextIntGeometric_helper(4, 4);
        nextIntGeometric_helper(5, 5);
        nextIntGeometric_helper(10, 36);
        nextIntGeometric_helper(100, 149);
        nextIntGeometric_fail_helper(0);
        nextIntGeometric_fail_helper(-1);
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
    }

    @Test
    public void testIntegersGeometric() {
        integersGeometric_helper(
                1,
                "[3, 0, 7, 0, 0, 0, -1, 2, 0, 0, -1, -1, 0, 2, 0, 1, 0, -1, 0, 0]",
                "{0=499997, -1=125173, 1=124586, -2=63103, 2=62228, -3=31398, 3=31095, -4=15652, 4=15618, 5=7797}",
                -0.0026230000000000515,
                1.0003059999977268
        );
        integersGeometric_helper(
                2,
                "[4, 3, 3, 2, 1, -4, 0, 0, 3, -5, -2, 0, 0, 2, -1, -5, -5, -7, -1, 0]",
                "{0=333193, 1=111050, -1=110588, -2=74365, 2=74298, -3=49431, 3=49320, 4=33035, -4=32667, 5=22100}",
                -0.0012509999999999928,
                2.0016309999890516
        );
        integersGeometric_helper(
                3,
                "[6, 7, -4, 4, 1, -7, -1, 0, 6, -5, -2, 0, 0, 6, -2, -6, 12, 3, -9, -3]",
                "{0=249591, 1=93584, -1=93352, 2=70403, -2=70322, -3=52499, 3=52349, -4=40059, 4=39837, -5=29751}",
                -0.005255999999999971,
                3.002566999991555
        );
        integersGeometric_helper(
                4,
                "[4, -2, 16, -6, 0, 0, -1, 3, 5, -16, -3, 0, -6, -12, -7, -4, -5, -12, 5, -3]",
                "{0=199635, -1=79830, 1=79510, 2=64369, -2=63659, -3=51353, 3=51159, -4=41216, 4=40646, 5=32985}",
                -0.006043999999999987,
                4.001105999991566
        );
        integersGeometric_helper(
                5,
                "[5, 5, 21, -8, -1, 0, -1, 3, -7, -21, -3, 0, -6, -16, -9, -5, -6, -14, 6, -4]",
                "{0=166265, -1=69464, 1=69145, -2=58027, 2=57911, -3=48038, 3=47784, -4=40407, 4=40086, 5=33867}",
                -0.004018000000000179,
                5.003640000004923
        );
        integersGeometric_helper(
                10,
                "[36, -10, 8, -7, -30, -23, 7, -17, 19, 9, 6, 1, -2, -1, -1, -4, 11, 7, 2, -8]",
                "{0=90994, -1=41558, 1=41352, -2=37594, 2=37352, -3=34219, 3=34039, -4=31245, 4=31235, 5=28202}",
                0.0011880000000002821,
                9.985122000004568
        );
        integersGeometric_helper(
                100,
                "[149, 64, 32, 29, 142, -32, 114, 24, 82, 6, -61, 8, -13, -137, 34, -287, 124, -64, 88, 23]",
                "{0=10017, 2=5010, -3=4863, -1=4855, 3=4819, 1=4807, -2=4776, -4=4749, -5=4710, 5=4689}",
                -0.09234699999999806,
                99.86097099999841
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
    }

    @Test
    public void testPositiveBigIntegers() {
        positiveBigIntegers_helper(
                2,
                "[15, 1, 186, 1, 1, 1, 2, 7, 1, 1, 2, 2, 1, 7, 1, 3, 1, 2, 1, 1]",
                "{1=499997, 2=125173, 3=124586, 6=31754, 4=31349, 5=31260, 7=30968, 13=7901, 10=7879, 12=7853}",
                69.68070899963234,
                1.9999089999798014
        );
        positiveBigIntegers_helper(
                3,
                "[29, 9, 13, 7, 3, 22, 1, 1, 11, 44, 6, 1, 1, 7, 2, 38, 60, 221, 2, 1]",
                "{1=333192, 3=111051, 2=110589, 5=37242, 4=37214, 6=37151, 7=37054, 14=12429, 10=12412, 13=12392}",
                48777.09156231954,
                3.0024959999899483
        );
        positiveBigIntegers_helper(
                4,
                "[125, 186, 30, 31, 3, 244, 2, 1, 83, 44, 6, 1, 1, 127, 6, 102, 6998, 9, 733, 14]",
                "{1=249582, 3=93568, 2=93356, 5=35456, 6=35308, 4=35019, 7=34942, 14=13254, 13=13233, 9=13174}",
                2.584100755623998E8,
                4.007541999990144
        );
        positiveBigIntegers_helper(
                5,
                "[31, 6, 128754, 74, 1, 1, 2, 15, 47, 122278, 12, 1, 90, 6750, 211, 18, 58, 5325, 49, 10]",
                "{1=199640, 2=79850, 3=79521, 7=32202, 5=32167, 4=32056, 6=31585, 8=12906, 13=12900, 14=12847}",
                6.642571312817901E12,
                5.0099500000086135
        );
        positiveBigIntegers_helper(
                10,
                "[68567587586, 90, 429, 102, 777331279, 578066, 59, 149564, 211254, 797, 87, 3, 2, 2, 2, 14, 1805," +
                " 89, 7, 76]",
                "{1=100061, 2=45133, 3=45017, 5=20400, 4=20372, 6=20233, 7=19883, 8=9210, 15=9140, 14=9137}",
                3.544461590419017E35,
                9.998229000005633
        );
        positiveBigIntegers_helper(
                100,
                "[171491947975428291073106393958556356696218384, 666733922234672488, 2800561118, 426754826," +
                " 4973382539154393382023039181388821734010791, 486317307, 31113674257148638473061092431931202," +
                " 2603939, 7221829089434139486576085, 29, 1776109995947233569, 213, 12837," +
                " 50470376168837087647456296575128778493542, 929861590," +
                " 483950622288622499876991094655284908519805893350573564439890270180685476039232812074527," +
                " 602275634430017182769551262807228622, 1177577916662216386, 134540776316088470488675072, 2467237]",
                "{1=10072, 2=4956, 3=4911, 7=2555, 5=2447, 4=2443, 6=2409, 12=1213, 13=1210, 15=1205}",
                Double.POSITIVE_INFINITY,
                99.96323900000282
        );
        positiveBigIntegers_fail_helper(1);
        positiveBigIntegers_fail_helper(0);
        positiveBigIntegers_fail_helper(-1);
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
    }

    @Test
    public void testNegativeBigIntegers() {
        negativeBigIntegers_helper(
		        2,
		        "[-15, -1, -186, -1, -1, -1, -2, -7, -1, -1, -2, -2, -1, -7, -1, -3, -1, -2, -1, -1]",
		        "{-1=499997, -2=125173, -3=124586, -6=31754, -4=31349, -5=31260, -7=30968, -13=7901, -10=7879," +
                " -12=7853}",
		        -69.68070899963234,
		        1.9999089999798014
        );
        negativeBigIntegers_helper(
        		3,
        		"[-29, -9, -13, -7, -3, -22, -1, -1, -11, -44, -6, -1, -1, -7, -2, -38, -60, -221, -2, -1]",
        		"{-1=333192, -3=111051, -2=110589, -5=37242, -4=37214, -6=37151, -7=37054, -14=12429, -10=12412," +
                " -13=12392}",
        		-48777.09156231954,
        		3.0024959999899483
        );
        negativeBigIntegers_helper(
        		4,
        		"[-125, -186, -30, -31, -3, -244, -2, -1, -83, -44, -6, -1, -1, -127, -6, -102, -6998, -9, -733, -14]",
        		"{-1=249582, -3=93568, -2=93356, -5=35456, -6=35308, -4=35019, -7=34942, -14=13254, -13=13233," +
                " -9=13174}",
        		-2.584100755623998E8,
        		4.007541999990144
        );
        negativeBigIntegers_helper(
        		5,
        		"[-31, -6, -128754, -74, -1, -1, -2, -15, -47, -122278, -12, -1, -90, -6750, -211, -18, -58, -5325," +
                " -49, -10]",
        		"{-1=199640, -2=79850, -3=79521, -7=32202, -5=32167, -4=32056, -6=31585, -8=12906, -13=12900," +
                " -14=12847}",
        		-6.642571312817901E12,
        		5.0099500000086135
        );
        negativeBigIntegers_helper(
        		10,
        		"[-68567587586, -90, -429, -102, -777331279, -578066, -59, -149564, -211254, -797, -87, -3, -2, -2," +
                " -2, -14, -1805, -89, -7, -76]",
        		"{-1=100061, -2=45133, -3=45017, -5=20400, -4=20372, -6=20233, -7=19883, -8=9210, -15=9140, -14=9137}",
        		-3.544461590419017E35,
        		9.998229000005633
        );
        negativeBigIntegers_helper(
        		100,
        		"[-171491947975428291073106393958556356696218384, -666733922234672488, -2800561118, -426754826," +
                " -4973382539154393382023039181388821734010791, -486317307, -31113674257148638473061092431931202," +
                " -2603939, -7221829089434139486576085, -29, -1776109995947233569, -213, -12837," +
                " -50470376168837087647456296575128778493542, -929861590," +
                " -483950622288622499876991094655284908519805893350573564439890270180685476039232812074527," +
                " -602275634430017182769551262807228622, -1177577916662216386, -134540776316088470488675072," +
                " -2467237]",
        		"{-1=10072, -2=4956, -3=4911, -7=2555, -5=2447, -4=2443, -6=2409, -12=1213, -13=1210, -15=1205}",
        		Double.NEGATIVE_INFINITY,
        		99.96323900000282
        );
        negativeBigIntegers_fail_helper(1);
        negativeBigIntegers_fail_helper(0);
        negativeBigIntegers_fail_helper(-1);
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
    }

    @Test
    public void testNaturalBigIntegers() {
		naturalBigIntegers_helper(
				1,
				"[7, 0, 186, 0, 1, 0, 2, 3, 0, 1, 1, 1, 0, 0, 1, 0, 0, 0, 1, 1]",
				"{0=499795, 1=249695, 2=63164, 3=62454, 5=15802, 4=15675, 6=15645, 7=15501, 10=3966, 15=3931}",
				34.20643800003216,
				1.0003699999976585
		);
		naturalBigIntegers_helper(
				2,
				"[13, 5, 5, 3, 1, 14, 0, 1, 7, 28, 2, 0, 1, 3, 1, 22, 28, 66, 1, 0]",
				"{0=333291, 1=221695, 2=74348, 3=74172, 5=24863, 6=24759, 4=24546, 7=24312, 14=8375, 9=8338}",
				23450.71832509025,
				2.0029729999891197
		);
		naturalBigIntegers_helper(
				3,
				"[61, 113, 14, 15, 1, 86, 1, 0, 0, 19, 28, 2, 0, 1, 63, 2, 38, 2902, 5, 477]",
				"{0=249213, 1=187438, 3=70231, 2=70108, 6=26549, 5=26525, 4=26321, 7=26102, 9=10081, 12=9998}",
				1.202255417129307E8,
				3.008166999991507
		);
		naturalBigIntegers_helper(
				4,
				"[15, 2, 63218, 42, 0, 1, 1, 7, 31, 56742, 4, 0, 58, 2654, 64, 10, 26, 3277, 17, 6]",
				"{0=199865, 1=159280, 3=64105, 2=63742, 5=25816, 4=25771, 6=25615, 7=25403, 11=10352, 12=10309}",
				2.9136340323703857E12,
				4.00929699999154
		);
		naturalBigIntegers_helper(
				5,
				"[29, 17, 1570546, 170, 1, 0, 1, 7, 102, 1236390, 4, 0, 58, 56834, 339, 18, 58, 13517, 49, 10]",
				"{0=166431, 1=138432, 2=57969, 3=57805, 4=24088, 6=24076, 5=24001, 7=23861, 12=10156, 14=10150}",
				4.7507769343063366E17,
				5.010013000004874
		);
		naturalBigIntegers_helper(
				10,
				"[68567587586, 393, 173, 102, 777331279, 5296658, 91, 70156, 342326, 285, 55, 1, 2, 1, 1, 14, 1805," +
                " 89, 3, 228]",
				"{0=91092, 1=82861, 2=37571, 3=37186, 6=17194, 7=17090, 5=17074, 4=17031, 13=7965, 12=7904}",
				2.6618978515967548E39,
				9.99564600000421
		);
		naturalBigIntegers_helper(
				100,
				"[617506851946040753903820759411523586815826704, 666733922234672488, 1395060011, 426754826," +
                " 4973382539154393382023039181388821734010791, 217881851, 20729080540078983216000099773491010," +
                " 1555363, 4803977450204881137163733, 13, 1776109995947233569, 125, 4645," +
                " 94026519134717210970768246326395109559910, 2003603414," +
                " 130106579005213050320828982313849454512304343148341886388712940410660985464322014051782," +
                " 1321342898819073405067311346410901103, 1177577916662216386, 134540776316088470488675072, 1418661]",
				"{0=9958, 1=9772, 3=4976, 2=4815, 4=2394, 6=2380, 7=2366, 5=2338, 10=1251, 9=1203}",
				Double.POSITIVE_INFINITY,
				99.95993899999823
		);
        naturalBigIntegers_fail_helper(0);
        naturalBigIntegers_fail_helper(-1);
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
    }

    @Test
    public void testNonzeroBigIntegers() {
		nonzeroBigIntegers_helper(
				2,
				"[-15, -954, -7, 10, -3, 7, 1, -1, -3, 6, -1, -1, -1, -1, 1, 1, 3, 2, 1, -1]",
				"{1=249983, -1=249126, 2=62883, -2=62756, 3=62544, -3=62405, -5=15943, 4=15696, -7=15688, 6=15659}",
				17.91493000000076,
				2.001177999979952
		);
		nonzeroBigIntegers_helper(
				3,
				"[29, -9, 5, 7, 1, 14, -1, 3, -3, -28, -2, -1, 15, 1, 22, 60, 221, -2, -1, 93]",
				"{1=166607, -1=166197, 3=55804, -3=55689, 2=55687, -2=55520, 6=18667, 5=18558, -5=18521, -6=18516}",
				-10855.3935519995,
				3.002819999989811
		);
		nonzeroBigIntegers_helper(
				4,
				"[125, -113, 14, 15, -1, 86, -1, -3, -11, -28, -2, -1, 173, 2, 38, 2902, 5, 477, -6, -1]",
				"{1=125257, -1=124807, 3=46905, -2=46857, -3=46762, 2=46499, 4=17732, 5=17670, 6=17657, -6=17547}",
				-4.1311559747530356E7,
				4.003355999990116
		);
		nonzeroBigIntegers_helper(
				5,
				"[31, 2, -128754, -42, -1, -1, 63, 47, 56742, -4, 225, 6750, 64, 10, -58, -5325, -49, -6, 5, -4]",
				"{1=99665, -1=99156, -3=40066, -2=39942, 3=39938, 2=39897, 4=16258, -7=16151, 7=16091, -5=16063}",
				4.772512823858073E12,
				5.011650000008701
		);
		nonzeroBigIntegers_helper(
				10,
				"[68567587586, -58, 173, 38, 508895823, -315922, -59, -84028, -211254, 285, 55, -3, -1, 2, -2, -14," +
                " 781, 89, 3, 44]",
				"{1=50418, -1=49875, 2=22690, -2=22574, -3=22381, 3=22313, -4=10176, -5=10156, 7=10152, 4=10125}",
				-1.5676202667125113E35,
				9.99773500000579
		);
		nonzeroBigIntegers_helper(
				100,
				"[171491947975428291073106393958556356696218384, -378503546082960744, -1395060011, 426754826," +
                " -2185789389338065489331074397307776545763239, 486317307, 20729080540078983216000099773491010," +
                " -2603939, -4803977450204881137163733, -13, 623188491340386593, 125, 4645," +
                " -34735810149384773915155398812821070253774, -392990678," +
                " 130106579005213050320828982313849454512304343148341886388712940410660985464322014051782," +
                " -602275634430017182769551262807228622, -1177577916662216386, 57169523860752203307479808, -1418661]",
				"{1=4979, -1=4899, 2=2554, -3=2542, 3=2523, -2=2439, 6=1256, 5=1231, -4=1230, -7=1217}",
				Double.NaN,
				99.97301499999901
		);
        nonzeroBigIntegers_fail_helper(1);
        nonzeroBigIntegers_fail_helper(0);
        nonzeroBigIntegers_fail_helper(-1);
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
    }

    @Test
    public void testBigIntegers() {
		bigIntegers_helper(
				1,
				"[-7, -442, -3, 6, -1, 3, 0, -1, -1, 2, 0, 0, -1, 0, 0, 0, 0, 3, 1, 0]",
				"{0=499517, -1=125039, 1=125034, -2=31642, 3=31356, -3=31242, 2=31149, 6=7940, 5=7850, 4=7816}",
				11.312045000000163,
				1.0011659999976752
		);
		bigIntegers_helper(
				2,
				"[13, -5, 3, 3, 0, 14, 0, 0, -7, -12, -1, 0, 0, 3, 0, 22, 28, 66, -1, 0]",
				"{0=333329, 1=111369, -1=110761, 2=37216, -2=36972, -3=36949, 3=36775, -5=12628, 5=12461, 6=12396}",
				-7186.845220999995,
				2.0017199999891138
		);
		bigIntegers_helper(
				3,
				"[61, -49, 6, 7, 0, 86, 0, 0, -51, -12, -1, 0, 0, 31, 1, 22, 1878, 3, 221, -2]",
				"{0=249591, 1=94010, -1=93698, -3=35100, 2=35041, -2=35023, 3=34983, 6=13359, -5=13276, -6=13247}",
				-1.8190746041600898E7,
				3.004465999991475
		);
		bigIntegers_helper(
				4,
				"[15, 1, -63218, -26, 0, 0, 1, 3, 31, 17117, -2, 90, 2654, 32, 6, -26, -3277, -17, -2, 3]",
				"{0=199043, -1=79797, 1=79710, -3=32232, 3=32110, 2=32050, -2=32024, 4=12950, -5=12920, 6=12815}",
				1.920666346451926E12,
				4.011480999991471
		);
		bigIntegers_helper(
				5,
				"[29, -9, -1570546, -108, 0, 0, 1, 3, 38, 712102, -2, 90, 31326, 211, 10, -58, -13517, -49, -6, 5]",
				"{0=165963, 1=69541, -1=69367, -2=28988, 2=28963, -3=28944, 3=28884, 4=12176, -5=12024, 6=12010}",
				-3.1566211227467066E17,
				5.010994000004885
		);
		bigIntegers_helper(
				10,
				"[68567587586, -137, 127, 38, 508895823, -3199506, -59, -37388, -211254, 157, 23, 1, -1, 1, -1, -6," +
                " 781, 89, 1, 76]",
				"{0=91283, -1=41346, 1=41138, 3=18747, -2=18736, -3=18674, 2=18630, 6=8631, 5=8621, -6=8597}",
				2.6150409359336613E38,
				9.997241000004482
		);
		bigIntegers_helper(
				100,
				"[617506851946040753903820759411523586815826704, -378503546082960744, -858189099, 158319370," +
                " -2185789389338065489331074397307776545763239, 217881851, 10344486823009327958939107115050818," +
                " -1555363, -2386125810975622787751381, -5, 623188491340386593, 61, 2597," +
                " -50470376168837087647456296575128778493542, -929861590," +
                " 67941174453989720051406201295496849499747324298673421708654943299016048337755342110150," +
                " -602275634430017182769551262807228622, -1177577916662216386, 57169523860752203307479808, -894373]",
				"{0=9785, 1=4994, -1=4978, 3=2450, 2=2415, -2=2397, -3=2386, -6=1233, -5=1225, -4=1215}",
				Double.NaN,
				99.96810800000098
		);
        bigIntegers_fail_helper(0);
        bigIntegers_fail_helper(-1);
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
    }

    @Test
    public void testRangeUp_BigInteger() {
		rangeUp_BigInteger_helper(
				5,
				8,
				"[127, 13, 1466, 9, 15, 15, 26, 63, 13, 15, 22, 18, 8, 47, 10, 29, 8, 30, 10, 14]",
				"{13=62876, 11=62785, 15=62616, 14=62585, 8=62454, 9=62277, 12=62240, 10=62164, 30=15752, 16=15749}",
				661.8043779958618,
				4.999908999951449
		);
		rangeUp_BigInteger_helper(
				10,
				8,
				"[410, 442, 2012672603, 27690, 58, 14, 19, 173, 9934, 182298122, 124, 30, 2657, 13767410, 8275," +
                " 21012, 1110, 3460362, 4167, 488]",
				"{13=18113, 12=17987, 9=17965, 10=17921, 8=17801, 14=17690, 11=17579, 15=17558, 20=7797, 23=7717}",
				6.179888359268556E22,
				10.009691999993237
		);
		rangeUp_BigInteger_helper(
				5,
				10,
				"[127, 15, 1466, 11, 15, 42, 63, 15, 11, 13, 12, 10, 47, 12, 29, 10, 30, 12, 14, 13]",
				"{15=83545, 14=83519, 10=83515, 13=83367, 11=83272, 12=83064, 16=15834, 23=15783, 22=15713, 26=15689}",
				659.7849479950901,
				4.999800999951615
		);
		rangeUp_BigInteger_helper(
				10,
				10,
				"[410, 442, 2012672603, 27690, 58, 14, 13, 173, 9934, 182298122, 124, 30, 2657, 13767410, 8275," +
                " 21012, 1110, 3460362, 4167, 488]",
				"{14=23903, 15=23884, 10=23773, 11=23765, 12=23720, 13=23536, 20=7801, 29=7733, 21=7716, 23=7709}",
				6.180120150120354E22,
				10.010728999993448
		);
		rangeUp_BigInteger_helper(
				1,
				0,
				"[7, 0, 186, 0, 1, 0, 2, 3, 0, 1, 1, 1, 0, 0, 1, 0, 0, 0, 1, 1]",
				"{0=499795, 1=249695, 2=63164, 3=62454, 5=15802, 4=15675, 6=15645, 7=15501, 10=3966, 15=3931}",
				34.20643800003216,
				1.0003699999976585
		);
		rangeUp_BigInteger_helper(
				10,
				0,
				"[68567587586, 393, 173, 102, 777331279, 5296658, 91, 70156, 342326, 285, 55, 1, 2, 1, 1, 14, 1805," +
                " 89, 3, 228]",
				"{0=91092, 1=82861, 2=37571, 3=37186, 6=17194, 7=17090, 5=17074, 4=17031, 13=7965, 12=7904}",
				2.6618978515967548E39,
				9.99564600000421
		);
		rangeUp_BigInteger_helper(
				5,
				-8,
				"[29, 17, 1570546, 170, -1, 0, -1, 7, 102, 1236390, 4, 0, 58, 56834, 339, 18, 58, 13517, 49, 10]",
				"{0=166506, -1=69283, 1=69092, 3=29084, -2=28987, 2=28959, -3=28731, -4=12170, 6=12101, 5=12057}",
				4.6583361387316E17,
				5.010092000004885
		);
		rangeUp_BigInteger_helper(
				10,
				-8,
				"[68567587586, 393, 173, 102, 777331279, 5296658, 91, 70156, 342326, 285, 55, 1, 2, -1, -1, -8, 781," +
                " 89, 3, 228]",
				"{0=91159, -1=41536, 1=41254, -3=18857, -2=18837, 2=18688, 3=18370, 6=8633, -7=8595, -4=8586}",
				2.6618978515967285E39,
				9.995757000004136
		);
		rangeUp_BigInteger_helper(
				5,
				-10,
				"[29, 17, 1570546, 170, -1, 0, -1, 7, 102, 1236390, 4, 0, 58, 56834, 339, 18, 58, 13517, 49, 10]",
				"{0=166474, -1=69242, 1=69072, 3=29107, -2=29007, 2=28964, -3=28739, -4=12173, 6=12080, 5=12073}",
				4.6587110102444096E17,
				5.010204000004878
		);
		rangeUp_BigInteger_helper(
				10,
				-10,
				"[68567587586, 393, 173, 102, 777331279, 5296658, 91, 70156, 342326, 285, 55, 1, 2, -1, -1, -8, 781," +
                " 89, 3, 228]",
				"{0=91124, -1=41567, 1=41267, -2=18862, -3=18833, 2=18681, 3=18400, 6=8666, -6=8581, -4=8574}",
				2.661897851596729E39,
				9.995318000004161
		);
        rangeUp_BigInteger_fail_helper(4, 10);
        rangeUp_BigInteger_fail_helper(3, 10);
        rangeUp_BigInteger_fail_helper(Integer.MAX_VALUE, -10);
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
    }

    @Test
    public void testRangeDown_BigInteger() {
		rangeDown_BigInteger_helper(
				5,
				8,
				"[-29, -17, -1570546, -170, 1, 0, 1, -7, -102, -1236390, -4, 0, -58, -56834, -339, -18, -58, -13517," +
                " -49, -10]",
				"{0=166506, 1=69283, -1=69092, -3=29084, 2=28987, -2=28959, 3=28731, 4=12170, -6=12101, -5=12057}",
				-4.6583361387316E17,
				5.010092000004885
		);
		rangeDown_BigInteger_helper(
				10,
				8,
				"[-68567587586, -393, -173, -102, -777331279, -5296658, -91, -70156, -342326, -285, -55, -1, -2, 1," +
                " 1, 8, -781, -89, -3, -228]",
				"{0=91159, 1=41536, -1=41254, 3=18857, 2=18837, -2=18688, -3=18370, -6=8633, 7=8595, 4=8586}",
				-2.6618978515967285E39,
				9.995757000004136
		);
		rangeDown_BigInteger_helper(
				5,
				10,
				"[-29, -17, -1570546, -170, 1, 0, 1, -7, -102, -1236390, -4, 0, -58, -56834, -339, -18, -58, -13517," +
                " -49, -10]",
				"{0=166474, 1=69242, -1=69072, -3=29107, 2=29007, -2=28964, 3=28739, 4=12173, -6=12080, -5=12073}",
				-4.6587110102444096E17,
				5.010204000004878
		);
		rangeDown_BigInteger_helper(
				10,
				10,
				"[-68567587586, -393, -173, -102, -777331279, -5296658, -91, -70156, -342326, -285, -55, -1, -2, 1," +
                " 1, 8, -781, -89, -3, -228]",
				"{0=91124, 1=41567, -1=41267, 2=18862, 3=18833, -2=18681, -3=18400, -6=8666, 6=8581, 4=8574}",
				-2.661897851596729E39,
				9.995318000004161
		);
		rangeDown_BigInteger_helper(
				1,
				0,
				"[-7, 0, -186, 0, -1, 0, -2, -3, 0, -1, -1, -1, 0, 0, -1, 0, 0, 0, -1, -1]",
				"{0=499795, -1=249695, -2=63164, -3=62454, -5=15802, -4=15675, -6=15645, -7=15501, -10=3966," +
                " -15=3931}",
				-34.20643800003216,
				1.0003699999976585
		);
		rangeDown_BigInteger_helper(
				10,
				0,
				"[-68567587586, -393, -173, -102, -777331279, -5296658, -91, -70156, -342326, -285, -55, -1, -2, -1," +
                " -1, -14, -1805, -89, -3, -228]",
				"{0=91092, -1=82861, -2=37571, -3=37186, -6=17194, -7=17090, -5=17074, -4=17031, -13=7965, -12=7904}",
				-2.6618978515967548E39,
				9.99564600000421
		);
		rangeDown_BigInteger_helper(
				5,
				-8,
				"[-127, -13, -1466, -9, -15, -15, -26, -63, -13, -15, -22, -18, -8, -47, -10, -29, -8, -30, -10, -14]",
				"{-13=62876, -11=62785, -15=62616, -14=62585, -8=62454, -9=62277, -12=62240, -10=62164, -30=15752," +
                " -16=15749}",
				-661.8043779958618,
				4.999908999951449
		);
		rangeDown_BigInteger_helper(
				10,
				-8,
				"[-410, -442, -2012672603, -27690, -58, -14, -19, -173, -9934, -182298122, -124, -30, -2657," +
                " -13767410, -8275, -21012, -1110, -3460362, -4167, -488]",
				"{-13=18113, -12=17987, -9=17965, -10=17921, -8=17801, -14=17690, -11=17579, -15=17558, -20=7797," +
                " -23=7717}",
				-6.179888359268556E22,
				10.009691999993237
		);
		rangeDown_BigInteger_helper(
				5,
				-10,
				"[-127, -15, -1466, -11, -15, -42, -63, -15, -11, -13, -12, -10, -47, -12, -29, -10, -30, -12, -14," +
                " -13]",
				"{-15=83545, -14=83519, -10=83515, -13=83367, -11=83272, -12=83064, -16=15834, -23=15783, -22=15713," +
                " -26=15689}",
				-659.7849479950901,
				4.999800999951615
		);
		rangeDown_BigInteger_helper(
				10,
				-10,
				"[-410, -442, -2012672603, -27690, -58, -14, -13, -173, -9934, -182298122, -124, -30, -2657," +
                " -13767410, -8275, -21012, -1110, -3460362, -4167, -488]",
				"{-14=23903, -15=23884, -10=23773, -11=23765, -12=23720, -13=23536, -20=7801, -29=7733, -21=7716," +
                " -23=7709}",
				-6.180120150120354E22,
				10.010728999993448
		);
        rangeDown_BigInteger_fail_helper(4, -10);
        rangeDown_BigInteger_fail_helper(3, -10);
        rangeDown_BigInteger_fail_helper(Integer.MAX_VALUE, 10);
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

    private static @NotNull List<Integer> readIntegerList(@NotNull String s) {
        return Readers.readList(Readers::readInteger).apply(s).get();
    }

    private static @NotNull List<Integer> readIntegerListWithNulls(@NotNull String s) {
        return Readers.readListWithNulls(Readers::readInteger).apply(s).get();
    }
}
// @formatter:on
