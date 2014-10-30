package mho.haskellesque.math;

import mho.haskellesque.iterables.ExhaustiveProvider;
import mho.haskellesque.iterables.IterableUtils;
import mho.haskellesque.ordering.Ordering;
import mho.haskellesque.structures.Pair;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.*;
import java.util.function.Function;
import java.util.zip.ZipEntry;

import static mho.haskellesque.iterables.IterableUtils.*;
import static mho.haskellesque.ordering.Ordering.*;

/**
 * Some mathematical utilities
 */
public final class MathUtils {
    private static final ExhaustiveProvider P = new ExhaustiveProvider();

    //must be >= ceiling(sqrt(Integer.MAX_VALUE)) = 46,341
    private static final int PRIME_SIEVE_SIZE = 1 << 16;

    private static final BitSet PRIME_SIEVE;
    static {
        PRIME_SIEVE = new BitSet(PRIME_SIEVE_SIZE);
        PRIME_SIEVE.set(2, PRIME_SIEVE_SIZE - 1);
        int multiple;
        for (multiple = 0; multiple < PRIME_SIEVE_SIZE; multiple++) {
            while (!PRIME_SIEVE.get(multiple) && multiple < PRIME_SIEVE_SIZE) multiple++;
            for (int i : rangeBy(multiple * 2, multiple, PRIME_SIEVE_SIZE - 1)) {
                PRIME_SIEVE.clear(i);
            }
        }
    }

    /**
     * Disallow instantiation
     */
    private MathUtils() {}

    /**
     * The greatest common divisor of two <tt>int</tt>s. If both <tt>x</tt> and <tt>y</tt> are zero, the result is
     * undefined. Otherwise, the result is positive.
     *
     * <ul>
     *  <li><tt>x</tt> may be any <tt>int</tt>.</li>
     *  <li><tt>y</tt> may be any <tt>int</tt>.</li>
     *  <li><tt>x</tt> and <tt>y</tt> y may not both be zero.</li>
     *  <li>The result is non-negative.</li>
     * </ul>
     *
     * @param x the first number
     * @param y the second number
     * @return gcd(x, y)
     */
    public static int gcd(int x, int y) {
        if (x == 0 && y == 0)
            throw new ArithmeticException("cannot take gcd of 0 and 0");
        return positiveGcd(Math.abs(x), Math.abs(y));
    }

    /**
     * The greatest common divisor of two non-negative <tt>int</tt>s.
     *
     * <ul>
     *  <li><tt>x</tt> must be non-negative.</li>
     *  <li><tt>y</tt> must be non-negative.</li>
     *  <li><tt>x</tt> and <tt>y</tt> y may not both be zero.</li>
     *  <li>The result is non-negative.</li>
     * </ul>
     *
     * @param x the first number
     * @param y the second number
     * @return gcd(x, y)
     */
    private static int positiveGcd(int x, int y) {
        return y == 0 ? x : positiveGcd(y, x % y);
    }

