package mho.wheels.iterables;

import mho.wheels.math.MathUtils;
import mho.wheels.ordering.Ordering;
import mho.wheels.random.IsaacPRNG;
import mho.wheels.structures.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.*;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.ordering.Ordering.lt;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * <p>A {@code RandomProvider} produces {@code Iterable}s that randomly generate some set of values with a specified
 * distribution. A {@code RandomProvider} is immutable and deterministic. The source of its randomness is a
 * {@code int[]} seed. It contains two scale parameters which some of the distributions depend on; the exact
 * relationship between the parameters and the distributions is specified in the distribution's documentation.
 *
 * <p>{@code RandomProvider} uses the cryptographically-secure ISAAC pseudorandom number generator, implemented in
 * {@link mho.wheels.random.IsaacPRNG}.
 *
 * <p>Note that sometimes the documentation will say things like "returns an {@code Iterable} containing all
 * {@code String}s". This cannot strictly be true, since {@link java.util.Random} has a finite period, and will
 * therefore produce only a finite number of {@code String}s. So in general, the documentation often pretends that the
 * source of randomness is perfect (but still deterministic).
 */
public final class RandomProvider extends IterableProvider {
    /**
     * The default value of {@code scale}.
     */
    private static final int DEFAULT_SCALE = 32;

    /**
     * The default value of {@code secondaryScale}.
     */
    private static final int DEFAULT_SECONDARY_SCALE = 8;

    /**
     * Sometimes a "special element" (for example, null) is inserted into an {@code Iterable} with some probability.
     * That probability is 1/{@code SPECIAL_ELEMENT_RATIO}.
     */
    private static final int SPECIAL_ELEMENT_RATIO = 50;

    /**
     * A list of numbers which determines exactly which values will be deterministically output over the life of
     * {@code this}. It must have length {@link mho.wheels.random.IsaacPRNG#SIZE}.
     */
    private @NotNull List<Integer> seed;

    /**
     * A parameter that determines the size of some of the generated objects. Cannot be negative.
     */
    private int scale = DEFAULT_SCALE;

    /**
     * Another parameter that determines the size of some of the generated objects. Cannot be negative.
     */
    private int secondaryScale = DEFAULT_SECONDARY_SCALE;

    /**
     * A {@code RandomProvider} used for testing. This allows for deterministic testing without manually setting up a
     * lengthy seed each time.
     */
    public static final @NotNull RandomProvider EXAMPLE;
    static {
        IsaacPRNG prng = IsaacPRNG.example();
        List<Integer> seed = new ArrayList<>();
        for (int i = 0; i < IsaacPRNG.SIZE; i++) {
            seed.add(prng.nextInt());
        }
        EXAMPLE = new RandomProvider(seed);
    }

    /**
     * Constructs a {@code RandomProvider} with a seed generated from the current system time.
     *
     * <ul>
     *  <li>(conjecture) Any {@code RandomProvider} with default {@code scale} and {@code secondaryScale} may be
     *  constructed with this constructor.</li>
     * </ul>
     */
    public RandomProvider() {
        IsaacPRNG prng = new IsaacPRNG();
        seed = new ArrayList<>();
        for (int i = 0; i < IsaacPRNG.SIZE; i++) {
            seed.add(prng.nextInt());
        }
    }

    /**
     * Constructs a {@code RandomProvider} with a given seed.
     *
     * <ul>
     *  <li>{@code seed} must have length {@link mho.wheels.random.IsaacPRNG#SIZE}.</li>
     *  <li>Any {@code RandomProvider} with default {@code scale} and {@code secondaryScale} may be constructed with
     *  this constructor.</li>
     * </ul>
     *
     * @param seed the source of randomness
     */
    public RandomProvider(@NotNull List<Integer> seed) {
        if (seed.size() != IsaacPRNG.SIZE) {
            throw new IllegalArgumentException("seed must have length mho.wheels.random.IsaacPRNG#SIZE, which is " +
                    IsaacPRNG.SIZE + ". Length of invalid seed: " + seed.size());
        }
        this.seed = seed;
    }

    /**
     * Returns {@code this}'s scale parameter.
     *
     * <ul>
     *  <li>The result is non-negative.</li>
     * </ul>
     *
     * @return the scale parameter of {@code this}
     */
    public int getScale() {
        return scale;
    }

    /**
     * Returns {@code this}'s other scale parameter.
     *
     * <ul>
     *  <li>The result is non-negative.</li>
     * </ul>
     *
     * @return the other scale parameter of {@code this}
     */
    public int getSecondaryScale() {
        return secondaryScale;
    }

    /**
     * Returns {@code this}'s seed. Makes a defensive copy.
     *
     * <ul>
     *  <li>The result is an array of {@link mho.wheels.random.IsaacPRNG#SIZE} {@code int}s.</li>
     * </ul>
     *
     * @return the seed of {@code this}
     */
    public @NotNull List<Integer> getSeed() {
        return toList(seed);
    }

    /**
     * A {@code RandomProvider} with the same fields as {@code this}.
     *
     * <ul>
     *  <li>The result is not null.</li>
     * </ul>
     *
     * @return A copy of {@code this}.
     */
    private @NotNull RandomProvider copy() {
        RandomProvider copy = new RandomProvider(seed);
        copy.scale = scale;
        copy.secondaryScale = secondaryScale;
        return copy;
    }

    /**
     * A new {@code RandomProvider} with a different, deterministically-chosen seed, and the same scale and secondary
     * scale. Use this method to avoid correlations between pseudorandom sequences. For example, consider the problem
     * of generating pairs of positive and negative {@code Integer}s given a {@code RandomProvider P}. Don't use
     * {@code P.pairs(P.positiveIntegers(), P.negativeIntegers())}; this will give pairs of the form (i, –i). Instead,
     * use {@code P.pairs(P.positiveIntegers(), P.alt().negativeIntegers())}.
     *
     * <ul>
     *  <li>The result is not null.</li>
     * </ul>
     *
     * @return another {@code RandomProvider}
     */
    @Override
    public @NotNull RandomProvider alt() {
        return randomProvidersDefault().iterator().next().withScale(scale).withSecondaryScale(secondaryScale);
    }

