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
    private static final double BIG_INTEGER_TERMINATION_THRESHOLD = 0.02;

    private final @NotNull Random generator;

    public RandomProvider() {
        generator = new Random();
    }

    public RandomProvider(@NotNull Random generator) {
        this.generator = generator;
    }

    /**
     * An <tt>Iterator</tt> that generates both <tt>Boolean</tt>s. Does not support removal.
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
     * An <tt>Iterator</tt> that generates all <tt>Ordering</tt>s. Does not support removal.
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
     * An <tt>Iterable</tt> that generates all <tt>RoundingMode</tt>s.
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
     * An <tt>Iterable</tt> that generates all positive <tt>Byte</tt>s. Does not support removal.
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
     * An <tt>Iterable</tt> that generates all positive <tt>Short</tt>s. Does not support removal.
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
     * An <tt>Iterable</tt> that generates all positive <tt>Integer</tt>s. Does not support removal.
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
     * An <tt>Iterable</tt> that generates all positive <tt>Long</tt>s. Does not support removal.
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
     * An <tt>Iterable</tt> that generates all positive <tt>BigInteger</tt>s. Does not support removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<BigInteger> positiveBigIntegers() {
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
                    if (generator.nextDouble() < BIG_INTEGER_TERMINATION_THRESHOLD) {
                        break;
                    } else {
                        bits.add(generator.nextBoolean());
                    }
                }
                return BasicMath.fromBits(bits);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    @NotNull
    @Override
    public Iterable<Byte> negativeBytes() {
        return null;
    }

    @NotNull
    @Override
    public Iterable<Short> negativeShorts() {
        return null;
    }

    @NotNull
    @Override
    public Iterable<Integer> negativeIntegers() {
        return null;
    }

    @NotNull
    @Override
    public Iterable<Long> negativeLongs() {
        return null;
    }

    @NotNull
    @Override
    public Iterable<BigInteger> negativeBigIntegers() {
        return null;
    }

    @NotNull
    @Override
    public Iterable<Byte> naturalBytes() {
        return null;
    }

    @NotNull
    @Override
    public Iterable<Short> naturalShorts() {
        return null;
    }

    @NotNull
    @Override
    public Iterable<Integer> naturalIntegers() {
        return null;
    }

    @NotNull
    @Override
    public Iterable<Long> naturalLongs() {
        return null;
    }

    @NotNull
    @Override
    public Iterable<BigInteger> naturalBigIntegers() {
        return null;
    }

    @NotNull
    @Override
    public Iterable<Byte> bytes() {
        return null;
    }

    @NotNull
    @Override
    public Iterable<Short> shorts() {
        return null;
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

    @NotNull
    @Override
    public Iterable<BigInteger> bigIntegers() {
        return null;
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

    @NotNull
    @Override
    public Iterable<Float> floats() {
        return null;
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

    @NotNull
    @Override
    public Iterable<Double> doubles() {
        return null;
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
}
