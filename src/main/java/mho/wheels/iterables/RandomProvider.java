package mho.wheels.iterables;

import mho.wheels.math.BinaryFraction;
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
import java.util.function.Function;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.ordering.Ordering.*;
import static mho.wheels.testing.Testing.*;

/**
 * <p>A {@code RandomProvider} produces {@code Iterable}s that randomly generate some set of values with a specified
 * distribution. A {@code RandomProvider} is deterministic, but not immutable: its state changes every time a random
 * value is generated. It may be reverted to its original state with {@link RandomProvider#reset}.</p>
 *
 * <p>{@code RandomProvider} uses the cryptographically-secure ISAAC pseudorandom number generator, implemented in
 * {@link mho.wheels.random.IsaacPRNG}. The source of its randomness is a {@code int[]} seed. It contains two scale
 * parameters which some of the distributions depend on; the exact relationship between the parameters and the
 * distributions is specified in the distribution's documentation.</p>
 *
 * <p>To create an instance which shares a generator with {@code this}, use {@link RandomProvider#copy()}. To create
 * an instance which copies the generator, use {@link RandomProvider#deepCopy()}.</p>
 *
 * <p>Note that sometimes the documentation will say things like "returns an {@code Iterable} containing all
 * {@code String}s". This cannot strictly be true, since {@link java.util.Random} has a finite period, and will
 * therefore produce only a finite number of {@code String}s. So in general, the documentation often pretends that the
 * source of randomness is perfect (but still deterministic).</p>
 */
