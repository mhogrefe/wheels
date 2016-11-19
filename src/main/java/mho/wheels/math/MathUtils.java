package mho.wheels.math;

import mho.wheels.iterables.ExhaustiveProvider;
import mho.wheels.iterables.NoRemoveIterator;
import mho.wheels.numberUtils.IntegerUtils;
import mho.wheels.structures.Pair;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.*;
import java.util.function.Function;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.ordering.Ordering.*;

/**
 * Some mathematical utilities
 */
public final class MathUtils {
    /**
     * The size of {@link mho.wheels.math.MathUtils#PRIME_SIEVE}. Must be greater than or equal to
     * ⌈sqrt({@code Integer.MAX_VALUE})⌉ = 46,341
     */
    private static final int PRIME_SIEVE_SIZE = 1 << 16;

    /**
     * A cache of small primes, generated using the Sieve of Eratosthenes algorithm.
     */
    private static final @NotNull BitSet PRIME_SIEVE;
    static {
        PRIME_SIEVE = new BitSet(PRIME_SIEVE_SIZE);
        PRIME_SIEVE.set(2, PRIME_SIEVE_SIZE - 1);
        int multiple;
        for (multiple = 0; multiple < PRIME_SIEVE_SIZE; multiple++) {
            while (!PRIME_SIEVE.get(multiple) && multiple < PRIME_SIEVE_SIZE) multiple++;
            for (int i = multiple * 2; i < PRIME_SIEVE_SIZE; i += multiple) {
                PRIME_SIEVE.clear(i);
            }
        }
    }

    /**
     * Returns all prime {@code int}s in ascending order.
     *
     * Length is 105,097,565
     */
    public static final @NotNull Iterable<Integer> INT_PRIMES;
    static {
        @SuppressWarnings("ConstantConditions")
        int start = (PRIME_SIEVE_SIZE & 1) == 0 ? PRIME_SIEVE_SIZE + 1 : PRIME_SIEVE_SIZE;
        INT_PRIMES = concat(
                filter(PRIME_SIEVE::get, ExhaustiveProvider.INSTANCE.rangeIncreasing(2, PRIME_SIEVE_SIZE - 1)),
                filter(MathUtils::isPrime, takeWhile(j -> j > 0, iterate(i -> i + 2, start)))
        );
    }

    /**
     * Returns all prime {@code BigInteger}s in ascending order.
     *
     * Length is infinite
     */
    public static final @NotNull Iterable<BigInteger> PRIMES;
    static {
        Iterable<BigInteger> candidates = concatMap(
                i -> {
                    BigInteger sixI = i.multiply(BigInteger.valueOf(6));
                    return Arrays.asList(sixI.subtract(BigInteger.ONE), sixI.add(BigInteger.ONE));
                },
                ExhaustiveProvider.INSTANCE.rangeUpIncreasing(BigInteger.valueOf(PRIME_SIEVE_SIZE / 6))
        );
        //noinspection Convert2MethodRef
        PRIMES = concat(map(i -> BigInteger.valueOf(i), INT_PRIMES), filterInfinite(MathUtils::isPrime, candidates));
    }

    /**
     * The Thue-Morse sequence; the sequence obtained by starting with 0 and repeatedly appending the complement of the
     * sequence to itself.
     */
    public static final @NotNull Iterable<Boolean> THUE_MORSE = () -> new NoRemoveIterator<Boolean>() {
        private final @NotNull BitSet sequence = new BitSet();
        private int i = 0;
        private int sequenceLength = 1;

        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        public @NotNull Boolean next() {
            if (i >= sequenceLength) {
                for (int j = 0; j < i; j++) {
                    sequence.set(sequenceLength++, !sequence.get(j));
                }
            }
            return sequence.get(i++);
        }
    };

    /**
     * The Kolakoski sequence; the sequence beginning with [1, 2] that is its own run-length encoding.
     */
    public static @NotNull Iterable<Integer> KOLAKOSKI = () -> new NoRemoveIterator<Integer>() {
        private final @NotNull List<Integer> prefix = new ArrayList<>();
        private int i = 0;
        private int counter = 1;
        private boolean ones = true;

        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        public @NotNull Integer next() {
            int next = ones ? 1 : 2;
            prefix.add(next);
            counter--;
            if (counter == 0) {
                i++;
                ones = !ones;
                counter = i == 1 ? 2 : prefix.get(i);
            }
            return next;
        }
    };

    /**
     * The look-and-say sequence. To generate this sequence, start with [1], then count the blocks of adjacent numbers
     * in the previous term: so the second term is "one 1", or [1, 1], the third term is "two 1s", or [2, 1], the
     * fourth term is "one 2, one 1", or [1, 2, 1, 1], and so on. Although this sequence is usually described as a
     * sequence of integers 1, 11, 21, 1211, ..., it is implemented here as a sequence of lists of digits.
     */
    public static @NotNull Iterable<List<Integer>> LOOK_AND_SAY = () -> new NoRemoveIterator<List<Integer>>() {
        private @NotNull List<Integer> preceding = new ArrayList<>();

        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        public @NotNull List<Integer> next() {
            List<Integer> next = new ArrayList<>();
            if (preceding.isEmpty()) {
                next.add(1);
            } else {
                next = toList(concatMap(p -> Arrays.asList(p.b, p.a), countAdjacent(preceding)));
            }
            preceding = next;
            return next;
        }
    };

    /**
     * The Sylvester sequence, or the sequence beginning with 1, each of whose successive terms is the product of all
     * the previous terms plus 1.
     */
    public static @NotNull Iterable<BigInteger> SYLVESTER = () -> new NoRemoveIterator<BigInteger>() {
        private @NotNull List<BigInteger> prefix = new ArrayList<>();

        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        public @NotNull BigInteger next() {
            BigInteger next = productBigInteger(prefix).add(BigInteger.ONE);
            prefix.add(next);
            return next;
        }
    };

    /**
     * Disallow instantiation
     */
    private MathUtils() {}

    /**
     * Returns an {@code int} raised to the power of another {@code int}. Throws an exception on overflow.
     * 0<sup>0</sup> is 1.
     *
     * <ul>
     *  <li>{@code n} may be any {@code int}.</li>
     *  <li>{@code p} cannot be negative.</li>
     *  <li>{@code p}<sup>{@code n}</sup> must be greater than or equal to –2<sup>31</sup> and less than
     *  2<sup>31</sup>.</li>
     *  <li>The result may be any {@code int}.</li>
     * </ul>
     *
     * @param n a number
     * @param p the power {@code n} is raised to
     * @return {@code n}<sup>{@code p}</sup>
     */
    public static int pow(int n, int p) {
        if (p < 0) {
            throw new ArithmeticException("p cannot be negative. Invalid p: " + p);
        }
        if (p == 0) return 1;
        if (n == 0) return 0;
        if (n == 1) return 1;
        if (n == 2) {
            if (p > 30) {
                throw new ArithmeticException("n^p must be less than 2^31. n: " + n + ", p: " + p);
            }
            return 1 << p;
        }
        if (n == -2) {
            if (p == 31) return Integer.MIN_VALUE;
            if (p > 31) {
                throw new ArithmeticException("n^p must be greater than or equal to -2^31. n: " + n + ", p: " + p);
            }
            int result = 1 << p;
            return (p & 1) == 0 ? result : -result;
        }
        int result = 1;
        for (int i = 0; i < p; i++) {
            result = Math.multiplyExact(result, n);
        }
        return result;
    }

