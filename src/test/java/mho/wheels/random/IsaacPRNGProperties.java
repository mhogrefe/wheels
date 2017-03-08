package mho.wheels.random;

import mho.wheels.structures.Pair;
import mho.wheels.testing.TestProperties;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.random.IsaacPRNG.SIZE;
import static mho.wheels.testing.Testing.*;

public class IsaacPRNGProperties extends TestProperties {
        private static final @NotNull String ISAAC_PRNG_CHARS = "-0123456789@GINPR[]acs";

    public IsaacPRNGProperties() {
        super("IsaacPRNG");
    }

    @Override
    protected void testBothModes() {
        propertiesConstructor();
        propertiesConstructor_List_Integer();
        propertiesSetSeed();
        propertiesCopy();
        propertiesGetId();
        propertiesNextInt();
        propertiesEquals();
        propertiesHashCode();
        propertiesToString();
    }

    private void propertiesConstructor() {
        initialize("new IsaacPRNG()");
        //noinspection unused
        for (Void v : take(LIMIT, repeat((Void) null))) {
            IsaacPRNG prng = new IsaacPRNG();
            prng.validate();
        }
    }

    private void propertiesConstructor_List_Integer() {
        initialize("new IsaacPRNG(List<Integer>)");
        for (List<Integer> is : take(LIMIT, P.lists(SIZE, P.integers()))) {
            IsaacPRNG prng = new IsaacPRNG(is);
            prng.validate();
        }
    }

    private void propertiesSetSeed() {
        initialize("setSeed(List<Integer>)");
        for (Pair<List<Integer>, List<Integer>> p : take(LIMIT, P.pairs(P.lists(SIZE, P.integers())))) {
            IsaacPRNG prng = new IsaacPRNG(p.a);
            prng.setSeed(p.b);
            prng.validate();
        }

        for (Pair<List<Integer>, List<Integer>> p : take(LIMIT, P.distinctPairs(P.lists(SIZE, P.integers())))) {
            IsaacPRNG prng = new IsaacPRNG(p.a);
            IsaacPRNG oldPrng = prng.copy();
            prng.setSeed(p.b);
            assertNotEquals(p, prng, oldPrng);
            prng.setSeed(p.a);
            assertEquals(p, prng, oldPrng);
        }
    }

    private void propertiesCopy() {
        initialize("copy()");
        for (List<Integer> is : take(SMALL_LIMIT, P.lists(SIZE, P.integers()))) {
            IsaacPRNG prng = new IsaacPRNG(is);
            IsaacPRNG copy = prng.copy();
            copy.validate();
            assertEquals(is, prng, copy);
        }
    }

    private void propertiesGetId() {
        initialize("getId()");
        for (List<Integer> is : take(SMALL_LIMIT, P.lists(SIZE, P.integers()))) {
            IsaacPRNG prng = new IsaacPRNG(is);
            assertEquals(is, prng.getId(), prng.copy().getId());
        }
    }

    private void propertiesNextInt() {
        initialize("nextInt()");
        for (List<Integer> is : take(SMALL_LIMIT, P.lists(SIZE, P.integers()))) {
            IsaacPRNG prng = new IsaacPRNG(is);
            IsaacPRNG copy = prng.copy();
            assertEquals(is, prng.nextInt(), copy.nextInt());
        }
    }

    private void propertiesEquals() {
        initialize("equals(Object)");
        propertiesEqualsHelper(LIMIT, P, p -> map(IsaacPRNG::new, p.lists(SIZE, p.integers())));
    }

    private void propertiesHashCode() {
        initialize("hashCode()");
        propertiesHashCodeHelper(LIMIT, P, p -> map(IsaacPRNG::new, p.lists(SIZE, p.integers())));
    }

    private void propertiesToString() {
        initialize("toString()");
        for (List<Integer> is : take(LIMIT, P.lists(SIZE, P.integers()))) {
            String s = new IsaacPRNG(is).toString();
            assertTrue(is, isSubsetOf(s, ISAAC_PRNG_CHARS));
        }
    }
}
