package mho.wheels.structures;

import mho.wheels.testing.Demos;

import java.util.Collections;
import java.util.Optional;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.structures.NullableOptional.empty;
import static mho.wheels.structures.NullableOptional.fromOptional;
import static mho.wheels.structures.NullableOptional.of;

@SuppressWarnings("UnusedDeclaration")
public class NullableOptionalDemos extends Demos {
    public NullableOptionalDemos(boolean useRandom) {
        super(useRandom);
    }

    private void demoOf_Integer() {
        for (Integer i : take(LIMIT, P.withNull(P.integers()))) {
            System.out.println("of(" + i + ") = " + of(i));
        }
    }

    private void demoIsPresent_Integer() {
        for (NullableOptional<Integer> noi : take(LIMIT, P.nullableOptionals(P.withNull(P.integers())))) {
            System.out.println(noi + " is" + (noi.isPresent() ? "" : " not") + " present");
        }
    }

    private void demoGet_Integer() {
        for (NullableOptional<Integer> noi : take(LIMIT, P.nonEmptyNullableOptionals(P.withNull(P.integers())))) {
            System.out.println("get(" + noi + ") = " + noi.get());
        }
    }

    private void demoFromOptional_Integer() {
        for (Optional<Integer> oi : take(LIMIT, P.optionals(P.integers()))) {
            System.out.println("fromOptional(" + oi + ") = " + fromOptional(oi));
        }
    }

    private void demoOrElse() {
        Iterable<Pair<NullableOptional<Integer>, Integer>> ps = P.pairs(
                P.nullableOptionals(P.integers()),
                P.integers()
        );
        for (Pair<NullableOptional<Integer>, Integer> p : take(LIMIT, ps)) {
            System.out.println("orElse(" + p.a + ", " + p.b + ") = " + p.a.orElse(p.b));
        }
    }

    private void demoMap() {
        Iterable<Pair<NullableOptional<Integer>, FiniteDomainFunction<Integer, Integer>>> ps = P.withElement(
                new Pair<>(empty(), new FiniteDomainFunction<>(Collections.emptyMap())),
                map(
                        p -> new Pair<>(of(p.a), new FiniteDomainFunction<>(Collections.singletonList(p))),
                        P.pairs(P.integers())
                )
        );
        for (Pair<NullableOptional<Integer>, FiniteDomainFunction<Integer, Integer>> p : take(LIMIT, ps)) {
            System.out.println("map(" + p.a + ", " + p.b + ") = " + p.a.map(p.b));
        }
    }

    private void demoEquals_NullableOptional_Integer() {
        Iterable<Pair<NullableOptional<Integer>, NullableOptional<Integer>>> nois = P.pairs(
                P.nullableOptionals(P.withNull(P.integers()))
        );
        for (Pair<NullableOptional<Integer>, NullableOptional<Integer>> p : take(LIMIT, nois)) {
            System.out.println(p.a + (p.a.equals(p.b) ? " = " : " ≠ ") + p.b);
        }
    }

    private void demoEquals_null() {
        for (NullableOptional<Integer> noi : take(LIMIT, P.nullableOptionals(P.withNull(P.integers())))) {
            //noinspection ObjectEqualsNull
            System.out.println(noi + (noi.equals(null) ? " = " : " ≠ ") + null);
        }
    }

    private void demoHashCode_Integer() {
        for (NullableOptional<Integer> noi : take(LIMIT, P.nullableOptionals(P.withNull(P.integers())))) {
            System.out.println("hashCode(" + noi + ") = " + noi.hashCode());
        }
    }

    private void demoToString_Integer() {
        for (NullableOptional<Integer> noi : take(LIMIT, P.nullableOptionals(P.withNull(P.integers())))) {
            System.out.println(noi);
        }
    }
}
