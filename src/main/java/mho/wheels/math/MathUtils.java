package mho.wheels.math;

import mho.wheels.iterables.ExhaustiveProvider;
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
    private static BitSet PRIME_SIEVE;

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
     *  <li>The result is non-negative.</li>
     * </ul>
     *
     * @param x the first number
     * @param y the second number
     * @return gcd({@code x}, {@code y})
     */
    @SuppressWarnings("JavaDoc")
    public static int gcd(int x, int y) {
        return nonNegativeGcd(Math.abs(x), Math.abs(y));
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
     *  <li>The result is non-negative.</li>
     * </ul>
     *
     * @param x the first number
     * @param y the second number
     * @return gcd({@code x}, {@code y})
     */
    @SuppressWarnings("JavaDoc")
    public static long gcd(long x, long y) {
        return nonNegativeGcd(Math.abs(x), Math.abs(y));
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
     * The greatest common divisor of a {@code List} of {@code BigInteger}s. The GCD of an empty {@code Iterable}, or
     * an {@code Iterable} containing only zeros, is zero.
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
            halves.add(h.and(BigInteger.ONE).equals(BigInteger.ZERO) ? h.subtract(BigInteger.ONE) : h);
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
        for (BigInteger i : range(BigInteger.ONE, n)) {
            sf = sf.multiply(i);
            if (i.getLowestSetBit() != 0) {
                sf = sf.add(BigInteger.ONE);
            } else {
                sf = sf.subtract(BigInteger.ONE);
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
        return productBigInteger(toList(range(x.subtract(BigInteger.valueOf(n - 1)), x)));
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
        return sumBigInteger(
                toList(map(k -> binomialCoefficient(BigInteger.valueOf(n), k), range(minSize, n)))
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
        for (int x : range(min, max)) {
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
     * Initializes the prime sieve, if it hasn't already been initialized.
     */
    private static void ensurePrimeSieveInitialized() {
        if (PRIME_SIEVE != null) return;
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
        ensurePrimeSieveInitialized();
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
        ensurePrimeSieveInitialized();
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
                rangeUp(BigInteger.valueOf(PRIME_SIEVE_SIZE / 6))
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
                toList(map(p -> toList(range(0, p.b)), primeFactors))
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
                toList(map(p -> toList(range(0, p.b)), primeFactors))
        );
        Function<List<Integer>, BigInteger> f = exponents -> productBigInteger(
                toList(zipWith(BigInteger::pow, map(q -> q.a, primeFactors), exponents))
        );
        return sort(map(f, possibleExponents));
    }

    /**
     * Returns all prime {@code int}s in ascending order.
     *
     * Length is 105,097,565
     *
     * @return prime {@code int}s
     */
    public static @NotNull Iterable<Integer> intPrimes() {
        @SuppressWarnings("ConstantConditions")
        int start = (PRIME_SIEVE_SIZE & 1) == 0 ? PRIME_SIEVE_SIZE + 1 : PRIME_SIEVE_SIZE;
        ensurePrimeSieveInitialized();
        return concat(
                filter(PRIME_SIEVE::get, range(2, PRIME_SIEVE_SIZE - 1)),
                filter(MathUtils::isPrime, rangeBy(start, 2))
        );
    }

    /**
     * Returns all prime {@code BigInteger}s in ascending order.
     *
     * Length is infinite
     *
     * @return prime {@code BigInteger}s
     */
    public static @NotNull Iterable<BigInteger> primes() {
        Iterable<BigInteger> candidates = concatMap(
                i -> {
                    BigInteger sixI = i.multiply(BigInteger.valueOf(6));
                    return Arrays.asList(sixI.subtract(BigInteger.ONE), sixI.add(BigInteger.ONE));
                },
                rangeUp(BigInteger.valueOf(PRIME_SIEVE_SIZE / 6))
        );
        //noinspection Convert2MethodRef
        return concat(map(i -> BigInteger.valueOf(i), intPrimes()), filterInfinite(MathUtils::isPrime, candidates));
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
        if (n.and(BigInteger.ONE).equals(BigInteger.ONE)) {
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
                    if (x.equals(BigInteger.ONE) || x.and(BigInteger.ONE).equals(BigInteger.ZERO)) {
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
            if (x.equals(BigInteger.ONE) || x.and(BigInteger.ONE).equals(BigInteger.ZERO)) {
                for (BigInteger y : inverseTotientHelper(x, cache, spfCache)) {
                    if (y.and(BigInteger.ONE).equals(BigInteger.ONE)) {
                        elements.add(y.shiftLeft(d + 2));
                    }
                }
            }
        }
        cache.put(n, elements);
        return elements;
    }
}
