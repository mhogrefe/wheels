package mho.wheels.random;

import mho.wheels.testing.Testing;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.random.IsaacPRNG.*;
import static mho.wheels.testing.Testing.aeq;
import static mho.wheels.testing.Testing.testEqualsHelper;

public class IsaacPRNGTest {
    private static final @NotNull List<Integer> SEED_A = Arrays.asList(
            -1027965243, -2056247012, -1072102476, -1279902882,  1621187076,  1238055049, -2041183925,  1149834766, ///
             1291607170,  1263576008, -2077392242, -1691515947, -1888481802,   102246273,  2107597305,   183635157, ///
            -1797820015, -1009173658,   435730678, -1171329061,  1737151160,   995303757, -2141682822,  1049204054, ///
              788765829,  2064534678,  -723417974,   663746331,   435188521,   298245395, -1352510097, -1719963216, ///
             1403521661,  1013080843, -1729998204, -2057400506, -1068374983, -1410411087,  1861389545,  -984196079, ///
              154020395, -1872255712, -1636782345, -1807845802,   415551872,  1350508284,  1286504834, -1951215328, ///
                9148046,  -689308711,  1400526998,  2106237777,  -619044578,  -839178118,  1892184419,   521886964, ///
             1976607822,  1734447221,   578043900,  -312388994, -1163851335,  -167209977,   -80694485,  -524200183, ///
             1244956938,  2068776164,    21295846,   561907636,   212833259,  -426231953, -1589242242,  -197894809, ///
              575522844,   799931886,   -22763284,  1982412105, -2097647302,   989658749,   -40158325,    53030973, ///
            -1937392126, -1235318374, -1587141269,  1363595121,   -66625256,  -572597895, -1318449906,  1261938702, ///
              -63087073,  1156763325, -1357335155, -1441161155,  1438310086,  -274039201, -1566541273,   848765028, ///
              815451557,   653212570,  -494863872,    74824466,  1526179121,  -320225581, -2144658309,  1777784139, ///
            -1001378297, -1107826233,   169596106,    31987261,  -678654753,   602194210, -2144376169,   593802110, ///
             -824246689,   214011703,  1260701533, -1653214995,  -719737646, -1915045174, -1401280853,  -218507792, ///
             -845257257, -1353288950, -1357950494,   729851731,  -974714369,  1951761539,   -15461389,  -391380206, ///
             2091869827,   134667231, -1189828708,  1008925130,  1436177488,   222605073,  1763172968, -1000046580, ///
            -1916848477,  -410801952,  1563322860,  2120245889,  2092753879,  -119586718,  1669671527,  -579787753, ///
              871644917,    73829605,   984611326, -1583344220,   540423487, -1238486907,    46365747, -1853223274, ///
             -587218832,  1156993784,  -230973794,   -23122985,   894982134,  1935544060,  1189253957,  -361257985, ///
             1087097561, -1121038317,  2058535483,   -44583929, -1051378204,   -33989493,   -72347977,   975057894, ///
             1790416702,  -413641613, -1318270288,   552576869,  1615276199,  2111878265,  -150416100,  1208832465, ///
             -985429786,  2025898664, -1208813762, -1048259537,  1137297800, -2116697629,  -348159177,  1569454849, ///
              270141992, -1445254555,   723093367,   917710418,  1277421648,   896206368, -1969456389, -1638136645, ///
              732453153,  -190541027,  1342609696,  -732659338, -1645534570,   -87318578, -2137322397,   260301493, ///
            -1521342101,  -335682933, -1332620699,  -437341627, -1474225931,  -509136306,  1079900127,  1642926194, ///
              834862366,   -61789399,  -199986572,  1298774106,  1895751093, -1203285729, -1066550508, -1958100545, ///
             2139850668,   640038978,   410677718,  2052164419,   393744010,  1622809025,   849437551, -2014926842, ///
            -2110197789,  1827842630,  -263538460,  2064153422,  1425638804,  1189268242,   927626129,  1863420829, ///
            -1032317697, -1303082907,     2733593, -2117974845,  1226235633,  1404173344,  1101111119,    39760067, ///
            -1627774054, -1983873299,  -342251408, -1826680161,   123691236, -1793214049,  -883777055,  1411776752, ///
             -343754333,  1582291286,  -403235925, -1887850753, -1420066386,  2057770224, -1971167049,   306168777  ///
    );

    private static final @NotNull List<Integer> SEED_B = toList(replicate(SIZE, 0));

