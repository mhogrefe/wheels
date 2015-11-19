package mho.wheels.testing;

import mho.wheels.iterables.ExhaustiveProvider;
import mho.wheels.iterables.IterableProvider;
import mho.wheels.iterables.IterableUtils;
import mho.wheels.ordering.Ordering;
import mho.wheels.structures.Pair;
import mho.wheels.structures.Triple;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Assert;
import org.junit.ComparisonFailure;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.ordering.Ordering.*;

public strictfp class Testing {
    private static final @NotNull ExhaustiveProvider EP = ExhaustiveProvider.INSTANCE;
    private static final int TINY_LIMIT = 20;
    private static final int SMALL_LIMIT = 128;

    /**
     * Disallow instantiation
     */
    private Testing() {}

    public static void aeq(@NotNull Object message, int i, int j) {
        assertEquals(message, i, j);
    }

    public static void aeq(@NotNull Object message, long i, long j) {
        assertEquals(message, i, j);
    }

    public static void aeqf(float f, float g) {
        assertEquals(Float.toString(f) + " != " + Float.toString(g), Float.toString(f), Float.toString(g));
    }

    public static void aeqd(double d, double e) {
        assertEquals(Double.toString(d) + " != " + Double.toString(e), Double.toString(d), Double.toString(e));
    }

    public static void aeqf(@NotNull Object message, float f1, float f2) {
        assertEquals(message, Float.toString(f1), Float.toString(f2));
    }

    public static void aeqd(@NotNull Object message, double d1, double d2) {
        assertEquals(message, Double.toString(d1), Double.toString(d2));
    }

    public static void aeq(@NotNull Object a, @NotNull Object b) {
        Assert.assertEquals(a.toString(), b.toString());
    }

    public static void aeqcs(@NotNull Iterable<Character> cs, @NotNull String s) {
        List<Character> truncated = toList(take(SMALL_LIMIT, cs));
        assertEquals(nicePrint(truncated), charsToString(truncated), s);
    }

    public static void aeqit(Iterable<?> a, Object b) {
        Assert.assertEquals(IterableUtils.toString(a), b.toString());
    }

    public static void aeqitLimit(int limit, Iterable<?> a, Object b) {
        Assert.assertEquals(IterableUtils.toString(limit, a), b.toString());
    }

    public static <T> void aeqit(@NotNull Object message, @NotNull Iterable<T> xs, @NotNull Iterable<T> ys) {
        assertTrue(message, IterableUtils.equal(xs, ys));
    }

    public static <T> void aeqit(
            @NotNull Object message,
            int limit,
            @NotNull Iterable<T> xs,
            @NotNull Iterable<T> ys
    ) {
        assertTrue(message, IterableUtils.equal(limit, xs, ys));
    }

    public static void assertTrue(Object message, boolean condition) {
        if (!condition) {
            fail(message);
        }
    }

    public static void assertFalse(Object message, boolean condition) {
        assertTrue(message, !condition);
    }

    public static void fail(Object message) {
        if(message == null) {
            throw new AssertionError();
        } else {
            throw new AssertionError(message);
        }
    }

    public static void assertNotEquals(Object message, Object first, Object second) {
        if(equalsRegardingNull(first, second)) {
            failEquals(message, first);
        }
    }

    private static void failEquals(Object message, Object actual) {
        String formatted = "Values should be different. ";
        if(message != null) {
            formatted = message + ". ";
        }
        formatted = formatted + "Actual: " + actual;
        fail(formatted);
    }

    public static void assertEquals(Object message, Object expected, Object actual) {
        if (!equalsRegardingNull(expected, actual)) {
            if (expected instanceof String && actual instanceof String) {
                String cleanMessage = message == null ? "" : message.toString();
                throw new ComparisonFailure(cleanMessage, (String) expected, (String) actual);
            } else {
                failNotEquals(message, expected, actual);
            }
        }
    }

    private static void failNotEquals(Object message, Object expected, Object actual) {
        fail(format(Objects.toString(message), expected, actual));
    }

    static String format(String message, Object expected, Object actual) {
        String formatted = "";
        if(message != null && !message.equals("")) {
            formatted = message + " ";
        }
        String expectedString = String.valueOf(expected);
        String actualString = String.valueOf(actual);
        return expectedString.equals(actualString) ?
                formatted + "expected: " + formatClassAndValue(expected, expectedString) + " but was: " +
                        formatClassAndValue(actual, actualString) :
                formatted + "expected:<" + expectedString + "> but was:<" + actualString + ">";
    }

    private static String formatClassAndValue(Object value, String valueString) {
        return (value == null ? "null" : value.getClass().getName()) + "<" + valueString + ">";
    }

    private static boolean equalsRegardingNull(Object expected, Object actual) {
        return expected == null ? actual == null : isEquals(expected, actual);
    }

    private static boolean isEquals(Object expected, Object actual) {
        return expected.equals(actual);
    }

    public static void assertNotNull(Object message, Object object) {
        assertTrue(message, object != null);
    }

    public static void assertNull(Object message, Object object) {
        if (object != null) {
            failNotNull(message, object);
        }
    }

    private static void failNotNull(Object message, Object actual) {
        String formatted = "";
        if (message != null) {
            formatted = message + " ";
        }
        fail(formatted + "expected null, but was:<" + actual + ">");
    }

    public static <A, B> void compareImplementations(
            @NotNull String method,
            @NotNull Iterable<A> inputs,
            @NotNull Map<String, Function<A, B>> functions
    ) {
        System.out.println("\t\tcomparing " + method + " implementations...");
        for (Map.Entry<String, Function<A, B>> entry : functions.entrySet()) {
            Function<A, B> function = entry.getValue();
            long totalTime = 0;
            for (A input : inputs) {
                long time = System.nanoTime();
                function.apply(input);
                totalTime += (System.nanoTime() - time);
            }
            System.out.println("\t\t\t" + entry.getKey() + ": " + ((double) totalTime) / 1e9 + " s");
        }
    }

    public static <T> Map<T, Integer> sampleCount(@NotNull List<T> xs) {
        Map<T, Integer> counts = new LinkedHashMap<>();
        for (T value : xs) {
            Integer count = counts.get(value);
            if (count == null) count = 0;
            counts.put(value, count + 1);
        }
        return counts;
    }

    public static <T> Map<T, Integer> topSampleCount(int topCount, @NotNull List<T> xs) {
        SortedMap<Integer, List<T>> frequencyMap = new TreeMap<>();
        for (Map.Entry<T, Integer> entry : sampleCount(xs).entrySet()) {
            int frequency = entry.getValue();
            List<T> elements = frequencyMap.get(-frequency);
            if (elements == null) {
                elements = new ArrayList<>();
                frequencyMap.put(-frequency, elements);
            }
            elements.add(entry.getKey());
        }
        Map<T, Integer> filteredCounts = new LinkedHashMap<>();
        int i = 0;
        for (Map.Entry<Integer, List<T>> entry : frequencyMap.entrySet()) {
            for (T x : entry.getValue()) {
                if (i == topCount) return filteredCounts;
                filteredCounts.put(x, -entry.getKey());
                i++;
            }
        }
        return filteredCounts;
    }

    public static <T> void symmetric(@NotNull BiPredicate<T, T> relation, @NotNull Pair<T, T> p) {
        assertEquals(p, relation.test(p.a, p.b), relation.test(p.b, p.a));
    }

    public static <T> void antiSymmetric(@NotNull BiPredicate<T, T> relation, @NotNull Pair<T, T> p) {
        assertTrue(p, (relation.test(p.a, p.b) ^ relation.test(p.b, p.a)) || p.a.equals(p.b));
    }

    public static <T> void transitive(@NotNull BiPredicate<T, T> relation, @NotNull Triple<T, T, T> t) {
        if (relation.test(t.a, t.b) && relation.test(t.b, t.c)) {
            assertTrue(t, relation.test(t.a, t.c));
        }
    }

    public static <T> void fixedPoint(@NotNull Function<T, T> f, @NotNull T x) {
        assertEquals(x, f.apply(x), x);
    }

    public static <T> void idempotent(@NotNull Function<T, T> f, @NotNull T x) {
        T y = f.apply(x);
        assertEquals(x, f.apply(y), y);
    }

    public static <T> void isInvolution(@NotNull Function<T, T> f, @NotNull T x) {
        assertEquals(x, f.andThen(f).apply(x), x);
    }

    public static <A, B> void inverses(@NotNull Function<A, B> f, @NotNull Function<B, A> g, @Nullable A x) {
        assertEquals(x, f.andThen(g).apply(x), x);
    }

    public static <A, B> void commutative(@NotNull BiFunction<A, A, B> f, @NotNull Pair<A, A> p) {
        assertEquals(p, f.apply(p.a, p.b), f.apply(p.b, p.a));
    }

    public static <A, B> void antiCommutative(
            @NotNull BiFunction<A, A, B> f,
            @NotNull Function<B, B> negate,
            @NotNull Pair<A, A> p) {
        assertEquals(p, f.apply(p.a, p.b), f.andThen(negate).apply(p.b, p.a));
    }

    public static <T> void associative(@NotNull BiFunction<T, T, T> f, @NotNull Triple<T, T, T> t) {
        assertEquals(t, f.apply(f.apply(t.a, t.b), t.c), f.apply(t.a, f.apply(t.b, t.c)));
    }

    public static <A, B, MA, MB> void homomorphic(
            @NotNull Function<A, MA> domainMap,
            @NotNull Function<B, MB> rangeMap,
            @NotNull Function<A, B> f,
            @NotNull Function<MA, MB> mf,
            A x
    ) {
        assertEquals(x, f.andThen(rangeMap).apply(x), domainMap.andThen(mf).apply(x));
    }

    public static <A, B, C, MA, MB, MC> void homomorphic(
            @NotNull Function<A, MA> domainMapA,
            @NotNull Function<B, MB> domainMapB,
            @NotNull Function<C, MC> rangeMap,
            @NotNull BiFunction<A, B, C> f,
            @NotNull BiFunction<MA, MB, MC> mf,
            @NotNull Pair<A, B> p
    ) {
        assertEquals(p, f.andThen(rangeMap).apply(p.a, p.b), mf.apply(domainMapA.apply(p.a), domainMapB.apply(p.b)));
    }

    public static <T> void testEqualsHelper(@NotNull List<T> xs, @NotNull List<T> ys) {
        for (T x : xs) {
            //noinspection ObjectEqualsNull
            assertFalse(x, x.equals(null));
        }

        for (int i = 0; i < xs.size(); i++) {
            for (int j = 0; j < xs.size(); j++) {
                T x = xs.get(i);
                T y = ys.get(j);
                assertEquals(new Pair<>(x, y), i == j, x.equals(y));
                if (i == j) {
                    assertEquals(x, x.hashCode(), y.hashCode());
                }
            }
        }
    }

    public static <T extends Comparable<T>> void testCompareToHelper(@NotNull List<T> xs) {
        for (int i = 0; i < xs.size(); i++) {
            T xsi = xs.get(i);
            for (int j = 0; j < xs.size(); j++) {
                T xsj = xs.get(j);
                assertEquals(new Pair<>(xsi, xsj), compare(i, j), compare(xsi, xsj));
            }
        }
    }

    public static <T> void propertiesEqualsHelper(
            int limit,
            @NotNull IterableProvider ip,
            @NotNull Function<IterableProvider, Iterable<T>> fxs
    ) {
        IterableProvider iq = ip.deepCopy();
        IterableProvider ir = ip.deepCopy();
        for (Triple<T, T, T> t : take(limit, zip3(fxs.apply(ip), fxs.apply(iq), fxs.apply(ir)))) {
            //noinspection ConstantConditions,ObjectEqualsNull
            assertFalse(t, t.a.equals(null));
            assertTrue(t, t.a.equals(t.b));
            //noinspection ConstantConditions
            assertTrue(t, t.b.equals(t.c));
        }

        ip.reset();
        iq.reset();
        for (Pair<T, T> p : take(limit, EP.pairs(fxs.apply(ip), fxs.apply(iq)))) {
            symmetric(Object::equals, p);
        }

        ip.reset();
        iq.reset();
        ir.reset();
        for (Triple<T, T, T> t : take(limit, EP.triples(fxs.apply(ip), fxs.apply(iq), fxs.apply(ir)))) {
            transitive(Object::equals, t);
        }
    }

    public static <T> void propertiesHashCodeHelper(
            int limit,
            @NotNull IterableProvider ip,
            @NotNull Function<IterableProvider,
            Iterable<T>> fxs
    ) {
        IterableProvider iq = ip.deepCopy();
        for (Pair<T, T> p : take(limit, zip(fxs.apply(ip), fxs.apply(iq)))) {
            //noinspection ConstantConditions
            assertTrue(p, p.a.equals(p.b));
            //noinspection ConstantConditions
            assertEquals(p, p.a.hashCode(), p.b.hashCode());
        }
    }

    public static <T extends Comparable<T>> void propertiesCompareToHelper(
            int limit,
            @NotNull IterableProvider ip,
            @NotNull Function<IterableProvider, Iterable<T>> fxs
    ) {
        IterableProvider iq = ip.deepCopy();
        IterableProvider ir = ip.deepCopy();
        for (Pair<T, T> p : take(limit, zip(fxs.apply(ip), fxs.apply(iq)))) {
            assertTrue(p, eq(p.a, p.b));
        }

        ip.reset();
        iq.reset();
        for (Pair<T, T> p : take(limit, EP.pairs(fxs.apply(ip), fxs.apply(iq)))) {
            int compare = p.a.compareTo(p.b);
            assertTrue(p, compare == 0 || compare == 1 || compare == -1);
            antiSymmetric(Ordering::le, p);
            assertTrue(p, le(p.a, p.b) || le(p.b, p.a));
            antiCommutative(Comparable::compareTo, c -> -c, p);
        }

        ip.reset();
        iq.reset();
        ir.reset();
        for (Triple<T, T, T> t : take(limit, EP.triples(fxs.apply(ip), fxs.apply(iq), fxs.apply(ir)))) {
            transitive(Ordering::le, t);
        }
    }

    public static <T> void testNoRemove(@NotNull Iterable<T> xs) {
        Iterator<T> it = xs.iterator();
        while (it.hasNext()) {
            it.next();
            try {
                it.remove();
                fail(IterableUtils.toString(TINY_LIMIT, xs));
            } catch (UnsupportedOperationException ignored) {}
        }
    }

    public static <T> void testNoRemove(int limit, @NotNull Iterable<T> xs) {
        Iterator<T> it = xs.iterator();
        int i = 0;
        while (it.hasNext()) {
            if (i == limit) return;
            it.next();
            try {
                it.remove();
                fail(IterableUtils.toString(TINY_LIMIT, xs));
            } catch (UnsupportedOperationException ignored) {}
            i++;
        }
    }

    public static <T> void testHasNext(@NotNull Iterable<T> xs) {
        Iterator<T> it = xs.iterator();
        while (it.hasNext()) {
            it.next();
        }
        try {
            it.next();
            fail(xs);
        } catch (Exception ignored) {}
    }

    public static @NotNull <T> String its(@NotNull Iterable<T> xs) {
        return IterableUtils.toString(TINY_LIMIT, xs);
    }

    public static @NotNull String cits(@NotNull Iterable<Character> cs) {
        return its(map(Testing::nicePrint, cs));
    }

    public static @NotNull String sits(@NotNull Iterable<String> ss) {
        return its(map(Testing::nicePrint, ss));
    }

    public static @NotNull String nicePrint(char c) {
        switch (c) {
            case '\b' :   return "\\b";
            case '\t' :   return "\\t";
            case '\n' :   return "\\n";
            case '\f' :   return "\\f";
            case '\r' :   return "\\r";
            case '"' :    return "\\\"";
            case '\\' :   return "\\\\";
            case '\177' : return "\\177";
            default:
                if (c < ' ') {
                    return "\\" + Integer.toOctalString(c);
                } else if (c > '\177' && c < '¡' || (c >= 256 && !Character.isLetter(c))) {
                    return String.format("\\u%04x", (int) c);
                } else {
                    return Character.toString(c);
                }
        }
    }

    public static @NotNull String nicePrint(@NotNull Iterable<Character> cs) {
        if (isEmpty(cs)) return "";
        StringBuilder sb = new StringBuilder();
        Character previous = null;
        for (char c : cs) {
            if (previous != null) {
                if (previous != '\b' && previous != '\t' && previous != '\n' && previous != '\f' && previous != '\r' &&
                        previous < ' ' && c >= '0' && c <= '9') {
                    sb.append(String.format("\\u%04x", (int) previous));
                } else {
                    sb.append(nicePrint(previous));
                }
            }
            previous = c;
        }
        //noinspection ConstantConditions
        sb.append(nicePrint(previous));
        return sb.toString();
    }

    public static @NotNull String nicePrint(@NotNull String s) {
        return nicePrint(fromString(s));
    }

    public static <T> void propertiesFoldHelper(
            int limit,
            @NotNull IterableProvider P,
            @NotNull Iterable<T> xs,
            @NotNull BiFunction<T, T, T> f,
            @NotNull Function<List<T>, T> listF,
            @NotNull Consumer<T> validate,
            boolean commutativeAndAssociative
    ) {
        for (List<T> lxs : take(limit, P.lists(xs))) {
            T result = listF.apply(lxs);
            validate.accept(result);
        }

        testNoRemove(limit, xs);

        if (commutativeAndAssociative) {
            Iterable<Pair<List<T>, List<T>>> ps = filter(
                    q -> !q.a.equals(q.b),
                    P.dependentPairs(P.lists(xs), P::permutationsFinite)
            );
            for (Pair<List<T>, List<T>> p : take(limit, ps)) {
                assertEquals(p, listF.apply(p.a), listF.apply(p.b));
            }
        }

        for (T x : take(limit, xs)) {
            assertEquals(x, listF.apply(Collections.singletonList(x)), x);
        }

        for (Pair<T, T> p : take(limit, P.pairs(xs))) {
            assertEquals(p, listF.apply(Arrays.asList(p.a, p.b)), f.apply(p.a, p.b));
        }

        for (List<T> lxs : take(limit, P.listsWithElement(null, xs))) {
            try {
                listF.apply(lxs);
                fail(lxs);
            } catch (NullPointerException ignored) {}
        }
    }

    public static <A, B> void propertiesDeltaHelper(
            int limit,
            @NotNull IterableProvider P,
            @NotNull Iterable<A> exs,
            @NotNull Iterable<A> xs,
            @NotNull Function<B, B> negate,
            @NotNull BiFunction<A, A, B> subtract,
            @NotNull Function<Iterable<A>, Iterable<B>> deltaF,
            @NotNull Consumer<B> validate
    ) {
        propertiesDeltaHelperClean(limit, P, exs, xs, negate, subtract, deltaF, validate, x -> x);
    }

    public static <A, B> void propertiesDeltaHelperClean(
            int limit,
            @NotNull IterableProvider P,
            @NotNull Iterable<A> exs,
            @NotNull Iterable<A> xs,
            @NotNull Function<B, B> negate,
            @NotNull BiFunction<A, A, B> subtract,
            @NotNull Function<Iterable<A>, Iterable<B>> deltaF,
            @NotNull Consumer<B> validate,
            @NotNull Function<B, B> clean
    ) {
        for (List<A> lxs : take(limit, P.listsAtLeast(1, xs))) {
            Iterable<B> deltas = deltaF.apply(lxs);
            deltas.forEach(validate);
            aeq(lxs, length(deltas), length(lxs) - 1);
            Iterable<B> reversed = reverse(map(negate.andThen(clean), deltaF.apply(reverse(lxs))));
            aeqit(lxs, deltas, reversed);
            testNoRemove(TINY_LIMIT, deltas);
            testHasNext(deltas);
        }

        for (A x : take(limit, xs)) {
            assertTrue(x, isEmpty(deltaF.apply(Collections.singletonList(x))));
        }

        for (Pair<A, A> p : take(limit, P.pairs(xs))) {
            aeqit(
                    p,
                    deltaF.apply(Pair.toList(p)),
                    Collections.singletonList(subtract.andThen(clean).apply(p.b, p.a))
            );
        }

        for (Iterable<A> ixs : take(limit, EP.prefixPermutations(exs))) {
            Iterable<B> deltas = deltaF.apply(ixs);
            List<B> deltaPrefix = toList(take(TINY_LIMIT, deltas));
            deltaPrefix.forEach(validate);
            aeq(IterableUtils.toString(TINY_LIMIT, ixs), length(deltaPrefix), TINY_LIMIT);
            testNoRemove(TINY_LIMIT, deltas);
        }

        for (List<A> lxs : take(limit, P.listsWithElement(null, xs))) {
            try {
                toList(deltaF.apply(lxs));
                fail(lxs);
            } catch (NullPointerException ignored) {}
        }
    }

    public static <T> void propertiesReadHelper(
            int limit,
            @NotNull IterableProvider P,
            @NotNull String usedChars,
            @NotNull Iterable<T> xs,
            @NotNull Function<String, Optional<T>> read,
            @NotNull Consumer<T> validate,
            boolean denseInUsedCharString
    ) {
        for (String s : take(limit, P.strings())) {
            read.apply(s);
        }

        for (T x : take(limit, xs)) {
            Optional<T> ox = read.apply(x.toString());
            assertEquals(x, ox.get(), x);
        }

        if (denseInUsedCharString) {
            Iterable<String> ss = filter(s -> read.apply(s).isPresent(), P.strings(usedChars));
            for (String s : take(limit, ss)) {
                Optional<T> ox = read.apply(s);
                validate.accept(ox.get());
            }
        }
    }

    public static <T> void propertiesFindInHelper(
            int limit,
            @NotNull IterableProvider P,
            @NotNull Iterable<T> xs,
            @NotNull Function<String, Optional<T>> read,
            @NotNull Function<String, Optional<Pair<T, Integer>>> findIn,
            @NotNull Consumer<T> validate
    ) {
        for (String s : take(limit, P.strings())) {
            findIn.apply(s);
        }

        for (String s : take(limit, P.stringsWithSubstrings(map(Object::toString, xs)))) {
            Optional<Pair<T, Integer>> op = findIn.apply(s);
            Pair<T, Integer> p = op.get();
            assertNotNull(s, p.a);
            assertNotNull(s, p.b);
            validate.accept(p.a);
            assertTrue(s, p.b >= 0 && p.b < s.length());
            String before = take(p.b, s);
            assertFalse(s, findIn.apply(before).isPresent());
            String during = p.a.toString();
            assertTrue(s, s.substring(p.b).startsWith(during));
            String after = drop(p.b + during.length(), s);
            assertTrue(s, after.isEmpty() || !read.apply(during + head(after)).isPresent());
        }
    }

    public static <T> void propertiesToStringHelper(
            int limit,
            @NotNull String usedChars,
            @NotNull Iterable<T> xs,
            @NotNull Function<String,
            Optional<T>> read
    ) {
        for (T x : take(limit, xs)) {
            String s = x.toString();
            assertTrue(x, isSubsetOf(s, usedChars));
            Optional<T> ox = read.apply(s);
            assertTrue(x, ox.isPresent());
            assertEquals(x, ox.get(), x);
        }
    }

    public static <A, B> void benchmark(
            int trialCount,
            @NotNull String method,
            @NotNull Iterable<A> inputs,
            @NotNull Function<A, B> function,
            @NotNull Function<A, List<Integer>> sizeFunction
    ) {
        benchmark(trialCount, method, inputs, function, Object::toString, sizeFunction);
    }

    public static <A, B> void benchmark(
            int trialCount,
            @NotNull String method,
            @NotNull Iterable<A> inputs,
            @NotNull Function<A, B> function,
            @NotNull Function<A, String> display,
            @NotNull Function<A, List<Integer>> sizeFunction
    ) {
        System.out.println("benchmarking " + method + "...");
        List<Map<Integer, Pair<A, Long>>> worstCasesList = new ArrayList<>();
        for (A input : inputs) {
            long[] trials = new long[trialCount];
            for (int i = 0; i < trialCount; i++) {
                long trialTime = System.nanoTime();
                function.apply(input);
                trials[i] = System.nanoTime() - trialTime;
            }
            long time = middleMean(trials);
            List<Integer> sizes = sizeFunction.apply(input);
            if (worstCasesList.isEmpty()) {
                for (int i = 0; i < sizes.size(); i++) {
                    worstCasesList.add(new TreeMap<>());
                }
            }
            for (int i = 0; i < sizes.size(); i++) {
                Map<Integer, Pair<A, Long>> worstCases = worstCasesList.get(i);
                Pair<A, Long> worstCase = worstCases.get(sizes.get(i));
                if (worstCase == null || time > worstCase.b) {
                    worstCase = new Pair<>(input, time);
                    worstCases.put(sizes.get(i), worstCase);
                }
            }
        }
        for (Map<Integer, Pair<A, Long>> worstCases : worstCasesList) {
            for (Map.Entry<Integer, Pair<A, Long>> worstCase : worstCases.entrySet()) {
                System.out.println(worstCase.getKey() + "\t" + display.apply(worstCase.getValue().a) + "\t" +
                        ((double) worstCase.getValue().b) / 1e9);
            }
            System.out.println();
        }
    }

    private static long median(long[] xs) {
        Arrays.sort(xs);
        int mid = xs.length / 2;
        return (xs.length & 1) == 1 ? xs[mid] : (xs[mid - 1] + xs[mid]) / 2;
    }

    private static long sum(long[] xs) {
        long result = 0;
        for (long x : xs) {
            result += x;
        }
        return result;
    }

    private static long middleMean(long[] xs) {
        if (xs.length == 1) return xs[0];
        Arrays.sort(xs);
        int a = xs.length / 4;
        int b = xs.length * 3 / 4;
        long sum = 0;
        for (int i = a; i <= b; i++) {
            sum += xs[i];
        }
        return sum / (b - a + 1);
    }
}
