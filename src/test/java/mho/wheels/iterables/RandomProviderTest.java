package mho.wheels.iterables;

import mho.wheels.io.Readers;
import mho.wheels.math.BinaryFraction;
import mho.wheels.numberUtils.FloatingPointUtils;
import mho.wheels.random.IsaacPRNG;
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
    public void testIntegers() {
        aeqit(
                take(TINY_LIMIT, P.integers()),
                "[-1084795351, 1143001545, -1986160253, -1177145870, -968883275, -1465892161, -470080200," +
                " -2011352603, -248472835, 1997176995, 293205759, -106693423, -1593537177, -206249451, 565581811," +
                " -195502731, 102870776, -1612587755, -483804495, -831718234]"
        );
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
    public void testBooleans() {
        simpleProviderHelper(
                P.booleans(),
                "[true, true, true, false, true, true, false, true, true, true, true, true, true, true, true, true," +
                " false, true, true, false]",
                "[true=499965, false=500035]"
        );
    }

    private static void uniformSample_Iterable_helper(@NotNull String xs, @NotNull String output) {
        aeqitLimit(TINY_LIMIT, P.uniformSample(readIntegerListWithNulls(xs)), output);
        P.reset();
    }

    private static void uniformSample_Iterable_fail_helper(@NotNull String xs) {
        try {
            P.uniformSample(readIntegerListWithNulls(xs));
        } catch (IllegalArgumentException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testUniformSample_Iterable() {
        uniformSample_Iterable_helper(
                "[3, 1, 4, 1]",
                "[1, 1, 1, 4, 1, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 4, ...]"
        );
        uniformSample_Iterable_helper(
                "[3, 1, null, 1]",
                "[1, 1, 1, null, 1, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 1, null, ...]"
        );
        uniformSample_Iterable_fail_helper("[]");
    }

    private static void uniformSample_String_helper(@NotNull String s, @NotNull String output) {
        aeqcs(P.uniformSample(s), output);
        P.reset();
    }

    private static void uniformSample_String_fail_helper(@NotNull String s) {
        try {
            P.uniformSample(s);
        } catch (IllegalArgumentException ignored) {}
        finally {
            P.reset();
        }
    }

    @Test
    public void testUniformSample_String() {
        uniformSample_String_helper(
                "hello",
                "eellhlelheeooleollleoololohllollllholhhlhhohllllehelollehllllllleolleolelehelllohoohelllllehllllolo" +
                "llloellhhllloollohohlllohollo"
        );
        uniformSample_String_fail_helper("");
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
    public void testPositiveBytes() {
        aeqit(take(TINY_LIMIT, P.positiveBytes()),
                "[41, 73, 3, 114, 53, 63, 56, 101, 125, 35, 127, 81, 103, 21, 115, 117, 120, 21, 49, 38]");
    }

    @Test
    public void testPositiveShorts() {
        aeqit(take(TINY_LIMIT, P.positiveShorts()),
                "[22057, 20937, 6531, 11762, 949, 17087, 9528, 12773, 6909, 163, 30463, 31953, 3431, 25109, 6131," +
                " 23925, 12024, 23829, 15025, 31910]");
    }

    @Test
    public void testPositiveIntegers() {
        aeqit(take(TINY_LIMIT, P.positiveIntegers()),
                "[1062688297, 1143001545, 161323395, 970337778, 1178600373, 681591487, 1677403448, 136131045," +
                " 1899010813, 1997176995, 293205759, 2040790225, 553946471, 1941234197, 565581811, 1951980917," +
                " 102870776, 534895893, 1663679153, 1315765414]");
    }

    @Test
    public void testPositiveLongs() {
        aeqit(take(TINY_LIMIT, P.positiveLongs()),
                "[4564211482599936457, 692878708722511346, 5062050059917476543, 7204392953641251301," +
                " 8156189338582548643, 1259309150092131537, 2379181980768330261, 2429155385556917621," +
                " 441826621316521237, 7145447556635229350, 404281420475794401, 5423599860460493276," +
                " 5963419290014921022, 7622708188730325951, 7874913887470575742, 2249014872100118826," +
                " 8454731288392606713, 347198304573602423, 8621628285435365246, 7096123436740836909]");
    }

    @Test
    public void testNegativeBytes() {
        aeqit(take(TINY_LIMIT, P.negativeBytes()),
                "[-42, -74, -4, -115, -54, -64, -57, -102, -126, -36, -128, -82, -104, -22, -116, -118, -121, -22," +
                " -50, -39]");
    }

    @Test
    public void testNegativeShorts() {
        aeqit(take(TINY_LIMIT, P.negativeShorts()),
                "[-22058, -20938, -6532, -11763, -950, -17088, -9529, -12774, -6910, -164, -30464, -31954, -3432," +
                " -25110, -6132, -23926, -12025, -23830, -15026, -31911]");
    }

    @Test
    public void testNegativeIntegers() {
        aeqit(take(TINY_LIMIT, P.negativeIntegers()),
                "[-1062688298, -1143001546, -161323396, -970337779, -1178600374, -681591488, -1677403449," +
                " -136131046, -1899010814, -1997176996, -293205760, -2040790226, -553946472, -1941234198," +
                " -565581812, -1951980918, -102870777, -534895894, -1663679154, -1315765415]");
    }

    @Test
    public void testNegativeLongs() {
        aeqit(take(TINY_LIMIT, P.negativeLongs()),
                "[-4564211482599936458, -692878708722511347, -5062050059917476544, -7204392953641251302," +
                " -8156189338582548644, -1259309150092131538, -2379181980768330262, -2429155385556917622," +
                " -441826621316521238, -7145447556635229351, -404281420475794402, -5423599860460493277," +
                " -5963419290014921023, -7622708188730325952, -7874913887470575743, -2249014872100118827," +
                " -8454731288392606714, -347198304573602424, -8621628285435365247, -7096123436740836910]");
    }

    @Test
    public void testNaturalBytes() {
        aeqit(take(TINY_LIMIT, P.naturalBytes()),
                "[41, 73, 3, 114, 53, 63, 56, 101, 125, 35, 127, 81, 103, 21, 115, 117, 120, 21, 49, 38]");
    }

    @Test
    public void testNaturalShorts() {
        aeqit(take(TINY_LIMIT, P.naturalShorts()),
                "[22057, 20937, 6531, 11762, 949, 17087, 9528, 12773, 6909, 163, 30463, 31953, 3431, 25109, 6131," +
                " 23925, 12024, 23829, 15025, 31910]");
    }

    @Test
    public void testNaturalIntegers() {
        aeqit(take(TINY_LIMIT, P.naturalIntegers()),
                "[1062688297, 1143001545, 161323395, 970337778, 1178600373, 681591487, 1677403448, 136131045," +
                " 1899010813, 1997176995, 293205759, 2040790225, 553946471, 1941234197, 565581811, 1951980917," +
                " 102870776, 534895893, 1663679153, 1315765414]");
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
    public void testNonzeroBytes() {
        aeqit(take(TINY_LIMIT, P.nonzeroBytes()),
                "[41, -55, -125, -14, -75, -65, 56, -27, -3, -93, -1, -47, 103, 21, -13, 117, -8, 21, -79, -90]");
    }

    @Test
    public void testNonzeroShorts() {
        aeqit(take(TINY_LIMIT, P.nonzeroShorts()),
                "[22057, -11831, -26237, 11762, 949, 17087, 9528, 12773, -25859, -32605, -2305, -815, -29337, -7659," +
                " 6131, -8843, -20744, -8939, -17743, -858]");
    }

    @Test
    public void testNonzeroIntegers() {
        aeqit(take(TINY_LIMIT, P.nonzeroIntegers()),
                "[-1084795351, 1143001545, -1986160253, -1177145870, -968883275, -1465892161, -470080200," +
                " -2011352603, -248472835, 1997176995, 293205759, -106693423, -1593537177, -206249451, 565581811," +
                " -195502731, 102870776, -1612587755, -483804495, -831718234]");
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
    public void testBytes() {
        aeqit(take(TINY_LIMIT, P.bytes()),
                "[41, -55, -125, -14, -75, -65, 56, -27, -3, -93, -1, -47, 103, 21, -13, 117, -8, 21, -79, -90]");
    }

    @Test
    public void testShorts() {
        aeqit(take(TINY_LIMIT, P.shorts()),
                "[22057, -11831, -26237, 11762, 949, 17087, 9528, 12773, -25859, -32605, -2305, -815, -29337, -7659," +
                " 6131, -8843, -20744, -8939, -17743, -858]");
    }

    @Test
    public void testAsciiCharacters() {
        aeqcs(P.asciiCharacters(),
                ")I\3r5?8e}#\177Qg\25sux\u00151&OaV\\?>5?u~\34*Oy\4w?~+-Br\7)\34d\26CLERd%@c7\2\5o.\u001c2S\6z=Vz\30" +
                "}l\nNph\32Xx^$x.\23\22\3oK10)\177u;\u001c2nEZF\17If`5f\23OSS\5\3v\5s\u000b2Y\\oKo;\1|CQ7&");
    }

    @Test
    public void testCharacters() {
        aeqcs(P.characters(),
                "嘩퇉馃\u2df2ε䊿\u2538\u31e5髽肣\uf6ffﳑ赧\ue215\u17f3\udd75껸\udd15몱ﲦ䯏ϡ罖\u19dc刿ㄾ䲵箿偵恾ᬜK" +
                "㵏ꏹ缄㩷ⴿ읾纫\ufe2d㗂䝲\uf207갩힜坤琖\u2a43퉌\uea45\ue352蕤餥䉀\u2b63\uf637鸂鸅误輮\uee1c\u33b2酓캆ᅺ됽煖䇺ᤘ" +
                "\ue9fd\u2aec㖊짎\uaaf0全覚돘䱸\u28de\u2e24\uf878ሮܓ鄒\uff03띯\ue5cb\ua7b1聆尩굿\uecf5ꪻ疜\ue8b2빮빅\ue2da䟆" +
                "\ue78f㱉泦㭠瀵컦刓嗏\u3353\ue2d3\ud805ឃᳶ쪅右䦋\u2832ﭙ빜䫯噋\uf36fꌻ躁\ue87c홃祝몷ࢦ");
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
                "[13 << 1, 221 << 1, 1 << 1, 3, 1 << 1, -1 >> 2, 3, 1 << 1, -1, -1, -3, -1, 1 << 1, -5 >> 3, 3 >> 2," +
                " 1 << 5, 3, -11 << 2, -3 << 2, -5]",
                "{1=125055, -1=124834, -3=62625, 3=62594, -1 << 1=31355, 1 << 1=31246, 1 >> 1=31181, -1 >> 1=31122," +
                " 7=15865, 3 << 1=15820}",
                -1658.0414945915677,
                1.999841999979959,
                0.9992589999977214
        );
        nonzeroBinaryFractions_helper(
                5,
                3,
                "[21 << 8, 76575 >> 5, -5, -1 >> 1, 3 << 6, 53 << 13, -217 >> 4, -7 << 11, 5785, -125, -13 >> 4, -3," +
                " -4701, -47, 7 >> 2, -3 >> 5, -1 >> 1, 5 << 1, -1 << 4, 63 << 2]",
                "{-1=25047, 1=24989, 3=20221, -3=19993, 1 >> 1=9429, -1 << 1=9380, -1 >> 1=9376, 1 << 1=9295," +
                " 7=8172, -5=7909}",
                5.037488944944938E14,
                5.002909000008661,
                3.0022489999914326
        );
        nonzeroBinaryFractions_helper(
                32,
                8,
                "[-9899036265412339 >> 7, -91244989 >> 28, -97061240401 >> 5, -100875513 >> 13, 46647 >> 5," +
                " 461 >> 1, 7781 >> 10, 2325 >> 2, -3945 << 3, -7, -3948241053762011322821917325385 << 9, 1 >> 6," +
                " -1191 << 3, 36444611421 >> 19, -4114415523 >> 8, -1074751205627367114659 >> 1, -7412493 << 29," +
                " 21 << 31, -161, -15 >> 12]",
                "{-1=1802, 1=1758, 3=1734, -3=1700, -5=839, 7=823, -7=806, 1 >> 1=806, 5=782, 1 << 1=773}",
                -4.399722772552127E126,
                31.996296000023637,
                7.998090000016753
        );
        nonzeroBinaryFractions_helper(
                100,
                10,
                "[-94790976865653102300816908025048767680216169 >> 15, 2431876897053 >> 6, 40193509 >> 12, 789 >> 3," +
                " -405178391452945205743838742601902485019373 << 1, 142361763 >> 23," +
                " -27934853437353795793120507739 >> 2, -596749 << 40, 8680751515129 << 4, 1225940409912787617 >> 5," +
                " 8445 >> 2, -6335973788661304888678252029688605863669 >> 9, 35807 >> 30," +
                " -4281825112654693651459071466596876043334654508762035987794871586861109396215 << 12," +
                " -47329496556100744717887168633315 << 21, -3404275986519 >> 27, 206401112759737881 >> 21," +
                " -490441 << 4, -291749695805053336700446163957989641185941795573 >> 3," +
                " -7565074697576503086418501144243243906404199739840721515127265994169792376514579092749220045 >> 34]",
                "{3=491, -1=453, -3=443, 1=441, -5=245, 7=240, 5=220, -3 << 2=207, -1 >> 1=205, -7=204}",
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
    public void testPositiveFloats() {
        aeqit(take(TINY_LIMIT, P.positiveFloats()),
                "[643.2779, 2.8091642E33, 1.9718748E-28, 1.2339139E-18, 3.040928E-35, 1.4698017E-35, 1.68898314E15," +
                " 2.400072E28, 3.8939846E27, 1.351875E-17, 38.282387, 2.7031316E32, 4.925231E-36, 2.0586195E-32," +
                " 1.859597E-4, 4.636554E-23, 6.5494645E-22, 8.8230604E-7, 1.841686E31, 6.544164E-24]");
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
    public void testNonzeroFloats() {
        aeqit(take(TINY_LIMIT, P.nonzeroFloats()),
                "[-0.84115845, 643.2779, -3.7940737E-33, -4.0851493E-4, -12288.927, -1.7792515E-14, -9.2656293E21," +
                " -4.730412E-34, -8.745077E29, 2.8091642E33, 1.9718748E-28, -1.0643401E35, -4.4910484E-19," +
                " -2.8662077E31, 1.2339139E-18, -6.8727283E31, 3.040928E-35, -9.5656956E-20, -3.1309523E21," +
                " -9.9399514E8]");
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
    public void testPositiveDoubles() {
        aeqit(take(TINY_LIMIT, P.positiveDoubles()),
                "[1.7536743524958916E-224, 2.764572212578795E-146, 3.896743264763858E-279, 1.217842565654434E-281," +
                " 2.792496130931957E218, 1.5945589508926632E257, 1.8384296161192212E-285, 1.220775439432479E-181," +
                " 1.774583871298936E-172, 2.409016063284675E-51, 8.548832445406645E247, 3.828636429008211E278," +
                " 6.640742249139187E161, 0.023971318720341354, 1.1041744221277698E-301, 1.5042403999912119E200," +
                " 2.0828213847164863E289, 7.70184820812619E-204, 2.1509695971995357E227, 7.617347059029767E-140]");
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
    public void testPositiveFloatsUniform() {
        aeqit(take(TINY_LIMIT, P.positiveFloatsUniform()),
                "[9.929094E37, 3.3988664E38, 1.6264806E38, 2.9887745E38, 2.3484774E37, 2.0084857E38, 3.076517E38," +
                " 1.293602E38, 2.6551666E38, 3.3685456E38, 9.823196E37, 3.2878975E37, 1.6963695E38, 6.2983905E37," +
                " 2.767365E38, 8.4991095E37, 3.9025083E37, 1.1304424E38, 1.9060855E37, 1.4810831E38]");
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
    public void testNonzeroFloatsUniform() {
        aeqit(take(TINY_LIMIT, P.nonzeroFloatsUniform()),
                "[9.929094E37, 1.9126927E38, 2.3923068E38, 2.7261729E38, 3.0669539E38, 2.5483572E38, -2.3865865E38," +
                " 2.584016E38, -1.8050742E38, 1.6911248E38, 3.2878975E37, -2.938879E38, -2.2382738E38, 2.366022E38," +
                " 2.0822496E38, 2.130368E37, -2.2906584E38, 1.7886733E38, 3.0884502E38, -7.3493103E37]");
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
    public void testPositiveDoublesUniform() {
        aeqit(take(TINY_LIMIT, P.positiveDoublesUniform()),
                "[6.010031716528839E307, 1.0138295960478667E308, 5.899671652551286E307, 1.126139741743928E307," +
                " 7.792941514562586E307, 7.116301123722935E307, 1.6486699798227538E308, 2.8116085611644773E307," +
                " 1.3981609323339555E308, 1.1533582639738811E308, 8.382602851978314E307, 1.2599937155037416E308," +
                " 1.3884097958031977E308, 1.2875997268467397E308, 5.684802572807711E307, 8.174351118522927E307," +
                " 2.5109586043466547E307, 1.3589111426555418E308, 1.093640071507309E308, 1.575071884935976E308]");
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
    public void testNonzeroDoublesUniform() {
        aeqit(take(TINY_LIMIT, P.nonzeroDoublesUniform()),
                "[-6.010031716528839E307, -4.38991984513168E306, 6.045170159795733E307, -1.2213236277546202E308," +
                " 1.093132363674069E308, -2.8350010073869094E307, 5.794375093677068E307, -6.211178609893169E307," +
                " 1.3155340847210589E308, -6.434171608796906E307, -9.114494942217177E307, 9.914564182242407E307," +
                " -8.523326292029085E306, 1.7505475135241652E308, -2.148267505726024E306, -8.64944544675027E307," +
                " -1.0680271527860177E308, 6.700517384784714E307, 1.7054593739868952E308, -1.318586604367549E308]");
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
                "[0.005, 7E-7, -1, 0.001, 0.7, 1, 1, 2, 5, 1, -1, -1, -1, -1, 1, 25, 0.01, 1, -1, -0.000009]",
                "{1=125141, -1=124288, 0.1=62644, -0.1=62522, -3=31390, -2=31337, -0.01=31293, 3=31288, 0.01=31171," +
                " 2=31110}",
                2.6687974633912868,
                2.0000579999799557,
                1.0015309999976933
        );
        nonzeroCanonicalBigDecimals_helper(
                5,
                3,
                "[0.000013, 2.5387, -0.0029, -0.01, 2, -0.00117, -3.44E-10, -0.001, -1, -0.0000018073, -0.0028," +
                " -1E-9, -0.014, -0.0605, -1E-11, 2, 0.4, 0.0002, 0.1, -0.01]",
                "{-1=24984, 1=24637, 0.1=18865, -0.1=18659, -0.01=14149, 0.01=14107, 0.001=10462, -0.001=10401," +
                " 2=10091, -2=9987}",
                -7.081594725123083E13,
                5.008832000008888,
                2.9996669999914487
        );
        nonzeroCanonicalBigDecimals_helper(
                32,
                8,
                "[4.04936997654063E-17, -3.03219670608E-15, -0.00003, -5.254848612E-7, -0.0969839, 2416.5," +
                " 0.000012564, -121.36, 0.18, -16911639184143047868995522284006, -5E-8, 0.05286, 2426030416.28," +
                " 1.4851833763E-8, -3E-7, -0.02487512677886, 2352046.8, 6.9E-27, 8.01E-28, -39]",
                "{1=1812, -1=1753, -0.1=1526, 0.1=1456, 0.01=1382, -0.01=1349, -0.001=1188, 0.001=1171," +
                " -0.0001=1127, 0.0001=1114}",
                2.1875960862631944E131,
                32.006803000023154,
                7.998565000016982
        );
        nonzeroCanonicalBigDecimals_helper(
                100,
                10,
                "[0.000572989721722831798340552401503927, 0.004630900152605, -1871.00459, 1.391E-8," +
                " 110207667890702717891682993862216378208126.1, -410797219, -604019.34935913508416666426308," +
                " 321818.8, 3.0670984070649E-26, -699054793294702.2497, -0.64652," +
                " -3355856314233638196574822062423006278014.9, 0.000232414," +
                " 79003279013208247584456647481183731637493415295.63303614027921087108394697463," +
                " -250153592592617448957.359681493474, 7.802322497623E-9, -6.38746676987305497E-9, 1.801161E-14," +
                " 882089440883056826028133921073055650976506246.571," +
                " 157132186029144474314922838978807565506100733145044665176718277915873175755679259174828095.48]",
                "{-1=450, 1=427, 0.1=421, -0.1=409, -0.01=400, 0.01=357, -0.001=355, 0.001=344, 0.0001=297," +
                " -0.0001=296}",
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
                "0.0",
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
                "0.0",
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
                "1",
                "[1.0050000000, 1.04, 1.0010, 1.060, 2, 1.01, 1, 6.00, 1, 1.000, 1, 1.0, 1.100, 1, 2.00, 2.0, 1.0," +
                " 1, 1.0, 1.02]",
                "{1=143066, 1.0=71565, 1.1=53796, 2=47223, 1.00=35476, 1.01=26881, 1.10=26765, 2.0=23894," +
                " 1.000=17826, 3=15821}",
                1385.5639559372435,
                7.622173999998321,
                1.8598829999882969
        );
        rangeUp_BigDecimal_helper(
                5,
                3,
                "1",
                "[1.0000130000, 1.0090030000, 1, 26.00, 1.00000000000, 3.0, 3.0000, 1.1, 2.000, 1.00140," +
                " 1.000800000000, 5.56500000000000000, 3, 1.9, 1.000000000000200000000000000000, 1.000100000," +
                " 1.30000, 13.6000, 1.00060, 1.0100]",
                "{1=20050, 1.0=14979, 1.00=11085, 1.1=8894, 1.000=8460, 2=8311, 1.10=6736, 1.01=6731, 2.0=6216," +
                " 1.0000=6179}",
                1.3255156090700816E14,
                21.815920999973557,
                5.873468000007206
        );
        rangeUp_BigDecimal_helper(
                32,
                8,
                "1",
                "[1.000000000000000040493699765406300000000000000000000000000, 2417.5000000000, 1.18," +
                " 180547618411867370589.2748088000000000, 76449.5400, 1.00000148518337630000000," +
                " 2352047.80000000000000000000000000000, 22222723879.730960000, 2.60161406000000000000000000000," +
                " 1.0000000416666200000, 109591.1908685744217951578289, 1.0000050000000000000000, 784415067.00," +
                " 284002.000000000000000000, 1.0000000000000170605244111048790, 1.00075000," +
                " 1.000000000000000002900000000, 1.00142577220000000000, 9.5730000000000000000000000000000000000," +
                " 13908417840098258751.12656]",
                "{1=759, 1.0=701, 1.00=556, 1.000=506, 1.0000=460, 1.00000=403, 1.000000=364, 2=362, 2.0=331," +
                " 1.1=329}",
                4.18021886093211E113,
                71.05169100002996,
                15.959081999996279
        );
        rangeUp_BigDecimal_helper(
                2,
                1,
                "-1",
                "[-0.9950000000, -0.96, -0.9990, -0.940, 0, -0.9, -1, 4.00, -1, -1.000, -1, -1.0, -0.900, -1, 0E+2," +
                " -0.90, -1.0, -1, -1.0, -0.98]",
                "{-1=143309, -1.0=71581, -0.9=53722, 0=47163, -1.00=35534, -0.99=26886, -0.90=26730, -1.000=17908," +
                " 1=15828, 2=15721}",
                3211.5313539618583,
                6.861828000004236,
                1.8599939999883222
        );
        rangeUp_BigDecimal_helper(
                5,
                3,
                "-1",
                "[-0.9999870000, -0.9909970000, -1, 24.00, -1.00000000000, 1.0, 1.0000, -0.9, 0.000, -0.9860," +
                " -0.999200000000, 3.56500000000000000, 1, -0.1, -0.999999999999800000000000000000, -0.999900000," +
                " -0.70000, 11.6000, -0.99940, -0.9900]",
                "{-1=20079, -1.0=14983, -1.00=11090, -0.9=8875, -1.000=8461, 0=8331, -0.90=6748, -0.99=6736," +
                " -1.0000=6196, -0.990=5084}",
                1.3255142240731088E14,
                21.0937569999323,
                5.872534000007241
        );
        rangeUp_BigDecimal_helper(
                32,
                8,
                "-1",
                "[-0.999999999999999959506300234593700000000000000000000000000, 2415.5000000000, -0.82," +
                " 180547618411867370587.2748088000000000, 76447.5400, -0.99999851481662370000000," +
                " 2352045.80000000000000000000000000000, 22222723877.730960000, 0.60161406000000000000000000000," +
                " -0.9999999583333800000, 109589.1908685744217951578289, -0.9999950000000000000000, 784415065.00," +
                " 284000.000000000000000, -0.9999999999999829394755888951210, -0.99925000," +
                " -0.999999999999999997100000000, -0.99857422780000000000, 7.5730000000000000000000000000000000000," +
                " 13908417840098258749.12656]",
                "{-1=760, -1.0=701, -1.00=556, -1.000=507, -1.0000=459, -1.00000=404, -1.000000=363, 0=362," +
                " -0.9=329, -1.0000000=321}",
                4.18021886093211E113,
                70.8140910000301,
                15.95903799999594
        );
        rangeUp_BigDecimal_helper(
                2,
                1,
                "1.0",
                "[1.0050000000, 1.04, 1.0010, 1.060, 2, 1.01, 1, 6.00, 1, 1.000, 1, 1.0, 1.100, 1, 2.00, 2.0, 1.0," +
                " 1, 1.0, 1.02]",
                "{1=143066, 1.0=71565, 1.1=53796, 2=47223, 1.00=35476, 1.01=26881, 1.10=26765, 2.0=23894," +
                " 1.000=17826, 3=15821}",
                1385.5639559372435,
                7.622173999998321,
                1.8598829999882969
        );
        rangeUp_BigDecimal_helper(
                5,
                3,
                "1.0",
                "[1.0000130000, 1.0090030000, 1, 26.00, 1.00000000000, 3.0, 3.0000, 1.1, 2.000, 1.00140," +
                " 1.000800000000, 5.56500000000000000, 3, 1.9, 1.000000000000200000000000000000, 1.000100000," +
                " 1.30000, 13.6000, 1.00060, 1.0100]",
                "{1=20050, 1.0=14979, 1.00=11085, 1.1=8894, 1.000=8460, 2=8311, 1.10=6736, 1.01=6731, 2.0=6216," +
                " 1.0000=6179}",
                1.3255156090700816E14,
                21.815920999973557,
                5.873468000007206
        );
        rangeUp_BigDecimal_helper(
                32,
                8,
                "1.0",
                "[1.000000000000000040493699765406300000000000000000000000000, 2417.5000000000, 1.18," +
                " 180547618411867370589.2748088000000000, 76449.5400, 1.00000148518337630000000," +
                " 2352047.80000000000000000000000000000, 22222723879.730960000, 2.60161406000000000000000000000," +
                " 1.0000000416666200000, 109591.1908685744217951578289, 1.0000050000000000000000, 784415067.00," +
                " 284002.000000000000000000, 1.0000000000000170605244111048790, 1.00075000," +
                " 1.000000000000000002900000000, 1.00142577220000000000, 9.5730000000000000000000000000000000000," +
                " 13908417840098258751.12656]",
                "{1=759, 1.0=701, 1.00=556, 1.000=506, 1.0000=460, 1.00000=403, 1.000000=364, 2=362, 2.0=331," +
                " 1.1=329}",
                4.18021886093211E113,
                71.05169100002996,
                15.959081999996279
        );
        rangeUp_BigDecimal_helper(
                2,
                1,
                "-1.0",
                "[-0.9950000000, -0.96, -0.9990, -0.940, 0, -0.9, -1, 4.00, -1, -1.000, -1, -1.0, -0.900, -1, 0E+2," +
                " -0.90, -1.0, -1, -1.0, -0.98]",
                "{-1=143309, -1.0=71581, -0.9=53722, 0=47163, -1.00=35534, -0.99=26886, -0.90=26730, -1.000=17908," +
                " 1=15828, 2=15721}",
                3211.5313539618583,
                6.861828000004236,
                1.8599939999883222
        );
        rangeUp_BigDecimal_helper(
                5,
                3,
                "-1.0",
                "[-0.9999870000, -0.9909970000, -1, 24.00, -1.00000000000, 1.0, 1.0000, -0.9, 0.000, -0.9860," +
                " -0.999200000000, 3.56500000000000000, 1, -0.1, -0.999999999999800000000000000000, -0.999900000," +
                " -0.70000, 11.6000, -0.99940, -0.9900]",
                "{-1=20079, -1.0=14983, -1.00=11090, -0.9=8875, -1.000=8461, 0=8331, -0.90=6748, -0.99=6736," +
                " -1.0000=6196, -0.990=5084}",
                1.3255142240731088E14,
                21.0937569999323,
                5.872534000007241
        );
        rangeUp_BigDecimal_helper(
                32,
                8,
                "-1.0",
                "[-0.999999999999999959506300234593700000000000000000000000000, 2415.5000000000, -0.82," +
                " 180547618411867370587.2748088000000000, 76447.5400, -0.99999851481662370000000," +
                " 2352045.80000000000000000000000000000, 22222723877.730960000, 0.60161406000000000000000000000," +
                " -0.9999999583333800000, 109589.1908685744217951578289, -0.9999950000000000000000, 784415065.00," +
                " 284000.000000000000000, -0.9999999999999829394755888951210, -0.99925000," +
                " -0.999999999999999997100000000, -0.99857422780000000000, 7.5730000000000000000000000000000000000," +
                " 13908417840098258749.12656]",
                "{-1=760, -1.0=701, -1.00=556, -1.000=507, -1.0000=459, -1.00000=404, -1.000000=363, 0=362," +
                " -0.9=329, -1.0000000=321}",
                4.18021886093211E113,
                70.8140910000301,
                15.95903799999594
        );
        rangeUp_BigDecimal_helper(
                2,
                1,
                "9",
                "[9.0050000000, 9.04, 9.0010, 9.060, 1E+1, 9.01, 9, 14.00, 9, 9.000, 9, 9.0, 9.100, 9, 10.0, 10," +
                " 9.0, 9, 9.0, 9.02]",
                "{9=143066, 9.0=71565, 9.1=53796, 1E+1=47223, 9.00=35476, 9.01=26881, 9.10=26765, 10=23894," +
                " 9.000=17826, 11=15821}",
                1393.5639559349913,
                9.880917999979038,
                1.8592359999871946
        );
        rangeUp_BigDecimal_helper(
                5,
                3,
                "9",
                "[9.0000130000, 9.0090030000, 9, 34.00, 9.00000000000, 11.0, 11.0000, 9.1, 10.00, 9.00140," +
                " 9.000800000000, 13.56500000000000000, 11, 9.9, 9.000000000000200000000000000000, 9.000100000," +
                " 9.30000, 21.6000, 9.00060, 9.0100]",
                "{9=20050, 9.0=14979, 9.00=11085, 9.1=8894, 9.000=8460, 1E+1=8311, 9.10=6736, 9.01=6731, 10=6216," +
                " 9.0000=6179}",
                1.3255156090701044E14,
                24.11267099993038,
                5.856708000007621
        );
        rangeUp_BigDecimal_helper(
                32,
                8,
                "9",
                "[9.000000000000000040493699765406300000000000000000000000000, 2425.5000000000, 9.18," +
                " 180547618411867370597.2748088000000000, 76457.5400, 9.00000148518337630000000," +
                " 2352055.80000000000000000000000000000, 22222723887.730960000, 10.60161406000000000000000000000," +
                " 9.0000000416666200000, 109599.1908685744217951578289, 9.0000050000000000000000, 784415075.00," +
                " 284010.00000000000000000, 9.0000000000000170605244111048790, 9.00075000," +
                " 9.000000000000000002900000000, 9.00142577220000000000, 17.5730000000000000000000000000000000000," +
                " 13908417840098258759.12656]",
                "{9=759, 9.0=701, 9.00=556, 9.000=506, 9.0000=460, 9.00000=403, 9.000000=364, 1E+1=362, 10=331," +
                " 9.1=329}",
                4.18021886093211E113,
                72.54750000002102,
                15.956679999995899
        );
        rangeUp_BigDecimal_helper(
                2,
                1,
                "-9",
                "[-8.9950000000, -8.96, -8.9990, -8.940, -8, -8.99, -9, -4.00, -9, -9.000, -9, -9.0, -8.900, -9," +
                " -8.00, -8.0, -9.0, -9, -9.0, -8.98]",
                "{-9=143083, -9.0=71532, -8.9=53760, -8=47248, -9.00=35506, -8.99=26881, -8.90=26747, -8.0=23909," +
                " -9.000=17821, -7=15816}",
                1378.9562815626282,
                9.876909999983395,
                1.8597449999883424
        );
        rangeUp_BigDecimal_helper(
                5,
                3,
                "-9",
                "[-8.9999870000, -8.9909970000, -9, 16.00, -9.00000000000, -7.0, -7.0000, -8.9, -8.000, -8.99860," +
                " -8.999200000000, -4.43500000000000000, -7, -8.1, -8.999999999999800000000000000000, -8.999900000," +
                " -8.70000, 3.6000, -8.99940, -8.9900]",
                "{-9=20051, -9.0=14976, -9.00=11083, -8.9=8895, -9.000=8470, -8=8308, -8.99=6744, -8.90=6738," +
                " -8.0=6218, -9.0000=6179}",
                1.3255156101085684E14,
                23.87646199989173,
                5.874416000007179
        );
        rangeUp_BigDecimal_helper(
                32,
                8,
                "-9",
                "[-8.999999999999999959506300234593700000000000000000000000000, 2407.5000000000, -8.82," +
                " 180547618411867370579.2748088000000000, 76439.5400, -8.99999851481662370000000," +
                " 2352037.80000000000000000000000000000, 22222723869.730960000, -7.39838594000000000000000000000," +
                " -8.9999999583333800000, 109581.1908685744217951578289, -8.9999950000000000000000, 784415057.00," +
                " 283992.000000000000000000, -8.9999999999999829394755888951210, -8.99925000," +
                " -8.999999999999999997100000000, -8.99857422780000000000, -0.4270000000000000000000000000000000000," +
                " 13908417840098258741.12656]",
                "{-9=759, -9.0=701, -9.00=556, -9.000=506, -9.0000=460, -9.00000=403, -9.000000=364, -8=362," +
                " -8.0=331, -8.9=329}",
                4.18021886093211E113,
                72.39022300002145,
                15.95933799999627
        );
        rangeUp_BigDecimal_helper(
                2,
                1,
                "10",
                "[10.0050000000, 10.04, 10.0010, 10.060, 11, 10.01, 1E+1, 15.00, 1E+1, 10.00, 1E+1, 10, 10.100," +
                " 1E+1, 11.00, 11.0, 10, 1E+1, 10, 10.02]",
                "{1E+1=143066, 10=71565, 10.1=53796, 11=47223, 10.0=35476, 10.01=26881, 10.10=26765, 11.0=23894," +
                " 10.00=17826, 12=15821}",
                1394.5639559317256,
                9.344789999982071,
                1.8601429999848516
        );
        rangeUp_BigDecimal_helper(
                5,
                3,
                "10",
                "[10.0000130000, 10.0090030000, 1E+1, 35.00, 10.0000000000, 12.0, 12.0000, 10.1, 11.000, 10.00140," +
                " 10.000800000000, 14.56500000000000000, 12, 10.9, 10.000000000000200000000000000000, 10.000100000," +
                " 10.30000, 22.6000, 10.00060, 10.0100]",
                "{1E+1=20050, 10=14979, 10.0=11085, 10.1=8894, 10.00=8460, 11=8311, 10.10=6736, 10.01=6731," +
                " 11.0=6216, 10.000=6179}",
                1.3255156090701067E14,
                24.027310999942575,
                5.833906000008166
        );
        rangeUp_BigDecimal_helper(
                32,
                8,
                "10",
                "[10.000000000000000040493699765406300000000000000000000000000, 2426.5000000000, 10.18," +
                " 180547618411867370598.2748088000000000, 76458.5400, 10.00000148518337630000000," +
                " 2352056.80000000000000000000000000000, 22222723888.730960000, 11.60161406000000000000000000000," +
                " 10.0000000416666200000, 109600.1908685744217951578289, 10.0000050000000000000000, 784415076.00," +
                " 284011.000000000000000000, 10.0000000000000170605244111048790, 10.00075000," +
                " 10.000000000000000002900000000, 10.00142577220000000000, 18.5730000000000000000000000000000000000," +
                " 13908417840098258760.12656]",
                "{1E+1=759, 10=701, 10.0=556, 10.00=506, 10.000=460, 10.0000=403, 10.00000=364, 11=362, 11.0=331," +
                " 10.1=329}",
                4.18021886093211E113,
                72.6158040000279,
                15.954095999995749
        );
        rangeUp_BigDecimal_helper(
                2,
                1,
                "-10",
                "[-9.9950000000, -9.96, -9.9990, -9.940, -9, -9.99, -1E+1, -5.00, -1E+1, -10.00, -1E+1, -10, -9.900," +
                " -1E+1, -9.00, -9.0, -10, -1E+1, -10, -9.98]",
                "{-1E+1=143116, -10=71551, -9.9=53806, -9=47211, -10.0=35447, -9.99=26881, -9.90=26772, -9.0=23886," +
                " -10.00=17821, -8=15836}",
                1371.5161272222995,
                9.041028999984173,
                1.8600609999848983
        );
        rangeUp_BigDecimal_helper(
                5,
                3,
                "-10",
                "[-9.9999870000, -9.9909970000, -1E+1, 15.00, -10.0000000000, -8.0, -8.0000, -9.9, -9.000, -9.99860," +
                " -9.999200000000, -5.43500000000000000, -8, -9.1, -9.999999999999800000000000000000, -9.999900000," +
                " -9.70000, 2.6000, -9.99940, -9.9900]",
                "{-1E+1=20056, -10=14969, -10.0=11080, -9.9=8899, -10.00=8454, -9=8307, -9.90=6733, -9.99=6731," +
                " -9.0=6224, -10.000=6186}",
                1.3255156078209173E14,
                23.679469999889513,
                5.835174000008125
        );
        rangeUp_BigDecimal_helper(
                32,
                8,
                "-10",
                "[-9.999999999999999959506300234593700000000000000000000000000, 2406.5000000000, -9.82," +
                " 180547618411867370578.2748088000000000, 76438.5400, -9.99999851481662370000000," +
                " 2352036.80000000000000000000000000000, 22222723868.730960000, -8.39838594000000000000000000000," +
                " -9.9999999583333800000, 109580.1908685744217951578289, -9.9999950000000000000000, 784415056.00," +
                " 283991.000000000000000000, -9.9999999999999829394755888951210, -9.99925000," +
                " -9.999999999999999997100000000, -9.99857422780000000000, -1.4270000000000000000000000000000000000," +
                " 13908417840098258740.12656]",
                "{-1E+1=758, -10=701, -10.0=556, -10.00=506, -10.000=460, -10.0000=403, -10.00000=363, -9=362," +
                " -9.0=331, -9.9=329}",
                4.18021886093211E113,
                72.45249300002985,
                15.954258999995814
        );
        rangeUp_BigDecimal_helper(
                2,
                1,
                "101",
                "[101.0050000000, 101.04, 101.0010, 101.060, 102, 101.01, 101, 106.00, 101, 101.000, 101, 101.0," +
                " 101.100, 101, 102.00, 102.0, 101.0, 101, 101.0, 101.02]",
                "{101=143066, 101.0=71565, 101.1=53796, 102=47223, 101.00=35476, 101.01=26881, 101.10=26765," +
                " 102.0=23894, 101.000=17826, 103=15821}",
                1485.5639559341907,
                13.261434999963267,
                1.8598879999882962
        );
        rangeUp_BigDecimal_helper(
                5,
                3,
                "101",
                "[101.0000130000, 101.0090030000, 101, 126.00, 101.00000000000, 103.0, 103.0000, 101.1, 102.000," +
                " 101.00140, 101.000800000000, 105.56500000000000000, 103, 101.9," +
                " 101.000000000000200000000000000000, 101.000100000, 101.30000, 113.6000, 101.00060, 101.0100]",
                "{101=20050, 101.0=14979, 101.00=11085, 101.1=8894, 101.000=8460, 102=8311, 101.10=6736," +
                " 101.01=6731, 102.0=6216, 101.0000=6179}",
                1.3255156090703184E14,
                27.119858999869805,
                5.873467000007205
        );
        rangeUp_BigDecimal_helper(
                32,
                8,
                "101",
                "[101.000000000000000040493699765406300000000000000000000000000, 2517.5000000000, 101.18," +
                " 180547618411867370689.2748088000000000, 76549.5400, 101.00000148518337630000000," +
                " 2352147.80000000000000000000000000000, 22222723979.730960000, 102.60161406000000000000000000000," +
                " 101.0000000416666200000, 109691.1908685744217951578289, 101.0000050000000000000000, 784415167.00," +
                " 284102.000000000000000000, 101.0000000000000170605244111048790, 101.00075000," +
                " 101.000000000000000002900000000, 101.00142577220000000000," +
                " 109.5730000000000000000000000000000000000, 13908417840098258851.12656]",
                "{101=759, 101.0=701, 101.00=556, 101.000=506, 101.0000=460, 101.00000=403, 101.000000=364, 102=362," +
                " 102.0=331, 101.1=329}",
                4.18021886093211E113,
                74.39138300003185,
                15.959073999996285
        );
        rangeUp_BigDecimal_helper(
                2,
                1,
                "-101",
                "[-100.9950000000, -100.96, -100.9990, -100.940, -1E+2, -100.99, -101, -96.00, -101, -101.000, -101," +
                " -101.0, -100.900, -101, -100, -1.0E+2, -101.0, -101, -101.0, -100.98]",
                "{-101=143062, -101.0=71569, -100.9=53802, -1E+2=47221, -101.00=35483, -100.99=26881, -100.90=26764," +
                " -1.0E+2=23895, -101.000=17823, -99=15820}",
                1283.5659129612752,
                12.548057999941495,
                1.9062969999885824
        );
        rangeUp_BigDecimal_helper(
                5,
                3,
                "-101",
                "[-100.9999870000, -100.9909970000, -101, -76.00, -101.00000000000, -99.0, -99.0000, -100.9, -100.0," +
                " -100.99860, -100.999200000000, -96.43500000000000000, -99, -100.1," +
                " -100.999999999999800000000000000000, -100.999900000, -100.70000, -88.4000, -100.99940, -100.9900]",
                "{-101=20049, -101.0=14983, -101.00=11083, -100.9=8895, -101.000=8462, -1E+2=8310, -100.90=6737," +
                " -100.99=6728, -1.0E+2=6215, -101.0000=6179}",
                1.3255156090699792E14,
                26.704327999833442,
                5.8527580000076265
        );
        rangeUp_BigDecimal_helper(
                32,
                8,
                "-101",
                "[-100.999999999999999959506300234593700000000000000000000000000, 2315.5000000000, -100.82," +
                " 180547618411867370487.2748088000000000, 76347.5400, -100.99999851481662370000000," +
                " 2351945.80000000000000000000000000000, 22222723777.730960000, -99.39838594000000000000000000000," +
                " -100.9999999583333800000, 109489.1908685744217951578289, -100.9999950000000000000000," +
                " 784414965.00, 283900.0000000000000000, -100.9999999999999829394755888951210, -100.99925000," +
                " -100.999999999999999997100000000, -100.99857422780000000000," +
                " -92.4270000000000000000000000000000000000, 13908417840098258649.12656]",
                "{-101=759, -101.0=701, -101.00=556, -101.000=506, -101.0000=460, -101.00000=403, -101.000000=364," +
                " -1E+2=362, -1.0E+2=331, -100.9=329}",
                4.18021886093211E113,
                74.22560500003182,
                15.954912999995985
        );
        rangeUp_BigDecimal_helper(
                2,
                1,
                "1234567",
                "[1234567.0050000000, 1234567.04, 1234567.0010, 1234567.060, 1234568, 1234567.01, 1234567," +
                " 1234572.00, 1234567, 1234567.000, 1234567, 1234567.0, 1234567.100, 1234567, 1234568.00, 1234568.0," +
                " 1234567.0, 1234567, 1234567.0, 1234567.02]",
                "{1234567=143066, 1234567.0=71565, 1234567.1=53796, 1234568=47223, 1234567.00=35476," +
                " 1234567.01=26881, 1234567.10=26765, 1234568.0=23894, 1234567.000=17826, 1234569=15821}",
                1235951.5639480965,
                26.786103999874832,
                1.8600099999879223
        );
        rangeUp_BigDecimal_helper(
                5,
                3,
                "1234567",
                "[1234567.0000130000, 1234567.0090030000, 1234567, 1234592.00, 1234567.00000000000, 1234569.0," +
                " 1234569.0000, 1234567.1, 1234568.000, 1234567.00140, 1234567.000800000000," +
                " 1234571.56500000000000000, 1234569, 1234567.9, 1234567.000000000000200000000000000000," +
                " 1234567.000100000, 1234567.30000, 1234579.6000, 1234567.00060, 1234567.0100]",
                "{1234567=20050, 1234567.0=14979, 1234567.00=11085, 1234567.1=8894, 1234567.000=8460, 1234568=8311," +
                " 1234567.10=6736, 1234567.01=6731, 1234568.0=6216, 1234567.0000=6179}",
                1.3255156214142816E14,
                40.21804799994806,
                5.866811000007397
        );
        rangeUp_BigDecimal_helper(
                32,
                8,
                "1234567",
                "[1234567.000000000000000040493699765406300000000000000000000000000, 1236983.5000000000, 1234567.18," +
                " 180547618411868605155.2748088000000000, 1311015.5400, 1234567.00000148518337630000000," +
                " 3586613.80000000000000000000000000000, 22223958445.730960000," +
                " 1234568.60161406000000000000000000000, 1234567.0000000416666200000," +
                " 1344157.1908685744217951578289, 1234567.0000050000000000000000, 785649633.00," +
                " 1518568.000000000000000000, 1234567.0000000000000170605244111048790, 1234567.00075000," +
                " 1234567.000000000000000002900000000, 1234567.00142577220000000000," +
                " 1234575.5730000000000000000000000000000000000, 13908417840099493317.12656]",
                "{1234567=759, 1234567.0=701, 1234567.00=556, 1234567.000=506, 1234567.0000=460, 1234567.00000=403," +
                " 1234567.000000=364, 1234568=362, 1234568.0=331, 1234567.1=329}",
                4.18021886093211E113,
                83.01889800010139,
                15.958300999996053
        );
        rangeUp_BigDecimal_helper(
                2,
                1,
                "-1234567",
                "[-1234566.9950000000, -1234566.96, -1234566.9990, -1234566.940, -1234566, -1234566.99, -1234567," +
                " -1234562.00, -1234567, -1234567.000, -1234567, -1234567.0, -1234566.900, -1234567, -1234566.00," +
                " -1234566.0, -1234567.0, -1234567, -1234567.0, -1234566.98]",
                "{-1234567=143066, -1234567.0=71565, -1234566.9=53796, -1234566=47223, -1234567.00=35476," +
                " -1234566.99=26881, -1234566.90=26765, -1234566.0=23894, -1234567.000=17826, -1234565=15821}",
                -1233182.4360437375,
                26.87187099987704,
                1.8600259999881925
        );
        rangeUp_BigDecimal_helper(
                5,
                3,
                "-1234567",
                "[-1234566.9999870000, -1234566.9909970000, -1234567, -1234542.00, -1234567.00000000000, -1234565.0," +
                " -1234565.0000, -1234566.9, -1234566.000, -1234566.99860, -1234566.999200000000," +
                " -1234562.43500000000000000, -1234565, -1234566.1, -1234566.999999999999800000000000000000," +
                " -1234566.999900000, -1234566.70000, -1234554.4000, -1234566.99940, -1234566.9900]",
                "{-1234567=20050, -1234567.0=14979, -1234567.00=11085, -1234566.9=8894, -1234567.000=8460," +
                " -1234566=8311, -1234566.90=6736, -1234566.99=6731, -1234566.0=6216, -1234567.0000=6179}",
                1.3255155967258822E14,
                40.239422999948026,
                5.871547000007296
        );
        rangeUp_BigDecimal_helper(
                32,
                8,
                "-1234567",
                "[-1234566.999999999999999959506300234593700000000000000000000000000, -1232150.5000000000," +
                " -1234566.82, 180547618411866136021.2748088000000000, -1158118.4600," +
                " -1234566.99999851481662370000000, 1117479.80000000000000000000000000000, 22221489311.730960000," +
                " -1234565.39838594000000000000000000000, -1234566.9999999583333800000," +
                " -1124976.8091314255782048421711, -1234566.9999950000000000000000, 783180499.00," +
                " -950566.000000000000000000, -1234566.9999999999999829394755888951210, -1234566.99925000," +
                " -1234566.999999999999999997100000000, -1234566.99857422780000000000," +
                " -1234558.4270000000000000000000000000000000000, 13908417840097024183.12656]",
                "{-1234567=759, -1234567.0=701, -1234567.00=556, -1234567.000=506, -1234567.0000=460," +
                " -1234567.00000=403, -1234567.000000=364, -1234566=362, -1234566.0=331, -1234566.9=329}",
                4.18021886093211E113,
                82.92891300009914,
                15.958750999996031
        );
        rangeUp_BigDecimal_helper(
                2,
                1,
                "0.09",
                "[0.0950000000, 0.13, 0.0910, 0.150, 1.09, 0.1, 0.09, 5.0900, 0.09, 0.09000, 0.09, 0.090, 0.1900," +
                " 0.09, 1.0900, 1.090, 0.090, 0.09, 0.090, 0.11]",
                "{0.09=143066, 0.090=71565, 0.19=53796, 1.09=47223, 0.0900=35476, 0.1=26881, 0.190=26765," +
                " 1.090=23894, 0.09000=17826, 2.09=15821}",
                1384.6539559255805,
                9.721092999988024,
                3.1611809999692686
        );
        rangeUp_BigDecimal_helper(
                5,
                3,
                "0.09",
                "[0.0900130000, 0.0990030000, 0.09, 25.0900, 0.0900000000000, 2.090, 2.090000, 0.19, 1.09000," +
                " 0.09140, 0.090800000000, 4.65500000000000000, 2.09, 0.99, 0.090000000000200000000000000000," +
                " 0.090100000, 0.390000, 12.69000, 0.09060, 0.100]",
                "{0.09=20050, 0.090=14979, 0.0900=11085, 0.19=8894, 0.09000=8460, 1.09=8311, 0.190=6736, 0.1=6731," +
                " 1.090=6216, 0.090000=6179}",
                1.3255156090700805E14,
                22.11084499998398,
                6.582647000006659
        );
        rangeUp_BigDecimal_helper(
                32,
                8,
                "0.09",
                "[0.090000000000000040493699765406300000000000000000000000000, 2416.59000000000, 0.27," +
                " 180547618411867370588.3648088000000000, 76448.6300, 0.09000148518337630000000," +
                " 2352046.890000000000000000000000000000, 22222723878.820960000, 1.69161406000000000000000000000," +
                " 0.0900000416666200000, 109590.2808685744217951578289, 0.0900050000000000000000, 784415066.0900," +
                " 284001.09000000000000000000, 0.0900000000000170605244111048790, 0.09075000," +
                " 0.090000000000000002900000000, 0.09142577220000000000, 8.6630000000000000000000000000000000000," +
                " 13908417840098258750.21656]",
                "{0.09=759, 0.090=701, 0.0900=556, 0.09000=506, 0.090000=460, 0.0900000=403, 0.09000000=364," +
                " 1.09=362, 1.090=331, 0.19=329}",
                4.18021886093211E113,
                70.64934000002235,
                16.281369000005466
        );
        rangeUp_BigDecimal_helper(
                2,
                1,
                "-0.09",
                "[-0.0850000000, -0.05, -0.0890, -0.030, 0.91, -0.08, -0.09, 4.9100, -0.09, -0.09000, -0.09, -0.090," +
                " 0.0100, -0.09, 0.9100, 0.910, -0.090, -0.09, -0.090, -0.07]",
                "{-0.09=143067, -0.090=71578, 0.01=53805, 0.91=47253, -0.0900=35463, -0.08=26874, 0.010=26763," +
                " 0.910=23886, -0.09000=17817, 1.91=15822}",
                1384.4640551254167,
                9.249282999984306,
                3.213727999966871
        );
        rangeUp_BigDecimal_helper(
                5,
                3,
                "-0.09",
                "[-0.0899870000, -0.0809970000, -0.09, 24.9100, -0.0900000000000, 1.910, 1.910000, 0.01, 0.91000," +
                " -0.08860, -0.089200000000, 4.47500000000000000, 1.91, 0.81, -0.089999999999800000000000000000," +
                " -0.089900000, 0.210000, 12.51000, -0.08940, -0.0800]",
                "{-0.09=20041, -0.090=14981, -0.0900=11088, 0.01=8904, -0.09000=8458, 0.91=8311, 0.010=6733," +
                " -0.08=6732, 0.910=6215, -0.090000=6179}",
                1.3255156086335206E14,
                21.758886999950676,
                6.607647000006336
        );
        rangeUp_BigDecimal_helper(
                32,
                8,
                "-0.09",
                "[-0.089999999999999959506300234593700000000000000000000000000, 2416.41000000000, 0.09," +
                " 180547618411867370588.1848088000000000, 76448.4500, -0.08999851481662370000000," +
                " 2352046.710000000000000000000000000000, 22222723878.640960000, 1.51161406000000000000000000000," +
                " -0.0899999583333800000, 109590.1008685744217951578289, -0.0899950000000000000000, 784415065.9100," +
                " 284000.91000000000000000000, -0.0899999999999829394755888951210, -0.08925000," +
                " -0.089999999999999997100000000, -0.08857422780000000000, 8.4830000000000000000000000000000000000," +
                " 13908417840098258750.03656]",
                "{-0.09=760, -0.090=701, -0.0900=556, -0.09000=506, -0.090000=460, -0.0900000=403, -0.09000000=364," +
                " 0.91=362, 0.910=331, 0.01=329}",
                4.18021886093211E113,
                70.50093900002139,
                16.283410000005688
        );
        rangeUp_BigDecimal_helper(
                2,
                1,
                "1E-12",
                "[0.0050000000010000000, 0.040000000001, 0.0010000000010, 0.0600000000010, 1.000000000001," +
                " 0.010000000001, 1E-12, 5.00000000000100, 1E-12, 1.000E-12, 1E-12, 1.0E-12, 0.10000000000100," +
                " 1E-12, 1.00000000000100, 1.0000000000010, 1.0E-12, 1E-12, 1.0E-12, 0.020000000001]",
                "{1E-12=143066, 1.0E-12=71565, 0.100000000001=53796, 1.000000000001=47223, 1.00E-12=35476," +
                " 0.010000000001=26881, 0.1000000000010=26765, 1.0000000000010=23894, 1.000E-12=17826," +
                " 2.000000000001=15821}",
                1384.5639559361018,
                30.66109999998521,
                13.00223999988918
        );
        rangeUp_BigDecimal_helper(
                5,
                3,
                "1E-12",
                "[0.0000130000010000, 0.0090030000010000, 1E-12, 25.00000000000100, 1.00000000000E-12," +
                " 2.0000000000010, 2.0000000000010000, 0.100000000001, 1.000000000001000, 0.0014000000010," +
                " 0.00080000000100000000, 4.56500000000100000000000000, 2.000000000001, 0.900000000001," +
                " 1.200000000000000000E-12, 0.00010000000100000, 0.3000000000010000, 12.600000000001000," +
                " 0.0006000000010, 0.01000000000100]",
                "{1E-12=20050, 1.0E-12=14979, 1.00E-12=11085, 0.100000000001=8894, 1.000E-12=8460," +
                " 1.000000000001=8311, 0.1000000000010=6736, 0.010000000001=6731, 1.0000000000010=6216," +
                " 1.0000E-12=6179}",
                1.3255156090700803E14,
                42.33828099999371,
                15.086760999914567
        );
        rangeUp_BigDecimal_helper(
                32,
                8,
                "1E-12",
                "[1.000040493699765406300000000000000000000000000E-12, 2416.500000000001000000000, 0.180000000001," +
                " 180547618411867370588.274808800001000000000, 76448.54000000000100, 0.00000148518437630000000," +
                " 2352046.8000000000010000000000000000000000000000, 22222723878.7309600000010000," +
                " 1.601614060001000000000000000000000, 4.16676200000E-8, 109590.1908685744227951578289," +
                " 0.0000050000010000000000000000, 784415066.00000000000100, 284001.000000000001000000000000000000," +
                " 1.0170605244111048790E-12, 0.000750000001000, 1.000002900000000E-12, 0.0014257722010000000000," +
                " 8.5730000000010000000000000000000000000000000000, 13908417840098258750.126560000001]",
                "{1E-12=759, 1.0E-12=701, 1.00E-12=556, 1.000E-12=506, 1.0000E-12=460, 1.00000E-12=403," +
                " 1.000000E-12=364, 1.000000000001=362, 1.0000000000010=331, 0.100000000001=329}",
                4.18021886093211E113,
                81.12436300001744,
                21.93101600001485
        );
        rangeUp_BigDecimal_helper(
                2,
                1,
                "-1E-12",
                "[0.0049999999990000000, 0.039999999999, 0.0009999999990, 0.0599999999990, 0.999999999999," +
                " 0.009999999999, -1E-12, 4.99999999999900, -1E-12, -1.000E-12, -1E-12, -1.0E-12, 0.09999999999900," +
                " -1E-12, 0.99999999999900, 0.9999999999990, -1.0E-12, -1E-12, -1.0E-12, 0.019999999999]",
                "{-1E-12=143063, -1.0E-12=71570, 0.099999999999=53796, 0.999999999999=47221, -1.00E-12=35476," +
                " 0.009999999999=26881, 0.0999999999990=26765, 0.9999999999990=23892, -1.000E-12=17827," +
                " 1.999999999999=15820}",
                1384.5634216923115,
                30.66058399998519,
                13.001653999889186
        );
        rangeUp_BigDecimal_helper(
                5,
                3,
                "-1E-12",
                "[0.0000129999990000, 0.0090029999990000, -1E-12, 24.99999999999900, -1.00000000000E-12," +
                " 1.9999999999990, 1.9999999999990000, 0.099999999999, 0.999999999999000, 0.0013999999990," +
                " 0.00079999999900000000, 4.56499999999900000000000000, 1.999999999999, 0.899999999999," +
                " -8.00000000000000000E-13, 0.00009999999900000, 0.2999999999990000, 12.599999999999000," +
                " 0.0005999999990, 0.00999999999900]",
                "{-1E-12=20047, -1.0E-12=14975, -1.00E-12=11080, 0.099999999999=8893, -1.000E-12=8454," +
                " 0.999999999999=8305, 0.0999999999990=6742, 0.009999999999=6731, 0.9999999999990=6216," +
                " -1.0000E-12=6172}",
                1.3255156106591658E14,
                42.30981799999284,
                15.068190999914787
        );
        rangeUp_BigDecimal_helper(
                32,
                8,
                "-1E-12",
                "[-9.99959506300234593700000000000000000000000000E-13, 2416.499999999999000000000, 0.179999999999," +
                " 180547618411867370588.274808799999000000000, 76448.53999999999900, 0.00000148518237630000000," +
                " 2352046.7999999999990000000000000000000000000000, 22222723878.7309599999990000," +
                " 1.601614059999000000000000000000000, 4.16656200000E-8, 109590.1908685744207951578289," +
                " 0.0000049999990000000000000000, 784415065.99999999999900, 284000.999999999999000000000000000000," +
                " -9.829394755888951210E-13, 0.000749999999000, -9.99997100000000E-13, 0.0014257721990000000000," +
                " 8.5729999999990000000000000000000000000000000000, 13908417840098258750.126559999999]",
                "{-1E-12=759, -1.0E-12=703, -1.00E-12=556, -1.000E-12=506, -1.0000E-12=460, -1.00000E-12=404," +
                " -1.000000E-12=364, 0.999999999999=362, 0.9999999999990=331, 0.099999999999=329}",
                4.18021886093211E113,
                81.06456700001691,
                21.920668000014597
        );
        rangeUp_BigDecimal_helper(
                2,
                1,
                "1E+12",
                "[1000000000000.0050000000, 1000000000000.04, 1000000000000.0010, 1000000000000.060, 1000000000001," +
                " 1000000000000.01, 1E+12, 1000000000005.00, 1E+12, 1.000E+12, 1E+12, 1.0E+12, 1000000000000.100," +
                " 1E+12, 1000000000001.00, 1000000000001.0, 1.0E+12, 1E+12, 1.0E+12, 1000000000000.02]",
                "{1E+12=143066, 1.0E+12=71565, 1000000000000.1=53796, 1000000000001=47223, 1.00E+12=35476," +
                " 1000000000000.01=26881, 1000000000000.10=26765, 1000000000001.0=23894, 1.000E+12=17826," +
                " 1000000000002=15821}",
                1.0000000013833472E12,
                35.175007999926635,
                4.719209999999073
        );
        rangeUp_BigDecimal_helper(
                5,
                3,
                "1E+12",
                "[1000000000000.0000130000, 1000000000000.0090030000, 1E+12, 1000000000025.00, 1.00000000000E+12," +
                " 1000000000002.0, 1000000000002.0000, 1000000000000.1, 1000000000001.000, 1000000000000.00140," +
                " 1000000000000.000800000000, 1000000000004.56500000000000000, 1000000000002, 1000000000000.9," +
                " 1000000000000.000000000000200000000000000000, 1000000000000.000100000, 1000000000000.30000," +
                " 1000000000012.6000, 1000000000000.00060, 1000000000000.0100]",
                "{1E+12=20050, 1.0E+12=14979, 1.00E+12=11085, 1000000000000.1=8894, 1.000E+12=8460," +
                " 1000000000001=8311, 1000000000000.10=6736, 1000000000000.01=6731, 1000000000001.0=6216," +
                " 1.0000E+12=6179}",
                1.3355156090700798E14,
                56.715418000037005,
                6.368124000006292
        );
        rangeUp_BigDecimal_helper(
                32,
                8,
                "1E+12",
                "[1000000000000.000000000000000040493699765406300000000000000000000000000, 1000000002416.5000000000," +
                " 1000000000000.18, 180547619411867370588.2748088000000000, 1000000076448.5400," +
                " 1000000000000.00000148518337630000000, 1000002352046.80000000000000000000000000000," +
                " 1022222723878.730960000, 1000000000001.60161406000000000000000000000," +
                " 1000000000000.0000000416666200000, 1000000109590.1908685744217951578289," +
                " 1000000000000.0000050000000000000000, 1000784415066.00, 1000000284001.000000000000000000," +
                " 1000000000000.0000000000000170605244111048790, 1000000000000.00075000," +
                " 1000000000000.000000000000000002900000000, 1000000000000.00142577220000000000," +
                " 1000000000008.5730000000000000000000000000000000000, 13908418840098258750.12656]",
                "{1E+12=759, 1.0E+12=701, 1.00E+12=556, 1.000E+12=506, 1.0000E+12=460, 1.00000E+12=403," +
                " 1.000000E+12=364, 1000000000001=362, 1000000000001.0=331, 1000000000000.1=329}",
                4.18021886093211E113,
                98.11771199995576,
                15.959104999996125
        );
        rangeUp_BigDecimal_helper(
                2,
                1,
                "-1E+12",
                "[-999999999999.9950000000, -999999999999.96, -999999999999.9990, -999999999999.940, -999999999999," +
                " -999999999999.99, -1E+12, -999999999995.00, -1E+12, -1.000E+12, -1E+12, -1.0E+12," +
                " -999999999999.900, -1E+12, -999999999999.00, -999999999999.0, -1.0E+12, -1E+12, -1.0E+12," +
                " -999999999999.98]",
                "{-1E+12=143066, -1.0E+12=71565, -999999999999.9=53796, -999999999999=47223, -1.00E+12=35476," +
                " -999999999999.99=26881, -999999999999.90=26765, -999999999999.0=23894, -1.000E+12=17826," +
                " -999999999998=15821}",
                -9.999999986166528E11,
                35.175007999926635,
                4.719209999999073
        );
        rangeUp_BigDecimal_helper(
                5,
                3,
                "-1E+12",
                "[-999999999999.9999870000, -999999999999.9909970000, -1E+12, -999999999975.00, -1.00000000000E+12," +
                " -999999999998.0, -999999999998.0000, -999999999999.9, -999999999999.000, -999999999999.99860," +
                " -999999999999.999200000000, -999999999995.43500000000000000, -999999999998, -999999999999.1," +
                " -999999999999.999999999999800000000000000000, -999999999999.999900000, -999999999999.70000," +
                " -999999999987.4000, -999999999999.99940, -999999999999.9900]",
                "{-1E+12=20050, -1.0E+12=14979, -1.00E+12=11085, -999999999999.9=8894, -1.000E+12=8460," +
                " -999999999999=8311, -999999999999.90=6736, -999999999999.99=6731, -999999999999.0=6216," +
                " -1.0000E+12=6179}",
                1.3155156090700802E14,
                56.71513600003691,
                6.368124000006292
        );
        rangeUp_BigDecimal_helper(
                32,
                8,
                "-1E+12",
                "[-999999999999.999999999999999959506300234593700000000000000000000000000, -999999997583.5000000000," +
                " -999999999999.82, 180547617411867370588.2748088000000000, -999999923551.4600," +
                " -999999999999.99999851481662370000000, -999997647953.20000000000000000000000000000," +
                " -977777276121.269040000, -999999999998.39838594000000000000000000000," +
                " -999999999999.9999999583333800000, -999999890409.8091314255782048421711," +
                " -999999999999.9999950000000000000000, -999215584934.00, -999999715999.000000000000000000," +
                " -999999999999.9999999999999829394755888951210, -999999999999.99925000," +
                " -999999999999.999999999999999997100000000, -999999999999.99857422780000000000," +
                " -999999999991.4270000000000000000000000000000000000, 13908416840098258750.12656]",
                "{-1E+12=759, -1.0E+12=701, -1.00E+12=556, -1.000E+12=506, -1.0000E+12=460, -1.00000E+12=403," +
                " -1.000000E+12=364, -999999999999=362, -999999999999.0=331, -999999999999.9=329}",
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
                "0.0",
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
                "0.0",
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
                "1",
                "[0.9950000000, 0.96, 0.9990, 0.940, 0, 0.9, 1, -4.00, 1, 1.000, 1, 1.0, 0.900, 1, 0E+2, 0.90, 1.0," +
                " 1, 1.0, 0.98]",
                "{1=143309, 1.0=71581, 0.9=53722, 0=47163, 1.00=35534, 0.99=26886, 0.90=26730, 1.000=17908," +
                " -1=15828, -2=15721}",
                -3211.5313539618583,
                6.861828000004236,
                1.8599939999883222
        );
        rangeDown_BigDecimal_helper(
                5,
                3,
                "1",
                "[0.9999870000, 0.9909970000, 1, -24.00, 1.00000000000, -1.0, -1.0000, 0.9, 0.000, 0.9860," +
                " 0.999200000000, -3.56500000000000000, -1, 0.1, 0.999999999999800000000000000000, 0.999900000," +
                " 0.70000, -11.6000, 0.99940, 0.9900]",
                "{1=20079, 1.0=14983, 1.00=11090, 0.9=8875, 1.000=8461, 0=8331, 0.90=6748, 0.99=6736, 1.0000=6196," +
                " 0.990=5084}",
                -1.3255142240731088E14,
                21.0937569999323,
                5.872534000007241
        );
        rangeDown_BigDecimal_helper(
                32,
                8,
                "1",
                "[0.999999999999999959506300234593700000000000000000000000000, -2415.5000000000, 0.82," +
                " -180547618411867370587.2748088000000000, -76447.5400, 0.99999851481662370000000," +
                " -2352045.80000000000000000000000000000, -22222723877.730960000, -0.60161406000000000000000000000," +
                " 0.9999999583333800000, -109589.1908685744217951578289, 0.9999950000000000000000, -784415065.00," +
                " -284000.000000000000000, 0.9999999999999829394755888951210, 0.99925000," +
                " 0.999999999999999997100000000, 0.99857422780000000000, -7.5730000000000000000000000000000000000," +
                " -13908417840098258749.12656]",
                "{1=760, 1.0=701, 1.00=556, 1.000=507, 1.0000=459, 1.00000=404, 1.000000=363, 0=362, 0.9=329," +
                " 1.0000000=321}",
                -4.18021886093211E113,
                70.8140910000301,
                15.95903799999594
        );
        rangeDown_BigDecimal_helper(
                2,
                1,
                "-1",
                "[-1.0050000000, -1.04, -1.0010, -1.060, -2, -1.01, -1, -6.00, -1, -1.000, -1, -1.0, -1.100, -1," +
                " -2.00, -2.0, -1.0, -1, -1.0, -1.02]",
                "{-1=143066, -1.0=71565, -1.1=53796, -2=47223, -1.00=35476, -1.01=26881, -1.10=26765, -2.0=23894," +
                " -1.000=17826, -3=15821}",
                -1385.5639559372435,
                7.622173999998321,
                1.8598829999882969
        );
        rangeDown_BigDecimal_helper(
                5,
                3,
                "-1",
                "[-1.0000130000, -1.0090030000, -1, -26.00, -1.00000000000, -3.0, -3.0000, -1.1, -2.000, -1.00140," +
                " -1.000800000000, -5.56500000000000000, -3, -1.9, -1.000000000000200000000000000000, -1.000100000," +
                " -1.30000, -13.6000, -1.00060, -1.0100]",
                "{-1=20050, -1.0=14979, -1.00=11085, -1.1=8894, -1.000=8460, -2=8311, -1.10=6736, -1.01=6731," +
                " -2.0=6216, -1.0000=6179}",
                -1.3255156090700816E14,
                21.815920999973557,
                5.873468000007206
        );
        rangeDown_BigDecimal_helper(
                32,
                8,
                "-1",
                "[-1.000000000000000040493699765406300000000000000000000000000, -2417.5000000000, -1.18," +
                " -180547618411867370589.2748088000000000, -76449.5400, -1.00000148518337630000000," +
                " -2352047.80000000000000000000000000000, -22222723879.730960000, -2.60161406000000000000000000000," +
                " -1.0000000416666200000, -109591.1908685744217951578289, -1.0000050000000000000000, -784415067.00," +
                " -284002.000000000000000000, -1.0000000000000170605244111048790, -1.00075000," +
                " -1.000000000000000002900000000, -1.00142577220000000000, -9.5730000000000000000000000000000000000," +
                " -13908417840098258751.12656]",
                "{-1=759, -1.0=701, -1.00=556, -1.000=506, -1.0000=460, -1.00000=403, -1.000000=364, -2=362," +
                " -2.0=331, -1.1=329}",
                -4.18021886093211E113,
                71.05169100002996,
                15.959081999996279
        );
        rangeDown_BigDecimal_helper(
                2,
                1,
                "1.0",
                "[0.9950000000, 0.96, 0.9990, 0.940, 0, 0.9, 1, -4.00, 1, 1.000, 1, 1.0, 0.900, 1, 0E+2, 0.90, 1.0," +
                " 1, 1.0, 0.98]",
                "{1=143309, 1.0=71581, 0.9=53722, 0=47163, 1.00=35534, 0.99=26886, 0.90=26730, 1.000=17908," +
                " -1=15828, -2=15721}",
                -3211.5313539618583,
                6.861828000004236,
                1.8599939999883222
        );
        rangeDown_BigDecimal_helper(
                5,
                3,
                "1.0",
                "[0.9999870000, 0.9909970000, 1, -24.00, 1.00000000000, -1.0, -1.0000, 0.9, 0.000, 0.9860," +
                " 0.999200000000, -3.56500000000000000, -1, 0.1, 0.999999999999800000000000000000, 0.999900000," +
                " 0.70000, -11.6000, 0.99940, 0.9900]",
                "{1=20079, 1.0=14983, 1.00=11090, 0.9=8875, 1.000=8461, 0=8331, 0.90=6748, 0.99=6736, 1.0000=6196," +
                " 0.990=5084}",
                -1.3255142240731088E14,
                21.0937569999323,
                5.872534000007241
        );
        rangeDown_BigDecimal_helper(
                32,
                8,
                "1.0",
                "[0.999999999999999959506300234593700000000000000000000000000, -2415.5000000000, 0.82," +
                " -180547618411867370587.2748088000000000, -76447.5400, 0.99999851481662370000000," +
                " -2352045.80000000000000000000000000000, -22222723877.730960000, -0.60161406000000000000000000000," +
                " 0.9999999583333800000, -109589.1908685744217951578289, 0.9999950000000000000000, -784415065.00," +
                " -284000.000000000000000, 0.9999999999999829394755888951210, 0.99925000," +
                " 0.999999999999999997100000000, 0.99857422780000000000, -7.5730000000000000000000000000000000000," +
                " -13908417840098258749.12656]",
                "{1=760, 1.0=701, 1.00=556, 1.000=507, 1.0000=459, 1.00000=404, 1.000000=363, 0=362, 0.9=329," +
                " 1.0000000=321}",
                -4.18021886093211E113,
                70.8140910000301,
                15.95903799999594
        );
        rangeDown_BigDecimal_helper(
                2,
                1,
                "-1.0",
                "[-1.0050000000, -1.04, -1.0010, -1.060, -2, -1.01, -1, -6.00, -1, -1.000, -1, -1.0, -1.100, -1," +
                " -2.00, -2.0, -1.0, -1, -1.0, -1.02]",
                "{-1=143066, -1.0=71565, -1.1=53796, -2=47223, -1.00=35476, -1.01=26881, -1.10=26765, -2.0=23894," +
                " -1.000=17826, -3=15821}",
                -1385.5639559372435,
                7.622173999998321,
                1.8598829999882969
        );
        rangeDown_BigDecimal_helper(
                5,
                3,
                "-1.0",
                "[-1.0000130000, -1.0090030000, -1, -26.00, -1.00000000000, -3.0, -3.0000, -1.1, -2.000, -1.00140," +
                " -1.000800000000, -5.56500000000000000, -3, -1.9, -1.000000000000200000000000000000, -1.000100000," +
                " -1.30000, -13.6000, -1.00060, -1.0100]",
                "{-1=20050, -1.0=14979, -1.00=11085, -1.1=8894, -1.000=8460, -2=8311, -1.10=6736, -1.01=6731," +
                " -2.0=6216, -1.0000=6179}",
                -1.3255156090700816E14,
                21.815920999973557,
                5.873468000007206
        );
        rangeDown_BigDecimal_helper(
                32,
                8,
                "-1.0",
                "[-1.000000000000000040493699765406300000000000000000000000000, -2417.5000000000, -1.18," +
                " -180547618411867370589.2748088000000000, -76449.5400, -1.00000148518337630000000," +
                " -2352047.80000000000000000000000000000, -22222723879.730960000, -2.60161406000000000000000000000," +
                " -1.0000000416666200000, -109591.1908685744217951578289, -1.0000050000000000000000, -784415067.00," +
                " -284002.000000000000000000, -1.0000000000000170605244111048790, -1.00075000," +
                " -1.000000000000000002900000000, -1.00142577220000000000, -9.5730000000000000000000000000000000000," +
                " -13908417840098258751.12656]",
                "{-1=759, -1.0=701, -1.00=556, -1.000=506, -1.0000=460, -1.00000=403, -1.000000=364, -2=362," +
                " -2.0=331, -1.1=329}",
                -4.18021886093211E113,
                71.05169100002996,
                15.959081999996279
        );
        rangeDown_BigDecimal_helper(
                2,
                1,
                "9",
                "[8.9950000000, 8.96, 8.9990, 8.940, 8, 8.99, 9, 4.00, 9, 9.000, 9, 9.0, 8.900, 9, 8.00, 8.0, 9.0," +
                " 9, 9.0, 8.98]",
                "{9=143083, 9.0=71532, 8.9=53760, 8=47248, 9.00=35506, 8.99=26881, 8.90=26747, 8.0=23909," +
                " 9.000=17821, 7=15816}",
                -1378.9562815626282,
                9.876909999983395,
                1.8597449999883424
        );
        rangeDown_BigDecimal_helper(
                5,
                3,
                "9",
                "[8.9999870000, 8.9909970000, 9, -16.00, 9.00000000000, 7.0, 7.0000, 8.9, 8.000, 8.99860," +
                " 8.999200000000, 4.43500000000000000, 7, 8.1, 8.999999999999800000000000000000, 8.999900000," +
                " 8.70000, -3.6000, 8.99940, 8.9900]",
                "{9=20051, 9.0=14976, 9.00=11083, 8.9=8895, 9.000=8470, 8=8308, 8.99=6744, 8.90=6738, 8.0=6218," +
                " 9.0000=6179}",
                -1.3255156101085684E14,
                23.87646199989173,
                5.874416000007179
        );
        rangeDown_BigDecimal_helper(
                32,
                8,
                "9",
                "[8.999999999999999959506300234593700000000000000000000000000, -2407.5000000000, 8.82," +
                " -180547618411867370579.2748088000000000, -76439.5400, 8.99999851481662370000000," +
                " -2352037.80000000000000000000000000000, -22222723869.730960000, 7.39838594000000000000000000000," +
                " 8.9999999583333800000, -109581.1908685744217951578289, 8.9999950000000000000000, -784415057.00," +
                " -283992.000000000000000000, 8.9999999999999829394755888951210, 8.99925000," +
                " 8.999999999999999997100000000, 8.99857422780000000000, 0.4270000000000000000000000000000000000," +
                " -13908417840098258741.12656]",
                "{9=759, 9.0=701, 9.00=556, 9.000=506, 9.0000=460, 9.00000=403, 9.000000=364, 8=362, 8.0=331," +
                " 8.9=329}",
                -4.18021886093211E113,
                72.39022300002145,
                15.95933799999627
        );
        rangeDown_BigDecimal_helper(
                2,
                1,
                "-9",
                "[-9.0050000000, -9.04, -9.0010, -9.060, -1E+1, -9.01, -9, -14.00, -9, -9.000, -9, -9.0, -9.100, -9," +
                " -10.0, -10, -9.0, -9, -9.0, -9.02]",
                "{-9=143066, -9.0=71565, -9.1=53796, -1E+1=47223, -9.00=35476, -9.01=26881, -9.10=26765, -10=23894," +
                " -9.000=17826, -11=15821}",
                -1393.5639559349913,
                9.880917999979038,
                1.8592359999871946
        );
        rangeDown_BigDecimal_helper(
                5,
                3,
                "-9",
                "[-9.0000130000, -9.0090030000, -9, -34.00, -9.00000000000, -11.0, -11.0000, -9.1, -10.00, -9.00140," +
                " -9.000800000000, -13.56500000000000000, -11, -9.9, -9.000000000000200000000000000000," +
                " -9.000100000, -9.30000, -21.6000, -9.00060, -9.0100]",
                "{-9=20050, -9.0=14979, -9.00=11085, -9.1=8894, -9.000=8460, -1E+1=8311, -9.10=6736, -9.01=6731," +
                " -10=6216, -9.0000=6179}",
                -1.3255156090701044E14,
                24.11267099993038,
                5.856708000007621
        );
        rangeDown_BigDecimal_helper(
                32,
                8,
                "-9",
                "[-9.000000000000000040493699765406300000000000000000000000000, -2425.5000000000, -9.18," +
                " -180547618411867370597.2748088000000000, -76457.5400, -9.00000148518337630000000," +
                " -2352055.80000000000000000000000000000, -22222723887.730960000, -10.60161406000000000000000000000," +
                " -9.0000000416666200000, -109599.1908685744217951578289, -9.0000050000000000000000, -784415075.00," +
                " -284010.00000000000000000, -9.0000000000000170605244111048790, -9.00075000," +
                " -9.000000000000000002900000000, -9.00142577220000000000," +
                " -17.5730000000000000000000000000000000000, -13908417840098258759.12656]",
                "{-9=759, -9.0=701, -9.00=556, -9.000=506, -9.0000=460, -9.00000=403, -9.000000=364, -1E+1=362," +
                " -10=331, -9.1=329}",
                -4.18021886093211E113,
                72.54750000002102,
                15.956679999995899
        );
        rangeDown_BigDecimal_helper(
                2,
                1,
                "10",
                "[9.9950000000, 9.96, 9.9990, 9.940, 9, 9.99, 1E+1, 5.00, 1E+1, 10.00, 1E+1, 10, 9.900, 1E+1, 9.00," +
                " 9.0, 10, 1E+1, 10, 9.98]",
                "{1E+1=143116, 10=71551, 9.9=53806, 9=47211, 10.0=35447, 9.99=26881, 9.90=26772, 9.0=23886," +
                " 10.00=17821, 8=15836}",
                -1371.5161272222995,
                9.041028999984173,
                1.8600609999848983
        );
        rangeDown_BigDecimal_helper(
                5,
                3,
                "10",
                "[9.9999870000, 9.9909970000, 1E+1, -15.00, 10.0000000000, 8.0, 8.0000, 9.9, 9.000, 9.99860," +
                " 9.999200000000, 5.43500000000000000, 8, 9.1, 9.999999999999800000000000000000, 9.999900000," +
                " 9.70000, -2.6000, 9.99940, 9.9900]",
                "{1E+1=20056, 10=14969, 10.0=11080, 9.9=8899, 10.00=8454, 9=8307, 9.90=6733, 9.99=6731, 9.0=6224," +
                " 10.000=6186}",
                -1.3255156078209173E14,
                23.679469999889513,
                5.835174000008125
        );
        rangeDown_BigDecimal_helper(
                32,
                8,
                "10",
                "[9.999999999999999959506300234593700000000000000000000000000, -2406.5000000000, 9.82," +
                " -180547618411867370578.2748088000000000, -76438.5400, 9.99999851481662370000000," +
                " -2352036.80000000000000000000000000000, -22222723868.730960000, 8.39838594000000000000000000000," +
                " 9.9999999583333800000, -109580.1908685744217951578289, 9.9999950000000000000000, -784415056.00," +
                " -283991.000000000000000000, 9.9999999999999829394755888951210, 9.99925000," +
                " 9.999999999999999997100000000, 9.99857422780000000000, 1.4270000000000000000000000000000000000," +
                " -13908417840098258740.12656]",
                "{1E+1=758, 10=701, 10.0=556, 10.00=506, 10.000=460, 10.0000=403, 10.00000=363, 9=362, 9.0=331," +
                " 9.9=329}",
                -4.18021886093211E113,
                72.45249300002985,
                15.954258999995814
        );
        rangeDown_BigDecimal_helper(
                2,
                1,
                "-10",
                "[-10.0050000000, -10.04, -10.0010, -10.060, -11, -10.01, -1E+1, -15.00, -1E+1, -10.00, -1E+1, -10," +
                " -10.100, -1E+1, -11.00, -11.0, -10, -1E+1, -10, -10.02]",
                "{-1E+1=143066, -10=71565, -10.1=53796, -11=47223, -10.0=35476, -10.01=26881, -10.10=26765," +
                " -11.0=23894, -10.00=17826, -12=15821}",
                -1394.5639559317256,
                9.344789999982071,
                1.8601429999848516
        );
        rangeDown_BigDecimal_helper(
                5,
                3,
                "-10",
                "[-10.0000130000, -10.0090030000, -1E+1, -35.00, -10.0000000000, -12.0, -12.0000, -10.1, -11.000," +
                " -10.00140, -10.000800000000, -14.56500000000000000, -12, -10.9," +
                " -10.000000000000200000000000000000, -10.000100000, -10.30000, -22.6000, -10.00060, -10.0100]",
                "{-1E+1=20050, -10=14979, -10.0=11085, -10.1=8894, -10.00=8460, -11=8311, -10.10=6736, -10.01=6731," +
                " -11.0=6216, -10.000=6179}",
                -1.3255156090701067E14,
                24.027310999942575,
                5.833906000008166
        );
        rangeDown_BigDecimal_helper(
                32,
                8,
                "-10",
                "[-10.000000000000000040493699765406300000000000000000000000000, -2426.5000000000, -10.18," +
                " -180547618411867370598.2748088000000000, -76458.5400, -10.00000148518337630000000," +
                " -2352056.80000000000000000000000000000, -22222723888.730960000, -11.60161406000000000000000000000," +
                " -10.0000000416666200000, -109600.1908685744217951578289, -10.0000050000000000000000," +
                " -784415076.00, -284011.000000000000000000, -10.0000000000000170605244111048790, -10.00075000," +
                " -10.000000000000000002900000000, -10.00142577220000000000," +
                " -18.5730000000000000000000000000000000000, -13908417840098258760.12656]",
                "{-1E+1=759, -10=701, -10.0=556, -10.00=506, -10.000=460, -10.0000=403, -10.00000=364, -11=362," +
                " -11.0=331, -10.1=329}",
                -4.18021886093211E113,
                72.6158040000279,
                15.954095999995749
        );
        rangeDown_BigDecimal_helper(
                2,
                1,
                "101",
                "[100.9950000000, 100.96, 100.9990, 100.940, 1E+2, 100.99, 101, 96.00, 101, 101.000, 101, 101.0," +
                " 100.900, 101, 100, 1.0E+2, 101.0, 101, 101.0, 100.98]",
                "{101=143062, 101.0=71569, 100.9=53802, 1E+2=47221, 101.00=35483, 100.99=26881, 100.90=26764," +
                " 1.0E+2=23895, 101.000=17823, 99=15820}",
                -1283.5659129612752,
                12.548057999941495,
                1.9062969999885824
        );
        rangeDown_BigDecimal_helper(
                5,
                3,
                "101",
                "[100.9999870000, 100.9909970000, 101, 76.00, 101.00000000000, 99.0, 99.0000, 100.9, 100.0," +
                " 100.99860, 100.999200000000, 96.43500000000000000, 99, 100.1, 100.999999999999800000000000000000," +
                " 100.999900000, 100.70000, 88.4000, 100.99940, 100.9900]",
                "{101=20049, 101.0=14983, 101.00=11083, 100.9=8895, 101.000=8462, 1E+2=8310, 100.90=6737," +
                " 100.99=6728, 1.0E+2=6215, 101.0000=6179}",
                -1.3255156090699792E14,
                26.704327999833442,
                5.8527580000076265
        );
        rangeDown_BigDecimal_helper(
                32,
                8,
                "101",
                "[100.999999999999999959506300234593700000000000000000000000000, -2315.5000000000, 100.82," +
                " -180547618411867370487.2748088000000000, -76347.5400, 100.99999851481662370000000," +
                " -2351945.80000000000000000000000000000, -22222723777.730960000, 99.39838594000000000000000000000," +
                " 100.9999999583333800000, -109489.1908685744217951578289, 100.9999950000000000000000," +
                " -784414965.00, -283900.0000000000000000, 100.9999999999999829394755888951210, 100.99925000," +
                " 100.999999999999999997100000000, 100.99857422780000000000," +
                " 92.4270000000000000000000000000000000000, -13908417840098258649.12656]",
                "{101=759, 101.0=701, 101.00=556, 101.000=506, 101.0000=460, 101.00000=403, 101.000000=364," +
                " 1E+2=362, 1.0E+2=331, 100.9=329}",
                -4.18021886093211E113,
                74.22560500003182,
                15.954912999995985
        );
        rangeDown_BigDecimal_helper(
                2,
                1,
                "-101",
                "[-101.0050000000, -101.04, -101.0010, -101.060, -102, -101.01, -101, -106.00, -101, -101.000, -101," +
                " -101.0, -101.100, -101, -102.00, -102.0, -101.0, -101, -101.0, -101.02]",
                "{-101=143066, -101.0=71565, -101.1=53796, -102=47223, -101.00=35476, -101.01=26881, -101.10=26765," +
                " -102.0=23894, -101.000=17826, -103=15821}",
                -1485.5639559341907,
                13.261434999963267,
                1.8598879999882962
        );
        rangeDown_BigDecimal_helper(
                5,
                3,
                "-101",
                "[-101.0000130000, -101.0090030000, -101, -126.00, -101.00000000000, -103.0, -103.0000, -101.1," +
                " -102.000, -101.00140, -101.000800000000, -105.56500000000000000, -103, -101.9," +
                " -101.000000000000200000000000000000, -101.000100000, -101.30000, -113.6000, -101.00060, -101.0100]",
                "{-101=20050, -101.0=14979, -101.00=11085, -101.1=8894, -101.000=8460, -102=8311, -101.10=6736," +
                " -101.01=6731, -102.0=6216, -101.0000=6179}",
                -1.3255156090703184E14,
                27.119858999869805,
                5.873467000007205
        );
        rangeDown_BigDecimal_helper(
                32,
                8,
                "-101",
                "[-101.000000000000000040493699765406300000000000000000000000000, -2517.5000000000, -101.18," +
                " -180547618411867370689.2748088000000000, -76549.5400, -101.00000148518337630000000," +
                " -2352147.80000000000000000000000000000, -22222723979.730960000," +
                " -102.60161406000000000000000000000, -101.0000000416666200000, -109691.1908685744217951578289," +
                " -101.0000050000000000000000, -784415167.00, -284102.000000000000000000," +
                " -101.0000000000000170605244111048790, -101.00075000, -101.000000000000000002900000000," +
                " -101.00142577220000000000, -109.5730000000000000000000000000000000000, -13908417840098258851.12656]",
                "{-101=759, -101.0=701, -101.00=556, -101.000=506, -101.0000=460, -101.00000=403, -101.000000=364," +
                " -102=362, -102.0=331, -101.1=329}",
                -4.18021886093211E113,
                74.39138300003185,
                15.959073999996285
        );
        rangeDown_BigDecimal_helper(
                2,
                1,
                "1234567",
                "[1234566.9950000000, 1234566.96, 1234566.9990, 1234566.940, 1234566, 1234566.99, 1234567," +
                " 1234562.00, 1234567, 1234567.000, 1234567, 1234567.0, 1234566.900, 1234567, 1234566.00, 1234566.0," +
                " 1234567.0, 1234567, 1234567.0, 1234566.98]",
                "{1234567=143066, 1234567.0=71565, 1234566.9=53796, 1234566=47223, 1234567.00=35476," +
                " 1234566.99=26881, 1234566.90=26765, 1234566.0=23894, 1234567.000=17826, 1234565=15821}",
                1233182.4360437375,
                26.87187099987704,
                1.8600259999881925
        );
        rangeDown_BigDecimal_helper(
                5,
                3,
                "1234567",
                "[1234566.9999870000, 1234566.9909970000, 1234567, 1234542.00, 1234567.00000000000, 1234565.0," +
                " 1234565.0000, 1234566.9, 1234566.000, 1234566.99860, 1234566.999200000000," +
                " 1234562.43500000000000000, 1234565, 1234566.1, 1234566.999999999999800000000000000000," +
                " 1234566.999900000, 1234566.70000, 1234554.4000, 1234566.99940, 1234566.9900]",
                "{1234567=20050, 1234567.0=14979, 1234567.00=11085, 1234566.9=8894, 1234567.000=8460, 1234566=8311," +
                " 1234566.90=6736, 1234566.99=6731, 1234566.0=6216, 1234567.0000=6179}",
                -1.3255155967258822E14,
                40.239422999948026,
                5.871547000007296
        );
        rangeDown_BigDecimal_helper(
                32,
                8,
                "1234567",
                "[1234566.999999999999999959506300234593700000000000000000000000000, 1232150.5000000000, 1234566.82," +
                " -180547618411866136021.2748088000000000, 1158118.4600, 1234566.99999851481662370000000," +
                " -1117479.80000000000000000000000000000, -22221489311.730960000," +
                " 1234565.39838594000000000000000000000, 1234566.9999999583333800000," +
                " 1124976.8091314255782048421711, 1234566.9999950000000000000000, -783180499.00," +
                " 950566.000000000000000000, 1234566.9999999999999829394755888951210, 1234566.99925000," +
                " 1234566.999999999999999997100000000, 1234566.99857422780000000000," +
                " 1234558.4270000000000000000000000000000000000, -13908417840097024183.12656]",
                "{1234567=759, 1234567.0=701, 1234567.00=556, 1234567.000=506, 1234567.0000=460, 1234567.00000=403," +
                " 1234567.000000=364, 1234566=362, 1234566.0=331, 1234566.9=329}",
                -4.18021886093211E113,
                82.92891300009914,
                15.958750999996031
        );
        rangeDown_BigDecimal_helper(
                2,
                1,
                "-1234567",
                "[-1234567.0050000000, -1234567.04, -1234567.0010, -1234567.060, -1234568, -1234567.01, -1234567," +
                " -1234572.00, -1234567, -1234567.000, -1234567, -1234567.0, -1234567.100, -1234567, -1234568.00," +
                " -1234568.0, -1234567.0, -1234567, -1234567.0, -1234567.02]",
                "{-1234567=143066, -1234567.0=71565, -1234567.1=53796, -1234568=47223, -1234567.00=35476," +
                " -1234567.01=26881, -1234567.10=26765, -1234568.0=23894, -1234567.000=17826, -1234569=15821}",
                -1235951.5639480965,
                26.786103999874832,
                1.8600099999879223
        );
        rangeDown_BigDecimal_helper(
                5,
                3,
                "-1234567",
                "[-1234567.0000130000, -1234567.0090030000, -1234567, -1234592.00, -1234567.00000000000, -1234569.0," +
                " -1234569.0000, -1234567.1, -1234568.000, -1234567.00140, -1234567.000800000000," +
                " -1234571.56500000000000000, -1234569, -1234567.9, -1234567.000000000000200000000000000000," +
                " -1234567.000100000, -1234567.30000, -1234579.6000, -1234567.00060, -1234567.0100]",
                "{-1234567=20050, -1234567.0=14979, -1234567.00=11085, -1234567.1=8894, -1234567.000=8460," +
                " -1234568=8311, -1234567.10=6736, -1234567.01=6731, -1234568.0=6216, -1234567.0000=6179}",
                -1.3255156214142816E14,
                40.21804799994806,
                5.866811000007397
        );
        rangeDown_BigDecimal_helper(
                32,
                8,
                "-1234567",
                "[-1234567.000000000000000040493699765406300000000000000000000000000, -1236983.5000000000," +
                " -1234567.18, -180547618411868605155.2748088000000000, -1311015.5400," +
                " -1234567.00000148518337630000000, -3586613.80000000000000000000000000000, -22223958445.730960000," +
                " -1234568.60161406000000000000000000000, -1234567.0000000416666200000," +
                " -1344157.1908685744217951578289, -1234567.0000050000000000000000, -785649633.00," +
                " -1518568.000000000000000000, -1234567.0000000000000170605244111048790, -1234567.00075000," +
                " -1234567.000000000000000002900000000, -1234567.00142577220000000000," +
                " -1234575.5730000000000000000000000000000000000, -13908417840099493317.12656]",
                "{-1234567=759, -1234567.0=701, -1234567.00=556, -1234567.000=506, -1234567.0000=460," +
                " -1234567.00000=403, -1234567.000000=364, -1234568=362, -1234568.0=331, -1234567.1=329}",
                -4.18021886093211E113,
                83.01889800010139,
                15.958300999996053
        );
        rangeDown_BigDecimal_helper(
                2,
                1,
                "0.09",
                "[0.0850000000, 0.05, 0.0890, 0.030, -0.91, 0.08, 0.09, -4.9100, 0.09, 0.09000, 0.09, 0.090," +
                " -0.0100, 0.09, -0.9100, -0.910, 0.090, 0.09, 0.090, 0.07]",
                "{0.09=143067, 0.090=71578, -0.01=53805, -0.91=47253, 0.0900=35463, 0.08=26874, -0.010=26763," +
                " -0.910=23886, 0.09000=17817, -1.91=15822}",
                -1384.4640551254167,
                9.249282999984306,
                3.213727999966871
        );
        rangeDown_BigDecimal_helper(
                5,
                3,
                "0.09",
                "[0.0899870000, 0.0809970000, 0.09, -24.9100, 0.0900000000000, -1.910, -1.910000, -0.01, -0.91000," +
                " 0.08860, 0.089200000000, -4.47500000000000000, -1.91, -0.81, 0.089999999999800000000000000000," +
                " 0.089900000, -0.210000, -12.51000, 0.08940, 0.0800]",
                "{0.09=20041, 0.090=14981, 0.0900=11088, -0.01=8904, 0.09000=8458, -0.91=8311, -0.010=6733," +
                " 0.08=6732, -0.910=6215, 0.090000=6179}",
                -1.3255156086335206E14,
                21.758886999950676,
                6.607647000006336
        );
        rangeDown_BigDecimal_helper(
                32,
                8,
                "0.09",
                "[0.089999999999999959506300234593700000000000000000000000000, -2416.41000000000, -0.09," +
                " -180547618411867370588.1848088000000000, -76448.4500, 0.08999851481662370000000," +
                " -2352046.710000000000000000000000000000, -22222723878.640960000, -1.51161406000000000000000000000," +
                " 0.0899999583333800000, -109590.1008685744217951578289, 0.0899950000000000000000, -784415065.9100," +
                " -284000.91000000000000000000, 0.0899999999999829394755888951210, 0.08925000," +
                " 0.089999999999999997100000000, 0.08857422780000000000, -8.4830000000000000000000000000000000000," +
                " -13908417840098258750.03656]",
                "{0.09=760, 0.090=701, 0.0900=556, 0.09000=506, 0.090000=460, 0.0900000=403, 0.09000000=364," +
                " -0.91=362, -0.910=331, -0.01=329}",
                -4.18021886093211E113,
                70.50093900002139,
                16.283410000005688
        );
        rangeDown_BigDecimal_helper(
                2,
                1,
                "-0.09",
                "[-0.0950000000, -0.13, -0.0910, -0.150, -1.09, -0.1, -0.09, -5.0900, -0.09, -0.09000, -0.09," +
                " -0.090, -0.1900, -0.09, -1.0900, -1.090, -0.090, -0.09, -0.090, -0.11]",
                "{-0.09=143066, -0.090=71565, -0.19=53796, -1.09=47223, -0.0900=35476, -0.1=26881, -0.190=26765," +
                " -1.090=23894, -0.09000=17826, -2.09=15821}",
                -1384.6539559255805,
                9.721092999988024,
                3.1611809999692686
        );
        rangeDown_BigDecimal_helper(
                5,
                3,
                "-0.09",
                "[-0.0900130000, -0.0990030000, -0.09, -25.0900, -0.0900000000000, -2.090, -2.090000, -0.19," +
                " -1.09000, -0.09140, -0.090800000000, -4.65500000000000000, -2.09, -0.99," +
                " -0.090000000000200000000000000000, -0.090100000, -0.390000, -12.69000, -0.09060, -0.100]",
                "{-0.09=20050, -0.090=14979, -0.0900=11085, -0.19=8894, -0.09000=8460, -1.09=8311, -0.190=6736," +
                " -0.1=6731, -1.090=6216, -0.090000=6179}",
                -1.3255156090700805E14,
                22.11084499998398,
                6.582647000006659
        );
        rangeDown_BigDecimal_helper(
                32,
                8,
                "-0.09",
                "[-0.090000000000000040493699765406300000000000000000000000000, -2416.59000000000, -0.27," +
                " -180547618411867370588.3648088000000000, -76448.6300, -0.09000148518337630000000," +
                " -2352046.890000000000000000000000000000, -22222723878.820960000, -1.69161406000000000000000000000," +
                " -0.0900000416666200000, -109590.2808685744217951578289, -0.0900050000000000000000," +
                " -784415066.0900, -284001.09000000000000000000, -0.0900000000000170605244111048790, -0.09075000," +
                " -0.090000000000000002900000000, -0.09142577220000000000, -8.6630000000000000000000000000000000000," +
                " -13908417840098258750.21656]",
                "{-0.09=759, -0.090=701, -0.0900=556, -0.09000=506, -0.090000=460, -0.0900000=403, -0.09000000=364," +
                " -1.09=362, -1.090=331, -0.19=329}",
                -4.18021886093211E113,
                70.64934000002235,
                16.281369000005466
        );
        rangeDown_BigDecimal_helper(
                2,
                1,
                "1E-12",
                "[-0.0049999999990000000, -0.039999999999, -0.0009999999990, -0.0599999999990, -0.999999999999," +
                " -0.009999999999, 1E-12, -4.99999999999900, 1E-12, 1.000E-12, 1E-12, 1.0E-12, -0.09999999999900," +
                " 1E-12, -0.99999999999900, -0.9999999999990, 1.0E-12, 1E-12, 1.0E-12, -0.019999999999]",
                "{1E-12=143063, 1.0E-12=71570, -0.099999999999=53796, -0.999999999999=47221, 1.00E-12=35476," +
                " -0.009999999999=26881, -0.0999999999990=26765, -0.9999999999990=23892, 1.000E-12=17827," +
                " -1.999999999999=15820}",
                -1384.5634216923115,
                30.66058399998519,
                13.001653999889186
        );
        rangeDown_BigDecimal_helper(
                5,
                3,
                "1E-12",
                "[-0.0000129999990000, -0.0090029999990000, 1E-12, -24.99999999999900, 1.00000000000E-12," +
                " -1.9999999999990, -1.9999999999990000, -0.099999999999, -0.999999999999000, -0.0013999999990," +
                " -0.00079999999900000000, -4.56499999999900000000000000, -1.999999999999, -0.899999999999," +
                " 8.00000000000000000E-13, -0.00009999999900000, -0.2999999999990000, -12.599999999999000," +
                " -0.0005999999990, -0.00999999999900]",
                "{1E-12=20047, 1.0E-12=14975, 1.00E-12=11080, -0.099999999999=8893, 1.000E-12=8454," +
                " -0.999999999999=8305, -0.0999999999990=6742, -0.009999999999=6731, -0.9999999999990=6216," +
                " 1.0000E-12=6172}",
                -1.3255156106591658E14,
                42.30981799999284,
                15.068190999914787
        );
        rangeDown_BigDecimal_helper(
                32,
                8,
                "1E-12",
                "[9.99959506300234593700000000000000000000000000E-13, -2416.499999999999000000000, -0.179999999999," +
                " -180547618411867370588.274808799999000000000, -76448.53999999999900, -0.00000148518237630000000," +
                " -2352046.7999999999990000000000000000000000000000, -22222723878.7309599999990000," +
                " -1.601614059999000000000000000000000, -4.16656200000E-8, -109590.1908685744207951578289," +
                " -0.0000049999990000000000000000, -784415065.99999999999900," +
                " -284000.999999999999000000000000000000, 9.829394755888951210E-13, -0.000749999999000," +
                " 9.99997100000000E-13, -0.0014257721990000000000," +
                " -8.5729999999990000000000000000000000000000000000, -13908417840098258750.126559999999]",
                "{1E-12=759, 1.0E-12=703, 1.00E-12=556, 1.000E-12=506, 1.0000E-12=460, 1.00000E-12=404," +
                " 1.000000E-12=364, -0.999999999999=362, -0.9999999999990=331, -0.099999999999=329}",
                -4.18021886093211E113,
                81.06456700001691,
                21.920668000014597
        );
        rangeDown_BigDecimal_helper(
                2,
                1,
                "-1E-12",
                "[-0.0050000000010000000, -0.040000000001, -0.0010000000010, -0.0600000000010, -1.000000000001," +
                " -0.010000000001, -1E-12, -5.00000000000100, -1E-12, -1.000E-12, -1E-12, -1.0E-12," +
                " -0.10000000000100, -1E-12, -1.00000000000100, -1.0000000000010, -1.0E-12, -1E-12, -1.0E-12," +
                " -0.020000000001]",
                "{-1E-12=143066, -1.0E-12=71565, -0.100000000001=53796, -1.000000000001=47223, -1.00E-12=35476," +
                " -0.010000000001=26881, -0.1000000000010=26765, -1.0000000000010=23894, -1.000E-12=17826," +
                " -2.000000000001=15821}",
                -1384.5639559361018,
                30.66109999998521,
                13.00223999988918
        );
        rangeDown_BigDecimal_helper(
                5,
                3,
                "-1E-12",
                "[-0.0000130000010000, -0.0090030000010000, -1E-12, -25.00000000000100, -1.00000000000E-12," +
                " -2.0000000000010, -2.0000000000010000, -0.100000000001, -1.000000000001000, -0.0014000000010," +
                " -0.00080000000100000000, -4.56500000000100000000000000, -2.000000000001, -0.900000000001," +
                " -1.200000000000000000E-12, -0.00010000000100000, -0.3000000000010000, -12.600000000001000," +
                " -0.0006000000010, -0.01000000000100]",
                "{-1E-12=20050, -1.0E-12=14979, -1.00E-12=11085, -0.100000000001=8894, -1.000E-12=8460," +
                " -1.000000000001=8311, -0.1000000000010=6736, -0.010000000001=6731, -1.0000000000010=6216," +
                " -1.0000E-12=6179}",
                -1.3255156090700803E14,
                42.33828099999371,
                15.086760999914567
        );
        rangeDown_BigDecimal_helper(
                32,
                8,
                "-1E-12",
                "[-1.000040493699765406300000000000000000000000000E-12, -2416.500000000001000000000," +
                " -0.180000000001, -180547618411867370588.274808800001000000000, -76448.54000000000100," +
                " -0.00000148518437630000000, -2352046.8000000000010000000000000000000000000000," +
                " -22222723878.7309600000010000, -1.601614060001000000000000000000000, -4.16676200000E-8," +
                " -109590.1908685744227951578289, -0.0000050000010000000000000000, -784415066.00000000000100," +
                " -284001.000000000001000000000000000000, -1.0170605244111048790E-12, -0.000750000001000," +
                " -1.000002900000000E-12, -0.0014257722010000000000," +
                " -8.5730000000010000000000000000000000000000000000, -13908417840098258750.126560000001]",
                "{-1E-12=759, -1.0E-12=701, -1.00E-12=556, -1.000E-12=506, -1.0000E-12=460, -1.00000E-12=403," +
                " -1.000000E-12=364, -1.000000000001=362, -1.0000000000010=331, -0.100000000001=329}",
                -4.18021886093211E113,
                81.12436300001744,
                21.93101600001485
        );
        rangeDown_BigDecimal_helper(
                2,
                1,
                "1E+12",
                "[999999999999.9950000000, 999999999999.96, 999999999999.9990, 999999999999.940, 999999999999," +
                " 999999999999.99, 1E+12, 999999999995.00, 1E+12, 1.000E+12, 1E+12, 1.0E+12, 999999999999.900," +
                " 1E+12, 999999999999.00, 999999999999.0, 1.0E+12, 1E+12, 1.0E+12, 999999999999.98]",
                "{1E+12=143066, 1.0E+12=71565, 999999999999.9=53796, 999999999999=47223, 1.00E+12=35476," +
                " 999999999999.99=26881, 999999999999.90=26765, 999999999999.0=23894, 1.000E+12=17826," +
                " 999999999998=15821}",
                9.999999986166528E11,
                35.175007999926635,
                4.719209999999073
        );
        rangeDown_BigDecimal_helper(
                5,
                3,
                "1E+12",
                "[999999999999.9999870000, 999999999999.9909970000, 1E+12, 999999999975.00, 1.00000000000E+12," +
                " 999999999998.0, 999999999998.0000, 999999999999.9, 999999999999.000, 999999999999.99860," +
                " 999999999999.999200000000, 999999999995.43500000000000000, 999999999998, 999999999999.1," +
                " 999999999999.999999999999800000000000000000, 999999999999.999900000, 999999999999.70000," +
                " 999999999987.4000, 999999999999.99940, 999999999999.9900]",
                "{1E+12=20050, 1.0E+12=14979, 1.00E+12=11085, 999999999999.9=8894, 1.000E+12=8460," +
                " 999999999999=8311, 999999999999.90=6736, 999999999999.99=6731, 999999999999.0=6216," +
                " 1.0000E+12=6179}",
                -1.3155156090700802E14,
                56.71513600003691,
                6.368124000006292
        );
        rangeDown_BigDecimal_helper(
                32,
                8,
                "1E+12",
                "[999999999999.999999999999999959506300234593700000000000000000000000000, 999999997583.5000000000," +
                " 999999999999.82, -180547617411867370588.2748088000000000, 999999923551.4600," +
                " 999999999999.99999851481662370000000, 999997647953.20000000000000000000000000000," +
                " 977777276121.269040000, 999999999998.39838594000000000000000000000," +
                " 999999999999.9999999583333800000, 999999890409.8091314255782048421711," +
                " 999999999999.9999950000000000000000, 999215584934.00, 999999715999.000000000000000000," +
                " 999999999999.9999999999999829394755888951210, 999999999999.99925000," +
                " 999999999999.999999999999999997100000000, 999999999999.99857422780000000000," +
                " 999999999991.4270000000000000000000000000000000000, -13908416840098258750.12656]",
                "{1E+12=759, 1.0E+12=701, 1.00E+12=556, 1.000E+12=506, 1.0000E+12=460, 1.00000E+12=403," +
                " 1.000000E+12=364, 999999999999=362, 999999999999.0=331, 999999999999.9=329}",
                -4.18021886093211E113,
                98.06712599995508,
                15.959104999996125
        );
        rangeDown_BigDecimal_helper(
                2,
                1,
                "-1E+12",
                "[-1000000000000.0050000000, -1000000000000.04, -1000000000000.0010, -1000000000000.060," +
                " -1000000000001, -1000000000000.01, -1E+12, -1000000000005.00, -1E+12, -1.000E+12, -1E+12," +
                " -1.0E+12, -1000000000000.100, -1E+12, -1000000000001.00, -1000000000001.0, -1.0E+12, -1E+12," +
                " -1.0E+12, -1000000000000.02]",
                "{-1E+12=143066, -1.0E+12=71565, -1000000000000.1=53796, -1000000000001=47223, -1.00E+12=35476," +
                " -1000000000000.01=26881, -1000000000000.10=26765, -1000000000001.0=23894, -1.000E+12=17826," +
                " -1000000000002=15821}",
                -1.0000000013833472E12,
                35.175007999926635,
                4.719209999999073
        );
        rangeDown_BigDecimal_helper(
                5,
                3,
                "-1E+12",
                "[-1000000000000.0000130000, -1000000000000.0090030000, -1E+12, -1000000000025.00," +
                " -1.00000000000E+12, -1000000000002.0, -1000000000002.0000, -1000000000000.1, -1000000000001.000," +
                " -1000000000000.00140, -1000000000000.000800000000, -1000000000004.56500000000000000," +
                " -1000000000002, -1000000000000.9, -1000000000000.000000000000200000000000000000," +
                " -1000000000000.000100000, -1000000000000.30000, -1000000000012.6000, -1000000000000.00060," +
                " -1000000000000.0100]",
                "{-1E+12=20050, -1.0E+12=14979, -1.00E+12=11085, -1000000000000.1=8894, -1.000E+12=8460," +
                " -1000000000001=8311, -1000000000000.10=6736, -1000000000000.01=6731, -1000000000001.0=6216," +
                " -1.0000E+12=6179}",
                -1.3355156090700798E14,
                56.715418000037005,
                6.368124000006292
        );
        rangeDown_BigDecimal_helper(
                32,
                8,
                "-1E+12",
                "[-1000000000000.000000000000000040493699765406300000000000000000000000000," +
                " -1000000002416.5000000000, -1000000000000.18, -180547619411867370588.2748088000000000," +
                " -1000000076448.5400, -1000000000000.00000148518337630000000," +
                " -1000002352046.80000000000000000000000000000, -1022222723878.730960000," +
                " -1000000000001.60161406000000000000000000000, -1000000000000.0000000416666200000," +
                " -1000000109590.1908685744217951578289, -1000000000000.0000050000000000000000, -1000784415066.00," +
                " -1000000284001.000000000000000000, -1000000000000.0000000000000170605244111048790," +
                " -1000000000000.00075000, -1000000000000.000000000000000002900000000," +
                " -1000000000000.00142577220000000000, -1000000000008.5730000000000000000000000000000000000," +
                " -13908418840098258750.12656]",
                "{-1E+12=759, -1.0E+12=701, -1.00E+12=556, -1.000E+12=506, -1.0000E+12=460, -1.00000E+12=403," +
                " -1.000000E+12=364, -1000000000001=362, -1000000000001.0=331, -1000000000000.1=329}",
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
                "0.0",
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
                "0.0",
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
                "1",
                "[1.005, 1.0000007, 1, 7, 1.7, 1.2, 7, 1, 1, 3.5, 1.01, 1, 1, 1.001, 1, 2, 1, 1.1, 1, 1.01]",
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
                " 1.9, 3723, 1.0001, 1.3, 1.0002]",
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
                " 1.00075]",
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
                " -0.99]",
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
                " -0.99999, -0.1, 3721, -0.9999, -0.7, -0.9998]",
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
                " 19.85970393, -0.994624905720080488, 2, -0.99851280201, -0.99925]",
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
                "[1.005, 1.0000007, 1, 7, 1.7, 1.2, 7, 1, 1, 3.5, 1.01, 1, 1, 1.001, 1, 2, 1, 1.1, 1, 1.01]",
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
                " 1.9, 3723, 1.0001, 1.3, 1.0002]",
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
                " 1.00075]",
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
                " -0.99]",
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
                " -0.99999, -0.1, 3721, -0.9999, -0.7, -0.9998]",
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
                " 19.85970393, -0.994624905720080488, 2, -0.99851280201, -0.99925]",
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
                "[9.005, 9.0000007, 9, 15, 9.7, 9.2, 15, 9, 9, 11.5, 9.01, 9, 9, 9.001, 9, 10, 9, 9.1, 9, 9.01]",
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
                " 9.00001, 9.9, 3731, 9.0001, 9.3, 9.0002]",
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
                " 9.00148719799, 9.00075]",
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
                " -9, -8.99]",
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
                " -3, -9, -8.99999, -8.1, 3713, -8.9999, -8.7, -8.9998]",
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
                " 11.85970393, -8.994624905720080488, -6, -8.99851280201, -8.99925]",
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
                " 10, 10.01]",
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
                " 16, 10, 10.00001, 10.9, 3732, 10.0001, 10.3, 10.0002]",
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
                " 30.85970393, 10.005375094279919512, 13, 10.00148719799, 10.00075]",
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
                " -10, -9.9, -10, -9.99]",
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
                " -4, -10, -9.99999, -9.1, 3712, -9.9999, -9.7, -9.9998]",
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
                " 10.85970393, -9.994624905720080488, -7, -9.99851280201, -9.99925]",
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
                " 101, 102, 101, 101.1, 101, 101.01]",
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
                " 101.00000000000002, 107, 101, 101.00001, 101.9, 3823, 101.0001, 101.3, 101.0002]",
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
                " 804.32, 121.85970393, 101.005375094279919512, 104, 101.00148719799, 101.00075]",
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
                " -100.999, -101, -100, -101, -100.9, -101, -100.99]",
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
                " -100.99999999999998, -95, -101, -100.99999, -100.1, 3621, -100.9999, -100.7, -100.9998]",
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
                " 602.32, -80.14029607, -100.994624905720080488, -98, -100.99851280201, -100.99925]",
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
                " 1234567, 1234567.01]",
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
                " 1234567.9, 1238289, 1234567.0001, 1234567.3, 1234567.0002]",
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
                " 1234567.00148719799, 1234567.00075]",
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
                " -1234566.9, -1234567, -1234566.99]",
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
                " -1234566.99999, -1234566.1, -1230845, -1234566.9999, -1234566.7, -1234566.9998]",
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
                " -1234566.99851280201, -1234566.99925]",
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
                " 1.09, 0.09, 0.19, 0.09, 0.1]",
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
                " 6.09, 0.09, 0.09001, 0.99, 3722.09, 0.0901, 0.39, 0.0902]",
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
                " 20.94970393, 0.095375094279919512, 3.09, 0.09148719799, 0.09075]",
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
                " -0.089, -0.09, 0.91, -0.09, 0.01, -0.09, -0.08]",
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
                " -0.08999999999998, 5.91, -0.09, -0.08999, 0.81, 3721.91, -0.0899, 0.21, -0.0898]",
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
                " 703.23, 20.76970393, -0.084624905720080488, 2.91, -0.08851280201, -0.08925]",
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
                " 1E-12, 0.100000000001, 1E-12, 0.010000000001]",
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
                " 0.300000000001, 0.000200000001]",
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
                " 0.001487197991, 0.000750000001]",
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
                " -1E-12, 0.999999999999, -1E-12, 0.099999999999, -1E-12, 0.009999999999]",
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
                " 0.299999999999, 0.000199999999]",
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
                " 0.001487197989, 0.000749999999]",
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
                " 1000000000000.1, 1000000000000, 1000000000000.01]",
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
                " 1000000000000.0002]",
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
                " 1000000000003, 1000000000000.00148719799, 1000000000000.00075]",
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
                " -999999999999.9, -1000000000000, -999999999999.99]",
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
                " -999999999999.9998]",
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
                " -999999999997, -999999999999.99851280201, -999999999999.99925]",
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
                "0.0",
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
                "0.0",
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
                "1",
                "[0.995, 0.9999993, 1, -5, 0.3, 0.8, -5, 1, 1, -1.5, 0.99, 1, 1, 0.999, 1, 0, 1, 0.9, 1, 0.99]",
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
                " 0.99999, 0.1, -3721, 0.9999, 0.7, 0.9998]",
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
                " -19.85970393, 0.994624905720080488, -2, 0.99851280201, 0.99925]",
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
                " -1, -1.01]",
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
                " -7, -1, -1.00001, -1.9, -3723, -1.0001, -1.3, -1.0002]",
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
                " -21.85970393, -1.005375094279919512, -4, -1.00148719799, -1.00075]",
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
                "[0.995, 0.9999993, 1, -5, 0.3, 0.8, -5, 1, 1, -1.5, 0.99, 1, 1, 0.999, 1, 0, 1, 0.9, 1, 0.99]",
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
                " 0.99999, 0.1, -3721, 0.9999, 0.7, 0.9998]",
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
                " -19.85970393, 0.994624905720080488, -2, 0.99851280201, 0.99925]",
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
                " -1, -1.01]",
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
                " -7, -1, -1.00001, -1.9, -3723, -1.0001, -1.3, -1.0002]",
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
                " -21.85970393, -1.005375094279919512, -4, -1.00148719799, -1.00075]",
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
                "[8.995, 8.9999993, 9, 3, 8.3, 8.8, 3, 9, 9, 6.5, 8.99, 9, 9, 8.999, 9, 8, 9, 8.9, 9, 8.99]",
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
                " 8.1, -3713, 8.9999, 8.7, 8.9998]",
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
                " -11.85970393, 8.994624905720080488, 6, 8.99851280201, 8.99925]",
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
                " -9.1, -9, -9.01]",
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
                " -9.00000000000002, -15, -9, -9.00001, -9.9, -3731, -9.0001, -9.3, -9.0002]",
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
                " -712.32, -29.85970393, -9.005375094279919512, -12, -9.00148719799, -9.00075]",
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
                "[9.995, 9.9999993, 10, 4, 9.3, 9.8, 4, 10, 10, 7.5, 9.99, 10, 10, 9.999, 10, 9, 10, 9.9, 10, 9.99]",
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
                " 9.99999, 9.1, -3712, 9.9999, 9.7, 9.9998]",
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
                " -10.85970393, 9.994624905720080488, 7, 9.99851280201, 9.99925]",
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
                " -10, -11, -10, -10.1, -10, -10.01]",
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
                " -10.00000000000002, -16, -10, -10.00001, -10.9, -3732, -10.0001, -10.3, -10.0002]",
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
                " -713.32, -30.85970393, -10.005375094279919512, -13, -10.00148719799, -10.00075]",
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
                " 100, 101, 100.9, 101, 100.99]",
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
                " 100.99999999999998, 95, 101, 100.99999, 100.1, -3621, 100.9999, 100.7, 100.9998]",
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
                " -602.32, 80.14029607, 100.994624905720080488, 98, 100.99851280201, 100.99925]",
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
                " -101.001, -101, -102, -101, -101.1, -101, -101.01]",
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
                " -101.00000000000002, -107, -101, -101.00001, -101.9, -3823, -101.0001, -101.3, -101.0002]",
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
                " -804.32, -121.85970393, -101.005375094279919512, -104, -101.00148719799, -101.00075]",
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
                " 1234567, 1234566.99]",
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
                " 1234566.1, 1230845, 1234566.9999, 1234566.7, 1234566.9998]",
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
                " 1234566.99851280201, 1234566.99925]",
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
                " -1234567.1, -1234567, -1234567.01]",
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
                " -1234567.00001, -1234567.9, -1238289, -1234567.0001, -1234567.3, -1234567.0002]",
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
                " -1234567.00148719799, -1234567.00075]",
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
                " 0.09, -0.91, 0.09, -0.01, 0.09, 0.08]",
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
                " 0.08999999999998, -5.91, 0.09, 0.08999, -0.81, -3721.91, 0.0899, -0.21, 0.0898]",
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
                " -703.23, -20.76970393, 0.084624905720080488, -2.91, 0.08851280201, 0.08925]",
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
                " -0.091, -0.09, -1.09, -0.09, -0.19, -0.09, -0.1]",
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
                " -0.09000000000002, -6.09, -0.09, -0.09001, -0.99, -3722.09, -0.0901, -0.39, -0.0902]",
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
                " -703.41, -20.94970393, -0.095375094279919512, -3.09, -0.09148719799, -0.09075]",
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
                " 1E-12, -0.999999999999, 1E-12, -0.099999999999, 1E-12, -0.009999999999]",
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
                " -0.299999999999, -0.000199999999]",
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
                " -2.999999999999, -0.001487197989, -0.000749999999]",
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
                " -0.001000000001, -1E-12, -1.000000000001, -1E-12, -0.100000000001, -1E-12, -0.010000000001]",
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
                " -0.300000000001, -0.000200000001]",
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
                " -3.000000000001, -0.001487197991, -0.000750000001]",
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
                " 999999999999.9, 1000000000000, 999999999999.99]",
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
                " 999999996278, 999999999999.9999, 999999999999.7, 999999999999.9998]",
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
                " 999999999999.99851280201, 999999999999.99925]",
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
                " -1000000000001, -1000000000000, -1000000000000.1, -1000000000000, -1000000000000.01]",
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
                " -1000000000000.0002]",
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
                " -1000000000003, -1000000000000.00148719799, -1000000000000.00075]",
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

    private static void withElement_helper(
            int scale,
            @NotNull String input,
            @Nullable Integer element,
            @NotNull String output,
            double elementFrequency
    ) {
        Iterable<Integer> xs = P.withScale(scale).withElement(element, cycle(readIntegerListWithNulls(input)));
        List<Integer> sample = toList(take(DEFAULT_SAMPLE_SIZE, xs));
        aeqit(take(TINY_LIMIT, sample), output);
        aeq(meanOfIntegers(toList(map(x -> Objects.equals(x, element) ? 1 : 0, sample))), elementFrequency);
        P.reset();
    }

    @Test
    public void testWithElement() {
        withElement_helper(
                2,
                "[1]",
                null,
                "[1, null, 1, 1, 1, 1, 1, 1, 1, null, 1, null, null, 1, null, null, 1, 1, null, 1]",
                0.4992549999935604
        );
        withElement_helper(
                8,
                "[1]",
                null,
                "[1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]",
                0.12480700000010415
        );
        withElement_helper(
                32,
                "[1]",
                null,
                "[1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]",
                0.031218000000010567
        );
        withElement_helper(
                2,
                "[null, 2, 3]",
                10,
                "[null, 10, 2, 3, null, 2, 3, null, 2, 10, 3, 10, 10, null, 10, 10, 2, 3, 10, null]",
                0.4992549999935604
        );
        withElement_helper(
                8,
                "[null, 2, 3]",
                10,
                "[null, 2, 3, null, 2, 3, null, 2, 3, null, 2, 3, null, 2, 3, null, 2, 3, null, 2]",
                0.12480700000010415
        );
        withElement_helper(
                32,
                "[null, 2, 3]",
                10,
                "[null, 2, 3, null, 2, 3, null, 2, 3, null, 2, 3, null, 2, 3, null, 2, 3, null, 2]",
                0.031218000000010567
        );
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
        aeqit(take(TINY_LIMIT, sample), output);
        aeq(meanOfIntegers(toList(map(x -> x == null ? 1 : 0, sample))), nullFrequency);
        P.reset();
    }

    @Test
    public void testWithNull() {
        withNull_helper(
                2,
                "[1]",
                "[1, null, 1, 1, 1, 1, 1, 1, 1, null, 1, null, null, 1, null, null, 1, 1, null, 1]",
                0.4992549999935604
        );
        withNull_helper(8, "[1]", "[1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]", 0.12480700000010415);
        withNull_helper(
                32,
                "[1]",
                "[1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]",
                0.031218000000010567
        );
        withNull_helper(
                2,
                "[1, 2, 3]",
                "[1, null, 2, 3, 1, 2, 3, 1, 2, null, 3, null, null, 1, null, null, 2, 3, null, 1]",
                0.4992549999935604
        );
        withNull_helper(
                8,
                "[1, 2, 3]",
                "[1, 2, 3, 1, 2, 3, 1, 2, 3, 1, 2, 3, 1, 2, 3, 1, 2, 3, 1, 2]",
                0.12480700000010415
        );
        withNull_helper(
                32,
                "[1, 2, 3]",
                "[1, 2, 3, 1, 2, 3, 1, 2, 3, 1, 2, 3, 1, 2, 3, 1, 2, 3, 1, 2]",
                0.031218000000010567
        );
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
        aeqit(take(TINY_LIMIT, sample), output);
        aeq(meanOfIntegers(toList(map(x -> x.isPresent() ? 0 : 1, sample))), emptyFrequency);
        P.reset();
    }

    @Test
    public void testOptionals() {
        optionalsHelper(
                2,
                "[1]",
                "[Optional[1], Optional.empty, Optional[1], Optional[1], Optional[1], Optional[1], Optional[1]," +
                " Optional[1], Optional[1], Optional.empty, Optional[1], Optional.empty, Optional.empty," +
                " Optional[1], Optional.empty, Optional.empty, Optional[1], Optional[1], Optional.empty, Optional[1]]",
                0.4992549999935604
        );
        optionalsHelper(
                8,
                "[1]",
                "[Optional[1], Optional[1], Optional[1], Optional[1], Optional[1], Optional[1], Optional[1]," +
                " Optional[1], Optional[1], Optional[1], Optional[1], Optional[1], Optional[1], Optional[1]," +
                " Optional[1], Optional[1], Optional[1], Optional[1], Optional[1], Optional[1]]",
                0.12480700000010415
        );
        optionalsHelper(
                32,
                "[1]",
                "[Optional[1], Optional[1], Optional[1], Optional[1], Optional[1], Optional[1], Optional[1]," +
                " Optional[1], Optional[1], Optional[1], Optional[1], Optional[1], Optional[1], Optional[1]," +
                " Optional[1], Optional[1], Optional[1], Optional[1], Optional[1], Optional[1]]",
                0.031218000000010567
        );
        optionalsHelper(
                2,
                "[1, 2, 3]",
                "[Optional[1], Optional.empty, Optional[2], Optional[3], Optional[1], Optional[2], Optional[3]," +
                " Optional[1], Optional[2], Optional.empty, Optional[3], Optional.empty, Optional.empty," +
                " Optional[1], Optional.empty, Optional.empty, Optional[2], Optional[3], Optional.empty, Optional[1]]",
                0.4992549999935604
        );
        optionalsHelper(
                8,
                "[1, 2, 3]",
                "[Optional[1], Optional[2], Optional[3], Optional[1], Optional[2], Optional[3], Optional[1]," +
                " Optional[2], Optional[3], Optional[1], Optional[2], Optional[3], Optional[1], Optional[2]," +
                " Optional[3], Optional[1], Optional[2], Optional[3], Optional[1], Optional[2]]",
                0.12480700000010415
        );
        optionalsHelper(
                32,
                "[1, 2, 3]",
                "[Optional[1], Optional[2], Optional[3], Optional[1], Optional[2], Optional[3], Optional[1]," +
                " Optional[2], Optional[3], Optional[1], Optional[2], Optional[3], Optional[1], Optional[2]," +
                " Optional[3], Optional[1], Optional[2], Optional[3], Optional[1], Optional[2]]",
                0.031218000000010567
        );
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
        aeqit(take(TINY_LIMIT, sample), output);
        aeq(meanOfIntegers(toList(map(x -> x.isPresent() ? 0 : 1, sample))), emptyFrequency);
        P.reset();
    }

    @Test
    public void testNullableOptionals() {
        nullableOptionals_helper(
                2,
                "[1]",
                "[NullableOptional[1], NullableOptional.empty, NullableOptional[1], NullableOptional[1]," +
                " NullableOptional[1], NullableOptional[1], NullableOptional[1], NullableOptional[1]," +
                " NullableOptional[1], NullableOptional.empty, NullableOptional[1], NullableOptional.empty," +
                " NullableOptional.empty, NullableOptional[1], NullableOptional.empty, NullableOptional.empty," +
                " NullableOptional[1], NullableOptional[1], NullableOptional.empty, NullableOptional[1]]",
                0.4992549999935604
        );
        nullableOptionals_helper(
                8,
                "[1]",
                "[NullableOptional[1], NullableOptional[1], NullableOptional[1], NullableOptional[1]," +
                " NullableOptional[1], NullableOptional[1], NullableOptional[1], NullableOptional[1]," +
                " NullableOptional[1], NullableOptional[1], NullableOptional[1], NullableOptional[1]," +
                " NullableOptional[1], NullableOptional[1], NullableOptional[1], NullableOptional[1]," +
                " NullableOptional[1], NullableOptional[1], NullableOptional[1], NullableOptional[1]]",
                0.12480700000010415
        );
        nullableOptionals_helper(
                32,
                "[1]",
                "[NullableOptional[1], NullableOptional[1], NullableOptional[1], NullableOptional[1]," +
                " NullableOptional[1], NullableOptional[1], NullableOptional[1], NullableOptional[1]," +
                " NullableOptional[1], NullableOptional[1], NullableOptional[1], NullableOptional[1]," +
                " NullableOptional[1], NullableOptional[1], NullableOptional[1], NullableOptional[1]," +
                " NullableOptional[1], NullableOptional[1], NullableOptional[1], NullableOptional[1]]",
                0.031218000000010567
        );
        nullableOptionals_helper(
                2,
                "[null, 2, 3]",
                "[NullableOptional[null], NullableOptional.empty, NullableOptional[2], NullableOptional[3]," +
                " NullableOptional[null], NullableOptional[2], NullableOptional[3], NullableOptional[null]," +
                " NullableOptional[2], NullableOptional.empty, NullableOptional[3], NullableOptional.empty," +
                " NullableOptional.empty, NullableOptional[null], NullableOptional.empty, NullableOptional.empty," +
                " NullableOptional[2], NullableOptional[3], NullableOptional.empty, NullableOptional[null]]",
                0.4992549999935604
        );
        nullableOptionals_helper(
                8,
                "[null, 2, 3]",
                "[NullableOptional[null], NullableOptional[2], NullableOptional[3], NullableOptional[null]," +
                " NullableOptional[2], NullableOptional[3], NullableOptional[null], NullableOptional[2]," +
                " NullableOptional[3], NullableOptional[null], NullableOptional[2], NullableOptional[3]," +
                " NullableOptional[null], NullableOptional[2], NullableOptional[3], NullableOptional[null]," +
                " NullableOptional[2], NullableOptional[3], NullableOptional[null], NullableOptional[2]]",
                0.12480700000010415
        );
        nullableOptionals_helper(
                32,
                "[null, 2, 3]",
                "[NullableOptional[null], NullableOptional[2], NullableOptional[3], NullableOptional[null]," +
                " NullableOptional[2], NullableOptional[3], NullableOptional[null], NullableOptional[2]," +
                " NullableOptional[3], NullableOptional[null], NullableOptional[2], NullableOptional[3]," +
                " NullableOptional[null], NullableOptional[2], NullableOptional[3], NullableOptional[null]," +
                " NullableOptional[2], NullableOptional[3], NullableOptional[null], NullableOptional[2]]",
                0.031218000000010567
        );
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
        aeqitLimit(
                TINY_LIMIT,
                P.dependentPairsInfinite(P.range(1, 5), i -> P.strings(i, charsToString(range('a', 'z')))),
                "[(2, ds), (4, rhvt), (1, v), (2, wv), (5, kpzex), (3, hje), (4, mfse), (1, d), (3, fpo), (3, tgw)," +
                " (1, m), (1, y), (1, o), (3, dpl), (1, j), (5, sofgp), (4, pttf), (4, lszp), (2, dr), (3, fvx), ...]"
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

    private static void permutationsFinite_helper(
            @NotNull String input,
            @NotNull String output,
            @NotNull String topSampleCount
    ) {
        permutationsFinite_helper(readIntegerListWithNulls(input), output, topSampleCount);
    }

    private static void permutationsFinite_helper(
            @NotNull List<Integer> input,
            @NotNull String output,
            @NotNull String topSampleCount
    ) {
        List<List<Integer>> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.permutationsFinite(input)));
        aeqitLimit(TINY_LIMIT, sample, output);
        aeq(topSampleCount(DEFAULT_TOP_COUNT, sample), topSampleCount);
        P.reset();
    }

    @Test
    public void testPermutationsFinite() {
        permutationsFinite_helper(
                "[]",
                "[[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], ...]",
                "{[]=1000000}");
        permutationsFinite_helper(
                "[5]",
                "[[5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5]," +
                " [5], ...]",
                "{[5]=1000000}");
        permutationsFinite_helper(
                "[1, 2]",
                "[[2, 1], [1, 2], [2, 1], [2, 1], [2, 1], [2, 1], [2, 1], [2, 1], [2, 1], [1, 2], [2, 1], [1, 2]," +
                " [1, 2], [2, 1], [1, 2], [1, 2], [2, 1], [2, 1], [1, 2], [2, 1], ...]",
                "{[2, 1]=500745, [1, 2]=499255}");
        permutationsFinite_helper(
                "[1, 2, 3]",
                "[[2, 1, 3], [2, 3, 1], [2, 3, 1], [2, 3, 1], [3, 1, 2], [1, 2, 3], [3, 2, 1], [2, 3, 1], [3, 1, 2]," +
                " [3, 1, 2], [1, 3, 2], [2, 1, 3], [1, 3, 2], [2, 1, 3], [3, 2, 1], [2, 1, 3], [2, 1, 3], [1, 2, 3]," +
                " [1, 2, 3], [3, 2, 1], ...]",
                "{[2, 3, 1]=167387, [3, 2, 1]=167243, [1, 3, 2]=166538, [1, 2, 3]=166496, [3, 1, 2]=166232," +
                " [2, 1, 3]=166104}");
        permutationsFinite_helper(
                "[1, 2, 3, 4]",
                "[[2, 4, 1, 3], [2, 3, 4, 1], [2, 3, 1, 4], [2, 1, 3, 4], [4, 1, 3, 2], [2, 4, 1, 3], [3, 1, 2, 4]," +
                " [4, 3, 2, 1], [1, 3, 2, 4], [3, 4, 2, 1], [3, 1, 2, 4], [1, 4, 3, 2], [1, 4, 3, 2], [4, 3, 1, 2]," +
                " [2, 1, 3, 4], [3, 4, 2, 1], [4, 3, 2, 1], [4, 1, 3, 2], [4, 2, 1, 3], [3, 4, 1, 2], ...]",
                "{[4, 2, 3, 1]=42026, [2, 3, 1, 4]=42012, [4, 1, 3, 2]=41883, [1, 4, 3, 2]=41846," +
                " [3, 2, 4, 1]=41820, [4, 3, 1, 2]=41782, [3, 1, 4, 2]=41776, [3, 4, 1, 2]=41771," +
                " [2, 1, 3, 4]=41764, [4, 3, 2, 1]=41745}");
        permutationsFinite_helper(
                "[1, 2, 2, 4]",
                "[[2, 4, 1, 2], [2, 2, 4, 1], [2, 2, 1, 4], [2, 1, 2, 4], [4, 1, 2, 2], [2, 4, 1, 2], [2, 1, 2, 4]," +
                " [4, 2, 2, 1], [1, 2, 2, 4], [2, 4, 2, 1], [2, 1, 2, 4], [1, 4, 2, 2], [1, 4, 2, 2], [4, 2, 1, 2]," +
                " [2, 1, 2, 4], [2, 4, 2, 1], [4, 2, 2, 1], [4, 1, 2, 2], [4, 2, 1, 2], [2, 4, 1, 2], ...]",
                "{[4, 2, 2, 1]=83771, [2, 2, 1, 4]=83554, [2, 2, 4, 1]=83502, [2, 4, 1, 2]=83498," +
                " [4, 1, 2, 2]=83476, [1, 4, 2, 2]=83417, [2, 1, 4, 2]=83341, [4, 2, 1, 2]=83271," +
                " [2, 4, 2, 1]=83193, [2, 1, 2, 4]=83115}");
        permutationsFinite_helper(
                "[2, 2, 2, 2]",
                "[[2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2]," +
                " [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2]," +
                " [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], ...]",
                "{[2, 2, 2, 2]=1000000}");
        permutationsFinite_helper(
                "[3, 1, 4, 1]",
                "[[1, 1, 3, 4], [1, 4, 1, 3], [1, 4, 3, 1], [1, 3, 4, 1], [1, 3, 4, 1], [1, 1, 3, 4], [4, 3, 1, 1]," +
                " [1, 4, 1, 3], [3, 4, 1, 1], [4, 1, 1, 3], [4, 3, 1, 1], [3, 1, 4, 1], [3, 1, 4, 1], [1, 4, 3, 1]," +
                " [1, 3, 4, 1], [4, 1, 1, 3], [1, 4, 1, 3], [1, 3, 4, 1], [1, 1, 3, 4], [4, 1, 3, 1], ...]",
                "{[1, 4, 3, 1]=83794, [1, 1, 4, 3]=83659, [1, 3, 4, 1]=83647, [1, 4, 1, 3]=83427," +
                " [4, 1, 1, 3]=83380, [3, 1, 4, 1]=83325, [4, 1, 3, 1]=83313, [1, 1, 3, 4]=83216," +
                " [1, 3, 1, 4]=83158, [4, 3, 1, 1]=83127}");
        permutationsFinite_helper(
                "[3, 1, null, 1]",
                "[[1, 1, 3, null], [1, null, 1, 3], [1, null, 3, 1], [1, 3, null, 1], [1, 3, null, 1]," +
                " [1, 1, 3, null], [null, 3, 1, 1], [1, null, 1, 3], [3, null, 1, 1], [null, 1, 1, 3]," +
                " [null, 3, 1, 1], [3, 1, null, 1], [3, 1, null, 1], [1, null, 3, 1], [1, 3, null, 1]," +
                " [null, 1, 1, 3], [1, null, 1, 3], [1, 3, null, 1], [1, 1, 3, null], [null, 1, 3, 1], ...]",
                "{[1, null, 3, 1]=83794, [1, 1, null, 3]=83659, [1, 3, null, 1]=83647, [1, null, 1, 3]=83427," +
                " [null, 1, 1, 3]=83380, [3, 1, null, 1]=83325, [null, 1, 3, 1]=83313, [1, 1, 3, null]=83216," +
                " [1, 3, 1, null]=83158, [null, 3, 1, 1]=83127}");
        permutationsFinite_helper(
                toList(IterableUtils.range(1, 10)),
                "[[10, 4, 1, 9, 8, 7, 5, 2, 3, 6], [7, 3, 1, 10, 2, 5, 4, 6, 8, 9], [3, 6, 2, 9, 4, 1, 10, 5, 8, 7]," +
                " [3, 8, 2, 6, 10, 1, 7, 5, 9, 4], [5, 4, 10, 1, 6, 3, 9, 2, 8, 7], [7, 1, 6, 2, 10, 9, 3, 8, 5, 4]," +
                " [2, 8, 5, 10, 3, 1, 4, 6, 9, 7], [5, 8, 4, 6, 2, 1, 7, 10, 3, 9], [3, 9, 2, 10, 4, 1, 6, 8, 7, 5]," +
                " [7, 2, 3, 1, 8, 10, 6, 5, 9, 4], [4, 8, 9, 7, 5, 2, 3, 6, 1, 10], [9, 2, 1, 5, 3, 7, 6, 4, 10, 8]," +
                " [3, 4, 9, 5, 10, 7, 6, 8, 2, 1], [9, 6, 4, 10, 5, 2, 3, 8, 1, 7], [4, 2, 9, 1, 6, 5, 3, 7, 10, 8]," +
                " [3, 1, 7, 5, 8, 9, 4, 6, 2, 10], [9, 8, 2, 6, 4, 5, 10, 7, 3, 1], [9, 2, 7, 3, 5, 10, 1, 6, 4, 8]," +
                " [10, 3, 2, 1, 6, 7, 8, 4, 9, 5], [3, 6, 2, 1, 10, 8, 9, 5, 7, 4], ...]",
                "{[7, 4, 2, 6, 9, 3, 1, 5, 10, 8]=6, [10, 9, 5, 3, 8, 1, 7, 2, 6, 4]=5," +
                " [5, 8, 1, 10, 6, 3, 9, 4, 7, 2]=5, [3, 9, 6, 4, 1, 10, 5, 7, 8, 2]=5," +
                " [4, 1, 6, 5, 8, 10, 3, 7, 2, 9]=5, [8, 5, 6, 2, 7, 9, 4, 3, 1, 10]=5," +
                " [1, 3, 4, 6, 2, 5, 9, 10, 8, 7]=5, [4, 5, 6, 7, 1, 10, 3, 8, 2, 9]=5," +
                " [5, 3, 6, 2, 8, 10, 1, 9, 7, 4]=5, [3, 10, 4, 7, 8, 9, 1, 5, 6, 2]=5}");
    }

    private static void stringPermutations_helper(
            @NotNull String input,
            @NotNull String output,
            @NotNull String topSampleCount
    ) {
        List<String> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.stringPermutations(input)));
        aeqitLimit(TINY_LIMIT, sample, output);
        aeq(topSampleCount(DEFAULT_TOP_COUNT, sample), topSampleCount);
        P.reset();
    }

    @Test
    public void testStringPermutations() {
        stringPermutations_helper("", "[, , , , , , , , , , , , , , , , , , , , ...]", "{=1000000}");
        stringPermutations_helper(
                "a",
                "[a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, ...]",
                "{a=1000000}");
        stringPermutations_helper(
                "abc",
                "[bac, bca, bca, bca, cab, abc, cba, bca, cab, cab, acb, bac, acb, bac, cba, bac, bac, abc, abc," +
                " cba, ...]",
                "{bca=167387, cba=167243, acb=166538, abc=166496, cab=166232, bac=166104}");
        stringPermutations_helper(
                "foo",
                "[ofo, oof, oof, oof, ofo, foo, oof, oof, ofo, ofo, foo, ofo, foo, ofo, oof, ofo, ofo, foo, foo," +
                " oof, ...]",
                "{oof=334630, foo=333034, ofo=332336}");
        stringPermutations_helper(
                "hello",
                "[elhol, elloh, eholl, lhoel, lheol, oellh, lleho, leolh, olhle, ellho, loehl, lohle, lhloe, lehlo," +
                " llheo, olleh, elohl, loehl, lhleo, helol, ...]",
                "{elolh=16971, lehlo=16937, lhloe=16931, llhoe=16917, leohl=16876, lleoh=16866, ollhe=16835," +
                " olhel=16828, lleho=16806, leolh=16802}");
        stringPermutations_helper(
                "Mississippi",
                "[psissisiipM, iMpssissipi, Mpipsiiisss, ipsisiipssM, iiissMpspsi, iiipsMispss, psiisiMspsi," +
                " sisMipiissp, siisspipiMs, piiMsssisip, ssMiipisspi, piisiiMssps, Mispspsiisi, iisssMisppi," +
                " sspsspMiiii, sipssiMspii, sipipissiMs, iissipisMps, isipiMsssip, siMipiipsss, ...]",
                "{iipssMiissp=54, iisMpissips=52, iisMsspiips=52, ssispiiMpis=51, spMsisiipsi=51, iMspiipssis=51," +
                " ssisMppiiis=50, sMsipssiipi=50, spisiiiMpss=50, sipMspsiisi=50}");
    }

    private static void prefixPermutations_helper(
            int scale,
            @NotNull String input,
            @NotNull String output,
            @NotNull String topSampleCount
    ) {
        prefixPermutations_helper(scale, readIntegerListWithNulls(input), output, topSampleCount);
    }

    private static void prefixPermutations_helper(
            int scale,
            @NotNull List<Integer> input,
            @NotNull String output,
            @NotNull String topSampleCount
    ) {
        List<List<Integer>> sample = toList(
                take(DEFAULT_SAMPLE_SIZE, map(IterableUtils::toList, P.withScale(scale).prefixPermutations(input)))
        );
        aeqitLimit(TINY_LIMIT, sample, output);
        aeq(topSampleCount(DEFAULT_TOP_COUNT, sample), topSampleCount);
        P.reset();
    }

    private static void prefixPermutations_helper(
            int scale,
            @NotNull Iterable<Integer> input,
            @NotNull String output,
            @NotNull String topSampleCount
    ) {
        List<String> sample = toList(
                take(DEFAULT_SAMPLE_SIZE, map(Testing::its, P.withScale(scale).prefixPermutations(input)))
        );
        aeqitLimit(TINY_LIMIT, sample, output);
        aeq(topSampleCount(DEFAULT_TOP_COUNT, sample), topSampleCount);
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
        prefixPermutations_helper(
                1,
                "[]",
                "[[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], ...]",
                "{[]=1000000}"
        );
        prefixPermutations_helper(
                2,
                "[]",
                "[[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], ...]",
                "{[]=1000000}"
        );
        prefixPermutations_helper(
                4,
                "[]",
                "[[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], ...]",
                "{[]=1000000}"
        );
        prefixPermutations_helper(
                1,
                "[5]",
                "[[5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5]," +
                " [5], ...]",
                "{[5]=1000000}"
        );
        prefixPermutations_helper(
                2,
                "[5]",
                "[[5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5]," +
                " [5], ...]",
                "{[5]=1000000}"
        );
        prefixPermutations_helper(
                4,
                "[5]",
                "[[5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5], [5]," +
                " [5], ...]",
                "{[5]=1000000}"
        );
        prefixPermutations_helper(
                1,
                "[1, 2]",
                "[[2, 1], [2, 1], [2, 1], [2, 1], [2, 1], [2, 1], [1, 2], [2, 1], [2, 1], [1, 2], [1, 2], [1, 2]," +
                " [2, 1], [1, 2], [1, 2], [1, 2], [1, 2], [2, 1], [1, 2], [2, 1], ...]",
                "{[1, 2]=500128, [2, 1]=499872}"
        );
        prefixPermutations_helper(
                2,
                "[1, 2]",
                "[[2, 1], [1, 2], [1, 2], [1, 2], [1, 2], [1, 2], [1, 2], [2, 1], [2, 1], [2, 1], [2, 1], [2, 1]," +
                " [1, 2], [2, 1], [1, 2], [1, 2], [1, 2], [2, 1], [1, 2], [2, 1], ...]",
                "{[2, 1]=500320, [1, 2]=499680}"
        );
        prefixPermutations_helper(
                4,
                "[1, 2]",
                "[[1, 2], [1, 2], [2, 1], [2, 1], [2, 1], [2, 1], [2, 1], [2, 1], [2, 1], [1, 2], [2, 1], [1, 2]," +
                " [1, 2], [1, 2], [1, 2], [2, 1], [1, 2], [1, 2], [1, 2], [2, 1], ...]",
                "{[1, 2]=500657, [2, 1]=499343}"
        );
        prefixPermutations_helper(
                1,
                "[1, 2, 3]",
                "[[2, 3, 1], [3, 1, 2], [1, 3, 2], [2, 1, 3], [3, 1, 2], [2, 3, 1], [3, 2, 1], [3, 2, 1], [1, 2, 3]," +
                " [1, 3, 2], [2, 3, 1], [1, 3, 2], [1, 2, 3], [2, 3, 1], [3, 1, 2], [3, 1, 2], [2, 1, 3], [1, 3, 2]," +
                " [3, 1, 2], [1, 2, 3], ...]",
                "{[1, 3, 2]=166994, [3, 1, 2]=166913, [2, 3, 1]=166781, [3, 2, 1]=166581, [1, 2, 3]=166397" +
                ", [2, 1, 3]=166334}"
        );
        prefixPermutations_helper(
                2,
                "[1, 2, 3]",
                "[[3, 2, 1], [3, 1, 2], [3, 2, 1], [3, 2, 1], [1, 3, 2], [2, 3, 1], [1, 2, 3], [3, 1, 2], [3, 1, 2]," +
                " [2, 3, 1], [3, 1, 2], [1, 3, 2], [2, 1, 3], [2, 3, 1], [1, 2, 3], [2, 1, 3], [2, 1, 3], [2, 3, 1]," +
                " [1, 2, 3], [2, 1, 3], ...]",
                "{[1, 2, 3]=167294, [2, 1, 3]=166661, [3, 2, 1]=166629, [2, 3, 1]=166619, [3, 1, 2]=166593" +
                ", [1, 3, 2]=166204}"
        );
        prefixPermutations_helper(
                4,
                "[1, 2, 3]",
                "[[2, 3, 1], [3, 1, 2], [2, 3, 1], [1, 2, 3], [3, 1, 2], [2, 3, 1], [2, 3, 1], [1, 2, 3], [1, 3, 2]," +
                " [3, 2, 1], [2, 1, 3], [1, 3, 2], [1, 3, 2], [1, 2, 3], [2, 1, 3], [3, 2, 1], [1, 2, 3], [2, 3, 1]," +
                " [2, 3, 1], [2, 3, 1], ...]",
                "{[3, 1, 2]=167085, [1, 3, 2]=167081, [2, 3, 1]=166636, [1, 2, 3]=166544, [2, 1, 3]=166445" +
                ", [3, 2, 1]=166209}"
        );
        prefixPermutations_helper(
                1,
                "[1, 2, 3, 4]",
                "[[2, 3, 1, 4], [4, 1, 2, 3], [1, 4, 3, 2], [4, 1, 2, 3], [2, 4, 3, 1], [2, 4, 3, 1], [1, 3, 2, 4]," +
                " [3, 1, 2, 4], [1, 3, 4, 2], [3, 1, 4, 2], [2, 1, 4, 3], [4, 3, 1, 2], [1, 4, 2, 3], [1, 3, 4, 2]," +
                " [1, 2, 4, 3], [2, 1, 4, 3], [3, 4, 1, 2], [1, 3, 4, 2], [2, 1, 3, 4], [1, 3, 2, 4], ...]",
                "{[3, 1, 2, 4]=42143, [3, 4, 1, 2]=41973, [2, 1, 4, 3]=41966, [1, 3, 4, 2]=41863," +
                " [4, 3, 2, 1]=41814, [2, 3, 1, 4]=41774, [2, 1, 3, 4]=41765, [3, 1, 4, 2]=41743," +
                " [4, 3, 1, 2]=41704, [4, 1, 2, 3]=41663}"
        );
        prefixPermutations_helper(
                2,
                "[1, 2, 3, 4]",
                "[[2, 1, 4, 3], [4, 2, 1, 3], [3, 4, 1, 2], [3, 1, 2, 4], [3, 2, 4, 1], [2, 3, 1, 4], [3, 4, 1, 2]," +
                " [1, 3, 2, 4], [3, 1, 4, 2], [1, 2, 4, 3], [2, 3, 4, 1], [3, 2, 4, 1], [4, 1, 2, 3], [4, 1, 3, 2]," +
                " [1, 3, 2, 4], [4, 3, 2, 1], [2, 1, 4, 3], [1, 2, 3, 4], [3, 2, 1, 4], [3, 4, 1, 2], ...]",
                "{[3, 2, 1, 4]=42069, [4, 2, 1, 3]=42028, [3, 4, 2, 1]=42017, [4, 1, 2, 3]=41899," +
                " [2, 1, 4, 3]=41847, [1, 3, 4, 2]=41824, [2, 4, 1, 3]=41808, [2, 4, 3, 1]=41726," +
                " [1, 2, 3, 4]=41711, [3, 4, 1, 2]=41707}"
        );
        prefixPermutations_helper(
                4,
                "[1, 2, 3, 4]",
                "[[2, 1, 4, 3], [3, 4, 1, 2], [4, 2, 1, 3], [1, 3, 2, 4], [1, 3, 4, 2], [3, 4, 1, 2], [4, 3, 1, 2]," +
                " [1, 4, 2, 3], [4, 2, 3, 1], [2, 3, 4, 1], [2, 4, 3, 1], [2, 3, 4, 1], [4, 2, 1, 3], [2, 3, 4, 1]," +
                " [3, 2, 1, 4], [2, 3, 1, 4], [1, 2, 4, 3], [4, 1, 2, 3], [3, 2, 4, 1], [4, 3, 1, 2], ...]",
                "{[2, 1, 4, 3]=41909, [1, 2, 3, 4]=41878, [1, 3, 2, 4]=41859, [3, 1, 4, 2]=41836," +
                " [2, 4, 1, 3]=41825, [1, 2, 4, 3]=41813, [3, 2, 1, 4]=41781, [2, 3, 4, 1]=41776," +
                " [4, 1, 2, 3]=41766, [4, 3, 1, 2]=41710}"
        );
        prefixPermutations_helper(
                1,
                "[1, 2, 2, 4]",
                "[[2, 2, 1, 4], [4, 1, 2, 2], [1, 4, 2, 2], [4, 1, 2, 2], [2, 4, 2, 1], [2, 4, 2, 1], [1, 2, 2, 4]," +
                " [2, 1, 2, 4], [1, 2, 4, 2], [2, 1, 4, 2], [2, 1, 4, 2], [4, 2, 1, 2], [1, 4, 2, 2], [1, 2, 4, 2]," +
                " [1, 2, 4, 2], [2, 1, 4, 2], [2, 4, 1, 2], [1, 2, 4, 2], [2, 1, 2, 4], [1, 2, 2, 4], ...]",
                "{[2, 1, 2, 4]=83908, [2, 1, 4, 2]=83709, [2, 4, 1, 2]=83591, [1, 2, 4, 2]=83469," +
                " [4, 2, 2, 1]=83413, [2, 2, 1, 4]=83293, [4, 2, 1, 2]=83185, [1, 2, 2, 4]=83168," +
                " [2, 2, 4, 1]=83139, [2, 4, 2, 1]=83052}"
        );
        prefixPermutations_helper(
                2,
                "[1, 2, 2, 4]",
                "[[2, 1, 4, 2], [4, 2, 1, 2], [2, 4, 1, 2], [2, 1, 2, 4], [2, 2, 4, 1], [2, 2, 1, 4], [2, 4, 1, 2]," +
                " [1, 2, 2, 4], [2, 1, 4, 2], [1, 2, 4, 2], [2, 2, 4, 1], [2, 2, 4, 1], [4, 1, 2, 2], [4, 1, 2, 2]," +
                " [1, 2, 2, 4], [4, 2, 2, 1], [2, 1, 4, 2], [1, 2, 2, 4], [2, 2, 1, 4], [2, 4, 1, 2], ...]",
                "{[2, 4, 2, 1]=83743, [2, 2, 1, 4]=83582, [4, 2, 1, 2]=83535, [2, 4, 1, 2]=83515," +
                " [4, 1, 2, 2]=83348, [2, 1, 4, 2]=83342, [2, 1, 2, 4]=83257, [1, 2, 2, 4]=83237," +
                " [1, 2, 4, 2]=83215, [1, 4, 2, 2]=83101}"
        );
        prefixPermutations_helper(
                4,
                "[1, 2, 2, 4]",
                "[[2, 1, 4, 2], [2, 4, 1, 2], [4, 2, 1, 2], [1, 2, 2, 4], [1, 2, 4, 2], [2, 4, 1, 2], [4, 2, 1, 2]," +
                " [1, 4, 2, 2], [4, 2, 2, 1], [2, 2, 4, 1], [2, 4, 2, 1], [2, 2, 4, 1], [4, 2, 1, 2], [2, 2, 4, 1]," +
                " [2, 2, 1, 4], [2, 2, 1, 4], [1, 2, 4, 2], [4, 1, 2, 2], [2, 2, 4, 1], [4, 2, 1, 2], ...]",
                "{[2, 1, 4, 2]=83745, [1, 2, 2, 4]=83737, [1, 2, 4, 2]=83458, [2, 2, 4, 1]=83446," +
                " [2, 2, 1, 4]=83442, [4, 2, 2, 1]=83360, [2, 4, 1, 2]=83245, [4, 2, 1, 2]=83230," +
                " [2, 4, 2, 1]=83181, [1, 4, 2, 2]=83133}"
        );
        prefixPermutations_helper(
                1,
                "[2, 2, 2, 2]",
                "[[2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2]," +
                " [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2]," +
                " [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], ...]",
                "{[2, 2, 2, 2]=1000000}"
        );
        prefixPermutations_helper(
                2,
                "[2, 2, 2, 2]",
                "[[2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2]," +
                " [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2]," +
                " [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], ...]",
                "{[2, 2, 2, 2]=1000000}"
        );
        prefixPermutations_helper(
                4,
                "[2, 2, 2, 2]",
                "[[2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2]," +
                " [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2]," +
                " [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2], ...]",
                "{[2, 2, 2, 2]=1000000}"
        );
        prefixPermutations_helper(
                1,
                "[3, 1, 4, 1]",
                "[[1, 4, 3, 1], [1, 3, 1, 4], [3, 1, 4, 1], [1, 3, 1, 4], [1, 1, 4, 3], [1, 1, 4, 3], [3, 4, 1, 1]," +
                " [4, 3, 1, 1], [3, 4, 1, 1], [4, 3, 1, 1], [1, 3, 1, 4], [1, 4, 3, 1], [3, 1, 1, 4], [3, 4, 1, 1]," +
                " [3, 1, 1, 4], [1, 3, 1, 4], [4, 1, 3, 1], [3, 4, 1, 1], [1, 3, 4, 1], [3, 4, 1, 1], ...]",
                "{[4, 3, 1, 1]=83886, [1, 3, 1, 4]=83629, [3, 4, 1, 1]=83508, [4, 1, 3, 1]=83492," +
                " [1, 4, 3, 1]=83478, [1, 4, 1, 3]=83387, [1, 3, 4, 1]=83135, [4, 1, 1, 3]=83122," +
                " [3, 1, 1, 4]=83104, [1, 1, 3, 4]=83099}"
        );
        prefixPermutations_helper(
                2,
                "[3, 1, 4, 1]",
                "[[1, 3, 1, 4], [1, 1, 3, 4], [4, 1, 3, 1], [4, 3, 1, 1], [4, 1, 1, 3], [1, 4, 3, 1], [4, 1, 3, 1]," +
                " [3, 4, 1, 1], [4, 3, 1, 1], [3, 1, 1, 4], [1, 4, 1, 3], [4, 1, 1, 3], [1, 3, 1, 4], [1, 3, 4, 1]," +
                " [3, 4, 1, 1], [1, 4, 1, 3], [1, 3, 1, 4], [3, 1, 4, 1], [4, 1, 3, 1], [4, 1, 3, 1], ...]",
                "{[1, 1, 3, 4]=83836, [4, 1, 3, 1]=83776, [1, 3, 1, 4]=83746, [4, 1, 1, 3]=83601," +
                " [3, 4, 1, 1]=83350, [3, 1, 4, 1]=83286, [1, 1, 4, 3]=83153, [4, 3, 1, 1]=83129," +
                " [1, 4, 1, 3]=83114, [1, 3, 4, 1]=83072}"
        );
        prefixPermutations_helper(
                4,
                "[3, 1, 4, 1]",
                "[[1, 3, 1, 4], [4, 1, 3, 1], [1, 1, 3, 4], [3, 4, 1, 1], [3, 4, 1, 1], [4, 1, 3, 1], [1, 4, 3, 1]," +
                " [3, 1, 1, 4], [1, 1, 4, 3], [1, 4, 1, 3], [1, 1, 4, 3], [1, 4, 1, 3], [1, 1, 3, 4], [1, 4, 1, 3]," +
                " [4, 1, 3, 1], [1, 4, 3, 1], [3, 1, 1, 4], [1, 3, 1, 4], [4, 1, 1, 3], [1, 4, 3, 1], ...]",
                "{[1, 3, 1, 4]=83675, [3, 4, 1, 1]=83504, [1, 4, 1, 3]=83477, [3, 1, 1, 4]=83475," +
                " [4, 3, 1, 1]=83395, [1, 4, 3, 1]=83371, [3, 1, 4, 1]=83349, [1, 1, 3, 4]=83345," +
                " [4, 1, 1, 3]=83297, [1, 1, 4, 3]=83213}"
        );
        prefixPermutations_helper(
                1,
                "[3, 1, null, 1]",
                "[[1, null, 3, 1], [1, 3, 1, null], [3, 1, null, 1], [1, 3, 1, null], [1, 1, null, 3]," +
                " [1, 1, null, 3], [3, null, 1, 1], [null, 3, 1, 1], [3, null, 1, 1], [null, 3, 1, 1]," +
                " [1, 3, 1, null], [1, null, 3, 1], [3, 1, 1, null], [3, null, 1, 1], [3, 1, 1, null]," +
                " [1, 3, 1, null], [null, 1, 3, 1], [3, null, 1, 1], [1, 3, null, 1], [3, null, 1, 1], ...]",
                "{[null, 3, 1, 1]=83886, [1, 3, 1, null]=83629, [3, null, 1, 1]=83508, [null, 1, 3, 1]=83492," +
                " [1, null, 3, 1]=83478, [1, null, 1, 3]=83387, [1, 3, null, 1]=83135, [null, 1, 1, 3]=83122," +
                " [3, 1, 1, null]=83104, [1, 1, 3, null]=83099}"
        );
        prefixPermutations_helper(
                2,
                "[3, 1, null, 1]",
                "[[1, 3, 1, null], [1, 1, 3, null], [null, 1, 3, 1], [null, 3, 1, 1], [null, 1, 1, 3]," +
                " [1, null, 3, 1], [null, 1, 3, 1], [3, null, 1, 1], [null, 3, 1, 1], [3, 1, 1, null]," +
                " [1, null, 1, 3], [null, 1, 1, 3], [1, 3, 1, null], [1, 3, null, 1], [3, null, 1, 1]," +
                " [1, null, 1, 3], [1, 3, 1, null], [3, 1, null, 1], [null, 1, 3, 1], [null, 1, 3, 1], ...]",
                "{[1, 1, 3, null]=83836, [null, 1, 3, 1]=83776, [1, 3, 1, null]=83746, [null, 1, 1, 3]=83601," +
                " [3, null, 1, 1]=83350, [3, 1, null, 1]=83286, [1, 1, null, 3]=83153, [null, 3, 1, 1]=83129," +
                " [1, null, 1, 3]=83114, [1, 3, null, 1]=83072}"
        );
        prefixPermutations_helper(
                4,
                "[3, 1, null, 1]",
                "[[1, 3, 1, null], [null, 1, 3, 1], [1, 1, 3, null], [3, null, 1, 1], [3, null, 1, 1]," +
                " [null, 1, 3, 1], [1, null, 3, 1], [3, 1, 1, null], [1, 1, null, 3], [1, null, 1, 3]," +
                " [1, 1, null, 3], [1, null, 1, 3], [1, 1, 3, null], [1, null, 1, 3], [null, 1, 3, 1]," +
                " [1, null, 3, 1], [3, 1, 1, null], [1, 3, 1, null], [null, 1, 1, 3], [1, null, 3, 1], ...]",
                "{[1, 3, 1, null]=83675, [3, null, 1, 1]=83504, [1, null, 1, 3]=83477, [3, 1, 1, null]=83475," +
                " [null, 3, 1, 1]=83395, [1, null, 3, 1]=83371, [3, 1, null, 1]=83349, [1, 1, 3, null]=83345," +
                " [null, 1, 1, 3]=83297, [1, 1, null, 3]=83213}"
        );
        prefixPermutations_helper(
                1,
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]",
                "[[2, 3, 1, 4, 5, 6, 7, 8, 9, 10], [2, 6, 1, 4, 3, 5, 7, 8, 9, 10], [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]," +
                " [2, 1, 3, 4, 5, 6, 7, 8, 9, 10], [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], [2, 1, 3, 4, 5, 6, 7, 8, 9, 10]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], ...]",
                "{[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]=3553, [2, 1, 3, 4, 5, 6, 7, 8, 9, 10]=298," +
                " [1, 3, 2, 4, 5, 6, 7, 8, 9, 10]=65, [2, 3, 1, 4, 5, 6, 7, 8, 9, 10]=62," +
                " [3, 1, 2, 4, 5, 6, 7, 8, 9, 10]=47, [3, 2, 1, 4, 5, 6, 7, 8, 9, 10]=41," +
                " [1, 4, 2, 3, 5, 6, 7, 8, 9, 10]=9, [3, 4, 1, 2, 5, 6, 7, 8, 9, 10]=9," +
                " [4, 3, 2, 1, 5, 6, 7, 8, 9, 10]=9, [4, 1, 3, 2, 5, 6, 7, 8, 9, 10]=9}"
        );
        prefixPermutations_helper(
                2,
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]",
                "[[2, 1, 4, 3, 5, 6, 7, 8, 9, 10], [3, 1, 2, 4, 5, 6, 7, 8, 9, 10], [2, 1, 3, 4, 5, 6, 7, 8, 9, 10]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], [5, 4, 2, 1, 3, 6, 7, 8, 9, 10], [3, 2, 1, 4, 5, 6, 7, 8, 9, 10]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], [3, 1, 2, 4, 5, 6, 7, 8, 9, 10], [3, 1, 4, 2, 5, 6, 7, 8, 9, 10]," +
                " [2, 1, 3, 4, 5, 6, 7, 8, 9, 10], [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], [3, 6, 7, 2, 4, 5, 1, 8, 9, 10]," +
                " [3, 4, 2, 1, 5, 6, 7, 8, 9, 10], [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], ...]",
                "{[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]=28, [2, 1, 3, 4, 5, 6, 7, 8, 9, 10]=6," +
                " [7, 5, 2, 10, 9, 8, 6, 3, 4, 1]=6, [1, 10, 4, 2, 6, 8, 7, 9, 3, 5]=6," +
                " [2, 5, 3, 6, 4, 7, 1, 9, 10, 8]=6, [10, 3, 1, 2, 6, 9, 7, 8, 4, 5]=6," +
                " [4, 5, 3, 10, 9, 8, 7, 6, 2, 1]=6, [8, 1, 7, 2, 10, 6, 9, 3, 4, 5]=6," +
                " [9, 7, 2, 10, 4, 6, 5, 8, 1, 3]=5, [9, 7, 6, 5, 2, 3, 10, 1, 8, 4]=5}"
        );
        prefixPermutations_helper(
                4,
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]",
                "[[2, 1, 4, 3, 5, 6, 7, 8, 9, 10], [7, 8, 5, 4, 10, 3, 1, 6, 9, 2], [10, 9, 1, 2, 8, 7, 6, 5, 4, 3]," +
                " [7, 4, 2, 10, 1, 6, 3, 5, 8, 9], [10, 8, 2, 7, 9, 5, 4, 1, 6, 3], [5, 9, 4, 8, 7, 6, 2, 1, 10, 3]," +
                " [5, 10, 9, 1, 6, 7, 8, 3, 2, 4], [2, 10, 7, 8, 6, 3, 5, 4, 1, 9], [1, 2, 9, 5, 4, 7, 10, 6, 3, 8]," +
                " [3, 6, 10, 8, 2, 9, 7, 5, 1, 4], [9, 4, 3, 2, 6, 7, 10, 1, 8, 5], [1, 2, 9, 3, 4, 5, 6, 8, 7, 10]," +
                " [8, 4, 1, 10, 3, 9, 7, 2, 5, 6], [5, 9, 1, 3, 4, 2, 6, 8, 10, 7], [6, 1, 5, 9, 8, 3, 4, 2, 10, 7]," +
                " [9, 2, 1, 10, 6, 8, 7, 5, 4, 3], [4, 3, 8, 2, 10, 9, 6, 1, 5, 7], [10, 2, 6, 9, 8, 3, 4, 1, 7, 5]," +
                " [3, 8, 10, 1, 5, 6, 4, 7, 9, 2], [10, 7, 2, 3, 4, 8, 5, 6, 1, 9], ...]",
                "{[2, 4, 5, 7, 8, 3, 10, 9, 6, 1]=6, [4, 10, 2, 9, 7, 3, 1, 5, 6, 8]=5," +
                " [10, 8, 3, 2, 4, 6, 9, 7, 5, 1]=5, [9, 4, 8, 10, 5, 1, 6, 7, 3, 2]=5," +
                " [9, 7, 5, 2, 10, 8, 1, 6, 4, 3]=5, [2, 3, 10, 1, 9, 6, 8, 7, 4, 5]=5," +
                " [9, 2, 7, 10, 4, 8, 5, 6, 1, 3]=5, [8, 9, 2, 7, 5, 10, 1, 4, 6, 3]=5," +
                " [2, 4, 7, 1, 8, 6, 10, 9, 5, 3]=5, [3, 5, 2, 9, 7, 8, 1, 6, 4, 10]=5}"
        );
        prefixPermutations_helper(
                1,
                ExhaustiveProvider.INSTANCE.positiveIntegers(),
                "[[2, 3, 1, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [2, 6, 1, 4, 3, 5, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [2, 1, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [2, 1, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...], ...]",
                "{[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]=824351," +
                " [2, 1, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]=74171," +
                " [3, 1, 2, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]=11916," +
                " [1, 3, 2, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]=11875," +
                " [3, 2, 1, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]=11874," +
                " [2, 3, 1, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]=11862," +
                " [3, 4, 1, 2, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]=1485," +
                " [4, 3, 1, 2, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]=1481," +
                " [1, 4, 3, 2, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]=1474" +
                ", [3, 2, 4, 1, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]=1469}"
        );
        prefixPermutations_helper(
                2,
                ExhaustiveProvider.INSTANCE.positiveIntegers(),
                "[[2, 1, 4, 3, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [3, 1, 2, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [2, 1, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [5, 4, 2, 1, 3, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [3, 2, 1, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [3, 1, 2, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [3, 1, 4, 2, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [2, 1, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [3, 6, 7, 2, 4, 5, 1, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [3, 4, 2, 1, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...], ...]",
                "{[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]=648981," +
                " [2, 1, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]=93409," +
                " [2, 3, 1, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]=19809," +
                " [3, 2, 1, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]=19717," +
                " [3, 1, 2, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]=19627," +
                " [1, 3, 2, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]=19442," +
                " [3, 1, 4, 2, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]=3322," +
                " [4, 2, 1, 3, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]=3273," +
                " [3, 4, 1, 2, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]=3264" +
                ", [2, 1, 4, 3, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]=3260}"
        );
        prefixPermutations_helper(
                4,
                ExhaustiveProvider.INSTANCE.positiveIntegers(),
                "[[2, 1, 4, 3, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [8, 7, 5, 10, 15, 16, 2, 6, 13, 1, 9, 11, 14, 3, 12, 4, 17, 18, 19, 20, ...]," +
                " [3, 1, 2, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [3, 9, 7, 10, 2, 12, 16, 4, 1, 13, 8, 6, 15, 5, 14, 11, 17, 18, 19, 20, ...]," +
                " [1, 5, 6, 8, 9, 10, 3, 7, 2, 4, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 4, 2, 3, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [5, 8, 1, 3, 6, 7, 4, 2, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [4, 2, 1, 3, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 5, 3, 4, 2, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [2, 1, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [4, 2, 1, 5, 3, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 9, 6, 2, 8, 3, 5, 4, 7, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]," +
                " [1, 6, 2, 4, 5, 3, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...], ...]",
                "{[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]=445737," +
                " [2, 1, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]=83952," +
                " [2, 3, 1, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]=21276," +
                " [1, 3, 2, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]=21147," +
                " [3, 1, 2, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]=21128," +
                " [3, 2, 1, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]=21069," +
                " [2, 1, 4, 3, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]=4172," +
                " [4, 1, 2, 3, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]=4121," +
                " [3, 2, 4, 1, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]=4098" +
                ", [3, 4, 1, 2, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, ...]=4060}"
        );
        prefixPermutations_helper(
                1,
                repeat(1),
                "[[1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...], ...]",
                "{[1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]=1000000}"
        );
        prefixPermutations_helper(
                2,
                repeat(1),
                "[[1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...], ...]",
                "{[1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]=1000000}"
        );
        prefixPermutations_helper(
                4,
                repeat(1),
                "[[1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]," +
                " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...], ...]",
                "{[1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, ...]=1000000}"
        );
        prefixPermutations_fail_helper(0, "[1, 2, 3]");
        prefixPermutations_fail_helper(-1, "[1, 2, 3]");
    }

    private static void strings_int_Iterable_helper(
            int size,
            @NotNull String input,
            @NotNull String output,
            @NotNull String topSampleCount
    ) {
        List<String> sample = toList(take(DEFAULT_SAMPLE_SIZE, P.strings(size, input)));
        aeqitLimit(TINY_LIMIT, sample, output);
        aeq(topSampleCount(DEFAULT_TOP_COUNT, sample), topSampleCount);
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
        strings_int_Iterable_helper(0, "a", "[, , , , , , , , , , , , , , , , , , , , ...]", "{=1000000}");
        strings_int_Iterable_helper(
                1,
                "a",
                "[a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, ...]",
                "{a=1000000}"
        );
        strings_int_Iterable_helper(
                2,
                "a",
                "[aa, aa, aa, aa, aa, aa, aa, aa, aa, aa, aa, aa, aa, aa, aa, aa, aa, aa, aa, aa, ...]",
                "{aa=1000000}"
        );
        strings_int_Iterable_helper(
                3,
                "a",
                "[aaa, aaa, aaa, aaa, aaa, aaa, aaa, aaa, aaa, aaa, aaa, aaa, aaa, aaa, aaa, aaa, aaa, aaa, aaa," +
                " aaa, ...]",
                "{aaa=1000000}"
        );
        strings_int_Iterable_helper(0, "abc", "[, , , , , , , , , , , , , , , , , , , , ...]", "{=1000000}");
        strings_int_Iterable_helper(
                1,
                "abc",
                "[b, b, c, b, a, b, b, b, b, b, a, b, b, c, b, c, a, c, b, b, ...]",
                "{c=333615, b=333313, a=333072}"
        );
        strings_int_Iterable_helper(
                2,
                "abc",
                "[bb, cb, ab, bb, bb, ab, bc, bc, ac, bb, ca, cb, ac, bc, cb, aa, ca, bc, ab, ac, ...]",
                "{cb=111390, bc=111322, ca=111219, aa=111121, ba=111088, cc=111028, ab=111012, bb=110943, ac=110877}"
        );
        strings_int_Iterable_helper(
                3,
                "abc",
                "[bbc, bab, bbb, bab, bcb, cac, bbc, acb, acb, ccb, aac, abc, aba, cbc, acc, cbc, cab, acc, aac," +
                " aac, ...]",
                "{aaa=37441, bbb=37355, cac=37327, bcb=37306, cbc=37273, cba=37236, cca=37231, ccc=37214, bca=37158," +
                " aab=37136}"
        );
        strings_int_Iterable_helper(0, "abbc", "[, , , , , , , , , , , , , , , , , , , , ...]", "{=1000000}");
        strings_int_Iterable_helper(
                1,
                "abbc",
                "[b, b, c, b, b, c, a, b, b, c, c, b, c, b, c, b, a, b, b, b, ...]",
                "{b=499640, c=250298, a=250062}"
        );
        strings_int_Iterable_helper(
                2,
                "abbc",
                "[bb, cb, bc, ab, bc, cb, cb, cb, ab, bb, cb, ba, cb, bc, bb, ab, cb, ac, cb, cb, ...]",
                "{bb=250376, bc=124904, cb=124818, ab=124686, ba=124685, ac=62739, cc=62694, aa=62656, ca=62442}"
        );
        strings_int_Iterable_helper(
                3,
                "abbc",
                "[bbc, bbc, abb, ccb, cbc, bab, bbc, bba, cbb, cbb, abc, bac, cbc, bbb, cba, abc, abb, aba, ccb," +
                " bcb, ...]",
                "{bbb=125202, cbb=62727, bba=62625, bbc=62606, abb=62481, bab=62386, bcb=62173, acb=31470," +
                " bcc=31464, cbc=31441}"
        );
        strings_int_Iterable_helper(0, "Mississippi", "[, , , , , , , , , , , , , , , , , , , , ...]", "{=1000000}");
        strings_int_Iterable_helper(
                1,
                "Mississippi",
                "[p, p, s, s, s, p, s, s, i, i, s, s, s, p, s, i, s, i, s, s, ...]",
                "{s=363979, i=363703, p=181581, M=90737}"
        );
        strings_int_Iterable_helper(
                2,
                "Mississippi",
                "[pp, ss, sp, ss, ii, ss, sp, si, si, ss, si, pi, is, si, pi, ss, ss, is, Ms, is, ...]",
                "{ss=132606, si=132473, is=132392, ii=131960, ps=66316, sp=66221, ip=66050, pi=65612, Mi=33235," +
                " pp=33071}"
        );
        strings_int_Iterable_helper(
                3,
                "Mississippi",
                "[pps, ssp, ssi, iss, sps, isi, sss, ipi, iss, ipi, sss, sis, Msi, sss, ssi, sip, iMp, ipp, ips," +
                " ssi, ...]",
                "{sss=48687, sis=48297, ssi=48283, iis=48220, sii=48107, iii=48048, iss=47940, isi=47797, psi=24274," +
                " pss=24260}"
        );
        strings_int_Iterable_fail_helper(0, "");
        strings_int_Iterable_fail_helper(-1, "abc");
    }

    private static void strings_int_helper(int size, @NotNull String output, @NotNull String topSampleCount) {
        List<String> sample = toList(take(DEFAULT_SAMPLE_SIZE, map(Testing::nicePrint, P.strings(size))));
        aeqitLimit(TINY_LIMIT, sample, output);
        aeq(topSampleCount(DEFAULT_TOP_COUNT, sample), topSampleCount);
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
        strings_int_helper(0, "[, , , , , , , , , , , , , , , , , , , , ...]", "{=1000000}");
        strings_int_helper(
                1,
                "[嘩, 퇉, 馃, \\u2df2, ε, 䊿, \\u2538, \\u31e5, 髽, 肣, \\uf6ff, ﳑ, 赧, \\ue215, \\u17f3, \\udd75, 껸," +
                " \\udd15, 몱, ﲦ, ...]",
                "{\\uf1b2=36, 撢=35, આ=34, 퉃=34, \\27=33, 韖=32, 㖒=32, 膗=31, 㗞=31, 䕦=31}"
        );
        strings_int_helper(
                2,
                "[嘩퇉, 馃\\u2df2, ε䊿, \\u2538\\u31e5, 髽肣, \\uf6ffﳑ, 赧\\ue215, \\u17f3\\udd75, 껸\\udd15, 몱ﲦ, 䯏ϡ," +
                " 罖\\u19dc, 刿ㄾ, 䲵箿, 偵恾, \u1B1CK, 㵏ꏹ, 缄㩷, \u2D3F읾, 纫\\ufe2d, ...]",
                "{\\uf310틺=2, 緑\\ue709=2, 㑰\\uf5be=2, \\ue429菧=2, \\uf480\\u23c0=2, \u2CC8고=2, 㜛땹=2," +
                " \\ue283捿=2, \\ua8ed\u2C04=2, 楮譂=2}"
        );
        strings_int_helper(
                3,
                "[嘩퇉馃, \\u2df2ε䊿, \\u2538\\u31e5髽, 肣\\uf6ffﳑ, 赧\\ue215\\u17f3, \\udd75껸\\udd15, 몱ﲦ䯏," +
                " ϡ罖\\u19dc, 刿ㄾ䲵, 箿偵恾, \u1B1CK㵏, ꏹ缄㩷, \u2D3F읾纫, \\ufe2d㗂䝲, \\uf207갩힜, 坤琖\\u2a43," +
                " 퉌\\uea45\\ue352, 蕤餥䉀, \\u2b63\\uf637鸂, 鸅误輮, ...]",
                "{嘩퇉馃=1, \\u2df2ε䊿=1, \\u2538\\u31e5髽=1, 肣\\uf6ffﳑ=1, 赧\\ue215\\u17f3=1, \\udd75껸\\udd15=1," +
                " 몱ﲦ䯏=1, ϡ罖\\u19dc=1, 刿ㄾ䲵=1, 箿偵恾=1}"
        );
        strings_int_fail_helper(-1);
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
