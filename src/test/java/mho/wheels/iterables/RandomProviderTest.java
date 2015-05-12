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
        aeq(Q, "RandomProvider[@2476331594451070591, 32, 8]");
        aeq(R, "RandomProvider[@7136644727607569499, 32, 8]");
        aeq(
                new RandomProvider(toList(IterableUtils.rangeBy(-1, -1, -256))),
                "RandomProvider[@-8825631725423005287, 32, 8]"
        );
    }

    @Test
    public void testExample() {
        aeq(RandomProvider.example(), "RandomProvider[@2311470349995791220, 32, 8]");
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
        aeq(P.withScale(100), "RandomProvider[@2311470349995791220, 100, 8]");
        aeq(Q.withScale(3), "RandomProvider[@2476331594451070591, 3, 8]");
        aeq(R.withScale(0), "RandomProvider[@7136644727607569499, 0, 8]");
    }

    @Test
    public void testWithSecondaryScale() {
        aeq(P.withSecondaryScale(100), "RandomProvider[@2311470349995791220, 32, 100]");
        aeq(Q.withSecondaryScale(3), "RandomProvider[@2476331594451070591, 32, 3]");
        aeq(R.withSecondaryScale(0), "RandomProvider[@7136644727607569499, 32, 0]");
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
        aeq(P.getId(), 2311470349995791220L);
        P.nextInt();
        aeq(P.getId(), -3363466775474220013L);
        aeq(Q.getId(), 2476331594451070591L);
        aeq(R.getId(), 7136644727607569499L);
    }

    private static <T> void simpleProviderHelper(
            @NotNull Iterable<T> xs,
            @NotNull String output,
            @NotNull String sampleCountOutput
    ) {
        P.reset();
        aeqit(take(TINY_LIMIT, xs), output);
        P.reset();
        aeqit(sampleCount(DEFAULT_SAMPLE_SIZE, xs).entrySet(), sampleCountOutput);
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
        P.reset();
        aeq(Objects.toString(P.nextUniformSample(readIntegerListWithNulls(xs))), output);
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
        P.reset();
        aeqit(TINY_LIMIT, P.uniformSample(readIntegerListWithNulls(xs)), output);
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
        P.reset();
        aeq(P.nextUniformSample(s), output);
    }

    @Test
    public void testNextUniformSample_String() {
        nextUniformSample_String_helper("hello", 'e');
        P.reset();
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
        P.reset();
        aeqit(take(TINY_LIMIT, P.rangeUp(a)), output);
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
        P.reset();
        aeqit(take(TINY_LIMIT, P.rangeUp(a)), output);
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
        P.reset();
        aeq(P.nextFromRangeUp(a), output);
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
        P.reset();
        aeqit(take(TINY_LIMIT, P.rangeUp(a)), output);
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
        P.reset();
        aeq(P.nextFromRangeUp(a), output);
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
        P.reset();
        aeqit(take(TINY_LIMIT, P.rangeUp(a)), output);
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
        P.reset();
        aeq(P.nextFromRangeUp(a), output);
    }

    @Test
    public void testNextFromRangeUp_char() {
        nextFromRangeUp_char_helper('\0', '嘩');
        nextFromRangeUp_char_helper('a', '嚊');
        nextFromRangeUp_char_helper('Ш', '婑');
        nextFromRangeUp_char_helper('\uffff', '\uffff');
    }

    private static void rangeUp_char_helper(char a, @NotNull String output) {
        P.reset();
        aeqcs(P.rangeUp(a), output);
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
        P.reset();
        aeq(P.nextFromRangeDown((byte) a), output);
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
        P.reset();
        aeqit(take(TINY_LIMIT, P.rangeDown(a)), output);
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
        P.reset();
        aeq(P.nextFromRangeDown((short) a), output);
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
        P.reset();
        aeqit(take(TINY_LIMIT, P.rangeDown(a)), output);
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
        P.reset();
        aeq(P.nextFromRangeDown(a), output);
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
        P.reset();
        aeqit(take(TINY_LIMIT, P.rangeDown(a)), output);
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
        P.reset();
        aeq(P.nextFromRangeDown(a), output);
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
        P.reset();
        aeqit(take(TINY_LIMIT, P.rangeDown(a)), output);
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
        P.reset();
        aeq(P.nextFromRangeDown(a), output);
    }

    @Test
    public void testNextFromRangeDown_char() {
        nextFromRangeDown_char_helper('\0', '\0');
        nextFromRangeDown_char_helper('a', ')');
        nextFromRangeDown_char_helper('Ш', 'ǉ');
        nextFromRangeDown_char_helper('\uffff', '嘩');
    }

    private static void rangeDown_char_helper(char a, @NotNull String output) {
        P.reset();
        aeqcs(P.rangeDown(a), output);
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

    private static void range_byte_byte_helper(byte a, byte b, @NotNull String output) {
        P.reset();
        aeqit(TINY_LIMIT, P.range(a, b), output);
    }

    @Test
    public void testRange_byte_byte() {
        range_byte_byte_helper(
                (byte) 10,
                (byte) 20,
                "[19, 19, 13, 12, 15, 18, 15, 13, 11, 17, 15, 13, 15, 18, 15, 11, 16, 11, 16, 15, ...]"
        );
        range_byte_byte_helper(
                (byte) 10,
                (byte) 10,
                "[10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, ...]"
        );
        range_byte_byte_helper((byte) 10, (byte) 9, "[]");
        range_byte_byte_helper(
                (byte) -20,
                (byte) -10,
                "[-11, -11, -17, -18, -15, -12, -15, -17, -19, -13, -15, -17, -15, -12, -15, -19, -14, -19, -14," +
                " -15, ...]"
        );
        range_byte_byte_helper(
                (byte) -20,
                (byte) -20,
                "[-20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20," +
                " -20, ...]"
        );
        range_byte_byte_helper((byte) -20, (byte) -21, "[]");
        range_byte_byte_helper(
                (byte) 0,
                (byte) 0,
                "[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, ...]"
        );
        range_byte_byte_helper(
                (byte) 0,
                (byte) 10,
                "[9, 9, 3, 2, 5, 8, 5, 3, 1, 7, 5, 3, 5, 8, 5, 1, 6, 1, 6, 5, ...]"
        );
        range_byte_byte_helper(
                (byte) -5,
                (byte) 0,
                "[-4, -4, -2, -3, 0, -5, 0, 0, -2, -4, 0, -2, 0, -5, 0, -4, -4, -1, 0, 0, ...]"
        );
        range_byte_byte_helper(
                (byte) -5,
                (byte) 10,
                "[4, 4, -2, -3, 0, 10, 3, 0, 8, -2, 10, -4, 2, 0, -2, 0, 3, 0, -4, 1, ...]"
        );
        range_byte_byte_helper(
                (byte) -10,
                (byte) 5,
                "[-1, -1, -7, -8, -5, 5, -2, -5, 3, -7, 5, -9, -3, -5, -7, -5, -2, -5, -9, -4, ...]"
        );
        range_byte_byte_helper((byte) 5, (byte) -10, "[]");
    }

    private static void range_short_short_helper(short a, short b, @NotNull String output) {
        P.reset();
        aeqit(TINY_LIMIT, P.range(a, b), output);
    }

    @Test
    public void testRange_short_short() {
        range_short_short_helper(
                (short) 10,
                (short) 20,
                "[19, 19, 13, 12, 15, 18, 15, 13, 11, 17, 15, 13, 15, 18, 15, 11, 16, 11, 16, 15, ...]"
        );
        range_short_short_helper(
                (short) 10,
                (short) 10,
                "[10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, ...]"
        );
        range_short_short_helper((short) 10, (short) 9, "[]");
        range_short_short_helper(
                (short) -20,
                (short) -10,
                "[-11, -11, -17, -18, -15, -12, -15, -17, -19, -13, -15, -17, -15, -12, -15, -19, -14, -19, -14," +
                " -15, ...]"
        );
        range_short_short_helper(
                (short) -20,
                (short) -20,
                "[-20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20," +
                " -20, ...]"
        );
        range_short_short_helper((short) -20, (short) -21, "[]");
        range_short_short_helper(
                (short) 0,
                (short) 0,
                "[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, ...]"
        );
        range_short_short_helper(
                (short) 0,
                (short) 10,
                "[9, 9, 3, 2, 5, 8, 5, 3, 1, 7, 5, 3, 5, 8, 5, 1, 6, 1, 6, 5, ...]"
        );
        range_short_short_helper(
                (short) -5,
                (short) 0,
                "[-4, -4, -2, -3, 0, -5, 0, 0, -2, -4, 0, -2, 0, -5, 0, -4, -4, -1, 0, 0, ...]"
        );
        range_short_short_helper(
                (byte) -5,
                (byte) 10,
                "[4, 4, -2, -3, 0, 10, 3, 0, 8, -2, 10, -4, 2, 0, -2, 0, 3, 0, -4, 1, ...]"
        );
        range_short_short_helper(
                (short) -10,
                (short) 5,
                "[-1, -1, -7, -8, -5, 5, -2, -5, 3, -7, 5, -9, -3, -5, -7, -5, -2, -5, -9, -4, ...]"
        );
        range_short_short_helper((short) 5, (short) -10, "[]");
    }

    private static void range_int_int_helper(int a, int b, @NotNull String output) {
        P.reset();
        aeqit(TINY_LIMIT, P.range(a, b), output);
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

    private static void range_BigInteger_BigInteger_helper(int a, int b, @NotNull String output) {
        P.reset();
        aeqit(TINY_LIMIT, P.range(BigInteger.valueOf(a), BigInteger.valueOf(b)), output);
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
        P.reset();
        aeqcs(P.range(a, b), output);
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
        range_char_char_helper('a', 'A', "");
        range_char_char_helper(
                '!',
                '9',
                "**$369&$2(646962'0\"766+0%8,.#3(*%7$-&3%&!$8#&0/34'79-+/1)99%9/43$0,21*63/&'0*'!6'4044&$7&4,30,0\"$" +
                "28'83&682)#,(5)75!'/*(##4,'39$8"
        );
    }

    private static void geometricHelper(
            @NotNull Iterable<Integer> xs,
            @NotNull String output,
            @NotNull String topSampleCount,
            double sampleMean
    ) {
        P.reset();
        aeqit(take(TINY_LIMIT, xs), output);
        P.reset();
        aeq(topSampleCount(DEFAULT_SAMPLE_SIZE, DEFAULT_TOP_COUNT, xs), topSampleCount);
        P.reset();
        aeq(meanOfIntegers(xs), sampleMean);
    }

    private static void positiveIntegersGeometric_helper(
            int mean,
            @NotNull String output,
            @NotNull String topSampleCount,
            double sampleMean
    ) {
        geometricHelper(P.withScale(mean).positiveIntegersGeometric(), output, topSampleCount, sampleMean);
    }

    private static void positiveIntegersGeometric_fail_helper(int mean) {
        try {
            P.withScale(mean).positiveIntegersGeometric();
            fail();
        } catch (IllegalStateException ignored) {}
    }

//    @Test
//    public void testPositiveIntegersGeometric() {
//        positiveIntegersGeometric_helper(
//                2,
//                "[4, 3, 10, 3, 3, 1, 2, 4, 1, 1, 3, 3, 3, 1, 3, 1, 1, 2, 2, 1]",
//                "{1=499128, 2=250900, 3=124845, 4=62517, 5=31407, 6=15634, 7=7825, 8=3926, 9=1896, 10=955}",
//                1.9991409999798677
//        );
//        positiveIntegersGeometric_helper(
//                3,
//                "[5, 6, 6, 5, 3, 6, 1, 2, 3, 2, 4, 7, 2, 3, 1, 2, 1, 2, 1, 4]",
//                "{1=333814, 2=221150, 3=148026, 4=98991, 5=66271, 6=43744, 7=29390, 8=19567, 9=12958, 10=8571}",
//                2.9994739999900566
//        );
//        positiveIntegersGeometric_helper(
//                4,
//                "[12, 11, 3, 1, 10, 2, 1, 7, 12, 3, 6, 3, 2, 7, 10, 3, 1, 3, 3, 1]",
//                "{1=249614, 2=187525, 3=140141, 4=105528, 5=79409, 6=59650, 7=44238, 8=33684, 9=25035, 10=18605}",
//                3.9988879999900515
//        );
//        positiveIntegersGeometric_helper(
//                5,
//                "[14, 4, 2, 17, 8, 7, 1, 9, 4, 1, 10, 2, 4, 1, 1, 3, 1, 2, 11, 3]",
//                "{1=199697, 2=160303, 3=127808, 4=102479, 5=81596, 6=65527, 7=52365, 8=42303, 9=33510, 10=26945}",
//                5.006003000008593
//        );
//        positiveIntegersGeometric_helper(
//                10,
//                "[19, 5, 36, 1, 13, 17, 4, 1, 2, 15, 5, 3, 5, 37, 9, 4, 2, 14, 10, 11]",
//                "{1=99638, 2=90219, 3=81366, 4=73133, 5=65565, 6=58852, 7=52976, 8=47799, 9=43211, 10=38657}",
//                10.003595000005705
//        );
//        positiveIntegersGeometric_helper(
//                100,
//                "[203, 17, 113, 4, 48, 71, 163, 136, 13, 11, 86, 254, 65, 77, 29, 198, 25, 441, 193, 16]",
//                "{1=9940, 2=9939, 3=9819, 5=9622, 4=9590, 6=9561, 7=9296, 8=9269, 11=9245, 10=9202}",
//                99.92948300000171
//        );
//        positiveIntegersGeometric_fail_helper(1);
//        positiveIntegersGeometric_fail_helper(0);
//        positiveIntegersGeometric_fail_helper(-1);
//    }

    private static void negativeIntegersGeometric_helper(
            int mean,
            @NotNull String output,
            @NotNull String topSampleCount,
            double sampleMean
    ) {
        geometricHelper(P.withScale(mean).negativeIntegersGeometric(), output, topSampleCount, sampleMean);
    }

    private static void negativeIntegersGeometric_fail_helper(int mean) {
        try {
            P.withScale(mean).negativeIntegersGeometric();
            fail();
        } catch (IllegalStateException ignored) {}
    }

//    @Test
//    public void testNegativeIntegersGeometric() {
//        negativeIntegersGeometric_helper(
//                2,
//                "[-2, -8, -2, -1, -2, -1, -3, -2, -2, -3, -1, -3, -1, -1, -2, -2, -1, -1, -1, -1]",
//                "{-1=499940, -2=249619, -3=124866, -4=62860, -5=31322, -6=15709, -7=7820, -8=3979, -9=1902, -10=1020}",
//                -1.9984779999797717
//        );
//        negativeIntegersGeometric_helper(
//                3,
//                "[-10, -9, -2, -1, -9, -2, -1, -6, -8, -1, -5, -2, -2, -5, -7, -3, -1, -1, -1, -1]",
//                "{-1=332840, -2=221716, -3=148288, -4=98826, -5=66476, -6=44096, -7=29148, -8=19471, -9=13051," +
//                " -10=8677}",
//                -2.9994739999900566
//        );
//        negativeIntegersGeometric_helper(
//                4,
//                "[-12, -11, -3, -1, -10, -2, -1, -7, -12, -3, -6, -3, -2, -7, -10, -3, -1, -3, -3, -1]",
//                "{-1=249614, -2=187525, -3=140141, -4=105528, -5=79409, -6=59650, -7=44238, -8=33684, -9=25035," +
//                " -10=18605}",
//                -3.9988879999900515
//        );
//        negativeIntegersGeometric_helper(
//                5,
//                "[-14, -4, -2, -17, -8, -7, -1, -9, -4, -1, -10, -2, -4, -1, -1, -3, -1, -2, -11, -3]",
//                "{-1=199697, -2=160303, -3=127808, -4=102479, -5=81596, -6=65527, -7=52365, -8=42303, -9=33510," +
//                " -10=26945}",
//                -5.006003000008593
//        );
//        negativeIntegersGeometric_helper(
//                10,
//                "[-19, -5, -36, -1, -13, -17, -4, -1, -2, -15, -5, -3, -5, -37, -9, -4, -2, -14, -10, -11]",
//                "{-1=99638, -2=90219, -3=81366, -4=73133, -5=65565, -6=58852, -7=52976, -8=47799, -9=43211," +
//                " -10=38657}",
//                -10.003595000005705
//        );
//        negativeIntegersGeometric_helper(
//                100,
//                "[-203, -17, -113, -4, -48, -71, -163, -136, -13, -11, -86, -254, -65, -77, -29, -198, -25, -441," +
//                " -193, -16]",
//                "{-1=9940, -2=9939, -3=9819, -5=9622, -4=9590, -6=9561, -7=9296, -8=9269, -11=9245, -10=9202}",
//                -99.92948300000171
//        );
//        negativeIntegersGeometric_fail_helper(1);
//        negativeIntegersGeometric_fail_helper(0);
//        negativeIntegersGeometric_fail_helper(-1);
//    }

    private static void naturalIntegersGeometric_helper(
            int mean,
            @NotNull String output,
            @NotNull String topSampleCount,
            double sampleMean
    ) {
        geometricHelper(P.withScale(mean).naturalIntegersGeometric(), output, topSampleCount, sampleMean);
    }

    private static void naturalIntegersGeometric_fail_helper(int mean) {
        try {
            P.withScale(mean).naturalIntegersGeometric();
            fail();
        } catch (IllegalStateException ignored) {}
    }

//    @Test
//    public void testNaturalIntegersGeometric() {
//        naturalIntegersGeometric_helper(
//                1,
//                "[1, 7, 1, 0, 1, 0, 2, 1, 1, 2, 0, 2, 0, 0, 1, 1, 0, 0, 0, 0]",
//                "{0=499940, 1=249619, 2=124866, 3=62860, 4=31322, 5=15709, 6=7820, 7=3979, 8=1902, 9=1020}",
//                0.9984779999977075
//        );
//        naturalIntegersGeometric_helper(
//                2,
//                "[9, 8, 1, 0, 8, 1, 0, 5, 7, 0, 4, 1, 1, 4, 6, 2, 0, 0, 0, 0]",
//                "{0=332840, 1=221716, 2=148288, 3=98826, 4=66476, 5=44096, 6=29148, 7=19471, 8=13051, 9=8677}",
//                1.9994739999891047
//        );
//        naturalIntegersGeometric_helper(
//                3,
//                "[11, 10, 2, 0, 9, 1, 0, 6, 11, 2, 5, 2, 1, 6, 9, 2, 0, 2, 2, 0]",
//                "{0=249614, 1=187525, 2=140141, 3=105528, 4=79409, 5=59650, 6=44238, 7=33684, 8=25035, 9=18605}",
//                2.998887999991593
//        );
//        naturalIntegersGeometric_helper(
//                4,
//                "[13, 3, 1, 16, 7, 6, 0, 8, 3, 0, 9, 1, 3, 0, 0, 2, 0, 1, 10, 2]",
//                "{0=199697, 1=160303, 2=127808, 3=102479, 4=81596, 5=65527, 6=52365, 7=42303, 8=33510, 9=26945}",
//                4.006002999991825
//        );
//        naturalIntegersGeometric_helper(
//                5,
//                "[19, 6, 1, 19, 7, 7, 0, 8, 4, 0, 12, 1, 3, 0, 0, 2, 0, 1, 13, 3]",
//                "{0=166281, 1=139287, 2=115713, 3=96288, 4=80406, 5=66742, 6=55828, 7=46438, 8=38736, 9=32457}",
//                5.008427000005021
//        );
//        naturalIntegersGeometric_helper(
//                10,
//                "[19, 6, 40, 0, 12, 16, 4, 0, 1, 15, 5, 2, 6, 42, 8, 3, 2, 14, 10, 11]",
//                "{0=90322, 1=83103, 2=75278, 3=68424, 4=62228, 5=56366, 6=51156, 7=46912, 8=42293, 9=38652}",
//                10.00416700000491
//        );
//        naturalIntegersGeometric_helper(
//                100,
//                "[204, 16, 112, 3, 48, 70, 163, 136, 12, 10, 85, 254, 64, 76, 28, 199, 24, 446, 196, 15]",
//                "{0=9851, 1=9847, 2=9741, 4=9504, 5=9491, 3=9448, 6=9269, 10=9193, 7=9143, 8=9101}",
//                99.9273790000026
//        );
//        naturalIntegersGeometric_fail_helper(0);
//        naturalIntegersGeometric_fail_helper(-1);
//        naturalIntegersGeometric_fail_helper(Integer.MAX_VALUE);
//    }

    private static void nonzeroIntegersGeometric_helper(
            int mean,
            @NotNull String output,
            @NotNull String topSampleCount,
            double sampleMean,
            double sampleAbsMean
    ) {
        Iterable<Integer> xs = P.withScale(mean).nonzeroIntegersGeometric();
        geometricHelper(xs, output, topSampleCount, sampleMean);
        P.reset();
        aeq(meanOfIntegers(map(Math::abs, xs)), sampleAbsMean);
    }

    private static void nonzeroIntegersGeometric_fail_helper(int mean) {
        try {
            P.withScale(mean).nonzeroIntegersGeometric();
            fail();
        } catch (IllegalStateException ignored) {}
    }

//    @Test
//    public void testNonzeroIntegersGeometric() {
//        nonzeroIntegersGeometric_helper(
//                2,
//                "[2, 1, 6, -1, 1, -2, 3, -1, -2, 1, 1, 2, -1, 2, -1, -1, -1, -1, -1, 4]",
//                "{1=250212, -1=249778, 2=125065, -2=124955, -3=62396, 3=62275, 4=31335, -4=31095, 5=15829, -5=15532}",
//                0.001756999999999992,
//                1.9991669999799087
//        );
//        nonzeroIntegersGeometric_helper(
//                3,
//                "[10, -3, -6, 1, 1, -2, -6, -1, -1, 1, -2, 4, 4, -7, -1, -6, 4, -5, -1, 1]",
//                "{1=166894, -1=166805, 2=110928, -2=110762, 3=74009, -3=73853, 4=49472, -4=49418, -5=33315, 5=32886}",
//                7.709999999999913E-4,
//                3.0031149999898528
//        );
//        nonzeroIntegersGeometric_helper(
//                4,
//                "[12, -3, -7, 1, 1, -4, -6, -1, -1, 4, -2, 4, 7, -12, -1, -11, 5, -6, -3, 1]",
//                "{-1=125038, 1=125033, 2=93863, -2=93650, 3=70178, -3=70172, 4=52723, -4=52605, 5=39966, -5=39890}",
//                7.639999999999724E-4,
//                4.001540999990154
//        );
//        nonzeroIntegersGeometric_helper(
//                5,
//                "[14, 5, -2, -1, 17, -3, 6, -12, -4, -12, -4, -3, -2, 1, -1, -2, -1, -5, 9, 2]",
//                "{-1=100532, 1=100120, 2=80205, -2=79693, 3=64429, -3=63784, -4=50931, 4=50715, -5=41126, 5=40940}",
//                0.001546000000000001,
//                4.994863000008314
//        );
//        nonzeroIntegersGeometric_helper(
//                10,
//                "[19, 10, -31, -3, -10, -11, -9, -1, -2, -6, -12, -6, -3, 28, 8, 1, -8, -43, -22, -5]",
//                "{-1=50574, 1=50054, 2=45215, -2=44881, 3=40452, -3=40005, 4=36884, -4=36232, -5=32756, 5=32648}",
//                -0.006155000000001084,
//                10.002291000006132
//        );
//        nonzeroIntegersGeometric_helper(
//                100,
//                "[-203, -79, 56, 35, -15, 81, 143, -67, -76, 106, -5, 155, -105, -88, -51, 52, 166, 66, 400, -96]",
//                "{1=5137, -1=5047, 2=4997, 3=4981, -4=4949, 4=4936, 5=4933, -2=4868, -3=4825, -7=4805}",
//                -0.13892300000000637,
//                100.18658599999718
//        );
//        nonzeroIntegersGeometric_fail_helper(1);
//        nonzeroIntegersGeometric_fail_helper(0);
//        nonzeroIntegersGeometric_fail_helper(-1);
//    }

    private static void integersGeometric_helper(
            int mean,
            @NotNull String output,
            @NotNull String topSampleCount,
            double sampleMean,
            double sampleAbsMean
    ) {
        Iterable<Integer> xs = P.withScale(mean).integersGeometric();
        geometricHelper(xs, output, topSampleCount, sampleMean);
        P.reset();
        aeq(meanOfIntegers(map(Math::abs, xs)), sampleAbsMean);
    }

    private static void integersGeometric_fail_helper(int mean) {
        try {
            P.withScale(mean).integersGeometric();
            fail();
        } catch (IllegalStateException ignored) {}
    }

//    @Test
//    public void testIntegersGeometric() {
//        integersGeometric_helper(
//                1,
//                "[1, 0, 5, 0, 0, -1, 2, 0, -1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 3]",
//                "{0=499990, 1=125065, -1=124955, -2=62396, 2=62275, 3=31335, -3=31095, 4=15829, -4=15532, -5=7943}",
//                9.949999999999957E-4,
//                0.9991669999977268
//        );
//        integersGeometric_helper(
//                2,
//                "[9, -2, -5, 0, 0, -1, -5, 0, 0, 0, -1, 3, 3, -6, 0, -5, 3, -4, 0, 0]",
//                "{0=333699, 1=110928, -1=110762, 2=74009, -2=73853, 3=49472, -3=49418, -4=33315, 4=32886, -5=22030}",
//                7.35000000000012E-4,
//                2.003114999989133
//        );
//        integersGeometric_helper(
//                3,
//                "[11, -2, -6, 0, 0, -3, -5, 0, 0, 3, -1, 3, 6, -11, 0, -10, 4, -5, -2, 0]",
//                "{0=250071, 1=93863, -1=93650, 2=70178, -2=70172, 3=52723, -3=52605, 4=39966, -4=39890, -5=29597}",
//                7.280000000000733E-4,
//                3.0015409999914318
//        );
//        integersGeometric_helper(
//                4,
//                "[13, 4, -1, 0, 16, -2, 5, -11, -3, -11, -3, -2, -1, 0, 0, -1, 0, -4, 8, 1]",
//                "{0=200652, 1=80205, -1=79693, 2=64429, -2=63784, -3=50931, 3=50715, -4=41126, 4=40940, -5=32687}",
//                0.002093999999999925,
//                3.9948629999913425
//        );
//        integersGeometric_helper(
//                5,
//                "[19, 4, -1, 0, 19, -2, 5, -16, -3, -11, -4, -3, -1, 0, 0, -2, 0, -5, 10, 2]",
//                "{0=167187, 1=69622, -1=69189, 2=58237, -2=57649, 3=48153, -3=48152, -4=40125, 4=40037, -5=33592}",
//                0.0035149999999997965,
//                4.995530000004772
//        );
//        integersGeometric_helper(
//                10,
//                "[19, 10, -34, -4, -9, -11, -8, 0, -1, -6, -12, -7, -4, 30, 7, 0, -7, -46, -25, -4]",
//                "{0=91255, 1=41644, -1=41237, 2=37534, -2=37149, 3=34461, -3=34043, 4=31258, -4=30783, -5=28000}",
//                -0.006076999999999771,
//                10.003386000004411
//        );
//        integersGeometric_helper(
//                100,
//                "[-204, -78, 55, 34, -14, 83, 142, -67, -76, 107, -4, 155, -104, -88, -50, 51, 167, 66, 405, -97]",
//                "{0=10076, 1=4956, -3=4939, 2=4920, 3=4882, 4=4876, -1=4861, -6=4760, -2=4740, 5=4716}",
//                -0.1406840000000054,
//                100.18935300000031
//        );
//        integersGeometric_fail_helper(0);
//        integersGeometric_fail_helper(-1);
//        integersGeometric_fail_helper(Integer.MAX_VALUE);
//    }

    private static void rangeUpGeometric_helper(
            int mean,
            int a,
            @NotNull String output,
            @NotNull String topSampleCount,
            double sampleMean
    ) {
        P.reset();
        Iterable<Integer> xs = P.withScale(mean).rangeUpGeometric(a);
        geometricHelper(xs, output, topSampleCount, sampleMean);
    }

    private static void rangeUpGeometric_fail_helper(int mean, int a) {
        try {
            P.withScale(mean).rangeUpGeometric(a);
            fail();
        } catch (IllegalStateException ignored) {}
    }

//    @Test
//    public void testRangeUpGeometric() {
//        rangeUpGeometric_helper(
//                3,
//                2,
//                "[3, 9, 3, 2, 3, 2, 4, 3, 3, 4, 2, 4, 2, 2, 3, 3, 2, 2, 2, 2]",
//                "{2=499939, 3=249619, 4=124867, 5=62860, 6=31322, 7=15709, 8=7820, 9=3979, 10=1902, 11=1020}",
//                2.9984879999686456
//        );
//        rangeUpGeometric_helper(
//                10,
//                2,
//                "[17, 6, 35, 2, 14, 15, 5, 2, 2, 15, 6, 4, 5, 34, 10, 5, 3, 14, 10, 10]",
//                "{2=110828, 3=98984, 4=88328, 5=78206, 6=69166, 7=61576, 8=54610, 9=48676, 10=43124, 11=38389}",
//                10.00413200001652
//        );
//        rangeUpGeometric_helper(
//                11,
//                10,
//                "[11, 17, 11, 10, 11, 10, 12, 11, 11, 12, 10, 12, 10, 10, 11, 11, 10, 10, 10, 10]",
//                "{10=499939, 11=249619, 12=124867, 13=62860, 14=31322, 15=15709, 16=7820, 17=3979, 18=1902, 19=1020}",
//                10.998487999897376
//        );
//        rangeUpGeometric_helper(
//                20,
//                10,
//                "[29, 16, 50, 10, 22, 26, 14, 10, 11, 25, 15, 12, 16, 52, 18, 13, 12, 24, 20, 21]",
//                "{10=90321, 11=83103, 12=75278, 13=68424, 14=62228, 15=56366, 16=51156, 17=46912, 18=42293, 19=38652}",
//                20.004143000003587
//        );
//        rangeUpGeometric_helper(
//                -9,
//                -10,
//                "[-9, -3, -9, -10, -9, -10, -8, -9, -9, -8, -10, -8, -10, -10, -9, -9, -10, -10, -10, -10]",
//                "{-10=499939, -9=249619, -8=124867, -7=62860, -6=31322, -5=15709, -4=7820, -3=3979, -2=1902, -1=1020}",
//                -9.001511999952484
//        );
//        rangeUpGeometric_helper(
//                0,
//                -10,
//                "[9, -4, 30, -10, 2, 6, -6, -10, -9, 5, -5, -8, -4, 32, -2, -7, -8, 4, 0, 1]",
//                "{-10=90321, -9=83103, -8=75278, -7=68424, -6=62228, -5=56366, -4=51156, -3=46912, -2=42293," +
//                " -1=38652}",
//                0.004143000000001288
//        );
//        rangeUpGeometric_helper(
//                Integer.MAX_VALUE,
//                Integer.MAX_VALUE - 1,
//                "[2147483647, 2147483647, 2147483646, 2147483647, 2147483646, 2147483647, 2147483647, 2147483646," +
//                " 2147483646, 2147483646, 2147483647, 2147483647, 2147483646, 2147483646, 2147483646, 2147483646," +
//                " 2147483646, 2147483646, 2147483646, 2147483646]",
//                "{2147483646=667071, 2147483647=332929}",
//                2.1474836463258882E9
//        );
//        rangeUpGeometric_helper(
//                Integer.MIN_VALUE + 1,
//                Integer.MIN_VALUE,
//                "[-2147483647, -2147483641, -2147483647, -2147483648, -2147483647, -2147483648, -2147483646," +
//                " -2147483647, -2147483647, -2147483646, -2147483648, -2147483646, -2147483648, -2147483648," +
//                " -2147483647, -2147483647, -2147483648, -2147483648, -2147483648, -2147483648]",
//                "{-2147483648=499939, -2147483647=249619, -2147483646=124867, -2147483645=62860, -2147483644=31322," +
//                " -2147483643=15709, -2147483642=7820, -2147483641=3979, -2147483640=1902, -2147483639=1020}",
//                -2.1474836470173213E9
//        );
//        rangeUpGeometric_fail_helper(5, 5);
//        rangeUpGeometric_fail_helper(4, 5);
//        rangeUpGeometric_fail_helper(Integer.MAX_VALUE - 5, -10);
//    }

    private static void rangeDownGeometric_helper(
            int mean,
            int a,
            @NotNull String output,
            @NotNull String topSampleCount,
            double sampleMean
    ) {
        P.reset();
        Iterable<Integer> xs = P.withScale(mean).rangeDownGeometric(a);
        geometricHelper(xs, output, topSampleCount, sampleMean);
    }

    private static void rangeDownGeometric_fail_helper(int mean, int a) {
        try {
            P.withScale(mean).rangeDownGeometric(a);
            fail();
        } catch (IllegalStateException ignored) {}
    }

//    @Test
//    public void testRangeDownGeometric() {
//        rangeDownGeometric_helper(
//                0,
//                2,
//                "[-7, -6, 1, 2, -6, 1, 2, -3, -5, 2, -2, 1, 1, -2, -4, 0, 2, 2, 2, 2]",
//                "{2=332840, 1=221716, 0=148288, -1=98825, -2=66477, -3=44096, -4=29148, -5=19471, -6=13051, -7=8677}",
//                5.259999999993722E-4
//        );
//        rangeDownGeometric_helper(
//                -5,
//                2,
//                "[-24, -7, 1, -26, -9, -10, 2, -11, -3, 2, -18, 1, -3, 2, 1, 0, 0, 0, -17, -3]",
//                "{2=124613, 1=110177, 0=95179, -1=83698, -2=73138, -3=64223, -4=55937, -5=49251, -6=42838, -7=37636}",
//                -5.008897999995255
//        );
//        rangeDownGeometric_helper(
//                5,
//                10,
//                "[-9, 4, 9, -9, 3, 3, 10, 2, 6, 10, -2, 9, 7, 10, 10, 8, 10, 9, -3, 7]",
//                "{10=166282, 9=139287, 8=115713, 7=96287, 6=80406, 5=66742, 4=55828, 3=46438, 2=38736, 1=32457}",
//                4.991570999991792
//        );
//        rangeDownGeometric_helper(
//                0,
//                10,
//                "[-9, 4, -30, 10, -2, -6, 6, 10, 9, -5, 5, 8, 4, -32, 2, 7, 8, -4, 0, -1]",
//                "{10=90321, 9=83103, 8=75278, 7=68424, 6=62228, 5=56366, 4=51156, 3=46912, 2=42293, 1=38652}",
//                -0.004143000000001288
//        );
//        rangeDownGeometric_helper(
//                -11,
//                -10,
//                "[-11, -17, -11, -10, -11, -10, -12, -11, -11, -12, -10, -12, -10, -10, -11, -11, -10, -10, -10, -10]",
//                "{-10=499939, -11=249619, -12=124867, -13=62860, -14=31322, -15=15709, -16=7820, -17=3979, -18=1902," +
//                " -19=1020}",
//                -10.998487999897376
//        );
//        rangeDownGeometric_helper(
//                -20,
//                -10,
//                "[-29, -16, -50, -10, -22, -26, -14, -10, -11, -25, -15, -12, -16, -52, -18, -13, -12, -24, -20, -21]",
//                "{-10=90321, -11=83103, -12=75278, -13=68424, -14=62228, -15=56366, -16=51156, -17=46912, -18=42293," +
//                " -19=38652}",
//                -20.004143000003587
//        );
//        rangeDownGeometric_helper(
//                Integer.MAX_VALUE - 1,
//                Integer.MAX_VALUE,
//                "[2147483646, 2147483640, 2147483646, 2147483647, 2147483646, 2147483647, 2147483645, 2147483646," +
//                " 2147483646, 2147483645, 2147483647, 2147483645, 2147483647, 2147483647, 2147483646, 2147483646," +
//                " 2147483647, 2147483647, 2147483647, 2147483647]",
//                "{2147483647=499939, 2147483646=249619, 2147483645=124867, 2147483644=62860, 2147483643=31322," +
//                " 2147483642=15709, 2147483641=7820, 2147483640=3979, 2147483639=1902, 2147483638=1020}",
//                2.1474836460161636E9
//        );
//        rangeDownGeometric_helper(
//                Integer.MIN_VALUE,
//                Integer.MIN_VALUE + 1,
//                "[-2147483648, -2147483648, -2147483647, -2147483648, -2147483647, -2147483648, -2147483648," +
//                " -2147483647, -2147483647, -2147483647, -2147483648, -2147483648, -2147483647, -2147483647," +
//                " -2147483647, -2147483647, -2147483647, -2147483647, -2147483647, -2147483647]",
//                "{-2147483647=667071, -2147483648=332929}",
//                -2.1474836473717701E9
//        );
//        rangeDownGeometric_fail_helper(5, 5);
//        rangeDownGeometric_fail_helper(6, 5);
//        rangeDownGeometric_fail_helper(-5, Integer.MIN_VALUE);
//    }

    private static void bigIntegerHelper(
            @NotNull Iterable<BigInteger> xs,
            @NotNull String output,
            @NotNull String topSampleCount,
            double sampleMean,
            double bitSizeMean
    ) {
        P.reset();
        aeqit(take(TINY_LIMIT, xs), output);
        P.reset();
        aeq(topSampleCount(DEFAULT_SAMPLE_SIZE, DEFAULT_TOP_COUNT, xs), topSampleCount);
        P.reset();
        aeq(meanOfBigIntegers(xs), sampleMean);
        P.reset();
        aeq(meanOfIntegers(map(x -> x.abs().bitLength(), xs)), bitSizeMean);
    }

    private void positiveBigIntegers_helper(
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
    }

    private void positiveBigIntegers_fail_helper(int meanBitSize) {
        try {
            P.withScale(meanBitSize).positiveBigIntegers();
            fail();
        } catch (IllegalStateException ignored) {}
    }

//    @Test
//    public void testPositiveBigIntegers() {
//        positiveBigIntegers_helper(
//                2,
//                "[3, 1, 47, 1, 1, 2, 7, 1, 2, 1, 1, 3, 1, 3, 1, 1, 1, 1, 1, 9]",
//                "{1=499990, 3=125065, 2=124955, 6=31410, 5=31166, 7=31109, 4=30986, 15=7895, 11=7830, 9=7808}",
//                54.64653399976356,
//                1.9991669999799087
//        );
//        positiveBigIntegers_helper(
//                3,
//                "[850, 6, 54, 1, 1, 2, 40, 1, 1, 1, 2, 13, 15, 86, 1, 38, 9, 20, 1, 1]",
//                "{1=333699, 3=110928, 2=110762, 5=37023, 7=36986, 6=36946, 4=36907, 12=12526, 13=12508, 15=12456}",
//                53893.43959620475,
//                3.003121999989852
//        );
//        positiveBigIntegers_helper(
//                4,
//                "[3922, 6, 86, 1, 1, 10, 40, 1, 1, 9, 2, 13, 111, 3647, 1, 1758, 17, 52, 4, 1]",
//                "{1=250073, 3=93859, 2=93648, 6=35281, 5=35275, 7=34903, 4=34894, 15=13323, 12=13195, 11=13189}",
//                1.8602743851232636E8,
//                4.001590999990151
//        );
//        positiveBigIntegers_helper(
//                5,
//                "[9003, 29, 2, 1, 88152, 6, 35, 3136, 12, 2653, 14, 4, 2, 1, 1, 2, 1, 30, 370, 3]",
//                "{1=200665, 3=80188, 2=79678, 5=32247, 7=32171, 4=31926, 6=31854, 14=12900, 9=12855, 10=12817}",
//                5.391946638294092E14,
//                4.9944610000082985
//        );
//        positiveBigIntegers_helper(
//                10,
//                "[469790, 860, 1184451838, 4, 515, 1707, 501, 1, 2, 62, 2187, 34, 6, 164983924, 228, 1, 166," +
//                " 7073663000613, 1774, 22678]",
//                "{1=100515, 3=45146, 2=44939, 7=20325, 5=20315, 4=20010, 6=20007, 9=9372, 11=9214, 14=9173}",
//                2.6155516373313137E34,
//                10.000194000005964
//        );
//        positiveBigIntegers_helper(
//                100,
//                "[7474222091329785814872640740054410525911684644179028352872359, 48462717462863011095245," +
//                " 25866170431568200, 7, 553871950, 29221, 1869439032775210910279904," +
//                " 4309140957379502269427663397985930682460781, 43045926355353680917, 22971436291561472210587," +
//                " 22382654889914592249016927170013, 339229681798927478466416769469956670709422563632," +
//                " 9832880511906474761920146323118, 14967622123686602716, 673179, 1323235453630777, 171406076," +
//                " 992404999, 49548070921229917697270742754368810273752217529416, 2955542]",
//                "{1=9999, 3=5013, 2=4914, 5=2540, 7=2490, 6=2465, 4=2419, 12=1247, 8=1227, 11=1222}",
//                Double.POSITIVE_INFINITY,
//                100.16059999999607
//        );
//        positiveBigIntegers_fail_helper(1);
//        positiveBigIntegers_fail_helper(0);
//        positiveBigIntegers_fail_helper(-1);
//    }

    private void negativeBigIntegers_helper(
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
    }

    private void negativeBigIntegers_fail_helper(int meanBitSize) {
        try {
            P.withScale(meanBitSize).negativeBigIntegers();
            fail();
        } catch (IllegalStateException ignored) {}
    }

//    @Test
//    public void testNegativeBigIntegers() {
//        negativeBigIntegers_helper(
//                2,
//                "[-3, -1, -47, -1, -1, -2, -7, -1, -2, -1, -1, -3, -1, -3, -1, -1, -1, -1, -1, -9]",
//                "{-1=499990, -3=125065, -2=124955, -6=31410, -5=31166, -7=31109, -4=30986, -15=7895, -11=7830," +
//                " -9=7808}",
//                -54.64653399976356,
//                1.9991669999799087
//        );
//        negativeBigIntegers_helper(
//                3,
//                "[-850, -6, -54, -1, -1, -2, -40, -1, -1, -1, -2, -13, -15, -86, -1, -38, -9, -20, -1, -1]",
//                "{-1=333699, -3=110928, -2=110762, -5=37023, -7=36986, -6=36946, -4=36907, -12=12526, -13=12508," +
//                " -15=12456}",
//                -53893.43959620475,
//                3.003121999989852
//        );
//        negativeBigIntegers_helper(
//                4,
//                "[-3922, -6, -86, -1, -1, -10, -40, -1, -1, -9, -2, -13, -111, -3647, -1, -1758, -17, -52, -4, -1]",
//                "{-1=250073, -3=93859, -2=93648, -6=35281, -5=35275, -7=34903, -4=34894, -15=13323, -12=13195," +
//                " -11=13189}",
//                -1.8602743851232636E8,
//                4.001590999990151
//        );
//        negativeBigIntegers_helper(
//                5,
//                "[-9003, -29, -2, -1, -88152, -6, -35, -3136, -12, -2653, -14, -4, -2, -1, -1, -2, -1, -30, -370, -3]",
//                "{-1=200665, -3=80188, -2=79678, -5=32247, -7=32171, -4=31926, -6=31854, -14=12900, -9=12855," +
//                " -10=12817}",
//                -5.391946638294092E14,
//                4.9944610000082985
//        );
//        negativeBigIntegers_helper(
//                10,
//                "[-469790, -860, -1184451838, -4, -515, -1707, -501, -1, -2, -62, -2187, -34, -6, -164983924, -228," +
//                " -1, -166, -7073663000613, -1774, -22678]",
//                "{-1=100515, -3=45146, -2=44939, -7=20325, -5=20315, -4=20010, -6=20007, -9=9372, -11=9214, -14=9173}",
//                -2.6155516373313137E34,
//                10.000194000005964
//        );
//        negativeBigIntegers_helper(
//                100,
//                "[-7474222091329785814872640740054410525911684644179028352872359, -48462717462863011095245," +
//                " -25866170431568200, -7, -553871950, -29221, -1869439032775210910279904," +
//                " -4309140957379502269427663397985930682460781, -43045926355353680917, -22971436291561472210587," +
//                " -22382654889914592249016927170013, -339229681798927478466416769469956670709422563632," +
//                " -9832880511906474761920146323118, -14967622123686602716, -673179, -1323235453630777, -171406076," +
//                " -992404999, -49548070921229917697270742754368810273752217529416, -2955542]",
//                "{-1=9999, -3=5013, -2=4914, -5=2540, -7=2490, -6=2465, -4=2419, -12=1247, -8=1227, -11=1222}",
//                Double.NEGATIVE_INFINITY,
//                100.16059999999607
//        );
//        negativeBigIntegers_fail_helper(1);
//        negativeBigIntegers_fail_helper(0);
//        negativeBigIntegers_fail_helper(-1);
//    }

    private void naturalBigIntegers_helper(
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
    }

    private void naturalBigIntegers_fail_helper(int meanBitSize) {
        try {
            P.withScale(meanBitSize).naturalBigIntegers();
            fail();
        } catch (IllegalStateException ignored) {}
    }

//    @Test
//    public void testNaturalBigIntegers() {
//        naturalBigIntegers_helper(
//                1,
//                "[1, 0, 31, 0, 0, 1, 3, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 5]",
//                "{0=499990, 1=250020, 2=62396, 3=62275, 7=15725, 5=15610, 6=15552, 4=15543, 15=4012, 11=3986}",
//                29.646820000052397,
//                0.9991669999977268
//        );
//        naturalBigIntegers_helper(
//                2,
//                "[338, 2, 22, 0, 0, 1, 24, 0, 0, 0, 1, 5, 7, 54, 0, 22, 5, 12, 0, 0]",
//                "{0=333699, 1=221690, 3=74009, 2=73853, 5=24813, 4=24785, 7=24659, 6=24634, 14=8418, 12=8380}",
//                28906.65638409626,
//                2.003113999989134
//        );
//        naturalBigIntegers_helper(
//                3,
//                "[1874, 2, 54, 0, 0, 6, 24, 0, 0, 5, 1, 5, 47, 1599, 0, 734, 9, 20, 2, 0]",
//                "{0=250073, 1=187509, 3=70180, 2=70174, 7=26510, 4=26346, 6=26253, 5=26211, 13=10051, 12=10045}",
//                8.772158340030755E7,
//                3.0015929999914364
//        );
//        naturalBigIntegers_helper(
//                4,
//                "[4907, 13, 1, 0, 55384, 2, 19, 1088, 4, 1629, 6, 2, 1, 0, 0, 1, 0, 14, 242, 1]",
//                "{0=200665, 1=159860, 3=64423, 2=63774, 6=25710, 5=25477, 7=25228, 4=25222, 14=10345, 8=10322}",
//                2.108336919780064E14,
//                3.9945489999913373
//        );
//        naturalBigIntegers_helper(
//                5,
//                "[469790, 13, 1, 0, 350296, 2, 19, 49189, 4, 1629, 14, 4, 1, 0, 0, 2, 0, 30, 882, 3]",
//                "{0=167158, 1=138802, 3=58252, 2=57662, 5=24261, 6=24102, 4=24034, 7=23913, 9=10199, 12=10086}",
//                1.5293391081642047E19,
//                4.995412000004775
//        );
//        naturalBigIntegers_helper(
//                10,
//                "[469790, 860, 11161894480, 2563, 1707, 245, 0, 1, 62, 2187, 98, 14, 836072564, 97, 0, 116," +
//                " 51054128111653, 1774, 38518, 14]",
//                "{0=91220, 1=82765, 3=37698, 2=37204, 7=17244, 5=17193, 6=17121, 4=16959, 13=7907, 9=7852}",
//                6.030744909293769E39,
//                9.998209000004323
//        );
//        naturalBigIntegers_helper(
//                100,
//                "[20329726445401708019208337478783711346089308594441371035283367, 29573251531384430240461," +
//                " 16858971176827208, 3, 285436494, 12837, 6705142311233727609104608," +
//                " 1521547807563174376735698613904885494213229, 43045926355353680917, 22971436291561472210587," +
//                " 62947474097217933096911429742045, 339229681798927478466416769469956670709422563632," +
//                " 4762278110993557155933333501614, 9137724122699570727, 22200207024421534130668, 887194347596460," +
//                " 24694362711025200515048602379622132673119032028174927216526184615040844, 13," +
//                " 97989174275153552236308131833898805441012036366390825136719667690067875080310351682352837161845946" +
//                "067076860677343772662, 13161932779676555289674991699]",
//                "{0=9962, 1=9853, 3=4943, 2=4825, 4=2466, 5=2419, 7=2376, 6=2352, 13=1244, 8=1206}",
//                Double.POSITIVE_INFINITY,
//                100.11822800000165
//        );
//        naturalBigIntegers_fail_helper(0);
//        naturalBigIntegers_fail_helper(-1);
//    }

    private void nonzeroBigIntegers_helper(
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
    }

    private void nonzeroBigIntegers_fail_helper(int meanBitSize) {
        try {
            P.withScale(meanBitSize).nonzeroBigIntegers();
            fail();
        } catch (IllegalStateException ignored) {}
    }

//    @Test
//    public void testNonzeroBigIntegers() {
//        nonzeroBigIntegers_helper(
//                2,
//                "[3, 79, -1, -2, 7, 1, 1, -3, -4, 1, 1, -1, -1, -1, 1, -10, 1, 1, 13, -1]",
//                "{-1=250035, 1=249416, 3=63007, -3=62500, -2=62270, 2=62035, 6=15936, -5=15736, 4=15722, -6=15683}",
//                -37.760902999999686,
//                1.9988649999798511
//        );
//        nonzeroBigIntegers_helper(
//                3,
//                "[-850, 244, -3, -337, -1, -50, 111, 1, -9, -1, -1, -11, -33, -2, 1, -1, -12, -2, -2, 6]",
//                "{-1=166944, 1=166213, 3=55582, -3=55349, 2=55102, -2=54996, -4=18792, -5=18711, 7=18653, 6=18530}",
//                -1693122.3375589792,
//                3.004373999989849
//        );
//        nonzeroBigIntegers_helper(
//                4,
//                "[-3922, 628, -3, -593, -1, -114, 1866, 3, -25, -2, -1, -35, -458, -2, 6, -2, -76, -2, -2, 6]",
//                "{-1=124929, 1=124786, 3=46816, -3=46725, 2=46538, -2=46491, 7=17813, -7=17808, 4=17647, -5=17627}",
//                -1.6537285501976652E9,
//                4.000947999990135
//        );
//        nonzeroBigIntegers_helper(
//                5,
//                "[9003, -8, -1, -55384, -67, -38, -441, -6, 622, 1, -10, -1, -2, 1, -1, -1906, 4, -12, -7, -38]",
//                "{-1=99894, 1=99615, -3=40143, 3=40121, 2=39864, -2=39827, 6=16178, -7=16168, -4=16084, 5=16067}",
//                -9.634700172191952E13,
//                4.996535000008218
//        );
//        nonzeroBigIntegers_helper(
//                10,
//                "[469790, -8, -62701502032, -4, -437219, 116, 26, -2, 62, 52, 9, 181, -1, 16466080783, -97, 166, -7," +
//                " -1, -6268, 650]",
//                "{-1=50324, 1=50109, -3=22697, 3=22605, 2=22568, -2=22349, 4=10301, 7=10252, -4=10192, -6=10176}",
//                5.231567747853736E32,
//                9.997070000005944
//        );
//        nonzeroBigIntegers_helper(
//                100,
//                "[-7474222091329785814872640740054410525911684644179028352872359, -14460," +
//                " -4647611991422616850007212632301013, -4352265912, 12837, 638621732503242780896," +
//                " -2143310024519363472901233232922339077187179213677, 24599182281644129301, -1125686953," +
//                " 3445661878449440726672398129191623636626977525, 156541977132564613690956165380421293252430995760," +
//                " -593941924706611343960195990, 61156276467143510523143200490, 760285500209465, -2013094254439084," +
//                " -26164044723935471006011785430908281959257296841800, -10661988971116141735, 53, -43870267," +
//                " -15773968203237]",
//                "{1=4991, -1=4952, 3=2511, -3=2456, 2=2439, -2=2381, -7=1306, 6=1262, -6=1222, -5=1220}",
//                Double.NaN,
//                100.19517499999985
//        );
//        nonzeroBigIntegers_fail_helper(1);
//        nonzeroBigIntegers_fail_helper(0);
//        nonzeroBigIntegers_fail_helper(-1);
//    }

    private void bigIntegers_helper(
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
    }

    private void bigIntegers_fail_helper(int meanBitSize) {
        try {
            P.withScale(meanBitSize).bigIntegers();
            fail();
        } catch (IllegalStateException ignored) {}
    }

//    @Test
//    public void testBigIntegers() {
//        bigIntegers_helper(
//                1,
//                "[1, 47, 0, -1, 3, 0, 0, -1, -2, 0, 0, 0, 0, 0, 0, -6, 0, 0, 5, 0]",
//                "{0=499451, 1=125042, -1=124770, 2=31658, -3=31362, 3=31257, -2=31081, -6=7969, -7=7927, -5=7840}",
//                -21.388764000000428,
//                0.9988649999976881
//        );
//        bigIntegers_helper(
//                2,
//                "[-338, 86, -1, -209, 0, -18, 47, 0, -5, 0, 0, -7, -17, -1, 0, 0, -4, -1, -1, 2]",
//                "{0=333153, 1=111005, -1=110318, 2=37213, 3=37171, -3=37087, -2=37057, 7=12504, -7=12481, 5=12458}",
//                -32114.49441799994,
//                2.0043589999890816
//        );
//        bigIntegers_helper(
//                3,
//                "[-1874, 372, -1, -337, 0, -50, 842, 1, -9, -1, 0, -19, -202, -1, 2, -1, -44, -1, -1, 2]",
//                "{0=250249, -1=93477, 1=93180, -3=35389, 2=35281, 3=35111, -2=34974, 5=13212, -6=13197, 7=13196}",
//                -1.0442690302550374E9,
//                3.0032639999915482
//        );
//        bigIntegers_helper(
//                4,
//                "[4907, -4, 0, -22872, -35, -22, -185, -2, 366, 0, -6, 0, -1, 0, 0, -882, 2, -4, -3, -22]",
//                "{0=199644, -1=80033, 1=80021, -3=32098, -2=32080, 3=32032, 2=31913, 6=13080, -7=12916, 4=12902}",
//                -5.565178732969937E13,
//                3.9967439999914847
//        );
//        bigIntegers_helper(
//                5,
//                "[469790, -40, 0, -219224, -35, -38, -185, -6, 3694, 0, -6, 0, -1, 0, 0, -7026, 4, -4, -3, -38]",
//                "{0=166834, -1=69661, 1=69236, -3=29288, 2=28811, 3=28698, -2=28552, -4=12271, 5=12239, 6=12197}",
//                4.448863958980225E18,
//                4.997573000004776
//        );
//        bigIntegers_helper(
//                10,
//                "[469790, -24, -303219670608, -4, -961507, 52, 10, -1, 62, 52, 5, 437, 0, 42235884559, -33, 116, -3," +
//                " -1, -6268, 650]",
//                "{0=91145, 1=41558, -1=41309, -3=18945, -2=18846, 3=18755, 2=18649, 5=8821, 7=8747, -5=8575}",
//                2.7766415629000997E38,
//                9.990744000004389
//        );
//        bigIntegers_helper(
//                100,
//                "[-20329726445401708019208337478783711346089308594441371035283367, -6268," +
//                " -1863854008273796605649540541372381, -2204782264, 4645, 343473827323889955040," +
//                " -2143310024519363472901233232922339077187179213677, 24599182281644129301, -588816041," +
//                " 17718138805509039537255257823686575000454443765, 156541977132564613690956165380421293252430995760," +
//                " -593941924706611343960195990, 61156276467143510523143200490, 478810523498809, -887194347596460," +
//                " -49548070921229917697270742754368810273752217529416, -10661988971116141735, 21, -27093051," +
//                " -6977875181029]",
//                "{0=9844, 1=4871, -1=4824, 2=2464, -3=2460, -2=2395, 3=2388, -7=1251, 7=1241, 6=1201}",
//                Double.NaN,
//                100.28482900000226
//        );
//        bigIntegers_fail_helper(0);
//        bigIntegers_fail_helper(-1);
//    }

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
    }

    private void rangeUp_BigInteger_fail_helper(int meanBitSize, int a) {
        try {
            P.withScale(meanBitSize).rangeUp(BigInteger.valueOf(a));
            fail();
        } catch (IllegalStateException ignored) {}
    }

//    @Test
//    public void testRangeUp_BigInteger() {
//        rangeUp_BigInteger_helper(
//                5,
//                8,
//                "[21, 13, 331, 12, 13, 26, 43, 10, 22, 13, 13, 21, 12, 29, 8, 10, 8, 8, 8, 105]",
//                "{9=62696, 8=62669, 13=62661, 15=62580, 12=62556, 10=62458, 11=62275, 14=62095, 26=15768, 19=15733}",
//                436.0807689998774,
//                4.999166999951439
//        );
//        rangeUp_BigInteger_helper(
//                10,
//                8,
//                "[120266492, 489, 24, 8, 425220133, 54, 939, 3948581, 697, 89563, 1646, 116, 50, 15, 8, 46, 14, 357," +
//                " 19314, 49]",
//                "{12=18089, 8=18054, 10=18023, 9=17964, 15=17944, 14=17902, 11=17796, 13=17784, 27=7788, 17=7787}",
//                2.9060601210889514E24,
//                9.995027999993223
//        );
//        rangeUp_BigInteger_helper(
//                5,
//                10,
//                "[21, 15, 331, 14, 15, 26, 43, 12, 22, 15, 15, 21, 14, 29, 10, 12, 10, 10, 10, 105]",
//                "{15=83579, 14=83560, 11=83513, 10=83358, 12=83263, 13=83003, 21=15797, 26=15776, 16=15728, 19=15715}",
//                427.4379230003693,
//                4.999171999951396
//        );
//        rangeUp_BigInteger_helper(
//                10,
//                10,
//                "[120266492, 489, 24, 10, 425220133, 54, 939, 3948581, 697, 89563, 1646, 116, 50, 10, 32, 3685," +
//                " 19314, 49, 10, 2741]",
//                "{14=24114, 10=23949, 11=23922, 12=23916, 15=23742, 13=23741, 27=7822, 17=7740, 19=7738, 30=7725}",
//                2.906055139587819E24,
//                9.9939089999934
//        );
//        rangeUp_BigInteger_helper(
//                1,
//                0,
//                "[1, 0, 31, 0, 0, 1, 3, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 5]",
//                "{0=499990, 1=250020, 2=62396, 3=62275, 7=15725, 5=15610, 6=15552, 4=15543, 15=4012, 11=3986}",
//                29.646820000052397,
//                0.9991669999977268
//        );
//        rangeUp_BigInteger_helper(
//                10,
//                0,
//                "[469790, 860, 11161894480, 2563, 1707, 245, 0, 1, 62, 2187, 98, 14, 836072564, 97, 0, 116," +
//                " 51054128111653, 1774, 38518, 14]",
//                "{0=91220, 1=82765, 3=37698, 2=37204, 7=17244, 5=17193, 6=17121, 4=16959, 13=7907, 9=7852}",
//                6.030744909293769E39,
//                9.998209000004323
//        );
//        rangeUp_BigInteger_helper(
//                5,
//                -8,
//                "[469790, 8, -1, 0, 219224, 2, 19, 49189, 4, 1629, 10, -2, -1, 0, 0, 2, 0, 30, 882, -3]",
//                "{0=166710, 1=69653, -1=69334, -3=29279, 3=29013, -2=28840, 2=28795, -5=12210, 4=12085, -6=12073}",
//                1.5203665801285165E19,
//                4.998150000004849
//        );
//        rangeUp_BigInteger_helper(
//                10,
//                -8,
//                "[469790, 860, 11161894480, 2563, 1707, 245, 0, -1, 62, 2187, 98, 14, 836072564, 97, 0, 116," +
//                " 51054128111653, 1774, 38518, 13]",
//                "{0=91121, 1=41469, -1=41206, -3=18959, 3=18737, 2=18714, -2=18584, -7=8711, -5=8644, 6=8606}",
//                6.030744674454112E39,
//                9.997504000004174
//        );
//        rangeUp_BigInteger_helper(
//                5,
//                -10,
//                "[469790, -10, 0, 0, 350296, 2, 19, 49189, 4, 1629, 10, -2, -1, 0, 0, 2, 0, 30, 882, -3]",
//                "{0=166855, 1=69585, -1=69273, -3=29244, 3=29026, 2=28839, -2=28834, -5=12210, -6=12099, -7=12081}",
//                1.5293390505087541E19,
//                4.996572000004833
//        );
//        rangeUp_BigInteger_helper(
//                10,
//                -10,
//                "[469790, 860, 11161894480, 2563, 1707, 245, 0, -1, 62, 2187, 98, 14, 836072564, 97, 0, 116," +
//                " 51054128111653, 1774, 38518, -9]",
//                "{0=91172, 1=41440, -1=41273, -3=18984, 3=18751, 2=18724, -2=18536, -7=8710, -5=8657, 6=8592}",
//                6.030744674401281E39,
//                9.999709000004243
//        );
//        rangeUp_BigInteger_fail_helper(4, 10);
//        rangeUp_BigInteger_fail_helper(3, 10);
//        rangeUp_BigInteger_fail_helper(Integer.MAX_VALUE, -10);
//    }

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
    }

    private void rangeDown_BigInteger_fail_helper(int meanBitSize, int a) {
        try {
            P.withScale(meanBitSize).rangeDown(BigInteger.valueOf(a));
            fail();
        } catch (IllegalStateException ignored) {}
    }

