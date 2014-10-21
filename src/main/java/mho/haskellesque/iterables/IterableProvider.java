package mho.haskellesque.iterables;

import mho.haskellesque.ordering.Ordering;

import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Random;

public class IterableProvider {
    private enum IterableType {
        EXHAUSTIVE, RANDOM
    }

    private IterableType iterableType;

    private java.util.Random generator;

    private IterableProvider() {}

    public static IterableProvider exhaustive() {
        IterableProvider iterableProvider = new IterableProvider();
        iterableProvider.iterableType = IterableType.EXHAUSTIVE;
        return iterableProvider;
    }

    public static IterableProvider random() {
        IterableProvider iterableProvider = new IterableProvider();
        iterableProvider.iterableType = IterableType.RANDOM;
        iterableProvider.generator = new Random();
        return iterableProvider;
    }

    public static IterableProvider random(Random generator) {
        IterableProvider iterableProvider = new IterableProvider();
        iterableProvider.iterableType = IterableType.RANDOM;
        iterableProvider.generator = generator;
        return iterableProvider;
    }

    public Iterable<Boolean> booleans() {
        if (iterableType == IterableType.EXHAUSTIVE) {
            return Exhaustive.BOOLEANS;
        } else {
            return mho.haskellesque.iterables.Random.booleans(generator);
        }
    }

    public Iterable<Ordering> orderings() {
        if (iterableType == IterableType.EXHAUSTIVE) {
            return Exhaustive.ORDERINGS;
        } else {
            return mho.haskellesque.iterables.Random.orderings(generator);
        }
    }

    public Iterable<RoundingMode> roundingModes() {
        if (iterableType == IterableType.EXHAUSTIVE) {
            return Exhaustive.ROUNDING_MODES;
        } else {
            return mho.haskellesque.iterables.Random.roundingModes(generator);
        }
    }

    public Iterable<Byte> positiveBytes() {
        if (iterableType == IterableType.EXHAUSTIVE) {
            return Exhaustive.POSITIVE_BYTES;
        } else {
            return mho.haskellesque.iterables.Random.positiveBytes(generator);
        }
    }

    public Iterable<Short> positiveShorts() {
        if (iterableType == IterableType.EXHAUSTIVE) {
            return Exhaustive.POSITIVE_SHORTS;
        } else {
            return mho.haskellesque.iterables.Random.positiveShorts(generator);
        }
    }

    public Iterable<Integer> positiveIntegers() {
        if (iterableType == IterableType.EXHAUSTIVE) {
            return Exhaustive.POSITIVE_INTEGERS;
        } else {
            return mho.haskellesque.iterables.Random.positiveIntegers(generator);
        }
    }

    public Iterable<Long> positiveLongs() {
        if (iterableType == IterableType.EXHAUSTIVE) {
            return Exhaustive.POSITIVE_LONGS;
        } else {
            return mho.haskellesque.iterables.Random.positiveLongs(generator);
        }
    }

    public Iterable<BigInteger> positiveBigIntegers() {
        return Exhaustive.POSITIVE_BIG_INTEGERS; //todo
    }

    public Iterable<Integer> integers() {
        return Exhaustive.INTEGERS; //todo
    }

    public Iterable<BigInteger> bigIntegers() {
        return Exhaustive.BIG_INTEGERS; //todo
    }

    public Iterable<Float> floats() {
        return Exhaustive.FLOATS; //todo
    }

    public Iterable<Double> doubles() {
        return Exhaustive.DOUBLES; //todo
    }
}
