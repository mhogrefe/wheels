package mho.haskellesque.iterables;

import mho.haskellesque.tuples.Pair;
import mho.haskellesque.tuples.Triple;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

import static mho.haskellesque.ordering.Ordering.*;

public class IterableUtils {
    /**
     * Adds an <tt>Iterable</tt>'s elements to a <tt>Collection</tt>, in the order that the elements appear in the
     * <tt>Iterable</tt>. Only works for finite <tt>Iterable</tt>s.
     *
     * <ul>
     *  <li><tt>xs</tt> must be finite.</li>
     *  <li><tt>collection</tt> must be non-null.</li>
     *  <li><tt>collection</tt> must be able to hold every element of <tt>xs</tt>.</li>
     * </ul>
     *
     * @param xs the iterable
     * @param collection the collection to which the <tt>Iterable</tt>'s elements are added
     * @param <T> the <tt>Iterable</tt>'s element type
     */
    public static <T> void addTo(@NotNull Iterable<T> xs, @NotNull Collection<T> collection) {
        for (T x : xs) {
            collection.add(x);
        }
    }

    /**
     * Adds a <tt>String</tt>'s characters to a <tt>Collection</tt>, in the order that the characters appear in the
     * <tt>String</tt>.
     *
     * <ul>
     *  <li><tt>s</tt> must be non-null.</li>
     *  <li><tt>collection</tt> must be non-null.</li>
     *  <li><tt>collection</tt> must be able to hold every character of <tt>s</tt>.</li>
     * </ul>
     *
     * @param s the string
     * @param collection the collection to which the <tt>String</tt>'s characters are added
     */
    public static void addTo(@NotNull String s, @NotNull Collection<Character> collection) {
        for (int i = 0; i < s.length(); i++) {
            collection.add(s.charAt(i));
        }
    }

    /**
     * Converts an <tt>Iterable</tt> to a <tt>List</tt>. Only works for finite <tt>Iterable</tt>s.
     *
     * <ul>
     *  <li><tt>xs</tt> must be finite.</li>
     *  <li>The result is non-null.</li>
     * </ul>
     *
     * @param xs the <tt>Iterable</tt>
     * @param <T> the <tt>Iterable</tt>'s element type
     * @return a <tt>List</tt> containing the elements of the <tt>Iterable</tt> in their original order
     */
    public static @NotNull <T> List<T> toList(@NotNull Iterable<T> xs) {
        List<T> list = new ArrayList<>();
        addTo(xs, list);
        return list;
    }

    /**
     * Converts an <tt>Iterable</tt> to a <tt>List</tt>. Only works for finite <tt>Iterable</tt>s.
     *
     * <ul>
     *  <li><tt>s</tt> may be any <tt>String</tt>.</li>
     *  <li>The result is non-null.</li>
     * </ul>
     *
     * @param s the <tt>String</tt>
     * @return a <tt>List</tt> containing the characters of <tt>s</tt> in their original order
     */
    public static @NotNull List<Character> toList(@NotNull String s) {
        List<Character> list = new ArrayList<>();
        addTo(s, list);
        return list;
    }

    /**
     * Creates a <tt>String</tt> representation of <tt>xs</tt>. Each element is converted to a <tt>String</tt> and
     * those strings are placed in a comma-separated list surrounded by square brackets. Only works for finite
     * <tt>Iterable</tt>s.
     *
     * <ul>
     *  <li><tt>xs</tt> must be finite.</li>
     *  <li>The result begins with '[' and ends with ']'.</li>
     * </ul>
     *
     * @param xs the <tt>Iterable</tt>
     * @param <T> the <tt>Iterable</tt>'s element type
     * @return a <tt>String</tt> representation of <tt>xs</tt>
     */
    public static @NotNull <T> String toString(@NotNull Iterable<T> xs) {
        return toList(xs).toString();
    }

