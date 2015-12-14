package mho.wheels.math;

import mho.wheels.ordering.Ordering;
import mho.wheels.structures.Pair;
import mho.wheels.testing.Demos;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.List;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.math.BinaryFraction.*;
import static mho.wheels.math.BinaryFraction.sum;
import static mho.wheels.testing.Testing.*;

@SuppressWarnings("UnusedDeclaration")
public strictfp class BinaryFractionDemos extends Demos {
    private static final @NotNull String BINARY_FRACTION_CHARS = " -0123456789<>";

    public BinaryFractionDemos(boolean useRandom) {
        super(useRandom);
    }

    private void demoGetMantissa() {
        initialize();
        for (BinaryFraction bf : take(LIMIT, P.binaryFractions())) {
            System.out.println("getMantissa(" + bf + ") = " + bf.getMantissa());
        }
    }

    private void demoGetExponent() {
        initialize();
        for (BinaryFraction bf : take(LIMIT, P.binaryFractions())) {
            System.out.println("getExponent(" + bf + ") = " + bf.getExponent());
        }
    }

    private void demoOf_BigInteger_int() {
        initialize();
        Iterable<Pair<BigInteger, Integer>> ps = filterInfinite(
                p -> (long) p.b + p.a.getLowestSetBit() < Integer.MAX_VALUE,
                P.pairs(P.bigIntegers(), P.integers())
        );
        for (Pair<BigInteger, Integer> p : take(LIMIT, ps)) {
            System.out.println("of(" + p.a + ", " + p.b + ") = " + of(p.a, p.b));
        }
    }

    private void demoOf_BigInteger() {
        initialize();
        for (BigInteger i : take(LIMIT, P.bigIntegers())) {
            System.out.println("of(" + i + ") = " + of(i));
        }
    }

    private void demoOf_int() {
        initialize();
        for (int i : take(LIMIT, P.integers())) {
            System.out.println("of(" + i + ") = " + of(i));
        }
    }

    private void demoOf_float() {
        initialize();
        for (float f : take(LIMIT, P.floats())) {
            System.out.println("of(" + f + ") = " + of(f));
        }
    }

    private void demoOf_double() {
        initialize();
        for (double d : take(LIMIT, P.doubles())) {
            System.out.println("of(" + d + ") = " + of(d));
        }
    }

    private void demoBigDecimalValue() {
        initialize();
        for (BinaryFraction bf : take(LIMIT, P.binaryFractions())) {
            System.out.println("bigDecimalValue(" + bf + ") = " + bf.bigDecimalValue());
        }
    }

    private void demoFloatRange() {
        initialize();
        for (BinaryFraction bf : take(LIMIT, P.binaryFractions())) {
            System.out.println("floatRange(" + bf + ") = " + bf.floatRange());
        }
    }

    private void demoDoubleRange() {
        initialize();
        for (BinaryFraction bf : take(LIMIT, P.binaryFractions())) {
            System.out.println("doubleRange(" + bf + ") = " + bf.doubleRange());
        }
    }

    private void demoBigIntegerValueExact() {
        initialize();
        for (BinaryFraction bf : take(LIMIT, map(BinaryFraction::of, P.bigIntegers()))) {
            System.out.println("bigIntegerValueExact(" + bf + ") = " + bf.bigIntegerValueExact());
        }
    }

    private void demoIsInteger() {
        initialize();
        for (BinaryFraction bf : take(LIMIT, P.binaryFractions())) {
            System.out.println(bf + " is " + (bf.isInteger() ? "" : "not ") + "an integer");
        }
    }

    private void demoAdd() {
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

    private void demoNegate() {
        initialize();
        for (BinaryFraction bf : take(LIMIT, P.binaryFractions())) {
            System.out.println("-(" + bf + ") = " + bf.negate());
        }
    }

    private void demoAbs() {
        initialize();
        for (BinaryFraction bf : take(LIMIT, P.binaryFractions())) {
            System.out.println("|" + bf + "| = " + bf.abs());
        }
    }

    private void demoSignum() {
        initialize();
        for (BinaryFraction bf : take(LIMIT, P.binaryFractions())) {
            System.out.println("signum(" + bf + ") = " + bf.signum());
        }
    }

    private void demoSubtract() {
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

    private void demoMultiply() {
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

    private void demoShiftLeft() {
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

    private void demoShiftRight() {
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

    private void demoSum() {
        initialize();
        for (List<BinaryFraction> rs : take(LIMIT, P.withScale(4).lists(P.binaryFractions()))) {
            String listString = tail(init(rs.toString()));
            System.out.println("Σ(" + listString + ") = " + sum(rs));
        }
    }

    private void demoProduct() {
        initialize();
        for (List<BinaryFraction> rs : take(LIMIT, P.withScale(4).lists(P.binaryFractions()))) {
            String listString = tail(init(rs.toString()));
            System.out.println("Π(" + listString + ") = " + product(rs));
        }
    }

    private void demoDelta_finite() {
        initialize();
        for (List<BinaryFraction> rs : take(LIMIT, P.withScale(4).listsAtLeast(1, P.binaryFractions()))) {
            String listString = tail(init(rs.toString()));
            System.out.println("Δ(" + listString + ") = " + its(delta(rs)));
        }
    }

    private void demoDelta_infinite() {
        initialize();
        for (Iterable<BinaryFraction> bfs : take(MEDIUM_LIMIT, P.prefixPermutations(EP.binaryFractions()))) {
            String listString = tail(init(its(bfs)));
            System.out.println("Δ(" + listString + ") = " + its(delta(bfs)));
        }
    }

    private void demoFloor() {
        initialize();
        for (BinaryFraction bf : take(LIMIT, P.binaryFractions())) {
            System.out.println("floor(" + bf + ") = " + bf.floor());
        }
    }

    private void demoCeiling() {
        initialize();
        for (BinaryFraction bf : take(LIMIT, P.binaryFractions())) {
            System.out.println("ceiling(" + bf + ") = " + bf.ceiling());
        }
    }

    private void demoEquals_BinaryFraction() {
        initialize();
        for (Pair<BinaryFraction, BinaryFraction> p : take(LIMIT, P.pairs(P.binaryFractions()))) {
            System.out.println(p.a + (p.a.equals(p.b) ? " = " : " ≠ ") + p.b);
        }
    }

    private void demoEquals_null() {
        initialize();
        for (BinaryFraction bf : take(LIMIT, P.binaryFractions())) {
            //noinspection ObjectEqualsNull
            System.out.println(bf + (bf.equals(null) ? " = " : " ≠ ") + null);
        }
    }

    private void demoHashCode() {
        initialize();
        for (BinaryFraction bf : take(LIMIT, P.binaryFractions())) {
            System.out.println("hashCode(" + bf + ") = " + bf.hashCode());
        }
    }

    private void demoCompareTo() {
        initialize();
        for (Pair<BinaryFraction, BinaryFraction> p : take(LIMIT, P.pairs(P.binaryFractions()))) {
            System.out.println(p.a + " " + Ordering.compare(p.a, p.b).toChar() + " " + p.b);
        }
    }

    private void demoRead() {
        initialize();
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("read(" + nicePrint(s) + ") = " + read(s));
        }
    }

    private void demoRead_targeted() {
        initialize();
        for (String s : take(LIMIT, P.strings(BINARY_FRACTION_CHARS))) {
            System.out.println("read(" + s + ") = " + read(s));
        }
    }

    private void demoFindIn() {
        initialize();
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("findIn(" + nicePrint(s) + ") = " + findIn(s));
        }
    }

    private void demoFindIn_targeted() {
        initialize();
        for (String s : take(LIMIT, P.strings(BINARY_FRACTION_CHARS))) {
            System.out.println("findIn(" + s + ") = " + findIn(s));
        }
    }

    private void demoToString() {
        initialize();
        for (BinaryFraction bf : take(LIMIT, P.binaryFractions())) {
            System.out.println(bf);
        }
    }
}
