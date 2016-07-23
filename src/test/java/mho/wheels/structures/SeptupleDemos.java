package mho.wheels.structures;

import mho.wheels.io.Readers;
import mho.wheels.testing.Demos;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.testing.Testing.*;

@SuppressWarnings("UnusedDeclaration")
public class SeptupleDemos extends Demos {
    private static final @NotNull String NULLABLE_INTEGER_SEPTUPLE_CHARS = " (),-0123456789lnu";

    public SeptupleDemos(boolean useRandom) {
        super(useRandom);
    }

    private void demoConstructor() {
        for (Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer> s :
                take(MEDIUM_LIMIT, P.septuples(P.withNull(P.integers())))) {
            System.out.println("new Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer>(" + s.a +
                    ", " + s.b + ", " + s.c + ", " + s.d + ", " + s.e + ", " + s.f + ", " + s.g + ") = " +
                    new Septuple<>(s.a, s.b, s.c, s.d, s.e, s.f, s.g));
        }
    }

    private void demoToList() {
        for (Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer> s :
                take(MEDIUM_LIMIT, P.septuples(P.withNull(P.integers())))) {
            System.out.println("toList" + s + " = " + Septuple.toList(s));
        }
    }

    private void demoFromList() {
        for (List<Integer> xs : take(LIMIT, P.lists(7, P.withNull(P.integers())))) {
            System.out.println("fromList(" + middle(xs.toString()) + ") = " + Septuple.fromList(xs));
        }
    }

    private void demoCompare() {
        Iterable<
                Pair<
                        Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer>,
                        Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer>
                >
        > ss = P.pairs(P.septuples(P.integers()));
        for (Pair<
                Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer>,
                Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer>
        > p : take(LIMIT, ss)) {
            System.out.println("compare(" + p.a + ", " + p.b + ") = " + Septuple.compare(p.a, p.b));
        }
    }

    private void demoEquals_Septuple() {
        Iterable<
                Pair<
                        Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer>,
                        Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer>
                >
        > ps = P.pairs(P.septuples(P.withNull(P.integers())));
        for (Pair<
                Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer>,
                Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer>
        > p : take(LIMIT, ps)) {
            System.out.println(p.a + (p.a.equals(p.b) ? " = " : " ≠ ") + p.b);
        }
    }

    private void demoEquals_null() {
        for (Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer> s :
                take(LIMIT, P.septuples(P.withNull(P.integers())))) {
            //noinspection ObjectEqualsNull
            System.out.println(s + (s.equals(null) ? " = " : " ≠ ") + null);
        }
    }

    private void demoHashCode() {
        for (Septuple<Integer, Integer, Integer, Integer, Integer, Integer, Integer> q :
                take(LIMIT, P.septuples(P.withNull(P.integers())))) {
            System.out.println("hashCode" + q + " = " + q.hashCode());
        }
    }

    private void demoReadStrict() {
        for (String s : take(LIMIT, P.strings())) {
            System.out.println(
                    "readStrict(" + nicePrint(s) + ") = " +
                    Septuple.readStrict(
                            s,
                            Readers.readWithNullsStrict(Readers::readIntegerStrict),
                            Readers.readWithNullsStrict(Readers::readIntegerStrict),
                            Readers.readWithNullsStrict(Readers::readIntegerStrict),
                            Readers.readWithNullsStrict(Readers::readIntegerStrict),
                            Readers.readWithNullsStrict(Readers::readIntegerStrict),
                            Readers.readWithNullsStrict(Readers::readIntegerStrict),
                            Readers.readWithNullsStrict(Readers::readIntegerStrict)
                    )
            );
        }
    }

    private void demoReadStrict_targeted() {
        for (String s : take(LIMIT, P.strings(NULLABLE_INTEGER_SEPTUPLE_CHARS))) {
            System.out.println(
                    "readStrict(" + nicePrint(s) + ") = " +
                    Septuple.readStrict(
                            s,
                            Readers.readWithNullsStrict(Readers::readIntegerStrict),
                            Readers.readWithNullsStrict(Readers::readIntegerStrict),
                            Readers.readWithNullsStrict(Readers::readIntegerStrict),
                            Readers.readWithNullsStrict(Readers::readIntegerStrict),
                            Readers.readWithNullsStrict(Readers::readIntegerStrict),
                            Readers.readWithNullsStrict(Readers::readIntegerStrict),
                            Readers.readWithNullsStrict(Readers::readIntegerStrict)
                    )
            );
        }
    }
}
