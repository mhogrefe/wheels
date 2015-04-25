package mho.wheels.random;

import java.util.Random;

// Implementation by Bob Jenkins
public class IsaacPRNG {
    final static int SIZE_BITS = 8;          //log of size of rsl[] and mem[]
    final static int SIZE = 1 << SIZE_BITS;  // size of rsl[] and mem[]
    final static int MASK = (SIZE - 1) << 2; // for pseudorandom lookup
    int count;                               // count through the results in rsl[]
    int rsl[];                               // the results given to the user
    private int mem[];                       // the internal state
    private int a;                           // accumulator
    private int b;                           // the last result
    private int c;                           // counter, guarantees cycle is at least 2^40

    // Seed from system time
    public IsaacPRNG() {
        mem = new int[SIZE];
        rsl = new int[SIZE];
        Random random = new Random();
        for (int i = 0; i < SIZE; i++) {
            rsl[i] = random.nextInt();
        }
        initialize();
    }

    // Equivalent to randinit(ctx, TRUE) after putting seed in randctx in C
    public IsaacPRNG(int seed[]) {
        mem = new int[SIZE];
        rsl = new int[SIZE];
        System.arraycopy(seed, 0, rsl, 0, seed.length);
        initialize();
    }


    // Generate 256 results. This is a fast (not small) implementation.
    private void generate() {
        int i = 0;
        int j = SIZE / 2;
        b += ++c;
        while (i < SIZE / 2) {
            int x = mem[i];
            a ^= a << 13;
            a += mem[j++];
            int y = mem[(x & MASK) >> 2] + a + b;
            mem[i] = y;
            b = mem[((y >> SIZE_BITS) & MASK) >> 2] + x;
            rsl[i++] = b;

            x = mem[i];
            a ^= a >>> 6;
            a += mem[j++];
            y = mem[(x & MASK) >> 2] + a + b;
            mem[i] = y;
            b = mem[((y >> SIZE_BITS) & MASK) >> 2] + x;
            rsl[i++] = b;

            x = mem[i];
            a ^= a<<2;
            a += mem[j++];
            y = mem[(x & MASK) >> 2] + a + b;
            mem[i] = y;
            b = mem[((y >> SIZE_BITS) & MASK) >> 2] + x;
            rsl[i++] = b;

            x = mem[i];
            a ^= a >>> 16;
            a += mem[j++];
            y = mem[(x & MASK) >> 2] + a + b;
            mem[i] = y;
            b = mem[((y >> SIZE_BITS) & MASK) >> 2] + x;
            rsl[i++] = b;
        }

        j = 0;
        while (j < SIZE / 2) {
            int x = mem[i];
            a ^= a << 13;
            a += mem[j++];
            int y = mem[(x & MASK) >> 2] + a + b;
            mem[i] = y;
            b = mem[((y >> SIZE_BITS) & MASK) >> 2] + x;
            rsl[i++] = b;

            x = mem[i];
            a ^= a >>> 6;
            a += mem[j++];
            mem[i] = y = mem[(x & MASK) >> 2] + a + b;
            b = mem[((y >> SIZE_BITS) & MASK) >> 2] + x;
            rsl[i++] = b;

            x = mem[i];
            a ^= a << 2;
            a += mem[j++];
            y = mem[(x & MASK) >> 2] + a + b;
            mem[i] = y;
            b = mem[((y >> SIZE_BITS) & MASK) >> 2] + x;
            rsl[i++] = b;

            x = mem[i];
            a ^= a >>> 16;
            a += mem[j++];
            y = mem[(x & MASK) >> 2] + a + b;
            mem[i] = y;
            b = mem[((y >> SIZE_BITS) & MASK) >> 2] + x;
            rsl[i++] = b;
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

        for (int i = 0; i < SIZE; i += 8) {              // fill in mem[] with messy stuff
            a += rsl[i];
            b += rsl[i + 1];
            c += rsl[i + 2];
            d += rsl[i + 3];
            e += rsl[i + 4];
            f += rsl[i + 5];
            g += rsl[i + 6];
            h += rsl[i + 7];

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

            mem[i] = a;
            mem[i + 1] = b;
            mem[i + 2] = c;
            mem[i + 3] = d;
            mem[i + 4] = e;
            mem[i + 5] = f;
            mem[i + 6] = g;
            mem[i + 7] = h;
        }

        // second pass makes all of seed affect all of mem
        for (int i = 0; i < SIZE; i += 8) {
            a += mem[i];
            b += mem[i + 1];
            c += mem[i + 2];
            d += mem[i + 3];
            e += mem[i + 4];
            f += mem[i + 5];
            g += mem[i + 6];
            h += mem[i + 7];

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

            mem[i] = a;
            mem[i + 1] = b;
            mem[i + 2] = c;
            mem[i + 3] = d;
            mem[i + 4] = e;
            mem[i + 5] = f;
            mem[i + 6] = g;
            mem[i + 7] = h;
        }

        generate();
        count = SIZE;
    }

    public int nextInt() {
        if (0 == count--) {
            generate();
            count = SIZE - 1;
        }
        return rsl[count];
    }
}
