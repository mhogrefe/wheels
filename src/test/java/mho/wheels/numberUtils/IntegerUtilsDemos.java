package mho.wheels.numberUtils;

import mho.wheels.iterables.ExhaustiveProvider;
import mho.wheels.iterables.RandomProvider;
import mho.wheels.structures.Pair;
import mho.wheels.structures.Triple;
import mho.wheels.testing.Demos;

import java.math.BigInteger;
import java.util.List;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.numberUtils.IntegerUtils.*;
import static mho.wheels.numberUtils.IntegerUtils.demux;
import static mho.wheels.numberUtils.IntegerUtils.mux;
import static mho.wheels.ordering.Ordering.lt;
import static mho.wheels.testing.Testing.MEDIUM_LIMIT;

@SuppressWarnings("UnusedDeclaration")
public class IntegerUtilsDemos extends Demos {
    public IntegerUtilsDemos(boolean useRandom) {
        super(useRandom);
    }

    private void demoIsPowerOfTwo_int() {
        for (int i : take(LIMIT, P.positiveIntegers())) {
            System.out.println(i + " is " + (isPowerOfTwo(i) ? "" : "not ") + "a power of two");
        }
    }

    private void demoIsPowerOfTwo_long() {
        for (long l : take(LIMIT, P.positiveLongs())) {
            System.out.println(l + " is " + (isPowerOfTwo(l) ? "" : "not ") + "a power of two");
        }
    }

    private void demoIsPowerOfTwo_BigInteger() {
        for (BigInteger i : take(LIMIT, P.positiveBigIntegers())) {
            System.out.println(i + " is " + (isPowerOfTwo(i) ? "" : "not ") + "a power of two");
        }
    }

    private void demoCeilingLog2_int() {
        for (int i : take(LIMIT, P.positiveIntegers())) {
            System.out.println("ceilingLog2(" + i + ") = " + ceilingLog2(i));
        }
    }

    private void demoCeilingLog2_long() {
        for (long l : take(LIMIT, P.positiveLongs())) {
            System.out.println("ceilingLog2(" + l + ") = " + ceilingLog2(l));
        }
    }

    private void demoCeilingLog2_BigInteger() {
        for (BigInteger i : take(LIMIT, P.positiveBigIntegers())) {
            System.out.println("ceilingLog2(" + i + ") = " + ceilingLog2(i));
        }
    }

    private void demoBits_int() {
        for (int i : take(LIMIT, P.naturalIntegers())) {
            System.out.println("bits(" + i + ") = " + bits(i));
        }
    }

    private void demoBits_BigInteger() {
        for (BigInteger i : take(LIMIT, P.naturalBigIntegers())) {
            System.out.println("bits(" + i + ") = " + bits(i));
        }
    }

    private void demoBitsPadded_int_int() {
        Iterable<Pair<Integer, Integer>> ps = P.pairsSquareRootOrder(
                P.naturalIntegers(),
                P.naturalIntegersGeometric()
        );
        for (Pair<Integer, Integer> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("bitsPadded(" + p.b + ", " + p.a + ") = " + bitsPadded(p.b, p.a));
        }
    }

    private void demoBitsPadded_int_BigInteger() {
        Iterable<Pair<BigInteger, Integer>> ps = P.pairsSquareRootOrder(
                P.naturalBigIntegers(),
                P.naturalIntegersGeometric()
        );
        for (Pair<BigInteger, Integer> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("bitsPadded(" + p.b + ", " + p.a + ") = " + bitsPadded(p.b, p.a));
        }
    }

    private void demoBigEndianBits_int() {
        for (int i : take(LIMIT, P.naturalIntegers())) {
            System.out.println("bigEndianBits(" + i + ") = " + bigEndianBits(i));
        }
    }

    private void demoBigEndianBits_BigInteger() {
        for (BigInteger i : take(LIMIT, P.naturalBigIntegers())) {
            System.out.println("bigEndianBits(" + i + ") = " + bigEndianBits(i));
        }
    }

    private void demoBigEndianBitsPadded_int_int() {
        Iterable<Pair<Integer, Integer>> ps = P.pairsSquareRootOrder(
                P.naturalIntegers(),
                P.naturalIntegersGeometric()
        );
        for (Pair<Integer, Integer> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("bigEndianBitsPadded(" + p.b + ", " + p.a + ") = " + bigEndianBitsPadded(p.b, p.a));
        }
    }

