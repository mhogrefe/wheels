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
            String listString = tail(init(is.toString()));
            System.out.println("toList(" + listString + ") = " + toList(is));
        }
    }

    private void demoToList_String() {
        for (String s : take(LIMIT, P.strings())) {
            System.out.println("toList(" + s + ") = " + toList(s));
        }
    }

    private void demoToString_Iterable() {
        for (List<Integer> is : take(LIMIT, P.lists(P.withNull(P.integers())))) {
            String listString = tail(init(is.toString()));
            System.out.println("toString(" + listString + ") = " + IterableUtils.toString(is));
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
            String listString = tail(init(cs.toString()));
            System.out.println("charsToString(" + listString + ") = " + charsToString(cs));
        }
    }

    private void demoRangeUp_byte() {
        for (byte b : take(LIMIT, P.bytes())) {
            System.out.println("range(" + b + ") = " + toList(rangeUp(b)));
        }
    }

    private void demoRangeUp_short() {
        for (short s : take(LIMIT, P.shorts())) {
            System.out.println("range(" + s + ") = " + IterableUtils.toString(TINY_LIMIT, rangeUp(s)));
        }
    }

    private void demoRangeUp_int() {
        for (int i : take(LIMIT, P.integers())) {
            System.out.println("range(" + i + ") = " + IterableUtils.toString(TINY_LIMIT, rangeUp(i)));
        }
    }

    private void demoRangeUp_long() {
        for (long l : take(LIMIT, P.longs())) {
            System.out.println("range(" + l + ") = " + IterableUtils.toString(TINY_LIMIT, rangeUp(l)));
        }
    }

    private void demoRangeUp_BigInteger() {
        for (BigInteger i : take(LIMIT, P.bigIntegers())) {
            System.out.println("range(" + i + ") = " + IterableUtils.toString(TINY_LIMIT, rangeUp(i)));
        }
    }

    private void demoRangeUp_BigDecimal() {
        for (BigDecimal bd : take(LIMIT, P.bigDecimals())) {
            System.out.println("range(" + bd + ") = " + IterableUtils.toString(TINY_LIMIT, rangeUp(bd)));
        }
    }

    private void demoRangeUp_char() {
        for (char c : take(LIMIT, P.characters())) {
            System.out.println("range(" + c + ") = " + IterableUtils.toString(TINY_LIMIT, rangeUp(c)));
        }
    }

    private void demoRangeUp_float() {
        for (float f : take(LIMIT, filter(g -> !Float.isNaN(g), P.floats()))) {
            System.out.println("range(" + f + ") = " + IterableUtils.toString(TINY_LIMIT, rangeUp(f)));
        }
    }

    private void demoRangeUp_double() {
        for (double d : take(LIMIT, filter(e -> !Double.isNaN(e), P.doubles()))) {
            System.out.println("range(" + d + ") = " + IterableUtils.toString(TINY_LIMIT, rangeUp(d)));
        }
    }

    private void demoRange_byte_byte() {
        for (Pair<Byte, Byte> p : take(LIMIT, P.pairs(P.bytes()))) {
            System.out.println("range(" + p.a + ", " + p.b + ") = " +
                    IterableUtils.toString(TINY_LIMIT, range(p.a, p.b)));
        }
    }

    private void demoRange_short_short() {
        for (Pair<Short, Short> p : take(LIMIT, P.pairs(P.shorts()))) {
            System.out.println("range(" + p.a + ", " + p.b + ") = " +
                    IterableUtils.toString(TINY_LIMIT, range(p.a, p.b)));
        }
    }

    private void demoRange_int_int() {
        for (Pair<Integer, Integer> p : take(LIMIT, P.pairs(P.integers()))) {
            System.out.println("range(" + p.a + ", " + p.b + ") = " +
                    IterableUtils.toString(TINY_LIMIT, range(p.a, p.b)));
        }
    }

    private void demoRange_long_long() {
        for (Pair<Long, Long> p : take(LIMIT, P.pairs(P.longs()))) {
            System.out.println("range(" + p.a + ", " + p.b + ") = " +
                    IterableUtils.toString(TINY_LIMIT, range(p.a, p.b)));
        }
    }

    private void demoRange_BigInteger_BigInteger() {
        for (Pair<BigInteger, BigInteger> p : take(LIMIT, P.pairs(P.bigIntegers()))) {
            System.out.println("range(" + p.a + ", " + p.b + ") = " +
                    IterableUtils.toString(TINY_LIMIT, range(p.a, p.b)));
        }
    }

    private void demoRange_BigDecimal_BigDecimal() {
        for (Pair<BigDecimal, BigDecimal> p : take(LIMIT, P.pairs(P.bigDecimals()))) {
            System.out.println("range(" + p.a + ", " + p.b + ") = " +
                    IterableUtils.toString(TINY_LIMIT, range(p.a, p.b)));
        }
    }

    private void demoRange_char_char() {
        for (Pair<Character, Character> p : take(LIMIT, P.pairs(P.characters()))) {
            System.out.println("range(" + p.a + ", " + p.b + ") = " +
                    IterableUtils.toString(TINY_LIMIT, range(p.a, p.b)));
        }
    }

    private void demoRange_float_float() {
        Iterable<Pair<Float, Float>> ps = P.pairs(filter(f -> !Float.isNaN(f), P.floats()));
        for (Pair<Float, Float> p : take(LIMIT, ps)) {
            System.out.println("range(" + p.a + ", " + p.b + ") = " +
                    IterableUtils.toString(TINY_LIMIT, range(p.a, p.b)));
        }
    }

    private void demoRange_double_double() {
        Iterable<Pair<Double, Double>> ps = P.pairs(filter(d -> !Double.isNaN(d), P.doubles()));
        for (Pair<Double, Double> p : take(LIMIT, ps)) {
            System.out.println("range(" + p.a + ", " + p.b + ") = " +
                    IterableUtils.toString(TINY_LIMIT, range(p.a, p.b)));
        }
    }

    private void demoUnrepeat() {
        for (List<Integer> is : take(LIMIT, P.lists(P.withNull(P.integers())))) {
            String listString = tail(init(is.toString()));
            System.out.println("unrepeat(" + listString + ") = " + unrepeat(is));
        }
    }

    private void demoSumByte() {
        for (List<Byte> bs : take(LIMIT, P.lists(P.bytes()))) {
            String listString = tail(init(bs.toString()));
            System.out.println("Σ(" + listString + ") = " + sumByte(bs));
        }
    }

    private void demoSumShort() {
        for (List<Short> ss : take(LIMIT, P.lists(P.shorts()))) {
            String listString = tail(init(ss.toString()));
            System.out.println("Σ(" + listString + ") = " + sumShort(ss));
        }
    }

    private void demoSumInteger() {
        for (List<Integer> is : take(LIMIT, P.lists(P.integers()))) {
            String listString = tail(init(is.toString()));
            System.out.println("Σ(" + listString + ") = " + sumInteger(is));
        }
    }

    private void demoSumLong() {
        for (List<Long> ls : take(LIMIT, P.lists(P.longs()))) {
            String listString = tail(init(ls.toString()));
            System.out.println("Σ(" + listString + ") = " + sumLong(ls));
        }
    }

    private void demoSumFloat() {
        for (List<Float> fs : take(LIMIT, P.lists(P.floats()))) {
            String listString = tail(init(fs.toString()));
            System.out.println("Σ(" + listString + ") = " + sumFloat(fs));
        }
    }

    private void demoSumDouble() {
        for (List<Double> ds : take(LIMIT, P.lists(P.doubles()))) {
            String listString = tail(init(ds.toString()));
            System.out.println("Σ(" + listString + ") = " + sumDouble(ds));
        }
    }

    private void demoSumBigInteger() {
        for (List<BigInteger> is : take(LIMIT, P.lists(P.bigIntegers()))) {
            String listString = tail(init(is.toString()));
            System.out.println("Σ(" + listString + ") = " + sumBigInteger(is));
        }
    }

    private void demoSumBigDecimal() {
        for (List<BigDecimal> bds : take(LIMIT, P.lists(P.bigDecimals()))) {
            String listString = tail(init(bds.toString()));
            System.out.println("Σ(" + listString + ") = " + sumBigDecimal(bds));
        }
    }

    private void demoProductByte() {
        for (List<Byte> bs : take(LIMIT, P.lists(P.bytes()))) {
            String listString = tail(init(bs.toString()));
            System.out.println("Π(" + listString + ") = " + productByte(bs));
        }
    }

    private void demoProductShort() {
        for (List<Short> ss : take(LIMIT, P.lists(P.shorts()))) {
            String listString = tail(init(ss.toString()));
            System.out.println("Π(" + listString + ") = " + productShort(ss));
        }
    }

    private void demoProductInteger() {
        for (List<Integer> is : take(LIMIT, P.lists(P.integers()))) {
            String listString = tail(init(is.toString()));
            System.out.println("Π(" + listString + ") = " + productInteger(is));
        }
    }

    private void demoProductLong() {
        for (List<Long> ls : take(LIMIT, P.lists(P.longs()))) {
            String listString = tail(init(ls.toString()));
            System.out.println("Π(" + listString + ") = " + productLong(ls));
        }
    }

    private void demoProductFloat() {
        for (List<Float> fs : take(LIMIT, P.lists(P.floats()))) {
            String listString = tail(init(fs.toString()));
            System.out.println("Π(" + listString + ") = " + productFloat(fs));
        }
    }

    private void demoProductDouble() {
        for (List<Double> ds : take(LIMIT, P.lists(P.doubles()))) {
            String listString = tail(init(ds.toString()));
            System.out.println("Π(" + listString + ") = " + productDouble(ds));
        }
    }

    private void demoProductBigInteger() {
        for (List<BigInteger> is : take(LIMIT, P.lists(P.bigIntegers()))) {
            String listString = tail(init(is.toString()));
            System.out.println("Π(" + listString + ") = " + productBigInteger(is));
        }
    }

    private void demoProductBigDecimal() {
        for (List<BigDecimal> bds : take(LIMIT, P.lists(P.bigDecimals()))) {
            String listString = tail(init(bds.toString()));
            System.out.println("Π(" + listString + ") = " + productBigDecimal(bds));
        }
    }

    private void demoSumSignBigInteger() {
        for (List<BigInteger> is : take(LIMIT, P.withScale(4).lists(P.bigIntegers()))) {
            String listString = tail(init(is.toString()));
            System.out.println("sumSignBigInteger(" + listString + ") = " + sumSignBigInteger(is));
        }
    }

    private void demoSumSignBigDecimal() {
        for (List<BigDecimal> bds : take(LIMIT, P.withScale(4).lists(P.bigDecimals()))) {
            String listString = tail(init(bds.toString()));
            System.out.println("sumSignBigDecimal(" + listString + ") = " + sumSignBigDecimal(bds));
        }
    }

    private void demoDeltaByte_finite() {
        for (List<Byte> bs : take(LIMIT, P.listsAtLeast(1, P.bytes()))) {
            String listString = tail(init(bs.toString()));
            System.out.println("Δ(" + listString + ") = " + IterableUtils.toString(TINY_LIMIT, deltaByte(bs)));
        }
    }

    private void demoDeltaByte_infinite() {
        for (Iterable<Byte> bs : take(MEDIUM_LIMIT, P.prefixPermutations(EP.bytes()))) {
            String listString = tail(init(IterableUtils.toString(TINY_LIMIT, bs)));
            System.out.println("Δ(" + listString + ") = " + IterableUtils.toString(TINY_LIMIT, deltaByte(bs)));
        }
    }

    private void demoDeltaShort_finite() {
        for (List<Short> ss : take(LIMIT, P.listsAtLeast(1, P.shorts()))) {
            String listString = tail(init(ss.toString()));
            System.out.println("Δ(" + listString + ") = " + IterableUtils.toString(TINY_LIMIT, deltaShort(ss)));
        }
    }

    private void demoDeltaShort_infinite() {
        for (Iterable<Short> ss : take(MEDIUM_LIMIT, P.prefixPermutations(EP.shorts()))) {
            String listString = tail(init(IterableUtils.toString(TINY_LIMIT, ss)));
            System.out.println("Δ(" + listString + ") = " + IterableUtils.toString(TINY_LIMIT, deltaShort(ss)));
        }
    }

    private void demoDeltaInteger_finite() {
        for (List<Integer> is : take(LIMIT, P.listsAtLeast(1, P.integers()))) {
            String listString = tail(init(is.toString()));
            System.out.println("Δ(" + listString + ") = " + IterableUtils.toString(TINY_LIMIT, deltaInteger(is)));
        }
    }

    private void demoDeltaInteger_infinite() {
        for (Iterable<Integer> is : take(MEDIUM_LIMIT, P.prefixPermutations(EP.integers()))) {
            String listString = tail(init(IterableUtils.toString(TINY_LIMIT, is)));
            System.out.println("Δ(" + listString + ") = " + IterableUtils.toString(TINY_LIMIT, deltaInteger(is)));
        }
    }

    private void demoDeltaLong_finite() {
        for (List<Long> ls : take(LIMIT, P.listsAtLeast(1, P.longs()))) {
            String listString = tail(init(ls.toString()));
            System.out.println("Δ(" + listString + ") = " + IterableUtils.toString(TINY_LIMIT, deltaLong(ls)));
        }
    }

    private void demoDeltaLong_infinite() {
        for (Iterable<Long> ls : take(MEDIUM_LIMIT, P.prefixPermutations(EP.longs()))) {
            String listString = tail(init(IterableUtils.toString(TINY_LIMIT, ls)));
            System.out.println("Δ(" + listString + ") = " + IterableUtils.toString(TINY_LIMIT, deltaLong(ls)));
        }
    }

    private void demoDeltaBigInteger_finite() {
        for (List<BigInteger> is : take(LIMIT, P.listsAtLeast(1, P.bigIntegers()))) {
            String listString = tail(init(is.toString()));
            System.out.println("Δ(" + listString + ") = " + IterableUtils.toString(TINY_LIMIT, deltaBigInteger(is)));
        }
    }

    private void demoDeltaBigInteger_infinite() {
        for (Iterable<BigInteger> is : take(MEDIUM_LIMIT, P.prefixPermutations(EP.bigIntegers()))) {
            String listString = tail(init(IterableUtils.toString(TINY_LIMIT, is)));
            System.out.println("Δ(" + listString + ") = " + IterableUtils.toString(TINY_LIMIT, deltaBigInteger(is)));
        }
    }

    private void demoDeltaBigDecimal_finite() {
        for (List<BigDecimal> bds : take(LIMIT, P.listsAtLeast(1, P.bigDecimals()))) {
            String listString = tail(init(bds.toString()));
            System.out.println("Δ(" + listString + ") = " + IterableUtils.toString(TINY_LIMIT, deltaBigDecimal(bds)));
        }
    }

    private void demoDeltaBigDecimal_infinite() {
        for (Iterable<BigDecimal> bds : take(SMALL_LIMIT, P.prefixPermutations(EP.bigDecimals()))) {
            String listString = tail(init(IterableUtils.toString(TINY_LIMIT, bds)));
            System.out.println("Δ(" + listString + ") = " + IterableUtils.toString(TINY_LIMIT, deltaBigDecimal(bds)));
        }
    }

    private void demoDeltaFloat_finite() {
        for (List<Float> fs : take(LIMIT, P.listsAtLeast(1, P.floats()))) {
            String listString = tail(init(fs.toString()));
            System.out.println("Δ(" + listString + ") = " + IterableUtils.toString(TINY_LIMIT, deltaFloat(fs)));
        }
    }

    private void demoDeltaFloat_infinite() {
        for (Iterable<Float> fs : take(MEDIUM_LIMIT, P.prefixPermutations(EP.floats()))) {
            String listString = tail(init(IterableUtils.toString(TINY_LIMIT, fs)));
            System.out.println("Δ(" + listString + ") = " + IterableUtils.toString(TINY_LIMIT, deltaFloat(fs)));
        }
    }

    private void demoDeltaDouble_finite() {
        for (List<Double> ds : take(LIMIT, P.listsAtLeast(1, P.doubles()))) {
            String listString = tail(init(ds.toString()));
            System.out.println("Δ(" + listString + ") = " + IterableUtils.toString(TINY_LIMIT, deltaDouble(ds)));
        }
    }

    private void demoDeltaDouble_infinite() {
        for (Iterable<Double> ds : take(MEDIUM_LIMIT, P.prefixPermutations(EP.doubles()))) {
            String listString = tail(init(IterableUtils.toString(TINY_LIMIT, ds)));
            System.out.println("Δ(" + listString + ") = " + IterableUtils.toString(TINY_LIMIT, deltaDouble(ds)));
        }
    }

    private void demoDeltaCharacter_finite() {
        for (List<Character> cs : take(LIMIT, P.listsAtLeast(1, P.characters()))) {
            String listString = tail(init(cs.toString()));
            System.out.println("Δ(" + listString + ") = " + IterableUtils.toString(TINY_LIMIT, deltaCharacter(cs)));
        }
    }

    private void demoDeltaCharacter_infinite() {
        for (Iterable<Character> cs : take(MEDIUM_LIMIT, P.prefixPermutations(EP.characters()))) {
            String listString = tail(init(IterableUtils.toString(TINY_LIMIT, cs)));
            System.out.println("Δ(" + nicePrint(listString) + ") = " +
                    nicePrint(IterableUtils.toString(TINY_LIMIT, deltaCharacter(cs))));
        }
    }
}
