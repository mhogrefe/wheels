package mho.wheels.random;

import mho.wheels.structures.Pair;
import mho.wheels.testing.Demos;

import java.util.List;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.iterables.IterableUtils.take;
import static mho.wheels.random.IsaacPRNG.*;
import static mho.wheels.testing.Testing.*;

@SuppressWarnings("UnusedDeclaration")
public class IsaacPRNGDemos extends Demos {
    public IsaacPRNGDemos(boolean useRandom) {
        super(useRandom);
    }

    private void demoConstructor() {
        for (Void v : take(LIMIT, repeat((Void) null))) {
            System.out.println("new IsaacPRNG() = " + new IsaacPRNG());
        }
    }

    private void demoConstructor_List_Integer() {
        for (List<Integer> is : take(SMALL_LIMIT, P.lists(IsaacPRNG.SIZE, P.integers()))) {
            System.out.println("new IsaacPRNG(" + middle(is.toString()) + ") = " + new IsaacPRNG(is));
        }
    }

    private void demoEquals_IsaacPRNG() {
        for (Pair<List<Integer>, List<Integer>> p : take(LIMIT, P.pairs(P.lists(SIZE, P.integers())))) {
            IsaacPRNG a = new IsaacPRNG(p.a);
            IsaacPRNG b = new IsaacPRNG(p.b);
            System.out.println(a + (a.equals(b) ? " = " : " ≠ ") + b);
        }
    }

    private void demoEquals_null() {
        for (List<Integer> xs : take(LIMIT, P.lists(SIZE, P.integers()))) {
            IsaacPRNG prng = new IsaacPRNG(xs);
            //noinspection ObjectEqualsNull
            System.out.println(prng + (prng.equals(null) ? " = " : " ≠ ") + null);
        }
    }

    private void demoHashCode() {
        for (List<Integer> xs : take(LIMIT, P.lists(SIZE, P.integers()))) {
            IsaacPRNG prng = new IsaacPRNG(xs);
            System.out.println("hashCode(" + prng + ") = " + prng.hashCode());
        }
    }

    private void demoToString() {
        for (List<Integer> xs : take(LIMIT, P.lists(SIZE, P.integers()))) {
            System.out.println(new IsaacPRNG(xs));
        }
    }
}
