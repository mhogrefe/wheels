package mho.wheels.io;

import mho.wheels.ordering.Ordering;
import mho.wheels.structures.FiniteDomainFunction;
import mho.wheels.structures.Pair;
import mho.wheels.testing.TestProperties;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static mho.wheels.io.Readers.*;
import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.testing.Testing.*;

public strictfp class ReadersProperties extends TestProperties {
    private static final @NotNull String INTEGRAL_CHARS = "-0123456789";
    
    public ReadersProperties() {
        super("Readers");
    }

    @Override
    protected void testBothModes() {
        propertiesGenericRead();
        propertiesGenericFindIn_List_T();
        propertiesReadBoolean();
        propertiesFindBooleanIn();
        propertiesReadOrdering();
        propertiesFindOrderingIn();
        propertiesReadRoundingMode();
        propertiesFindRoundingModeIn();
        propertiesReadBigInteger();
        propertiesFindBigIntegerIn();
        propertiesReadByte();
        propertiesFindByteIn();
        propertiesReadShort();
        propertiesFindShortIn();
        propertiesReadInteger();
        propertiesFindIntegerIn();
        propertiesReadLong();
        propertiesFindLongIn();
        propertiesReadFloat();
        propertiesFindFloatIn();
        propertiesReadDouble();
        propertiesFindDoubleIn();
        propertiesReadBigDecimal();
        propertiesFindBigDecimalIn();
        propertiesReadCharacter();
        propertiesFindCharacterIn();
        propertiesReadString();
    }

    private void propertiesGenericRead() {
        initialize("genericRead(Function<String, T>)");
        Iterable<Pair<Function<String, Integer>, String>> ps = map(
                p -> new Pair<>(new FiniteDomainFunction<>(Collections.singletonList(p)), p.a),
                P.pairs(P.strings(INTEGRAL_CHARS), P.withNull(P.integers()))
        );
        for (Pair<Function<String, Integer>, String> p : take(LIMIT, ps)) {
            genericRead(p.a).apply(p.b);
        }

        for (int i : take(LIMIT, P.integers())) {
            String s = Integer.toString(i);
            Function<String, Integer> f = new FiniteDomainFunction<>(Collections.singletonList(new Pair<>(s, i)));
            assertEquals(i, genericRead(f).apply(s).get(), i);
        }
    }

    private void propertiesGenericFindIn_List_T() {
        initialize("genericFindIn(List<T>)");
        Iterable<Pair<List<Integer>, String>> ps = P.pairs(P.subsets(P.integers()), P.strings(INTEGRAL_CHARS));
        for (Pair<List<Integer>, String> p : take(LIMIT, ps)) {
            genericFindIn(p.a).apply(p.b);
        }

        for (int i : take(LIMIT, P.integers())) {
            assertEquals(
                    i,
                    genericFindIn(Collections.singletonList(i)).apply(Integer.toString(i)).get(),
                    new Pair<>(i, 0)
            );
        }

        ps = map(
                p -> p.b,
                P.dependentPairsInfinite(
                        P.integers(),
                        i -> P.pairs(
                                P.subsetsWithElement(i, P.integers()),
                                P.stringsWithSubstrings(P.uniformSample(Collections.singletonList(i.toString())))
                        )
                )
        );
        for (Pair<List<Integer>, String> p : take(LIMIT, ps)) {
            Optional<Pair<Integer, Integer>> oq = genericFindIn(p.a).apply(p.b);
            Pair<Integer, Integer> q = oq.get();
            assertNotNull(p, q.a);
            assertNotNull(p, q.b);
            assertTrue(p, q.b >= 0 && q.b < p.b.length());
            String before = take(q.b, p.b);
            Optional<Pair<Integer, Integer>> appliedBefore = genericFindIn(p.a).apply(before);
            assertFalse(p, appliedBefore.isPresent() && appliedBefore.get().a.equals(q.a));
            String during = q.a.toString();
            assertTrue(p, p.b.substring(q.b).startsWith(during));
        }

        Iterable<Pair<List<Integer>, String>> psFail = P.pairs(P.listsWithElement(null, P.integers()), P.strings());
        for (Pair<List<Integer>, String> p : take(LIMIT, psFail)) {
            try {
                genericFindIn(p.a).apply(p.b);
                fail(p);
            } catch (NullPointerException ignored) {}
        }

        Iterable<List<Integer>> nonUniqueLists = nub(
                map(
                        p -> toList(insert(p.a, p.b.b, p.b.a)),
                        P.dependentPairs(
                                P.distinctListsAtLeast(1, P.integers()),
                                xs -> P.pairs(P.uniformSample(xs), P.range(0, xs.size()))
                        )
                )
        );
        for (Pair<List<Integer>, String> p : take(LIMIT, P.pairs(nonUniqueLists, P.strings()))) {
            try {
                genericFindIn(p.a).apply(p.b);
                fail(p);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void propertiesReadBoolean() {
        initialize("readBoolean(String)");
        for (String s : take(LIMIT, P.strings())) {
            readBoolean(s);
        }

        for (boolean b : take(LIMIT, P.booleans())) {
            Optional<Boolean> ob = readBoolean(Boolean.toString(b));
            assertEquals(b, ob.get(), b);
        }
    }

    private void propertiesFindBooleanIn() {
        initialize("findBooleanIn(String)");
        propertiesFindInHelper(LIMIT, P, P.booleans(), Readers::readBoolean, Readers::findBooleanIn, b -> {});
    }

    private void propertiesReadOrdering() {
        initialize("readOrdering(String)");
        for (String s : take(LIMIT, P.strings())) {
            readOrdering(s);
        }

        for (Ordering o : take(LIMIT, P.orderings())) {
            Optional<Ordering> oo = readOrdering(o.toString());
            assertEquals(o, oo.get(), o);
        }
    }

    private void propertiesFindOrderingIn() {
        initialize("findOrderingIn(String)");
        propertiesFindInHelper(LIMIT, P, P.orderings(), Readers::readOrdering, Readers::findOrderingIn, o -> {});
    }

    private void propertiesReadRoundingMode() {
        initialize("readRoundingMode(String)");
        for (String s : take(LIMIT, P.strings())) {
            readRoundingMode(s);
        }

        for (RoundingMode rm : take(LIMIT, P.roundingModes())) {
            Optional<RoundingMode> orm = readRoundingMode(rm.toString());
            assertEquals(rm, orm.get(), rm);
        }
    }

    private void propertiesFindRoundingModeIn() {
        initialize("findRoundingModeIn(String)");
        propertiesFindInHelper(
                LIMIT,
                P,
                P.roundingModes(),
                Readers::readRoundingMode,
                Readers::findRoundingModeIn,
                rm -> {}
        );
    }

    private void propertiesReadBigInteger() {
        initialize("readBigInteger(String)");
        for (String s : take(LIMIT, P.strings())) {
            readBigInteger(s);
        }

        for (BigInteger i : take(LIMIT, P.bigIntegers())) {
            Optional<BigInteger> oi = readBigInteger(i.toString());
            assertEquals(i, oi.get(), i);
        }
    }

    private void propertiesFindBigIntegerIn() {
        initialize("findBigIntegerIn(String)");
        propertiesFindInHelper(LIMIT, P, P.bigIntegers(), Readers::readBigInteger, Readers::findBigIntegerIn, i -> {});
    }

    private void propertiesReadByte() {
        initialize("readByte(String)");
        for (String s : take(LIMIT, P.strings())) {
            readByte(s);
        }

        for (byte b : take(LIMIT, P.bytes())) {
            Optional<Byte> ob = readByte(Byte.toString(b));
            assertEquals(b, ob.get(), b);
        }
    }

    private void propertiesFindByteIn() {
        initialize("findByteIn(String)");
        propertiesFindInHelper(LIMIT, P, P.bytes(), Readers::readByte, Readers::findByteIn, b -> {});
    }

    private void propertiesReadShort() {
        initialize("readShort(String)");
        for (String s : take(LIMIT, P.strings())) {
            readShort(s);
        }

        for (short s : take(LIMIT, P.shorts())) {
            Optional<Short> os = readShort(Short.toString(s));
            assertEquals(s, os.get(), s);
        }
    }

    private void propertiesFindShortIn() {
        initialize("findShortIn(String)");
        propertiesFindInHelper(LIMIT, P, P.shorts(), Readers::readShort, Readers::findShortIn, s -> {});
    }

    private void propertiesReadInteger() {
        initialize("readInteger(String)");
        for (String s : take(LIMIT, P.strings())) {
            readInteger(s);
        }

        for (int i : take(LIMIT, P.integers())) {
            Optional<Integer> oi = readInteger(Integer.toString(i));
            assertEquals(i, oi.get(), i);
        }
    }

    private void propertiesFindIntegerIn() {
        initialize("findIntegerIn(String)");
        propertiesFindInHelper(LIMIT, P, P.integers(), Readers::readInteger, Readers::findIntegerIn, i -> {});
    }

    private void propertiesReadLong() {
        initialize("readLong(String)");
        for (String s : take(LIMIT, P.strings())) {
            readLong(s);
        }

        for (long l : take(LIMIT, P.longs())) {
            Optional<Long> ol = readLong(Long.toString(l));
            assertEquals(l, ol.get(), l);
        }
    }

    private void propertiesFindLongIn() {
        initialize("findLongIn(String)");
        propertiesFindInHelper(LIMIT, P, P.longs(), Readers::readLong, Readers::findLongIn, l -> {});
    }

    private void propertiesReadFloat() {
        initialize("readFloat(String)");
        for (String s : take(LIMIT, P.strings())) {
            readFloat(s);
        }

        for (float f : take(LIMIT, P.floats())) {
            Optional<Float> of = readFloat(Float.toString(f));
            assertEquals(f, of.get(), f);
        }
    }

    private void propertiesFindFloatIn() {
        initialize("findFloatIn(String)");
        propertiesFindInHelper(LIMIT, P, P.floats(), Readers::readFloat, Readers::findFloatIn, f -> {});
    }

    private void propertiesReadDouble() {
        initialize("readDouble(String)");
        for (String s : take(LIMIT, P.strings())) {
            readDouble(s);
        }

        for (double d : take(LIMIT, P.doubles())) {
            Optional<Double> od = readDouble(Double.toString(d));
            assertEquals(d, od.get(), d);
        }
    }

    private void propertiesFindDoubleIn() {
        initialize("findDoubleIn(String)");
        propertiesFindInHelper(LIMIT, P, P.doubles(), Readers::readDouble, Readers::findDoubleIn, d -> {});
    }

    private void propertiesReadBigDecimal() {
        initialize("readBigDecimal(String)");
        for (String s : take(LIMIT, P.strings())) {
            readBigDecimal(s);
        }

        for (BigDecimal bd : take(LIMIT, P.bigDecimals())) {
            Optional<BigDecimal> obd = readBigDecimal(bd.toString());
            assertEquals(bd, obd.get(), bd);
        }
    }

    private void propertiesFindBigDecimalIn() {
        initialize("findBigDecimalIn(String)");
        propertiesFindInHelper(
                LIMIT,
                P,
                P.bigDecimals(),
                Readers::readBigDecimal,
                Readers::findBigDecimalIn,
                bd -> {}
        );
    }

    private void propertiesReadCharacter() {
        initialize("readCharacter(String)");
        for (String s : take(LIMIT, P.strings())) {
            readCharacter(s);
        }

        for (char c : take(LIMIT, P.characters())) {
            Optional<Character> oc = readCharacter(Character.toString(c));
            assertEquals(c, oc.get(), c);
        }
    }

    private void propertiesFindCharacterIn() {
        initialize("findCharacterIn(String)");
        propertiesFindInHelper(LIMIT, P, P.characters(), Readers::readCharacter, Readers::findCharacterIn, c -> {});
    }

    private void propertiesReadString() {
        initialize("readString(String)");
        for (String s : take(LIMIT, P.strings())) {
            Optional<String> os = readString(s);
            assertEquals(s, os.get(), s);
        }
    }
}
