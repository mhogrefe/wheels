package mho.wheels.math;

import mho.wheels.iterables.ExhaustiveProvider;
import mho.wheels.iterables.IterableProvider;
import mho.wheels.iterables.RandomProvider;
import mho.wheels.structures.Pair;
import mho.wheels.structures.Triple;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.math.MathUtils.*;
import static mho.wheels.ordering.Ordering.*;
import static mho.wheels.ordering.Ordering.lt;

public class MathUtilsDemos {
    private static final boolean USE_RANDOM = false;
    private static int LIMIT;
    private static IterableProvider P;

    private static void initialize() {
        if (USE_RANDOM) {
            P = new RandomProvider(new Random(0x6af477d9a7e54fcaL));
            LIMIT = 1000;
        } else {
            P = ExhaustiveProvider.INSTANCE;
            LIMIT = 10000;
        }
    }

    private static void demoGcd_int_int() {
        initialize();
        Iterable<Pair<Integer, Integer>> ps = filter(p -> p.a != 0 || p.b != 0, P.pairs(P.integers()));
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            System.out.println("gcd(" + p.a + ", " + p.b + ") = " + gcd(p.a, p.b));
        }
    }

    private static void demoGcd_long_long() {
        initialize();
        Iterable<Pair<Long, Long>> ps = filter(p -> p.a != 0 || p.b != 0, P.pairs(P.longs()));
        for (Pair<Long, Long> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            System.out.println("gcd(" + p.a + ", " + p.b + ") = " + gcd(p.a, p.b));
        }
    }

    private static void demoBits_int() {
        initialize();
        for (int i : take(LIMIT, P.naturalIntegers())) {
            System.out.println("bits(" + i + ") = " + toList(bits(i)));
        }
    }

    private static void demoBits_BigInteger() {
        initialize();
        for (BigInteger i : take(LIMIT, P.naturalBigIntegers())) {
            System.out.println("bits(" + i + ") = " + toList(bits(i)));
        }
    }

    private static void demoBitsPadded_int_int() {
        initialize();
        Iterable<Pair<Integer, Integer>> ps;
        if (P instanceof ExhaustiveProvider) {
            ps = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.naturalIntegers());
        } else {
            ps = P.pairs(P.naturalIntegers(), ((RandomProvider) P).naturalIntegersGeometric(20));
        }
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            System.out.println("bitsPadded(" + p.b + ", " + p.a + ") = " + toList(bitsPadded(p.b, p.a)));
        }
    }

    private static void demoBitsPadded_int_BigInteger() {
        initialize();
        Iterable<Pair<BigInteger, Integer>> ps;
        if (P instanceof ExhaustiveProvider) {
            ps = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.naturalBigIntegers(), P.naturalIntegers());
        } else {
            ps = P.pairs(P.naturalBigIntegers(), ((RandomProvider) P).naturalIntegersGeometric(20));
        }
        for (Pair<BigInteger, Integer> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            System.out.println("bitsPadded(" + p.b + ", " + p.a + ") = " + toList(bitsPadded(p.b, p.a)));
        }
    }

    private static void demoBigEndianBits_int() {
        initialize();
        for (int i : take(LIMIT, P.naturalIntegers())) {
            System.out.println("bigEndianBits(" + i + ") = " + toList(bigEndianBits(i)));
        }
    }

    private static void demoBigEndianBits_BigInteger() {
        initialize();
        for (BigInteger i : take(LIMIT, P.naturalBigIntegers())) {
            System.out.println("bigEndianBits(" + i + ") = " + toList(bigEndianBits(i)));
        }
    }

    private static void demoBigEndianBitsPadded_int_int() {
        initialize();
        Iterable<Pair<Integer, Integer>> ps;
        if (P instanceof ExhaustiveProvider) {
            ps = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.naturalIntegers());
        } else {
            ps = P.pairs(P.naturalIntegers(), ((RandomProvider) P).naturalIntegersGeometric(20));
        }
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            System.out.println("bigEndianBitsPadded(" + p.b + ", " + p.a + ") = " + toList(bigEndianBitsPadded(p.b, p.a)));
        }
    }

    private static void demoBigEndianBitsPadded_int_BigInteger() {
        initialize();
        Iterable<Pair<BigInteger, Integer>> ps;
        if (P instanceof ExhaustiveProvider) {
            ps = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.naturalBigIntegers(), P.naturalIntegers());
        } else {
            ps = P.pairs(P.naturalBigIntegers(), ((RandomProvider) P).naturalIntegersGeometric(20));
        }
        for (Pair<BigInteger, Integer> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            System.out.println("bigEndianBitsPadded(" + p.b + ", " + p.a + ") = " + toList(bigEndianBitsPadded(p.b, p.a)));
        }
    }

    private static void demoFromBits() {
        initialize();
        for (List<Boolean> bs : take(LIMIT, P.lists(P.booleans()))) {
            String listString = tail(init(bs.toString()));
            System.out.println("fromBits(" + listString + ") = " + fromBits(bs));
        }
    }

    private static void demoFromBigEndianBits() {
        initialize();
        for (List<Boolean> bs : take(LIMIT, P.lists(P.booleans()))) {
            String listString = tail(init(bs.toString()));
            System.out.println("fromBigEndianBits(" + listString + ") = " + fromBigEndianBits(bs));
        }
    }

    private static void demoDigits_int_int() {
        initialize();
        Iterable<Pair<Integer, Integer>> ps;
        if (P instanceof ExhaustiveProvider) {
            ps = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.naturalIntegers(), P.rangeUp(2));
        } else {
            ps = P.pairs(P.naturalIntegers(), map(i -> i + 2, ((RandomProvider) P).naturalIntegersGeometric(20)));
        }
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            System.out.println("digits(" + p.b + ", " + p.a + ") = " + toList(digits(p.b, p.a)));
        }
    }

    private static void demoDigits_BigInteger_BigInteger() {
        initialize();
        Iterable<Pair<BigInteger, BigInteger>> ps;
        if (P instanceof ExhaustiveProvider) {
            ps = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.naturalBigIntegers(), P.rangeUp(BigInteger.valueOf(2)));
        } else {
            ps = P.pairs(
                    P.naturalBigIntegers(),
                    map(i -> BigInteger.valueOf(i + 2), ((RandomProvider) P).naturalIntegersGeometric(20))
            );
        }
        for (Pair<BigInteger, BigInteger> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            System.out.println("digits(" + p.b + ", " + p.a + ") = " + toList(digits(p.b, p.a)));
        }
    }

    private static void demoDigitsPadded_int_int_int() {
        initialize();
        Iterable<Triple<Integer, Integer, Integer>> ts;
        if (P instanceof ExhaustiveProvider) {
            ts = map(
                    p -> {
                        assert p.a != null;
                        return new Triple<>(p.a.a, p.a.b, p.b);
                    },
                    (Iterable<Pair<Pair<Integer, Integer>, Integer>>) P.pairs(
                            P.pairs(P.naturalIntegers(), map(i -> i + 2, P.naturalIntegers())),
                            P.naturalIntegers()
                    )
            );
        } else {
            Iterable<Integer> is = ((RandomProvider) P).naturalIntegersGeometric(20);
            ts = P.triples(is, map(i -> i + 2, is), P.naturalIntegers());
        }
        for (Triple<Integer, Integer, Integer> t : take(LIMIT, ts)) {
            assert t.a != null;
            assert t.b != null;
            assert t.c != null;
            System.out.println("digitsPadded(" + t.a + ", " + t.b + ", " + t.c + ") = " +
                    toList(digitsPadded(t.a, t.b, t.c)));
        }
    }

    private static void demoDigitsPadded_BigInteger_BigInteger_BigInteger() {
        initialize();
        Iterable<Triple<BigInteger, BigInteger, BigInteger>> ts;
        if (P instanceof ExhaustiveProvider) {
            ts = map(
                    p -> {
                        assert p.a != null;
                        return new Triple<>(p.a.a, p.a.b, p.b);
                    },
                    (Iterable<Pair<Pair<BigInteger, BigInteger>, BigInteger>>) P.pairs(
                            P.pairs(
                                    P.naturalBigIntegers(),
                                    map(i -> i.add(BigInteger.valueOf(2)), P.naturalBigIntegers())
                            ),
                            P.naturalBigIntegers()
                    )
            );
        } else {
            Iterable<BigInteger> is = map(
                    i -> BigInteger.valueOf(i),
                    ((RandomProvider) P).naturalIntegersGeometric(20)
            );
            ts = P.triples(is, map(i -> i.add(BigInteger.valueOf(2)), is), P.naturalBigIntegers());
        }
        for (Triple<BigInteger, BigInteger, BigInteger> t : take(LIMIT, ts)) {
            assert t.a != null;
            assert t.b != null;
            assert t.c != null;
            System.out.println("digitsPadded(" + t.a + ", " + t.b + ", " + t.c + ") = " +
                    toList(digitsPadded(t.a, t.b, t.c)));
        }
    }

    private static void demoBigEndianDigits_int_int() {
        initialize();
        Iterable<Pair<Integer, Integer>> ps;
        if (P instanceof ExhaustiveProvider) {
            ps = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.naturalIntegers(), P.rangeUp(2));
        } else {
            ps = P.pairs(P.naturalIntegers(), map(i -> i + 2, ((RandomProvider) P).naturalIntegersGeometric(20)));
        }
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            System.out.println("bigEndianDigits(" + p.b + ", " + p.a + ") = " + bigEndianDigits(p.b, p.a));
        }
    }

    private static void demoBigEndianDigits_BigInteger_BigInteger() {
        initialize();
        Iterable<Pair<BigInteger, BigInteger>> ps;
        if (P instanceof ExhaustiveProvider) {
            ps = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.naturalBigIntegers(), P.rangeUp(BigInteger.valueOf(2)));
        } else {
            ps = P.pairs(
                    P.naturalBigIntegers(),
                    map(i -> BigInteger.valueOf(i + 2), ((RandomProvider) P).naturalIntegersGeometric(20))
            );
        }
        for (Pair<BigInteger, BigInteger> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            System.out.println("bigEndianDigits(" + p.b + ", " + p.a + ") = " + bigEndianDigits(p.b, p.a));
        }
    }

    private static void demoBigEndianDigitsPadded_int_int_int() {
        initialize();
        Iterable<Triple<Integer, Integer, Integer>> ts;
        if (P instanceof ExhaustiveProvider) {
            ts = map(
                    p -> {
                        assert p.a != null;
                        return new Triple<>(p.a.a, p.a.b, p.b);
                    },
                    (Iterable<Pair<Pair<Integer, Integer>, Integer>>) P.pairs(
                            P.pairs(P.naturalIntegers(), map(i -> i + 2, P.naturalIntegers())),
                            P.naturalIntegers()
                    )
            );
        } else {
            Iterable<Integer> is = ((RandomProvider) P).naturalIntegersGeometric(20);
            ts = P.triples(is, map(i -> i + 2, is), P.naturalIntegers());
        }
        for (Triple<Integer, Integer, Integer> t : take(LIMIT, ts)) {
            assert t.a != null;
            assert t.b != null;
            assert t.c != null;
            System.out.println("bigEndianDigitsPadded(" + t.a + ", " + t.b + ", " + t.c + ") = " +
                    toList(bigEndianDigitsPadded(t.a, t.b, t.c)));
        }
    }

    private static void demoBigEndianDigitsPadded_BigInteger_BigInteger_BigInteger() {
        initialize();
        Iterable<Triple<BigInteger, BigInteger, BigInteger>> ts;
        if (P instanceof ExhaustiveProvider) {
            ts = map(
                    p -> {
                        assert p.a != null;
                        return new Triple<>(p.a.a, p.a.b, p.b);
                    },
                    (Iterable<Pair<Pair<BigInteger, BigInteger>, BigInteger>>) P.pairs(
                            P.pairs(
                                    P.naturalBigIntegers(),
                                    map(i -> i.add(BigInteger.valueOf(2)), P.naturalBigIntegers())
                            ),
                            P.naturalBigIntegers()
                    )
            );
        } else {
            Iterable<BigInteger> is = map(
                    i -> BigInteger.valueOf(i),
                    ((RandomProvider) P).naturalIntegersGeometric(20)
            );
            ts = P.triples(is, map(i -> i.add(BigInteger.valueOf(2)), is), P.naturalBigIntegers());
        }
        for (Triple<BigInteger, BigInteger, BigInteger> t : take(LIMIT, ts)) {
            assert t.a != null;
            assert t.b != null;
            assert t.c != null;
            System.out.println("bigEndianDigitsPadded(" + t.a + ", " + t.b + ", " + t.c + ") = " +
                    toList(bigEndianDigitsPadded(t.a, t.b, t.c)));
        }
    }

    private static void demoFromBigEndianDigits_int_Iterable_Integer() {
        initialize();
        Iterable<Pair<List<Integer>, Integer>> unfilteredPs;
        if (P instanceof ExhaustiveProvider) {
            unfilteredPs = ((ExhaustiveProvider) P).pairsLogarithmicOrder(
                    P.lists(P.naturalIntegers()),
                    map(i -> i + 2, P.naturalIntegers())
            );
        } else {
            unfilteredPs = P.pairs(
                    P.lists(((RandomProvider) P).naturalIntegersGeometric(10)),
                    map(i -> i + 2, ((RandomProvider) P).naturalIntegersGeometric(20))
            );
        }
        Iterable<Pair<List<Integer>, Integer>> ps = filter(p -> all(i -> i < p.b, p.a), unfilteredPs);
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            System.out.println("fromBigEndianDigits(" + p.b + ", " + p.a + ") = " + fromBigEndianDigits(p.b, p.a));
        }
    }

    private static void demoFromBigEndianDigits_BigInteger_Iterable_BigInteger() {
        initialize();
        Iterable<Pair<List<BigInteger>, BigInteger>> unfilteredPs;
        if (P instanceof ExhaustiveProvider) {
            unfilteredPs = ((ExhaustiveProvider) P).pairsLogarithmicOrder(
                    P.lists(P.naturalBigIntegers()),
                    map(i -> i.add(BigInteger.valueOf(2)), P.naturalBigIntegers())
            );
        } else {
            unfilteredPs = P.pairs(
                    P.lists(map(i -> BigInteger.valueOf(i), ((RandomProvider) P).naturalIntegersGeometric(10))),
                    map(i -> BigInteger.valueOf(i + 2), ((RandomProvider) P).naturalIntegersGeometric(20))
            );
        }
        Iterable<Pair<List<BigInteger>, BigInteger>> ps = filter(p -> {
            assert p.a != null;
            return all((BigInteger i) -> lt(i, p.b), p.a);
        }, unfilteredPs);
        for (Pair<List<BigInteger>, BigInteger> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            System.out.println("fromBigEndianDigits(" + p.b + ", " + p.a + ") = " + fromBigEndianDigits(p.b, p.a));
        }
    }

    private static void demoFromDigits_int_Iterable_Integer() {
        initialize();
        Iterable<Pair<List<Integer>, Integer>> unfilteredPs;
        if (P instanceof ExhaustiveProvider) {
            unfilteredPs = ((ExhaustiveProvider) P).pairsLogarithmicOrder(P.lists(P.naturalIntegers()), P.rangeUp(2));
        } else {
            unfilteredPs = P.pairs(
                    P.lists(((RandomProvider) P).naturalIntegersGeometric(10)),
                    map(i -> i + 2, ((RandomProvider) P).naturalIntegersGeometric(20))
            );
        }
        Iterable<Pair<List<Integer>, Integer>> ps = filter(p -> all(i -> i < p.b, p.a), unfilteredPs);
        for (Pair<List<Integer>, Integer> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            System.out.println("fromDigits(" + p.b + ", " + p.a + ") = " + fromDigits(p.b, p.a));
        }
    }

    private static void demoFromDigits_BigInteger_Iterable_BigInteger() {
        initialize();
        Iterable<Pair<List<BigInteger>, BigInteger>> unfilteredPs;
        if (P instanceof ExhaustiveProvider) {
            unfilteredPs = ((ExhaustiveProvider) P).pairsLogarithmicOrder(
                    P.lists(P.naturalBigIntegers()),
                    P.rangeUp(BigInteger.valueOf(2))
            );
        } else {
            unfilteredPs = P.pairs(
                    P.lists(map(i -> BigInteger.valueOf(i), ((RandomProvider) P).naturalIntegersGeometric(10))),
                    map(i -> BigInteger.valueOf(i + 2), ((RandomProvider) P).naturalIntegersGeometric(20))
            );
        }
        Iterable<Pair<List<BigInteger>, BigInteger>> ps = filter(p -> {
            assert p.a != null;
            return all((BigInteger i) -> lt(i, p.b), p.a);
        }, unfilteredPs);
        for (Pair<List<BigInteger>, BigInteger> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            System.out.println("fromDigits(" + p.b + ", " + p.a + ") = " + fromDigits(p.b, p.a));
        }
    }

    private static void demoToDigit() {
        initialize();
        for (int i : take(LIMIT, P.range(0, 35))) {
            System.out.println("toDigit(" + i + ") = " + toDigit(i));
        }
    }

    private static void demoFromDigit() {
        initialize();
        for (int i : take(LIMIT, P.range(0, 35))) {
            char c = toDigit(i);
            System.out.println("fromDigit(" + c + ") = " + fromDigit(c));
        }
    }

    private static void demoToStringBase_int_int() {
        initialize();
        Iterable<Pair<Integer, Integer>> ps;
        if (P instanceof ExhaustiveProvider) {
            ps = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.integers(), P.rangeUp(2));
        } else {
            ps = P.pairs(P.integers(), map(i -> i + 2, ((RandomProvider) P).naturalIntegersGeometric(20)));
        }
        for (Pair<Integer, Integer> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            System.out.println("toStringBase(" + p.b + ", " + p.a + ") = " + toStringBase(p.b, p.a));
        }
    }

    private static void demoToStringBase_BigInteger_BigInteger() {
        initialize();
        Iterable<Pair<BigInteger, BigInteger>> ps;
        if (P instanceof ExhaustiveProvider) {
            ps = ((ExhaustiveProvider) P).pairsSquareRootOrder(P.bigIntegers(), P.rangeUp(BigInteger.valueOf(2)));
        } else {
            ps = P.pairs(
                    P.bigIntegers(),
                    map(i -> BigInteger.valueOf(i + 2), ((RandomProvider) P).naturalIntegersGeometric(20))
            );
        }
        for (Pair<BigInteger, BigInteger> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            System.out.println("toStringBase(" + p.b + ", " + p.a + ") = " + toStringBase(p.b, p.a));
        }
    }

    private static void demoFromStringBase_int_String() {
        initialize();
        Iterable<Pair<Integer, String>> ps = P.dependentPairs(
                P.rangeUp(2),
                b -> {
                    Iterable<String> positiveStrings;
                    if (b <= 36) {
                        positiveStrings = P.strings(map(MathUtils::toDigit, P.range(0, b - 1)));
                    } else {
                        positiveStrings = map(
                                is -> concatMapStrings(i -> "(" + i + ")", is),
                                P.lists(P.range(0, b - 1))
                        );
                    }
                    return mux(
                            (List<Iterable<String>>) Arrays.asList(
                                    positiveStrings,
                                    map((String s) -> cons('-', s), filter(t -> !t.isEmpty(), positiveStrings))
                            )
                    );
                }
        );
        for (Pair<Integer, String> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            System.out.println("fromStringBase(" + p.a + ", " + p.b + ") = " + fromStringBase(p.a, p.b));
        }
    }

    private static void demoFromStringBase_BigInteger_String() {
        initialize();
        Iterable<Pair<BigInteger, String>> ps = P.dependentPairs(
                P.rangeUp(BigInteger.valueOf(2)),
                b -> {
                    Iterable<String> positiveStrings;
                    if (le(b, BigInteger.valueOf(36))) {
                        positiveStrings = P.strings(
                                map(
                                        i -> MathUtils.toDigit(i.intValueExact()),
                                        (Iterable<BigInteger>) P.range(BigInteger.ZERO, b.subtract(BigInteger.ONE))
                                )
                        );
                    } else {
                        positiveStrings = map(
                                is -> concatMapStrings(i -> "(" + i + ")", is),
                                P.lists(P.range(BigInteger.ZERO, b.subtract(BigInteger.ONE)))
                        );
                    }
                    return mux(
                            (List<Iterable<String>>) Arrays.asList(
                                    positiveStrings,
                                    map((String s) -> cons('-', s), filter(t -> !t.isEmpty(), positiveStrings))
                            )
                    );
                }
        );
        for (Pair<BigInteger, String> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            System.out.println("fromStringBase(" + p.a + ", " + p.b + ") = " + fromStringBase(p.a, p.b));
        }
    }

    private static void demoLogarithmicMux() {
        initialize();
        Iterable<Pair<BigInteger, BigInteger>> ps;
        if (P instanceof ExhaustiveProvider) {
            ps = P.pairs(P.naturalBigIntegers());
        } else {
            //noinspection Convert2MethodRef
            ps = P.pairs(
                    P.naturalBigIntegers(),
                    map(i -> BigInteger.valueOf(i), ((RandomProvider) P).naturalIntegersGeometric(20))
            );
        }
        for (Pair<BigInteger, BigInteger> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            System.out.println("logarithmicMux(" + p.a + ", " + p.b + ") = " + logarithmicMux(p.a, p.b));
        }
    }

    private static void demoLogarithmicDemux() {
        initialize();
        for (BigInteger i : take(LIMIT, P.naturalBigIntegers())) {
            System.out.println("logarithmicDemux(" + i + ") = " + logarithmicDemux(i));
        }
    }

    private static void demoSquareRootMux() {
        initialize();
        for (Pair<BigInteger, BigInteger> p : take(LIMIT, P.pairs(P.naturalBigIntegers()))) {
            assert p.a != null;
            assert p.b != null;
            System.out.println("squareRootMux(" + p.a + ", " + p.b + ") = " + squareRootMux(p.a, p.b));
        }
    }

    private static void demoSquareRootDemux() {
        initialize();
        for (BigInteger i : take(LIMIT, P.naturalBigIntegers())) {
            System.out.println("squareRootDemux(" + i + ") = " + squareRootDemux(i));
        }
    }

    private static void demoMux() {
        initialize();
        for (List<BigInteger> is : take(LIMIT, P.lists(P.naturalBigIntegers()))) {
            String listString = tail(init(is.toString()));
            System.out.println("mux(" + listString + ") = " + mux(is));
        }
    }

    private static void demoDemux() {
        initialize();
        Iterable<Pair<BigInteger, Integer>> ps;
        Pair<BigInteger, Integer> zeroPair = new Pair<>(BigInteger.ZERO, 0);
        if (P instanceof ExhaustiveProvider) {
            ps = cons(
                    zeroPair,
                    ((ExhaustiveProvider) P).pairsLogarithmicOrder(P.naturalBigIntegers(), P.positiveIntegers())
            );
        } else {
            ps = ((RandomProvider) P).addSpecialElement(
                    zeroPair,
                    P.pairs(P.naturalBigIntegers(), ((RandomProvider) P).positiveIntegersGeometric(20))
            );
        }
        for (Pair<BigInteger, Integer> p : take(LIMIT, ps)) {
            assert p.a != null;
            assert p.b != null;
            System.out.println("demux(" + p.b + ", " + p.a + ") = " + demux(p.b, p.a));
        }
    }
}
