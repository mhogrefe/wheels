package mho.wheels.random;

import org.jetbrains.annotations.NotNull;

import java.util.*;

import static org.junit.Assert.assertEquals;

// Implementation by Bob Jenkins
public class IsaacPRNG {
    private static final @NotNull int[] EXAMPLE_SEED = new int[] {
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
    };

    private static final int SIZE_BITS = 8;          // log of size of result[] and state[]
    public static final int SIZE = 1 << SIZE_BITS;   // size of result[] and state[]
    private static final int MASK = (SIZE - 1) << 2; // for pseudorandom lookup
    private int count;                               // count through the results in result[]
    private @NotNull int[] result;                   // the results given to the user
    private @NotNull int[] state;                    // the internal state
    private int a;                                   // accumulator
    private int b;                                   // the last result
    private int c;                                   // counter, guarantees cycle is at least 2^40

    // Seed from system time
    public IsaacPRNG() {
        state = new int[SIZE];
        result = new int[SIZE];
        Random random = new Random();
        for (int i = 0; i < SIZE; i++) {
            result[i] = random.nextInt();
        }
        initialize();
    }

    // Equivalent to randinit(ctx, TRUE) after putting seed in randctx in C
    private IsaacPRNG(@NotNull int[] seed) {
        state = new int[SIZE];
        result = new int[SIZE];
        System.arraycopy(seed, 0, result, 0, seed.length);
        initialize();
    }

    public IsaacPRNG(@NotNull List<Integer> seed) {
        state = new int[SIZE];
        result = new int[SIZE];
        for (int i = 0; i < seed.size(); i++) {
            result[i] = seed.get(i);
        }
        initialize();
    }

    public void setSeed(@NotNull List<Integer> seed) {
        a = 0;
        b = 0;
        c = 0;
        count = 0;
        state = new int[SIZE];
        result = new int[SIZE];
        for (int i = 0; i < seed.size(); i++) {
            result[i] = seed.get(i);
        }
        initialize();
    }

    public static @NotNull IsaacPRNG example() {
        return new IsaacPRNG(EXAMPLE_SEED);
    }

    public @NotNull IsaacPRNG copy() {
        IsaacPRNG copy = new IsaacPRNG(Collections.emptyList());
        copy.a = a;
        copy.b = b;
        copy.c = c;
        copy.count = count;
        copy.state = new int[state.length];
        System.arraycopy(state, 0, copy.state, 0, state.length);
        copy.result = new int[result.length];
        System.arraycopy(result, 0, copy.result, 0, result.length);
        return copy;
    }

    public long getId() {
        List<Integer> newSeed = new ArrayList<>();
        for (int i : state) {
            newSeed.add(i);
        }
        newSeed.set(0, a);
        newSeed.set(1, b);
        newSeed.set(2, c);
        newSeed.set(3, count);
        IsaacPRNG temp = new IsaacPRNG(newSeed);
        int a = temp.nextInt();
        int b = temp.nextInt();
        return (long) a << 32 | b & 0xffffffffL;
    }

    // Generate 256 results. This is a fast (not small) implementation.
    private void generate() {
        int i = 0;
        int j = SIZE / 2;
        b += ++c;
        while (i < SIZE / 2) {
            int x = state[i];
            a ^= a << 13;
            a += state[j++];
            int y = state[(x & MASK) >> 2] + a + b;
            state[i] = y;
            b = state[((y >> SIZE_BITS) & MASK) >> 2] + x;
            result[i++] = b;

            x = state[i];
            a ^= a >>> 6;
            a += state[j++];
            y = state[(x & MASK) >> 2] + a + b;
            state[i] = y;
            b = state[((y >> SIZE_BITS) & MASK) >> 2] + x;
            result[i++] = b;

            x = state[i];
            a ^= a << 2;
            a += state[j++];
            y = state[(x & MASK) >> 2] + a + b;
            state[i] = y;
            b = state[((y >> SIZE_BITS) & MASK) >> 2] + x;
            result[i++] = b;

            x = state[i];
            a ^= a >>> 16;
            a += state[j++];
            y = state[(x & MASK) >> 2] + a + b;
            state[i] = y;
            b = state[((y >> SIZE_BITS) & MASK) >> 2] + x;
            result[i++] = b;
        }

        j = 0;
        while (j < SIZE / 2) {
            int x = state[i];
            a ^= a << 13;
            a += state[j++];
            int y = state[(x & MASK) >> 2] + a + b;
            state[i] = y;
            b = state[((y >> SIZE_BITS) & MASK) >> 2] + x;
            result[i++] = b;

            x = state[i];
            a ^= a >>> 6;
            a += state[j++];
            y = state[(x & MASK) >> 2] + a + b;
            state[i] = y;
            b = state[((y >> SIZE_BITS) & MASK) >> 2] + x;
            result[i++] = b;

            x = state[i];
            a ^= a << 2;
            a += state[j++];
            y = state[(x & MASK) >> 2] + a + b;
            state[i] = y;
            b = state[((y >> SIZE_BITS) & MASK) >> 2] + x;
            result[i++] = b;

            x = state[i];
            a ^= a >>> 16;
            a += state[j++];
            y = state[(x & MASK) >> 2] + a + b;
            state[i] = y;
            b = state[((y >> SIZE_BITS) & MASK) >> 2] + x;
            result[i++] = b;
        }
    }

