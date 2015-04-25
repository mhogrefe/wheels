package mho.wheels.random;

import java.util.Random;

// Implementation by Bob Jenkins
public class IsaacPRNG {
    final static int SIZE_BITS = 8;          // log of size of result[] and state[]
    final static int SIZE = 1 << SIZE_BITS;  // size of result[] and state[]
    final static int MASK = (SIZE - 1) << 2; // for pseudorandom lookup
    int count;                               // count through the results in result[]
    int result[];                            // the results given to the user
    private int state[];                     // the internal state
    private int a;                           // accumulator
    private int b;                           // the last result
    private int c;                           // counter, guarantees cycle is at least 2^40

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
    public IsaacPRNG(int seed[]) {
        state = new int[SIZE];
        result = new int[SIZE];
        System.arraycopy(seed, 0, result, 0, seed.length);
        initialize();
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
            a ^= a<<2;
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
            state[i] = y = state[(x & MASK) >> 2] + a + b;
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

            state[i] = a;
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

            state[i] = a;
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
}