//    @Test
//    public void testRangeDown_BigInteger() {
//        rangeDown_BigInteger_helper(
//                5,
//                8,
//                "[-469790, -8, 1, 0, -219224, -2, -19, -49189, -4, -1629, -10, 2, 1, 0, 0, -2, 0, -30, -882, 3]",
//                "{0=166710, -1=69653, 1=69334, 3=29279, -3=29013, 2=28840, -2=28795, 5=12210, -4=12085, 6=12073}",
//                -1.5203665801285165E19,
//                4.998150000004849
//        );
//        rangeDown_BigInteger_helper(
//                10,
//                8,
//                "[-469790, -860, -11161894480, -2563, -1707, -245, 0, 1, -62, -2187, -98, -14, -836072564, -97, 0," +
//                " -116, -51054128111653, -1774, -38518, -13]",
//                "{0=91121, -1=41469, 1=41206, 3=18959, -3=18737, -2=18714, 2=18584, 7=8711, 5=8644, -6=8606}",
//                -6.030744674454112E39,
//                9.997504000004174
//        );
//        rangeDown_BigInteger_helper(
//                5,
//                10,
//                "[-469790, 10, 0, 0, -350296, -2, -19, -49189, -4, -1629, -10, 2, 1, 0, 0, -2, 0, -30, -882, 3]",
//                "{0=166855, -1=69585, 1=69273, 3=29244, -3=29026, -2=28839, 2=28834, 5=12210, 6=12099, 7=12081}",
//                -1.5293390505087541E19,
//                4.996572000004833
//        );
//        rangeDown_BigInteger_helper(
//                10,
//                10,
//                "[-469790, -860, -11161894480, -2563, -1707, -245, 0, 1, -62, -2187, -98, -14, -836072564, -97, 0," +
//                " -116, -51054128111653, -1774, -38518, 9]",
//                "{0=91172, -1=41440, 1=41273, 3=18984, -3=18751, -2=18724, 2=18536, 7=8710, 5=8657, -6=8592}",
//                -6.030744674401281E39,
//                9.999709000004243
//        );
//        rangeDown_BigInteger_helper(
//                1,
//                0,
//                "[-1, 0, -31, 0, 0, -1, -3, 0, -1, 0, 0, -1, 0, -1, 0, 0, 0, 0, 0, -5]",
//                "{0=499990, -1=250020, -2=62396, -3=62275, -7=15725, -5=15610, -6=15552, -4=15543, -15=4012," +
//                " -11=3986}",
//                -29.646820000052397,
//                0.9991669999977268
//        );
//        rangeDown_BigInteger_helper(
//                10,
//                0,
//                "[-469790, -860, -11161894480, -2563, -1707, -245, 0, -1, -62, -2187, -98, -14, -836072564, -97, 0," +
//                " -116, -51054128111653, -1774, -38518, -14]",
//                "{0=91220, -1=82765, -3=37698, -2=37204, -7=17244, -5=17193, -6=17121, -4=16959, -13=7907, -9=7852}",
//                -6.030744909293769E39,
//                9.998209000004323
//        );
//        rangeDown_BigInteger_helper(
//                5,
//                -8,
//                "[-21, -13, -331, -12, -13, -26, -43, -10, -22, -13, -13, -21, -12, -29, -8, -10, -8, -8, -8, -105]",
//                "{-9=62696, -8=62669, -13=62661, -15=62580, -12=62556, -10=62458, -11=62275, -14=62095, -26=15768," +
//                " -19=15733}",
//                -436.0807689998774,
//                4.999166999951439
//        );
//        rangeDown_BigInteger_helper(
//                10,
//                -8,
//                "[-120266492, -489, -24, -8, -425220133, -54, -939, -3948581, -697, -89563, -1646, -116, -50, -15," +
//                " -8, -46, -14, -357, -19314, -49]",
//                "{-12=18089, -8=18054, -10=18023, -9=17964, -15=17944, -14=17902, -11=17796, -13=17784, -27=7788," +
//                " -17=7787}",
//                -2.9060601210889514E24,
//                9.995027999993223
//        );
//        rangeDown_BigInteger_helper(
//                5,
//                -10,
//                "[-21, -15, -331, -14, -15, -26, -43, -12, -22, -15, -15, -21, -14, -29, -10, -12, -10, -10, -10," +
//                " -105]",
//                "{-15=83579, -14=83560, -11=83513, -10=83358, -12=83263, -13=83003, -21=15797, -26=15776, -16=15728," +
//                " -19=15715}",
//                -427.4379230003693,
//                4.999171999951396
//        );
//        rangeDown_BigInteger_helper(
//                10,
//                -10,
//                "[-120266492, -489, -24, -10, -425220133, -54, -939, -3948581, -697, -89563, -1646, -116, -50, -10," +
//                " -32, -3685, -19314, -49, -10, -2741]",
//                "{-14=24114, -10=23949, -11=23922, -12=23916, -15=23742, -13=23741, -27=7822, -17=7740, -19=7738," +
//                " -30=7725}",
//                -2.906055139587819E24,
//                9.9939089999934
//        );
//        rangeDown_BigInteger_fail_helper(4, -10);
//        rangeDown_BigInteger_fail_helper(3, -10);
//        rangeDown_BigInteger_fail_helper(Integer.MAX_VALUE, 10);
//    }

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
        aeq(P, "RandomProvider[@2311470349995791220, 32, 8]");
        aeq(Q.withScale(3).withSecondaryScale(0), "RandomProvider[@2476331594451070591, 3, 0]");
        aeq(R.withScale(0).withSecondaryScale(10), "RandomProvider[@7136644727607569499, 0, 10]");
    }

    private static double meanOfIntegers(@NotNull Iterable<Integer> xs) {
        return sumDouble(map(i -> (double) i / DEFAULT_SAMPLE_SIZE, take(DEFAULT_SAMPLE_SIZE, xs)));
    }

    private static double meanOfBigIntegers(@NotNull Iterable<BigInteger> xs) {
        return sumDouble(map(i -> i.doubleValue() / DEFAULT_SAMPLE_SIZE, take(DEFAULT_SAMPLE_SIZE, xs)));
    }

    private static @NotNull List<Integer> readIntegerList(@NotNull String s) {
        return Readers.readList(Readers::readInteger).apply(s).get();
    }

    private static @NotNull List<Integer> readIntegerListWithNulls(@NotNull String s) {
        return Readers.readListWithNulls(Readers::readInteger).apply(s).get();
    }
}
// @formatter:on
