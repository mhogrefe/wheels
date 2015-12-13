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
    private void demoAddTo_Collection_Iterable() {
        initialize();
        for (Pair<List<Integer>, List<Integer>> p : take(LIMIT, P.pairs(P.lists(P.withNull(P.integers()))))) {
            List<Integer> list = new ArrayList<>();
            list.addAll(p.b);
            addTo(p.a, list);
            System.out.println("addTo(" + p.a + ", " + p.b + ") => " + list);
        }
    }

    private void demoAddTo_Collection_String() {
        initialize();
        Iterable<Pair<String, List<Character>>> ps = P.pairs(P.strings(), P.lists(P.withNull(P.characters())));
        for (Pair<String, List<Character>> p : take(LIMIT, ps)) {
            List<Character> list = new ArrayList<>();
            list.addAll(p.b);
            addTo(p.a, list);
            System.out.println("addTo(" + p.a + ", " + p.b + ") => " + list);
        }
    }

    private void demoToList_Iterable() {
        initialize();
        for (List<Integer> is : take(LIMIT, P.lists(P.withNull(P.integers())))) {
            String listString = tail(init(is.toString()));
            System.out.println("toList(" + listString + ") = " + toList(is));
        }
    }

    private void demoToList_String() {
        initialize();
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("toList(" + s + ") = " + toList(s));
        }
    }

    private void demoToString_Iterable() {
        initialize();
        for (List<Integer> is : take(LIMIT, P.lists(P.withNull(P.integers())))) {
            String listString = tail(init(is.toString()));
            System.out.println("toString(" + listString + ") = " + IterableUtils.toString(is));
        }
    }

    private void demoToString_int_finite_Iterable() {
        initialize();
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
        initialize();
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
        initialize();
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("fromString(" + s + ") = " + toList(fromString(s)));
        }
    }

    private void demoCharsToString() {
        initialize();
        for (List<Character> cs : take(LIMIT, P.lists(P.characters()))) {
            String listString = tail(init(cs.toString()));
            System.out.println("charsToString(" + listString + ") = " + charsToString(cs));
        }
    }

    private void demoRangeUp_byte() {
        initialize();
        for (byte b : take(LIMIT, P.bytes())) {
            System.out.println("range(" + b + ") = " + toList(rangeUp(b)));
        }
    }

    private void demoRangeUp_short() {
        initialize();
        for (short s : take(LIMIT, P.shorts())) {
            System.out.println("range(" + s + ") = " + IterableUtils.toString(TINY_LIMIT, rangeUp(s)));
        }
    }

    private void demoRangeUp_int() {
        initialize();
        for (int i : take(LIMIT, P.integers())) {
            System.out.println("range(" + i + ") = " + IterableUtils.toString(TINY_LIMIT, rangeUp(i)));
        }
    }

    private void demoRangeUp_long() {
        initialize();
        for (long l : take(LIMIT, P.longs())) {
            System.out.println("range(" + l + ") = " + IterableUtils.toString(TINY_LIMIT, rangeUp(l)));
        }
    }

    private void demoRangeUp_BigInteger() {
        initialize();
        for (BigInteger i : take(LIMIT, P.bigIntegers())) {
            System.out.println("range(" + i + ") = " + IterableUtils.toString(TINY_LIMIT, rangeUp(i)));
        }
    }

    private void demoRangeUp_BigDecimal() {
        initialize();
        for (BigDecimal bd : take(LIMIT, P.bigDecimals())) {
            System.out.println("range(" + bd + ") = " + IterableUtils.toString(TINY_LIMIT, rangeUp(bd)));
        }
    }

    private void demoRangeUp_char() {
        initialize();
        for (char c : take(LIMIT, P.characters())) {
            System.out.println("range(" + c + ") = " + IterableUtils.toString(TINY_LIMIT, rangeUp(c)));
        }
    }

    private void demoRangeUp_float() {
        initialize();
        for (float f : take(LIMIT, filter(g -> !Float.isNaN(g), P.floats()))) {
            System.out.println("range(" + f + ") = " + IterableUtils.toString(TINY_LIMIT, rangeUp(f)));
        }
    }

    private void demoRangeUp_double() {
        initialize();
        for (double d : take(LIMIT, filter(e -> !Double.isNaN(e), P.doubles()))) {
            System.out.println("range(" + d + ") = " + IterableUtils.toString(TINY_LIMIT, rangeUp(d)));
        }
    }

    private void demoRange_byte_byte() {
        initialize();
        for (Pair<Byte, Byte> p : take(LIMIT, P.pairs(P.bytes()))) {
            System.out.println("range(" + p.a + ", " + p.b + ") = " +
                    IterableUtils.toString(TINY_LIMIT, range(p.a, p.b)));
        }
    }

    private void demoRange_short_short() {
        initialize();
        for (Pair<Short, Short> p : take(LIMIT, P.pairs(P.shorts()))) {
            System.out.println("range(" + p.a + ", " + p.b + ") = " +
                    IterableUtils.toString(TINY_LIMIT, range(p.a, p.b)));
        }
    }

    private void demoRange_int_int() {
        initialize();
        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.integers()))) {
            System.out.println("range(" + p.a + ", " + p.b + ") = " +
                    IterableUtils.toString(TINY_LIMIT, range(p.a, p.b)));
        }
    }

    private void demoRange_long_long() {
        initialize();
        for (Pair<Long, Long> p : take(LIMIT, P.pairs(P.longs()))) {
            System.out.println("range(" + p.a + ", " + p.b + ") = " +
                    IterableUtils.toString(TINY_LIMIT, range(p.a, p.b)));
        }
    }

    private void demoRange_BigInteger_BigInteger() {
        initialize();
        for (Pair<BigInteger, BigInteger> p : take(LIMIT, P.pairs(P.bigIntegers()))) {
            System.out.println("range(" + p.a + ", " + p.b + ") = " +
                    IterableUtils.toString(TINY_LIMIT, range(p.a, p.b)));
        }
    }

    private void demoRange_BigDecimal_BigDecimal() {
        initialize();
        for (Pair<BigDecimal, BigDecimal> p : take(LIMIT, P.pairs(P.bigDecimals()))) {
            System.out.println("range(" + p.a + ", " + p.b + ") = " +
                    IterableUtils.toString(TINY_LIMIT, range(p.a, p.b)));
        }
    }

    private void demoRange_char_char() {
        initialize();
        for (Pair<Character, Character> p : take(LIMIT, P.pairs(P.characters()))) {
            System.out.println("range(" + p.a + ", " + p.b + ") = " +
                    IterableUtils.toString(TINY_LIMIT, range(p.a, p.b)));
        }
    }

    private void demoRange_float_float() {
        initialize();
        Iterable<Pair<Float, Float>> ps = P.pairs(filter(f -> !Float.isNaN(f), P.floats()));
        for (Pair<Float, Float> p : take(LIMIT, ps)) {
            System.out.println("range(" + p.a + ", " + p.b + ") = " +
                    IterableUtils.toString(TINY_LIMIT, range(p.a, p.b)));
        }
    }

    private void demoRange_double_double() {
        initialize();
        Iterable<Pair<Double, Double>> ps = P.pairs(filter(d -> !Double.isNaN(d), P.doubles()));
        for (Pair<Double, Double> p : take(LIMIT, ps)) {
            System.out.println("range(" + p.a + ", " + p.b + ") = " +
                    IterableUtils.toString(TINY_LIMIT, range(p.a, p.b)));
        }
    }

    private void demoUnrepeat() {
        initialize();
        for (List<Integer> is : take(LIMIT, P.lists(P.withNull(P.integers())))) {
            String listString = tail(init(is.toString()));
            System.out.println("unrepeat(" + listString + ") = " + unrepeat(is));
        }
    }

    private void demoSumByte() {
        initialize();
        for (List<Byte> bs : take(LIMIT, P.lists(P.bytes()))) {
            String listString = tail(init(bs.toString()));
            System.out.println("Σ(" + listString + ") = " + sumByte(bs));
        }
    }

    private void demoSumShort() {
        initialize();
        for (List<Short> ss : take(LIMIT, P.lists(P.shorts()))) {
            String listString = tail(init(ss.toString()));
            System.out.println("Σ(" + listString + ") = " + sumShort(ss));
        }
    }

    private void demoSumInteger() {
        initialize();
        for (List<Integer> is : take(LIMIT, P.lists(P.integers()))) {
            String listString = tail(init(is.toString()));
            System.out.println("Σ(" + listString + ") = " + sumInteger(is));
        }
    }

    private void demoSumLong() {
        initialize();
        for (List<Long> ls : take(LIMIT, P.lists(P.longs()))) {
            String listString = tail(init(ls.toString()));
            System.out.println("Σ(" + listString + ") = " + sumLong(ls));
        }
    }

    private void demoSumFloat() {
        initialize();
        for (List<Float> fs : take(LIMIT, P.lists(P.floats()))) {
            String listString = tail(init(fs.toString()));
            System.out.println("Σ(" + listString + ") = " + sumFloat(fs));
        }
    }

    private void demoSumDouble() {
        initialize();
        for (List<Double> ds : take(LIMIT, P.lists(P.doubles()))) {
            String listString = tail(init(ds.toString()));
            System.out.println("Σ(" + listString + ") = " + sumDouble(ds));
        }
    }

    private void demoSumBigInteger() {
        initialize();
        for (List<BigInteger> is : take(LIMIT, P.lists(P.bigIntegers()))) {
            String listString = tail(init(is.toString()));
            System.out.println("Σ(" + listString + ") = " + sumBigInteger(is));
        }
    }

    private void demoSumBigDecimal() {
        initialize();
        for (List<BigDecimal> bds : take(LIMIT, P.lists(P.bigDecimals()))) {
            String listString = tail(init(bds.toString()));
            System.out.println("Σ(" + listString + ") = " + sumBigDecimal(bds));
        }
    }

    private void demoProductByte() {
        initialize();
        for (List<Byte> bs : take(LIMIT, P.lists(P.bytes()))) {
            String listString = tail(init(bs.toString()));
            System.out.println("Π(" + listString + ") = " + productByte(bs));
        }
    }

    private void demoProductShort() {
        initialize();
        for (List<Short> ss : take(LIMIT, P.lists(P.shorts()))) {
            String listString = tail(init(ss.toString()));
            System.out.println("Π(" + listString + ") = " + productShort(ss));
        }
    }

    private void demoProductInteger() {
        initialize();
        for (List<Integer> is : take(LIMIT, P.lists(P.integers()))) {
            String listString = tail(init(is.toString()));
            System.out.println("Π(" + listString + ") = " + productInteger(is));
        }
    }

    private void demoProductLong() {
        initialize();
        for (List<Long> ls : take(LIMIT, P.lists(P.longs()))) {
            String listString = tail(init(ls.toString()));
            System.out.println("Π(" + listString + ") = " + productLong(ls));
        }
    }

    private void demoProductFloat() {
        initialize();
        for (List<Float> fs : take(LIMIT, P.lists(P.floats()))) {
            String listString = tail(init(fs.toString()));
            System.out.println("Π(" + listString + ") = " + productFloat(fs));
        }
    }

    private void demoProductDouble() {
        initialize();
        for (List<Double> ds : take(LIMIT, P.lists(P.doubles()))) {
            String listString = tail(init(ds.toString()));
            System.out.println("Π(" + listString + ") = " + productDouble(ds));
        }
    }

    private void demoProductBigInteger() {
        initialize();
        for (List<BigInteger> is : take(LIMIT, P.lists(P.bigIntegers()))) {
            String listString = tail(init(is.toString()));
            System.out.println("Π(" + listString + ") = " + productBigInteger(is));
        }
    }

    private void demoProductBigDecimal() {
        initialize();
        for (List<BigDecimal> bds : take(LIMIT, P.lists(P.bigDecimals()))) {
            String listString = tail(init(bds.toString()));
            System.out.println("Π(" + listString + ") = " + productBigDecimal(bds));
        }
    }

    private void demoDeltaByte_finite() {
        initialize();
        for (List<Byte> bs : take(LIMIT, P.listsAtLeast(1, P.bytes()))) {
            String listString = tail(init(bs.toString()));
            System.out.println("Δ(" + listString + ") = " + IterableUtils.toString(TINY_LIMIT, deltaByte(bs)));
        }
    }

    private void demoDeltaByte_infinite() {
        initialize();
        for (Iterable<Byte> bs : take(MEDIUM_LIMIT, P.prefixPermutations(EP.bytes()))) {
            String listString = tail(init(IterableUtils.toString(TINY_LIMIT, bs)));
            System.out.println("Δ(" + listString + ") = " + IterableUtils.toString(TINY_LIMIT, deltaByte(bs)));
        }
    }

    private void demoDeltaShort_finite() {
        initialize();
        for (List<Short> ss : take(LIMIT, P.listsAtLeast(1, P.shorts()))) {
            String listString = tail(init(ss.toString()));
            System.out.println("Δ(" + listString + ") = " + IterableUtils.toString(TINY_LIMIT, deltaShort(ss)));
        }
    }

    private void demoDeltaShort_infinite() {
        initialize();
        for (Iterable<Short> ss : take(MEDIUM_LIMIT, P.prefixPermutations(EP.shorts()))) {
            String listString = tail(init(IterableUtils.toString(TINY_LIMIT, ss)));
            System.out.println("Δ(" + listString + ") = " + IterableUtils.toString(TINY_LIMIT, deltaShort(ss)));
        }
    }

    private void demoDeltaInteger_finite() {
        initialize();
        for (List<Integer> is : take(LIMIT, P.listsAtLeast(1, P.integers()))) {
            String listString = tail(init(is.toString()));
            System.out.println("Δ(" + listString + ") = " + IterableUtils.toString(TINY_LIMIT, deltaInteger(is)));
        }
    }

    private void demoDeltaInteger_infinite() {
        initialize();
        for (Iterable<Integer> is : take(MEDIUM_LIMIT, P.prefixPermutations(EP.integers()))) {
            String listString = tail(init(IterableUtils.toString(TINY_LIMIT, is)));
            System.out.println("Δ(" + listString + ") = " + IterableUtils.toString(TINY_LIMIT, deltaInteger(is)));
        }
    }

    private void demoDeltaLong_finite() {
        initialize();
        for (List<Long> ls : take(LIMIT, P.listsAtLeast(1, P.longs()))) {
            String listString = tail(init(ls.toString()));
            System.out.println("Δ(" + listString + ") = " + IterableUtils.toString(TINY_LIMIT, deltaLong(ls)));
        }
    }

    private void demoDeltaLong_infinite() {
        initialize();
        for (Iterable<Long> ls : take(MEDIUM_LIMIT, P.prefixPermutations(EP.longs()))) {
            String listString = tail(init(IterableUtils.toString(TINY_LIMIT, ls)));
            System.out.println("Δ(" + listString + ") = " + IterableUtils.toString(TINY_LIMIT, deltaLong(ls)));
        }
    }

    private void demoDeltaBigInteger_finite() {
        initialize();
        for (List<BigInteger> is : take(LIMIT, P.listsAtLeast(1, P.bigIntegers()))) {
            String listString = tail(init(is.toString()));
            System.out.println("Δ(" + listString + ") = " + IterableUtils.toString(TINY_LIMIT, deltaBigInteger(is)));
        }
    }

    private void demoDeltaBigInteger_infinite() {
        initialize();
        for (Iterable<BigInteger> is : take(MEDIUM_LIMIT, P.prefixPermutations(EP.bigIntegers()))) {
            String listString = tail(init(IterableUtils.toString(TINY_LIMIT, is)));
            System.out.println("Δ(" + listString + ") = " + IterableUtils.toString(TINY_LIMIT, deltaBigInteger(is)));
        }
    }

    private void demoDeltaBigDecimal_finite() {
        initialize();
        for (List<BigDecimal> bds : take(LIMIT, P.listsAtLeast(1, P.bigDecimals()))) {
            String listString = tail(init(bds.toString()));
            System.out.println("Δ(" + listString + ") = " + IterableUtils.toString(TINY_LIMIT, deltaBigDecimal(bds)));
        }
    }

    private void demoDeltaBigDecimal_infinite() {
        initialize();
        for (Iterable<BigDecimal> bds : take(SMALL_LIMIT, P.prefixPermutations(EP.bigDecimals()))) {
            String listString = tail(init(IterableUtils.toString(TINY_LIMIT, bds)));
            System.out.println("Δ(" + listString + ") = " + IterableUtils.toString(TINY_LIMIT, deltaBigDecimal(bds)));
        }
    }

    private void demoDeltaFloat_finite() {
        initialize();
        for (List<Float> fs : take(LIMIT, P.listsAtLeast(1, P.floats()))) {
            String listString = tail(init(fs.toString()));
            System.out.println("Δ(" + listString + ") = " + IterableUtils.toString(TINY_LIMIT, deltaFloat(fs)));
        }
    }

    private void demoDeltaFloat_infinite() {
        initialize();
        for (Iterable<Float> fs : take(MEDIUM_LIMIT, P.prefixPermutations(EP.floats()))) {
            String listString = tail(init(IterableUtils.toString(TINY_LIMIT, fs)));
            System.out.println("Δ(" + listString + ") = " + IterableUtils.toString(TINY_LIMIT, deltaFloat(fs)));
        }
    }

    private void demoDeltaDouble_finite() {
        initialize();
        for (List<Double> ds : take(LIMIT, P.listsAtLeast(1, P.doubles()))) {
            String listString = tail(init(ds.toString()));
            System.out.println("Δ(" + listString + ") = " + IterableUtils.toString(TINY_LIMIT, deltaDouble(ds)));
        }
    }

    private void demoDeltaDouble_infinite() {
        initialize();
        for (Iterable<Double> ds : take(MEDIUM_LIMIT, P.prefixPermutations(EP.doubles()))) {
            String listString = tail(init(IterableUtils.toString(TINY_LIMIT, ds)));
            System.out.println("Δ(" + listString + ") = " + IterableUtils.toString(TINY_LIMIT, deltaDouble(ds)));
        }
    }

    private void demoDeltaCharacter_finite() {
        initialize();
        for (List<Character> cs : take(LIMIT, P.listsAtLeast(1, P.characters()))) {
            String listString = tail(init(cs.toString()));
            System.out.println("Δ(" + listString + ") = " + IterableUtils.toString(TINY_LIMIT, deltaCharacter(cs)));
        }
    }

    private void demoDeltaCharacter_infinite() {
        initialize();
        for (Iterable<Character> cs : take(MEDIUM_LIMIT, P.prefixPermutations(EP.characters()))) {
            String listString = tail(init(IterableUtils.toString(TINY_LIMIT, cs)));
            System.out.println("Δ(" + nicePrint(listString) + ") = " +
                    nicePrint(IterableUtils.toString(TINY_LIMIT, deltaCharacter(cs))));
        }
    }
}
