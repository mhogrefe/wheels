package mho.haskellesque.iterables;

import mho.haskellesque.ordering.Ordering;

import java.math.RoundingMode;
import java.util.Iterator;

import static mho.haskellesque.iterables.IterableUtils.*;

public final class Random {
    private Random() {}

    public static Iterable<Boolean> booleans(java.util.Random generator) {
        return () -> new Iterator<Boolean>() {
            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public Boolean next() {
                return generator.nextBoolean();
            }
        };
    }

    public static Iterable<Boolean> booleans() {
        return booleans(new java.util.Random());
    }

    public static Iterable<Ordering> orderings(java.util.Random generator) {
        return () -> new Iterator<Ordering>() {
            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public Ordering next() {
                return Ordering.fromInt(generator.nextInt(3) - 1);
            }
        };
    }

    public static Iterable<Ordering> orderings() {
        return orderings(new java.util.Random());
    }

    public static Iterable<RoundingMode> roundingModes(java.util.Random generator) {
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
        };
    }

    public static Iterable<RoundingMode> roundingModes() {
        return roundingModes(new java.util.Random());
    }

    public static Iterable<Byte> positiveBytes(java.util.Random generator) {
        return () -> new Iterator<Byte>() {
            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public Byte next() {
                return (byte) (generator.nextInt(127) + 1);
            }
        };
    }

    public static Iterable<Byte> positiveBytes() {
        return positiveBytes(new java.util.Random());
    }
}