    /**
     * The greatest common divisor of two {@link int}s. If both {@code x} and {@code y} are zero, the result is zero.
     * Otherwise, the result is positive.
     *
     * <ul>
     *  <li>{@code x} may be any {@code int}.</li>
     *  <li>{@code y} may be any {@code int}.</li>
     *  <li>{@code x} and {@code y} cannot both equal –2<sup>31</sup>. Also, if one of {@code x} and {@code y} is 0,
     *  the other cannot be –2<sup>31</sup>.</li>
     *  <li>The result is non-negative.</li>
     * </ul>
     *
     * @param x the first number
     * @param y the second number
     * @return gcd({@code x}, {@code y})
     */
    @SuppressWarnings("JavaDoc")
    public static int gcd(int x, int y) {
        x = Math.abs(x);
        y = Math.abs(y);
        if (x == Integer.MIN_VALUE) {
            if (y == Integer.MIN_VALUE) {
                throw new ArithmeticException("x and y cannot both equal -2^31.");
            } else if (y == 0) {
                throw new ArithmeticException("If y is 0, x cannot be -2^31.");
            } else {
                return IntegerUtils.isPowerOfTwo(y) ? y : 1;
            }
        } else if (y == Integer.MIN_VALUE) {
            if (x == 0) {
                throw new ArithmeticException("If x is 0, y cannot be -2^31.");
            }
            return IntegerUtils.isPowerOfTwo(x) ? x : 1;
        } else {
            return nonNegativeGcd(x, y);
        }
    }

    /**
     * The greatest common divisor of two non-negative {@code int}s.
     *
     * <ul>
     *  <li>{@code x} cannot be negative.</li>
     *  <li>{@code y} cannot be negative.</li>
     *  <li>The result is non-negative.</li>
     * </ul>
     *
     * @param x the first number
     * @param y the second number
     * @return gcd({@code x}, {@code y})
     */
    @SuppressWarnings("JavaDoc")
    private static int nonNegativeGcd(int x, int y) {
        //noinspection SuspiciousNameCombination
        return y == 0 ? x : nonNegativeGcd(y, x % y);
    }

    /**
     * The greatest common divisor of two {@link long}s. If both {@code x} and {@code y} are zero, the result is zero.
     * Otherwise, the result is positive.
     *
     * <ul>
     *  <li>{@code x} may be any {@code long}.</li>
     *  <li>{@code y} may be any {@code long}.</li>
     *  <li>{@code x} and {@code y} cannot both equal –2<sup>63</sup>. Also, if one of {@code x} and {@code y} is 0,
     *  the other cannot be –2<sup>63</sup>.</li>
     *  <li>The result is non-negative.</li>
     * </ul>
     *
     * @param x the first number
     * @param y the second number
     * @return gcd({@code x}, {@code y})
     */
    @SuppressWarnings("JavaDoc")
    public static long gcd(long x, long y) {
        x = Math.abs(x);
        y = Math.abs(y);
        if (x == Long.MIN_VALUE) {
            if (y == Long.MIN_VALUE) {
                throw new ArithmeticException("x and y cannot both equal -2^63.");
            } else if (y == 0L) {
                throw new ArithmeticException("If y is 0, x cannot be -2^63.");
            } else {
                return IntegerUtils.isPowerOfTwo(y) ? y : 1L;
            }
        } else if (y == Long.MIN_VALUE) {
            if (x == 0L) {
                throw new ArithmeticException("If x is 0, y cannot be -2^63.");
            }
            return IntegerUtils.isPowerOfTwo(x) ? x : 1L;
        } else {
            return nonNegativeGcd(x, y);
        }
    }

    /**
     * The greatest common divisor of two non-negative {@code long}s.
     *
     * <ul>
     *  <li>{@code x} cannot be negative.</li>
     *  <li>{@code y} cannot be negative.</li>
     *  <li>The result is non-negative.</li>
     * </ul>
     *
     * @param x the first number
     * @param y the second number
     * @return gcd({@code x}, {@code y})
     */
    @SuppressWarnings("JavaDoc")
    private static long nonNegativeGcd(long x, long y) {
        //noinspection SuspiciousNameCombination
        return y == 0 ? x : nonNegativeGcd(y, x % y);
    }

    /**
     * The least common multiple of two positive {@code BigInteger}s. Versions of this method for {@code int}s and
     * {@code long}s are not provided to avoid overflow concerns.
     *
     * <ul>
     *  <li>{@code x} must be positive.</li>
     *  <li>{@code y} must be positive.</li>
     *  <li>The result is positive.</li>
     * </ul>
     *
     * @param x the first number
     * @param y the second number
     * @return lcm({@code x}, {@code y})
     */
    @SuppressWarnings("JavaDoc")
    public static @NotNull BigInteger lcm(@NotNull BigInteger x, @NotNull BigInteger y) {
        if (x.signum() != 1) {
            throw new ArithmeticException("x must be positive. Invalid x: " + x);
        }
        if (y.signum() != 1) {
            throw new ArithmeticException("y must be positive. Invalid y: " + y);
        }
        return x.divide(x.gcd(y)).multiply(y);
    }

    /**
     * The greatest common divisor of a {@code List} of {@code BigInteger}s. The GCD of an empty {@code List}, or a
     * {@code List} containing only zeros, is zero.
     *
     * <ul>
     *  <li>{@code xs} cannot contain any nulls.</li>
     *  <li>The result is non-negative.</li>
     * </ul>
     *
     * @param xs some numbers
     * @return gcd({@code xs})
     */
    @SuppressWarnings("JavaDoc")
    public static @NotNull BigInteger gcd(@NotNull List<BigInteger> xs) {
        return foldl(BigInteger::gcd, BigInteger.ZERO, xs);
    }

    /**
     * The least common multiple of a {@code List} of {@code BigInteger}s.
     *
     * <ul>
     *  <li>{@code xs} cannot be empty, and every element of {@code xs} must be positive.</li>
     *  <li>The result is positive.</li>
     * </ul>
     *
     * @param xs some numbers
     * @return lcm({@code xs})
     */
    @SuppressWarnings("JavaDoc")
    public static @NotNull BigInteger lcm(@NotNull List<BigInteger> xs) {
        if (xs.isEmpty()) {
            throw new ArithmeticException("xs cannot be empty.");
        }
        if (any(x -> x.signum() != 1, xs)) {
            throw new ArithmeticException("Every element of xs must be positive. Invalid xs: " + xs);
        }
        return foldl1(MathUtils::lcm, sort(xs));
    }

