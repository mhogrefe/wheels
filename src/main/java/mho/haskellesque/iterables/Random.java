package mho.haskellesque.iterables;

import java.util.Iterator;

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
}
