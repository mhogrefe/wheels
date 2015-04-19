package mho.wheels.iterables;

import mho.wheels.math.Combinatorics;
import mho.wheels.math.MathUtils;
import mho.wheels.ordering.Ordering;
import mho.wheels.structures.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Function;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.ordering.Ordering.fromInt;
import static mho.wheels.ordering.Ordering.lt;
import static org.junit.Assert.assertTrue;

/**
 * <p>A {@code RandomProvider} produces {@code Iterable}s that randomly generate some set of values with a specified
 * distribution. A {@code RandomProvider} is immutable and deterministic. The source of its randomness is a
 * {@code long} seed. It contains two scale parameters which some of the distributions depend on; the exact
 * relationship between the parameters and the distributions is specified in the distribution's documentation.
 *
 * <p>Note that sometimes the documentation will say things like "returns an {@code Iterable} containing all
 * {@code String}s". This cannot strictly be true, since {@link java.util.Random} has a period of 2<sup>48</sup>, and
 * will produce no more than that many unique {@code String}s. So in general, the documentation often pretends that
 * the source of randomness is perfect (but still deterministic).
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
     * A number which determines exactly which values will be deterministically output over the life of {@code this}.
     */
    private long seed;

    /**
     * A parameter that determines the size of some of the generated objects. Cannot be negative.
     */
    private int scale = DEFAULT_SCALE;

    /**
     * Another parameter that determines the size of some of the generated objects. Cannot be negative.
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
        seed = new Random().nextLong();
    }

    /**
     * Constructs a {@code RandomProvider} with a seed generated from the current system time.
     *
     * <ul>
     *  <li>{@code seed} may be any {@code long}.</li>
     *  <li>Any {@code RandomProvider} with default {@code scale} and {@code secondaryScale} may be constructed with
     *  this constructor.</li>
     * </ul>
     */
    public RandomProvider(long seed) {
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
     * Returns {@code this}'s scale parameter.
     *
     * <ul>
     *  <li>The result may be any {@code long}.</li>
     * </ul>
     *
     * @return the scale parameter of {@code this}
     */
    public long getSeed() {
        return seed;
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
     * scale.
     *
     * <ul>
     *  <li>The result is not null.</li>
     * </ul>
     *
     * @return another {@code RandomProvider}
     */
    @Override
    public @NotNull RandomProvider alt() {
        RandomProvider alt = copy();
        alt.seed = new Random(seed).nextLong();
        return alt;
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
            throw new IllegalArgumentException("Scale cannot be negative. Invalid scale: " + scale);
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
            throw new IllegalArgumentException("Secondary scale cannot be negative. Invalid secondary scale: " +
                    secondaryScale);
        }
        RandomProvider copy = copy();
        copy.secondaryScale = secondaryScale;
        return copy;
    }

    /**
     * An {@code Iterable} that generates all {@code Integer}s from a uniform distribution. Does not support removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<Integer> integers() {
        return () -> new NoRemoveIterator<Integer>() {
            private Random generator = new Random(seed);

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public Integer next() {
                return generator.nextInt();
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
            private Random generator = new Random(seed);

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public Long next() {
                return generator.nextLong();
            }
        };
    }

    private @NotNull Iterable<Integer> randomIntsPow2(int bits) {
        int mask = (1 << bits) - 1;
        return map(i -> i & mask, integers());
    }

    private @NotNull Iterable<Long> randomLongsPow2(int bits) {
        long mask = (1L << bits) - 1;
        return map(l -> l & mask, longs());
    }

    private @NotNull Iterable<BigInteger> randomBigIntegersPow2(int bits) {
        return map(MathUtils::fromBits, lists(bits, booleans()));
    }

    private @NotNull Iterable<Integer> randomInts(int n) {
        return filter(
                i -> i < n,
                randomIntsPow2(MathUtils.ceilingLog(BigInteger.valueOf(2), BigInteger.valueOf(n)).intValueExact())
        );
    }

    private @NotNull Iterable<Long> randomLongs(long n) {
        return filter(
                l -> l < n,
                randomLongsPow2(MathUtils.ceilingLog(BigInteger.valueOf(2), BigInteger.valueOf(n)).intValueExact())
        );
    }

    private @NotNull Iterable<BigInteger> randomBigIntegers(@NotNull BigInteger n) {
        return filter(
                i -> lt(i, n),
                randomBigIntegersPow2(MathUtils.ceilingLog(BigInteger.valueOf(2), n).intValueExact())
        );
    }

    //2^7 - a
    @Override
    public @NotNull Iterable<Byte> rangeUp(byte a) {
        return map(i -> (byte) (i + a), randomInts(128 - a));
    }

    //2^15 - a
    @Override
    public @NotNull Iterable<Short> rangeUp(short a) {
        return map(i -> (short) (i + a), randomInts(32768 - a));
    }

    //2^31 - a
    @Override
    public @NotNull Iterable<Integer> rangeUp(int a) {
        return map(l -> (int) (l + a), randomLongs((1L << 31) - a));
    }

    //2^63 - a
    @Override
    public @NotNull Iterable<Long> rangeUp(long a) {
        return map(
                i -> i.add(BigInteger.valueOf(a)).longValueExact(),
                randomBigIntegers(BigInteger.ONE.shiftLeft(63).subtract(BigInteger.valueOf(a)))
        );
    }

    @Override
    public @NotNull Iterable<BigInteger> rangeUp(@NotNull BigInteger a) {
        return map(i -> i.add(a), naturalBigIntegers());
    }

    //2^16 - a
    @Override
    public @NotNull Iterable<Character> rangeUp(char a) {
        return map(i -> (char) (i + a), randomInts(65536 - a));
    }

    @Override
    public @NotNull Iterable<Byte> rangeDown(byte a) {
        return map(i -> (byte) (i - 128), randomInts(a + 129));
    }

    @Override
    public @NotNull Iterable<Short> rangeDown(short a) {
        return map(i -> (short) (i - 32768), randomInts(a + 32769));
    }

    @Override
    public @NotNull Iterable<Integer> rangeDown(int a) {
        return map(l -> (int) (l - (1L << 31)), randomLongs(a + (1L << 31) + 1));
    }

    @Override
    public @NotNull Iterable<Long> rangeDown(long a) {
        return map(
                i -> i.subtract(BigInteger.ONE.shiftLeft(63)).longValueExact(),
                randomBigIntegers(BigInteger.valueOf(a).add(BigInteger.ONE).add(BigInteger.ONE.shiftLeft(63)))
        );
    }

    @Override
    public @NotNull Iterable<BigInteger> rangeDown(@NotNull BigInteger a) {
        return map(i -> i.add(BigInteger.ONE).add(a), negativeBigIntegers());
    }

    @Override
    public @NotNull Iterable<Character> rangeDown(char a) {
        return range('\0', a);
    }

    //b - a + 1
    @Override
    public @NotNull Iterable<Byte> range(byte a, byte b) {
        if (a > b) return new ArrayList<>();
        return map(i -> (byte) (i + a), randomInts((int) b - a + 1));
    }

    //b - a + 1
    @Override
    public @NotNull Iterable<Short> range(short a, short b) {
        if (a > b) return new ArrayList<>();
        return map(i -> (short) (i + a), randomInts((int) b - a + 1));
    }

    //b - a + 1
    @Override
    public @NotNull Iterable<Integer> range(int a, int b) {
        if (a > b) return new ArrayList<>();
        return map(i -> (int) (i + a), randomLongs(b - a + 1));
    }

    //b - a + 1
    public @NotNull Iterable<Long> range(long a, long b) {
        if (a > b) return new ArrayList<>();
        return map(
                i -> i.add(BigInteger.valueOf(a)).longValueExact(),
                randomBigIntegers(BigInteger.valueOf(b).subtract(BigInteger.valueOf(a)).add(BigInteger.ONE))
        );
    }

    //b - a + 1
    @Override
    public @NotNull Iterable<BigInteger> range(@NotNull BigInteger a, @NotNull BigInteger b) {
        if (Ordering.gt(a, b)) return new ArrayList<>();
        return map(i -> i.add(a), randomBigIntegers(b.subtract(a).add(BigInteger.ONE)));
    }

    @Override
    public @NotNull Iterable<Character> range(char a, char b) {
        if (a > b) return new ArrayList<>();
        return map(i -> (char) (i + a), randomInts(b - a + 1));
    }

    @Override
    public @NotNull <T> Iterable<T> uniformSample(@NotNull List<T> xs) {
        if (isEmpty(xs)) return new ArrayList<>();
        return map(xs::get, range(0, xs.size() - 1));
    }

    @Override
    public @NotNull Iterable<Character> uniformSample(@NotNull String s) {
        if (s.isEmpty()) return new ArrayList<>();
        return map(s::charAt, range(0, s.length() - 1));
    }

    /**
     * An {@code Iterator} that generates both {@code Boolean}s from a uniform distribution. Does not support removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<Boolean> booleans() {
        return () -> new Iterator<Boolean>() {
            private Random generator = new Random(seed);

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public Boolean next() {
                return generator.nextBoolean();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    /**
     * An {@code Iterator} that generates all {@code Ordering}s from a uniform distribution. Does not support removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<Ordering> orderings() {
        return () -> new Iterator<Ordering>() {
            private Random generator = new Random(seed);

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public Ordering next() {
                return fromInt(generator.nextInt(3) - 1);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    /**
     * An {@code Iterable} that generates all {@code RoundingMode}s from a uniform distribution. Does not support
     * removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<RoundingMode> roundingModes() {
        Function<Integer, RoundingMode> lookup = i -> {
            switch (i) {
                case 0:  return RoundingMode.UNNECESSARY;
                case 1:  return RoundingMode.UP;
                case 2:  return RoundingMode.DOWN;
                case 3:  return RoundingMode.CEILING;
                case 4:  return RoundingMode.FLOOR;
                case 5:  return RoundingMode.HALF_UP;
                case 6:  return RoundingMode.HALF_DOWN;
                default: return RoundingMode.HALF_EVEN;
            }
        };
        return map(lookup, randomIntsPow2(3));
    }

    public @NotNull <T> Iterable<T> geometricSample(int meanIndex, @NotNull Iterable<T> xs) {
        if (isEmpty(xs)) return new ArrayList<>();
        CachedIterable<T> cxs = new CachedIterable<>(xs);
        return map(
                NullableOptional::get,
                filter(
                        NullableOptional::isPresent,
                        (Iterable<NullableOptional<T>>) map(i -> cxs.get(i), naturalIntegersGeometric(meanIndex))
                )
        );
    }

    /**
     * Returns an {@code int} taken from the geometric distribution with support on the positive integers and a mean
     * of {@code meanValue}.
     *
     * @param generator
     * @param meanValue
     * @return
     */
    private static int nextIntGeometric(@NotNull Random generator, int meanValue) {
        int x = 1;
        while (true) {
            if (generator.nextDouble() < 1.0 / meanValue) return x;
            x++;
        }
    }


    /**
     * An {@code Iterable} that generates all natural {@code Integer}s chosen from a geometric distribution with mean
     * {@code mean}, or all zeros if {@code mean} is 0. Does not support removal.
     *
     * <ul>
     *  <li>{@code mean} cannot be negative.</li>
     *  <li>The result is an infinite pseudorandom sequence of all natural {@code Integer}s, or 0.</li>
     * </ul>
     *
     * Length is infinite
     *
     * @param mean the approximate mean bit size of the {@code Integer}s generated
     */
    public @NotNull Iterable<Integer> naturalIntegersGeometric(int mean) {
        if (mean < 0)
            throw new IllegalArgumentException("mean cannot be negative.");
        if (mean == 0) return repeat(0);
        return () -> new Iterator<Integer>() {
            private Random generator = new Random(seed);

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public Integer next() {
                return nextIntGeometric(generator, mean + 1) - 1;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    /**
     * An {@code Iterable} that generates all positive {@code Integer}s chosen from a geometric distribution with mean
     * {@code mean}, or all ones if {@code mean} is 1. Does not support removal.
     *
     * <ul>
     *  <li>{@code mean} must be positive.</li>
     *  <li>The result is an infinite pseudorandom sequence of all positive {@code Integer}s, or 1.</li>
     * </ul>
     *
     * Length is infinite
     *
     * @param mean the approximate mean size of the {@code Integer}s generated
     */
    public @NotNull Iterable<Integer> positiveIntegersGeometric(int mean) {
        if (mean <= 0)
            throw new IllegalArgumentException("mean must be positive.");
        if (mean == 1) return repeat(1);
        return () -> new Iterator<Integer>() {
            private Random generator = new Random(seed);

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public Integer next() {
                return nextIntGeometric(generator, mean);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    /**
     * An {@code Iterable} that generates all negative {@code Integer}s chosen from a geometric distribution with mean
     * {@code mean}, or all –1s if {@code mean} is –1. Does not support removal.
     *
     * <ul>
     *  <li>{@code mean} must be negative.</li>
     *  <li>The result is an infinite pseudorandom sequence of all negative {@code Integer}s, or –1.</li>
     * </ul>
     *
     * Length is infinite
     *
     * @param mean the approximate absolute mean size of the {@code Integer}s generated
     */
    public @NotNull Iterable<Integer> negativeIntegersGeometric(int mean) {
        return map(i -> -i, positiveIntegersGeometric(-mean));
    }

    /**
     * An {@code Iterable} that generates all nonzero {@code Integer}s (or just 1 and –1, if {@code mean} is 1) whose
     * absolute value is chosen from a geometric distribution with absolute mean {@code mean}, and whose sign is chosen
     * uniformly. Does not support removal.
     *
     * <ul>
     *  <li>{@code mean} cannot be negative.</li>
     *  <li>The result is an infinite pseudorandom sequence of all nonzero {@code Integer}s, or –1 and 1.</li>
     * </ul>
     *
     * Length is infinite
     *
     * @param mean the approximate mean bit size of the {@code Integer}s generated
     */
    public @NotNull Iterable<Integer> nonzeroIntegersGeometric(int mean) {
        return zipWith((i, b) -> b ? i : -i, positiveIntegersGeometric(mean), booleans());
    }

    /**
     * An {@code Iterable} that generates all {@code Integer}s whose absolute value is chosen from a geometric
     * distribution with absolute mean {@code mean}, and whose sign is chosen uniformly. Does not support removal.
     *
     * <ul>
     *  <li>{@code mean} must be greater than 1.</li>
     *  <li>The result is an infinite pseudorandom sequence of all {@code Integer}s.</li>
     * </ul>
     *
     * Length is infinite
     *
     * @param mean the approximate mean bit size of the {@code Integer}s generated
     */
    public @NotNull Iterable<Integer> integersGeometric(int mean) {
        if (mean < 0)
            throw new IllegalArgumentException("mean cannot be negative.");
        if (mean == 0) return repeat(0);
        return () -> new Iterator<Integer>() {
            private Random generator = new Random(seed);
            private final Iterator<Integer> nats = naturalIntegersGeometric(mean).iterator();
            private final Iterator<Integer> negs = alt().negativeIntegersGeometric(-mean).iterator();

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public Integer next() {
                return generator.nextBoolean() ? nats.next() : negs.next();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    /**
     * An {@code Iterable} that generates all positive {@code Byte}s from a uniform distribution. Does not support
     * removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<Byte> positiveBytes() {
        return () -> new Iterator<Byte>() {
            private Random generator = new Random(seed);

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public Byte next() {
                return (byte) (generator.nextInt(Byte.MAX_VALUE) + 1);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    /**
     * An {@code Iterable} that generates all positive {@code Short}s from a uniform distribution. Does not support
     * removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<Short> positiveShorts() {
        return () -> new Iterator<Short>() {
            private Random generator = new Random(seed);

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public Short next() {
                return (short) (generator.nextInt(Short.MAX_VALUE) + 1);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    /**
     * An {@code Iterable} that generates all positive {@code Integer}s from a uniform distribution. Does not support
     * removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<Integer> positiveIntegers() {
        return () -> new Iterator<Integer>() {
            private Random generator = new Random(seed);

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public Integer next() {
                return generator.nextInt(Integer.MAX_VALUE) + 1;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    /**
     * An {@code Iterable} that generates all positive {@code Long}s from a uniform distribution from a uniform
     * distribution. Does not support removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<Long> positiveLongs() {
        return () -> new Iterator<Long>() {
            private Random generator = new Random(seed);

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public Long next() {
                long next;
                do {
                    next = Math.abs(generator.nextLong());
                } while (next < 0);
                return next;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
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
        if (scale <= 2)
            throw new IllegalArgumentException("meanBitSize must be greater than 2.");
        return () -> new Iterator<BigInteger>() {
            private Random generator = new Random(seed);

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public BigInteger next() {
                List<Boolean> bits = new ArrayList<>();
                bits.add(true);
                int bitSize = nextIntGeometric(generator, scale);
                for (int i = 0; i < bitSize - 1; i++) {
                    bits.add(generator.nextBoolean());
                }
                return MathUtils.fromBigEndianBits(bits);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    /**
     * An {@code Iterable} that generates all negative {@code Byte}s from a uniform distribution. Does not support
     * removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<Byte> negativeBytes() {
        return () -> new Iterator<Byte>() {
            private Random generator = new Random(seed);

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public Byte next() {
                return (byte) (-1 - generator.nextInt(Byte.MAX_VALUE + 1));
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    /**
     * An {@code Iterable} that generates all negative {@code Short}s from a uniform distribution. Does not support
     * removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<Short> negativeShorts() {
        return () -> new Iterator<Short>() {
            private Random generator = new Random(seed);

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public Short next() {
                return (short) (-1 - generator.nextInt(Short.MAX_VALUE + 1));
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
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
        if (scale <= 2)
            throw new IllegalArgumentException("meanBitSize must be greater than 2.");
        return () -> new Iterator<BigInteger>() {
            private Random generator = new Random(seed);

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public BigInteger next() {
                List<Boolean> bits = new ArrayList<>();
                int bitSize = nextIntGeometric(generator, scale + 1) - 1;
                if (bitSize != 0) {
                    bits.add(true);
                }
                for (int i = 0; i < bitSize - 1; i++) {
                    bits.add(generator.nextBoolean());
                }
                return MathUtils.fromBigEndianBits(bits);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
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
            private Random generator = new Random(seed);

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
            private Random generator = new Random(seed);

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
            private Random generator = new Random(seed);

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
                pairs(negativeBigIntegers(), integersGeometric(scale))
        );
    }

    @Override
    public @NotNull Iterable<BigDecimal> negativeBigDecimals() {
        return map(
                p -> new BigDecimal(p.a, p.b),
                pairs(negativeBigIntegers(), integersGeometric(scale))
        );
    }

    @Override
    public @NotNull Iterable<BigDecimal> bigDecimals() {
        return map(p -> new BigDecimal(p.a, p.b), pairs(bigIntegers(), integersGeometric(scale)));
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
    public @NotNull <A, B> Iterable<Pair<A, B>> dependentPairs(
            @NotNull Iterable<A> xs,
            @NotNull Function<A, Iterable<B>> f
    ) {
        return Combinatorics.dependentPairs(
                xs,
                x -> geometricSample(scale, f.apply(x)),
                (BigInteger i) -> {
                    List<BigInteger> list = MathUtils.demux(2, i);
                    return new Pair<>(list.get(0), list.get(1));
                }
        );
    }

    @Override
    public @NotNull <A, B> Iterable<Pair<A, B>> dependentPairsLogarithmic(
            @NotNull Iterable<A> xs,
            @NotNull Function<A, Iterable<B>> f
    ) {
        return Combinatorics.dependentPairs(
                xs,
                x -> geometricSample(scale, f.apply(x)),
                MathUtils::logarithmicDemux
        );
    }

    @Override
    public @NotNull <A, B> Iterable<Pair<A, B>> dependentPairsSquareRoot(
            @NotNull Iterable<A> xs,
            @NotNull Function<A, Iterable<B>> f
    ) {
        return Combinatorics.dependentPairs(
                xs,
                x -> geometricSample(scale, f.apply(x)),
                MathUtils::squareRootDemux
        );
    }

    @Override
    public @NotNull <A, B> Iterable<Pair<A, B>> dependentPairsExponential(
            @NotNull Iterable<A> xs,
            @NotNull Function<A, Iterable<B>> f
    ) {
        return Combinatorics.dependentPairs(
                xs,
                x -> geometricSample(scale, f.apply(x)),
                i -> {
                    Pair<BigInteger, BigInteger> p = MathUtils.logarithmicDemux(i);
                    return new Pair<>(p.b, p.a);
                }
        );
    }

    @Override
    public @NotNull <A, B> Iterable<Pair<A, B>> dependentPairsSquare(
            @NotNull Iterable<A> xs,
            @NotNull Function<A, Iterable<B>> f
    ) {
        return Combinatorics.dependentPairs(
                xs,
                x -> geometricSample(scale, f.apply(x)),
                i -> {
                    Pair<BigInteger, BigInteger> p = MathUtils.squareRootDemux(i);
                    return new Pair<>(p.b, p.a);
                }
        );
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
        return transpose(demux(size, xs));
    }

    @Override
    public @NotNull <T> Iterable<List<T>> listsAtLeast(int minSize, @NotNull Iterable<T> xs) {
        if (isEmpty(xs)) return Arrays.asList(new ArrayList<T>());
        return () -> new Iterator<List<T>>() {
            private final Iterator<T> xsi = cycle(xs).iterator();
            private final Iterator<Integer> sizes = naturalIntegersGeometric(scale).iterator();

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
        if (isEmpty(xs)) return Arrays.asList(new ArrayList<T>());
        return () -> new Iterator<List<T>>() {
            private final Iterator<T> xsi = cycle(xs).iterator();
            private final Iterator<Integer> sizes = naturalIntegersGeometric(scale).iterator();

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
            private final Iterator<Integer> sizes = naturalIntegersGeometric(scale).iterator();

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
            private final Iterator<Integer> sizes = naturalIntegersGeometric(scale).iterator();

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
        return scale == provider.scale && secondaryScale == provider.secondaryScale && seed == provider.seed;
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
        int result = scale;
        result = 31 * result + secondaryScale;
        result = 31 * result + (int) (seed ^ (seed >>> 32));
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
        return "RandomProvider[" + seed + ", " + scale + ", " + secondaryScale + "]";
    }

    /**
     * Ensures that {@code this} is valid. Must return true for any {@code RandomProvider} used outside this class.
     */
    public void validate() {
        assertTrue(toString(), scale >= 0);
        assertTrue(toString(), secondaryScale >= 0);
    }
}