    /**
     * The factorial function {@code n}! Multiplies the odd factors first, then the powers of 2.
     *
     * <ul>
     *  <li>{@code n} cannot be negative.</li>
     *  <li>The result is a factorial.</li>
     * </ul>
     *
     * @param n the argument
     * @return {@code n}!
     */
    public static @NotNull BigInteger factorial(int n) {
        if (n < 0) {
            throw new ArithmeticException("n cannot be negative. Invalid n: " + n);
        }
        BigInteger factorial = BigInteger.ONE;
        List<Integer> halves = new ArrayList<>();
        int h = n;
        while (h > 2) {
            halves.add(h % 2 == 0 ? h - 1 : h);
            h >>= 1;
        }
        int j = halves.size() - 1;
        BigInteger subproduct = BigInteger.ONE;
        for (int i = 3; i <= n; i += 2) {
            subproduct = subproduct.multiply(BigInteger.valueOf(i));
            if (i >= halves.get(j)) {
                factorial = factorial.multiply(subproduct);
                j--;
            }
        }
        return factorial.shiftLeft(n - Integer.bitCount(n));
    }

    /**
     * The factorial function {@code n}! Multiplies the odd factors first, then the powers of 2.
     *
     * <ul>
     *  <li>{@code n} cannot be negative.</li>
     *  <li>The result is a factorial.</li>
     * </ul>
     *
     * @param n the argument
     * @return {@code n}!
     */
    public static @NotNull BigInteger factorial(@NotNull BigInteger n) {
        if (n.signum() == -1) {
            throw new ArithmeticException("n cannot be negative. Invalid n: " + n);
        }
        BigInteger factorial = BigInteger.ONE;
        List<BigInteger> halves = new ArrayList<>();
        BigInteger h = n;
        while (gt(h, IntegerUtils.TWO)) {
            halves.add(h.testBit(0) ? h : h.subtract(BigInteger.ONE));
            h = h.shiftRight(1);
        }
        int j = halves.size() - 1;
        BigInteger subproduct = BigInteger.ONE;
        for (BigInteger i = BigInteger.valueOf(3); le(i, n); i = i.add(IntegerUtils.TWO)) {
            subproduct = subproduct.multiply(i);
            if (ge(i, halves.get(j))) {
                factorial = factorial.multiply(subproduct);
                j--;
            }
        }
        return factorial.shiftLeft(n.subtract(BigInteger.valueOf(n.bitCount())).intValueExact());
    }

    /**
     * The subfactorial function !{@code n}
     *
     * <ul>
     *  <li>{@code n} cannot be negative.</li>
     *  <li>The result is a subfactorial (derangement number).</li>
     * </ul>
     *
     * @param n the argument
     * @return !{@code n}
     */
    public static @NotNull BigInteger subfactorial(int n) {
        if (n < 0) {
            throw new ArithmeticException("n cannot be negative. Invalid n: " + n);
        }
        BigInteger sf = BigInteger.ONE;
        for (int i = 1; i <= n; i++) {
            sf = sf.multiply(BigInteger.valueOf(i));
            if ((i & 1) == 0) {
                sf = sf.add(BigInteger.ONE);
            } else {
                sf = sf.subtract(BigInteger.ONE);
            }
        }
        return sf;
    }

    /**
     * The subfactorial function !{@code n}
     *
     * <ul>
     *  <li>{@code n} cannot be negative.</li>
     *  <li>The result is a subfactorial (derangement number).</li>
     * </ul>
     *
     * @param n the argument
     * @return !{@code n}
     */
    public static @NotNull BigInteger subfactorial(@NotNull BigInteger n) {
        if (n.signum() == -1) {
            throw new ArithmeticException("n cannot be negative. Invalid n: " + n);
        }
        BigInteger sf = BigInteger.ONE;
        for (BigInteger i = BigInteger.ONE; le(i, n); i = i.add(BigInteger.ONE)) {
            sf = sf.multiply(i);
            if (i.testBit(0)) {
                sf = sf.subtract(BigInteger.ONE);
            } else {
                sf = sf.add(BigInteger.ONE);
            }
        }
        return sf;
    }

    /**
     * The falling factorial, or Pochhammer symbol, of {@code x} and {@code n}. Given a set containing {@code x}
     * elements, returns the number of length-{@code n} lists of elements from the set.
     *
     * <ul>
     *  <li>{@code x} cannot be negative.</li>
     *  <li>{@code n} cannot be negative.</li>
     *  <li>The result is not negative.</li>
     * </ul>
     *
     * @param x the number of elements in a set
     * @param n the length of a list made by choosing elements from the set
     * @return ({@code x})<sub>{@code n}</sub>
     */
    @SuppressWarnings("JavaDoc")
    public static @NotNull BigInteger fallingFactorial(@NotNull BigInteger x, int n) {
        if (x.signum() == -1) {
            throw new ArithmeticException("x cannot be negative. Invalid x: " + x);
        }
        if (n < 0) {
            throw new ArithmeticException("n cannot be negative. Invalid n: " + n);
        }
        if (n == 0) {
            return BigInteger.ONE;
        }
        return productBigInteger(
                toList(ExhaustiveProvider.INSTANCE.rangeIncreasing(x.subtract(BigInteger.valueOf(n - 1)), x))
        );
    }

    /**
     * The number of arrangements (of any length) of a set with {@code n} elements. OEIS A000522.
     *
     * <ul>
     *  <li>{@code n} cannot be negative.</li>
     *  <li>The result is positive.</li>
     * </ul>
     *
     * @param n the number of elements in a set
     * @return the number of different lists that can be made by choosing elements from the set
     */
    public static @NotNull BigInteger numberOfArrangementsOfASet(int n) {
        if (n < 0) {
            throw new ArithmeticException("n cannot be negative. Invalid n: " + n);
        }
        BigInteger sum = BigInteger.ONE;
        BigInteger product = BigInteger.ONE;
        for (int i = n; i > 0; i--) {
            product = product.multiply(BigInteger.valueOf(i));
            sum = sum.add(product);
        }
        return sum;
    }

    /**
     * The number of arrangements (of length at least {@code minSize}) of a set with {@code n} elements.
     *
     * <ul>
     *  <li>{@code minSize} cannot be negative.</li>
     *  <li>{@code n} cannot be negative.</li>
     *  <li>The result is not negative.</li>
     * </ul>
     *
     * @param minSize the minimum length of the arrangements
     * @param n the number of elements in a set
     * @return the number of different lists that can be made by choosing at least {@code minSize} elements from the
     * set
     */
    public static @NotNull BigInteger numberOfArrangementsOfASet(int minSize, int n) {
        if (minSize < 0) {
            throw new ArithmeticException("minSize cannot be negative. Invalid minSize: " + minSize);
        }
        if (n < 0) {
            throw new ArithmeticException("n cannot be negative. Invalid n: " + n);
        }
        if (minSize > n) {
            return BigInteger.ZERO;
        }
        BigInteger sum = minSize == 0 ? BigInteger.ONE : BigInteger.ZERO;
        BigInteger product = BigInteger.ONE;
        int limit = n - minSize + 1;
        for (int i = n; i >= 0; i--) {
            product = product.multiply(BigInteger.valueOf(i));
            if (i <= limit) {
                sum = sum.add(product);
            }
        }
        return sum;
    }

    /**
     * The binomial coefficient <sub>{@code n}</sub>C<sub>{@code k}</sub>, or the number of {@code k}-element subsets
     * of an {@code n}-element set.
     *
     * <ul>
     *  <li>{@code n} cannot be negative.</li>
     *  <li>{@code k} cannot be negative.</li>
     *  <li>The result is not negative.</li>
     * </ul>
     *
     * @param n the number of elements in a set
     * @param k the number of elements in a subset of the set
     * @return {@code n} choose {@code k}
     */
    public static @NotNull BigInteger binomialCoefficient(@NotNull BigInteger n, int k) {
        return fallingFactorial(n, k).divide(factorial(k));
    }