    /**
     * Returns the bits of a non-negative <tt>int</tt>. The <tt>Iterable</tt> returned is little-endian; the least-
     * significant bits come first. Zero gives an empty <tt>Iterable</tt>. There are no trailing unset bits. Does not
     * support removal.
     *
     * <ul>
     *  <li><tt>n</tt> must be non-negative.</li>
     *  <li>The result is a finite <tt>Iterable</tt> ending with <tt>true</tt>.</li>
     * </ul>
     *
     * Result length is 0 if <tt>n</tt> is 0, or &#x230A;log<sub>2</sub><tt>n</tt>&#x230B; otherwise
     *
     * @param n a number
     * @return <tt>n</tt>'s bits in little-endian order
     */
    public static @NotNull Iterable<Boolean> bits(final int n) {
        if (n < 0)
            throw new ArithmeticException("cannot get bits of a negative number");
        return () -> new Iterator<Boolean>() {
            private int remaining = n;

            @Override
            public boolean hasNext() {
                return remaining != 0;
            }

            @Override
            public Boolean next() {
                boolean bit = (remaining & 1) == 1;
                remaining >>= 1;
                return bit;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    /**
     * Returns the bits of a non-negative <tt>BigInteger</tt>. The <tt>Iterable</tt> returned is little-endian; the
     * least-significant bits come first. Zero gives an empty <tt>Iterable</tt>. There are no trailing unset bits.
     * Does not support removal.
     *
     * <ul>
     *  <li><tt>n</tt> must be non-negative.</li>
     *  <li>The result is a finite <tt>Iterable</tt> ending with <tt>true</tt>.</li>
     * </ul>
     *
     * Result length is 0 if <tt>n</tt> is 0, or &#x230A;log<sub>2</sub><tt>n</tt>&#x230B; otherwise
     *
     * @param n a number
     * @return <tt>n</tt>'s bits in little-endian order
     */
    public static @NotNull Iterable<Boolean> bits(final @NotNull BigInteger n) {
        if (n.signum() == -1)
            throw new ArithmeticException("cannot get bits of a negative number");
        return () -> new Iterator<Boolean>() {
            private BigInteger remaining = n;

            @Override
            public boolean hasNext() {
                return !remaining.equals(BigInteger.ZERO);
            }

            @Override
            public Boolean next() {
                boolean bit = remaining.testBit(0);
                remaining = remaining.shiftRight(1);
                return bit;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    /**
     * Returns the lowest <tt>n</tt> bits of a non-negative <tt>int</tt>. The <tt>Iterable</tt> returned is
     * little-endian; the least-significant bits come first. It is exactly <tt>n</tt> bits long, and padded with zeroes
     * (falses) if necessary. Does not support removal.
     *
     * <ul>
     *  <li><tt>length</tt> must be non-negative.</li>
     *  <li><tt>n</tt> must be non-negative.</li>
     *  <li>The result is a finite <tt>Iterable</tt>.</li>
     * </ul>
     *
     * Result length is <tt>n</tt>
     *
     * @param n a number
     * @return <tt>n</tt>'s bits in little-endian order
     */
    public static @NotNull Iterable<Boolean> bitsPadded(int length, int n) {
        if (length < 0)
            throw new ArithmeticException("cannot pad with a negative length");
        return pad(false, length, bits(n));
    }

    /**
     * Returns the lowest <tt>n</tt> bits of a non-negative <tt>BigInteger</tt>. The <tt>Iterable</tt> returned is
     * little-endian; the least-significant bits come first. It is exactly <tt>n</tt> bits long, and padded with zeroes
     * (falses) if necessary. Does not support removal.
     *
     * <ul>
     *  <li><tt>length</tt> must be non-negative.</li>
     *  <li><tt>n</tt> must be non-negative.</li>
     *  <li>The result is a finite <tt>Iterable</tt>.</li>
     * </ul>
     *
     * Result length is <tt>n</tt>
     *
     * @param n a number
     * @return <tt>n</tt>'s bits in little-endian order
     */
    public static @NotNull Iterable<Boolean> bitsPadded(@NotNull BigInteger length, @NotNull BigInteger n) {
        if (length.signum() == -1)
            throw new ArithmeticException("cannot pad with a negative length");
        return pad(false, length, bits(n));
    }

    public static @NotNull Iterable<Boolean> bigEndianBits(final int n) {
        return reverse(bits(n));
    }

    public static @NotNull Iterable<Boolean> bigEndianBits(@NotNull final BigInteger n) {
        return reverse(bits(n));
    }

    public static @NotNull Iterable<Boolean> bigEndianBitsPadded(int length, int n) {
        return reverse(bitsPadded(length, n));
    }

    public static @NotNull Iterable<Boolean> bigEndianBitsPadded(BigInteger length, BigInteger n) {
        return reverse(bitsPadded(length, n));
    }

    public static @NotNull BigInteger fromBigEndianBits(@NotNull Iterable<Boolean> bits) {
        BigInteger n = BigInteger.ZERO;
        for (boolean bit : bits) {
            n = n.shiftLeft(1);
            if (bit) n = n.add(BigInteger.ONE);
        }
        return n;
    }

    public static @NotNull BigInteger fromBits(@NotNull Iterable<Boolean> bits) {
        return fromBigEndianBits(reverse(bits));
    }

    public static @NotNull Iterable<Integer> digits(int base, final int n) {
        return () -> new Iterator<Integer>() {
            private int remaining = n;

            @Override
            public boolean hasNext() {
                return remaining != 0;
            }

            @Override
            public Integer next() {
                int digit = remaining % base;
                remaining /= base;
                return digit;
            }
        };
    }

    public static @NotNull Iterable<BigInteger> digits(@NotNull BigInteger base, @NotNull final BigInteger n) {
        return () -> new Iterator<BigInteger>() {
            private BigInteger remaining = n;

            @Override
            public boolean hasNext() {
                return !remaining.equals(BigInteger.ZERO);
            }

            @Override
            public BigInteger next() {
                BigInteger digit = remaining.mod(base);
                remaining = remaining.divide(base);
                return digit;
            }
        };
    }

    public static @NotNull Iterable<Integer> digitsPadded(int length, int base, int n) {
        return pad(0, length, digits(base, n));
    }

    public static @NotNull Iterable<BigInteger> digitsPadded(
            @NotNull BigInteger length,
            @NotNull BigInteger base,
            @NotNull BigInteger n
    ) {
        return pad(BigInteger.ZERO, length, digits(base, n));
    }

    public static @NotNull List<Integer> bigEndianDigits(int base, final int n) {
        return reverse(digits(base, n));
    }

    public static @NotNull List<BigInteger> bigEndianDigits(@NotNull BigInteger base, final @NotNull BigInteger n) {
        return reverse(digits(base, n));
    }

    public static @NotNull Iterable<Integer> bigEndianDigitsPadded(int length, int base, int n) {
        return reverse(digitsPadded(length, base, n));
    }

    public static @NotNull Iterable<BigInteger> bigEndianDigitsPadded(
            @NotNull BigInteger length,
            @NotNull BigInteger base,
            @NotNull BigInteger n
    ) {
        return reverse(digitsPadded(length, base, n));
    }

    public static @NotNull BigInteger fromBigEndianDigits(int base, @NotNull Iterable<Integer> digits) {
        BigInteger n = BigInteger.ZERO;
        for (int digit : digits) {
            n = n.multiply(BigInteger.valueOf(base)).add(BigInteger.valueOf(digit));
        }
        return n;
    }

    public static @NotNull BigInteger fromBigEndianDigits(
            @NotNull BigInteger base,
            @NotNull Iterable<BigInteger> digits
    ) {
        BigInteger n = BigInteger.ZERO;
        for (BigInteger digit : digits) {
            n = n.multiply(base).add(digit);
        }
        return n;
    }

    public static @NotNull BigInteger fromDigits(int base, @NotNull Iterable<Integer> digits) {
        return fromBigEndianDigits(base, reverse(digits));
    }

    public static @NotNull BigInteger fromDigits(@NotNull BigInteger base, @NotNull Iterable<BigInteger> digits) {
        return fromBigEndianDigits(base, (Iterable<BigInteger>) reverse(digits));
    }

    public static @NotNull Pair<BigInteger, BigInteger> logarithmicDemux(@NotNull BigInteger n) {
        n = n.add(BigInteger.ONE);
        int exp = n.getLowestSetBit();
        return new Pair<>(n.shiftRight(exp + 1), BigInteger.valueOf(exp));
    }

    public static @NotNull Pair<BigInteger, BigInteger> squareRootDemux(@NotNull BigInteger n) {
        List<Boolean> bits = toList(bits(n));
        Iterable<Boolean> aMask = cycle(Arrays.asList(true, false, false));
        Iterable<Boolean> bMask = cycle(Arrays.asList(false, true, true));
        return new Pair<>(fromBits(select(bMask, bits)), fromBits(select(aMask, bits)));
    }

    public static @NotNull List<BigInteger> demux(int lines, @NotNull BigInteger n) {
        if (n.equals(BigInteger.ZERO)) {
            return toList(replicate(lines, BigInteger.ZERO));
        }
        return reverse(IterableUtils.map(MathUtils::fromBits, IterableUtils.demux(lines, bits(n))));
    }

    public static boolean isAPowerOfTwo(@NotNull BigInteger n) {
        return n.getLowestSetBit() == n.bitLength() - 1;
    }

    public static @NotNull BigInteger fastGrowingCeilingInverse(
            @NotNull Function<BigInteger, BigInteger> f,
            @NotNull BigInteger y,
            @NotNull BigInteger min,
            @NotNull BigInteger max
    ) {
        for (BigInteger x : range(min, max)) {
            BigInteger j = f.apply(x);
            if (ge(j, y)) {
                return x;
            }
        }
        throw new IllegalArgumentException("inverse not found in range");
    }

    public static @NotNull BigInteger ceilingLog(@NotNull BigInteger base, @NotNull BigInteger x) {
        return fastGrowingCeilingInverse(
                i -> base.pow(i.intValueExact()),
                x,
                BigInteger.ONE,
                x //very loose bound
        );
    }

    public static @NotNull BigInteger ceilingInverse(
            @NotNull Function<BigInteger, BigInteger> f,
            @NotNull BigInteger y,
            @NotNull BigInteger min,
            @NotNull BigInteger max
    ) {
        while (true) {
            if (min.equals(max)) return max;
            BigInteger mid = min.add(max).shiftRight(1);
            BigInteger fMid = f.apply(mid);
            switch (compare(fMid, y)) {
                case GT:
                    max = mid; break;
                case LT:
                    min = mid.add(BigInteger.ONE); break;
                default:
                    return mid;
            }
        }
    }

    public static @NotNull BigInteger ceilingRoot(@NotNull BigInteger r, @NotNull BigInteger x) {
        return ceilingInverse(
                i -> i.pow(r.intValueExact()),
                x,
                BigInteger.ZERO,
                x //very loose bound
        );
    }

    public static int smallestPrimeFactor(int n) {
        if (n < 2)
            throw new IllegalArgumentException("argument must be at least 2");
        if (n % 2 == 0) return 2;
        if (n < PRIME_SIEVE_SIZE && PRIME_SIEVE.get(n)) return n;
        int limit = ceilingRoot(BigInteger.valueOf(2), BigInteger.valueOf(n)).intValueExact();
        for (int i = 3; i <= limit; i++) {
            if (PRIME_SIEVE.get(i) && n % i == 0) return i;
        }
        return n;
    }

    public static @NotNull BigInteger smallestPrimeFactor(@NotNull BigInteger n) {
        if (le(n, BigInteger.valueOf(Integer.MAX_VALUE))) {
            return BigInteger.valueOf(smallestPrimeFactor(n.intValueExact()));
        }
        if (!n.testBit(0)) return BigInteger.valueOf(2);
        BigInteger limit = ceilingRoot(BigInteger.valueOf(2), n);
        int sieveLimit = min(limit, BigInteger.valueOf(PRIME_SIEVE_SIZE - 1)).intValueExact();
        for (int i = 3; i <= sieveLimit; i++) {
            BigInteger bi = BigInteger.valueOf(i);
            if (PRIME_SIEVE.get(i) && n.mod(bi).equals(BigInteger.ZERO)) return bi;
        }
        if (limit.equals(BigInteger.valueOf(sieveLimit))) return n;
        Iterable<BigInteger> candidates = concatMap(
                i -> {
                    BigInteger sixI = i.multiply(BigInteger.valueOf(6));
                    return Arrays.asList(sixI.subtract(BigInteger.ONE), sixI.add(BigInteger.ONE));
                },
                range(BigInteger.valueOf(PRIME_SIEVE_SIZE / 6))
        );
        for (BigInteger candidate : takeWhile(i -> le(i, limit), candidates)) {
            if (n.mod(candidate).equals(BigInteger.ZERO)) return candidate;
        }
        return n;
    }

    public static boolean isPrime(int n) {
        return smallestPrimeFactor(n) == n;
    }

    public static boolean isPrime(@NotNull BigInteger n) {
        return smallestPrimeFactor(n).equals(n);
    }

    public static @NotNull Iterable<Integer> primeFactors(int n) {
        return unfoldr(
                i -> {
                    if (i == 1) return Optional.empty();
                    int spf = smallestPrimeFactor(i);
                    return Optional.of(new Pair<>(spf, i / spf));
                },
                n
        );
    }

    public static @NotNull Iterable<BigInteger> primeFactors(@NotNull BigInteger n) {
        return unfoldr(
                i -> {
                    if (i.equals(BigInteger.ONE)) return Optional.empty();
                    BigInteger spf = smallestPrimeFactor(i);
                    return Optional.of(new Pair<>(spf, i.divide(spf)));
                },
                n
        );
    }
}
