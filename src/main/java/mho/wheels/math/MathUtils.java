package mho.wheels.math;

import mho.wheels.iterables.ExhaustiveProvider;
import mho.wheels.numberUtils.IntegerUtils;
import mho.wheels.structures.Pair;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import java.util.Optional;
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
     * The greatest common divisor of two {@link int}s. If both {@code x} and {@code y} are zero, the result is
     * undefined. Otherwise, the result is positive.
     *
     * <ul>
     *  <li>{@code x} may be any {@code int}.</li>
     *  <li>{@code y} may be any {@code int}.</li>
     *  <li>{@code x} and {@code y} may not both be zero.</li>
     *  <li>The result is non-negative.</li>
     * </ul>
     *
     * @param x the first number
     * @param y the second number
     * @return gcd(x, y)
     */
    public static int gcd(int x, int y) {
        if (x == 0 && y == 0) {
            throw new ArithmeticException("x and y may not both be zero.");
        }
        return positiveGcd(Math.abs(x), Math.abs(y));
    }

    /**
     * The greatest common divisor of two non-negative {@code int}s.
     *
     * <ul>
     *  <li>{@code x} cannot be negative.</li>
     *  <li>{@code y} cannot be negative.</li>
     *  <li>{@code x} and {@code y} may not both be zero.</li>
     *  <li>The result is non-negative.</li>
     * </ul>
     *
     * @param x the first number
     * @param y the second number
     * @return gcd(x, y)
     */
    private static int positiveGcd(int x, int y) {
        //noinspection SuspiciousNameCombination
        return y == 0 ? x : positiveGcd(y, x % y);
    }

    /**
     * The greatest common divisor of two {@link long}s. If both {@code x} and {@code y} are zero, the result is
     * undefined. Otherwise, the result is positive.
     *
     * <ul>
     *  <li>{@code x} may be any {@code long}.</li>
     *  <li>{@code y} may be any {@code long}.</li>
     *  <li>{@code x} and {@code y} may not both be zero.</li>
     *  <li>The result is non-negative.</li>
     * </ul>
     *
     * @param x the first number
     * @param y the second number
     * @return gcd(x, y)
     */
    public static long gcd(long x, long y) {
        if (x == 0L && y == 0L) {
            throw new ArithmeticException("x and y may not both be zero.");
        }
        return positiveGcd(Math.abs(x), Math.abs(y));
    }

    /**
     * The greatest common divisor of two non-negative {@code long}s.
     *
     * <ul>
     *  <li>{@code x} cannot be negative.</li>
     *  <li>{@code y} cannot be negative.</li>
     *  <li>{@code x} and {@code y} may not both be zero.</li>
     *  <li>The result is non-negative.</li>
     * </ul>
     *
     * @param x the first number
     * @param y the second number
     * @return gcd(x, y)
     */
    private static long positiveGcd(long x, long y) {
        //noinspection SuspiciousNameCombination
        return y == 0 ? x : positiveGcd(y, x % y);
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
     * @return lcm(x, y)
     */
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
     * The factorial function {@code n}!
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
        if (n < 0)
            throw new ArithmeticException("cannot take factorial of " + n);
        return productBigInteger(range(BigInteger.ONE, BigInteger.valueOf(n)));
    }

    /**
     * The factorial function {@code n}!
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
        if (n.signum() == -1)
            throw new ArithmeticException("cannot take factorial of " + n);
        return productBigInteger(range(BigInteger.ONE, n));
    }

    /**
     * The subfactorial function !{@code n}
     *
     * <ul>
     *  <li>{@code n} cannot be negative.</li>
     *  <li>The result is a subfactorial (rencontres number).</li>
     * </ul>
     *
     * @param n the argument
     * @return !{@code n}
     */
    public static @NotNull BigInteger subfactorial(int n) {
        if (n < 0)
            throw new ArithmeticException("cannot take subfactorial of " + n);
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
     *  <li>The result is a subfactorial (rencontres number).</li>
     * </ul>
     *
     * @param n the argument
     * @return !{@code n}
     */
    public static @NotNull BigInteger subfactorial(@NotNull BigInteger n) {
        if (n.signum() == -1)
            throw new ArithmeticException("cannot take subfactorial of " + n);
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
        if (lt(base, IntegerUtils.TWO)) {
            throw new ArithmeticException("Base must be at least 2. Invalid base: " + base);
        }
        if (x.signum() != 1) {
            throw new ArithmeticException("x must be positive. Invalid x: " + x);
        }
        //noinspection SuspiciousNameCombination
        return fastGrowingCeilingInverse(i -> base.pow(i.intValueExact()), x, BigInteger.ZERO, x); //very loose bound
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
        //noinspection SuspiciousNameCombination
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
        for (int i = 3; ; i += 2) {
            int square = i * i;
            if (square > n || square < 0) break;
            if (PRIME_SIEVE.get(i) && n % i == 0) return i;
        }
        return n;
    }

    public static @NotNull BigInteger smallestPrimeFactor(@NotNull BigInteger n) {
        if (le(n, BigInteger.valueOf(Integer.MAX_VALUE))) {
            return BigInteger.valueOf(smallestPrimeFactor(n.intValueExact()));
        }
        if (!n.testBit(0)) return IntegerUtils.TWO;
        for (int i = 3; i < PRIME_SIEVE_SIZE; i += 2) {
            BigInteger bi = BigInteger.valueOf(i);
            if (gt(bi.pow(2), n)) return n;
            if (PRIME_SIEVE.get(i) && n.mod(bi).equals(BigInteger.ZERO)) return bi;
        }
        BigInteger limit = ceilingRoot(IntegerUtils.TWO, n);
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

    public static @NotNull Iterable<Pair<Integer, Integer>> compactPrimeFactors(int n) {
        return countAdjacent(primeFactors(n));
    }

    public static @NotNull Iterable<Pair<BigInteger, Integer>> compactPrimeFactors(@NotNull BigInteger n) {
        return countAdjacent(primeFactors(n));
    }

    public static @NotNull List<Integer> factors(int n) {
        List<Pair<Integer, Integer>> cpf = toList(compactPrimeFactors(n));
        Iterable<List<Integer>> possibleExponents = ExhaustiveProvider.INSTANCE.controlledListsLex(
                toList(map(p -> range(0, p.b), cpf))
        );
        Function<List<Integer>, Integer> f = exponents -> productInteger(
                zipWith((x, y) -> BigInteger.valueOf(x).pow(y).intValueExact(), map(q -> q.a, cpf), exponents)
        );
        return sort(map(f, possibleExponents));
    }

    public static @NotNull List<BigInteger> factors(@NotNull BigInteger n) {
        List<Pair<BigInteger, Integer>> cpf = toList(compactPrimeFactors(n));
        Iterable<List<Integer>> possibleExponents = ExhaustiveProvider.INSTANCE.controlledListsLex(
                toList(map(p -> range(0, p.b), cpf))
        );
        Function<List<Integer>, BigInteger> f = exponents -> productBigInteger(
                zipWith(BigInteger::pow, map(q -> q.a, cpf), exponents)
        );
        return sort(map(f, possibleExponents));
    }

    public static @NotNull Iterable<Integer> intPrimes() {
        @SuppressWarnings("ConstantConditions")
        int start = (PRIME_SIEVE_SIZE & 1) == 0 ? PRIME_SIEVE_SIZE + 1 : PRIME_SIEVE_SIZE;
        return concat(
                filter(PRIME_SIEVE::get, range(2, PRIME_SIEVE_SIZE - 1)),
                filter(MathUtils::isPrime, rangeBy(start, 2))
        );
    }

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
}