    private static final @NotNull List<Integer> SEED_C = toList(take(SIZE, Testing.EP.rangeUp(0)));

    private static final @NotNull List<Integer> NULL_SEED = toList(replicate(SIZE, null));

    @Test
    public void testConstructor() {
        new IsaacPRNG().validate();
    }

    private static void constructor_List_helper(@NotNull List<Integer> seed, @NotNull String output) {
        IsaacPRNG prng = new IsaacPRNG(seed);
        prng.validate();
        aeq(prng, output);
    }

    private static void constructor_List_fail_helper(@NotNull List<Integer> seed) {
        try {
            new IsaacPRNG(seed);
            Assert.fail();
        } catch (IllegalArgumentException | NullPointerException ignored) {}
    }

    @Test
    public void testConstructor_List() {
        constructor_List_helper(SEED_A, "IsaacPRNG[@3171466001973574424]");
        constructor_List_helper(SEED_B, "IsaacPRNG[@-7948823947390831374]");
        constructor_List_helper(SEED_C, "IsaacPRNG[@7281920748093454742]");

        constructor_List_fail_helper(Collections.emptyList());
        constructor_List_fail_helper(Collections.singletonList(1));
        constructor_List_fail_helper(NULL_SEED);
    }

    private static void setSeed_fail_helper(@NotNull IsaacPRNG prng, @NotNull List<Integer> seed) {
        try {
            prng.setSeed(seed);
            Assert.fail();
        } catch (IllegalArgumentException | NullPointerException ignored) {}
    }

    @Test
    public void testSetSeed() {
        IsaacPRNG a = new IsaacPRNG(SEED_A);
        IsaacPRNG b = new IsaacPRNG(SEED_B);
        IsaacPRNG c = new IsaacPRNG(SEED_C);

        a.setSeed(SEED_B);
        aeq(a, b);
        a.setSeed(SEED_C);
        aeq(a, c);
        a.nextInt();
        Assert.assertNotEquals(a, c);
        c.nextInt();
        aeq(a, c);

        setSeed_fail_helper(a, Collections.emptyList());
        setSeed_fail_helper(a, Collections.singletonList(1));
        setSeed_fail_helper(a, NULL_SEED);
    }

    @Test
    public void testExample() {
        aeq(example(), "IsaacPRNG[@3171466001973574424]");
    }

    @Test
    public void testCopy() {
        IsaacPRNG a1 = new IsaacPRNG(SEED_A);
        IsaacPRNG a2 = a1.copy();
        aeq(a1, a2);
        a1.nextInt();
        Assert.assertNotEquals(a1, a2);
        a2 = a1.copy();
        aeq(a1, a2);
    }

    private static void getId_helper(@NotNull IsaacPRNG input, long output) {
        aeq(input.getId(), output);
    }

    @Test
    public void testGetId() {
        IsaacPRNG a = new IsaacPRNG(SEED_A);
        getId_helper(a, 3171466001973574424L);
        a.nextInt();
        getId_helper(a, 2169744302903133081L);
        getId_helper(new IsaacPRNG(SEED_B), -7948823947390831374L);
        getId_helper(new IsaacPRNG(SEED_C), 7281920748093454742L);
    }

    private static void nextInt_helper(@NotNull IsaacPRNG input, int output) {
        aeq(input.nextInt(), output);
    }

    @Test
    public void testNextInt() {
        IsaacPRNG a = new IsaacPRNG(SEED_A);
        nextInt_helper(a, -1740315277);
        nextInt_helper(a, -1661427768);
        nextInt_helper(new IsaacPRNG(SEED_B), 405143795);
        nextInt_helper(new IsaacPRNG(SEED_C), -934296046);
    }

    @Test
    public void testEquals() {
        testEqualsHelper(
                Arrays.asList(new IsaacPRNG(SEED_A), new IsaacPRNG(SEED_B), new IsaacPRNG(SEED_C)),
                Arrays.asList(new IsaacPRNG(SEED_A), new IsaacPRNG(SEED_B), new IsaacPRNG(SEED_C))
        );
    }

    private static void hashCode_helper(@NotNull List<Integer> seed, int hashCode) {
        IsaacPRNG prng = new IsaacPRNG(seed);
        prng.validate();
        aeq(prng.hashCode(), hashCode);
    }

    @Test
    public void testHashCode() {
        hashCode_helper(SEED_A, 864032668);
        hashCode_helper(SEED_B, -975068690);
        hashCode_helper(SEED_C, 1043425877);
    }
}