    /**
     * Converts a <tt>String</tt> to an <tt>Iterable</tt> of characters. The order of the characters is preserved. Uses
     * O(1) additional memory.
     *
     * <ul>
     *  <li><tt>s</tt> must be non-null.</li>
     *  <li>The result is finite and does not contain any nulls.</li>
     * </ul>
     *
     * @param s the <tt>String</tt>
     * @return an <tt>Iterable</tt> containing all the <tt>String</tt>'s characters in their original order
     */
    public static @NotNull Iterable<Character> fromString(@NotNull String s) {
        return () -> new Iterator<Character>() {
            private int i = 0;

            @Override
            public boolean hasNext() {
                return i < s.length();
            }

            @Override
            public Character next() {
                return s.charAt(i++);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    /**
     * Creates a <tt>String</tt> from an <tt>Iterable</tt> of characters. The order of the characters is preserved.
     * Only works for finite <tt>Iterable</tt>s.
     *
     * <ul>
     *  <li><tt>cs</tt> must be finite and cannot contain nulls.</li>
     *  <li>The result is non-null.</li>
     * </ul>
     *
     * @param cs the <tt>Iterable</tt> of characters
     * @return the <tt>String</tt> containing all of <tt>chars</tt>'s characters in their original order
     */
    public static @NotNull String charsToString(@NotNull Iterable<Character> cs) {
        StringBuilder sb = new StringBuilder();
        for (char c : cs) {
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     * Equivalent of Haskell's (:) list constructor. Creates an <tt>Iterable</tt> whose first element is <tt>x</tt> and
     * whose remaining elements are given by <tt>xs</tt>. <tt>xs</tt> may be infinite, in which case the result is also
     * infinite. Uses O(1) additional memory.
     *
     * <ul>
     *  <li><tt>x</tt> can be anything.</li>
     *  <li><tt>xs</tt> must be non-null.</li>
     *  <li>The result is a non-empty <tt>Iterable</tt>.</li>
     * </ul>
     *
     * @param x the first element of the <tt>Iterable</tt> to be created.
     * @param xs the second-through-last elements of the <tt>Iterable</tt> to be created.
     * @param <T> the element type of the <tt>Iterable</tt> to be created.
     * @return the <tt>Iterable</tt> to be created.
     */
    public static @NotNull <T> Iterable<T> cons(@Nullable T x, @NotNull Iterable<T> xs) {
        return () -> new Iterator<T>() {
            private boolean readHead = false;
            private final Iterator<T> xsi = xs.iterator();

            @Override
            public boolean hasNext() {
                return !readHead || xsi.hasNext();
            }

            @Override
            public T next() {
                if (readHead) {
                    return xsi.next();
                } else {
                    readHead = true;
                    return x;
                }
            }
        };
    }

    /**
     * Equivalent of Haskell's (:) list constructor. Creates a <tt>String</tt> whose first character is <tt>c</tt> and
     * whose remaining characters are given by <tt>cs</tt>. Uses O(n) additional memory, where n is the length of cs.
     *
     * <ul>
     *  <li><tt>c</tt> can be anything.</li>
     *  <li><tt>cs</tt> must be non-null.</li>
     *  <li>The result is a non-empty <tt>String</tt>.</li>
     * </ul>
     *
     * @param c the first character of the <tt>String</tt> to be created.
     * @param cs the second-through-last characters of the <tt>String</tt> to be created.
     * @return the <tt>String</tt> to be created.
     */
    public static @NotNull String cons(char c, @NotNull String cs) {
        return Character.toString(c) + cs;
    }

    public static <T> Iterable<T> concat(Iterable<T> xs, Iterable<T> ys) {
        return () -> new Iterator<T>() {
            private final Iterator<T> xsi = xs.iterator();
            private final Iterator<T> ysi = ys.iterator();

            @Override
            public boolean hasNext() {
                return xsi.hasNext() || ysi.hasNext();
            }

            @Override
            public T next() {
                return xsi.hasNext() ? xsi.next() : ysi.next();
            }
        };
    }

    public static String concat(String s, String t) {
        return s + t;
    }

    public static <T> T head(Iterable<T> xs) {
        return xs.iterator().next();
    }

    public static <T> T head(List<T> xs) {
        return xs.get(0);
    }

    public static char head(String s) {
        return s.charAt(0);
    }

    public static <T> T last(Iterable<T> xs) {
        T previous = null;
        for (T x : xs) {
            previous = x;
        }
        return previous;
    }

    public static <T> T last(List<T> xs) {
        return xs.get(xs.size() - 1);
    }

    public static char last(String s) {
        return s.charAt(s.length() - 1);
    }

    public static <T> Iterable<T> tail(Iterable<T> xs) {
        return () -> new Iterator<T>() {
            private final Iterator<T> xsi = xs.iterator();
            {
                xsi.next();
            }

            @Override
            public boolean hasNext() {
                return xsi.hasNext();
            }

            @Override
            public T next() {
                return xsi.next();
            }
        };
    }

    public static String tail(String s) {
        return s.substring(1);
    }

    public static <T> Iterable<T> init(Iterable<T> xs) {
        return () -> new Iterator<T>() {
            private final Iterator<T> xsi = xs.iterator();
            private T next = xsi.next();
            private boolean hasNext = true;

            @Override
            public boolean hasNext() {
                return hasNext;
            }

            @Override
            public T next() {
                T oldNext = next;
                if (hasNext) {
                    next = xsi.next();
                }
                hasNext = xsi.hasNext();
                return oldNext;
            }
        };
    }

    public static String init(String s) {
        return s.substring(0, s.length() - 1);
    }

    public static <T> boolean isEmpty(Iterable<T> xs) {
        return !xs.iterator().hasNext();
    }

    public static <T> boolean isEmpty(Collection<T> xs) {
        return xs.isEmpty();
    }

    public static <T> int length(Iterable<T> xs) {
        int i = 0;
        for (T x : xs) {
            i++;
        }
        return i;
    }

    public static <T> int length(Collection<T> xs) {
        return xs.size();
    }

    public static <T, S> Iterable<S> map(Function<T, S> f, Iterable<T> xs) {
        return () -> new Iterator<S>() {
            private final Iterator<T> xsi = xs.iterator();

            @Override
            public boolean hasNext() {
                return xsi.hasNext();
            }

            @Override
            public S next() {
                return f.apply(xsi.next());
            }
        };
    }

    public static String map(Function<Character, Character> f, String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            sb.append(f.apply(s.charAt(i)));
        }
        return sb.toString();
    }

    public static <T> List<T> reverse(Iterable<T> xs) {
        List<T> list = toList(xs);
        Collections.reverse(list);
        return list;
    }

    public static String reverse(String s) {
        char[] reversed = new char[s.length()];
        for (int i = 0; i < s.length() / 2; i++) {
            int j = s.length() - i - 1;
            reversed[i] = s.charAt(j);
            reversed[j] = s.charAt(i);
        }
        return new String(reversed);
    }

    public static <T> Iterable<T> intersperse(T sep, Iterable<T> xs) {
        return () -> new Iterator<T>() {
            private final Iterator<T> xsi = xs.iterator();
            private boolean separating = false;

            @Override
            public boolean hasNext() {
                return xsi.hasNext();
            }

            @Override
            public T next() {
                if (separating) {
                    separating = !separating;
                    return sep;
                } else {
                    separating = !separating;
                    return xsi.next();
                }
            }
        };
    }

    public static String intersperse(char sep, String s) {
        if (s.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        sb.append(s.charAt(0));
        for (int i = 1; i < s.length(); i++) {
            sb.append(sep);
            sb.append(s.charAt(i));
        }
        return sb.toString();
    }

    public static <T> Iterable<T> intercalate(Iterable<T> xs, Iterable<Iterable<T>> xss) {
        return concat(intersperse(xs, xss));
    }

    public static String intercalate(String sep, Iterable<String> strings) {
        return concatStrings(intersperse(sep, strings));
    }

    public static <T> Iterable<Iterable<T>> transpose(Iterable<Iterable<T>> xss) {
        return () -> new Iterator<Iterable<T>>() {
            private Iterable<Iterator<T>> iterators = map(Iterable::iterator, xss);

            @Override
            public boolean hasNext() {
                return any(Iterator::hasNext, iterators);
            }

            @Override
            public Iterable<T> next() {
                Iterable<Pair<T, Iterator<T>>> advanced = map(
                        it -> new Pair<>(it.next(), it),
                        (Iterable<Iterator<T>>) filter(Iterator::hasNext, iterators)
                );
                iterators = map(p -> p.snd, advanced);
                return (Iterable<T>) map(p -> p.fst, advanced);
            }
        };
    }

    public static <T> Iterable<T> concat(Iterable<Iterable<T>> xss) {
        return () -> new Iterator<T>() {
            final Iterator<Iterable<T>> xssi = xss.iterator();
            Iterator<T> xsi = xssi.hasNext() ? xssi.next().iterator() : null;

            @Override
            public boolean hasNext() {
                if (xsi == null) return false;
                while (!xsi.hasNext()) {
                    if (!xssi.hasNext()) return false;
                    xsi = xssi.next().iterator();
                }
                return true;
            }

            @Override
            public T next() {
                hasNext();
                return xsi.next();
            }
        };
    }

    public static String concatStrings(Iterable<String> strings) {
        StringBuilder sb = new StringBuilder();
        for (String s : strings) {
            sb.append(s);
        }
        return sb.toString();
    }

    public static <T, S> Iterable<S> concatMap(Function<T, Iterable<S>> f, Iterable<T> xs) {
        return concat(map(f, xs));
    }

    public static boolean and(Iterable<Boolean> xs) {
        for (boolean x : xs) {
            if (!x) return false;
        }
        return true;
    }

    public static boolean or(Iterable<Boolean> xs) {
        for (boolean x : xs) {
            if (x) return true;
        }
        return false;
    }

    public static <T> boolean any(Predicate<T> predicate, Iterable<T> xs) {
        for (T x : xs) {
            if (predicate.test(x)) return true;
        }
        return false;
    }

    public static <T> boolean all(Predicate<T> predicate, Iterable<T> xs) {
        for (T x : xs) {
            if (!predicate.test(x)) return false;
        }
        return true;
    }

    public static byte sumByte(Iterable<Byte> xs) {
        byte sum = 0;
        for (byte x : xs) {
            sum += x;
        }
        return sum;
    }

    public static short sumShort(Iterable<Short> xs) {
        short sum = 0;
        for (short x : xs) {
            sum += x;
        }
        return sum;
    }

    public static int sumInteger(Iterable<Integer> xs) {
        int sum = 0;
        for (int x : xs) {
            sum += x;
        }
        return sum;
    }

    public static long sumLong(Iterable<Long> xs) {
        long sum = 0;
        for (long x : xs) {
            sum += x;
        }
        return sum;
    }

    public static float sumFloat(Iterable<Float> xs) {
        float sum = 0;
        for (float x : xs) {
            sum += x;
        }
        return sum;
    }

    public static double sumDouble(Iterable<Double> xs) {
        double sum = 0;
        for (double x : xs) {
            sum += x;
        }
        return sum;
    }

    public static BigInteger sumBigInteger(Iterable<BigInteger> xs) {
        BigInteger sum = BigInteger.ZERO;
        for (BigInteger x : xs) {
            sum = sum.add(x);
        }
        return sum;
    }

    public static BigDecimal sumBigDecimal(Iterable<BigDecimal> xs) {
        BigDecimal sum = BigDecimal.ZERO;
        for (BigDecimal x : xs) {
            sum = sum.add(x);
        }
        return sum;
    }

    public static byte productByte(Iterable<Byte> xs) {
        byte product = 1;
        for (byte x : xs) {
            product *= x;
        }
        return product;
    }

    public static short productShort(Iterable<Short> xs) {
        short product = 1;
        for (short x : xs) {
            product *= x;
        }
        return product;
    }

    public static int productInteger(Iterable<Integer> xs) {
        int product = 1;
        for (int x : xs) {
            product *= x;
        }
        return product;
    }

    public static long productLong(Iterable<Long> xs) {
        long product = 1;
        for (long x : xs) {
            product *= x;
        }
        return product;
    }

    public static float productFloat(Iterable<Float> xs) {
        float product = 1;
        for (float x : xs) {
            product *= x;
        }
        return product;
    }

    public static double productDouble(Iterable<Double> xs) {
        double product = 1;
        for (double x : xs) {
            product *= x;
        }
        return product;
    }

    public static BigInteger productBigInteger(Iterable<BigInteger> xs) {
        BigInteger product = BigInteger.ONE;
        for (BigInteger x : xs) {
            product = product.multiply(x);
        }
        return product;
    }

    public static BigDecimal productBigDecimal(Iterable<BigDecimal> xs) {
        BigDecimal product = BigDecimal.ONE;
        for (BigDecimal x : xs) {
            product = product.multiply(x);
        }
        return product;
    }

    public static <T extends Comparable<T>> T maximum(Iterable<T> xs) {
        T max = null;
        for (T x : xs) {
            if (gt(x, max)) {
                max = x;
            }
        }
        return max;
    }

    public static <T extends Comparable<T>> T minimum(Iterable<T> xs) {
        T min = null;
        for (T x : xs) {
            if (lt(x, min)) {
                min = x;
            }
        }
        return min;
    }

    public static <T> Iterable<T> iterate(Function<T, T> f, T x) {
        return () -> new Iterator<T>() {
            private T current = x;
            private boolean firstTime = true;

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public T next() {
                if (firstTime) {
                    firstTime = false;
                } else {
                    current = f.apply(current);
                }
                return current;
            }
        };
    }

    public static Iterable<Byte> range(byte a, byte b) {
        return () -> new Iterator<Byte>() {
            private byte x = a;

            @Override
            public boolean hasNext() {
                return x != b;
            }

            @Override
            public Byte next() {
                return x++;
            }
        };
    }

    public static Iterable<Short> range(short a, short b) {
        return () -> new Iterator<Short>() {
            private short x = a;

            @Override
            public boolean hasNext() {
                return x != b;
            }

            @Override
            public Short next() {
                return x++;
            }
        };
    }

    public static Iterable<Integer> range(int a, int b) {
        return () -> new Iterator<Integer>() {
            private int x = a;

            @Override
            public boolean hasNext() {
                return x != b;
            }

            @Override
            public Integer next() {
                return x++;
            }
        };
    }

    public static Iterable<Long> range(long a, long b) {
        return () -> new Iterator<Long>() {
            private long x = a;

            @Override
            public boolean hasNext() {
                return x != b;
            }

            @Override
            public Long next() {
                return x++;
            }
        };
    }

    public static Iterable<BigInteger> range(BigInteger a, BigInteger b) {
        return () -> new Iterator<BigInteger>() {
            private BigInteger x = a;

            @Override
            public boolean hasNext() {
                return !x.equals(b);
            }

            @Override
            public BigInteger next() {
                BigInteger oldX = x;
                x = x.add(BigInteger.ONE);
                return oldX;
            }
        };
    }

    public static <T> Iterable<T> repeat(T x) {
        return () -> new Iterator<T>() {
            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public T next() {
                return x;
            }
        };
    }

    public static <T> Iterable<T> replicate(int n, T x) {
        return () -> new Iterator<T>() {
            private int i = 0;

            @Override
            public boolean hasNext() {
                return i < n;
            }

            @Override
            public T next() {
                i++;
                return x;
            }
        };
    }

    public static <T> Iterable<T> replicate(BigInteger n, T x) {
        return () -> new Iterator<T>() {
            private BigInteger i = BigInteger.ZERO;

            @Override
            public boolean hasNext() {
                return lt(i, n);
            }

            @Override
            public T next() {
                i = i.add(BigInteger.ONE);
                return x;
            }
        };
    }

    public static <T> Iterable<T> cycle(Iterable<T> xs) {
        return () -> new Iterator<T>() {
            private Iterator<T> xsi = xs.iterator();

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public T next() {
                if (!xsi.hasNext()) xsi = xs.iterator();
                return xsi.next();
            }
        };
    }

    public static <T> Iterable<T> take(int n, Iterable<T> xs) {
        return () -> new Iterator<T>() {
            private int i = 0;
            private final Iterator<T> xsi = xs.iterator();

            @Override
            public boolean hasNext() {
                return i < n && xsi.hasNext();
            }

            @Override
            public T next() {
                i++;
                return xsi.next();
            }
        };
    }

    public static <T> Iterable<T> take(BigInteger n, Iterable<T> xs) {
        return () -> new Iterator<T>() {
            private BigInteger i = BigInteger.ZERO;
            private final Iterator<T> xsi = xs.iterator();

            @Override
            public boolean hasNext() {
                return lt(i, n) && xsi.hasNext();
            }

            @Override
            public T next() {
                i = i.add(BigInteger.ONE);
                return xsi.next();
            }
        };
    }

    public static <T> Iterable<T> drop(int n, Iterable<T> xs) {
        return () -> new Iterator<T>() {
            private final Iterator<T> xsi = xs.iterator();
            {
                int i = n;
                while (xsi.hasNext()) {
                    if (i <= 0) break;
                    xsi.next();
                    i--;
                }
            }

            @Override
            public boolean hasNext() {
                return xsi.hasNext();
            }

            @Override
            public T next() {
                return xsi.next();
            }
        };
    }

    public static <T> Iterable<T> drop(BigInteger n, Iterable<T> xs) {
        return () -> new Iterator<T>() {
            private final Iterator<T> xsi = xs.iterator();
            {
                BigInteger i = n;
                while (xsi.hasNext()) {
                    if (le(i, BigInteger.ZERO)) break;
                    xsi.next();
                    i = i.subtract(BigInteger.ONE);
                }
            }

            @Override
            public boolean hasNext() {
                return xsi.hasNext();
            }

            @Override
            public T next() {
                return xsi.next();
            }
        };
    }

    public static <T> Pair<Iterable<T>, Iterable<T>> splitAt(int n, Iterable<T> xs) {
        return new Pair<>(take(n, xs), drop(n, xs));
    }

    public static <T> Pair<Iterable<T>, Iterable<T>> splitAt(BigInteger n, Iterable<T> xs) {
        return new Pair<>(take(n, xs), drop(n, xs));
    }

    public static <T> Iterable<T> takeWhile(Predicate<T> p, Iterable<T> xs) {
        return () -> new Iterator<T>() {
            private final Iterator<T> xsi = xs.iterator();
            private T next;
            private boolean hasNext;
            {
                advanceNext();
            }

            @Override
            public boolean hasNext() {
                return hasNext;
            }

            @Override
            public T next() {
                T current = next;
                advanceNext();
                return current;
            }

            private void advanceNext() {
                if (xsi.hasNext()) {
                    next = xsi.next();
                    hasNext = p.test(next);
                } else {
                    hasNext = false;
                }
            }
        };
    }

    public static <T> Iterable<T> filter(Predicate<T> p, Iterable<T> xs) {
        return () -> new Iterator<T>() {
            private final Iterator<T> xsi = xs.iterator();
            private T next;
            private boolean hasNext;
            {
                advanceNext();
            }

            @Override
            public boolean hasNext() {
                return hasNext;
            }

            @Override
            public T next() {
                T current = next;
                advanceNext();
                return current;
            }

            private void advanceNext() {
                while (xsi.hasNext()) {
                    next = xsi.next();
                    if (p.test(next)) {
                        hasNext = true;
                        return;
                    }
                }
                hasNext = false;
            }
        };
    }

    public static <S, T> Iterable<Pair<S, T>> zip(Iterable<S> fsts, Iterable<T> snds) {
        return () -> new Iterator<Pair<S, T>>() {
            private final Iterator<S> fstsi = fsts.iterator();
            private final Iterator<T> sndsi = snds.iterator();

            @Override
            public boolean hasNext() {
                return fstsi.hasNext() && sndsi.hasNext();
            }

            @Override
            public Pair<S, T> next() {
                return new Pair<>(fstsi.next(), sndsi.next());
            }
        };
    }

    public static <A, B, C> Iterable<Triple<A, B, C>> zip3(Iterable<A> as, Iterable<B> bs, Iterable<C> cs) {
        return () -> new Iterator<Triple<A, B, C>>() {
            private final Iterator<A> asi = as.iterator();
            private final Iterator<B> bsi = bs.iterator();
            private final Iterator<C> csi = cs.iterator();

            @Override
            public boolean hasNext() {
                return asi.hasNext() && bsi.hasNext() && csi.hasNext();
            }

            @Override
            public Triple<A, B, C> next() {
                return new Triple<>(asi.next(), bsi.next(), csi.next());
            }
        };
    }

//    public static <A, B, C, D> Iterable<Quadruple<A, B, C, D>> zip4(Iterable<A> as, Iterable<B> bs, Iterable<C> cs, Iterable<D> ds) {
//        return () -> new Iterator<Quadruple<A, B, C, D>>() {
//            private Iterator<A> asi = as.iterator();
//            private Iterator<B> bsi = bs.iterator();
//            private Iterator<C> csi = cs.iterator();
//            private Iterator<D> dsi = ds.iterator();
//
//            @Override
//            public boolean hasNext() {
//                return asi.hasNext() && bsi.hasNext() && csi.hasNext() && dsi.hasNext();
//            }
//
//            @Override
//            public Quadruple<A, B, C, D> next() {
//                return Quadruple.of(asi.next(), bsi.next(), csi.next(), dsi.next());
//            }
//        };
//    }

    public static <S, T> Iterable<Pair<S, T>> zipPadded(S fstPad, T sndPad, Iterable<S> fsts, Iterable<T> snds) {
        return () -> new Iterator<Pair<S, T>>() {
            private final Iterator<S> fstsi = fsts.iterator();
            private final Iterator<T> sndsi = snds.iterator();

            @Override
            public boolean hasNext() {
                return fstsi.hasNext() || sndsi.hasNext();
            }

            @Override
            public Pair<S, T> next() {
                S fst = fstsi.hasNext() ? fstsi.next() : fstPad;
                T snd = sndsi.hasNext() ? sndsi.next() : sndPad;
                return new Pair<>(fst, snd);
            }
        };
    }

    public static <A, B, C> Iterable<Triple<A, B, C>> zip3Padded(A aPad, B bPad, C cPad, Iterable<A> as, Iterable<B> bs, Iterable<C> cs) {
        return () -> new Iterator<Triple<A, B, C>>() {
            private final Iterator<A> asi = as.iterator();
            private final Iterator<B> bsi = bs.iterator();
            private final Iterator<C> csi = cs.iterator();

            @Override
            public boolean hasNext() {
                return asi.hasNext() || bsi.hasNext() || csi.hasNext();
            }

            @Override
            public Triple<A, B, C> next() {
                A a = asi.hasNext() ? asi.next() : aPad;
                B b = bsi.hasNext() ? bsi.next() : bPad;
                C c = csi.hasNext() ? csi.next() : cPad;
                return new Triple<>(a, b, c);
            }
        };
    }

//    public static <A, B, C, D> Iterable<Quadruple<A, B, C, D>> zip4Padded(A aPad, B bPad, C cPad, D dPad, Iterable<A> as, Iterable<B> bs, Iterable<C> cs, Iterable<D> ds) {
//        return () -> new Iterator<Quadruple<A, B, C, D>>() {
//            private Iterator<A> asi = as.iterator();
//            private Iterator<B> bsi = bs.iterator();
//            private Iterator<C> csi = cs.iterator();
//            private Iterator<D> dsi = ds.iterator();
//
//            @Override
//            public boolean hasNext() {
//                return asi.hasNext() || bsi.hasNext() || csi.hasNext() || dsi.hasNext();
//            }
//
//            @Override
//            public Quadruple<A, B, C, D> next() {
//                A a = asi.hasNext() ? asi.next() : aPad;
//                B b = bsi.hasNext() ? bsi.next() : bPad;
//                C c = csi.hasNext() ? csi.next() : cPad;
//                D d = dsi.hasNext() ? dsi.next() : dPad;
//                return Quadruple.of(a, b, c, d);
//            }
//        };
//    }

    public static <S, T, O> Iterable<O> zipWith(Function<Pair<S, T>, O> f, Iterable<S> fsts, Iterable<T> snds) {
        return map(f, zip(fsts, snds));
    }

    public static <A, B, C, O> Iterable<O> zipWith3(Function<Triple<A, B, C>, O> f, Iterable<A> as, Iterable<B> bs, Iterable<C> cs) {
        return map(f, zip3(as, bs, cs));
    }

//    public static <A, B, C, D, O> Iterable<O> zipWith4(Function<Quadruple<A, B, C, D>, O> f, Iterable<A> as, Iterable<B> bs, Iterable<C> cs, Iterable<D> ds) {
//        return map(f, zip4(as, bs, cs, ds));
//    }

    public static <S, T, O> Iterable<O> zipWithPadded(Function<Pair<S, T>, O> f, S fstPad, T sndPad, Iterable<S> fsts, Iterable<T> snds) {
        return map(f, zipPadded(fstPad, sndPad, fsts, snds));
    }

    public static <A, B, C, O> Iterable<O> zipWith3Padded(Function<Triple<A, B, C>, O> f, A aPad, B bPad, C cPad, Iterable<A> as, Iterable<B> bs, Iterable<C> cs) {
        return map(f, zip3Padded(aPad, bPad, cPad, as, bs, cs));
    }

//    public static <A, B, C, D, O> Iterable<O> zipWith4Padded(Function<Quadruple<A, B, C, D>, O> f, A aPad, B bPad, C cPad, D dPad, Iterable<A> as, Iterable<B> bs, Iterable<C> cs, Iterable<D> ds) {
//        return map(f, zip4Padded(aPad, bPad, cPad, dPad, as, bs, cs, ds));
//    }
}