    private void demoBigEndianBitsPadded_int_BigInteger() {
        Iterable<Pair<BigInteger, Integer>> ps = P.pairsSquareRootOrder(
                P.naturalBigIntegers(),
                P.naturalIntegersGeometric()
        );
        for (Pair<BigInteger, Integer> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("bigEndianBitsPadded(" + p.b + ", " + p.a + ") = " + bigEndianBitsPadded(p.b, p.a));
        }
    }

    private void demoFromBits() {
        for (List<Boolean> bs : take(LIMIT, P.lists(P.booleans()))) {
            String listString = tail(init(bs.toString()));
            System.out.println("fromBits(" + listString + ") = " + fromBits(bs));
        }
    }

    private void demoFromBigEndianBits() {
        for (List<Boolean> bs : take(LIMIT, P.lists(P.booleans()))) {
            String listString = tail(init(bs.toString()));
            System.out.println("fromBigEndianBits(" + listString + ") = " + fromBigEndianBits(bs));
        }
    }

    private void demoDigits_int_int() {
        Iterable<Pair<Integer, Integer>> ps = P.pairsSquareRootOrder(P.naturalIntegers(), P.rangeUpGeometric(2));
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            System.out.println("digits(" + p.b + ", " + p.a + ") = " + toList(digits(p.b, p.a)));
        }
    }

    private void demoDigits_BigInteger_BigInteger() {
        //noinspection Convert2MethodRef
        Iterable<Pair<BigInteger, BigInteger>> ps = P.pairsSquareRootOrder(
                P.naturalBigIntegers(),
                map(i -> BigInteger.valueOf(i), P.rangeUpGeometric(2))
        );
        for (Pair<BigInteger, BigInteger> p : take(LIMIT, ps)) {
            System.out.println("digits(" + p.b + ", " + p.a + ") = " + toList(digits(p.b, p.a)));
        }
    }

    private void demoDigitsPadded_int_int_int() {
        Iterable<Triple<Integer, Integer, Integer>> ts = P.triples(
                P.naturalIntegersGeometric(),
                P.rangeUpGeometric(2),
                P.naturalIntegers()
        );
        for (Triple<Integer, Integer, Integer> t : take(LIMIT, ts)) {
            System.out.println("digitsPadded(" + t.a + ", " + t.b + ", " + t.c + ") = " + digitsPadded(t.a, t.b, t.c));
        }
    }

    private void demoDigitsPadded_int_BigInteger_BigInteger() {
        //noinspection Convert2MethodRef
        Iterable<Triple<Integer, BigInteger, BigInteger>> ts = P.triples(
                P.naturalIntegersGeometric(),
                map(i -> BigInteger.valueOf(i), P.rangeUpGeometric(2)),
                P.naturalBigIntegers()
        );
        for (Triple<Integer, BigInteger, BigInteger> t : take(LIMIT, ts)) {
            System.out.println("digitsPadded(" + t.a + ", " + t.b + ", " + t.c + ") = " + digitsPadded(t.a, t.b, t.c));
        }
    }

    private void demoBigEndianDigits_int_int() {
        Iterable<Pair<Integer, Integer>> ps = P.pairsSquareRootOrder(P.naturalIntegers(), P.rangeUpGeometric(2));
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            System.out.println("bigEndianDigits(" + p.b + ", " + p.a + ") = " + bigEndianDigits(p.b, p.a));
        }
    }

    private void demoBigEndianDigits_BigInteger_BigInteger() {
        //noinspection Convert2MethodRef
        Iterable<Pair<BigInteger, BigInteger>> ps = P.pairsSquareRootOrder(
                P.naturalBigIntegers(),
                map(i -> BigInteger.valueOf(i), P.rangeUpGeometric(2))
        );
        for (Pair<BigInteger, BigInteger> p : take(LIMIT, ps)) {
            System.out.println("bigEndianDigits(" + p.b + ", " + p.a + ") = " + bigEndianDigits(p.b, p.a));
        }
    }

    private void demoBigEndianDigitsPadded_int_int_int() {
        Iterable<Triple<Integer, Integer, Integer>> ts = P.triples(
                P.naturalIntegersGeometric(),
                P.rangeUpGeometric(2),
                P.naturalIntegers()
        );
        for (Triple<Integer, Integer, Integer> t : take(LIMIT, ts)) {
            System.out.println("bigEndianDigitsPadded(" + t.a + ", " + t.b + ", " + t.c + ") = " +
                    bigEndianDigitsPadded(t.a, t.b, t.c));
        }
    }

    private void demoBigEndianDigitsPadded_BigInteger_BigInteger_BigInteger() {
        //noinspection Convert2MethodRef
        Iterable<Triple<Integer, BigInteger, BigInteger>> ts = P.triples(
                P.naturalIntegersGeometric(),
                map(i -> BigInteger.valueOf(i), P.rangeUpGeometric(2)),
                P.naturalBigIntegers()
        );
        for (Triple<Integer, BigInteger, BigInteger> t : take(LIMIT, ts)) {
            System.out.println("bigEndianDigitsPadded(" + t.a + ", " + t.b + ", " + t.c + ") = " +
                    bigEndianDigitsPadded(t.a, t.b, t.c));
        }
    }

    private void demoFromDigits_int_Iterable_Integer() {
        Iterable<Pair<List<Integer>, Integer>> ps = filterInfinite(
                p -> all(i -> i < p.b, p.a),
                P.pairsLogarithmicOrder(P.lists(P.naturalIntegersGeometric()), P.rangeUpGeometric(2))
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            System.out.println("fromDigits(" + p.b + ", " + p.a + ") = " + fromDigits(p.b, p.a));
        }
    }

    private void demoFromDigits_BigInteger_Iterable_BigInteger() {
        //noinspection Convert2MethodRef
        Iterable<Pair<List<BigInteger>, BigInteger>> ps = filterInfinite(
                p -> all(i -> lt(i, p.b), p.a),
                P.pairsLogarithmicOrder(
                        P.lists(map(i -> BigInteger.valueOf(i), P.naturalIntegersGeometric())),
                        map(i -> BigInteger.valueOf(i), P.rangeUpGeometric(2))
                )
        );
        for (Pair<List<BigInteger>, BigInteger> p : take(LIMIT, ps)) {
            System.out.println("fromDigits(" + p.b + ", " + p.a + ") = " + fromDigits(p.b, p.a));
        }
    }

    private void demoFromBigEndianDigits_int_Iterable_Integer() {
        Iterable<Pair<List<Integer>, Integer>> ps = filterInfinite(
                p -> all(i -> i < p.b, p.a),
                P.pairsLogarithmicOrder(P.lists(P.naturalIntegersGeometric()), P.rangeUpGeometric(2))
        );
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            System.out.println("fromBigEndianDigits(" + p.b + ", " + p.a + ") = " + fromBigEndianDigits(p.b, p.a));
        }
    }

    private void demoFromBigEndianDigits_BigInteger_Iterable_BigInteger() {
        //noinspection Convert2MethodRef
        Iterable<Pair<List<BigInteger>, BigInteger>> ps = filterInfinite(
                p -> all(i -> lt(i, p.b), p.a),
                P.pairsLogarithmicOrder(
                        P.lists(map(i -> BigInteger.valueOf(i), P.naturalIntegersGeometric())),
                        map(i -> BigInteger.valueOf(i), P.rangeUpGeometric(2))
                )
        );
        for (Pair<List<BigInteger>, BigInteger> p : take(LIMIT, ps)) {
            System.out.println("fromBigEndianDigits(" + p.b + ", " + p.a + ") = " + fromBigEndianDigits(p.b, p.a));
        }
    }

    private void demoToDigit() {
        for (int i : take(LIMIT, P.range(0, 35))) {
            System.out.println("toDigit(" + i + ") = " + toDigit(i));
        }
    }

    private void demoFromDigit() {
        for (char c : take(LIMIT, P.withScale(1).choose(P.range('0', '9'), P.range('A', 'Z')))) {
            System.out.println("fromDigit(" + c + ") = " + fromDigit(c));
        }
    }

    private void demoToStringBase_int_int() {
        Iterable<Pair<Integer, Integer>> ps;
        if (P instanceof ExhaustiveProvider) {
            ps = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.integers(), P.rangeUp(2));
        } else {
            ps = P.pairs(P.integers(), map(i -> i + 2, P.withScale(20).naturalIntegersGeometric()));
        }
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            System.out.println("toStringBase(" + p.b + ", " + p.a + ") = " + toStringBase(p.b, p.a));
        }
    }

    private void demoToStringBase_BigInteger_BigInteger() {
        Iterable<Pair<BigInteger, BigInteger>> ps;
        if (P instanceof ExhaustiveProvider) {
            ps = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.bigIntegers(), P.rangeUp(TWO));
        } else {
            ps = P.pairs(
                    P.bigIntegers(),
                    map(i -> BigInteger.valueOf(i + 2), P.withScale(20).naturalIntegersGeometric())
            );
        }
        for (Pair<BigInteger, BigInteger> p : take(LIMIT, ps)) {
            System.out.println("toStringBase(" + p.b + ", " + p.a + ") = " + toStringBase(p.b, p.a));
        }
    }

    private void demoFromStringBase_int_String() {
        Iterable<Pair<Integer, String>> ps = map(
                p -> new Pair<>(p.a, toStringBase(BigInteger.valueOf(p.a), p.b)),
                P.pairs(P.rangeUpGeometric(2), P.bigIntegers())
        );
        for (Pair<Integer, String> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("fromStringBase(" + p.a + ", " + p.b + ") = " + fromStringBase(p.a, p.b));
        }
    }

    private void demoFromStringBase_BigInteger_String() {
        Iterable<Pair<BigInteger, String>> ps = map(
                p -> new Pair<>(p.a, toStringBase(p.a, p.b)),
                P.pairs(P.rangeUp(TWO), P.bigIntegers())
        );
        for (Pair<BigInteger, String> p : take(MEDIUM_LIMIT, ps)) {
            System.out.println("fromStringBase(" + p.a + ", " + p.b + ") = " + fromStringBase(p.a, p.b));
        }
    }

    private void demoLogarithmicMux() {
        Iterable<Pair<BigInteger, BigInteger>> ps;
        if (P instanceof ExhaustiveProvider) {
            ps = P.pairs(P.naturalBigIntegers());
        } else {
            //noinspection Convert2MethodRef
            ps = P.pairs(
                    P.naturalBigIntegers(),
                    map(i -> BigInteger.valueOf(i), P.withScale(20).naturalIntegersGeometric())
            );
        }
        for (Pair<BigInteger, BigInteger> p : take(LIMIT, ps)) {
            System.out.println("logarithmicMux(" + p.a + ", " + p.b + ") = " + logarithmicMux(p.a, p.b));
        }
    }

    private void demoLogarithmicDemux() {
        for (BigInteger i : take(LIMIT, P.naturalBigIntegers())) {
            System.out.println("logarithmicDemux(" + i + ") = " + logarithmicDemux(i));
        }
    }

    private void demoSquareRootMux() {
        for (Pair<BigInteger, BigInteger> p : take(LIMIT, P.pairs(P.naturalBigIntegers()))) {
            System.out.println("squareRootMux(" + p.a + ", " + p.b + ") = " + squareRootMux(p.a, p.b));
        }
    }

    private void demoSquareRootDemux() {
        for (BigInteger i : take(LIMIT, P.naturalBigIntegers())) {
            System.out.println("squareRootDemux(" + i + ") = " + squareRootDemux(i));
        }
    }

    private void demoMux() {
        for (List<BigInteger> is : take(LIMIT, P.lists(P.naturalBigIntegers()))) {
            String listString = tail(init(is.toString()));
            System.out.println("mux(" + listString + ") = " + mux(is));
        }
    }

    private void demoDemux() {
        Iterable<Pair<BigInteger, Integer>> ps;
        Pair<BigInteger, Integer> zeroPair = new Pair<>(BigInteger.ZERO, 0);
        if (P instanceof ExhaustiveProvider) {
            ps = cons(
                    zeroPair,
                    ((ExhaustiveProvider) P).pairsLogarithmicOrder(P.naturalBigIntegers(), P.positiveIntegers())
            );
        } else {
            ps = ((RandomProvider) P).withElement(
                    zeroPair,
                    P.pairs(P.naturalBigIntegers(), P.withScale(20).positiveIntegersGeometric())
            );
        }
        for (Pair<BigInteger, Integer> p : take(LIMIT, ps)) {
            System.out.println("demux(" + p.b + ", " + p.a + ") = " + demux(p.b, p.a));
        }
    }
}