    /**
     * A {@code RandomProvider} with the same fields as {@code this} except for a new scale.
     *
     * <ul>
     *  <li>{@code scale} cannot be negative.</li>
     *  <li>The result is not null.</li>
     * </ul>
     *
     * @param scale the new scale
     * @return A copy of {@code this} with a new scale
     */
    @Override
    public @NotNull RandomProvider withScale(int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("scale cannot be negative. Invalid scale: " + scale);
        }
        RandomProvider copy = copy();
        copy.scale = scale;
        return copy;
    }

    /**
     * A {@code RandomProvider} with the same fields as {@code this} except for a new secondary scale.
     *
     * <ul>
     *  <li>{@code secondaryScale} cannot be negative.</li>
     *  <li>The result is not null.</li>
     * </ul>
     *
     * @param secondaryScale the new secondary scale
     * @return A copy of {@code this} with a new secondary scale
     */
    @Override
    public @NotNull RandomProvider withSecondaryScale(int secondaryScale) {
        if (secondaryScale < 0) {
            throw new IllegalArgumentException("secondaryScale cannot be negative. Invalid secondaryScale: " +
                    secondaryScale);
        }
        RandomProvider copy = copy();
        copy.secondaryScale = secondaryScale;
        return copy;
    }

    /**
     * An {@code Iterator} that generates both {@code Boolean}s from a uniform distribution. Does not support removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<Boolean> booleans() {
        return concatMap(i -> map(p -> (i & (1 << p)) != 0, IterableUtils.range(0, 31)), integers());
    }

    /**
     * An {@code Iterable} that generates all {@code Integer}s from a uniform distribution. Does not support removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<Integer> integers() {
        return () -> new NoRemoveIterator<Integer>() {
            private @NotNull IsaacPRNG prng = new IsaacPRNG(seed);

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public Integer next() {
                return prng.nextInt();
            }
        };
    }

    /**
     * An {@code Iterable} that generates all {@code Long}s from a uniform distribution. Does not support removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<Long> longs() {
        return () -> new NoRemoveIterator<Long>() {
            private @NotNull IsaacPRNG prng = new IsaacPRNG(seed);

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public Long next() {
                int a = prng.nextInt();
                int b = prng.nextInt();
                return (long) a << 32 | b & 0xffffffffL;
            }
        };
    }

    /**
     * Returns the next non-negative {@code BigInteger} of up to {@code bits} bits generated by {@code prng}.
     *
     * <ul>
     *  <li>{@code prng} cannot be null.</li>
     *  <li>{@code bits} must be positive.</li>
     *  <li>The result is non-negative.</li>
     * </ul>
     *
     * @param prng the {@code IsaacPRNG} generating a {@code BigInteger}
     * @param bits the maximum bitlength of the generated {@code BigInteger}
     * @return A random {@code BigInteger}
     */
    private static @NotNull BigInteger nextBigInteger(@NotNull IsaacPRNG prng, int bits) {
        byte[] bytes = new byte[bits / 8 + 1];
        int x = 0;
        for (int i = 0; i < bytes.length; i++) {
            if (i % 4 == 0) {
                x = prng.nextInt();
            }
            bytes[i] = (byte) x;
            x >>= 8;
        }
        bytes[0] &= (1 << (bits % 8)) - 1;
        return new BigInteger(bytes);
    }

    /**
     * An {@code Iterable} that generates a {@code Integer}s taken from a uniform distribution between 0 and
     * 2<sup>{@code bits}</sup>–1, inclusive.
     *
     * <ul>
     *  <li>{@code bits} must be greater than 0 and less than 32.</li>
     *  <li>The result is an infinite, non-removable {@code Iterable} containing non-negative {@code Integer}s.</li>
     * </ul>
     *
     * Length is infinite
     *
     * @param bits the maximum number of bits of any element in the output {@code Iterable}
     * @return uniformly-distributed positive {@code Integer}s with up to {@code bits} bits
     */
    private @NotNull Iterable<Integer> randomIntsPow2(int bits) {
        int mask = (1 << bits) - 1;
        return map(i -> i & mask, integers());
    }

    /**
     * An {@code Iterable} that generates {@code Long}s taken from a uniform distribution between 0 and
     * 2<sup>{@code bits}</sup>–1, inclusive.
     *
     * <ul>
     *  <li>{@code bits} must be greater than 0 and less than 64.</li>
     *  <li>The result is an infinite, non-removable {@code Iterable} containing non-negative {@code Long}s.</li>
     * </ul>
     *
     * Length is infinite
     *
     * @param bits the maximum number of bits of any element in the output {@code Iterable}
     * @return uniformly-distributed positive {@code Long}s with up to {@code bits} bits
     */
    private @NotNull Iterable<Long> randomLongsPow2(int bits) {
        long mask = (1L << bits) - 1;
        return map(l -> l & mask, longs());
    }

    /**
     * An {@code Iterable} that generates {@code BigInteger}s taken from a uniform distribution between 0 and
     * 2<sup>{@code bits}</sup>–1, inclusive.
     *
     * <ul>
     *  <li>{@code bits} must be positive.</li>
     *  <li>The result is an infinite, non-removable {@code Iterable} containing non-negative {@code BigInteger}s.</li>
     * </ul>
     *
     * Length is infinite
     *
     * @param bits the maximum number of bits of any element in the output {@code Iterable}
     * @return uniformly-distributed positive {@code BigInteger}s with up to {@code bits} bits
     */
    private @NotNull Iterable<BigInteger> randomBigIntegersPow2(int bits) {
        return () -> new NoRemoveIterator<BigInteger>() {
            private @NotNull IsaacPRNG prng = new IsaacPRNG(seed);

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public BigInteger next() {
                return nextBigInteger(prng, bits);
            }
        };
    }

    /**
     * An {@code Iterable} that generates {@code Integer}s taken from a uniform distribution between 0 and {@code n}–1,
     * inclusive.
     *
     * <ul>
     *  <li>{@code n} must be positive.</li>
     *  <li>The result is an infinite, non-removable {@code Iterable} containing non-negative {@code Integer}s.</li>
     * </ul>
     *
     * Length is infinite
     *
     * @param n one more than the maximum value of any element in the output {@code Iterable}
     * @return uniformly-distributed positive {@code Integer}s less than {@code n}
     */
    private @NotNull Iterable<Integer> randomInts(int n) {
        return filter(
                i -> i < n,
                randomIntsPow2(MathUtils.ceilingLog(BigInteger.valueOf(2), BigInteger.valueOf(n)).intValueExact())
        );
    }

    /**
     * An {@code Iterable} that generates {@code Long}s taken from a uniform distribution between 0 and {@code n}–1,
     * inclusive.
     *
     * <ul>
     *  <li>{@code n} must be positive.</li>
     *  <li>The result is an infinite, non-removable {@code Iterable} containing non-negative {@code Long}s.</li>
     * </ul>
     *
     * Length is infinite
     *
     * @param n one more than the maximum value of any element in the output {@code Iterable}
     * @return uniformly-distributed positive {@code Long}s less than {@code n}
     */
    private @NotNull Iterable<Long> randomLongs(long n) {
        return filter(
                l -> l < n,
                randomLongsPow2(MathUtils.ceilingLog(BigInteger.valueOf(2), BigInteger.valueOf(n)).intValueExact())
        );
    }

    /**
     * An {@code Iterable} that generates {@code BigInteger}s taken from a uniform distribution between 0 and
     * {@code n}–1, inclusive.
     *
     * <ul>
     *  <li>{@code n} must be positive.</li>
     *  <li>The result is an infinite, non-removable {@code Iterable} containing non-negative {@code BigInteger}s.</li>
     * </ul>
     *
     * Length is infinite
     *
     * @param n one more than the maximum value of any element in the output {@code Iterable}
     * @return uniformly-distributed positive {@code BigInteger}s less than {@code n}
     */
    private @NotNull Iterable<BigInteger> randomBigIntegers(@NotNull BigInteger n) {
        return filter(
                i -> lt(i, n),
                randomBigIntegersPow2(MathUtils.ceilingLog(BigInteger.valueOf(2), n).intValueExact())
        );
    }

    /**
     * An {@code Iterable} that uniformly generates elements from a (possibly null-containing) list.
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result is non-removable and either empty or infinite.</li>
     * </ul>
     *
     * Length is 0 if {@code xs} is empty, infinite otherwise
     *
     * @param xs the source list
     * @param <T> the type of {@code xs}'s elements
     * @return uniformly-distributed elements of {@code xs}
     */
    @Override
    public @NotNull <T> Iterable<T> uniformSample(@NotNull List<T> xs) {
        return map(xs::get, range(0, xs.size() - 1));
    }

    /**
     * An {@code Iterable} that uniformly generates {@code Character}s from a {@code String}.
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result is an empty or infinite non-removable {@code Iterable} containing {@code Character}s.</li>
     * </ul>
     *
     * Length is 0 if {@code s} is empty, infinite otherwise
     *
     * @param s the source {@code String}
     * @return uniformly-distributed {@code Character}s from {@code s}
     */
    @Override
    public @NotNull Iterable<Character> uniformSample(@NotNull String s) {
        return map(s::charAt, range(0, s.length() - 1));
    }

    /**
     * An {@code Iterator} that generates all {@code Ordering}s from a uniform distribution. Does not support removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<Ordering> orderings() {
        return uniformSample(toList(ExhaustiveProvider.INSTANCE.orderings()));
    }

    /**
     * An {@code Iterable} that generates all {@code RoundingMode}s from a uniform distribution. Does not support
     * removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<RoundingMode> roundingModes() {
        return uniformSample(toList(ExhaustiveProvider.INSTANCE.roundingModes()));
    }

    /**
     * An {@code Iterable} that generates all positive {@code Byte}s from a uniform distribution. Does not support
     * removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<Byte> positiveBytes() {
        return filter(b -> b != 0, naturalBytes());
    }

    /**
     * An {@code Iterable} that generates all positive {@code Short}s from a uniform distribution. Does not support
     * removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<Short> positiveShorts() {
        return filter(s -> s != 0, naturalShorts());
    }

    /**
     * An {@code Iterable} that generates all positive {@code Integer}s from a uniform distribution. Does not support
     * removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<Integer> positiveIntegers() {
        return filter(i -> i > 0, integers());
    }

    /**
     * An {@code Iterable} that generates all positive {@code Long}s from a uniform distribution from a uniform
     * distribution. Does not support removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<Long> positiveLongs() {
        return filter(l -> l > 0, longs());
    }

    /**
     * An {@code Iterable} that generates all negative {@code Byte}s from a uniform distribution. Does not support
     * removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<Byte> negativeBytes() {
        return map(b -> (byte) ~b, naturalBytes());
    }

    /**
     * An {@code Iterable} that generates all negative {@code Short}s from a uniform distribution. Does not support
     * removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<Short> negativeShorts() {
        return map(s -> (short) ~s, naturalShorts());
    }

    /**
     * An {@code Iterable} that generates all negative {@code Integer}s from a uniform distribution. Does not support
     * removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<Integer> negativeIntegers() {
        return filter(i -> i < 0, integers());
    }

    /**
     * An {@code Iterable} that generates all negative {@code Long}s from a uniform distribution. Does not support
     * removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<Long> negativeLongs() {
        return filter(l -> l < 0, longs());
    }

    /**
     * An {@code Iterable} that generates all natural {@code Byte}s (including 0) from a uniform distribution. Does
     * not support removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<Byte> naturalBytes() {
        return map(Integer::byteValue, randomIntsPow2(7));
    }

    /**
     * An {@code Iterable} that generates all natural {@code Short}s (including 0) from a uniform distribution. Does
     * not support removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<Short> naturalShorts() {
        return map(Integer::shortValue, randomIntsPow2(15));
    }

    /**
     * An {@code Iterable} that generates all natural {@code Integer}s (including 0) from a uniform distribution.
     * Does not support removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<Integer> naturalIntegers() {
        return randomIntsPow2(31);
    }

    /**
     * An {@code Iterable} that generates all natural {@code Long}s (including 0) from a uniform distribution. Does
     * not support removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<Long> naturalLongs() {
        return randomLongsPow2(63);
    }

    /**
     * An {@code Iterable} that generates all {@code Byte}s from a uniform distribution. Does not support removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<Byte> bytes() {
        return map(Integer::byteValue, randomIntsPow2(8));
    }

    /**
     * An {@code Iterable} that generates all {@code Short}s from a uniform distribution. Does not support removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<Short> shorts() {
        return map(Integer::shortValue, randomIntsPow2(16));
    }

    /**
     * An {@code Iterable} that generates all ASCII {@code Character}s from a uniform distribution. Does not support
     * removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<Character> asciiCharacters() {
        return map(i -> (char) (int) i, randomIntsPow2(7));
    }

    /**
     * An {@code Iterable} that generates all {@code Character}s from a uniform distribution. Does not support
     * removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<Character> characters() {
        return map(i -> (char) (int) i, randomIntsPow2(16));
    }

    /**
     * An {@code Iterable} that uniformly generates {@code Byte}s greater than or equal to {@code a}.
     *
     * <ul>
     *  <li>{@code a} may be any {@code byte}.</li>
     *  <li>The result is an infinite, non-removable {@code Iterable} containing {@code Byte}s.</li>
     * </ul>
     *
     * Length is infinite
     *
     * @param a the inclusive lower bound of the generated elements
     * @return uniformly-distributed {@code Byte}s greater than or equal to {@code a}
     */
    @Override
    public @NotNull Iterable<Byte> rangeUp(byte a) {
        return map(i -> (byte) (i + a), randomInts((1 << 7) - a));
    }

    /**
     * An {@code Iterable} that uniformly generates {@code Short}s greater than or equal to {@code a}.
     *
     * <ul>
     *  <li>{@code a} may be any {@code short}.</li>
     *  <li>The result is an infinite, non-removable {@code Iterable} containing {@code Short}s.</li>
     * </ul>
     *
     * Length is infinite
     *
     * @param a the inclusive lower bound of the generated elements
     * @return uniformly-distributed {@code Short}s greater than or equal to {@code a}
     */
    @Override
    public @NotNull Iterable<Short> rangeUp(short a) {
        return map(i -> (short) (i + a), randomInts((1 << 15) - a));
    }

    /**
     * An {@code Iterable} that uniformly generates {@code Integer}s greater than or equal to {@code a}.
     *
     * <ul>
     *  <li>{@code a} may be any {@code int}.</li>
     *  <li>The result is an infinite, non-removable {@code Iterable} containing {@code Integer}s.</li>
     * </ul>
     *
     * Length is infinite
     *
     * @param a the inclusive lower bound of the generated elements
     * @return uniformly-distributed {@code Integer}s greater than or equal to {@code a}
     */
    @Override
    public @NotNull Iterable<Integer> rangeUp(int a) {
        return map(l -> (int) (l + a), randomLongs((1L << 31) - a));
    }

    /**
     * An {@code Iterable} that uniformly generates {@code Long}s greater than or equal to {@code a}.
     *
     * <ul>
     *  <li>{@code a} may be any {@code long}.</li>
     *  <li>The result is an infinite, non-removable {@code Iterable} containing {@code Long}s.</li>
     * </ul>
     *
     * Length is infinite
     *
     * @param a the inclusive lower bound of the generated elements
     * @return uniformly-distributed {@code Long}s greater than or equal to {@code a}
     */
    @Override
    public @NotNull Iterable<Long> rangeUp(long a) {
        return map(
                i -> i.add(BigInteger.valueOf(a)).longValueExact(),
                randomBigIntegers(BigInteger.ONE.shiftLeft(63).subtract(BigInteger.valueOf(a)))
        );
    }

    //todo docs
    @Override
    public @NotNull Iterable<BigInteger> rangeUp(@NotNull BigInteger a) {
        return map(i -> i.add(a), naturalBigIntegers());
    }

    /**
     * An {@code Iterable} that uniformly generates {@code Characters}s greater than or equal to {@code a}.
     *
     * <ul>
     *  <li>{@code a} may be any {@code char}.</li>
     *  <li>The result is an infinite, non-removable {@code Iterable} containing {@code Character}s.</li>
     * </ul>
     *
     * Length is infinite
     *
     * @param a the inclusive lower bound of the generated elements
     * @return uniformly-distributed {@code Character}s greater than or equal to {@code a}
     */
    @Override
    public @NotNull Iterable<Character> rangeUp(char a) {
        return map(i -> (char) (i + a), randomInts((1 << 16) - a));
    }

    /**
     * An {@code Iterable} that uniformly generates {@code Byte}s less than or equal to {@code a}.
     *
     * <ul>
     *  <li>{@code a} may be any {@code byte}.</li>
     *  <li>The result is an infinite, non-removable {@code Iterable} containing {@code Byte}s.</li>
     * </ul>
     *
     * Length is infinite
     *
     * @param a the inclusive upper bound of the generated elements
     * @return uniformly-distributed {@code Byte}s less than or equal to {@code a}
     */
    @Override
    public @NotNull Iterable<Byte> rangeDown(byte a) {
        int offset = 1 << 7;
        return map(i -> (byte) (i - offset), randomInts(a + offset + 1));
    }

    /**
     * An {@code Iterable} that uniformly generates {@code Short}s less than or equal to {@code a}.
     *
     * <ul>
     *  <li>{@code a} may be any {@code short}.</li>
     *  <li>The result is an infinite, non-removable {@code Iterable} containing {@code Short}s.</li>
     * </ul>
     *
     * Length is infinite
     *
     * @param a the inclusive upper bound of the generated elements
     * @return uniformly-distributed {@code Short}s less than or equal to {@code a}
     */
    @Override
    public @NotNull Iterable<Short> rangeDown(short a) {
        int offset = 1 << 15;
        return map(i -> (short) (i - offset), randomInts(a + offset + 1));
    }

    /**
     * An {@code Iterable} that uniformly generates {@code Integer}s less than or equal to {@code a}.
     *
     * <ul>
     *  <li>{@code a} may be any {@code int}.</li>
     *  <li>The result is an infinite, non-removable {@code Iterable} containing {@code Integer}s.</li>
     * </ul>
     *
     * Length is infinite
     *
     * @param a the inclusive upper bound of the generated elements
     * @return uniformly-distributed {@code Integer}s less than or equal to {@code a}
     */
    @Override
    public @NotNull Iterable<Integer> rangeDown(int a) {
        long offset = 1L << 31;
        return map(l -> (int) (l - offset), randomLongs(a + offset + 1));
    }

    /**
     * An {@code Iterable} that uniformly generates {@code Long}s less than or equal to {@code a}.
     *
     * <ul>
     *  <li>{@code a} may be any {@code long}.</li>
     *  <li>The result is an infinite, non-removable {@code Iterable} containing {@code Long}s.</li>
     * </ul>
     *
     * Length is infinite
     *
     * @param a the inclusive upper bound of the generated elements
     * @return uniformly-distributed {@code Long}s less than or equal to {@code a}
     */
    @Override
    public @NotNull Iterable<Long> rangeDown(long a) {
        return map(
                i -> i.subtract(BigInteger.ONE.shiftLeft(63)).longValueExact(),
                randomBigIntegers(BigInteger.valueOf(a).add(BigInteger.ONE).add(BigInteger.ONE.shiftLeft(63)))
        );
    }

    //todo docs
    @Override
    public @NotNull Iterable<BigInteger> rangeDown(@NotNull BigInteger a) {
        return map(i -> i.add(BigInteger.ONE).add(a), negativeBigIntegers());
    }

    /**
     * An {@code Iterable} that uniformly generates {@code Character}s less than or equal to {@code a}.
     *
     * <ul>
     *  <li>{@code a} may be any {@code char}.</li>
     *  <li>The result is an infinite, non-removable {@code Iterable} containing {@code Character}s.</li>
     * </ul>
     *
     * Length is infinite
     *
     * @param a the inclusive upper bound of the generated elements
     * @return uniformly-distributed {@code Character}s less than or equal to {@code a}
     */
    @Override
    public @NotNull Iterable<Character> rangeDown(char a) {
        return map(i -> (char) (int) i, randomInts(a + 1));
    }

    /**
     * An {@code Iterable} that uniformly generates {@code Byte}s between {@code a} and {@code b}, inclusive. If
     * {@code a}{@literal >}{@code b}, an empty {@code Iterable} is returned.
     *
     * <ul>
     *  <li>{@code a} may be any {@code byte}.</li>
     *  <li>{@code b} may be any {@code byte}.</li>
     *  <li>The result is an infinite, non-removable {@code Iterable} containing {@code Byte}s.</li>
     * </ul>
     *
     * Length is infinite if a≤b, 0 otherwise
     *
     * @param a the inclusive lower bound of the generated elements
     * @param b the inclusive upper bound of the generated elements
     * @return uniformly-distributed {@code Byte}s between {@code a} and {@code b}, inclusive
     */
    @Override
    public @NotNull Iterable<Byte> range(byte a, byte b) {
        if (a > b) return Collections.emptyList();
        return map(i -> (byte) (i + a), randomInts((int) b - a + 1));
    }

    /**
     * An {@code Iterable} that uniformly generates {@code Short}s between {@code a} and {@code b}, inclusive. If
     * {@code a}{@literal >}{@code b}, an empty {@code Iterable} is returned.
     *
     * <ul>
     *  <li>{@code a} may be any {@code short}.</li>
     *  <li>{@code b} may be any {@code short}.</li>
     *  <li>The result is an infinite, non-removable {@code Iterable} containing {@code Short}s.</li>
     * </ul>
     *
     * Length is infinite if a≤b, 0 otherwise
     *
     * @param a the inclusive lower bound of the generated elements
     * @param b the inclusive upper bound of the generated elements
     * @return uniformly-distributed {@code Short}s between {@code a} and {@code b}, inclusive
     */
    @Override
    public @NotNull Iterable<Short> range(short a, short b) {
        if (a > b) return Collections.emptyList();
        return map(i -> (short) (i + a), randomInts((int) b - a + 1));
    }

    /**
     * An {@code Iterable} that uniformly generates {@code Integer}s between {@code a} and {@code b}, inclusive. If
     * {@code a}{@literal >}{@code b}, an empty {@code Iterable} is returned.
     *
     * <ul>
     *  <li>{@code a} may be any {@code int}.</li>
     *  <li>{@code b} may be any {@code int}.</li>
     *  <li>The result is an infinite, non-removable {@code Iterable} containing {@code Integer}s.</li>
     * </ul>
     *
     * Length is infinite if a≤b, 0 otherwise
     *
     * @param a the inclusive lower bound of the generated elements
     * @param b the inclusive upper bound of the generated elements
     * @return uniformly-distributed {@code Integer}s between {@code a} and {@code b}, inclusive
     */
    @Override
    public @NotNull Iterable<Integer> range(int a, int b) {
        if (a > b) return Collections.emptyList();
        return map(i -> (int) (i + a), randomLongs((long) b - a + 1));
    }

    /**
     * An {@code Iterable} that uniformly generates {@code Long}s between {@code a} and {@code b}, inclusive. If
     * {@code a}{@literal >}{@code b}, an empty {@code Iterable} is returned.
     *
     * <ul>
     *  <li>{@code a} may be any {@code long}.</li>
     *  <li>{@code b} may be any {@code long}.</li>
     *  <li>The result is an infinite, non-removable {@code Iterable} containing {@code Long}s.</li>
     * </ul>
     *
     * Length is infinite if a≤b, 0 otherwise
     *
     * @param a the inclusive lower bound of the generated elements
     * @param b the inclusive upper bound of the generated elements
     * @return uniformly-distributed {@code Long}s between {@code a} and {@code b}, inclusive
     */
    public @NotNull Iterable<Long> range(long a, long b) {
        if (a > b) return Collections.emptyList();
        return map(
                i -> i.add(BigInteger.valueOf(a)).longValueExact(),
                randomBigIntegers(BigInteger.valueOf(b).subtract(BigInteger.valueOf(a)).add(BigInteger.ONE))
        );
    }

    /**
     * An {@code Iterable} that uniformly generates {@code BigInteger}s between {@code a} and {@code b}, inclusive. If
     * {@code a}{@literal >}{@code b}, an empty {@code Iterable} is returned.
     *
     * <ul>
     *  <li>{@code a} may be any {@code BigInteger}.</li>
     *  <li>{@code b} may be any {@code BigInteger}.</li>
     *  <li>The result is an infinite, non-removable {@code Iterable} containing {@code BigInteger}s.</li>
     * </ul>
     *
     * Length is infinite if a≤b, 0 otherwise
     *
     * @param a the inclusive lower bound of the generated elements
     * @param b the inclusive upper bound of the generated elements
     * @return uniformly-distributed {@code BigInteger}s between {@code a} and {@code b}, inclusive
     */
    @Override
    public @NotNull Iterable<BigInteger> range(@NotNull BigInteger a, @NotNull BigInteger b) {
        if (Ordering.gt(a, b)) return Collections.emptyList();
        return map(i -> i.add(a), randomBigIntegers(b.subtract(a).add(BigInteger.ONE)));
    }

    /**
     * An {@code Iterable} that uniformly generates {@code Character}s between {@code a} and {@code b}, inclusive. If
     * {@code a}{@literal >}{@code b}, an empty {@code Iterable} is returned.
     *
     * <ul>
     *  <li>{@code a} may be any {@code char}.</li>
     *  <li>{@code b} may be any {@code char}.</li>
     *  <li>The result is an infinite, non-removable {@code Iterable} containing {@code Character}s.</li>
     * </ul>
     *
     * Length is infinite if a≤b, 0 otherwise
     *
     * @param a the inclusive lower bound of the generated elements
     * @param b the inclusive upper bound of the generated elements
     * @return uniformly-distributed {@code Character}s between {@code a} and {@code b}, inclusive
     */
    @Override
    public @NotNull Iterable<Character> range(char a, char b) {
        if (a > b) return Collections.emptyList();
        return map(i -> (char) (i + a), randomInts(b - a + 1));
    }

    /**
     * An {@code Iterable} that generates all positive {@code Integer}s chosen from a geometric distribution with mean
     * {@code scale}. Does not support removal.
     *
     * <ul>
     *  <li>{@code this} must have a scale of at least 2.</li>
     *  <li>The result is an infinite, non-removable {@code Iterable} containing positive {@code Integer}s.</li>
     * </ul>
     *
     * Length is infinite
     */
    public @NotNull Iterable<Integer> positiveIntegersGeometric() {
        if (scale < 2) {
            throw new IllegalStateException("this must have a scale of at least 2. Invalid scale: " + scale);
        }
        //noinspection ConstantConditions
        return map(p -> p.b, filter(p -> p.a, countAdjacent(map(i -> i != 0, range(0, scale - 1)))));
    }

    /**
     * An {@code Iterable} that generates all negative {@code Integer}s chosen from a geometric distribution with mean
     * –{@code scale}. Does not support removal.
     *
     * <ul>
     *  <li>{@code this} must have a scale of at least 2.</li>
     *  <li>The result is an infinite, non-removable {@code Iterable} containing negative {@code Integer}s.</li>
     * </ul>
     *
     * Length is infinite
     */
    public @NotNull Iterable<Integer> negativeIntegersGeometric() {
        return map(i -> -i, positiveIntegersGeometric());
    }

    /**
     * An {@code Iterable} that generates all natural {@code Integer}s chosen from a geometric distribution with mean
     * {@code scale}. Does not support removal.
     *
     * <ul>
     *  <li>{@code this} must have a positive scale.</li>
     *  <li>The result is an infinite, non-removable {@code Iterable} containing natural {@code Integer}s.</li>
     * </ul>
     *
     * Length is infinite
     */
    public @NotNull Iterable<Integer> naturalIntegersGeometric() {
        if (scale < 1) {
            throw new IllegalStateException("this must have a positive scale. Invalid scale: " + scale);
        }
        return map(i -> i - 1, withScale(scale + 1).positiveIntegersGeometric());
    }

    /**
     * An {@code Iterable} that generates all nonzero {@code Integer}s (or just 1 and –1, if {@code scale} is 1) whose
     * absolute value is chosen from a geometric distribution with absolute mean {@code scale}, and whose sign is
     * chosen uniformly. Does not support removal.
     *
     * <ul>
     *  <li>{@code this} must have a scale of at least 1.</li>
     *  <li>The result is an infinite, non-removable {@code Iterable} containing natural {@code Integer}s.</li>
     * </ul>
     *
     * Length is infinite
     */
    public @NotNull Iterable<Integer> nonzeroIntegersGeometric() {
        return zipWith((i, b) -> b ? i : -i, positiveIntegersGeometric(), alt().booleans());
    }

    /**
     * An {@code Iterable} that generates all {@code Integer}s (or just 0, if {@code scale} is 0) whose absolute value
     * is chosen from a geometric distribution with absolute mean {@code scale}, and whose sign is chosen uniformly.
     * an absolute value of 0 and a negative sign come up, that combination is skipped; this prevents having too many
     * zeros. Does not support removal.
     *
     * <ul>
     *  <li>The result is an infinite, non-removable {@code Iterable} containing {@code Integer}s.</li>
     * </ul>
     *
     * Length is infinite
     */
    public @NotNull Iterable<Integer> integersGeometric() {
        //noinspection ConstantConditions
        return map(
                q -> q.b ? q.a : -q.a,
                filter(p -> p.b || p.a != 0, zip(naturalIntegersGeometric(), alt().booleans()))
        );
    }

    /**
     * An {@code Iterable} that generates all positive {@code BigInteger}s. The bit size is chosen from a geometric
     * distribution with mean approximately {@code meanBitSize} (The ratio between the actual mean and
     * {@code meanBitSize} decreases as {@code meanBitSize} increases). Does not support removal.
     *
     * <ul>
     *  <li>{@code meanBitSize} must be greater than 2.</li>
     *  <li>The is an infinite pseudorandom sequence of all {@code BigInteger}s</li>
     * </ul>
     *
     * Length is infinite
     */
    public @NotNull Iterable<BigInteger> positiveBigIntegers() {
        return () -> new NoRemoveIterator<BigInteger>() {
            private @NotNull IsaacPRNG prng = new IsaacPRNG(seed);
            private @NotNull Iterator<Integer> sizes = alt().positiveIntegersGeometric().iterator();

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public BigInteger next() {
                int size = sizes.next();
                BigInteger i = nextBigInteger(prng, size);
                i = i.setBit(size - 1);
                return i;
            }
        };
    }

    /**
     * An {@code Iterable} that generates all negative {@code BigInteger}s. The bit size is chosen from a geometric
     * distribution with mean approximately {@code meanBitSize} (The ratio between the actual mean and
     * {@code meanBitSize} decreases as {@code meanBitSize} increases). Does not support removal.
     *
     * <ul>
     *  <li>{@code meanBitSize} must be greater than 2.</li>
     *  <li>The result is an infinite pseudorandom sequence of all {@code BigInteger}s.</li>
     * </ul>
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<BigInteger> negativeBigIntegers() {
        return map(BigInteger::negate, positiveBigIntegers());
    }

    /**
     * An {@code Iterable} that generates all natural {@code BigInteger}s (including 0). The bit size is chosen from a
     * geometric distribution with mean approximately {@code meanBitSize} (The ratio between the actual mean and
     * {@code meanBitSize} decreases as {@code meanBitSize} increases). Does not support removal.
     *
     * <ul>
     *  <li>{@code meanBitSize} must be greater than 2.</li>
     *  <li>The result is an infinite pseudorandom sequence of all {@code BigInteger}s.</li>
     * </ul>
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<BigInteger> naturalBigIntegers() {
        return () -> new NoRemoveIterator<BigInteger>() {
            private @NotNull IsaacPRNG prng = new IsaacPRNG(seed);
            private @NotNull Iterator<Integer> sizes = alt().naturalIntegersGeometric().iterator();

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public BigInteger next() {
                int size = sizes.next();
                BigInteger i = nextBigInteger(prng, size);
                if (size != 0) {
                    i = i.setBit(size - 1);
                }
                return i;
            }
        };
    }

    /**
     * An {@code Iterable} that generates all {@code BigInteger}s. The bit size is chosen from a geometric distribution
     * with mean approximately {@code meanBitSize} (The ratio between the actual mean and {@code meanBitSize} decreases
     * as {@code meanBitSize} increases). Does not support removal.
     *
     * <ul>
     *  <li>{@code meanBitSize} must be greater than 2.</li>
     *  <li>The result is an infinite pseudorandom sequence of all {@code BigInteger}s.</li>
     * </ul>
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<BigInteger> bigIntegers() {
        if (scale <= 2)
            throw new IllegalArgumentException("meanBitSize must be greater than 2.");
        return () -> new Iterator<BigInteger>() {
            private Random generator = new Random(0x6af477d9a7e54fcaL);

            private final Iterator<BigInteger> it = naturalBigIntegers().iterator();

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public BigInteger next() {
                BigInteger nbi = it.next();
                if (generator.nextBoolean()) {
                    nbi = nbi.negate().subtract(BigInteger.ONE);
                }
                return nbi;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    /**
     * An {@code Iterable} that generates all ordinary (neither NaN nor infinite) positive floats from a uniform
     * distribution. Does not support removal.
     *
     * Length is infinite.
     */
    @Override
    public @NotNull Iterable<Float> positiveOrdinaryFloats() {
        return map(Math::abs, filter(
                f -> Float.isFinite(f) && !Float.isNaN(f) && !f.equals(-0.0f) && !f.equals(0.0f),
                floats()
        ));
    }

    /**
     * An {@code Iterable} that generates all ordinary (neither NaN nor infinite) negative floats from a uniform
     * distribution. Negative zero is not included. Does not support removal.
     *
     * Length is infinite.
     */
    @Override
    public @NotNull Iterable<Float> negativeOrdinaryFloats() {
        return map(f -> -f, positiveOrdinaryFloats());
    }

    /**
     * An {@code Iterable} that generates all ordinary (neither NaN nor infinite) floats from a uniform distribution.
     * Does not support removal.
     *
     * Length is infinite.
     */
    @Override
    public @NotNull Iterable<Float> ordinaryFloats() {
        return filter(f -> Float.isFinite(f) && !Float.isNaN(f) && !f.equals(-0.0f), floats());
    }

    /**
     * An {@code Iterable} that generates all {@code Float}s from a uniform distribution. Does not support removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<Float> floats() {
        return () -> new Iterator<Float>() {
            private Random generator = new Random(0x6af477d9a7e54fcaL);

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public Float next() {
                float next;
                boolean problematicNaN;
                do {
                    int floatBits = generator.nextInt();
                    next = Float.intBitsToFloat(floatBits);
                    problematicNaN = Float.isNaN(next) && floatBits != 0x7fc00000;
                } while (problematicNaN);
                return next;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    /**
     * An {@code Iterable} that generates all ordinary (neither NaN nor infinite) positive floats from a uniform
     * distribution. Does not support removal.
     *
     * Length is infinite.
     */
    @Override
    public @NotNull Iterable<Double> positiveOrdinaryDoubles() {
        return map(Math::abs, filter(
                d -> Double.isFinite(d) && !Double.isNaN(d) && !d.equals(-0.0) && !d.equals(0.0),
                doubles()
        ));
    }

    /**
     * An {@code Iterable} that generates all ordinary (neither NaN nor infinite) negative floats from a uniform
     * distribution. Negative zero is not included. Does not support removal.
     *
     * Length is infinite.
     */
    @Override
    public @NotNull Iterable<Double> negativeOrdinaryDoubles() {
        return map(d -> -d, positiveOrdinaryDoubles());
    }

    /**
     * An {@code Iterable} that generates all ordinary (neither NaN nor infinite) floats from a uniform distribution.
     * Does not support removal.
     *
     * Length is infinite.
     */
    @Override
    public @NotNull Iterable<Double> ordinaryDoubles() {
        return filter(d -> Double.isFinite(d) && !Double.isNaN(d) && !d.equals(-0.0), doubles());
    }

    /**
     * An {@code Iterable} that generates all {@code Double}s from a uniform distribution. Does not support removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<Double> doubles() {
        return () -> new Iterator<Double>() {
            private Random generator = new Random(0x6af477d9a7e54fcaL);

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public Double next() {
                double next;
                boolean problematicNaN;
                do {
                    long doubleBits = generator.nextLong();
                    next = Double.longBitsToDouble(doubleBits);
                    problematicNaN = Double.isNaN(next) && doubleBits != 0x7ff8000000000000L;
                } while (problematicNaN);
                return next;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    @Override
    public @NotNull Iterable<BigDecimal> positiveBigDecimals() {
        return map(
                p -> new BigDecimal(p.a, p.b),
                pairs(negativeBigIntegers(), integersGeometric())
        );
    }

    @Override
    public @NotNull Iterable<BigDecimal> negativeBigDecimals() {
        return map(
                p -> new BigDecimal(p.a, p.b),
                pairs(negativeBigIntegers(), integersGeometric())
        );
    }

    @Override
    public @NotNull Iterable<BigDecimal> bigDecimals() {
        return map(p -> new BigDecimal(p.a, p.b), pairs(bigIntegers(), integersGeometric()));
    }

    public @NotNull <T> Iterable<T> addSpecialElement(@Nullable T x, @NotNull Iterable<T> xs) {
        return () -> new Iterator<T>() {
            private Iterator<T> xsi = xs.iterator();
            private Iterator<Integer> specialSelector = randomInts(SPECIAL_ELEMENT_RATIO).iterator();
            boolean specialSelection = specialSelector.next() == 0;

            @Override
            public boolean hasNext() {
                return specialSelection || xsi.hasNext();
            }

            @Override
            public T next() {
                boolean previousSelection = specialSelection;
                specialSelection = specialSelector.next() == 0;
                return previousSelection ? x : xsi.next();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    @Override
    public @NotNull <T> Iterable<T> withNull(@NotNull Iterable<T> xs) {
        return addSpecialElement(null, xs);
    }

    @Override
    public @NotNull <T> Iterable<Optional<T>> optionals(@NotNull Iterable<T> xs) {
        return addSpecialElement(Optional.<T>empty(), map(Optional::of, xs));
    }

    @Override
    public @NotNull <T> Iterable<NullableOptional<T>> nullableOptionals(@NotNull Iterable<T> xs) {
        return addSpecialElement(NullableOptional.<T>empty(), map(NullableOptional::of, xs));
    }

    @Override
    public @NotNull <A, B> Iterable<Pair<A, B>> pairs(@NotNull Iterable<A> as, @NotNull Iterable<B> bs) {
        return zip(as, bs);
    }

    @Override
    public @NotNull <A, B, C> Iterable<Triple<A, B, C>> triples(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull Iterable<C> cs
    ) {
        return zip3(as, bs, cs);
    }

    @Override
    public @NotNull <A, B, C, D> Iterable<Quadruple<A, B, C, D>> quadruples(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull Iterable<C> cs,
            @NotNull Iterable<D> ds
    ) {
        return zip4(as, bs, cs, ds);
    }

    @Override
    public @NotNull <A, B, C, D, E> Iterable<Quintuple<A, B, C, D, E>> quintuples(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull Iterable<C> cs,
            @NotNull Iterable<D> ds,
            @NotNull Iterable<E> es
    ) {
        return zip5(as, bs, cs, ds, es);
    }

    @Override
    public @NotNull <A, B, C, D, E, F> Iterable<Sextuple<A, B, C, D, E, F>> sextuples(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull Iterable<C> cs,
            @NotNull Iterable<D> ds,
            @NotNull Iterable<E> es,
            @NotNull Iterable<F> fs
    ) {
        return zip6(as, bs, cs, ds, es, fs);
    }

    @Override
    public @NotNull <A, B, C, D, E, F, G> Iterable<Septuple<A, B, C, D, E, F, G>> septuples(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull Iterable<C> cs,
            @NotNull Iterable<D> ds,
            @NotNull Iterable<E> es,
            @NotNull Iterable<F> fs,
            @NotNull Iterable<G> gs
    ) {
        return zip7(as, bs, cs, ds, es, fs, gs);
    }

    @Override
    public @NotNull <T> Iterable<Pair<T, T>> pairs(@NotNull Iterable<T> xs) {
        List<Iterable<T>> xss = demux(2, xs);
        return zip(xss.get(0), xss.get(1));
    }

    @Override
    public @NotNull <T> Iterable<Triple<T, T, T>> triples(@NotNull Iterable<T> xs) {
        List<Iterable<T>> xss = demux(3, xs);
        return zip3(xss.get(0), xss.get(1), xss.get(2));
    }

    @Override
    public @NotNull <T> Iterable<Quadruple<T, T, T, T>> quadruples(@NotNull Iterable<T> xs) {
        List<Iterable<T>> xss = demux(4, xs);
        return zip4(xss.get(0), xss.get(1), xss.get(2), xss.get(3));
    }

    @Override
    public @NotNull <T> Iterable<Quintuple<T, T, T, T, T>> quintuples(@NotNull Iterable<T> xs) {
        List<Iterable<T>> xss = demux(5, xs);
        return zip5(xss.get(0), xss.get(1), xss.get(2), xss.get(3), xss.get(4));
    }

    @Override
    public @NotNull <T> Iterable<Sextuple<T, T, T, T, T, T>> sextuples(@NotNull Iterable<T> xs) {
        List<Iterable<T>> xss = demux(6, xs);
        return zip6(xss.get(0), xss.get(1), xss.get(2), xss.get(3), xss.get(4), xss.get(5));
    }

    @Override
    public @NotNull <T> Iterable<Septuple<T, T, T, T, T, T, T>> septuples(@NotNull Iterable<T> xs) {
        List<Iterable<T>> xss = demux(7, xs);
        return zip7(xss.get(0), xss.get(1), xss.get(2), xss.get(3), xss.get(4), xss.get(5), xss.get(6));
    }

    @Override
    public @NotNull <T> Iterable<List<T>> lists(int size, @NotNull Iterable<T> xs) {
        if (size == 0) {
            return repeat(Collections.emptyList());
        } else {
            return transpose(demux(size, xs));
        }
    }

    @Override
    public @NotNull <T> Iterable<List<T>> listsAtLeast(int minSize, @NotNull Iterable<T> xs) {
        if (isEmpty(xs)) return Collections.singletonList(Collections.emptyList());
        return () -> new Iterator<List<T>>() {
            private final Iterator<T> xsi = cycle(xs).iterator();
            private final Iterator<Integer> sizes = naturalIntegersGeometric().iterator();

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public List<T> next() {
                int size = sizes.next() + minSize;
                List<T> list = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    list.add(xsi.next());
                }
                return list;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    @Override
    public @NotNull <T> Iterable<List<T>> lists(@NotNull Iterable<T> xs) {
        if (isEmpty(xs)) return Collections.singletonList(Collections.emptyList());
        return () -> new Iterator<List<T>>() {
            private final Iterator<T> xsi = cycle(xs).iterator();
            private final Iterator<Integer> sizes = naturalIntegersGeometric().iterator();

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public List<T> next() {
                int size = sizes.next();
                List<T> list = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    list.add(xsi.next());
                }
                return list;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    @Override
    public @NotNull Iterable<String> strings(int size, @NotNull Iterable<Character> cs) {
        return map(IterableUtils::charsToString, transpose(demux(size, cs)));
    }

    @Override
    public @NotNull Iterable<String> stringsAtLeast(int minSize, @NotNull Iterable<Character> cs) {
        if (isEmpty(cs)) return Arrays.asList("");
        return () -> new Iterator<String>() {
            private final Iterator<Character> csi = cycle(cs).iterator();
            private final Iterator<Integer> sizes = naturalIntegersGeometric().iterator();

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public String next() {
                int size = sizes.next() + minSize;
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < size; i++) {
                    sb.append(csi.next());
                }
                return sb.toString();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    @Override
    public @NotNull Iterable<String> strings(int size) {
        return strings(size, characters());
    }

    @Override
    public @NotNull Iterable<String> stringsAtLeast(int minSize) {
        return stringsAtLeast(minSize, characters());
    }

    @Override
    public @NotNull Iterable<String> strings(@NotNull Iterable<Character> cs) {
        if (isEmpty(cs)) return Arrays.asList("");
        return () -> new Iterator<String>() {
            private final Iterator<Character> csi = cycle(cs).iterator();
            private final Iterator<Integer> sizes = naturalIntegersGeometric().iterator();

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public String next() {
                int size = sizes.next();
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < size; i++) {
                    sb.append(csi.next());
                }
                return sb.toString();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    @Override
    public @NotNull Iterable<String> strings() {
        return strings(characters());
    }

    /**
     * Determines whether {@code this} is equal to {@code that}.
     *
     * <ul>
     *  <li>{@code this} may be any {@code RandomProvider}.</li>
     *  <li>{@code that} may be any {@code Object}.</li>
     *  <li>The result may be either {@code boolean}.</li>
     * </ul>
     *
     * @param that The {@code RandomProvider} to be compared with {@code this}
     * @return {@code this}={@code that}
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        if (that == null || getClass() != that.getClass()) return false;
        RandomProvider provider = (RandomProvider) that;
        return scale == provider.scale && secondaryScale == provider.secondaryScale && seed.equals(provider.seed);
    }

    /**
     * Calculates the hash code of {@code this}.
     *
     * <ul>
     *  <li>{@code this} may be any {@code RandomProvider}.</li>
     *  <li>(conjecture) The result may be any {@code int}.</li>
     * </ul>
     *
     * @return {@code this}'s hash code.
     */
    @Override
    public int hashCode() {
        int result = seed.hashCode();
        result = 31 * result + scale;
        result = 31 * result + secondaryScale;
        return result;
    }

    /**
     * Creates a {@code String} representation of {@code this}.
     *
     * <ul>
     *  <li>{@code this} may be any {@code RandomProvider}.</li>
     *  <li>See tests and demos for example results.</li>
     * </ul>
     *
     * @return a {@code String} representation of {@code this}
     */
    public String toString() {
        return "RandomProvider[@" + new IsaacPRNG(seed).nextInt() + ", " + scale + ", " + secondaryScale + "]";
    }

    /**
     * Ensures that {@code this} is valid. Must return true for any {@code RandomProvider} used outside this class.
     */
    public void validate() {
        assertEquals(toString(), seed.size(), IsaacPRNG.SIZE);
        assertTrue(toString(), scale >= 0);
        assertTrue(toString(), secondaryScale >= 0);
    }
}