    private void initialize() {
        int a = 0x9e3779b9; // the golden ratio
        int b = a;
        int c = a;
        int d = a;
        int e = a;
        int f = a;
        int g = a;
        int h = a;

        for (int i = 0; i < 4; i++) {
            a ^= b << 11;
            d += a;
            b += c;

            b ^= c >>> 2;
            e += b;
            c += d;

            c ^= d << 8;
            f += c;
            d += e;

            d ^= e >>> 16;
            g += d;
            e += f;

            e ^= f << 10;
            h += e;
            f += g;

            f ^= g >>> 4;
            a += f;
            g += h;

            g ^= h << 8;
            b += g;
            h += a;

            h ^= a >>> 9;
            c += h;
            a += b;
        }

        for (int i = 0; i < SIZE; i += 8) {              // fill in state[] with messy stuff
            a += result[i];
            b += result[i + 1];
            c += result[i + 2];
            d += result[i + 3];
            e += result[i + 4];
            f += result[i + 5];
            g += result[i + 6];
            h += result[i + 7];

            a ^= b << 11;
            d += a;
            b += c;

            b ^= c >>> 2;
            e += b;
            c += d;

            c ^= d << 8;
            f += c;
            d += e;

            d ^= e >>> 16;
            g += d;
            e += f;

            e ^= f << 10;
            h += e;
            f += g;

            f ^= g >>> 4;
            a += f;
            g += h;

            g ^= h << 8;
            b += g;
            h += a;

            h ^= a >>> 9;
            c += h;
            a += b;

            state[i]     = a;
            state[i + 1] = b;
            state[i + 2] = c;
            state[i + 3] = d;
            state[i + 4] = e;
            state[i + 5] = f;
            state[i + 6] = g;
            state[i + 7] = h;
        }

        // second pass makes all of seed affect all of state
        for (int i = 0; i < SIZE; i += 8) {
            a += state[i];
            b += state[i + 1];
            c += state[i + 2];
            d += state[i + 3];
            e += state[i + 4];
            f += state[i + 5];
            g += state[i + 6];
            h += state[i + 7];

            a ^= b << 11;
            d += a;
            b += c;

            b ^= c >>> 2;
            e += b;
            c += d;

            c ^= d << 8;
            f += c;
            d += e;

            d ^= e >>> 16;
            g += d;
            e += f;

            e ^= f << 10;
            h += e;
            f += g;

            f ^= g >>> 4;
            a += f;
            g += h;

            g ^= h << 8;
            b += g;
            h += a;

            h ^= a >>> 9;
            c += h;
            a += b;

            state[i]     = a;
            state[i + 1] = b;
            state[i + 2] = c;
            state[i + 3] = d;
            state[i + 4] = e;
            state[i + 5] = f;
            state[i + 6] = g;
            state[i + 7] = h;
        }

        generate();
        count = SIZE;
    }

    public int nextInt() {
        if (0 == count--) {
            generate();
            count = SIZE - 1;
        }
        return result[count];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IsaacPRNG prng = (IsaacPRNG) o;
        return count == prng.count && a == prng.a && b == prng.b && c == prng.c &&
                Arrays.equals(result, prng.result) && Arrays.equals(state, prng.state);
    }

    @Override
    public int hashCode() {
        int code = count;
        code = 31 * code + Arrays.hashCode(result);
        code = 31 * code + Arrays.hashCode(state);
        code = 31 * code + a;
        code = 31 * code + b;
        code = 31 * code + c;
        return code;
    }

    public @NotNull String toString() {
        return "IsaacPRNG[@" + getId() + "]";
    }

    public void validate() {
        assertEquals(toString(), result.length, SIZE);
        assertEquals(toString(), state.length, SIZE);
    }
}
