package mho.wheels.iterables;

import mho.wheels.structures.Pair;
import mho.wheels.structures.Triple;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static mho.wheels.testing.Testing.*;
import static org.junit.Assert.*;
import static mho.wheels.iterables.IterableUtils.repeat;
import static mho.wheels.iterables.IterableUtils.take;

@SuppressWarnings("ConstantConditions")
public class RandomProviderProperties {
    private static int LIMIT;
    private static IterableProvider P;

    @Test
    public void testAllProperties() {
        List<Triple<IterableProvider, Integer, String>> configs = new ArrayList<>();
        configs.add(new Triple<>(ExhaustiveProvider.INSTANCE, 10000, "exhaustively"));
        configs.add(new Triple<>(new RandomProvider(0x6af477d9a7e54fcaL), 1000, "randomly"));
        System.out.println("RandomProvider properties");
        for (Triple<IterableProvider, Integer, String> config : configs) {
            P = config.a;
            LIMIT = config.b;
            System.out.println("\ttesting " + config.c);
            propertiesConstructor();
            propertiesConstructor_int();
            propertiesGetScale();
            propertiesGetSecondaryScale();
            propertiesGetSeed();
            propertiesAlt();
            propertiesWithScale();
            propertiesWithSecondaryScale();
        }
        System.out.println("Done");
    }

    private static void propertiesConstructor() {
        System.out.println("\t\ttesting RandomProvider() properties...");

        for (Void v : take(LIMIT, repeat((Void) null))) {
            RandomProvider rp = new RandomProvider();
            rp.validate();
        }
    }

    private static void propertiesConstructor_int() {
        System.out.println("\t\ttesting RandomProvider(int) properties...");

        for (long l : take(LIMIT, P.longs())) {
            RandomProvider rp = new RandomProvider(l);
            rp.validate();
            assertEquals(Long.toString(l), rp.getScale(), 32);
            assertEquals(Long.toString(l), rp.getSecondaryScale(), 8);
        }
    }

    private static void propertiesGetScale() {
        System.out.println("\t\ttesting getScale() properties...");

        for (RandomProvider rp : take(LIMIT, P.randomProviders())) {
            int scale = rp.getScale();
            assertTrue(rp.toString(), scale >= 0);
            assertEquals(rp.toString(), rp.withScale(scale), rp);
        }
    }

    private static void propertiesGetSecondaryScale() {
        System.out.println("\t\ttesting getSecondaryScale() properties...");

        for (RandomProvider rp : take(LIMIT, P.randomProviders())) {
            int secondaryScale = rp.getSecondaryScale();
            assertTrue(rp.toString(), secondaryScale >= 0);
            assertEquals(rp.toString(), rp.withSecondaryScale(secondaryScale), rp);
        }
    }

    private static void propertiesGetSeed() {
        System.out.println("\t\ttesting getSeed() properties...");

        for (RandomProvider rp : take(LIMIT, P.randomProviders())) {
            long seed = rp.getSeed();
            assertEquals(
                    rp.toString(),
                    new RandomProvider(seed).withScale(rp.getScale()).withSecondaryScale(rp.getSecondaryScale()),
                    rp
            );
        }
    }

    private static void propertiesAlt() {
        System.out.println("\t\ttesting alt() properties...");

        for (RandomProvider rp : take(LIMIT, P.randomProviders())) {
            RandomProvider alt = rp.alt();
            alt.validate();
            assertEquals(rp.toString(), alt.getScale(), rp.getScale());
            assertEquals(rp.toString(), alt.getSecondaryScale(), rp.getSecondaryScale());
        }
    }

    private static void propertiesWithScale() {
        System.out.println("\t\ttesting withScale(int) properties...");

        for (Pair<RandomProvider, Integer> p : take(LIMIT, P.pairs(P.randomProviders(), P.naturalIntegers()))) {
            RandomProvider rp = p.a.withScale(p.b);
            rp.validate();
            assertEquals(p.toString(), rp.getScale(), p.b.intValue());
            assertEquals(p.toString(), rp.getSecondaryScale(), p.a.getSecondaryScale());
            assertEquals(p.toString(), rp.getSeed(), p.a.getSeed());
            inverses(x -> x.withScale(p.b), (RandomProvider y) -> y.withScale(p.a.getScale()), p.a);
        }

        for (RandomProvider rp : take(LIMIT, P.randomProviders())) {
            idempotent(x -> x.withScale(rp.getScale()), rp);
        }

        for (Pair<RandomProvider, Integer> p : take(LIMIT, P.pairs(P.randomProviders(), P.negativeIntegers()))) {
            try {
                p.a.withScale(p.b);
                fail(p.toString());
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private static void propertiesWithSecondaryScale() {
        System.out.println("\t\ttesting withSecondaryScale(int) properties...");

        for (Pair<RandomProvider, Integer> p : take(LIMIT, P.pairs(P.randomProviders(), P.naturalIntegers()))) {
            RandomProvider rp = p.a.withSecondaryScale(p.b);
            rp.validate();
            assertEquals(p.toString(), rp.getScale(), p.a.getScale());
            assertEquals(p.toString(), rp.getSecondaryScale(), p.b.intValue());
            assertEquals(p.toString(), rp.getSeed(), p.a.getSeed());
            inverses(
                    x -> x.withSecondaryScale(p.b),
                    (RandomProvider y) -> y.withSecondaryScale(p.a.getSecondaryScale()),
                    p.a
            );
        }

        for (RandomProvider rp : take(LIMIT, P.randomProviders())) {
            idempotent(x -> x.withSecondaryScale(rp.getSecondaryScale()), rp);
        }

        for (Pair<RandomProvider, Integer> p : take(LIMIT, P.pairs(P.randomProviders(), P.negativeIntegers()))) {
            try {
                p.a.withSecondaryScale(p.b);
                fail(p.toString());
            } catch (IllegalArgumentException ignored) {}
        }
    }
}
