package mho.wheels.math;

import mho.wheels.iterables.ExhaustiveProvider;
import mho.wheels.iterables.IterableProvider;
import mho.wheels.iterables.IterableUtils;
import mho.wheels.iterables.RandomProvider;
import mho.wheels.ordering.Ordering;
import mho.wheels.structures.Pair;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.List;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.math.BinaryFraction.*;
import static mho.wheels.testing.Testing.nicePrint;

@SuppressWarnings("UnusedDeclaration")
public strictfp class BinaryFractionDemos {
    private static final boolean USE_RANDOM = false;
    private static final @NotNull ExhaustiveProvider EP = ExhaustiveProvider.INSTANCE;
    private static final @NotNull String BINARY_FRACTION_CHARS = " -0123456789<>";
    private static int LIMIT;
    private static final int SMALL_LIMIT = 1000;
    private static final int TINY_LIMIT = 20;
    private static IterableProvider P;

    private static void initialize() {
        if (USE_RANDOM) {
            P = RandomProvider.example();
            LIMIT = 1000;
        } else {
            P = ExhaustiveProvider.INSTANCE;
            LIMIT = 10000;
        }
    }

    private static void demoGetMantissa() {
        initialize();
        for (BinaryFraction bf : take(LIMIT, P.binaryFractions())) {
            System.out.println("getMantissa(" + bf + ") = " + bf.getMantissa());
        }
    }

    private static void demoGetExponent() {
        initialize();
        for (BinaryFraction bf : take(LIMIT, P.binaryFractions())) {
            System.out.println("getExponent(" + bf + ") = " + bf.getExponent());
        }
    }

    private static void demoOf_BigInteger_int() {
        initialize();
        Iterable<Pair<BigInteger, Integer>> ps = filterInfinite(
                p -> (long) p.b + p.a.getLowestSetBit() < Integer.MAX_VALUE,
                P.pairs(P.bigIntegers(), P.integers())
        );
        for (Pair<BigInteger, Integer> p : take(LIMIT, ps)) {
            System.out.println("of(" + p.a + ", " + p.b + ") = " + of(p.a, p.b));
        }
    }

    private static void demoOf_BigInteger() {
        initialize();
        for (BigInteger i : take(LIMIT, P.bigIntegers())) {
            System.out.println("of(" + i + ") = " + of(i));
        }
    }

    private static void demoOf_int() {
        initialize();
        for (int i : take(LIMIT, P.integers())) {
            System.out.println("of(" + i + ") = " + of(i));
        }
    }

    private static void demoOf_float() {
        initialize();
        for (float f : take(LIMIT, P.floats())) {
            System.out.println("of(" + f + ") = " + of(f));
        }
    }

    private static void demoOf_double() {
        initialize();
        for (double d : take(LIMIT, P.doubles())) {
            System.out.println("of(" + d + ") = " + of(d));
        }
    }

    private static void demoBigDecimalValue() {
        initialize();
        for (BinaryFraction bf : take(LIMIT, P.binaryFractions())) {
            System.out.println("bigDecimalValue(" + bf + ") = " + bf.bigDecimalValue());
        }
    }

    private static void demoFloatRange() {
        initialize();
        for (BinaryFraction bf : take(LIMIT, P.binaryFractions())) {
            System.out.println("floatRange(" + bf + ") = " + bf.floatRange());
        }
    }

    private static void demoDoubleRange() {
        initialize();
        for (BinaryFraction bf : take(LIMIT, P.binaryFractions())) {
            System.out.println("doubleRange(" + bf + ") = " + bf.doubleRange());
        }
    }

    private static void demoBigIntegerValueExact() {
        initialize();
        for (BinaryFraction bf : take(LIMIT, map(BinaryFraction::of, P.bigIntegers()))) {
            System.out.println("bigIntegerValueExact(" + bf + ") = " + bf.bigIntegerValueExact());
        }
    }

    private static void demoIsInteger() {
        initialize();
        for (BinaryFraction bf : take(LIMIT, P.binaryFractions())) {
            System.out.println(bf + " is " + (bf.isInteger() ? "" : "not ") + "an integer");
        }
    }

    private static void demoAdd() {
        initialize();
        Iterable<Pair<BinaryFraction, BinaryFraction>> ps = filterInfinite(
                p -> {
                    try {
                        p.a.add(p.b);
                        return true;
                    } catch (ArithmeticException e) {
                        return false;
                    }
                },
                P.pairs(P.binaryFractions())
        );
        for (Pair<BinaryFraction, BinaryFraction> p : take(LIMIT, ps)) {
            System.out.println("(" + p.a + ") + (" + p.b + ") = " + p.a.add(p.b));
        }
    }

    private static void demoNegate() {
        initialize();
        for (BinaryFraction bf : take(LIMIT, P.binaryFractions())) {
            System.out.println("-(" + bf + ") = " + bf.negate());
        }
    }

    private static void demoAbs() {
        initialize();
        for (BinaryFraction bf : take(LIMIT, P.binaryFractions())) {
            System.out.println("|" + bf + "| = " + bf.abs());
        }
    }

    private static void demoSignum() {
        initialize();
        for (BinaryFraction bf : take(LIMIT, P.binaryFractions())) {
            System.out.println("sgn(" + bf + ") = " + bf.signum());
        }
    }

    private static void demoSubtract() {
        initialize();
        Iterable<Pair<BinaryFraction, BinaryFraction>> ps = filterInfinite(
                p -> {
                    try {
                        p.a.subtract(p.b);
                        return true;
                    } catch (ArithmeticException e) {
                        return false;
                    }
                },
                P.pairs(P.binaryFractions())
        );
        for (Pair<BinaryFraction, BinaryFraction> p : take(LIMIT, ps)) {
            System.out.println("(" + p.a + ") - (" + p.b + ") = " + p.a.subtract(p.b));
        }
    }

    private static void demoMultiply() {
        initialize();
        Iterable<Pair<BinaryFraction, BinaryFraction>> ps = filterInfinite(
                p -> {
                    long productExponent = (long) p.a.getExponent() + p.b.getExponent();
                    return productExponent <= Integer.MAX_VALUE && productExponent >= Integer.MIN_VALUE;
                },
                P.pairs(P.binaryFractions())
        );
        for (Pair<BinaryFraction, BinaryFraction> p : take(LIMIT, ps)) {
            System.out.println("(" + p.a + ") * (" + p.b + ") = " + p.a.multiply(p.b));
        }
    }

    private static void demoShiftLeft() {
        initialize();
        Iterable<Pair<BinaryFraction, Integer>> ps = filterInfinite(
                p -> {
                    long shiftedExponent = (long) p.a.getExponent() + p.b;
                    return shiftedExponent <= Integer.MAX_VALUE && shiftedExponent >= Integer.MIN_VALUE;
                },
                P.pairs(P.binaryFractions(), P.integersGeometric())
        );
        for (Pair<BinaryFraction, Integer> p : take(LIMIT, ps)) {
            System.out.println("(" + p.a + ") << " + p.b + " = " + p.a.shiftLeft(p.b));
        }
    }

    private static void demoShiftRight() {
        initialize();
        Iterable<Pair<BinaryFraction, Integer>> ps = filterInfinite(
                p -> {
                    long shiftedExponent = (long) p.a.getExponent() - p.b;
                    return shiftedExponent <= Integer.MAX_VALUE && shiftedExponent >= Integer.MIN_VALUE;
                },
                P.pairs(P.binaryFractions(), P.integersGeometric())
        );
        for (Pair<BinaryFraction, Integer> p : take(LIMIT, ps)) {
            System.out.println("(" + p.a + ") >> " + p.b + " = " + p.a.shiftRight(p.b));
        }
    }

    private static void demoSum() {
        initialize();
        for (List<BinaryFraction> rs : take(LIMIT, P.lists(P.binaryFractions()))) {
            String listString = tail(init(rs.toString()));
            System.out.println("Σ(" + listString + ") = " + sum(rs));
        }
    }

    private static void demoProduct() {
        initialize();
        for (List<BinaryFraction> rs : take(LIMIT, P.lists(P.binaryFractions()))) {
            String listString = tail(init(rs.toString()));
            System.out.println("Π(" + listString + ") = " + product(rs));
        }
    }

    private static void demoDelta_finite_Iterable() {
        initialize();
        for (List<BinaryFraction> rs : take(SMALL_LIMIT, P.withScale(16).listsAtLeast(1, P.binaryFractions()))) {
            String listString = tail(init(rs.toString()));
            System.out.println("Δ(" + listString + ") = " + IterableUtils.toString(delta(rs)));
        }
    }

    private static void demoDelta_infinite_Iterable() {
        initialize();
        for (Iterable<BinaryFraction> bfs : take(SMALL_LIMIT, P.prefixPermutations(EP.binaryFractions()))) {
            String listString = tail(init(IterableUtils.toString(TINY_LIMIT, bfs)));
            System.out.println("Δ(" + listString + ") = " + IterableUtils.toString(TINY_LIMIT, delta(bfs)));
        }
    }

    private static void demoFloor() {
        initialize();
        for (BinaryFraction bf : take(LIMIT, P.binaryFractions())) {
            System.out.println("floor(" + bf + ") = " + bf.floor());
        }
    }

    private static void demoCeiling() {
        initialize();
        for (BinaryFraction bf : take(LIMIT, P.binaryFractions())) {
            System.out.println("ceiling(" + bf + ") = " + bf.ceiling());
        }
    }

    private static void demoEquals_BinaryFraction() {
        initialize();
        for (Pair<BinaryFraction, BinaryFraction> p : take(LIMIT, P.pairs(P.binaryFractions()))) {
            System.out.println(p.a + (p.a.equals(p.b) ? " = " : " ≠ ") + p.b);
        }
    }

    private static void demoEquals_null() {
        initialize();
        for (BinaryFraction bf : take(LIMIT, P.binaryFractions())) {
            //noinspection ObjectEqualsNull
            System.out.println(bf + (bf.equals(null) ? " = " : " ≠ ") + null);
        }
    }

    private static void demoHashCode() {
        initialize();
        for (BinaryFraction bf : take(LIMIT, P.binaryFractions())) {
            System.out.println("hashCode(" + bf + ") = " + bf.hashCode());
        }
    }

    private static void demoCompareTo() {
        initialize();
        for (Pair<BinaryFraction, BinaryFraction> p : take(LIMIT, P.pairs(P.binaryFractions()))) {
            System.out.println(p.a + " " + Ordering.compare(p.a, p.b).toChar() + " " + p.b);
        }
    }

    private static void demoRead() {
        initialize();
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("read(" + nicePrint(s) + ") = " + read(s));
        }
    }

    private static void demoRead_targeted() {
        initialize();
        for (String s : take(LIMIT, P.strings(BINARY_FRACTION_CHARS))) {
            System.out.println("read(" + s + ") = " + read(s));
        }
    }

    private static void demoFindIn() {
        initialize();
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("findIn(" + nicePrint(s) + ") = " + findIn(s));
        }
    }

    private static void demoFindIn_targeted() {
        initialize();
        for (String s : take(LIMIT, P.strings(BINARY_FRACTION_CHARS))) {
            System.out.println("findIn(" + s + ") = " + findIn(s));
        }
    }

    private static void demoToString() {
        initialize();
        for (BinaryFraction bf : take(LIMIT, P.binaryFractions())) {
            System.out.println(bf);
        }
    }
}
