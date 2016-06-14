package mho.wheels.iterables;

import mho.wheels.structures.Pair;
import mho.wheels.testing.Demos;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.testing.Testing.*;

@SuppressWarnings("UnusedDeclaration")
public strictfp class IterableUtilsDemos extends Demos {
    public IterableUtilsDemos(boolean useRandom) {
        super(useRandom);
    }

    private void demoAddTo_Collection_Iterable() {
        for (Pair<List<Integer>, List<Integer>> p : take(LIMIT, P.pairs(P.lists(P.withNull(P.integers()))))) {
            List<Integer> list = new ArrayList<>();
            list.addAll(p.b);
            addTo(p.a, list);
            System.out.println("addTo(" + p.a + ", " + p.b + ") => " + list);
        }
    }

    private void demoAddTo_Collection_String() {
        Iterable<Pair<String, List<Character>>> ps = P.pairs(P.strings(), P.lists(P.withNull(P.characters())));
        for (Pair<String, List<Character>> p : take(LIMIT, ps)) {
            List<Character> list = new ArrayList<>();
            list.addAll(p.b);
            addTo(p.a, list);
            System.out.println("addTo(" + p.a + ", " + p.b + ") => " + list);
        }
    }

    private void demoToList_Iterable() {
        for (List<Integer> is : take(LIMIT, P.lists(P.withNull(P.integers())))) {
            System.out.println("toList(" + middle(is.toString()) + ") = " + toList(is));
        }
    }

    private void demoToList_String() {
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("toList(" + s + ") = " + toList(s));
        }
    }

    private void demoToString_Iterable() {
        for (List<Integer> is : take(LIMIT, P.lists(P.withNull(P.integers())))) {
            System.out.println("toString(" + middle(is.toString()) + ") = " + IterableUtils.toString(is));
        }
    }

    private void demoToString_int_finite_Iterable() {
        Iterable<Pair<Integer, List<Integer>>> ps;
        if (P instanceof ExhaustiveProvider) {
            ps = map(
                    p -> new Pair<Integer, List<Integer>>(p.b, p.a),
                    ((ExhaustiveProvider) P).pairsSquareRootOrder(
                            P.lists(P.withNull(P.integers())),
                            P.naturalIntegers()
                    )
            );
        } else {
            ps = P.pairs(P.withScale(20).naturalIntegersGeometric(), P.lists(P.integers()));
        }
        for (Pair<Integer, List<Integer>> p : take(LIMIT, ps)) {
            System.out.println("toString(" + p.a + ", " + p.b + ") = " + IterableUtils.toString(p.a, p.b));
        }
    }

    private void demoToString_int_infinite_Iterable() {
        Iterable<Pair<Integer, Iterable<Integer>>> ps;
        if (P instanceof ExhaustiveProvider) {
            ps = map(
                    p -> new Pair<>(p.b, cycle(p.a)),
                    ((ExhaustiveProvider) P).pairsSquareRootOrder(
                            P.lists(P.withNull(P.integers())),
                            P.naturalIntegers()
                    )
            );
        } else {
            ps = P.pairs(
                    P.withScale(20).naturalIntegersGeometric(),
                    map(IterableUtils::cycle, P.lists(P.integers()))
            );
        }
        for (Pair<Integer, Iterable<Integer>> p : take(LIMIT, ps)) {
            System.out.println("toString(" + p.a + ", " + IterableUtils.toString(10, p.b) + ") = " +
                    IterableUtils.toString(p.a, p.b));
        }
    }

    private void demoFromString() {
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("fromString(" + s + ") = " + toList(fromString(s)));
        }
    }

    private void demoCharsToString() {
        for (List<Character> cs : take(LIMIT, P.lists(P.characters()))) {
            System.out.println("charsToString(" + middle(cs.toString()) + ") = " + charsToString(cs));
        }
    }

    private void demoUnrepeat() {
        for (List<Integer> is : take(LIMIT, P.lists(P.withNull(P.integers())))) {
            System.out.println("unrepeat(" + middle(is.toString()) + ") = " + unrepeat(is));
        }
    }

    private void demoSumByte() {
        for (List<Byte> bs : take(LIMIT, P.lists(P.bytes()))) {
            System.out.println("Σ(" + middle(bs.toString()) + ") = " + sumByte(bs));
        }
    }

    private void demoSumShort() {
        for (List<Short> ss : take(LIMIT, P.lists(P.shorts()))) {
            System.out.println("Σ(" + middle(ss.toString()) + ") = " + sumShort(ss));
        }
    }

    private void demoSumInteger() {
        for (List<Integer> is : take(LIMIT, P.lists(P.integers()))) {
            System.out.println("Σ(" + middle(is.toString()) + ") = " + sumInteger(is));
        }
    }

    private void demoSumLong() {
        for (List<Long> ls : take(LIMIT, P.lists(P.longs()))) {
            System.out.println("Σ(" + middle(ls.toString()) + ") = " + sumLong(ls));
        }
    }

    private void demoSumFloat() {
        for (List<Float> fs : take(LIMIT, P.lists(P.floats()))) {
            System.out.println("Σ(" + middle(fs.toString()) + ") = " + sumFloat(fs));
        }
    }

    private void demoSumDouble() {
        for (List<Double> ds : take(LIMIT, P.lists(P.doubles()))) {
            System.out.println("Σ(" + middle(ds.toString()) + ") = " + sumDouble(ds));
        }
    }

    private void demoSumBigInteger() {
        for (List<BigInteger> is : take(LIMIT, P.lists(P.bigIntegers()))) {
            System.out.println("Σ(" + middle(is.toString()) + ") = " + sumBigInteger(is));
        }
    }

    private void demoSumBigDecimal() {
        for (List<BigDecimal> bds : take(LIMIT, P.lists(P.bigDecimals()))) {
            System.out.println("Σ(" + middle(bds.toString()) + ") = " + sumBigDecimal(bds));
        }
    }

    private void demoProductByte() {
        for (List<Byte> bs : take(LIMIT, P.lists(P.bytes()))) {
            System.out.println("Π(" + middle(bs.toString()) + ") = " + productByte(bs));
        }
    }

    private void demoProductShort() {
        for (List<Short> ss : take(LIMIT, P.lists(P.shorts()))) {
            System.out.println("Π(" + middle(ss.toString()) + ") = " + productShort(ss));
        }
    }

    private void demoProductInteger() {
        for (List<Integer> is : take(LIMIT, P.lists(P.integers()))) {
            System.out.println("Π(" + middle(is.toString()) + ") = " + productInteger(is));
        }
    }

    private void demoProductLong() {
        for (List<Long> ls : take(LIMIT, P.lists(P.longs()))) {
            System.out.println("Π(" + middle(ls.toString()) + ") = " + productLong(ls));
        }
    }

    private void demoProductFloat() {
        for (List<Float> fs : take(LIMIT, P.lists(P.floats()))) {
            System.out.println("Π(" + middle(fs.toString()) + ") = " + productFloat(fs));
        }
    }

    private void demoProductDouble() {
        for (List<Double> ds : take(LIMIT, P.lists(P.doubles()))) {
            System.out.println("Π(" + middle(ds.toString()) + ") = " + productDouble(ds));
        }
    }

    private void demoProductBigInteger() {
        for (List<BigInteger> is : take(LIMIT, P.lists(P.bigIntegers()))) {
            System.out.println("Π(" + middle(is.toString()) + ") = " + productBigInteger(is));
        }
    }

    private void demoProductBigDecimal() {
        for (List<BigDecimal> bds : take(LIMIT, P.lists(P.bigDecimals()))) {
            System.out.println("Π(" + middle(bds.toString()) + ") = " + productBigDecimal(bds));
        }
    }

    private void demoSumSignBigInteger() {
        for (List<BigInteger> is : take(LIMIT, P.withScale(4).lists(P.bigIntegers()))) {
            System.out.println("sumSignBigInteger(" + middle(is.toString()) + ") = " + sumSignBigInteger(is));
        }
    }

    private void demoSumSignBigDecimal() {
        for (List<BigDecimal> bds : take(LIMIT, P.withScale(4).lists(P.bigDecimals()))) {
            System.out.println("sumSignBigDecimal(" + middle(bds.toString()) + ") = " + sumSignBigDecimal(bds));
        }
    }

    private void demoDeltaByte_finite() {
        for (List<Byte> bs : take(LIMIT, P.listsAtLeast(1, P.bytes()))) {
            System.out.println("Δ(" + middle(bs.toString()) + ") = " +
                    IterableUtils.toString(TINY_LIMIT, deltaByte(bs)));
        }
    }

    private void demoDeltaByte_infinite() {
        for (Iterable<Byte> bs : take(MEDIUM_LIMIT, P.prefixPermutations(EP.bytes()))) {
            System.out.println("Δ(" + middle(IterableUtils.toString(TINY_LIMIT, bs)) + ") = " +
                    IterableUtils.toString(TINY_LIMIT, deltaByte(bs)));
        }
    }

    private void demoDeltaShort_finite() {
        for (List<Short> ss : take(LIMIT, P.listsAtLeast(1, P.shorts()))) {
            System.out.println("Δ(" + middle(ss.toString()) + ") = " +
                    IterableUtils.toString(TINY_LIMIT, deltaShort(ss)));
        }
    }

    private void demoDeltaShort_infinite() {
        for (Iterable<Short> ss : take(MEDIUM_LIMIT, P.prefixPermutations(EP.shorts()))) {
            System.out.println("Δ(" + middle(IterableUtils.toString(TINY_LIMIT, ss)) + ") = " +
                    IterableUtils.toString(TINY_LIMIT, deltaShort(ss)));
        }
    }

    private void demoDeltaInteger_finite() {
        for (List<Integer> is : take(LIMIT, P.listsAtLeast(1, P.integers()))) {
            System.out.println("Δ(" + middle(is.toString()) + ") = " +
                    IterableUtils.toString(TINY_LIMIT, deltaInteger(is)));
        }
    }

    private void demoDeltaInteger_infinite() {
        for (Iterable<Integer> is : take(MEDIUM_LIMIT, P.prefixPermutations(EP.integers()))) {
            System.out.println("Δ(" + middle(IterableUtils.toString(TINY_LIMIT, is)) + ") = " +
                    IterableUtils.toString(TINY_LIMIT, deltaInteger(is)));
        }
    }

    private void demoDeltaLong_finite() {
        for (List<Long> ls : take(LIMIT, P.listsAtLeast(1, P.longs()))) {
            System.out.println("Δ(" + middle(ls.toString()) + ") = " +
                    IterableUtils.toString(TINY_LIMIT, deltaLong(ls)));
        }
    }

    private void demoDeltaLong_infinite() {
        for (Iterable<Long> ls : take(MEDIUM_LIMIT, P.prefixPermutations(EP.longs()))) {
            System.out.println("Δ(" + middle(IterableUtils.toString(TINY_LIMIT, ls)) + ") = " +
                    IterableUtils.toString(TINY_LIMIT, deltaLong(ls)));
        }
    }

    private void demoDeltaBigInteger_finite() {
        for (List<BigInteger> is : take(LIMIT, P.listsAtLeast(1, P.bigIntegers()))) {
            System.out.println("Δ(" + middle(is.toString()) + ") = " +
                    IterableUtils.toString(TINY_LIMIT, deltaBigInteger(is)));
        }
    }

    private void demoDeltaBigInteger_infinite() {
        for (Iterable<BigInteger> is : take(MEDIUM_LIMIT, P.prefixPermutations(EP.bigIntegers()))) {
            System.out.println("Δ(" + middle(IterableUtils.toString(TINY_LIMIT, is)) + ") = " +
                    IterableUtils.toString(TINY_LIMIT, deltaBigInteger(is)));
        }
    }

    private void demoDeltaBigDecimal_finite() {
        for (List<BigDecimal> bds : take(LIMIT, P.listsAtLeast(1, P.bigDecimals()))) {
            System.out.println("Δ(" + middle(bds.toString()) + ") = " +
                    IterableUtils.toString(TINY_LIMIT, deltaBigDecimal(bds)));
        }
    }

    private void demoDeltaBigDecimal_infinite() {
        for (Iterable<BigDecimal> bds : take(SMALL_LIMIT, P.prefixPermutations(EP.bigDecimals()))) {
            System.out.println("Δ(" + middle(IterableUtils.toString(TINY_LIMIT, bds)) + ") = " +
                    IterableUtils.toString(TINY_LIMIT, deltaBigDecimal(bds)));
        }
    }

    private void demoDeltaFloat_finite() {
        for (List<Float> fs : take(LIMIT, P.listsAtLeast(1, P.floats()))) {
            System.out.println("Δ(" + middle(fs.toString()) + ") = " +
                    IterableUtils.toString(TINY_LIMIT, deltaFloat(fs)));
        }
    }

    private void demoDeltaFloat_infinite() {
        for (Iterable<Float> fs : take(MEDIUM_LIMIT, P.prefixPermutations(EP.floats()))) {
            System.out.println("Δ(" + middle(IterableUtils.toString(TINY_LIMIT, fs)) + ") = " +
                    IterableUtils.toString(TINY_LIMIT, deltaFloat(fs)));
        }
    }

    private void demoDeltaDouble_finite() {
        for (List<Double> ds : take(LIMIT, P.listsAtLeast(1, P.doubles()))) {
            System.out.println("Δ(" + middle(ds.toString()) + ") = " +
                    IterableUtils.toString(TINY_LIMIT, deltaDouble(ds)));
        }
    }

    private void demoDeltaDouble_infinite() {
        for (Iterable<Double> ds : take(MEDIUM_LIMIT, P.prefixPermutations(EP.doubles()))) {
            System.out.println("Δ(" + middle(IterableUtils.toString(TINY_LIMIT, ds)) + ") = " +
                    IterableUtils.toString(TINY_LIMIT, deltaDouble(ds)));
        }
    }

    private void demoDeltaCharacter_finite() {
        for (List<Character> cs : take(LIMIT, P.listsAtLeast(1, P.characters()))) {
            System.out.println("Δ(" + middle(cs.toString()) + ") = " +
                    IterableUtils.toString(TINY_LIMIT, deltaCharacter(cs)));
        }
    }

    private void demoDeltaCharacter_infinite() {
        for (Iterable<Character> cs : take(MEDIUM_LIMIT, P.prefixPermutations(EP.characters()))) {
            System.out.println("Δ(" + nicePrint(middle(IterableUtils.toString(TINY_LIMIT, cs))) + ") = " +
                    nicePrint(IterableUtils.toString(TINY_LIMIT, deltaCharacter(cs))));
        }
    }
}