    /**
     * The multiset coefficient of {@code n} and {@code k}, or the number of {@code k}-element sub-multisets of an
     * {@code n}-element set.
     *
     * <ul>
     *  <li>{@code n} cannot be negative.</li>
     *  <li>{@code k} cannot be negative.</li>
     *  <li>The result is not negative.</li>
     * </ul>
     *
     * @param n the number of elements in a multiset
     * @param k the number of elements in a sub-multiset of the set
     * @return The number of ways to choose {@code k} elements from a set of {@code n} elements, disregarding order and
     * allowing repetitions
     */
    public static @NotNull BigInteger multisetCoefficient(@NotNull BigInteger n, int k) {
        if (n.signum() == -1) {
            throw new ArithmeticException("n cannot be negative. Invalid n: " + n);
        }
        if (k < 0) {
            throw new ArithmeticException("k cannot be negative. Invalid k: " + k);
        }
        if (n.equals(BigInteger.ZERO)) {
            return k == 0 ? BigInteger.ONE : BigInteger.ZERO;
        }
        return binomialCoefficient(n.add(BigInteger.valueOf(k - 1)), k);
    }

    /**
     * The number of unordered arrangements (of length at least {@code minSize}) of a set with {@code n} elements.
     *
     * <ul>
     *  <li>{@code minSize} cannot be negative.</li>
     *  <li>{@code n} cannot be negative.</li>
     *  <li>The result is not negative.</li>
     * </ul>
     *
     * @param minSize the minimum length of the arrangements
     * @param n the number of elements in a set
     * @return the number of different subsets that can be made by choosing at least {@code minSize} elements from the
     * set
     */
    public static @NotNull BigInteger subsetCount(int minSize, int n) {
        if (n < 0) {
            throw new ArithmeticException("n cannot be negative. Invalid n: " + n);
        }
        if (minSize > n) {
            return BigInteger.ZERO;
        }
        return sumBigInteger(
                toList(
                        map(k -> binomialCoefficient(BigInteger.valueOf(n), k),
                        ExhaustiveProvider.INSTANCE.rangeIncreasing(minSize, n))
                )
        );
    }

    /**
     * The number of distinct permutations of a list, taking repeated elements into account.
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result is positive.</li>
     * </ul>
     *
     * @param xs a {@code List} of elements
     * @param <T> the type of elements in {@code xs}
     * @return the number of permutations of {@code xs}
     */
    public static @NotNull <T> BigInteger permutationCount(@NotNull List<T> xs) {
        BigInteger result = factorial(xs.size());
        for (BigInteger divisor : map(f -> factorial(f.b), frequencies(xs))) {
            if (!divisor.equals(BigInteger.ONE)) {
                result = result.divide(divisor);
            }
        }
        return result;
    }

