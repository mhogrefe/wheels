package mho.haskellesque.iterables;

import mho.haskellesque.math.BasicMath;
import mho.haskellesque.ordering.Ordering;
import mho.haskellesque.structures.*;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import static mho.haskellesque.iterables.IterableUtils.*;

/**
 * <tt>Iterable</tt>s that randomly generate all (or some important subset) of a type's values.
 */
public final class RandomProvider implements IterableProvider {
    private static final int BIG_INTEGER_MEAN_BIT_SIZE = 64;

    private final @NotNull Random generator;

    public RandomProvider() {
        generator = new Random();
    }

    public RandomProvider(@NotNull Random generator) {
        this.generator = generator;
    }

    /**
     * An <tt>Iterator</tt> that generates both <tt>Boolean</tt>s from a uniform distribution. Does not support
     * removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<Boolean> booleans() {
        return () -> new Iterator<Boolean>() {
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
     * An <tt>Iterator</tt> that generates all <tt>Ordering</tt>s from a uniform distribution. Does not support
     * removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<Ordering> orderings() {
        return () -> new Iterator<Ordering>() {
            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public Ordering next() {
                return Ordering.fromInt(generator.nextInt(3) - 1);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    /**
     * An <tt>Iterable</tt> that generates all <tt>RoundingMode</tt>s from a uniform distribution. Does not support
     * removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<RoundingMode> roundingModes() {
        return () -> new Iterator<RoundingMode>() {
            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public RoundingMode next() {
                int index = generator.nextInt(8);
                switch (index) {
                    case 0:  return RoundingMode.UNNECESSARY;
                    case 1:  return RoundingMode.UP;
                    case 2:  return RoundingMode.DOWN;
                    case 3:  return RoundingMode.CEILING;
                    case 4:  return RoundingMode.FLOOR;
                    case 5:  return RoundingMode.HALF_UP;
                    case 6:  return RoundingMode.HALF_DOWN;
                    default: return RoundingMode.HALF_EVEN;
                }
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    /**
     * An <tt>Iterable</tt> that generates all positive <tt>Byte</tt>s from a uniform distribution. Does not support
     * removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<Byte> positiveBytes() {
        return () -> new Iterator<Byte>() {
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
     * An <tt>Iterable</tt> that generates all positive <tt>Short</tt>s from a uniform distribution. Does not support
     * removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<Short> positiveShorts() {
        return () -> new Iterator<Short>() {
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
     * An <tt>Iterable</tt> that generates all positive <tt>Integer</tt>s from a uniform distribution. Does not support
     * removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<Integer> positiveIntegers() {
        return () -> new Iterator<Integer>() {
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
     * An <tt>Iterable</tt> that generates all positive <tt>Long</tt>s from a uniform distribution from a uniform
     * distribution. Does not support removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<Long> positiveLongs() {
        return () -> new Iterator<Long>() {
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
     * An <tt>Iterable</tt> that generates all positive <tt>BigInteger</tt>s. The bit size is chosen from a geometric
     * distribution with mean approximately <tt>meanBitSize</tt> (The ratio between the actual mean and
     * <tt>meanBitSize</tt> decreases as <tt>meanBitSize</tt> increases). Does not support removal.
     *
     * <ul>
     *  <li><tt>meanBitSize</tt> must be greater than 2.</li>
     *  <li>The is an infinite pseudorandom sequence of all <tt>BigIntegers</tt></li>
     * </ul>
     *
     * Length is infinite
     */
    public @NotNull Iterable<BigInteger> positiveBigIntegers(int meanBitSize) {
        if (meanBitSize <= 2)
            throw new IllegalArgumentException("meanBitSize must be greater than 2.");
        return () -> new Iterator<BigInteger>() {
            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public BigInteger next() {
                List<Boolean> bits = new ArrayList<>();
                bits.add(true);
                while (true) {
                    if (generator.nextDouble() < 1.0 / (meanBitSize - 1)) {
                        break;
                    } else {
                        bits.add(generator.nextBoolean());
                    }
                }
                return BasicMath.fromBigEndianBits(bits);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    /**
     * An <tt>Iterable</tt> that generates all positive <tt>BigInteger</tt>s. The bit size is chosen from a geometric
     * distribution with mean approximately 64. Does not support removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<BigInteger> positiveBigIntegers() {
        return positiveBigIntegers(BIG_INTEGER_MEAN_BIT_SIZE);
    }

    @Override
    public @NotNull Iterable<Byte> negativeBytes() {
        return () -> new Iterator<Byte>() {
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

    @Override
    public @NotNull Iterable<Short> negativeShorts() {
        return () -> new Iterator<Short>() {
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

    @Override
    public @NotNull Iterable<Integer> negativeIntegers() {
        return filter(i -> i < 0, integers());
    }

    @Override
    public @NotNull Iterable<Long> negativeLongs() {
        return filter(l -> l < 0, longs());
    }

    public @NotNull Iterable<BigInteger> negativeBigIntegers(int meanBitSize) {
        return map(BigInteger::negate, positiveBigIntegers(meanBitSize));
    }

    @Override
    public @NotNull Iterable<BigInteger> negativeBigIntegers() {
        return map(BigInteger::negate, positiveBigIntegers());
    }

    @Override
    public @NotNull Iterable<Byte> naturalBytes() {
        return () -> new Iterator<Byte>() {
            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public Byte next() {
                return (byte) (generator.nextInt(Byte.MAX_VALUE + 1));
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    @Override
    public @NotNull Iterable<Short> naturalShorts() {
        return () -> new Iterator<Short>() {
            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public Short next() {
                return (short) (generator.nextInt(Short.MAX_VALUE + 1));
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    @Override
    public @NotNull Iterable<Integer> naturalIntegers() {
        return filter(i -> i >= 0, integers());
    }

    @Override
    public @NotNull Iterable<Long> naturalLongs() {
        return filter(l -> l >= 0, longs());
    }

    public @NotNull Iterable<BigInteger> naturalBigIntegers(int meanBitSize) {
        return () -> new Iterator<BigInteger>() {
            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public BigInteger next() {
                List<Boolean> bits = new ArrayList<>();
                bits.add(true);
                while (true) {
                    if (generator.nextDouble() < 1.0 / (meanBitSize - 1)) {
                        break;
                    } else {
                        bits.add(generator.nextBoolean());
                    }
                }
                return BasicMath.fromBigEndianBits(bits);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    @Override
    public @NotNull Iterable<BigInteger> naturalBigIntegers() {
        return naturalBigIntegers(BIG_INTEGER_MEAN_BIT_SIZE);
    }

    @Override
    public @NotNull Iterable<Byte> bytes() {
        return () -> new Iterator<Byte>() {
            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public Byte next() {
                return (byte) (generator.nextInt(1 << 8) - (1 << 7));
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    @Override
    public @NotNull Iterable<Short> shorts() {
        return () -> new Iterator<Short>() {
            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public Short next() {
                return (short) (generator.nextInt(1 << 16) - (1 << 15));
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    @Override
    public @NotNull Iterable<Integer> integers() {
        return () -> new Iterator<Integer>() {
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

    @Override
    public @NotNull Iterable<Long> longs() {
        return () -> new Iterator<Long>() {
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

    public @NotNull Iterable<BigInteger> bigIntegers(int meanBitSize) {
        return () -> new Iterator<BigInteger>() {
            private Iterator<BigInteger> it = naturalBigIntegers(meanBitSize).iterator();

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public BigInteger next() {
                BigInteger nbi = it.next();
                if (generator.nextBoolean()) {
                    nbi = nbi.negate();
                }
                return nbi;
            }
        };
    }

    @Override
    public @NotNull Iterable<BigInteger> bigIntegers() {
        return bigIntegers(BIG_INTEGER_MEAN_BIT_SIZE);
    }

    @NotNull
    @Override
    public Iterable<Character> asciiCharacters() {
        return null;
    }

    @NotNull
    @Override
    public Iterable<Character> characters() {
        return null;
    }

    @NotNull
    @Override
    public Iterable<Float> positiveOrdinaryFloats() {
        return null;
    }

    @NotNull
    @Override
    public Iterable<Float> negativeOrdinaryFloats() {
        return null;
    }

    @NotNull
    @Override
    public Iterable<Float> ordinaryFloats() {
        return null;
    }

    @Override
    public @NotNull Iterable<Float> floats() {
        return () -> new Iterator<Float>() {
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
        };
    }

    @NotNull
    @Override
    public Iterable<Double> positiveOrdinaryDoubles() {
        return null;
    }

    @NotNull
    @Override
    public Iterable<Double> negativeOrdinaryDoubles() {
        return null;
    }

    @NotNull
    @Override
    public Iterable<Double> ordinaryDoubles() {
        return null;
    }

    @Override
    public @NotNull Iterable<Double> doubles() {
        return () -> new Iterator<Double>() {
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
        };
    }

    @NotNull
    @Override
    public <T> Iterable<Pair<T, T>> pairsLogarithmicOrder(@NotNull Iterable<T> xs) {
        return null;
    }

    @NotNull
    @Override
    public <A, B> Iterable<Pair<A, B>> pairsLogarithmicOrder(@NotNull Iterable<A> as, @NotNull Iterable<B> bs) {
        return null;
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
}