public final strictfp class RandomProvider extends IterableProvider {
    /**
     * A list of all {@code Ordering}s.
     */
    private static final List<Ordering> ORDERINGS = toList(ExhaustiveProvider.INSTANCE.orderings());

    /**
     * A list of all {@code RoundingMode}s.
     */
    private static final List<RoundingMode> ROUNDING_MODES = toList(ExhaustiveProvider.INSTANCE.roundingModes());

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
    private final @NotNull List<Integer> seed;

    /**
     * A pseudorandom number generator. It changes state every time a random number is generated.
     */
    private @NotNull IsaacPRNG prng;

    /**
     * A map containing {@code RandomProvider}s that were created from {@code this} using
     * {@link RandomProvider#withScale(int)} and {@link RandomProvider#withSecondaryScale(int)}. The key is a pair
     * consisting of {@code scale} and {@code secondaryScale}, respectively. Whenever {@code this}
     * is reset with {@link RandomProvider#reset()}, the dependents are reset as well.
     */
    private @NotNull Map<Pair<Integer, Integer>, RandomProvider> dependents;

    /**
     * A parameter that determines the size of some of the generated objects.
     */
    private int scale = DEFAULT_SCALE;

    /**
     * Another parameter that determines the size of some of the generated objects.
     */
    private int secondaryScale = DEFAULT_SECONDARY_SCALE;

    /**
     * Constructs a {@code RandomProvider} with a seed generated from the current system time.
     *
     * <ul>
     *  <li>(conjecture) Any {@code RandomProvider} with default {@code scale} and {@code secondaryScale} may be
     *  constructed with this constructor.</li>
     * </ul>
     */
    public RandomProvider() {
        prng = new IsaacPRNG();
        seed = new ArrayList<>();
        for (int i = 0; i < IsaacPRNG.SIZE; i++) {
            seed.add(prng.nextInt());
        }
        prng = new IsaacPRNG(seed);
        dependents = new HashMap<>();
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
        prng = new IsaacPRNG(seed);
        dependents = new HashMap<>();
    }

    /**
     * A {@code RandomProvider} used for testing. This allows for deterministic testing without manually setting up a
     * lengthy seed each time.
     */
    public static @NotNull RandomProvider example() {
        IsaacPRNG prng = IsaacPRNG.example();
        List<Integer> seed = new ArrayList<>();
        for (int i = 0; i < IsaacPRNG.SIZE; i++) {
            seed.add(prng.nextInt());
        }
        return new RandomProvider(seed);
    }

    /**
     * Returns {@code this}'s scale parameter.
     *
     * <ul>
     *  <li>{@code this} may be any {@code RandomProvider}.</li>
     *  <li>The result may be any {@code int}.</li>
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
     *  <li>{@code this} may be any {@code RandomProvider}.</li>
     *  <li>The result may be any {@code int}.</li>
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
     * A {@code RandomProvider} with the same fields as {@code this}. The copy shares {@code prng} with the original.
     *
     * <ul>
     *  <li>{@code this} may be any {@code RandomProvider}.</li>
     *  <li>The result is not null.</li>
     * </ul>
     *
     * @return A copy of {@code this}.
     */
    @Override
    public @NotNull RandomProvider copy() {
        RandomProvider copy = new RandomProvider(seed);
        copy.scale = scale;
        copy.secondaryScale = secondaryScale;
        copy.prng = prng;
        return copy;
    }

    /**
     * A {@code RandomProvider} with the same fields as {@code this}. The copy receives a new copy of {@code prng},
     * so generating values from the copy will not affect the state of the original's {@code prng}.
     *
     * <ul>
     *  <li>{@code this} may be any {@code RandomProvider}.</li>
     *  <li>The result is not null.</li>
     * </ul>
     *
     * @return A copy of {@code this}.
     */
    @Override
    public @NotNull RandomProvider deepCopy() {
        RandomProvider copy = new RandomProvider(seed);
        copy.scale = scale;
        copy.secondaryScale = secondaryScale;
        copy.prng = prng.copy();
        return copy;
    }

    /**
     * A {@code RandomProvider} with the same fields as {@code this} except for a new scale.
     *
     * <ul>
     *  <li>{@code this} may be any {@code RandomProvider}.</li>
     *  <li>{@code scale} may be any {@code int}.</li>
     *  <li>The result is not null.</li>
     * </ul>
     *
     * @param scale the new scale
     * @return A copy of {@code this} with a new scale
     */
    @Override
    public @NotNull RandomProvider withScale(int scale) {
        Pair<Integer, Integer> key = new Pair<>(scale, secondaryScale);
        RandomProvider copy = dependents.get(key);
        if (copy == null) {
            copy = copy();
            copy.scale = scale;
            dependents.put(key, copy);
        }
        return copy;
    }

    /**
     * A {@code RandomProvider} with the same fields as {@code this} except for a new secondary scale.
     *
     * <ul>
     *  <li>{@code this} may be any {@code RandomProvider}.</li>
     *  <li>{@code secondaryScale} mat be any {@code int}.</li>
     *  <li>The result is not null.</li>
     * </ul>
     *
     * @param secondaryScale the new secondary scale
     * @return A copy of {@code this} with a new secondary scale
     */
    @Override
    public @NotNull RandomProvider withSecondaryScale(int secondaryScale) {
        Pair<Integer, Integer> key = new Pair<>(scale, secondaryScale);
        RandomProvider copy = dependents.get(key);
        if (copy == null) {
            copy = copy();
            copy.secondaryScale = secondaryScale;
            dependents.put(key, copy);
        }
        return copy;
    }

    /**
     * Put the {@code prng} back in its original state. Creating a {@code RandomProvider}, generating some values,
     * resetting, and generating the same types of values again will result in the same values. Any
     * {@code RandomProvider}s created from {@code this} using {@link RandomProvider#withScale(int)} or
     * {@link RandomProvider#withSecondaryScale(int)} (but not {@link RandomProvider#copy} or
     * {@link RandomProvider#deepCopy()}) is also reset.
     *
     * <ul>
     *  <li>{@code this} may be any {@code RandomProvider}.</li>
     *  <li>The result is not null.</li>
     * </ul>
     */
    @Override
    public void reset() {
        prng = new IsaacPRNG(seed);
        dependents.values().forEach(mho.wheels.iterables.RandomProvider::reset);
    }

    /**
     * Returns an id which has a good chance of being different in two instances with unequal {@code prng}s. It's used
     * in {@link RandomProvider#toString()} to distinguish between different {@code RandomProvider} instances.
     *
     * <ul>
     *  <li>{@code this} may be any {@code RandomProvider}.</li>
     *  <li>{@code this} may be any {@code long}.</li>
     * </ul>
     */
    public long getId() {
        return prng.getId();
    }

    /**
     * Returns a randomly-generated {@code int} from a uniform distribution.
     *
     * <ul>
     *  <li>{@code this} may be any {@code RandomProvider}.</li>
     *  <li>The result may be any {@code int}.</li>
     * </ul>
     *
     * @return an {@code int}
     */
    public int nextInt() {
        return prng.nextInt();
    }

    /**
     * An {@code Iterable} that generates all {@code Integer}s from a uniform distribution. Does not support removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<Integer> integers() {
        return fromSupplier(this::nextInt);
    }

    /**
     * Returns a randomly-generated {@code long} from a uniform distribution.
     *
     * <ul>
     *  <li>{@code this} may be any {@code RandomProvider}.</li>
     *  <li>The result may be any {@code long}.</li>
     * </ul>
     *
     * @return a {@code long}
     */
    public long nextLong() {
        int a = nextInt();
        int b = nextInt();
        return (long) a << 32 | b & 0xffffffffL;
    }

    /**
     * An {@code Iterable} that generates all {@code Long}s from a uniform distribution. Does not support removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<Long> longs() {
        return fromSupplier(this::nextLong);
    }

    /**
     * Returns a randomly-generated {@code boolean} from a uniform distribution.
     *
     * <ul>
     *  <li>{@code this} may be any {@code RandomProvider}.</li>
     *  <li>The result may be either {@code boolean}.</li>
     * </ul>
     *
     * @return a {@code boolean}
     */
    public boolean nextBoolean() {
        return (nextInt() & 1) != 0;
    }

    /**
     * An {@code Iterator} that generates both {@code Boolean}s from a uniform distribution. Does not support removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<Boolean> booleans() {
        return fromSupplier(this::nextBoolean);
    }

    /**
     * Returns a randomly-generated {@code int} taken from a uniform distribution between 0 and
     * 2<sup>{@code bits}</sup>–1, inclusive.
     *
     * <ul>
     *  <li>{@code this} may be any {@code RandomProvider}.</li>
     *  <li>{@code bits} must be greater than 0 and less than 32.</li>
     *  <li>The result cannot be negative.</li>
     * </ul>
     *
     * @param bits the maximum bitlength of the generated {@code int}
     * @return an {@code int} with up to {@code bits} bits
     */
    private int nextIntPow2(int bits) {
        return nextInt() & ((1 << bits) - 1);
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
    private @NotNull Iterable<Integer> integersPow2(int bits) {
        int mask = (1 << bits) - 1;
        return map(i -> i & mask, integers());
    }

    /**
     * Returns a randomly-generated {@code long} taken from a uniform distribution between 0 and
     * 2<sup>{@code bits}</sup>–1, inclusive.
     *
     * <ul>
     *  <li>{@code this} may be any {@code RandomProvider}.</li>
     *  <li>{@code bits} must be greater than 0 and less than 64.</li>
     *  <li>The result cannot be negative.</li>
     * </ul>
     *
     * @param bits the maximum bitlength of the generated {@code long}
     * @return a {@code long} with up to {@code bits} bits
     */
    private long nextLongPow2(int bits) {
        return nextLong() & ((1L << bits) - 1);
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
    private @NotNull Iterable<Long> longsPow2(int bits) {
        long mask = (1L << bits) - 1;
        return map(l -> l & mask, longs());
    }

    /**
     * Returns a randomly-generated {@code BigInteger} taken from a uniform distribution between 0 and
     * 2<sup>{@code bits}</sup>–1, inclusive.
     *
     * <ul>
     *  <li>{@code this} may be any {@code RandomProvider}.</li>
     *  <li>{@code bits} must be positive.</li>
     *  <li>The result cannot be negative.</li>
     * </ul>
     *
     * @param bits the maximum bitlength of the generated {@code BigInteger}
     * @return a {@code BigInteger} with up to {@code bits} bits
     */
    private @NotNull BigInteger nextBigIntegerPow2(int bits) {
        byte[] bytes = new byte[bits / 8 + 1];
        int x = 0;
        for (int i = 0; i < bytes.length; i++) {
            if (i % 4 == 0) {
                x = nextInt();
            }
            bytes[i] = (byte) x;
            x >>= 8;
        }
        bytes[0] &= (1 << (bits % 8)) - 1;
        return new BigInteger(bytes);
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
    private @NotNull Iterable<BigInteger> bigIntegersPow2(int bits) {
        return fromSupplier(() -> this.nextBigIntegerPow2(bits));
    }

    /**
     * Returns a randomly-generated {@code int} taken from a uniform distribution between 0 and {@code n}–1, inclusive.
     *
     * <ul>
     *  <li>{@code this} may be any {@code RandomProvider}.</li>
     *  <li>{@code n} must be positive.</li>
     *  <li>The result cannot be negative.</li>
     * </ul>
     *
     * @param n the exclusive upper bound of the result
     * @return a non-negative {@code int} less than {@code n}
     */
    private int nextIntBounded(int n) {
        int maxBits = MathUtils.ceilingLog2(n);
        int i;
        do {
            i = nextIntPow2(maxBits);
        } while (i >= n);
        return i;
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
    private @NotNull Iterable<Integer> integersBounded(int n) {
        return filter(i -> i < n, integersPow2(MathUtils.ceilingLog2(n)));
    }

    /**
     * Returns a randomly-generated {@code long} taken from a uniform distribution between 0 and {@code n}–1,
     * inclusive.
     *
     * <ul>
     *  <li>{@code this} may be any {@code RandomProvider}.</li>
     *  <li>{@code n} must be positive.</li>
     *  <li>The result cannot be negative.</li>
     * </ul>
     *
     * @param n the exclusive upper bound of the result
     * @return a non-negative {@code long} less than {@code n}
     */
    private long nextLongBounded(long n) {
        int maxBits = MathUtils.ceilingLog2(n);
        long l;
        do {
            l = nextLongPow2(maxBits);
        } while (l >= n);
        return l;
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
    private @NotNull Iterable<Long> longsBounded(long n) {
        return filter(l -> l < n, longsPow2(MathUtils.ceilingLog2(n)));
    }

    /**
     * Returns a randomly-generated {@code BigInteger} taken from a uniform distribution between 0 and {@code n}–1,
     * inclusive.
     *
     * <ul>
     *  <li>{@code this} may be any {@code RandomProvider}.</li>
     *  <li>{@code n} must be positive.</li>
     *  <li>The result cannot be negative.</li>
     * </ul>
     *
     * @param n the exclusive upper bound of the result
     * @return a non-negative {@code BigInteger} less than {@code n}
     */
    private @NotNull BigInteger nextBigIntegerBounded(@NotNull BigInteger n) {
        if (n.equals(BigInteger.ONE)) return BigInteger.ZERO;
        int maxBits = MathUtils.ceilingLog2(n);
        BigInteger i;
        do {
            i = nextBigIntegerPow2(maxBits);
        } while (ge(i, n));
        return i;
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
    private @NotNull Iterable<BigInteger> bigIntegersBounded(@NotNull BigInteger n) {
        if (n.equals(BigInteger.ONE)) return repeat(BigInteger.ZERO);
        return filter(i -> lt(i, n), bigIntegersPow2(MathUtils.ceilingLog2(n)));
    }

    /**
     * Returns a randomly-generated value taken from a given list.
     *
     * <ul>
     *  <li>{@code this} may be any {@code RandomProvider}.</li>
     *  <li>{@code xs} cannot be empty.</li>
     *  <li>The result may be any value of type {@code T}, or null.</li>
     * </ul>
     *
     * @param xs the source list
     * @param <T> the type of {@code xs}'s elements
     * @return a value from {@code xs}
     */
    public <T> T nextUniformSample(@NotNull List<T> xs) {
        return xs.get(nextIntBounded(xs.size()));
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
        return xs.isEmpty() ? Collections.emptyList() : map(xs::get, integersBounded(xs.size()));
    }

    /**
     * Returns a randomly-generated character taken from a given {@code String}.
     *
     * <ul>
     *  <li>{@code this} may be any {@code RandomProvider}.</li>
     *  <li>{@code s} cannot be empty.</li>
     *  <li>The result may be any {@code char}.</li>
     * </ul>
     *
     * @param s the source {@code String}
     * @return a {@code char} from {@code s}
     */
    public char nextUniformSample(@NotNull String s) {
        return s.charAt(nextIntBounded(s.length()));
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
        return s.isEmpty() ? Collections.emptyList() : map(s::charAt, integersBounded(s.length()));
    }

    /**
     * Returns a randomly-generated {@code Ordering} from a uniform distribution.
     *
     * <ul>
     *  <li>{@code this} may be any {@code RandomProvider}.</li>
     *  <li>The result is not null.</li>
     * </ul>
     *
     * @return an {@code Ordering}
     */
    public @NotNull Ordering nextOrdering() {
        return nextUniformSample(ORDERINGS);
    }

    /**
     * An {@code Iterator} that generates all {@code Ordering}s from a uniform distribution. Does not support removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<Ordering> orderings() {
        return uniformSample(ORDERINGS);
    }

    /**
     * Returns a randomly-generated {@code RoundingMode} from a uniform distribution.
     *
     * <ul>
     *  <li>{@code this} may be any {@code RandomProvider}.</li>
     *  <li>The result is not null.</li>
     * </ul>
     *
     * @return a {@code RoundingMode}
     */
    public @NotNull RoundingMode nextRoundingMode() {
        return nextUniformSample(ROUNDING_MODES);
    }

    /**
     * An {@code Iterable} that generates all {@code RoundingMode}s from a uniform distribution. Does not support
     * removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<RoundingMode> roundingModes() {
        return uniformSample(ROUNDING_MODES);
    }

    /**
     * Returns a randomly-generated positive {@code byte} from a uniform distribution.
     *
     * <ul>
     *  <li>{@code this} may be any {@code RandomProvider}.</li>
     *  <li>The result is positive.</li>
     * </ul>
     *
     * @return a positive {@code byte}
     */
    public byte nextPositiveByte() {
        byte b;
        do {
            b = nextNaturalByte();
        } while (b == 0);
        return b;
    }

    /**
     * An {@code Iterable} that generates all positive {@code Byte}s from a uniform distribution. Does not support
     * removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<Byte> positiveBytes() {
        return fromSupplier(this::nextPositiveByte);
    }

    /**
     * Returns a randomly-generated positive {@code short} from a uniform distribution.
     *
     * <ul>
     *  <li>{@code this} may be any {@code RandomProvider}.</li>
     *  <li>The result is positive.</li>
     * </ul>
     *
     * @return a positive {@code short}
     */
    public short nextPositiveShort() {
        short s;
        do {
            s = nextNaturalShort();
        } while (s == 0);
        return s;
    }

    /**
     * An {@code Iterable} that generates all positive {@code Short}s from a uniform distribution. Does not support
     * removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<Short> positiveShorts() {
        return fromSupplier(this::nextPositiveShort);
    }

    /**
     * Returns a randomly-generated positive {@code int} from a uniform distribution.
     *
     * <ul>
     *  <li>{@code this} may be any {@code RandomProvider}.</li>
     *  <li>The result is positive.</li>
     * </ul>
     *
     * @return a positive {@code int}
     */
    public int nextPositiveInt() {
        int i;
        do {
            i = nextInt();
        } while (i <= 0);
        return i;
    }

    /**
     * An {@code Iterable} that generates all positive {@code Integer}s from a uniform distribution. Does not support
     * removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<Integer> positiveIntegers() {
        return fromSupplier(this::nextPositiveInt);
    }

    /**
     * Returns a randomly-generated positive {@code long} from a uniform distribution.
     *
     * <ul>
     *  <li>{@code this} may be any {@code RandomProvider}.</li>
     *  <li>The result is positive.</li>
     * </ul>
     *
     * @return a positive {@code long}
     */
    public long nextPositiveLong() {
        long l;
        do {
            l = nextLong();
        } while (l <= 0);
        return l;
    }

    /**
     * An {@code Iterable} that generates all positive {@code Long}s from a uniform distribution from a uniform
     * distribution. Does not support removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<Long> positiveLongs() {
        return fromSupplier(this::nextPositiveLong);
    }

    /**
     * Returns a randomly-generated negative {@code byte} from a uniform distribution.
     *
     * <ul>
     *  <li>{@code this} may be any {@code RandomProvider}.</li>
     *  <li>The result is negative.</li>
     * </ul>
     *
     * @return a negative {@code byte}
     */
    public byte nextNegativeByte() {
        return (byte) ~nextNaturalByte();
    }

    /**
     * An {@code Iterable} that generates all negative {@code Byte}s from a uniform distribution. Does not support
     * removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<Byte> negativeBytes() {
        return fromSupplier(this::nextNegativeByte);
    }

    /**
     * Returns a randomly-generated negative {@code short} from a uniform distribution.
     *
     * <ul>
     *  <li>{@code this} may be any {@code RandomProvider}.</li>
     *  <li>The result is negative.</li>
     * </ul>
     *
     * @return a negative {@code short}
     */
    public short nextNegativeShort() {
        return (short) ~nextNaturalShort();
    }

    /**
     * An {@code Iterable} that generates all negative {@code Short}s from a uniform distribution. Does not support
     * removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<Short> negativeShorts() {
        return fromSupplier(this::nextNegativeShort);
    }

    /**
     * Returns a randomly-generated negative {@code int} from a uniform distribution.
     *
     * <ul>
     *  <li>{@code this} may be any {@code RandomProvider}.</li>
     *  <li>The result is negative.</li>
     * </ul>
     *
     * @return a negative {@code int}
     */
    public int nextNegativeInt() {
        int i;
        do {
            i = nextInt();
        } while (i >= 0);
        return i;
    }

    /**
     * An {@code Iterable} that generates all negative {@code Integer}s from a uniform distribution. Does not support
     * removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<Integer> negativeIntegers() {
        return fromSupplier(this::nextNegativeInt);
    }

    /**
     * Returns a randomly-generated negative {@code long} from a uniform distribution.
     *
     * <ul>
     *  <li>{@code this} may be any {@code RandomProvider}.</li>
     *  <li>The result is negative.</li>
     * </ul>
     *
     * @return a negative {@code long}
     */
    public long nextNegativeLong() {
        long l;
        do {
            l = nextLong();
        } while (l >= 0);
        return l;
    }

    /**
     * An {@code Iterable} that generates all negative {@code Long}s from a uniform distribution. Does not support
     * removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<Long> negativeLongs() {
        return fromSupplier(this::nextNegativeLong);
    }

    /**
     * Returns a randomly-generated natural (non-negative) {@code byte} from a uniform distribution.
     *
     * <ul>
     *  <li>{@code this} may be any {@code RandomProvider}.</li>
     *  <li>The result is not negative.</li>
     * </ul>
     *
     * @return a natural {@code byte}
     */
    public byte nextNaturalByte() {
        return (byte) nextIntPow2(7);
    }

    /**
     * An {@code Iterable} that generates all natural {@code Byte}s (including 0) from a uniform distribution. Does
     * not support removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<Byte> naturalBytes() {
        return fromSupplier(this::nextNaturalByte);
    }

    /**
     * Returns a randomly-generated natural (non-negative) {@code short} from a uniform distribution.
     *
     * <ul>
     *  <li>{@code this} may be any {@code RandomProvider}.</li>
     *  <li>The result is not negative.</li>
     * </ul>
     *
     * @return a natural {@code short}
     */
    public short nextNaturalShort() {
        return (short) nextIntPow2(15);
    }

    /**
     * An {@code Iterable} that generates all natural {@code Short}s (including 0) from a uniform distribution. Does
     * not support removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<Short> naturalShorts() {
        return fromSupplier(this::nextNaturalShort);
    }

    /**
     * Returns a randomly-generated natural (non-negative) {@code int} from a uniform distribution.
     *
     * <ul>
     *  <li>{@code this} may be any {@code RandomProvider}.</li>
     *  <li>The result is not negative.</li>
     * </ul>
     *
     * @return a natural {@code int}
     */
    public int nextNaturalInt() {
        return nextIntPow2(31);
    }

    /**
     * An {@code Iterable} that generates all natural {@code Integer}s (including 0) from a uniform distribution.
     * Does not support removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<Integer> naturalIntegers() {
        return fromSupplier(this::nextNaturalInt);
    }

    /**
     * Returns a randomly-generated natural (non-negative) {@code long} from a uniform distribution.
     *
     * <ul>
     *  <li>{@code this} may be any {@code RandomProvider}.</li>
     *  <li>The result is not negative.</li>
     * </ul>
     *
     * @return a natural {@code long}
     */
    public long nextNaturalLong() {
        return nextLongPow2(63);
    }

    /**
     * An {@code Iterable} that generates all natural {@code Long}s (including 0) from a uniform distribution. Does
     * not support removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<Long> naturalLongs() {
        return fromSupplier(this::nextNaturalLong);
    }

    /**
     * Returns a randomly-generated nonzero {@code byte} from a uniform distribution.
     *
     * <ul>
     *  <li>{@code this} may be any {@code RandomProvider}.</li>
     *  <li>The result is not zero.</li>
     * </ul>
     *
     * @return a nonzero {@code byte}
     */
    public byte nextNonzeroByte() {
        byte b;
        do {
            b = nextByte();
        } while (b == 0);
        return b;
    }

    /**
     * Returns a randomly-generated nonzero {@code short} from a uniform distribution.
     *
     * <ul>
     *  <li>{@code this} may be any {@code RandomProvider}.</li>
     *  <li>The result is not zero.</li>
     * </ul>
     *
     * @return a nonzero {@code short}
     */
    public short nextNonzeroShort() {
        short s;
        do {
            s = nextShort();
        } while (s == 0);
        return s;
    }

    /**
     * Returns a randomly-generated nonzero {@code int} from a uniform distribution.
     *
     * <ul>
     *  <li>{@code this} may be any {@code RandomProvider}.</li>
     *  <li>The result is not zero.</li>
     * </ul>
     *
     * @return a nonzero {@code int}
     */
    public int nextNonzeroInt() {
        int i;
        do {
            i = nextInt();
        } while (i == 0);
        return i;
    }

    /**
     * Returns a randomly-generated nonzero {@code long} from a uniform distribution.
     *
     * <ul>
     *  <li>{@code this} may be any {@code RandomProvider}.</li>
     *  <li>The result is not zero.</li>
     * </ul>
     *
     * @return a nonzero {@code long}
     */
    public long nextNonzeroLong() {
        long l;
        do {
            l = nextLong();
        } while (l == 0L);
        return l;
    }

    /**
     * Returns a randomly-generated {@code byte} from a uniform distribution.
     *
     * <ul>
     *  <li>{@code this} may be any {@code RandomProvider}.</li>
     *  <li>The result may be any {@code byte}.</li>
     * </ul>
     *
     * @return a {@code byte}
     */
    public byte nextByte() {
        return (byte) nextInt();
    }

    /**
     * An {@code Iterable} that generates all {@code Byte}s from a uniform distribution. Does not support removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<Byte> bytes() {
        return fromSupplier(this::nextByte);
    }

    /**
     * Returns a randomly-generated {@code short} from a uniform distribution.
     *
     * <ul>
     *  <li>{@code this} may be any {@code RandomProvider}.</li>
     *  <li>The result may be any {@code short}.</li>
     * </ul>
     *
     * @return a {@code short}
     */
    public short nextShort() {
        return (short) nextInt();
    }

    /**
     * An {@code Iterable} that generates all {@code Short}s from a uniform distribution. Does not support removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<Short> shorts() {
        return fromSupplier(this::nextShort);
    }

    /**
     * Returns a randomly-generated ASCII {@code char} from a uniform distribution.
     *
     * <ul>
     *  <li>{@code this} may be any {@code RandomProvider}.</li>
     *  <li>The result is an ASCII {@code char}.</li>
     * </ul>
     *
     * @return an ASCII {@code char}
     */
    public char nextAsciiChar() {
        return (char) nextIntPow2(7);
    }

    /**
     * An {@code Iterable} that generates all ASCII {@code Character}s from a uniform distribution. Does not support
     * removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<Character> asciiCharacters() {
        return fromSupplier(this::nextAsciiChar);
    }

    /**
     * Returns a randomly-generated {@code char} from a uniform distribution.
     *
     * <ul>
     *  <li>{@code this} may be any {@code RandomProvider}.</li>
     *  <li>The result may be any {@code char}.</li>
     * </ul>
     *
     * @return a {@code char}
     */
    public char nextChar() {
        return (char) nextInt();
    }

    /**
     * An {@code Iterable} that generates all {@code Character}s from a uniform distribution. Does not support
     * removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<Character> characters() {
        return fromSupplier(this::nextChar);
    }

    /**
     * Returns a randomly-generated {@code byte} greater than or equal to {@code a}.
     *
     * <ul>
     *  <li>{@code this} may be any {@code RandomProvider}.</li>
     *  <li>The result may be any {@code byte}.</li>
     * </ul>
     *
     * @param a the inclusive lower bound of the generated {@code byte}
     * @return a {@code byte} greater than or equal to {@code a}
     */
    public byte nextFromRangeUp(byte a) {
        return (byte) (nextIntBounded((1 << 7) - a) + a);
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
        int offset = 1 << 7;
        return map(i -> (byte) (i + a), integersBounded(offset - a));
    }

    /**
     * Returns a randomly-generated {@code short} greater than or equal to {@code a}.
     *
     * <ul>
     *  <li>{@code this} may be any {@code RandomProvider}.</li>
     *  <li>The result may be any {@code short}.</li>
     * </ul>
     *
     * @param a the inclusive lower bound of the generated {@code short}
     * @return a {@code short} greater than or equal to {@code a}
     */
    public short nextFromRangeUp(short a) {
        return (short) (nextIntBounded((1 << 15) - a) + a);
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
        int offset = 1 << 15;
        return map(i -> (short) (i + a), integersBounded(offset - a));
    }

    /**
     * Returns a randomly-generated {@code int} greater than or equal to {@code a}.
     *
     * <ul>
     *  <li>{@code this} may be any {@code RandomProvider}.</li>
     *  <li>The result may be any {@code int}.</li>
     * </ul>
     *
     * @param a the inclusive lower bound of the generated {@code int}
     * @return an {@code int} greater than or equal to {@code a}
     */
    public int nextFromRangeUp(int a) {
        return (int) (nextLongBounded((1L << 31) - a) + a);
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
        long offset = 1L << 31;
        return map(l -> (int) (l + a), longsBounded(offset - a));
    }

    /**
     * Returns a randomly-generated {@code long} greater than or equal to {@code a}.
     *
     * <ul>
     *  <li>{@code this} may be any {@code RandomProvider}.</li>
     *  <li>The result may be any {@code long}.</li>
     * </ul>
     *
     * @param a the inclusive lower bound of the generated {@code long}
     * @return a {@code long} greater than or equal to {@code a}
     */
    public long nextFromRangeUp(long a) {
        BigInteger ba = BigInteger.valueOf(a);
        return nextBigIntegerBounded(BigInteger.ONE.shiftLeft(63).subtract(ba)).add(ba).longValueExact();
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
        BigInteger offset = BigInteger.ONE.shiftLeft(63);
        return map(
                i -> i.add(BigInteger.valueOf(a)).longValueExact(),
                bigIntegersBounded(offset.subtract(BigInteger.valueOf(a)))
        );
    }

    /**
     * Returns a randomly-generated {@code char} greater than or equal to {@code a}.
     *
     * <ul>
     *  <li>{@code this} may be any {@code RandomProvider}.</li>
     *  <li>The result may be any {@code char}.</li>
     * </ul>
     *
     * @param a the inclusive lower bound of the generated {@code char}
     * @return a {@code char} greater than or equal to {@code a}
     */
    public char nextFromRangeUp(char a) {
        return (char) (nextIntBounded((1 << 16) - a) + a);
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
        int offset = 1 << 16;
        return map(i -> (char) (i + a), integersBounded(offset - a));
    }

    /**
     * Returns a randomly-generated {@code byte} less than or equal to {@code a}.
     *
     * <ul>
     *  <li>{@code this} may be any {@code RandomProvider}.</li>
     *  <li>The result may be any {@code byte}.</li>
     * </ul>
     *
     * @param a the inclusive upper bound of the generated {@code byte}
     * @return a {@code byte} less than or equal to {@code a}
     */
    public byte nextFromRangeDown(byte a) {
        int offset = 1 << 7;
        return (byte) (nextIntBounded(a + offset + 1) - offset);
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
        return map(i -> (byte) (i - offset), integersBounded(a + offset + 1));
    }

    /**
     * Returns a randomly-generated {@code short} less than or equal to {@code a}.
     *
     * <ul>
     *  <li>{@code this} may be any {@code RandomProvider}.</li>
     *  <li>The result may be any {@code short}.</li>
     * </ul>
     *
     * @param a the inclusive upper bound of the generated {@code short}
     * @return a {@code short} less than or equal to {@code a}
     */
    public short nextFromRangeDown(short a) {
        int offset = 1 << 15;
        return (short) (nextIntBounded(a + offset + 1) - offset);
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
        return map(i -> (short) (i - offset), integersBounded(a + offset + 1));
    }

    /**
     * Returns a randomly-generated {@code int} less than or equal to {@code a}.
     *
     * <ul>
     *  <li>{@code this} may be any {@code RandomProvider}.</li>
     *  <li>The result may be any {@code int}.</li>
     * </ul>
     *
     * @param a the inclusive upper bound of the generated {@code int}
     * @return an {@code int} less than or equal to {@code a}
     */
    public int nextFromRangeDown(int a) {
        long offset = 1L << 31;
        return (int) (nextLongBounded(a + offset + 1) - offset);
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
        return map(l -> (int) (l - offset), longsBounded(a + offset + 1));
    }

    /**
     * Returns a randomly-generated {@code long} less than or equal to {@code a}.
     *
     * <ul>
     *  <li>{@code this} may be any {@code RandomProvider}.</li>
     *  <li>The result may be any {@code long}.</li>
     * </ul>
     *
     * @param a the inclusive upper bound of the generated {@code long}
     * @return a {@code long} less than or equal to {@code a}
     */
    public long nextFromRangeDown(long a) {
        BigInteger offset = BigInteger.ONE.shiftLeft(63);
        BigInteger ba = BigInteger.valueOf(a);
        return nextBigIntegerBounded(ba.add(BigInteger.ONE).add(offset)).subtract(offset).longValueExact();
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
        BigInteger offset = BigInteger.ONE.shiftLeft(63);
        return map(
                i -> i.subtract(offset).longValueExact(),
                bigIntegersBounded(BigInteger.valueOf(a).add(BigInteger.ONE).add(offset))
        );
    }

    /**
     * Returns a randomly-generated {@code char} less than or equal to {@code a}.
     *
     * <ul>
     *  <li>{@code this} may be any {@code RandomProvider}.</li>
     *  <li>The result may be any {@code char}.</li>
     * </ul>
     *
     * @param a the inclusive upper bound of the generated {@code char}
     * @return a {@code char} less than or equal to {@code a}
     */
    public char nextFromRangeDown(char a) {
        return (char) nextIntBounded(a + 1);
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
        return fromSupplier(() -> nextFromRangeDown(a));
    }

    /**
     * Returns a randomly-generated {@code byte} between {@code a} and {@code b}, inclusive.
     *
     * <ul>
     *  <li>{@code a} may be any {@code byte}.</li>
     *  <li>{@code b} may be any {@code byte}.</li>
     *  <li>{@code a} must be less than or equal to {@code b}.</li>
     *  <li>The result may be any {@code byte}.</li>
     * </ul>
     *
     * @param a the inclusive lower bound of the generated {@code byte}
     * @param b the inclusive upper bound of the generated {@code byte}
     * @return a {@code byte} between {@code a} and {@code b}, inclusive
     */
    public byte nextFromRange(byte a, byte b) {
        if (a > b) {
            throw new IllegalArgumentException("a must be less than or equal to b. a is " + a + " and b is " + b +
                    ".");
        }
        return (byte) (nextIntBounded((int) b - a + 1) + a);
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
        return map(i -> (byte) (i + a), integersBounded((int) b - a + 1));
    }

    /**
     * Returns a randomly-generated {@code short} between {@code a} and {@code b}, inclusive.
     *
     * <ul>
     *  <li>{@code a} may be any {@code short}.</li>
     *  <li>{@code b} may be any {@code short}.</li>
     *  <li>{@code a} must be less than or equal to {@code b}.</li>
     *  <li>The result may be any {@code short}.</li>
     * </ul>
     *
     * @param a the inclusive lower bound of the generated {@code short}
     * @param b the inclusive upper bound of the generated {@code short}
     * @return a {@code short} between {@code a} and {@code b}, inclusive
     */
    public short nextFromRange(short a, short b) {
        if (a > b) {
            throw new IllegalArgumentException("a must be less than or equal to b. a is " + a + " and b is " + b +
                    ".");
        }
        return (short) (nextIntBounded((int) b - a + 1) + a);
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
        return map(i -> (short) (i + a), integersBounded((int) b - a + 1));
    }

    /**
     * Returns a randomly-generated {@code int} between {@code a} and {@code b}, inclusive.
     *
     * <ul>
     *  <li>{@code a} may be any {@code int}.</li>
     *  <li>{@code b} may be any {@code int}.</li>
     *  <li>{@code a} must be less than or equal to {@code b}.</li>
     *  <li>The result may be any {@code int}.</li>
     * </ul>
     *
     * @param a the inclusive lower bound of the generated {@code int}
     * @param b the inclusive upper bound of the generated {@code int}
     * @return an {@code int} between {@code a} and {@code b}, inclusive
     */
    public int nextFromRange(int a, int b) {
        if (a > b) {
            throw new IllegalArgumentException("a must be less than or equal to b. a is " + a + " and b is " + b +
                    ".");
        }
        return (int) (nextLongBounded((long) b - a + 1) + a);
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
        return map(i -> (int) (i + a), longsBounded((long) b - a + 1));
    }

    /**
     * Returns a randomly-generated {@code long} between {@code a} and {@code b}, inclusive.
     *
     * <ul>
     *  <li>{@code a} may be any {@code long}.</li>
     *  <li>{@code b} may be any {@code long}.</li>
     *  <li>{@code a} must be less than or equal to {@code b}.</li>
     *  <li>The result may be any {@code long}.</li>
     * </ul>
     *
     * @param a the inclusive lower bound of the generated {@code long}
     * @param b the inclusive upper bound of the generated {@code long}
     * @return a {@code long} between {@code a} and {@code b}, inclusive
     */
    public long nextFromRange(long a, long b) {
        if (a > b) {
            throw new IllegalArgumentException("a must be less than or equal to b. a is " + a + " and b is " + b +
                    ".");
        }
        BigInteger ba = BigInteger.valueOf(a);
        BigInteger bb = BigInteger.valueOf(b);
        return nextBigIntegerBounded(bb.subtract(ba).add(BigInteger.ONE)).add(ba).longValueExact();
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
                bigIntegersBounded(BigInteger.valueOf(b).subtract(BigInteger.valueOf(a)).add(BigInteger.ONE))
        );
    }

    /**
     * Returns a randomly-generated {@code BigInteger} between {@code a} and {@code b}, inclusive.
     *
     * <ul>
     *  <li>{@code a} may be any {@code BigInteger}.</li>
     *  <li>{@code b} may be any {@code BigInteger}.</li>
     *  <li>{@code a} must be less than or equal to {@code b}.</li>
     *  <li>The result is not null.</li>
     * </ul>
     *
     * @param a the inclusive lower bound of the generated {@code BigInteger}
     * @param b the inclusive upper bound of the generated {@code BigInteger}
     * @return a {@code BigInteger} between {@code a} and {@code b}, inclusive
     */
    public @NotNull BigInteger nextFromRange(@NotNull BigInteger a, @NotNull BigInteger b) {
        if (gt(a, b)) {
            throw new IllegalArgumentException("a must be less than or equal to b. a is " + a + " and b is " + b +
                    ".");
        }
        return nextBigIntegerBounded(b.subtract(a).add(BigInteger.ONE)).add(a);
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
        if (gt(a, b)) return Collections.emptyList();
        return map(i -> i.add(a), bigIntegersBounded(b.subtract(a).add(BigInteger.ONE)));
    }

    /**
     * Returns a randomly-generated {@code char} between {@code a} and {@code b}, inclusive.
     *
     * <ul>
     *  <li>{@code a} may be any {@code char}.</li>
     *  <li>{@code b} may be any {@code char}.</li>
     *  <li>{@code a} must be less than or equal to {@code b}.</li>
     *  <li>The result may be any {@code char}.</li>
     * </ul>
     *
     * @param a the inclusive lower bound of the generated {@code char}
     * @param b the inclusive upper bound of the generated {@code char}
     * @return a {@code char} between {@code a} and {@code b}, inclusive
     */
    public char nextFromRange(char a, char b) {
        if (a > b) {
            throw new IllegalArgumentException("a must be less than or equal to b. a is " + nicePrint(a) +
            " and b is " + nicePrint(b) + ".");
        }
        return (char) (nextIntBounded(b - a + 1) + a);
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
        return map(i -> (char) (i + a), integersBounded(b - a + 1));
    }

    /**
     * Returns a randomly-generated positive {@code int} from a geometric distribution with mean {@code scale}.
     *
     * <ul>
     *  <li>{@code this} must have a scale of at least 2.</li>
     *  <li>The result is positive.</li>
     * </ul>
     *
     * @return a positive {@code int}
     */
    public int nextPositiveIntGeometric() {
        if (scale < 2) {
            throw new IllegalStateException("this must have a scale of at least 2. Invalid scale: " + scale);
        }
        int i;
        int j = 0;
        do {
            i = nextIntBounded(scale);
            j++;
        } while (i != 0);
        return j;
    }

    /**
     * An {@code Iterable} that generates all positive {@code Integer}s chosen from a geometric distribution with mean
     * {@code scale}. The distribution is truncated at {@code Integer.MAX_VALUE}. Does not support removal.
     *
     * <ul>
     *  <li>{@code this} must have a scale of at least 2.</li>
     *  <li>The result is an infinite, non-removable {@code Iterable} containing positive {@code Integer}s.</li>
     * </ul>
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<Integer> positiveIntegersGeometric() {
        if (scale < 2) {
            throw new IllegalStateException("this must have a scale of at least 2. Invalid scale: " + scale);
        }
        return () -> new NoRemoveIterator<Integer>() {
            private @NotNull Iterator<Integer> is = integersBounded(scale).iterator();

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public Integer next() {
                int i;
                int j = 0;
                do {
                    i = is.next();
                    j++;
                } while (i != 0);
                return j;
            }
        };
    }

    /**
     * Returns a randomly-generated negative {@code int} from a geometric distribution with mean {@code scale}.
     *
     * <ul>
     *  <li>{@code this} must have a scale of at least 2.</li>
     *  <li>The result is negative.</li>
     * </ul>
     *
     * @return a negative {@code int}
     */
    public int nextNegativeIntGeometric() {
        return -nextPositiveIntGeometric();
    }

    /**
     * An {@code Iterable} that generates all negative {@code Integer}s chosen from a geometric distribution with mean
     * –{@code scale}. The distribution is truncated at –{@code Integer.MAX_VALUE}. Does not support removal.
     *
     * <ul>
     *  <li>{@code this} must have a scale of at least 2.</li>
     *  <li>The result is an infinite, non-removable {@code Iterable} containing negative {@code Integer}s.</li>
     * </ul>
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<Integer> negativeIntegersGeometric() {
        if (scale < 2) {
            throw new IllegalStateException("this must have a scale of at least 2. Invalid scale: " + scale);
        }
        return fromSupplier(this::nextNegativeIntGeometric);
    }

    /**
     * Returns a randomly-generated natural {@code int} from a geometric distribution with mean {@code scale}.
     *
     * <ul>
     *  <li>{@code this} must have a positive scale. The scale cannot be {@code Integer.MAX_VALUE}.</li>
     *  <li>The result is non-negative.</li>
     * </ul>
     *
     * @return a natural {@code int}
     */
    public int nextNaturalIntGeometric() {
        if (scale < 1) {
            throw new IllegalStateException("this must have a positive scale. Invalid scale: " + scale);
        }
        if (scale == Integer.MAX_VALUE) {
            throw new IllegalStateException("this cannot have a scale of Integer.MAX_VALUE, or " + scale);
        }
        int i;
        int j = 0;
        do {
            i = nextIntBounded(scale + 1);
            j++;
        } while (i != 0);
        return j - 1;
    }

    /**
     * An {@code Iterable} that generates all natural {@code Integer}s (including 0) chosen from a geometric
     * distribution with mean {@code scale}. The distribution is truncated at {@code Integer.MAX_VALUE}. Does not
     * support removal.
     *
     * <ul>
     *  <li>{@code this} must have a positive scale. The scale cannot be {@code Integer.MAX_VALUE}.</li>
     *  <li>The result is an infinite, non-removable {@code Iterable} containing natural {@code Integer}s.</li>
     * </ul>
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<Integer> naturalIntegersGeometric() {
        if (scale < 1) {
            throw new IllegalStateException("this must have a positive scale. Invalid scale: " + scale);
        }
        if (scale == Integer.MAX_VALUE) {
            throw new IllegalStateException("this cannot have a scale of Integer.MAX_VALUE, or " + scale);
        }
        return map(i -> i - 1, withScale(scale + 1).positiveIntegersGeometric());
    }

    /**
     * Returns a randomly-generated natural {@code int} from a geometric distribution with mean
     * {@code numerator}/{@code denominator}.
     *
     * <ul>
     *  <li>{@code numerator} must be positive.</li>
     *  <li>{@code denominator} must be positive.</li>
     *  <li>The sum of {@code numerator} and {@code denominator} must be less than 2<sup>31</sup>.</li>
     *  <li>The result is non-negative.</li>
     * </ul>
     *
     * @return a natural {@code int}
     */
    private int nextNaturalIntGeometric(int numerator, int denominator) {
        if (numerator < 1) {
            throw new IllegalArgumentException("numerator must be positive. numerator: " + numerator);
        }
        if (denominator < 1) {
            throw new IllegalArgumentException("denominator must be positive. denominator: " + denominator);
        }
        long sum = (long) numerator + denominator;
        if (sum > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("The sum of numerator and denominator must be less than 2^31." +
                    " numerator is " + numerator + " and denominator is " + denominator + ".");
        }
        int i;
        int j = 0;
        do {
            i = nextIntBounded((int) sum);
            j++;
        } while (i >= denominator);
        return j - 1;
    }

    /**
     * Returns a randomly-generated nonzero {@code int} from a geometric distribution with mean {@code scale}.
     *
     * <ul>
     *  <li>{@code this} must have a scale of at least 2.</li>
     *  <li>The result is nonzero.</li>
     * </ul>
     *
     * @return a nonzero {@code int}
     */
    public int nextNonzeroIntGeometric() {
        int i = nextPositiveIntGeometric();
        return nextBoolean() ? i : -i;
    }

    /**
     * An {@code Iterable} that generates all nonzero {@code Integer}s whose absolute value is chosen from a geometric
     * distribution with mean {@code scale}, and whose sign is chosen uniformly. The distribution is truncated at
     * ±{@code Integer.MAX_VALUE}. Does not support removal.
     *
     * <ul>
     *  <li>{@code this} must have a scale of at least 2.</li>
     *  <li>The result is an infinite, non-removable {@code Iterable} containing natural {@code Integer}s.</li>
     * </ul>
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<Integer> nonzeroIntegersGeometric() {
        if (scale < 2) {
            throw new IllegalStateException("this must have a scale of at least 2. Invalid scale: " + scale);
        }
        return fromSupplier(this::nextNonzeroIntGeometric);
    }

    /**
     * Returns a randomly-generated {@code int} from a geometric distribution with mean {@code scale}.
     *
     * <ul>
     *  <li>{@code this} must have a positive scale. The scale cannot be {@code Integer.MAX_VALUE}.</li>
     *  <li>The result may be any {@code int}.</li>
     * </ul>
     *
     * @return a negative {@code int}
     */
    public int nextIntGeometric() {
        int i = nextNaturalIntGeometric();
        return nextBoolean() ? i : -i;
    }

    /**
     * An {@code Iterable} that generates all {@code Integer}s whose absolute value is chosen from a geometric
     * distribution with mean {@code scale}, and whose sign is chosen uniformly. The distribution is truncated at
     * ±{@code Integer.MAX_VALUE}. Does not support removal.
     *
     * <ul>
     *  <li>{@code this} must have a positive scale. The scale cannot be {@code Integer.MAX_VALUE}.</li>
     *  <li>The result is an infinite, non-removable {@code Iterable} containing {@code Integer}s.</li>
     * </ul>
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<Integer> integersGeometric() {
        if (scale < 1) {
            throw new IllegalStateException("this must have a positive scale. Invalid scale: " + scale);
        }
        if (scale == Integer.MAX_VALUE) {
            throw new IllegalStateException("this cannot have a scale of Integer.MAX_VALUE, or " + scale);
        }
        return fromSupplier(this::nextIntGeometric);
    }

    /**
     * Returns a randomly-generated {@code int} whose absolute value is chosen from a geometric distribution with mean
     * {@code numerator}/{@code denominator}, and whose sign is chosen uniformly.
     *
     * <ul>
     *  <li>{@code numerator} must be positive.</li>
     *  <li>{@code denominator} must be positive.</li>
     *  <li>The sum of {@code numerator} and {@code denominator} must be less than 2<sup>31</sup>.</li>
     *  <li>The result is an infinite, non-removable {@code Iterable} containing {@code Integer}s.</li>
     * </ul>
     *
     * @return a negative {@code int}
     */
    private int nextIntGeometric(int numerator, int denominator) {
        int absolute = nextNaturalIntGeometric(numerator, denominator);
        return nextBoolean() ? absolute : -absolute;
    }

    /**
     * Returns a randomly-generated {@code int} greater than or equal to {@code a}, chosen from a geometric
     * distribution with mean {@code scale}.
     *
     * <ul>
     *  <li>{@code this} must have a scale greater than {@code a} and less than {@code Integer.MAX_VALUE}+a.</li>
     *  <li>{@code a} may be any {@code int}.</li>
     *  <li>The result may be any {@code int}.</li>
     * </ul>
     *
     * @return an {@code int} greater than or equal to {@code a}
     */
    public int nextIntGeometricFromRangeUp(int a) {
        if (scale <= a) {
            throw new IllegalStateException("this must have a scale greater than a, which is " + a +
                    ". Invalid scale: " + scale);
        }
        if (a < 1 && scale >= Integer.MAX_VALUE + a) {
            throw new IllegalStateException("this must have a scale less than Integer.MAX_VALUE + a, which is " +
                    (Integer.MAX_VALUE + a));
        }
        int j;
        do {
            int i;
            j = 0;
            do {
                i = nextIntBounded(scale - a + 1);
                j++;
            } while (i != 0);
            j += a - 1;
        } while (j < a);
        return j;
    }

    /**
     * An {@code Iterable} that generates all {@code Integer}s greater than or equal to {@code a}, chosen from a
     * geometric distribution with mean {@code scale}. Does not support removal.
     *
     * <ul>
     *  <li>{@code this} must have a scale greater than {@code a} and less than {@code Integer.MAX_VALUE}+a.</li>
     *  <li>{@code a} may be any {@code int}.</li>
     *  <li>The result is an infinite, non-removable {@code Iterable} containing {@code Integer}s.</li>
     * </ul>
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<Integer> rangeUpGeometric(int a) {
        if (scale <= a) {
            throw new IllegalStateException("this must have a scale greater than a, which is " + a +
                    ". Invalid scale: " + scale);
        }
        if (a < 1 && scale >= Integer.MAX_VALUE + a) {
            throw new IllegalStateException("this must have a scale less than Integer.MAX_VALUE + a, which is " +
                    (Integer.MAX_VALUE + a));
        }
        return filter(j -> j >= a, map(i -> i + a - 1, withScale(scale - a + 1).positiveIntegersGeometric()));
    }

    /**
     * Returns a randomly-generated {@code int} less than or equal to {@code a}, chosen from a geometric distribution
     * with mean {@code scale}.
     *
     * <ul>
     *  <li>{@code this} must have a scale less than {@code a} and greater than
     *  {@code a}–{@code Integer.MAX_VALUE}.</li>
     *  <li>{@code a} may be any {@code int}.</li>
     *  <li>The result may be any {@code int}.</li>
     * </ul>
     *
     * @return an {@code int} less than or equal to {@code a}
     */
    public int nextIntGeometricFromRangeDown(int a) {
        if (scale >= a) {
            throw new IllegalStateException("this must have a scale less than a, which is " + a + ". Invalid scale: " +
                    scale);
        }
        if (a > -1 && scale <= a - Integer.MAX_VALUE) {
            throw new IllegalStateException("this must have a scale greater than a - Integer.MAX_VALUE, which is " +
                    (a - Integer.MAX_VALUE));
        }
        int j;
        do {
            int i;
            j = 0;
            do {
                i = nextIntBounded(a - scale + 1);
                j++;
            } while (i != 0);
            j = a - j + 1;
        } while (j > a);
        return j;
    }

    /**
     * An {@code Iterable} that generates all {@code Integer}s less than or equal to {@code a}, chosen from a geometric
     * distribution with mean {@code scale}. Does not support removal.
     *
     * <ul>
     *  <li>{@code this} must have a scale less than {@code a} and greater than
     *  {@code a}–{@code Integer.MAX_VALUE}.</li>
     *  <li>{@code a} may be any {@code int}.</li>
     *  <li>The result is an infinite, non-removable {@code Iterable} containing {@code Integer}s.</li>
     * </ul>
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<Integer> rangeDownGeometric(int a) {
        if (scale >= a) {
            throw new IllegalStateException("this must have a scale less than a, which is " + a + ". Invalid scale: " +
                    scale);
        }
        if (a > -1 && scale <= a - Integer.MAX_VALUE) {
            throw new IllegalStateException("this must have a scale greater than a - Integer.MAX_VALUE, which is " +
                    (a - Integer.MAX_VALUE));
        }
        return filter(j -> j <= a, map(i -> a - i + 1, withScale(a - scale + 1).positiveIntegersGeometric()));
    }

    /**
     * Returns a randomly-generated positive {@code BigInteger}. The bit size is chosen from a geometric distribution
     * with mean {@code scale}, and then the {@code BigInteger} is chosen uniformly from all {@code BigInteger}s with
     * that bit size.
     *
     * <ul>
     *  <li>{@code this} must have a scale of at least 2.</li>
     *  <li>The result is positive.</li>
     * </ul>
     *
     * @return a positive {@code BigInteger}
     */
    public @NotNull BigInteger nextPositiveBigInteger() {
        int size = nextPositiveIntGeometric();
        return nextBigIntegerPow2(size).setBit(size - 1);
    }

    /**
     * An {@code Iterable} that generates all positive {@code BigInteger}s. The bit size is chosen from a geometric
     * distribution with mean {@code scale}, and then the {@code BigInteger} is chosen uniformly from all
     * {@code BigInteger}s with that bit size. Does not support removal.
     *
     * <ul>
     *  <li>{@code this} must have a scale of at least 2.</li>
     *  <li>The result is an infinite, non-removable {@code Iterable} containing positive {@code BigInteger}s.</li>
     * </ul>
     *
     * Length is infinite
     */
    public @NotNull Iterable<BigInteger> positiveBigIntegers() {
        if (scale < 2) {
            throw new IllegalStateException("this must have a scale of at least 2. Invalid scale: " + scale);
        }
        return fromSupplier(this::nextPositiveBigInteger);
    }

    /**
     * Returns a randomly-generated negative {@code BigInteger}. The bit size is chosen from a geometric distribution
     * with mean {@code scale}, and then the {@code BigInteger} is chosen uniformly from all {@code BigInteger}s with
     * that bit size.
     *
     * <ul>
     *  <li>{@code this} must have a scale of at least 2.</li>
     *  <li>The result is negative.</li>
     * </ul>
     *
     * @return a negative {@code BigInteger}
     */
    public @NotNull BigInteger nextNegativeBigInteger() {
        return nextPositiveBigInteger().negate();
    }

    /**
     * An {@code Iterable} that generates all negative {@code BigInteger}s. The bit size is chosen from a geometric
     * distribution with mean {@code scale}, and then the {@code BigInteger} is chosen uniformly from all
     * {@code BigInteger}s with that bit size. Does not support removal.
     *
     * <ul>
     *  <li>{@code this} must have a scale of at least 2.</li>
     *  <li>The result is an infinite, non-removable {@code Iterable} containing negative {@code BigInteger}s.</li>
     * </ul>
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<BigInteger> negativeBigIntegers() {
        return map(BigInteger::negate, positiveBigIntegers());
    }

    /**
     * Returns a randomly-generated natural {@code BigInteger}. The bit size is chosen from a geometric distribution
     * with mean {@code scale}, and then the {@code BigInteger} is chosen uniformly from all {@code BigInteger}s with
     * that bit size.
     *
     * <ul>
     *  <li>{@code this} must have a positive scale. The scale cannot be {@code Integer.MAX_VALUE}.</li>
     *  <li>The result is non-negative.</li>
     * </ul>
     *
     * @return a natural {@code BigInteger}
     */
    public @NotNull BigInteger nextNaturalBigInteger() {
        if (scale < 1) {
            throw new IllegalStateException("this must have a positive scale. Invalid scale: " + scale);
        }
        if (scale == Integer.MAX_VALUE) {
            throw new IllegalStateException("this cannot have a scale of Integer.MAX_VALUE, or " + scale);
        }
        int size = nextNaturalIntGeometric();
        if (size == 0) return BigInteger.ZERO;
        return nextBigIntegerPow2(size).setBit(size - 1);
    }

    /**
     * An {@code Iterable} that generates all natural {@code BigInteger}s (including 0). The bit size is chosen from a
     * geometric distribution with mean {@code scale}, and then the {@code BigInteger} is chosen uniformly from all
     * {@code BigInteger}s with that bit size. Does not support removal.
     *
     * <ul>
     *  <li>{@code this} must have a positive scale. The scale cannot be {@code Integer.MAX_VALUE}.</li>
     *  <li>The result is an infinite, non-removable {@code Iterable} containing negative {@code BigInteger}s.</li>
     * </ul>
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<BigInteger> naturalBigIntegers() {
        if (scale < 1) {
            throw new IllegalStateException("this must have a positive scale. Invalid scale: " + scale);
        }
        if (scale == Integer.MAX_VALUE) {
            throw new IllegalStateException("this cannot have a scale of Integer.MAX_VALUE, or " + scale);
        }
        return fromSupplier(this::nextNaturalBigInteger);
    }

    /**
     * Returns a randomly-generated nonzero {@code BigInteger}. The bit size is chosen from a geometric distribution
     * with mean {@code scale}, and then the {@code BigInteger} is chosen uniformly from all {@code BigInteger}s with
     * that bit size.
     *
     * <ul>
     *  <li>{@code this} must have a scale of at least 2.</li>
     *  <li>The result is not zero.</li>
     * </ul>
     *
     * @return a nonzero {@code BigInteger}
     */
    public @NotNull BigInteger nextNonzeroBigInteger() {
        BigInteger i = nextPositiveBigInteger();
        return nextBoolean() ? i : i.negate();
    }

    /**
     * An {@code Iterable} that generates all nonzero {@code BigInteger}s. The bit size is chosen from a
     * geometric distribution with mean {@code scale}, and then the {@code BigInteger} is chosen uniformly from all
     * {@code BigInteger}s with that bit size; the sign is also chosen uniformly. Does not support removal.
     *
     * <ul>
     *  <li>{@code this} must have a scale of at least 2.</li>
     *  <li>The result is an infinite, non-removable {@code Iterable} containing nonzero {@code BigInteger}s.</li>
     * </ul>
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<BigInteger> nonzeroBigIntegers() {
        if (scale < 2) {
            throw new IllegalStateException("this must have a scale of at least 2. Invalid scale: " + scale);
        }
        return fromSupplier(this::nextNonzeroBigInteger);
    }

    /**
     * Returns a randomly-generated {@code BigInteger}. The bit size is chosen from a geometric distribution with mean
     * {@code scale}, and then the {@code BigInteger} is chosen uniformly from all {@code BigInteger}s with that bit
     * size.
     *
     * <ul>
     *  <li>{@code this} must have a positive scale. The scale cannot be {@code Integer.MAX_VALUE}.</li>
     *  <li>The result is not null.</li>
     * </ul>
     *
     * @return a {@code BigInteger}
     */
    public @NotNull BigInteger nextBigInteger() {
        BigInteger i = nextNaturalBigInteger();
        return nextBoolean() ? i : i.negate();
    }

    /**
     * An {@code Iterable} that generates all natural {@code BigInteger}s. The bit size is chosen from a
     * geometric distribution with mean {@code scale}, and then the {@code BigInteger} is chosen uniformly from all
     * {@code BigInteger}s with that bit size; the sign is also chosen uniformly. Does not support removal.
     *
     * <ul>
     *  <li>{@code this} must have a positive scale.</li>
     *  <li>The result is an infinite, non-removable {@code Iterable} containing {@code BigInteger}s.</li>
     * </ul>
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<BigInteger> bigIntegers() {
        if (scale < 1) {
            throw new IllegalStateException("this must have a positive scale. Invalid scale: " + scale);
        }
        if (scale == Integer.MAX_VALUE) {
            throw new IllegalStateException("this cannot have a scale of Integer.MAX_VALUE, or " + scale);
        }
        return fromSupplier(this::nextBigInteger);
    }

    /**
     * Returns a randomly-generated {@code BigInteger} greater than or equal to {@code a}. The bit size is chosen from
     * a geometric distribution with mean {@code scale}, and then the {@code BigInteger} is chosen uniformly from all
     * {@code BigInteger}s greater than or equal to {@code a} with that bit size.
     *
     * <ul>
     *  <li>Let {@code minBitLength} be 0 if {@code a} is negative, and ⌊log<sub>2</sub>({@code a})⌋ otherwise.
     *  {@code this} must have a scale greater than {@code minBitLength}. If {@code minBitLength} is 0, {@code scale}
     *  cannot be {@code Integer.MAX_VALUE}.</li>
     *  <li>{@code a} may be any {@code int}.</li>
     *  <li>The result is not null.</li>
     * </ul>
     *
     * @return a {@code BigInteger} greater than or equal to {@code a}
     */
    public @NotNull BigInteger nextFromRangeUp(@NotNull BigInteger a) {
        int minBitLength = a.signum() == -1 ? 0 : a.bitLength();
        if (scale <= minBitLength) {
            throw new IllegalStateException("this must have a scale greater than minBitLength, which is " +
                    minBitLength + ". Invalid scale: " + scale);
        }
        if (minBitLength == 0 && scale == Integer.MAX_VALUE) {
            throw new IllegalStateException("If {@code minBitLength} is 0, {@code scale} cannot be" +
                    " {@code Integer.MAX_VALUE}.");
        }
        int absBitLength = a.abs().bitLength();
        int size = nextIntGeometricFromRangeUp(minBitLength);
        BigInteger i;
        if (size != absBitLength) {
            if (size == 0) {
                i = BigInteger.ZERO;
            } else {
                i = nextBigIntegerPow2(size);
                boolean mostSignificantBit = i.testBit(size - 1);
                if (!mostSignificantBit) {
                    i = i.setBit(size - 1);
                    if (size < absBitLength && a.signum() == -1) {
                        i = i.negate();
                    }
                }
            }
        } else {
            if (a.signum() != -1) {
                i = nextBigIntegerBounded(BigInteger.ONE.shiftLeft(absBitLength).subtract(a)).add(a);
            } else {
                BigInteger b = BigInteger.ONE.shiftLeft(absBitLength - 1);
                BigInteger x = nextBigIntegerBounded(b.add(a.negate().subtract(b)).add(BigInteger.ONE));
                i = lt(x, b) ? x.add(b) : b.negate().subtract(x.subtract(b));
            }
        }
        return i;
    }

    /**
     * An {@code Iterable} that generates all {@code BigInteger}s greater than or equal to {@code a}. The bit size is
     * chosen from a geometric distribution with mean {@code scale}, and then the {@code BigInteger} is chosen
     * uniformly from all {@code BigInteger}s greater than or equal to {@code a} with that bit size. Does not support
     * removal.
     *
     * <ul>
     *  <li>Let {@code minBitLength} be 0 if {@code a} is negative, and ⌊log<sub>2</sub>({@code a})⌋ otherwise.
     *  {@code this} must have a scale greater than {@code minBitLength}. If {@code minBitLength} is 0, {@code scale}
     *  cannot be {@code Integer.MAX_VALUE}.</li>
     *  <li>{@code a} cannot be null.</li>
     *  <li>The result is an infinite, non-removable {@code Iterable} containing {@code BigInteger}s.</li>
     * </ul>
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<BigInteger> rangeUp(@NotNull BigInteger a) {
        int minBitLength = a.signum() == -1 ? 0 : a.bitLength();
        if (scale <= minBitLength) {
            throw new IllegalStateException("this must have a scale greater than minBitLength, which is " +
                    minBitLength + ". Invalid scale: " + scale);
        }
        if (minBitLength == 0 && scale == Integer.MAX_VALUE) {
            throw new IllegalStateException("If {@code minBitLength} is 0, {@code scale} cannot be" +
                    " {@code Integer.MAX_VALUE}.");
        }
        return fromSupplier(() -> nextFromRangeUp(a));
    }

    /**
     * Returns a randomly-generated {@code BigInteger} less than or equal to {@code a}. The bit size is chosen from a
     * geometric distribution with mean {@code scale}, and then the {@code BigInteger} is chosen uniformly from all
     * {@code BigInteger}s less than or equal to {@code a} with that bit size.
     *
     * <ul>
     *  <li>Let {@code minBitLength} be 0 if {@code a} is positive, and ⌊log<sub>2</sub>(–{@code a})⌋ otherwise.
     *  {@code this} must have a scale greater than {@code minBitLength}. If {@code minBitLength} is 0, {@code scale}
     *  cannot be {@code Integer.MAX_VALUE}.</li>
     *  <li>{@code a} may be any {@code BigInteger}.</li>
     *  <li>The result is not null.</li>
     * </ul>
     *
     * @return a {@code BigInteger} less than or equal to {@code a}
     */
    public @NotNull BigInteger nextFromRangeDown(@NotNull BigInteger a) {
        return nextFromRangeUp(a.negate()).negate();
    }

    /**
     * An {@code Iterable} that generates all {@code BigInteger}s less than or equal to {@code a}. The bit size is
     * chosen from a geometric distribution with mean {@code scale}, and then the {@code BigInteger} is chosen
     * uniformly from all {@code BigInteger}s greater than or equal to {@code a} with that bit size. Does not support
     * removal.
     *
     * <ul>
     *  <li>Let {@code minBitLength} be 0 if {@code a} is positive, and ⌊log<sub>2</sub>(–{@code a})⌋ otherwise.
     *  {@code this} must have a scale greater than {@code minBitLength}. If {@code minBitLength} is 0, {@code scale}
     *  cannot be {@code Integer.MAX_VALUE}.</li>
     *  <li>{@code a} cannot be null.</li>
     *  <li>The result is an infinite, non-removable {@code Iterable} containing {@code BigInteger}s.</li>
     * </ul>
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<BigInteger> rangeDown(@NotNull BigInteger a) {
        int minBitLength = a.signum() == 1 ? 0 : a.negate().bitLength();
        if (scale <= minBitLength) {
            throw new IllegalStateException("this must have a scale greater than minBitLength, which is " +
                    minBitLength + ". Invalid scale: " + scale);
        }
        if (minBitLength == 0 && scale == Integer.MAX_VALUE) {
            throw new IllegalStateException("If {@code minBitLength} is 0, {@code scale} cannot be" +
                    " {@code Integer.MAX_VALUE}.");
        }
        return map(BigInteger::negate, fromSupplier(() -> nextFromRangeUp(a.negate())));
    }

    /**
     * Returns a randomly-generated positive {@code BinaryFraction}. The mantissa bit size is chosen from a geometric
     * distribution with mean {@code scale}, and then the mantissa is chosen uniformly from all odd {@code BigInteger}s
     * with that bit size. The absolute value of the exponent is chosen from a geometric distribution with mean
     * {@code secondaryScale}, and its sign is chosen uniformly.
     *
     * <ul>
     *  <li>{@code this} must have a scale of at least 2 and a positive secondary scale.</li>
     *  <li>The result is positive.</li>
     * </ul>
     *
     * @return a positive {@code BinaryFraction}
     */
    public @NotNull BinaryFraction nextPositiveBinaryFraction() {
        return BinaryFraction.of(nextPositiveBigInteger().setBit(0), withScale(secondaryScale).nextIntGeometric());
    }

    /**
     * An {@code Iterable} that generates all positive {@code BinaryFraction}s. The mantissa bit size is chosen from a
     * geometric distribution with mean {@code scale}, and then the mantissa is chosen uniformly from all odd
     * {@code BigInteger}s with that bit size. The absolute value of the exponent is chosen from a geometric
     * distribution with mean {@code secondaryScale}, and its sign is chosen uniformly. Does not support removal.
     *
     * <ul>
     *  <li>{@code this} must have a scale of at least 2 and a positive secondary scale.</li>
     *  <li>The result is an infinite, non-removable {@code Iterable} containing positive {@code BinaryFraction}s.</li>
     * </ul>
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<BinaryFraction> positiveBinaryFractions() {
        if (scale < 2) {
            throw new IllegalStateException("this must have a scale of at least 2. Invalid scale: " + scale);
        }
        if (secondaryScale < 1) {
            throw new IllegalStateException("this must have a positive secondaryScale. Invalid secondaryScale: " +
                    secondaryScale);
        }
        return fromSupplier(this::nextPositiveBinaryFraction);
    }

    /**
     * Returns a randomly-generated negative {@code BinaryFraction}. The mantissa bit size is chosen from a geometric
     * distribution with mean {@code scale}, and then the mantissa is chosen uniformly from all odd {@code BigInteger}s
     * with that bit size. The absolute value of the exponent is chosen from a geometric distribution with mean
     * {@code secondaryScale}, and its sign is chosen uniformly.
     *
     * <ul>
     *  <li>{@code this} must have a scale of at least 2 and a positive secondary scale.</li>
     *  <li>The result is negative.</li>
     * </ul>
     *
     * @return a negative {@code BinaryFraction}
     */
    public @NotNull BinaryFraction nextNegativeBinaryFraction() {
        return nextPositiveBinaryFraction().negate();
    }

    /**
     * An {@code Iterable} that generates all negative {@code BinaryFraction}s. The mantissa bit size is chosen from a
     * geometric distribution with mean {@code scale}, and then the mantissa is chosen uniformly from all odd
     * {@code BigInteger}s with that bit size. The absolute value of the exponent is chosen from a geometric
     * distribution with mean {@code secondaryScale}, and its sign is chosen uniformly. Does not support removal.
     *
     * <ul>
     *  <li>{@code this} must have a scale of at least 2 and a secondary scale of at least 1.</li>
     *  <li>The result is an infinite, non-removable {@code Iterable} containing negative {@code BinaryFraction}s.</li>
     * </ul>
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<BinaryFraction> negativeBinaryFractions() {
        return map(BinaryFraction::negate, positiveBinaryFractions());
    }

    /**
     * Returns a randomly-generated nonzero {@code BinaryFraction}. The mantissa bit size is chosen from a geometric
     * distribution with mean {@code scale}, and then the mantissa is chosen uniformly from all odd {@code BigInteger}s
     * with that bit size. The absolute value of the exponent is chosen from a geometric distribution with mean
     * {@code secondaryScale}, and its sign is chosen uniformly. Finally, the sign of the {@code BinaryFraction} itself
     * is chosen uniformly.
     *
     * <ul>
     *  <li>{@code this} must have a scale of at least 2 and a positive secondary scale.</li>
     *  <li>The result is not zero.</li>
     * </ul>
     *
     * @return a nonzero {@code BinaryFraction}
     */
    public @NotNull BinaryFraction nextNonzeroBinaryFraction() {
        return nextBoolean() ? nextPositiveBinaryFraction() : nextPositiveBinaryFraction().negate();
    }

    /**
     * An {@code Iterable} that generates all nonzero {@code BinaryFraction}s. The mantissa bit size is chosen from a
     * geometric distribution with mean {@code scale}, and then the mantissa is chosen uniformly from all odd
     * {@code BigInteger}s with that bit size. The absolute value of the exponent is chosen from a geometric
     * distribution with mean {@code secondaryScale}, and its sign is chosen uniformly. Finally, the sign of the
     * {@code BinaryFraction} itself is chosen uniformly. Does not support removal.
     *
     * <ul>
     *  <li>{@code this} must have a scale of at least 2 and a positive secondary scale.</li>
     *  <li>The result is an infinite, non-removable {@code Iterable} containing nonzero {@code BinaryFraction}s.</li>
     * </ul>
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<BinaryFraction> nonzeroBinaryFractions() {
        return zipWith((s, bf) -> s ? bf : bf.negate(), booleans(), positiveBinaryFractions());
    }

    /**
     * Returns a randomly-generated {@code BinaryFraction}. The mantissa bit size is chosen from a geometric
     * distribution with mean {@code scale}. If the bit size is zero, the {@code BinaryFraction} is zero; otherwise,
     * the mantissa is chosen uniformly from all odd {@code BigInteger}s with that bit size, thhe absolute value of the
     * exponent is chosen from a geometric distribution with mean {@code secondaryScale}, the exponent's sign is chosen
     * uniformly, and, finally, the sign of the {@code BinaryFraction} itself is chosen uniformly.
     *
     * <ul>
     *  <li>{@code this} must have a positive scale and a positive secondary scale.</li>
     *  <li>The result is not null.</li>
     * </ul>
     *
     * @return a {@code BinaryFraction}
     */
    public @NotNull BinaryFraction nextBinaryFraction() {
        if (scale < 1) {
            throw new IllegalStateException("this must have a positive scale. Invalid scale: " + scale);
        }
        if (secondaryScale < 1) {
            throw new IllegalStateException("this must have a positive secondaryScale. Invalid secondaryScale: " +
                    secondaryScale);
        }
        BigInteger mantissa = nextBigInteger();
        if (mantissa.equals(BigInteger.ZERO)) {
            return BinaryFraction.ZERO;
        } else {
            int exponent = nextIntGeometric(secondaryScale * (scale + 1), scale);
            return BinaryFraction.of(mantissa.setBit(0), exponent);
        }
    }

    /**
     * An {@code Iterable} that generates all {@code BinaryFraction}s. The mantissa bit size is chosen from a geometric
     * distribution with mean {@code scale}. If the bit size is zero, the {@code BinaryFraction} is zero; otherwise,
     * the mantissa is chosen uniformly from all odd {@code BigInteger}s with that bit size, thhe absolute value of the
     * exponent is chosen from a geometric distribution with mean {@code secondaryScale}, the exponent's sign is chosen
     * uniformly, and, finally, the sign of the {@code BinaryFraction} itself is chosen uniformly. Does not support
     * removal.
     *
     * <ul>
     *  <li>{@code this} must have a positive scale and a positive secondary scale.</li>
     *  <li>The result is an infinite, non-removable {@code Iterable} containing {@code BinaryFraction}s.</li>
     * </ul>
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<BinaryFraction> binaryFractions() {
        if (scale < 1) {
            throw new IllegalStateException("this must have a positive scale. Invalid scale: " + scale);
        }
        if (secondaryScale < 1) {
            throw new IllegalStateException("this must have a positive secondaryScale. Invalid secondaryScale: " +
                    secondaryScale);
        }
        return fromSupplier(this::nextBinaryFraction);
    }

    /**
     * Returns a randomly-generated {@code BinaryFraction} greater than or equal to {@code a}. A higher {@code scale}
     * corresponds to a higher mantissa bit size and a higher {@code secondaryScale} corresponds to a higher exponent
     * bit size, but the exact relationship is not simple to describe.
     *
     * <ul>
     *  <li>{@code this} must have a positive scale and a positive secondary scale.</li>
     *  <li>{@code a} cannot be null.</li>
     *  <li>The result is not null.</li>
     * </ul>
     *
     * @return a {@code BinaryFraction} greater than or equal to {@code a}
     */
    public @NotNull BinaryFraction nextFromRangeUp(@NotNull BinaryFraction a) {
        return nextBinaryFraction().abs().add(BinaryFraction.of(a.getMantissa())).shiftLeft(a.getExponent());
    }

    /**
     * An {@code Iterable} that generates all {@code BinaryFraction}s greater than or equal to {@code a}. A higher
     * {@code scale} corresponds to a higher mantissa bit size and a higher {@code secondaryScale} corresponds to a
     * higher exponent bit size, but the exact relationship is not simple to describe. Does not support removal.
     *
     * <ul>
     *  <li>{@code this} must have a positive scale and a positive secondary scale.</li>
     *  <li>{@code a} cannot be null.</li>
     *  <li>The result is an infinite, non-removable {@code Iterable} containing {@code BinaryFraction}s.</li>
     * </ul>
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<BinaryFraction> rangeUp(@NotNull BinaryFraction a) {
        if (scale < 1) {
            throw new IllegalStateException("this must have a positive scale. Invalid scale: " + scale);
        }
        if (secondaryScale < 1) {
            throw new IllegalStateException("this must have a positive secondaryScale. Invalid secondaryScale: " +
                    secondaryScale);
        }
        return fromSupplier(() -> nextFromRangeUp(a));
    }

    /**
     * Returns a randomly-generated {@code BinaryFraction} less than or equal to {@code a}. A higher {@code scale}
     * corresponds to a higher mantissa bit size and a higher {@code secondaryScale} corresponds to a higher exponent
     * bit size, but the exact relationship is not simple to describe.
     *
     * <ul>
     *  <li>{@code this} must have a positive scale and a positive secondary scale.</li>
     *  <li>{@code a} cannot be null.</li>
     *  <li>The result is not null.</li>
     * </ul>
     *
     * @return a {@code BinaryFraction} less than or equal to {@code a}
     */
    public @NotNull BinaryFraction nextFromRangeDown(@NotNull BinaryFraction a) {
        return nextFromRangeUp(a.negate()).negate();
    }

    /**
     * An {@code Iterable} that generates all {@code BinaryFraction}s less than or equal to {@code a}. A higher
     * {@code scale} corresponds to a higher mantissa bit size and a higher {@code secondaryScale} corresponds to a
     * higher exponent bit size, but the exact relationship is not simple to describe. Does not support removal.
     *
     * <ul>
     *  <li>{@code this} must have a positive scale and a positive secondary scale.</li>
     *  <li>{@code a} cannot be null.</li>
     *  <li>The result is an infinite, non-removable {@code Iterable} containing {@code BinaryFraction}s.</li>
     * </ul>
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<BinaryFraction> rangeDown(@NotNull BinaryFraction a) {
        if (scale < 1) {
            throw new IllegalStateException("this must have a positive scale. Invalid scale: " + scale);
        }
        if (secondaryScale < 1) {
            throw new IllegalStateException("this must have a positive secondaryScale. Invalid secondaryScale: " +
                    secondaryScale);
        }
        return map(BinaryFraction::negate, rangeUp(a.negate()));
    }

    /**
     * <p>Returns a {@code BinaryFraction} between {@code a} and {@code b}, inclusive.</p>
     *
     * <p>Every interval with {@code BinaryFraction} bounds may be broken up into equal blocks whose length is a power
     * of 2. Consider the subdivision with the largest possible block size. We can call points that lie on the
     * boundaries between blocks, along with the lower and upper bounds of the interval, <i>division-0</i> points.
     * Let points within the interval that are halfway between division-0 points be called <i>division-1</i> points;
     * points halfway between division-0 or division-1 points <i>division-2</i> points; and so on. The
     * {@code BinaryFraction}s returned by this method have divisions chosen from a geometric distribution with mean
     * {@code scale}. The distribution of {@code BinaryFraction}s is approximately uniform.</p>
     *
     * <ul>
     *  <li>{@code this} must have a positive scale. The scale cannot be {@code Integer.MAX_VALUE}.</li>
     *  <li>{@code a} may be any {@code BinaryFraction}.</li>
     *  <li>{@code b} may be any {@code BinaryFraction}.</li>
     *  <li>{@code a} must be less than or equal to {@code b}.</li>
     *  <li>The result is an infinite, non-removable {@code Iterable} containing {@code BinaryFraction}s.</li>
     * </ul>
     *
     * Length is infinite if a≤b, 0 otherwise
     *
     * @param a the inclusive lower bound of the generated elements
     * @param b the inclusive upper bound of the generated elements
     * @return approximately uniformly-distributed {@code BigInteger}s between {@code a} and {@code b}, inclusive
     */
    public @NotNull BinaryFraction nextFromRange(@NotNull BinaryFraction a, @NotNull BinaryFraction b) {
        if (scale < 1) {
            throw new IllegalStateException("this must have a positive scale. Invalid scale: " + scale);
        }
        if (scale == Integer.MAX_VALUE) {
            throw new IllegalStateException("this cannot have a scale of Integer.MAX_VALUE, or " + scale);
        }
        if (a.equals(b)) return a;
        if (gt(a, b)) {
            throw new IllegalArgumentException("a must be less than or equal to b. a is " + a + " and b is " + b +
                    ".");
        }
        BinaryFraction difference = b.subtract(a);
        int division = nextNaturalIntGeometric();
        if (division == 0) {
            return BinaryFraction.of(
                    nextFromRange(BigInteger.ZERO, difference.getMantissa()),
                    difference.getExponent()
            ).add(a);
        } else {
            BinaryFraction fraction = BinaryFraction.of(
                    nextFromRange(
                            BigInteger.ZERO,
                            BigInteger.ONE.shiftLeft(division - 1).subtract(BigInteger.ONE)
                    ).shiftLeft(1).add(BigInteger.ONE),
                    -division
            );
            return fraction.add(
                    BinaryFraction.of(
                            nextFromRange(BigInteger.ZERO, difference.getMantissa().subtract(BigInteger.ONE))
                    )
            ).shiftLeft(difference.getExponent()).add(a);
        }
    }

    /**
     * <p>An {@code Iterable} that generates {@code BinaryFraction}s between {@code a} and {@code b}, inclusive. If
     * {@code a}{@literal >}{@code b}, an empty {@code Iterable} is returned. Does not support removal.</p>
     *
     * <p>Every interval with {@code BinaryFraction} bounds may be broken up into equal blocks whose length is a power
     * of 2. Consider the subdivision with the largest possible block size. We can call points that lie on the
     * boundaries between blocks, along with the lower and upper bounds of the interval, <i>division-0</i> points.
     * Let points within the interval that are halfway between division-0 points be called <i>division-1</i> points;
     * points halfway between division-0 or division-1 points <i>division-2</i> points; and so on. The
     * {@code BinaryFraction}s returned by this method have divisions chosen from a geometric distribution with mean
     * {@code scale}. The distribution of {@code BinaryFraction}s is approximately uniform.</p>
     *
     * <ul>
     *  <li>{@code this} must have a positive scale. The scale cannot be {@code Integer.MAX_VALUE}.</li>
     *  <li>{@code a} may be any {@code BinaryFraction}.</li>
     *  <li>{@code b} may be any {@code BinaryFraction}.</li>
     *  <li>The result is an infinite, non-removable {@code Iterable} containing {@code BinaryFraction}s.</li>
     * </ul>
     *
     * Length is infinite if a≤b, 0 otherwise
     *
     * @param a the inclusive lower bound of the generated elements
     * @param b the inclusive upper bound of the generated elements
     * @return approximately uniformly-distributed {@code BigInteger}s between {@code a} and {@code b}, inclusive
     */
    @Override
    public @NotNull Iterable<BinaryFraction> range(@NotNull BinaryFraction a, @NotNull BinaryFraction b) {
        if (scale < 1) {
            throw new IllegalStateException("this must have a positive scale. Invalid scale: " + scale);
        }
        if (scale == Integer.MAX_VALUE) {
            throw new IllegalStateException("this cannot have a scale of Integer.MAX_VALUE, or " + scale);
        }
        if (gt(a, b)) return Collections.emptyList();
        return fromSupplier(() -> nextFromRange(a, b));
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
            private Iterator<Integer> specialSelector = integersBounded(SPECIAL_ELEMENT_RATIO).iterator();
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

    public @NotNull <A, B> Iterable<Pair<A, B>> dependentPairs(
            @NotNull Iterable<A> xs,
            @NotNull Function<A, Iterable<B>> f
    ) {
        return () -> new NoRemoveIterator<Pair<A, B>>() {
            private @NotNull Iterator<A> xsi = xs.iterator();
            private @NotNull Map<A, Iterator<B>> map = new HashMap<>();

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public Pair<A, B> next() {
                A x = xsi.next();
                Iterator<B> ys = map.get(x);
                if (ys == null) {
                    ys = f.apply(x).iterator();
                    map.put(x, ys);
                }
                return new Pair<>(x, ys.next());
            }
        };
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
    public
    @NotNull <A, B, C, D> Iterable<Quadruple<A, B, C, D>> quadruples(
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
        if (isEmpty(cs)) return Collections.singletonList("");
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
        if (isEmpty(cs)) return Collections.singletonList("");
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

    @Override
    public @NotNull <T> Iterable<List<T>> permutations(@NotNull List<T> xs) {
        return () -> new NoRemoveIterator<List<T>>() {
            Random random = new Random(0x6af477d9a7e54fcaL);

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public List<T> next() {
                List<T> shuffled = toList(xs);
                Collections.shuffle(shuffled, random);
                return shuffled;
            }
        };
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RandomProvider that = (RandomProvider) o;
        return scale == that.scale && secondaryScale == that.secondaryScale &&
                seed.equals(that.seed) && prng.equals(that.prng);
    }

    @Override
    public int hashCode() {
        int result = seed.hashCode();
        result = 31 * result + prng.hashCode();
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
        return "RandomProvider[@" + getId() + ", " + scale + ", " + secondaryScale + "]";
    }

    /**
     * Ensures that {@code this} is valid. Must return true for any {@code RandomProvider} used outside this class.
     */
    public void validate() {
        prng.validate();
        assertEquals(this, seed.size(), IsaacPRNG.SIZE);
        for (Map.Entry<Pair<Integer, Integer>, RandomProvider> entry : dependents.entrySet()) {
            Pair<Integer, Integer> key = entry.getKey();
            RandomProvider value = entry.getValue();
            assertNotNull(this, key);
            assertNotNull(this, key.a);
            assertNotNull(this, key.b);
            assertNotNull(this, value);
            assertTrue(this, prng == value.prng);
            value.validate();
        }
    }
}