    /**
     * Returns the sign, or signature, of the reversal of a list of {@code i} elements, considered as a permutation.
     * False means negative, true means positive.
     *
     * <ul>
     *  <li>{@code i} cannot be negative.</li>
     *  <li>The result may be either {@code boolean}.</li>
     * </ul>
     *
     * @param i the number of elements of a list
     * @return the parity (false is odd, true is even) of reversing the elements of the list with {@code i} elements
     */
    public static boolean reversePermutationSign(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("i cannot be negative. Invalid i: " + i);
        }
        return (i & 2) == 0;
    }

    /**
     * Given a weakly monotonically increasing function from {@code Integer} to {@code BigInteger} {@code f} over the
     * inclusive range [{@code min}, {@code max}] and a value {@code y}, finds the smallest {@code int} x in
     * [{@code min}, {@code max}] such that {@code f}(x)≥{@code y}. {@code f} should be fast-growing (exponential or
     * better) for a quick result. Otherwise, use
     * {@link MathUtils#ceilingInverse(Function, BigInteger, BigInteger, BigInteger)}.
     *
     * <ul>
     *  <ul>{@code f} must terminate on all inputs in [{@code min}, {@code max}] with throwing an exception, and cannot
     *  return null.</ul>
     *  <li>{@code f} should be weakly monotonically increasing over [{@code min}, {@code max}], and {@code f}(x)
     *  should be greater than or equal to {@code y} for some value of x in that range.</li>
     *  <li>{@code min} may be any {@code int}.</li>
     *  <li>{@code max} may be any {@code int}.</li>
     *  <li>{@code min} must be less than or equal to {@code max}.</li>
     *  <li>{@code y} cannot be null.</li>
     *  <li>The result may be any {@code int}.</li>
     * </ul>
     *
     * @param f a fast-growing function that is weakly monotonically increasing in [{@code min}, {@code max}].
     * @param min the inclusive lower bound of the search interval
     * @param max the inclusive upper bound of the search interval
     * @param y a value
     * @return ⌈{@code f}<sup>–1</sup>({@code y})⌉, where the inverse is defined over x∈[{@code min}, {@code max}]
     */
    public static int fastGrowingCeilingInverse(
            @NotNull Function<Integer, BigInteger> f,
            int min,
            int max,
            @NotNull BigInteger y
    ) {
        if (gt(min, max)) {
            throw new IllegalArgumentException("min must be less than or equal to max. min: " + min + ", max: " + max);
        }
        BigInteger previous = null;
        for (int x : ExhaustiveProvider.INSTANCE.rangeIncreasing(min, max)) {
            BigInteger i = f.apply(x);
            if (i == null) {
                throw new IllegalArgumentException("f cannot return null for any value in [min, max]. min: " +
                        min + ", max: " + max + ", f(" + x + ") = null");
            }
            if (previous != null && lt(i, previous)) {
                throw new IllegalArgumentException("f must be weakly monotonically increasing over [min, max]. min: " +
                        min + ", max: " + max + ", f(" + x + ") < f(" + (x - 1) + ")");
            }
            if (ge(i, y)) {
                return x;
            }
            previous = i;
        }
        throw new IllegalArgumentException("f(x) should be greater than or equal to y for some value of x in" +
                " [min, max]. y: " + y + ", min: " + min + ", max: " + max + ", f(" + max + ") = " + f.apply(max));
    }

    /**
     * Returns the ceiling of the base-{@code base} logarithm of {@code x}.
     *
     * <ul>
     *  <li>{@code base} must be at least 2.</li>
     *  <li>{@code x} must be positive.</li>
     *  <li>The result is not negative.</li>
     * </ul>
     *
     * @param base the base of the logarithm
     * @param x the argument of the logarithm
     * @return ⌈log<sub>{@code base}</sub>({@code x})⌉
     */
    public static int ceilingLog(@NotNull BigInteger base, @NotNull BigInteger x) {
        if (lt(base, IntegerUtils.TWO)) {
            throw new ArithmeticException("base must be at least 2. Invalid base: " + base);
        }
        if (x.signum() != 1) {
            throw new ArithmeticException("x must be positive. Invalid x: " + x);
        }
        //noinspection SuspiciousNameCombination
        return fastGrowingCeilingInverse(base::pow, 0, x.bitLength(), x);
    }

    /**
     * Given a weakly monotonically increasing function from {@code BigInteger} to {@code BigInteger} {@code f} over
     * the inclusive range [{@code min}, {@code max}] and a value {@code y}, finds the smallest {@code int} x in
     * [{@code min}, {@code max}] such that {@code f}(x)≥{@code y}.
     *
     * <ul>
     *  <ul>{@code f} must terminate on all inputs in [{@code min}, {@code max}] with throwing an exception, and cannot
     *  return null.</ul>
     *  <li>{@code f} should be weakly monotonically increasing over [{@code min}, {@code max}], and {@code f}(x)
     *  should be greater than or equal to {@code y} for some value of x in that range.</li>
     *  <li>{@code min} may be any {@code int}.</li>
     *  <li>{@code max} may be any {@code int}.</li>
     *  <li>{@code min} must be less than or equal to {@code max}.</li>
     *  <li>{@code y} cannot be null.</li>
     *  <li>The result may be any {@code int}.</li>
     * </ul>
     *
     * @param f a fast-growing function that is weakly monotonically increasing in [{@code min}, {@code max}].
     * @param min the inclusive lower bound of the search interval
     * @param max the inclusive upper bound of the search interval
     * @param y a value
     * @return ⌈{@code f}<sup>–1</sup>({@code y})⌉, where the inverse is defined over x∈[{@code min}, {@code max}]
     */
    public static @NotNull BigInteger ceilingInverse(
            @NotNull Function<BigInteger, BigInteger> f,
            @NotNull BigInteger min,
            @NotNull BigInteger max,
            @NotNull BigInteger y
    ) {
        BigInteger originalMin = min;
        BigInteger originalMax = max;
        if (gt(min, max)) {
            throw new IllegalArgumentException("min must be less than or equal to max. min: " + min + ", max: " + max);
        }
        BigInteger previous = null;
        boolean previousWasLower = false;
        while (true) {
            if (min.equals(max)) {
                BigInteger fMax = f.apply(max);
                if (ge(fMax, y)) {
                    return max;
                } else {
                    throw new IllegalArgumentException("f(x) should be greater than or equal to y for some value in" +
                            " [min, max]. y: " + y + ", min: " + originalMin + ", max: " + originalMax + ", f(" + max +
                            ") = " + fMax);
                }
            }
            BigInteger mid = min.add(max).shiftRight(1);
            BigInteger fMid = f.apply(mid);
            if (fMid == null) {
                throw new IllegalArgumentException("f cannot return null for any value in [min, max]. min: " +
                        min + ", max: " + max + ", f(" + mid + ") = null");
            }
            if (previous != null) {
                if (previousWasLower) {
                    if (lt(fMid, previous)) {
                        throw new IllegalArgumentException("f must be weakly monotonically increasing over" +
                                " [min, max]. min: " + min + ", max: " + max + ", f(" + fMid + ") < f(" + previous +
                                ")");
                    }
                } else {
                    if (gt(fMid, previous)) {
                        throw new IllegalArgumentException("f must be weakly monotonically increasing over" +
                                " [min, max]. min: " + min + ", max: " + max + ", f(" + previous + ") < f(" + fMid +
                                ")");
                    }
                }
            }
            switch (compare(fMid, y)) {
                case GT:
                    max = mid;
                    previousWasLower = false;
                    break;
                case LT:
                    min = mid.add(BigInteger.ONE);
                    previousWasLower = true;
                    break;
                default:
                    return mid;
            }
            previous = fMid;
        }
    }

    /**
     * Returns the ceiling of the {@code r}th root of {@code x}.
     *
     * <ul>
     *  <li>{@code r} must be positive.</li>
     *  <li>{@code x} cannot be negative.</li>
     *  <li>The result is not negative.</li>
     * </ul>
     *
     * @param r the order of the root
     * @param x the argument of the root
     * @return ⌈{@code x}<sup>1/{@code r}</sup>⌉
     */
    public static @NotNull BigInteger ceilingRoot(int r, @NotNull BigInteger x) {
        if (r < 1) {
            throw new ArithmeticException("r must be positive. Invalid r: " + r);
        }
        if (x.signum() == -1) {
            throw new ArithmeticException("x cannot be negative. Invalid x: " + x);
        }
        if (x.equals(BigInteger.ZERO)) return BigInteger.ZERO;
        if (r == 1) return x;
        int bitLengthEstimate = x.bitLength();
        bitLengthEstimate = (bitLengthEstimate & 1) == 0 ? bitLengthEstimate / 2 : bitLengthEstimate / 2 + 1;
        //noinspection SuspiciousNameCombination
        return ceilingInverse(i -> i.pow(r), BigInteger.ZERO, BigInteger.ONE.shiftLeft(bitLengthEstimate), x);
    }

    /**
     * Returns the smallest prime factor of {@code n}.
     *
     * <ul>
     *  <li>{@code n} must be at least 2.</li>
     *  <li>The result is prime.</li>
     * </ul>
     *
     * @param n a number
     * @return the smallest prime factor of {@code n}
     */
    public static int smallestPrimeFactor(int n) {
        if (n < 2) {
            throw new IllegalArgumentException("n must be at least 2. Invalid n: " + n);
        }
        if (n % 2 == 0) return 2;
        if (n < PRIME_SIEVE_SIZE && PRIME_SIEVE.get(n)) return n;
        for (int i = 3; i < PRIME_SIEVE_SIZE; i += 2) {
            int square = i * i;
            if (square > n) break;
            if (PRIME_SIEVE.get(i) && n % i == 0) return i;
        }
        return n;
    }

    /**
     * Returns the smallest prime factor of {@code n}.
     *
     * <ul>
     *  <li>{@code n} must be at least 2.</li>
     *  <li>The result is prime.</li>
     * </ul>
     *
     * @param n a number
     * @return the smallest prime factor of {@code n}
     */
    public static @NotNull BigInteger smallestPrimeFactor(@NotNull BigInteger n) {
        if (lt(n, IntegerUtils.TWO)) {
            throw new IllegalArgumentException("n must be at least 2. Invalid n: " + n);
        }
        if (le(n, BigInteger.valueOf(Integer.MAX_VALUE))) {
            return BigInteger.valueOf(smallestPrimeFactor(n.intValueExact()));
        }
        if (!n.testBit(0)) return IntegerUtils.TWO;
        for (int i = 3; i < PRIME_SIEVE_SIZE; i += 2) {
            BigInteger bi = BigInteger.valueOf(i);
            if (gt(bi.pow(2), n)) return n;
            if (PRIME_SIEVE.get(i) && n.mod(bi).equals(BigInteger.ZERO)) return bi;
        }
        BigInteger limit = ceilingRoot(2, n);
        Iterable<BigInteger> candidates = concatMap(
                i -> {
                    BigInteger sixI = i.multiply(BigInteger.valueOf(6));
                    return Arrays.asList(sixI.subtract(BigInteger.ONE), sixI.add(BigInteger.ONE));
                },
                ExhaustiveProvider.INSTANCE.rangeUpIncreasing(BigInteger.valueOf(PRIME_SIEVE_SIZE / 6))
        );
        for (BigInteger candidate : takeWhile(i -> le(i, limit), candidates)) {
            if (n.mod(candidate).equals(BigInteger.ZERO)) return candidate;
        }
        return n;
    }

    /**
     * Determines whether a number is prime. 1 is not prime (it has zero prime factors).
     *
     * <ul>
     *  <li>{@code n} must be positive.</li>
     *  <li>The result may be either {@code boolean}.</li>
     * </ul>
     *
     * @param n a number
     * @return whether {@code n} is prime
     */
    public static boolean isPrime(int n) {
        return n != 1 && smallestPrimeFactor(n) == n;
    }

    /**
     * Determines whether a number is prime. 1 is not prime (it has zero prime factors).
     *
     * <ul>
     *  <li>{@code n} must be positive.</li>
     *  <li>The result may be either {@code boolean}.</li>
     * </ul>
     *
     * @param n a number
     * @return whether {@code n} is prime
     */
    public static boolean isPrime(@NotNull BigInteger n) {
        return !n.equals(BigInteger.ONE) && smallestPrimeFactor(n).equals(n);
    }

    /**
     * Returns the prime factors of a number, including multiplicities.
     *
     * <ul>
     *  <li>{@code n} must be positive.</li>
     *  <li>The result is a finite {@code Iterable} of primes in weakly ascending order.</li>
     * </ul>
     *
     * @param n a number
     * @return the prime factors of {@code n}
     */
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

    /**
     * Returns the prime factors of a number, including multiplicities.
     *
     * <ul>
     *  <li>{@code n} must be positive.</li>
     *  <li>The result is a finite {@code Iterable} of primes in weakly ascending order.</li>
     * </ul>
     *
     * @param n a number
     * @return the prime factors of {@code n}
     */
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

    /**
     * Returns the prime factors of number. The output format is a list of pairs, where the first elements are the
     * primes and the second elements are the powers that the corresponding primes are raised to.
     *
     * <ul>
     *  <li>{@code n} must be positive.</li>
     *  <li>The result is a finite {@code Iterable} of pairs, where the first elements are distinct primes in
     *  increasing order and the second elements are all positive.</li>
     * </ul>
     *
     * @param n a number
     * @return [p<sub>1</sub>, e<sub>1</sub>, p<sub>2</sub>, e<sub>2</sub>, ..., p<sub>n</sub>, e<sub>n</sub>], where
     * p are distinct primes in ascending order and {@code n}=Πp<sub>i</sub><sup>e<sub>i</sub></sup>
     */
    public static @NotNull Iterable<Pair<Integer, Integer>> compactPrimeFactors(int n) {
        return countAdjacent(primeFactors(n));
    }

    /**
     * Returns the prime factors of number. The output format is a list of pairs, where the first elements are the
     * primes and the second elements are the powers that the corresponding primes are raised to.
     *
     * <ul>
     *  <li>{@code n} must be positive.</li>
     *  <li>The result is a finite {@code Iterable} of pairs, where the first elements are distinct primes in
     *  increasing order and the second elements are all positive.</li>
     * </ul>
     *
     * @param n a number
     * @return [p<sub>1</sub>, e<sub>1</sub>, p<sub>2</sub>, e<sub>2</sub>, ..., p<sub>n</sub>, e<sub>n</sub>], where
     * p are distinct primes in ascending order and {@code n}=Πp<sub>i</sub><sup>e<sub>i</sub></sup>
     */
    public static @NotNull Iterable<Pair<BigInteger, Integer>> compactPrimeFactors(@NotNull BigInteger n) {
        return countAdjacent(primeFactors(n));
    }

    /**
     * Returns the factors of a number in ascending order.
     *
     * <ul>
     *  <li>{@code n} must be positive.</li>
     *  <li>The result is a finite {@code List} of {@code int}s in ascending order.</li>
     * </ul>
     *
     * @param n a number
     * @return the factors of {@code n}
     */
    public static @NotNull List<Integer> factors(int n) {
        List<Pair<Integer, Integer>> primeFactors = toList(compactPrimeFactors(n));
        Iterable<List<Integer>> possibleExponents = ExhaustiveProvider.INSTANCE.cartesianProduct(
                toList(map(p -> toList(ExhaustiveProvider.INSTANCE.rangeIncreasing(0, p.b)), primeFactors))
        );
        Function<List<Integer>, Integer> f = exponents -> productInteger(
                toList(zipWith(MathUtils::pow, map(q -> q.a, primeFactors), exponents))
        );
        return sort(map(f, possibleExponents));
    }

    /**
     * Returns the factors of a number in ascending order.
     *
     * <ul>
     *  <li>{@code n} must be positive.</li>
     *  <li>The result is a finite {@code List} of {@code BigInteger}s in ascending order.</li>
     * </ul>
     *
     * @param n a number
     * @return the factors of {@code n}
     */
    public static @NotNull List<BigInteger> factors(@NotNull BigInteger n) {
        List<Pair<BigInteger, Integer>> primeFactors = toList(compactPrimeFactors(n));
        Iterable<List<Integer>> possibleExponents = ExhaustiveProvider.INSTANCE.cartesianProduct(
                toList(map(p -> toList(ExhaustiveProvider.INSTANCE.rangeIncreasing(0, p.b)), primeFactors))
        );
        Function<List<Integer>, BigInteger> f = exponents -> productBigInteger(
                toList(zipWith(BigInteger::pow, map(q -> q.a, primeFactors), exponents))
        );
        return sort(map(f, possibleExponents));
    }

    /**
     * Returns the largest number that, when raised to the {@code p}th power, divides {@code n}.
     *
     * <ul>
     *  <li>{@code p} must be positive.</li>
     *  <li>{@code n} must be positive.</li>
     *  <li>The result is positive.</li>
     * </ul>
     *
     * @param p a power
     * @param n a number
     * @return the largest m such that m<sup>{@code p}</sup>|{@code n}
     */
    public static int largestPerfectPowerFactor(int p, int n) {
        if (p < 1) {
            throw new IllegalArgumentException("p must be positive. Invalid p: " + p);
        }
        if (n < 1) {
            throw new IllegalArgumentException("n must be positive. Invalid n: " + n);
        }
        return p == 1 ? n : productInteger(toList(map(q -> pow(q.a, q.b / p), compactPrimeFactors(n))));
    }

    /**
     * Returns the largest number that, when raised to the {@code p}th power, divides {@code n}.
     *
     * <ul>
     *  <li>{@code p} must be positive.</li>
     *  <li>{@code n} must be positive.</li>
     *  <li>The result is positive.</li>
     * </ul>
     *
     * @param p a power
     * @param n a number
     * @return the largest m such that m<sup>{@code p}</sup>|{@code n}
     */
    public static @NotNull BigInteger largestPerfectPowerFactor(int p, @NotNull BigInteger n) {
        if (p < 1) {
            throw new IllegalArgumentException("p must be positive. Invalid p: " + p);
        }
        if (n.signum() != 1) {
            throw new IllegalArgumentException("n must be positive. Invalid n: " + n);
        }
        return p == 1 ? n : productBigInteger(toList(map(q -> q.a.pow(q.b / p), compactPrimeFactors(n))));
    }

    /**
     * Expresses {@code n} as a<sup>b</sup> with the largest possible b. {@code n} must be at least 2 (otherwise there
     * is no upper bound on the exponent).
     *
     * <ul>
     *  <li>{@code n} must be at least 2.</li>
     *  <li>The result is a pair whose first element is at least 2 and whose second element is positive.</li>
     * </ul>
     *
     * @param n a number
     * @return (a, b), where a<sup>b</sup>={@code n} and b is as large as possible
     */
    public static @NotNull Pair<BigInteger, Integer> expressAsPower(@NotNull BigInteger n) {
        if (lt(n, IntegerUtils.TWO)) {
            throw new ArithmeticException("n must be at least 2. Invalid n: " + n);
        }
        for (int p = n.bitLength() - 1; p >= 2; p--) {
            BigInteger root = ceilingRoot(p, n);
            if (root.pow(p).equals(n)) {
                return new Pair<>(root, p);
            }
        }
        return new Pair<>(n, 1);
    }

    /**
     * If {@code n} is a perfect {@code r}th power, returns the {@code r}th root of {@code n}. Otherwise, returns
     * empty.
     *
     * <ul>
     *  <li>{@code n} may be any {@code BigInteger}.</li>
     *  <li>{@code r} must be positive.</li>
     *  <li>If {@code r} is even, {@code this} cannot be negative.</li>
     *  <li>The result may be any {@code BigInteger}, or empty.</li>
     * </ul>
     *
     * @param r the degree of the root extracted from {@code n}
     * @return {@code n}<sup>1/{@code r}</sup>
     */
    public static @NotNull Optional<BigInteger> root(@NotNull BigInteger n, int r) {
        if (r <= 0) {
            throw new ArithmeticException("r must be positive. Invalid r: " + r);
        }
        if (n.equals(BigInteger.ONE) || r == 1) {
            return Optional.of(n);
        }
        if (n.equals(BigInteger.ZERO)) {
            return Optional.of(n);
        }
        BigInteger root;
        if (n.signum() == -1) {
            if ((r & 1) == 0) {
                throw new ArithmeticException("If r is even, n cannot be negative. r: " + r + ", n: " + n);
            } else {
                root = ceilingRoot(r, n.negate()).negate();
            }
        } else {
            root = ceilingRoot(r, n);
        }
        return root.pow(r).equals(n) ? Optional.of(root) : Optional.empty();
    }

    /**
     * If {@code n} is a perfect square, returns the square root of {@code n}. Otherwise, returns empty.
     *
     * <ul>
     *  <li>{@code n} must be non-negative.</li>
     *  <li>The result may be any non-negative {@code BigInteger}, or empty.</li>
     * </ul>
     *
     * @return sqrt({@code n})
     */
    @SuppressWarnings("JavaDoc")
    public static @NotNull Optional<BigInteger> sqrt(@NotNull BigInteger n) {
        if (n.equals(BigInteger.ZERO) || n.equals(BigInteger.ONE)) {
            return Optional.of(n);
        }
        if (n.signum() == -1) {
            throw new ArithmeticException("n cannot be negative. Invalid n: " + n);
        }
        BigInteger root = ceilingRoot(2, n);
        return root.pow(2).equals(n) ? Optional.of(root) : Optional.empty();
    }

    /**
     * If {@code n} is a perfect cibe, returns the cube root of {@code n}. Otherwise, returns empty.
     *
     * <ul>
     *  <li>{@code n} cannot be null.</li>
     *  <li>The result may be any {@code BigInteger}, or empty.</li>
     * </ul>
     *
     * @return cbrt({@code n})
     */
    @SuppressWarnings("JavaDoc")
    public static @NotNull Optional<BigInteger> cbrt(@NotNull BigInteger n) {
        if (n.equals(BigInteger.ZERO) || n.equals(BigInteger.ONE)) {
            return Optional.of(n);
        }
        BigInteger root = n.signum() == -1 ? ceilingRoot(3, n.negate()).negate() : ceilingRoot(3, n);
        return root.pow(3).equals(n) ? Optional.of(root) : Optional.empty();
    }

    /**
     * Returns Euler's totient function of {@code n}, or the number of positive integers less than or equal to
     * {@code n} that are relatively prime to {@code n}.
     *
     * <ul>
     *  <li>{@code n} must be positive.</li>
     *  <li>The result is positive.</li>
     * </ul>
     *
     * @param n a number
     * @return φ({@code n})
     */
    @SuppressWarnings("JavaDoc")
    public static int totient(int n) {
        return productInteger(toList(map(p -> pow(p.a, p.b - 1) * (p.a - 1), compactPrimeFactors(n))));
    }

    /**
     * Returns Euler's totient function of {@code n}, or the number of positive integers less than or equal to
     * {@code n} that are relatively prime to {@code n}.
     *
     * <ul>
     *  <li>{@code n} must be positive.</li>
     *  <li>The result is positive.</li>
     * </ul>
     *
     * @param n a number
     * @return φ({@code n})
     */
    @SuppressWarnings("JavaDoc")
    public static @NotNull BigInteger totient(@NotNull BigInteger n) {
        return productBigInteger(
                toList(map(p -> p.a.pow(p.b - 1).multiply(p.a.subtract(BigInteger.ONE)), compactPrimeFactors(n)))
        );
    }

    /**
     * Returns the inverse of Euler's totient function, or every number whose totient is a given number.
     *
     * <ul>
     *  <li>{@code n} must be positive.</li>
     *  <li>The result is a {@code List} of distinct positive {@code BigInteger}s in ascending order.</li>
     * </ul>
     *
     * @param n a totient
     * @return φ<sup>–1</sup>({@code n})
     */
    public static @NotNull List<BigInteger> inverseTotient(@NotNull BigInteger n) {
        if (n.signum() != 1) {
            throw new IllegalArgumentException();
        }
        return sort(inverseTotientHelper(n, new HashMap<>(), new HashMap<>()));
    }

    /**
     * A helper function for {@link MathUtils#inverseTotient(BigInteger)}. Since the inverse totient algorithm involves
     * several recursive calls to itself and several calls to {@link MathUtils#smallestPrimeFactor(BigInteger)}, we get
     * a considerable speedup by memoizing these calls.
     *
     * <ul>
     *  <li>{@code n} must be positive.</li>
     *  <li>Every entry in {@code cache} must be of the form n -> φ<sup>–1</sup>({@code n}).</li>
     *  <li>Every entry in {@code spfCache} must be of the form n -> smallest prime factor of n.</li>
     *  <li>The result is a {@code List} of distinct positive {@code BigInteger}s in ascending order.</li>
     * </ul>
     *
     * @param n a totient
     * @param cache a cache of inverse totient results
     * @param spfCache a cache of smallest prime factor results
     * @return φ<sup>–1</sup>({@code n})
     */
    private static @NotNull List<BigInteger> inverseTotientHelper(
            @NotNull BigInteger n,
            @NotNull Map<BigInteger, List<BigInteger>> cache,
            @NotNull Map<BigInteger, BigInteger> spfCache
    ) {
        List<BigInteger> result = cache.get(n);
        if (result != null) return result;
        if (n.equals(BigInteger.ONE)) {
            result = Arrays.asList(BigInteger.ONE, IntegerUtils.TWO);
            cache.put(n, result);
            return result;
        }
        if (n.testBit(0)) {
            result = Collections.emptyList();
            cache.put(n, result);
            return result;
        }

        ArrayList<BigInteger> elements = new ArrayList<>();
        for (BigInteger factor : factors(n)) {
            if (factor.equals(BigInteger.ONE)) continue;
            BigInteger p = factor.add(BigInteger.ONE);
            if (isPrime(p)) {
                BigInteger pPower = p;
                BigInteger x = n.divide(factor);
                boolean first = true;
                while (first || x.mod(p).equals(BigInteger.ZERO)) {
                    if (first) {
                        first = false;
                    } else {
                        x = x.divide(p);
                    }
                    if (x.equals(BigInteger.ONE) || !x.testBit(0)) {
                        for (BigInteger y : inverseTotientHelper(x, cache, spfCache)) {
                            if (y.equals(BigInteger.ONE)) {
                                elements.add(pPower);
                            } else {
                                BigInteger spf = spfCache.get(y);
                                if (spf == null) {
                                    spf = smallestPrimeFactor(y);
                                    spfCache.put(y, spf);
                                }
                                if (gt(spf, p)) {
                                    elements.add(y.multiply(pPower));
                                }
                            }
                        }
                    }
                    pPower = pPower.multiply(p);
                }
            }
        }

        List<BigInteger> doubles = toList(map(e -> e.shiftLeft(1), elements));
        elements.addAll(doubles);

        BigInteger x = n;
        int limit = n.getLowestSetBit();
        for (int d = 0; d < limit; d++) {
            x = x.shiftRight(1);
            if (x.equals(BigInteger.ONE) || !x.testBit(0)) {
                for (BigInteger y : inverseTotientHelper(x, cache, spfCache)) {
                    if (y.testBit(0)) {
                        elements.add(y.shiftLeft(d + 2));
                    }
                }
            }
        }
        cache.put(n, elements);
        return elements;
    }

    /**
     * A normal sequence is a sequence over an alphabet ∑ such that for all n, every length-n string over ∑ appears
     * with an equal asymptotic frequency. A base-b greedy normal sequence is a sequence over the symbols 0 to b–1 that
     * tries to be as normal as possible: that is, whenever a new digit is added, an attempt is made to keep the
     * relative frequencies of all substrings in the sequence balanced. If several choices are the "most normal", the
     * lowest allowed digit is added.
     *
     * For example, suppose that b is 10, so that our sequence contains the digits 0 to 9. We begin with the empty
     * sequence. The first ten digits in the sequence must be distinct: otherwise, that frequencies of the length-1
     * subsequences would be prematurely unbalanced. Going by the lowest-digit rule, the sequence begins [0, 1, 2, 3,
     * 4, 5, 6, 7, 8, 9, ...]. Notice that every length-n subsequence appears 0 or 1 times. What should the next digit
     * be? Again, we can default to the lowest digit rule and add 0. How about the next digit? We shouldn't add 1,
     * because then the 2-digit subsequence [0, 1] would appear twice. But 2 is ok. Similarly, we should avoid the
     * subsequences [1, 2], [2, 3], etc. because they have already been used. It turns out that the first 20 terms are
     * [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 2, 1, 3, 5, 4, 6, 8, 7, 9, ...].
     *
     * For the next digit, let's take a closer look at the algorithm. The algorithm considers adding each digit 0 to 9,
     * and assigns each digit a number of points. From the set of digits with maximal points, the lowest digit is
     * selected. A point is given to digit d for each n such that adding d to the sequence would add a "rare" length-n
     * subsequence, where a rare length-n subsequence is one whose frequency in the sequence is minimal accross all
     * length-n subsequences.
     *
     * If we append a 0 to the first 20 terms, we are adding the 21 substrings "0", "90", "790", "8790", etc. This
     * gives the digit 0 a score of 21–1 = 20, since all the substrings except for "90" are rare: "0" is rare since its
     * frequency before adding the next digit is 2, the same as all other length-1 substrings; "790" is rare because
     * its frequency was 0; and all of the longer substrings are rare. But "90" is not rare, because its frequency is
     * 1, greater than the frequency of most length-2 substrings, which is 0.
     *
     * On the other hand, all digits from 1 to 9 get the full score of 21, so we append the lowest, which is 1.
     *
     * <ul>
     *  <li>{@code base} must be at least 2.</li>
     *  <li>The result is infinite and contains no negative numbers.</li>
     * </ul>
     *
     * @param base the exclusive upper bound on the elements in the sequence
     * @return a sequence which tries to be as normal as possible
     */
    public static @NotNull Iterable<Integer> greedyNormalSequence(int base) {
        if (base < 2) {
            throw new IllegalArgumentException("base must be at least 2. Invalid base: " + base);
        }
        return () -> new NoRemoveIterator<Integer>() {
            private final @NotNull List<Map<List<Integer>, Integer>> frequencyMaps = new ArrayList<>();
            private final @NotNull List<Integer> minimumFrequencies = new ArrayList<>();
            private final @NotNull List<Integer> digitsSoFar = new ArrayList<>();
            {
                frequencyMaps.add(new HashMap<>());
                minimumFrequencies.add(0);
            }

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public Integer next() {
                int currentSize = digitsSoFar.size();
                frequencyMaps.add(new HashMap<>());
                minimumFrequencies.add(0);
                int maxPoints = 0;
                int bestDigit = 0;
                for (int i = 0; i < base; i++) {
                    int points = 0;
                    for (int length = 1; length <= currentSize + 1; length++) {
                        List<Integer> segment = new ArrayList<>();
                        for (int j = currentSize - length + 1; j < currentSize; j++) {
                            segment.add(digitsSoFar.get(j));
                        }
                        segment.add(i);
                        Integer frequency = frequencyMaps.get(length).get(segment);
                        if (frequency == null) {
                            frequency = 0;
                        }
                        if (Objects.equals(frequency, minimumFrequencies.get(length))) {
                            points++;
                        }
                    }
                    if (points > maxPoints) {
                        maxPoints = points;
                        bestDigit = i;
                    }
                }
                for (int length = 1; length <= currentSize + 1; length++) {
                    List<Integer> segment = new ArrayList<>();
                    for (int j = currentSize - length + 1; j < currentSize; j++) {
                        segment.add(digitsSoFar.get(j));
                    }
                    segment.add(bestDigit);
                    Map<List<Integer>, Integer> frequencyMap = frequencyMaps.get(length);
                    Integer frequency = frequencyMap.get(segment);
                    if (frequency == null) {
                        frequencyMap.put(segment, 1);
                    } else {
                        frequencyMap.put(segment, frequency + 1);
                    }
                    int minimumFrequency = minimumFrequencies.get(length);
                    boolean minimumFrequencySeen = false;
                    if (ceilingLog(BigInteger.valueOf(base), BigInteger.valueOf(frequencyMap.size() + 1)) <= length) {
                        minimumFrequencySeen = true;
                    } else {
                        for (Map.Entry<List<Integer>, Integer> entry : frequencyMap.entrySet()) {
                            if (entry.getValue() == minimumFrequency) {
                                minimumFrequencySeen = true;
                                break;
                            }
                        }
                    }
                    if (!minimumFrequencySeen) {
                        minimumFrequencies.set(length, minimumFrequency + 1);
                    }
                }
                digitsSoFar.add(bestDigit);
                return bestDigit;
            }
        };
    }
}
