package mho.haskellesque.iterables;

import mho.haskellesque.structures.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

import static mho.haskellesque.ordering.Ordering.*;

public final class IterableUtils {
    /**
     * Disallow instantiation
     */
    private IterableUtils() {}

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
     * Converts an <tt>Iterable</tt> to a <tt>List</tt>. Only works for finite <tt>Iterable</tt>s. The resulting list
     * may be modified, but the modifications will not affect the original <tt>Iterable</tt>.
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
     * O(1) additional memory. The <tt>Iterable</tt> produced does not support removing elements.
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

    public static @NotNull Iterable<Byte> range(byte a) {
        return range(a, Byte.MAX_VALUE);
    }

    public static @NotNull Iterable<Short> range(short a) {
        return range(a, Short.MAX_VALUE);
    }

    public static @NotNull Iterable<Integer> range(int a) {
        return range(a, Integer.MAX_VALUE);
    }

    public static @NotNull Iterable<Long> range(long a) {
        return range(a, Long.MAX_VALUE);
    }

    public static @NotNull Iterable<BigInteger> range(@NotNull BigInteger a) {
        return iterate(bi -> bi.add(BigInteger.ONE), a);
    }

    public static @NotNull Iterable<Character> range(char a) {
        return range(a, Character.MAX_VALUE);
    }

    public static @NotNull Iterable<Byte> range(byte a, byte b) {
        if (a > b) return new ArrayList<>();
        return () -> new Iterator<Byte>() {
            private byte x = a;
            private boolean reachedEnd;

            @Override
            public boolean hasNext() {
                return !reachedEnd;
            }

            @Override
            public Byte next() {
                reachedEnd = x == b;
                return x++;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static @NotNull Iterable<Short> range(short a, short b) {
        if (a > b) return new ArrayList<>();
        return () -> new Iterator<Short>() {
            private short x = a;
            private boolean reachedEnd;

            @Override
            public boolean hasNext() {
                return !reachedEnd;
            }

            @Override
            public Short next() {
                reachedEnd = x == b;
                return x++;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static @NotNull Iterable<Integer> range(int a, int b) {
        if (a > b) return new ArrayList<>();
        return () -> new Iterator<Integer>() {
            private int x = a;
            private boolean reachedEnd;

            @Override
            public boolean hasNext() {
                return !reachedEnd;
            }

            @Override
            public Integer next() {
                reachedEnd = x == b;
                return x++;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static @NotNull Iterable<Long> range(long a, long b) {
        if (a > b) return new ArrayList<>();
        return () -> new Iterator<Long>() {
            private long x = a;
            private boolean reachedEnd;

            @Override
            public boolean hasNext() {
                return !reachedEnd;
            }

            @Override
            public Long next() {
                reachedEnd = x == b;
                return x++;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static @NotNull Iterable<BigInteger> range(@NotNull BigInteger a, @NotNull BigInteger b) {
        if (gt(a, b)) return new ArrayList<>();
        return () -> new Iterator<BigInteger>() {
            private BigInteger x = a;
            private boolean reachedEnd;

            @Override
            public boolean hasNext() {
                return !reachedEnd;
            }

            @Override
            public BigInteger next() {
                reachedEnd = x.equals(b);
                BigInteger oldX = x;
                x = x.add(BigInteger.ONE);
                return oldX;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static @NotNull Iterable<Character> range(char a, char b) {
        if (a > b) return new ArrayList<>();
        return () -> new Iterator<Character>() {
            private char x = a;
            private boolean reachedEnd;

            @Override
            public boolean hasNext() {
                return !reachedEnd;
            }

            @Override
            public Character next() {
                reachedEnd = x == b;
                return x++;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static @NotNull Iterable<Byte> rangeBy(byte a, byte i) {
        return () -> new Iterator<Byte>() {
            private byte x = a;
            private boolean reachedEnd;

            @Override
            public boolean hasNext() {
                return !reachedEnd;
            }

            @Override
            public Byte next() {
                byte oldX = x;
                x += i;
                reachedEnd = i > 0 ? x < a : x > a;
                return oldX;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static @NotNull Iterable<Short> rangeBy(short a, short i) {
        return () -> new Iterator<Short>() {
            private short x = a;
            private boolean reachedEnd;

            @Override
            public boolean hasNext() {
                return !reachedEnd;
            }

            @Override
            public Short next() {
                short oldX = x;
                x += i;
                reachedEnd = i > 0 ? x < a : x > a;
                return oldX;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static @NotNull Iterable<Integer> rangeBy(int a, int i) {
        return () -> new Iterator<Integer>() {
            private int x = a;
            private boolean reachedEnd;

            @Override
            public boolean hasNext() {
                return !reachedEnd;
            }

            @Override
            public Integer next() {
                int oldX = x;
                x += i;
                reachedEnd = i > 0 ? x < a : x > a;
                return oldX;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static @NotNull Iterable<Long> rangeBy(long a, long i) {
        return () -> new Iterator<Long>() {
            private long x = a;
            private boolean reachedEnd;

            @Override
            public boolean hasNext() {
                return !reachedEnd;
            }

            @Override
            public Long next() {
                long oldX = x;
                x += i;
                reachedEnd = i > 0 ? x < a : x > a;
                return oldX;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static @NotNull Iterable<BigInteger> rangeBy(@NotNull BigInteger a, @NotNull BigInteger i) {
        return () -> new Iterator<BigInteger>() {
            private BigInteger x = a;
            private boolean reachedEnd;

            @Override
            public boolean hasNext() {
                return !reachedEnd;
            }

            @Override
            public BigInteger next() {
                BigInteger oldX = x;
                x = x.add(i);
                reachedEnd = i.signum() == 1 ? lt(x, a) : gt(x, a);
                return oldX;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static @NotNull Iterable<Character> rangeBy(char a, int i) {
        return () -> new Iterator<Character>() {
            private char x = a;
            private boolean reachedEnd;

            @Override
            public boolean hasNext() {
                return !reachedEnd;
            }

            @Override
            public Character next() {
                char oldX = x;
                x += i;
                reachedEnd = i > 0 ? x < a : x > a;
                return oldX;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static Iterable<Byte> rangeBy(byte a, byte i, byte b) {
        if (i > 0 ? a > b : b > a) return new ArrayList<>();
        return () -> new Iterator<Byte>() {
            private byte x = a;
            private boolean reachedEnd;

            @Override
            public boolean hasNext() {
                return !reachedEnd;
            }

            @Override
            public Byte next() {
                byte oldX = x;
                x += i;
                reachedEnd = i > 0 ? (x > b || x < a) : (x < b || x > a);
                return oldX;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static Iterable<Short> rangeBy(short a, short i, short b) {
        if (i > 0 ? a > b : b > a) return new ArrayList<>();
        return () -> new Iterator<Short>() {
            private short x = a;
            private boolean reachedEnd;

            @Override
            public boolean hasNext() {
                return !reachedEnd;
            }

            @Override
            public Short next() {
                short oldX = x;
                x += i;
                reachedEnd = i > 0 ? (x > b || x < a) : (x < b || x > a);
                return oldX;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static Iterable<Integer> rangeBy(int a, int i, int b) {
        if (i > 0 ? a > b : b > a) return new ArrayList<>();
        return () -> new Iterator<Integer>() {
            private int x = a;
            private boolean reachedEnd;

            @Override
            public boolean hasNext() {
                return !reachedEnd;
            }

            @Override
            public Integer next() {
                int oldX = x;
                x += i;
                reachedEnd = i > 0 ? (x > b || x < a) : (x < b || x > a);
                return oldX;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static Iterable<Long> rangeBy(long a, long i, long b) {
        if (i > 0 ? a > b : b > a) return new ArrayList<>();
        return () -> new Iterator<Long>() {
            private long x = a;
            private boolean reachedEnd;

            @Override
            public boolean hasNext() {
                return !reachedEnd;
            }

            @Override
            public Long next() {
                long oldX = x;
                x += i;
                reachedEnd = i > 0 ? (x > b || x < a) : (x < b || x > a);
                return oldX;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static Iterable<BigInteger> rangeBy(BigInteger a, BigInteger i, BigInteger b) {
        if (i.signum() == 1 ? gt(a, b) : gt(b, a)) return new ArrayList<>();
        return () -> new Iterator<BigInteger>() {
            private BigInteger x = a;
            private boolean reachedEnd;

            @Override
            public boolean hasNext() {
                return !reachedEnd;
            }

            @Override
            public BigInteger next() {
                BigInteger oldX = x;
                x = x.add(i);
                reachedEnd = i.signum() == 1 ? gt(x, b) : lt(x, b);
                return oldX;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static Iterable<Character> rangeBy(char a, int i, char b) {
        if (i > 0 ? a > b : b > a) return new ArrayList<>();
        return () -> new Iterator<Character>() {
            private char x = a;
            private boolean reachedEnd;

            @Override
            public boolean hasNext() {
                return !reachedEnd;
            }

            @Override
            public Character next() {
                char oldX = x;
                x += i;
                reachedEnd = i > 0 ? (x > b || x < a) : (x < b || x > a);
                return oldX;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    /**
     * Equivalent of Haskell's <tt>(:)</tt> list constructor. Creates an <tt>Iterable</tt> whose first element is
     * <tt>x</tt> and whose remaining elements are given by <tt>xs</tt>. <tt>xs</tt> may be infinite, in which case the
     * result is also infinite. Uses O(1) additional memory. The <tt>Iterable</tt> produced does not support removing
     * elements.
     *
     * <ul>
     *  <li><tt>x</tt> can be anything.</li>
     *  <li><tt>xs</tt> must be non-null.</li>
     *  <li>The result is a non-empty <tt>Iterable</tt>.</li>
     * </ul>
     *
     * Result length is |<tt>xs</tt>|+1
     *
     * @param x the first element of the <tt>Iterable</tt> to be created
     * @param xs the second-through-last elements of the <tt>Iterable</tt> to be created
     * @param <T> the element type of the <tt>Iterable</tt> to be created
     * @return the <tt>Iterable</tt> to be created
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

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    /**
     * Equivalent of Haskell's <tt>(:)</tt> list constructor. Creates a <tt>String</tt> whose first character is
     * <tt>c</tt> and whose remaining characters are given by <tt>cs</tt>. Uses O(n) additional memory, where n is the
     * length of cs.
     *
     * <ul>
     *  <li><tt>c</tt> can be anything.</li>
     *  <li><tt>cs</tt> must be non-null.</li>
     *  <li>The result is a non-empty <tt>String</tt>.</li>
     * </ul>
     *
     * Result length is |<tt>cs</tt>|+1
     *
     * @param c the first character of the <tt>String</tt> to be created
     * @param cs the second-through-last characters of the <tt>String</tt> to be created
     * @return the <tt>String</tt> to be created
     */
    public static @NotNull String cons(char c, @NotNull String cs) {
        return Character.toString(c) + cs;
    }

    /**
     * Equivalent of Haskell's <tt>(++)</tt> operator. Creates an <tt>Iterable</tt> consisting of <tt>xs</tt>'s
     * elements followed by <tt>ys</tt>'s elements. <tt>xs</tt> may be infinite, in which case the result will be equal
     * to <tt>xs</tt>. <tt>ys</tt> may be infinite, in which case the result will also be infinite. Uses O(1)
     * additional memory. The <tt>Iterable</tt> produced does not support removing elements.
     *
     * <ul>
     *  <li><tt>xs</tt> must be non-null.</li>
     *  <li><tt>ys</tt> must be non-null.</li>
     *  <li>The result is non-null.</li>
     * </ul>
     *
     * Result length is |<tt>xs</tt>|+|<tt>ys</tt>|
     *
     * @param xs an <tt>Iterable</tt>
     * @param ys another <tt>Iterable</tt>
     * @param <T> the element type of the <tt>Iterable</tt> to be created
     * @return <tt>xs</tt> concatenated with <tt>ys</tt>
     */
    public static @NotNull <T> Iterable<T> concat(@NotNull Iterable<T> xs, @NotNull Iterable<T> ys) {
        return () -> new Iterator<T>() {
            private final Iterator<T> xsi = xs.iterator();
            private final Iterator<T> ysi = ys.iterator();

            @Override
            public boolean hasNext() {
                return xsi.hasNext() || ysi.hasNext();
            }

            @Override
            public T next() {
                return (xsi.hasNext() ? xsi : ysi).next();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    /**
     * Equivalent of Haskell's <tt>(++)</tt> operator. Creates a <tt>String</tt> consisting of <tt>s</tt>'s characters
     * followed by <tt>t</tt>'s characters. Uses O(n+m) additional memory, where n is the length of <tt>s</tt> and m is
     * the length of <tt>t</tt>.
     *
     * <ul>
     *  <li><tt>s</tt> must be non-null.</li>
     *  <li><tt>t</tt> must be non-null.</li>
     *  <li>The result is non-null.</li>
     * </ul>
     *
     * Result length is |<tt>s</tt>|+|<tt>t</tt>|
     *
     * @param s a <tt>String</tt>
     * @param t a <tt>String</tt>
     * @return <tt>s</tt> concatenated with <tt>t</tt>
     */
    public static @NotNull String concat(@NotNull String s, @NotNull String t) {
        return s + t;
    }

    /**
     * Equivalent of Haskell's <tt>head</tt> function. Returns the first element of an <tt>Iterable</tt>. Works on
     * infinite <tt>Iterable</tt>s. Uses O(1) additional memory.
     *
     * <ul>
     *  <li><tt>xs</tt> must be non-empty.</li>
     *  <li>The result may be anything.</li>
     * </ul>
     *
     * @param xs an <tt>Iterable</tt>
     * @param <T> the <tt>Iterable</tt>'s element type
     * @return the <tt>Iterable</tt>'s first element
     */
    public static @Nullable <T> T head(@NotNull Iterable<T> xs) {
        return xs.iterator().next();
    }

    /**
     * Equivalent of Haskell's <tt>head</tt> function. Returns the first element of a <tt>List</tt>. Uses O(1)
     * additional memory.
     *
     * <ul>
     *  <li><tt>xs</tt> must be non-empty.</li>
     *  <li>The result may be anything.</li>
     * </ul>
     *
     * @param xs a <tt>List</tt>
     * @param <T> the <tt>List</tt>'s element type
     * @return the <tt>List</tt>'s first element
     */
    public static @Nullable <T> T head(@NotNull List<T> xs) {
        return xs.get(0);
    }

    /**
     * Equivalent of Haskell's <tt>head</tt> function. Returns the first element of a <tt>SortedSet</tt>. Uses O(1)
     * additional memory.
     *
     * <ul>
     *  <li><tt>xs</tt> must be non-empty.</li>
     *  <li>The result may be anything.</li>
     * </ul>
     *
     * @param xs a <tt>SortedSet</tt>
     * @param <T> the <tt>SortedSet</tt>'s element type
     * @return the <tt>SortedSet</tt>'s first element
     */
    public static @Nullable <T> T head(@NotNull SortedSet<T> xs) {
        return xs.first();
    }

    /**
     * Equivalent of Haskell's <tt>head</tt> function. Returns the first character of a <tt>String</tt>. Uses O(1)
     * additional memory.
     *
     * <ul>
     *  <li><tt>s</tt> must be non-empty.</li>
     *  <li>The result may be any <tt>char</tt>.</li>
     * </ul>
     *
     * @param s a <tt>String</tt>
     * @return the <tt>String</tt>'s first character
     */
    public static char head(@NotNull String s) {
        return s.charAt(0);
    }

    /**
     * Equivalent of Haskell's <tt>last</tt> function. Returns the last element of an <tt>Iterable</tt>. Only works on
     * finite <tt>Iterable</tt>s. Uses O(1) additional memory.
     *
     * <ul>
     *  <li><tt>xs</tt> must be non-empty and finite.</li>
     *  <li>The result may be anything.</li>
     * </ul>
     *
     * @param xs an <tt>Iterable</tt>
     * @param <T> the <tt>Iterable</tt>'s element type
     * @return the <tt>Iterable</tt>'s last element
     */
    public static @Nullable <T> T last(@NotNull Iterable<T> xs) {
        T previous = null;
        boolean empty = true;
        for (T x : xs) {
            empty = false;
            previous = x;
        }
        if (empty)
            throw new NoSuchElementException();
        return previous;
    }

    /**
     * Equivalent of Haskell's <tt>last</tt> function. Returns the last element of a <tt>List</tt>. Uses O(1)
     * additional memory.
     *
     * <ul>
     *  <li><tt>xs</tt> must be non-empty.</li>
     *  <li>The result may be anything.</li>
     * </ul>
     *
     * @param xs a <tt>List</tt>
     * @param <T> the <tt>List</tt>'s element type
     * @return the <tt>List</tt>'s last element
     */
    public static @Nullable <T> T last(@NotNull List<T> xs) {
        return xs.get(xs.size() - 1);
    }

    /**
     * Equivalent of Haskell's <tt>last</tt> function. Returns the last element of a <tt>SortedSet</tt>. Uses O(1)
     * additional memory.
     *
     * <ul>
     *  <li><tt>xs</tt> must be non-empty.</li>
     *  <li>The result may be anything.</li>
     * </ul>
     *
     * @param xs a <tt>SortedSet</tt>
     * @param <T> the <tt>SortedSet</tt>'s element type
     * @return the <tt>SortedSet</tt>'s last element
     */
    public static @Nullable <T> T last(@NotNull SortedSet<T> xs) {
        return xs.last();
    }

    /**
     * Equivalent of Haskell's <tt>last</tt> function. Returns the last character of a <tt>String</tt>. Uses O(1)
     * additional memory.
     *
     * <ul>
     *  <li><tt>s</tt> must be non-empty.</li>
     *  <li>The result may be any <tt>char</tt>.</li>
     * </ul>
     *
     * @param s a <tt>String</tt>
     * @return the <tt>String</tt>'s last character
     */
    public static char last(@NotNull String s) {
        return s.charAt(s.length() - 1);
    }

    /**
     * Equivalent of Haskell's <tt>tail</tt> function. Returns all elements of an <tt>Iterable</tt> but the first.
     * <tt>xs</tt> may be infinite, in which the result will also be infinite. Uses O(1) additional memory. The
     * <tt>Iterable</tt> produced does not support removing elements.
     *
     * <ul>
     *  <li><tt>xs</tt> must be non-empty.</li>
     *  <li>The result is non-null.</li>
     * </ul>
     *
     * Result length is |<tt>xs</tt>|&#x2212;1
     *
     * @param xs an <tt>Iterable</tt>
     * @param <T> the <tt>Iterable</tt>'s element type
     * @return an <tt>Iterable</tt> containing all elements of <tt>xs</tt> but the first
     */
    public static @NotNull <T> Iterable<T> tail(@NotNull Iterable<T> xs) {
        if (isEmpty(xs))
            throw new NoSuchElementException();
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

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    /**
     * Equivalent of Haskell's <tt>tail</tt> function. Given a <tt>String</tt>, returns a <tt>String</tt> containing
     * all of its characters but the first. Uses O(n) additional memory, where n is the length of <tt>s</tt>.
     *
     * <ul>
     *  <li><tt>s</tt> must be non-empty.</li>
     *  <li>The result may be any <tt>char</tt>.</li>
     * </ul>
     *
     * Result length is |<tt>s</tt>|&#x2212;1
     *
     * @param s a <tt>String</tt>
     * @return a <tt>String</tt> containing all characters of <tt>s</tt> but the first
     */
    public static @NotNull String tail(@NotNull String s) {
        return s.substring(1);
    }

    /**
     * Equivalent of Haskell's <tt>init</tt> function. Returns all elements of an <tt>Iterable</tt> but the last.
     * <tt>xs</tt> may be infinite, in which the result will be <tt>xs</tt>. Uses O(1) additional memory. The
     * <tt>Iterable</tt> produced does not support removing elements.
     *
     * <ul>
     *  <li><tt>xs</tt> must be non-empty.</li>
     *  <li>The result is non-null.</li>
     * </ul>
     *
     * Result length is |<tt>xs</tt>|&#x2212;1
     *
     * @param xs an <tt>Iterable</tt>
     * @param <T> the <tt>Iterable</tt>'s element type
     * @return an <tt>Iterable</tt> containing all elements of <tt>xs</tt> but the last
     */
    public static @NotNull <T> Iterable<T> init(@NotNull Iterable<T> xs) {
        if (isEmpty(xs))
            throw new NoSuchElementException();
        return new Iterable<T>() {
            @Override
            public Iterator<T> iterator() {
                return new Iterator<T>() {
                    private final Iterator<T> xsi = xs.iterator();
                    private T next = xsi.next();

                    @Override
                    public boolean hasNext() {
                        return xsi.hasNext();
                    }

                    @Override
                    public T next() {
                        T oldNext = next;
                        next = xsi.next();
                        return oldNext;
                    }

                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException("cannot remove from this iterator");
                    }
                };
            }
        };
    }

    /**
     * Equivalent of Haskell's <tt>tail</tt> function. Given a <tt>String</tt>, returns a <tt>String</tt> containing
     * all of its characters but the last. Uses O(n) additional memory, where n is the length of <tt>s</tt>.
     *
     * <ul>
     *  <li><tt>s</tt> must be non-empty.</li>
     *  <li>The result may be any <tt>char</tt>.</li>
     * </ul>
     *
     * Result length is |<tt>s</tt>|&#x2212;1
     *
     * @param s a <tt>String</tt>
     * @return a <tt>String</tt> containing all characters of <tt>s</tt> but the last
     */
    public static @NotNull String init(@NotNull String s) {
        return s.substring(0, s.length() - 1);
    }

    /**
     * Equivalent of Haskell's <tt>null</tt> function. Tests whether an <tt>Iterable</tt> contains no elements.
     * <tt>xs</tt> may be infinite. Uses O(1) additional space.
     *
     * <ul>
     *  <li><tt>xs</tt> must be non-null.</li>
     *  <li>The result may be either <tt>boolean</tt>.</li>
     * </ul>
     *
     * @param xs an <tt>Iterable</tt>
     * @param <T> the <tt>Iterable</tt>'s element type
     * @return whether <tt>xs</tt> is empty
     */
    public static <T> boolean isEmpty(@NotNull Iterable<T> xs) {
        return !xs.iterator().hasNext();
    }

    /**
     * Equivalent of Haskell's <tt>null</tt> function. Tests whether a <tt>Collection</tt> contains no elements. Uses
     * O(1) additional space.
     *
     * <ul>
     *  <li><tt>xs</tt> must be non-null.</li>
     *  <li>The result may be either <tt>boolean</tt>.</li>
     * </ul>
     *
     * @param xs a <tt>Collection</tt>
     * @param <T> the <tt>Collection</tt>'s element type
     * @return whether <tt>xs</tt> is empty
     */
    public static <T> boolean isEmpty(@NotNull Collection<T> xs) {
        return xs.isEmpty();
    }

    /**
     * Equivalent of Haskell's <tt>null</tt> function. Tests whether a <tt>String</tt> contains no characters. Uses
     * O(1) additional space.
     *
     * <ul>
     *  <li><tt>xs</tt> must be non-null.</li>
     *  <li>The result may be either <tt>boolean</tt>.</li>
     * </ul>
     *
     * @param s a <tt>String</tt>
     * @return whether <tt>s</tt> is empty
     */
    public static boolean isEmpty(@NotNull String s) {
        return s.isEmpty();
    }

    /**
     * Equivalent of Haskell's <tt>length</tt> function. Returns the number of elements in an <tt>Iterable</tt>. Only
     * works on finite <tt>Iterable</tt>s. Uses O(1) additional space.
     *
     * <ul>
     *  <li><tt>xs</tt> must be finite.</li>
     *  <li>The result is non-negative.</li>
     * </ul>
     *
     * @param xs an <tt>Iterable</tt>
     * @param <T> the <tt>Iterable</tt>'s element type
     * @return the <tt>Iterable</tt>'s length
     */
    public static <T> int length(@NotNull Iterable<T> xs) {
        int i = 0;
        for (T x : xs) {
            i++;
        }
        return i;
    }

    /**
     * Equivalent of Haskell's <tt>length</tt> function. Returns the number of elements in an <tt>Iterable</tt>. Only
     * works on finite <tt>Iterable</tt>s. Uses O(log(n)) additional space, where n is <tt>xs</tt>'s length; but it's
     * effectively constant space.
     *
     * <ul>
     *  <li><tt>xs</tt> must be finite.</li>
     *  <li>The result is non-negative.</li>
     * </ul>
     *
     * @param xs an <tt>Iterable</tt>
     * @param <T> the <tt>Iterable</tt>'s element type
     * @return the <tt>Iterable</tt>'s length
     */
    public static @NotNull <T> BigInteger bigIntegerLength(@NotNull Iterable<T> xs) {
        BigInteger bi = BigInteger.ZERO;
        for (T x : xs) {
            bi = bi.add(BigInteger.ONE);
        }
        return bi;
    }

    /**
     * Equivalent of Haskell's <tt>length</tt> function. Returns the number of elements in a <tt>Collection</tt>. Uses
     * O(1) additional space.
     *
     * <ul>
     *  <li><tt>xs</tt> must be non-null.</li>
     *  <li>The result is non-negative.</li>
     * </ul>
     *
     * @param xs a <tt>Collection</tt>
     * @param <T> the <tt>Collection</tt>'s element type
     * @return the <tt>Collection</tt>'s length
     */
    public static <T> int length(@NotNull Collection<T> xs) {
        return xs.size();
    }

    /**
     * Equivalent of Haskell's <tt>length</tt> function. Returns the number of characters in a <tt>String</tt>. Uses
     * O(1) additional space.
     *
     * <ul>
     *  <li><tt>xs</tt> must be non-null.</li>
     *  <li>The result is non-negative.</li>
     * </ul>
     *
     * @param s a <tt>String</tt>
     * @return the <tt>String</tt>'s length
     */
    public static int length(@NotNull String s) {
        return s.length();
    }

    /**
     * Equivalent of Haskell's <tt>map</tt> function. Transforms one <tt>Iterable</tt> into another by applying a
     * function to each element. <tt>xs</tt> may be infinite, in which case the result is also infinite. Uses O(1)
     * additional memory. The <tt>Iterable</tt> produced does not support removing elements.
     *
     * <ul>
     *  <li><tt>f</tt> must be non-null.</li>
     *  <li><tt>xs</tt> must be non-null.</li>
     *  <li><tt>xs</tt> must only contain elements that are valid inputs for <tt>f</tt>.</li>
     *  <li>The result is non-null.</li>
     * </ul>
     *
     * Result length is |<tt>xs</tt>|
     *
     * @param f the function that transforms each element in the <tt>Iterable</tt>
     * @param xs the <tt>Iterable</tt>
     * @param <A> the type of the original <tt>Iterable</tt>'s elements
     * @param <B> the type of the output <tt>Iterable</tt>'s elements
     * @return an <tt>Iterable</tt> containing the elements of <tt>xs</tt> transformed by <tt>f</tt>
     */
    public static @NotNull <A, B> Iterable<B> map(@NotNull Function<A, B> f, @NotNull Iterable<A> xs) {
        return () -> new Iterator<B>() {
            private final Iterator<A> xsi = xs.iterator();

            @Override
            public boolean hasNext() {
                return xsi.hasNext();
            }

            @Override
            public B next() {
                return f.apply(xsi.next());
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    /**
     * Equivalent of Haskell's <tt>map</tt> function. Transforms one <tt>String</tt> into another by applying a
     * function to each character. Uses O(n) additional memory, where n is the length of the input string.
     *
     * <ul>
     *  <li><tt>f</tt> must be non-null.</li>
     *  <li><tt>xs</tt> must be non-null.</li>
     *  <li><tt>xs</tt> must only contain characters that are valid inputs for <tt>f</tt>.</li>
     *  <li>The result is non-null.</li>
     * </ul>
     *
     * Result length is |<tt>s</tt>|
     *
     * @param f the function that transforms each character in the <tt>String</tt>
     * @param s the <tt>String</tt>
     * @return a <tt>String</tt> containing the characters of <tt>s</tt> transformed by <tt>f</tt>
     */
    public static @NotNull String map(@NotNull Function<Character, Character> f, @NotNull String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            sb.append(f.apply(s.charAt(i)));
        }
        return sb.toString();
    }

    /**
     * Equivalent of Haskell's <tt>reverse</tt> function. Reverses an <tt>Iterable</tt>. <tt>xs</tt> must be finite.
     * Uses O(n) additional memory, where n is the length of <tt>xs</tt>. The resulting list may be modified, but the
     * modifications will not affect the original <tt>Iterable</tt>.
     *
     * <ul>
     *  <li><tt>xs</tt> must be finite.</li>
     *  <li>The result is non-null.</li>
     * </ul>
     *
     * Result length is |<tt>xs</tt>|
     *
     * @param xs an <tt>Iterable</tt>
     * @param <T> the <tt>Iterable</tt>'s element type
     * @return a <tt>List</tt> containing <tt>xs</tt>'s elements in reverse order
     */
    public static @NotNull <T> List<T> reverse(@NotNull Iterable<T> xs) {
        List<T> list = toList(xs);
        Collections.reverse(list);
        return list;
    }

    /**
     * Equivalent of Haskell's <tt>reverse</tt> function. Reverses a <tt>String</tt>. Uses O(n) additional memory,
     * where n is the length of <tt>s</tt>.
     *
     * <ul>
     *  <li><tt>s</tt> must be non-null.</li>
     *  <li>The result is non-null.</li>
     * </ul>
     *
     * Result length is |<tt>s</tt>|
     *
     * @param s a <tt>String</tt>
     * @return a <tt>String</tt> containing <tt>s</tt>'s characters in reverse order
     */
    public static @NotNull String reverse(@NotNull String s) {
        char[] reversed = new char[s.length()];
        for (int i = 0; i < s.length() / 2; i++) {
            int j = s.length() - i - 1;
            reversed[i] = s.charAt(j);
            reversed[j] = s.charAt(i);
        }
        if ((s.length() & 1) == 1) {
            int i = s.length() / 2;
            reversed[i] = s.charAt(i);
        }
        return new String(reversed);
    }

    /**
     * Equivalent of Haskell's <tt>intersperse</tt> function. Given an <tt>Iterable</tt> <tt>xs</tt> and a seperator
     * <tt>sep</tt>, returns an <tt>Iterable</tt> consisting of the elements of <tt>xs</tt> with <tt>sep</tt> between
     * every adjacent pair. <tt>xs</tt> may be infinite, in which case the result is also infinite. Uses O(1)
     * additional memory. The <tt>Iterable</tt> produced does not support removing elements.
     *
     * <ul>
     *  <li><tt>sep</tt> may be anything.</li>
     *  <li><tt>xs</tt> must be non-null.</li>
     *  <li>The result is an <tt>Iterable</tt> whose odd-indexed (using 0-based indexing) elements are identical.</li>
     * </ul>
     *
     * Result length is 0 when |<tt>xs</tt>|=0, 2|<tt>xs</tt>|&#x2212;1 otherwise
     *
     * @param sep a separator
     * @param xs an <tt>Iterable</tt>
     * @param <T> the <tt>Iterable</tt>'s element type
     * @return an <tt>Iterable</tt> consisting of the elements of <tt>xs</tt> interspersed with <tt>sep</tt>
     */
    public static @NotNull <T> Iterable<T> intersperse(@Nullable T sep, @NotNull Iterable<T> xs) {
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
                    separating = false;
                    return sep;
                } else {
                    separating = true;
                    return xsi.next();
                }
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    /**
     * Equivalent of Haskell's <tt>intersperse</tt> function. Given a <tt>String</tt> <tt>s</tt> and a seperator
     * <tt>sep</tt>, returns a <tt>String</tt> consisting of the characters of <tt>s</tt> with <tt>sep</tt> between
     * every adjacent pair. Uses O(n) additional memory, where n is the length of <tt>s</tt>.
     *
     * <ul>
     *  <li><tt>sep</tt> may be any <tt>char</tt>.</li>
     *  <li><tt>s</tt> must be non-null.</li>
     *  <li>The result is a <tt>String</tt> whose odd-indexed (using 0-based indexing) characters are identical.</li>
     * </ul>
     *
     * Result length is 0 when |<tt>s</tt>|=0, 2|<tt>s</tt>|&#x2212;1 otherwise
     *
     * @param sep a separator
     * @param s a <tt>String</tt>
     * @return a <tt>String</tt> consisting of the characters of <tt>s</tt> interspersed with <tt>sep</tt>
     */
    public static @NotNull String intersperse(char sep, @NotNull String s) {
        if (s.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        sb.append(s.charAt(0));
        for (int i = 1; i < s.length(); i++) {
            sb.append(sep);
            sb.append(s.charAt(i));
        }
        return sb.toString();
    }

    /**
     * Equivalent of Haskell's <tt>intercalate</tt> function. Inserts an <tt>Iterable</tt> between every two adjacent
     * <tt>Iterable</tt>s in an <tt>Iterable</tt> of <tt>Iterable</tt>s, flattening the result. <tt>xss</tt>, any
     * element of <tt>xss</tt>, or <tt>xs</tt> may be infinite, in which case the result is also infinite. Uses O(1)
     * additional memory. The <tt>Iterable</tt> produced does not support removing elements.
     *
     * Result length is the sum of the lengths of <tt>xs</tt>'s elements and (0 if |<tt>xss</tt>|=0,
     * |<tt>xss</tt>|(|<tt>xs</tt>|&#x2212;1) otherwise)
     *
     * <ul>
     *  <li><tt>xs</tt> must be non-null.</li>
     *  <li><tt>xss</tt> must be non-null.</li>
     *  <li>The result is non-null.</li>
     * </ul>
     *
     * @param xs the separating <tt>Iterable</tt>
     * @param xss the separated <tt>Iterable</tt>
     * @param <T> the resulting <tt>Iterable</tt>'s element type
     * @return <tt>xss</tt> intercalated by <tt>xs</tt>
     */
    public static @NotNull <T> Iterable<T> intercalate(@NotNull Iterable<T> xs, @NotNull Iterable<Iterable<T>> xss) {
        return concat(intersperse(xs, xss));
    }

    /**
     * Equivalent of Haskell's <tt>intercalate</tt> function. Inserts a <tt>String</tt> between every two adjacent
     * <tt>String</tt>s in an <tt>Iterable</tt> of <tt>String</tt>s, flattening the result. Uses O(abc) additional
     * memory, where a is the length of <tt>strings</tt>, b is the maximum length of any string in <tt>strings</tt>,
     * and c is the length of <tt>sep</tt>.
     * The <tt>Iterable</tt> produced does not support removing elements.
     *
     * <ul>
     *  <li><tt>sep</tt> must be non-null.</li>
     *  <li><tt>strings</tt> must be finite.</li>
     *  <li>The result is non-null.</li>
     * </ul>
     *
     * Result length is the sum of the lengths of <tt>xs</tt>'s elements and (0 if |<tt>strings</tt>|=0,
     * |<tt>strings</tt>|(|<tt>sep</tt>|&#x2212;1) otherwise)
     *
     * @param sep the separating <tt>String</tt>
     * @param strings the separated <tt>String</tt>s
     * @return <tt>strings</tt> intercalated by <tt>sep</tt>
     */
    public static @NotNull String intercalate(@NotNull String sep, @NotNull Iterable<String> strings) {
        return concatStrings(intersperse(sep, strings));
    }

    /**
     * Equivalent of Haskell's <tt>transpose</tt> function. Swaps rows and columns of an <tt>Iterable</tt> of
     * <tt>Iterables</tt>. If the rows have different lengths, then the "overhanging" elements still end up in the
     * result. See test cases for examples. Any element of <tt>xss</tt> may be infinite, in which case the result will
     * be infinite. Uses O(nm) additional memory, where n is then length of <tt>xss</tt> and m is the largest amount of
     * memory used by any <tt>Iterable</tt> in <tt>xss</tt>. The <tt>Iterable</tt> produced does not support removing
     * elements.
     *
     * <ul>
     *  <li><tt>xss</tt> must be finite.</li>
     *  <li>The lengths of the result's elements are finite, non-increasing, and never 0.</li>
     * </ul>
     *
     * Result length is the maximum length of <tt>xss</tt>'s elements
     *
     * @param xss an <tt>Iterable</tt> of <tt>Iterable</tt>s
     * @param <T> the <tt>Iterable</tt>'s elements' element type
     * @return <tt>xss</tt>, transposed
     */
    public static @NotNull <T> Iterable<List<T>> transpose(@NotNull Iterable<Iterable<T>> xss) {
        return () -> new Iterator<List<T>>() {
            private final List<Iterator<T>> iterators = toList(map(Iterable::iterator, xss));

            @Override
            public boolean hasNext() {
                return any(Iterator::hasNext, iterators);
            }

            @Override
            public List<T> next() {
                List<T> nextList = new ArrayList<>();
                for (Iterator<T> iterator : iterators) {
                    if (iterator.hasNext()) {
                        nextList.add(iterator.next());
                    }
                }
                return nextList;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    /**
     * Equivalent of Haskell's <tt>transpose</tt> function. Swaps rows and columns of an <tt>Iterable</tt> of
     * <tt>String</tt>s. If the rows have different lengths, then the "overhanging" characters still end up in the
     * result. See test cases for examples. Uses O(nm) additional memory, where n is then length of <tt>xss</tt> and m
     * is the length of the longest <tt>String</tt> in <tt>xss</tt>. The <tt>Iterable</tt> produced does not support
     * removing elements.
     *
     * <ul>
     *  <li><tt>strings</tt> must be non-null.</li>
     *  <li>The lengths of the result's elements are non-increasing and never 0.</li>
     * </ul>
     *
     * Result length is the maximum length of <tt>strings</tt>'s elements
     *
     * @param strings an <tt>Iterable</tt> of <tt>String</tt>s
     * @return <tt>strings</tt>, transposed
     */
    public static @NotNull Iterable<String> transposeStrings(@NotNull Iterable<String> strings) {
        return map(
                IterableUtils::charsToString,
                transpose(map(s -> fromString(s), strings))
        );
    }

    /**
     * Equivalent of Haskell's <tt>transpose</tt> function. Swaps rows and columns of an <tt>Iterable</tt> of
     * <tt>Iterables</tt>. If the rows have different lengths, then the "overhanging" elements will be truncated; the
     * result's rows will all have equal lengths. See test cases for examples. Any element of <tt>xss</tt> may be
     * infinite, in which case the result will be infinite. Uses O(nm) additional memory, where n is then length of
     * <tt>xss</tt> and m is the largest amount of memory used by any <tt>Iterable</tt> in <tt>xss</tt>. The
     * <tt>Iterable</tt> produced does not support removing elements.
     *
     * <ul>
     *  <li><tt>xss</tt> must be finite.</li>
     *  <li>The lengths of the result's elements are finite and equal.</li>
     * </ul>
     *
     * Result length is the minimum length of <tt>xss</tt>'s elements
     *
     * @param xss an <tt>Iterable</tt> of <tt>Iterable</tt>s
     * @param <T> the <tt>Iterable</tt>'s elements' element type
     * @return <tt>xss</tt>, transposed
     */
    public static @NotNull <T> Iterable<List<T>> transposeTruncating(@NotNull Iterable<Iterable<T>> xss) {
        return () -> new Iterator<List<T>>() {
            private final List<Iterator<T>> iterators = toList(map(Iterable::iterator, xss));

            @Override
            public boolean hasNext() {
                return !iterators.isEmpty() && all(Iterator::hasNext, iterators);
            }

            @Override
            public List<T> next() {
                List<T> nextList = new ArrayList<>();
                for (Iterator<T> iterator : iterators) {
                    nextList.add(iterator.next());
                }
                return nextList;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    /**
     * Equivalent of Haskell's <tt>transpose</tt> function. Swaps rows and columns of an <tt>Iterable</tt> of
     * <tt>String</tt>s. If the rows have different lengths, then the "overhanging" characters will be truncated. See
     * test cases for examples. Uses O(nm) additional memory, where n is then length of <tt>xss</tt> and m is the
     * length of the longest <tt>String</tt> in <tt>xss</tt>. The <tt>Iterable</tt> produced does not support removing
     * elements.
     *
     * <ul>
     *  <li><tt>strings</tt> must be non-null.</li>
     *  <li>The lengths of the result's elements are equal.</li>
     * </ul>
     *
     * Result length is the minimum length of <tt>strings</tt>'s elements
     *
     * @param strings an <tt>Iterable</tt> of <tt>String</tt>s
     * @return <tt>strings</tt>, transposed
     */
    public static @NotNull Iterable<String> transposeStringsTruncating(@NotNull Iterable<String> strings) {
        return map(
                IterableUtils::charsToString,
                transposeTruncating(map(s -> fromString(s), strings))
        );
    }

    /**
     * Equivalent of Haskell's <tt>transpose</tt> function. Swaps rows and columns of an <tt>Iterable</tt> of
     * <tt>Iterables</tt>. If the rows have different lengths, then the gaps will be padded; the result's rows will all
     * have equal lengths. See test cases for examples. Any element of <tt>xss</tt> may be infinite, in which case the
     * result will be infinite. Uses O(nm) additional memory, where n is then length of <tt>xss</tt> and m is the
     * largest amount of memory used by any <tt>Iterable</tt> in <tt>xss</tt>. The <tt>Iterable</tt> produced does not
     * support removing elements.
     *
     * <ul>
     *  <li><tt>xss</tt> must be finite.</li>
     *  <li>The lengths of the result's elements are equal.</li>
     * </ul>
     *
     * Result length is the maximum length of <tt>xss</tt>'s elements
     *
     * @param xss an <tt>Iterable</tt> of <tt>Iterable</tt>s
     * @param pad the padding
     * @param <T> the <tt>Iterable</tt>'s elements' element type
     * @return <tt>xss</tt>, transposed
     */
    public static @NotNull <T> Iterable<Iterable<T>>
    transposePadded(@Nullable T pad, @NotNull Iterable<Iterable<T>> xss) {
        return () -> new Iterator<Iterable<T>>() {
            private final List<Iterator<T>> iterators = toList(map(Iterable::iterator, xss));

            @Override
            public boolean hasNext() {
                return any(Iterator::hasNext, iterators);
            }

            @Override
            public Iterable<T> next() {
                List<T> nextList = new ArrayList<>();
                for (Iterator<T> iterator : iterators) {
                    nextList.add(iterator.hasNext() ? iterator.next() : pad);
                }
                return nextList;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    /**
     * Equivalent of Haskell's <tt>transpose</tt> function. Swaps rows and columns of an <tt>Iterable</tt> of
     * <tt>String</tt>s. If the rows have different lengths, then the gaps will be padded; the result's rows will all
     * have equal lengths. Uses O(nm) additional memory, where n is then length of <tt>xss</tt> and m is the length of
     * the longest <tt>String</tt> in <tt>xss</tt>. The <tt>Iterable</tt> produced does not support removing elements.
     *
     * <ul>
     *  <li><tt>strings</tt> must be non-null.</li>
     *  <li>The lengths of the result's elements are equal.</li>
     * </ul>
     *
     * Result length is the maximum length of <tt>strings</tt>'s elements
     *
     * @param strings an <tt>Iterable</tt> of <tt>String</tt>s
     * @param pad the padding
     * @return <tt>strings</tt>, transposed
     */
    public static @NotNull Iterable<String> transposeStringsPadded(char pad, @NotNull Iterable<String> strings) {
        return map(
                IterableUtils::charsToString,
                transposePadded(pad, map(s -> fromString(s), strings))
        );
    }

    public static @Nullable <A, B> B foldl(
            @NotNull Function<Pair<B, A>, B> f,
            @Nullable B z,
            @NotNull Iterable<A> xs
    ) {
        B result = z;
        for (A x : xs) {
            result = f.apply(new Pair<B, A>(result, x));
        }
        return result;
    }

    public static @Nullable <A> A foldl1(@NotNull Function<Pair<A, A>, A> f, @NotNull Iterable<A> xs) {
        A result = null;
        boolean started = false;
        for (A x : xs) {
            if (started) {
                result = f.apply(new Pair<A, A>(result, x));
            } else {
                result = x;
                started = true;
            }
        }
        return result;
    }

    public static @Nullable <A, B> B foldr(
            @NotNull Function<Pair<A, B>, B> f,
            @Nullable B z,
            @NotNull Iterable<A> xs
    ) {
        return foldl(p -> f.apply(new Pair<>(p.b, p.a)), z, reverse(xs));
    }

    public static @Nullable <A> A foldr1(@NotNull Function<Pair<A, A>, A> f, @NotNull Iterable<A> xs) {
        return foldl1(p -> f.apply(new Pair<>(p.b, p.a)), reverse(xs));
    }

    public static @NotNull <T> Iterable<T> concat(@NotNull Iterable<Iterable<T>> xss) {
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

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static @NotNull String concatStrings(@NotNull Iterable<String> strings) {
        StringBuilder sb = new StringBuilder();
        for (String s : strings) {
            sb.append(s);
        }
        return sb.toString();
    }

    public static @NotNull <A, B> Iterable<B> concatMap(@NotNull Function<A, Iterable<B>> f, @NotNull Iterable<A> xs) {
        return concat(map(f, xs));
    }

    public static boolean and(@NotNull Iterable<Boolean> xs) {
        for (boolean x : xs) {
            if (!x) return false;
        }
        return true;
    }

    public static boolean or(@NotNull Iterable<Boolean> xs) {
        for (boolean x : xs) {
            if (x) return true;
        }
        return false;
    }

    public static <T> boolean any(@NotNull Predicate<T> predicate, @NotNull Iterable<T> xs) {
        for (T x : xs) {
            if (predicate.test(x)) return true;
        }
        return false;
    }

    public static <T> boolean all(@NotNull Predicate<T> predicate, @NotNull Iterable<T> xs) {
        for (T x : xs) {
            if (!predicate.test(x)) return false;
        }
        return true;
    }

    public static byte sumByte(@NotNull Iterable<Byte> xs) {
        return foldl(p -> (byte) (p.a + p.b), (byte) 0, xs);
    }

    public static short sumShort(@NotNull Iterable<Short> xs) {
        return foldl(p -> (short) (p.a + p.b), (short) 0, xs);
    }

    public static int sumInteger(@NotNull Iterable<Integer> xs) {
        return foldl(p -> p.a + p.b, 0, xs);
    }

    public static long sumLong(@NotNull Iterable<Long> xs) {
        return foldl(p -> p.a + p.b, 0L, xs);
    }

    public static float sumFloat(@NotNull Iterable<Float> xs) {
        return foldl(p -> p.a + p.b, 0.0f, xs);
    }

    public static double sumDouble(Iterable<Double> xs) {
        return foldl(p -> p.a + p.b, 0.0, xs);
    }

    public static @NotNull BigInteger sumBigInteger(@NotNull Iterable<BigInteger> xs) {
        return foldl(p -> p.a.add(p.b), BigInteger.ZERO, xs);
    }

    public static @NotNull BigDecimal sumBigDecimal(@NotNull Iterable<BigDecimal> xs) {
        return foldl(p -> p.a.add(p.b), BigDecimal.ZERO, xs);
    }

    public static byte productByte(@NotNull Iterable<Byte> xs) {
        return foldl(p -> (byte) (p.a * p.b), (byte) 1, xs);
    }

    public static short productShort(@NotNull Iterable<Short> xs) {
        return foldl(p -> (short) (p.a * p.b), (short) 1, xs);
    }

    public static int productInteger(@NotNull Iterable<Integer> xs) {
        return foldl(p -> p.a * p.b, 1, xs);
    }

    public static long productLong(@NotNull Iterable<Long> xs) {
        return foldl(p -> p.a * p.b, 1L, xs);
    }

    public static float productFloat(@NotNull Iterable<Float> xs) {
        return foldl(p -> p.a * p.b, 1.0f, xs);
    }

    public static double productDouble(@NotNull Iterable<Double> xs) {
        return foldl(p -> p.a * p.b, 1.0, xs);
    }

    public static @NotNull BigInteger productBigInteger(Iterable<BigInteger> xs) {
        return foldl(p -> p.a.multiply(p.b), BigInteger.ONE, xs);
    }

    public static @NotNull BigDecimal productBigDecimal(@NotNull Iterable<BigDecimal> xs) {
        return foldl(p -> p.a.multiply(p.b), BigDecimal.ONE, xs);
    }

    public static <T extends Comparable<T>> T maximum(@NotNull Iterable<T> xs) {
        return foldl1(p -> max(p.a, p.b), xs);
    }

    public static <T extends Comparable<T>> T minimum(@NotNull Iterable<T> xs) {
        return foldl1(p -> min(p.a, p.b), xs);
    }

    public static @NotNull <A, B> Iterable<B> scanl(
            @NotNull Function<Pair<B, A>, B> f,
            @Nullable B z,
            @NotNull Iterable<A> xs
    ) {
        return () -> new Iterator<B>() {
            private final Iterator<A> xsi = xs.iterator();
            private B result = z;
            private boolean firstTime = true;

            @Override
            public boolean hasNext() {
                return firstTime || xsi.hasNext();
            }

            @Override
            public B next() {
                if (firstTime) {
                    firstTime = false;
                    return result;
                } else {
                    result = f.apply(new Pair<B, A>(result, xsi.next()));
                    return result;
                }
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

//    public static @NotNull <A> Iterable<A> scanl1(@NotNull Function<Pair<A, A>, A> f, @NotNull Iterable<A> xs) {
//        return () -> new Iterator<A>() {
//            private Iterator<A> xsi = xs.iterator();
//            private A result = xsi.next();
//
//            @Override
//            public boolean hasNext() {
//                return false;
//            }
//
//            @Override
//            public A next() {
//                return null;
//            }
//
//            @Override
//            public void remove() {
//                throw new UnsupportedOperationException("cannot remove from this iterator");
//            }
//        };
//    }

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

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
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

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
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

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static <T> Iterable<T> replicate(BigInteger n, T x) {
        return () -> new Iterator<T>() {
            private BigInteger bi = BigInteger.ZERO;

            @Override
            public boolean hasNext() {
                return lt(bi, n);
            }

            @Override
            public T next() {
                bi = bi.add(BigInteger.ONE);
                return x;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static String replicate(int n, char c) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(c);
        }
        return sb.toString();
    }

    public static String replicate(BigInteger n, char c) {
        StringBuilder sb = new StringBuilder();
        for (BigInteger bi : range(BigInteger.ONE, n)) {
            sb.append(c);
        }
        return sb.toString();
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

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static @NotNull <A, B> Iterable<A> unfoldr(@NotNull Function<B, Optional<Pair<A, B>>> f, @NotNull B x) {
        return new Iterable<A>() {
            @Override
            public Iterator<A> iterator() {
                return new Iterator<A>() {
                    private boolean hasNext = true;
                    private A next;
                    private B seed = x;
                    {
                        advance();
                    }

                    @Override
                    public boolean hasNext() {
                        return hasNext;
                    }

                    @Override
                    public A next() {
                        A oldNext = next;
                        advance();
                        return oldNext;
                    }

                    private void advance() {
                        Optional<Pair<A, B>> p = f.apply(seed);
                        if (p.isPresent()) {
                            next = p.get().a;
                            seed = p.get().b;
                        } else {
                            hasNext = false;
                        }
                    }

                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException("cannot remove from this iterator");
                    }
                };
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

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static <T> Iterable<T> take(BigInteger n, Iterable<T> xs) {
        return () -> new Iterator<T>() {
            private BigInteger bi = BigInteger.ZERO;
            private final Iterator<T> xsi = xs.iterator();

            @Override
            public boolean hasNext() {
                return lt(bi, n) && xsi.hasNext();
            }

            @Override
            public T next() {
                bi = bi.add(BigInteger.ONE);
                return xsi.next();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static String take(int n, String s) {
        return s.substring(0, n);
    }

    public static String take(BigInteger n, String s) {
        return s.substring(0, n.intValueExact());
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

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static <T> Iterable<T> drop(BigInteger n, Iterable<T> xs) {
        return () -> new Iterator<T>() {
            private final Iterator<T> xsi = xs.iterator();
            {
                BigInteger bi = n;
                while (xsi.hasNext()) {
                    if (le(bi, BigInteger.ZERO)) break;
                    xsi.next();
                    bi = bi.subtract(BigInteger.ONE);
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

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static <T> Iterable<T> pad(T pad, int length, Iterable<T> xs) {
        if (length < 0)
            throw new IllegalArgumentException("cannot pad with a negative length");
        return take(length, concat(xs, repeat(pad)));
    }

    public static <T> Iterable<T> pad(T pad, BigInteger length, Iterable<T> xs) {
        if (length.signum() == -1)
            throw new IllegalArgumentException("cannot pad with a negative length");
        return take(length, (Iterable<T>) concat(xs, repeat(pad)));
    }

    public static <T> String pad(char pad, int length, String s) {
        if (s.length() == length) return s;
        if (s.length() > length) return take(length, s);
        return s + replicate(length - s.length(), pad);
    }

    public static <T> String pad(char pad, BigInteger length, String s) {
        if (s.length() == length.intValueExact()) return s;
        if (s.length() > length.intValueExact()) return take(length, s);
        return s + replicate(length.intValueExact() - s.length(), pad);
    }

    public static <T> Pair<Iterable<T>, Iterable<T>> splitAt(int n, Iterable<T> xs) {
        return new Pair<>(take(n, xs), drop(n, xs));
    }

    public static <T> Pair<Iterable<T>, Iterable<T>> splitAt(BigInteger n, Iterable<T> xs) {
        return new Pair<>(take(n, xs), drop(n, xs));
    }

    public static <T> Iterable<T> takeWhile(Predicate<T> p, Iterable<T> xs) {
        return new Iterable<T>() {
            @Override
            public Iterator<T> iterator() {
                return new Iterator<T>() {
                    private final Iterator<T> xsi = xs.iterator();
                    private T next;
                    private boolean hasNext;
                    {
                        advance();
                    }

                    @Override
                    public boolean hasNext() {
                        return hasNext;
                    }

                    @Override
                    public T next() {
                        T current = next;
                        advance();
                        return current;
                    }

                    private void advance() {
                        if (xsi.hasNext()) {
                            next = xsi.next();
                            hasNext = p.test(next);
                        } else {
                            hasNext = false;
                        }
                    }

                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException("cannot remove from this iterator");
                    }
                };
            }
        };
    }

    public static <T> Iterable<T> stopAt(Predicate<T> p, Iterable<T> xs) {
        return new Iterable<T>() {
            @Override
            public Iterator<T> iterator() {
                return new Iterator<T>() {
                    private final Iterator<T> xsi = xs.iterator();
                    private T next;
                    private boolean hasNext;
                    {
                        advance();
                    }

                    @Override
                    public boolean hasNext() {
                        return hasNext;
                    }

                    @Override
                    public T next() {
                        T current = next;
                        advance();
                        return current;
                    }

                    private void advance() {
                        if (next != null && p.test(next)) {
                            hasNext = false;
                        } else {
                            hasNext = xsi.hasNext();
                            if (xsi.hasNext()) {
                                next = xsi.next();
                            }
                        }
                    }

                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException("cannot remove from this iterator");
                    }
                };
            }
        };
    }

    public static <T> Iterable<List<T>> chunk(int size, Iterable<T> xs) {
        return () -> new Iterator<List<T>>() {
            private final Iterator<T> xsi = xs.iterator();

            @Override
            public boolean hasNext() {
                return xsi.hasNext();
            }

            @Override
            public List<T> next() {
                List<T> chunk = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    if (!xsi.hasNext()) break;
                    chunk.add(xsi.next());
                }
                return chunk;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static <T> Iterable<String> chunk(int size, String s) {
        return () -> new Iterator<String>() {
            private int i = 0;

            @Override
            public boolean hasNext() {
                return i != s.length();
            }

            @Override
            public String next() {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < size; j++) {
                    if (i == s.length()) break;
                    sb.append(s.charAt(i++));
                }
                return sb.toString();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static <T> Iterable<List<T>> chunkPadded(T pad, int size, Iterable<T> xs) {
        return () -> new Iterator<List<T>>() {
            private final Iterator<T> xsi = xs.iterator();

            @Override
            public boolean hasNext() {
                return xsi.hasNext();
            }

            @Override
            public List<T> next() {
                List<T> chunk = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    chunk.add(xsi.hasNext() ? xsi.next() : pad);
                }
                return chunk;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static @NotNull <T> Iterable<Pair<T, Integer>> countAdjacent(@NotNull Iterable<T> xs) {
        return new Iterable<Pair<T, Integer>>() {
            @Override
            public Iterator<Pair<T, Integer>> iterator() {
                return new Iterator<Pair<T, Integer>>() {
                    private Iterator<T> xsi = xs.iterator();
                    private boolean hasNext = xsi.hasNext();
                    private boolean isLast = false;
                    private T nextX = null;
                    private Pair<T, Integer> next = null;

                    {
                        if (hasNext) {
                            nextX = xsi.next();
                        }
                        advance();
                    }

                    @Override
                    public boolean hasNext() {
                        return hasNext;
                    }

                    @Override
                    public Pair<T, Integer> next() {
                        if (isLast) {
                            hasNext = false;
                            return next;
                        } else {
                            Pair<T, Integer> oldNext = next;
                            advance();
                            return oldNext;
                        }
                    }

                    private void advance() {
                        T original = nextX;
                        int count = 0;
                        do {
                            count++;
                            if (!xsi.hasNext()) {
                                isLast = true;
                                break;
                            }
                            nextX = xsi.next();
                        } while (original == null && nextX == null || original != null && original.equals(nextX));
                        next = new Pair<>(original, count);
                    }

                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException("cannot remove from this iterator");
                    }
                };
            }
        };
    }

    public static <T> Iterable<T> mux(List<Iterable<T>> xss) {
        return concat(map(list -> list, transpose(xss)));
    }

    public static <T> String muxStrings(List<String> xss) {
        return concatStrings(transposeStrings(xss));
    }

    public static <T> List<Iterable<T>> demux(int lines, Iterable<T> xs) {
        List<Iterable<T>> demuxed = new ArrayList<>();
        for (int i = 0; i < lines; i++) {
            Iterable<Boolean> mask = concat(
                    replicate(i, false),
                    cycle(cons(true, (Iterable<Boolean>) replicate(lines - 1, false)))
            );
            demuxed.add(select(mask, xs));
        }
        return demuxed;
    }

    public static List<String> demux(int lines, String s) {
        List<String> demuxed = new ArrayList<>();
        for (int i = 0; i < lines; i++) {
            Iterable<Boolean> mask = concat(
                    replicate(i, false),
                    cycle(cons(true, (Iterable<Boolean>) replicate(lines - 1, false)))
            );
            demuxed.add(select(mask, s));
        }
        return demuxed;
    }

    public static <T> Iterable<T> filter(Predicate<T> p, Iterable<T> xs) {
        return new Iterable<T>() {
            @Override
            public Iterator<T> iterator() {
                return new Iterator<T>() {
                    private final Iterator<T> xsi = xs.iterator();
                    private T next;
                    private boolean hasNext;
                    {
                        advance();
                    }

                    @Override
                    public boolean hasNext() {
                        return hasNext;
                    }

                    @Override
                    public T next() {
                        T current = next;
                        advance();
                        return current;
                    }

                    private void advance() {
                        while (xsi.hasNext()) {
                            next = xsi.next();
                            if (p.test(next)) {
                                hasNext = true;
                                return;
                            }
                        }
                        hasNext = false;
                    }

                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException("cannot remove from this iterator");
                    }
                };
            }
        };
    }

    public static <T> T get(Iterable<T> xs, int i) {
        if (i < 0)
            throw new IndexOutOfBoundsException();
        Iterator<T> xsi = xs.iterator();
        T element = null;
        for (int j = 0; j <= i; j++) {
            if (!xsi.hasNext())
                throw new IndexOutOfBoundsException();
            element = xsi.next();
        }
        return element;
    }

    public static <T> T get(Iterable<T> xs, BigInteger i) {
        if (lt(i, BigInteger.ZERO))
            throw new IndexOutOfBoundsException();
        Iterator<T> xsi = xs.iterator();
        T element = null;
        for (BigInteger j : range(BigInteger.ONE, i)) {
            if (!xsi.hasNext())
                throw new IndexOutOfBoundsException();
            element = xsi.next();
        }
        return element;
    }

    public static <T> T get(List<T> xs, int i) {
        return xs.get(i);
    }

    public static char get(String s, int i) {
        return s.charAt(i);
    }

    public static <T> Iterable<T> select(Iterable<Boolean> bs, Iterable<T> xs) {
        return map(p -> p.b, filter(p -> p.a, (Iterable<Pair<Boolean, T>>) zip(bs, xs)));
    }

    public static <T> String select(Iterable<Boolean> bs, String s) {
        return charsToString(
                map(p -> p.b, filter(p -> p.a, (Iterable<Pair<Boolean, Character>>) zip(bs, fromString(s))))
        );
    }

    public static <A, B> Iterable<Pair<A, B>> zip(Iterable<A> as, Iterable<B> bs) {
        return () -> new Iterator<Pair<A, B>>() {
            private final Iterator<A> asi = as.iterator();
            private final Iterator<B> bsi = bs.iterator();

            @Override
            public boolean hasNext() {
                return asi.hasNext() && bsi.hasNext();
            }

            @Override
            public Pair<A, B> next() {
                return new Pair<>(asi.next(), bsi.next());
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
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

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static <A, B, C, D> Iterable<Quadruple<A, B, C, D>> zip4(
            Iterable<A> as,
            Iterable<B> bs,
            Iterable<C> cs,
            Iterable<D> ds
    ) {
        return () -> new Iterator<Quadruple<A, B, C, D>>() {
            private final Iterator<A> asi = as.iterator();
            private final Iterator<B> bsi = bs.iterator();
            private final Iterator<C> csi = cs.iterator();
            private final Iterator<D> dsi = ds.iterator();

            @Override
            public boolean hasNext() {
                return asi.hasNext() && bsi.hasNext() && csi.hasNext() && dsi.hasNext();
            }

            @Override
            public Quadruple<A, B, C, D> next() {
                return new Quadruple<>(asi.next(), bsi.next(), csi.next(), dsi.next());
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static <A, B, C, D, E> Iterable<Quintuple<A, B, C, D, E>> zip5(
            Iterable<A> as,
            Iterable<B> bs,
            Iterable<C> cs,
            Iterable<D> ds,
            Iterable<E> es
    ) {
        return () -> new Iterator<Quintuple<A, B, C, D, E>>() {
            private final Iterator<A> asi = as.iterator();
            private final Iterator<B> bsi = bs.iterator();
            private final Iterator<C> csi = cs.iterator();
            private final Iterator<D> dsi = ds.iterator();
            private final Iterator<E> esi = es.iterator();

            @Override
            public boolean hasNext() {
                return asi.hasNext() && bsi.hasNext() && csi.hasNext() && dsi.hasNext() && esi.hasNext();
            }

            @Override
            public Quintuple<A, B, C, D, E> next() {
                return new Quintuple<>(asi.next(), bsi.next(), csi.next(), dsi.next(), esi.next());
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static <A, B, C, D, E, F> Iterable<Sextuple<A, B, C, D, E, F>> zip6(
            Iterable<A> as,
            Iterable<B> bs,
            Iterable<C> cs,
            Iterable<D> ds,
            Iterable<E> es,
            Iterable<F> fs
    ) {
        return () -> new Iterator<Sextuple<A, B, C, D, E, F>>() {
            private final Iterator<A> asi = as.iterator();
            private final Iterator<B> bsi = bs.iterator();
            private final Iterator<C> csi = cs.iterator();
            private final Iterator<D> dsi = ds.iterator();
            private final Iterator<E> esi = es.iterator();
            private final Iterator<F> fsi = fs.iterator();

            @Override
            public boolean hasNext() {
                return asi.hasNext() &&
                        bsi.hasNext() &&
                        csi.hasNext() &&
                        dsi.hasNext() &&
                        esi.hasNext() &&
                        fsi.hasNext();
            }

            @Override
            public Sextuple<A, B, C, D, E, F> next() {
                return new Sextuple<>(asi.next(), bsi.next(), csi.next(), dsi.next(), esi.next(), fsi.next());
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static <A, B, C, D, E, F, G> Iterable<Septuple<A, B, C, D, E, F, G>> zip7(
            Iterable<A> as,
            Iterable<B> bs,
            Iterable<C> cs,
            Iterable<D> ds,
            Iterable<E> es,
            Iterable<F> fs,
            Iterable<G> gs
    ) {
        return () -> new Iterator<Septuple<A, B, C, D, E, F, G>>() {
            private final Iterator<A> asi = as.iterator();
            private final Iterator<B> bsi = bs.iterator();
            private final Iterator<C> csi = cs.iterator();
            private final Iterator<D> dsi = ds.iterator();
            private final Iterator<E> esi = es.iterator();
            private final Iterator<F> fsi = fs.iterator();
            private final Iterator<G> gsi = gs.iterator();

            @Override
            public boolean hasNext() {
                return asi.hasNext() &&
                        bsi.hasNext() &&
                        csi.hasNext() &&
                        dsi.hasNext() &&
                        esi.hasNext() &&
                        fsi.hasNext() &&
                        gsi.hasNext();
            }

            @Override
            public Septuple<A, B, C, D, E, F, G> next() {
                return new Septuple<>(
                        asi.next(),
                        bsi.next(),
                        csi.next(),
                        dsi.next(),
                        esi.next(),
                        fsi.next(),
                        gsi.next()
                );
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static <A, B> Iterable<Pair<A, B>> zipPadded(A aPad, B bPad, Iterable<A> as, Iterable<B> bs) {
        return () -> new Iterator<Pair<A, B>>() {
            private final Iterator<A> asi = as.iterator();
            private final Iterator<B> bsi = bs.iterator();

            @Override
            public boolean hasNext() {
                return asi.hasNext() || bsi.hasNext();
            }

            @Override
            public Pair<A, B> next() {
                A a = asi.hasNext() ? asi.next() : aPad;
                B b = bsi.hasNext() ? bsi.next() : bPad;
                return new Pair<>(a, b);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static <A, B, C> Iterable<Triple<A, B, C>> zip3Padded(
            A aPad,
            B bPad,
            C cPad,
            Iterable<A> as,
            Iterable<B> bs,
            Iterable<C> cs
    ) {
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

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static <A, B, C, D> Iterable<Quadruple<A, B, C, D>> zip4Padded(
            A aPad,
            B bPad,
            C cPad,
            D dPad,
            Iterable<A> as,
            Iterable<B> bs,
            Iterable<C> cs,
            Iterable<D> ds
    ) {
        return () -> new Iterator<Quadruple<A, B, C, D>>() {
            private final Iterator<A> asi = as.iterator();
            private final Iterator<B> bsi = bs.iterator();
            private final Iterator<C> csi = cs.iterator();
            private final Iterator<D> dsi = ds.iterator();

            @Override
            public boolean hasNext() {
                return asi.hasNext() || bsi.hasNext() || csi.hasNext() || dsi.hasNext();
            }

            @Override
            public Quadruple<A, B, C, D> next() {
                A a = asi.hasNext() ? asi.next() : aPad;
                B b = bsi.hasNext() ? bsi.next() : bPad;
                C c = csi.hasNext() ? csi.next() : cPad;
                D d = dsi.hasNext() ? dsi.next() : dPad;
                return new Quadruple<>(a, b, c, d);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static <A, B, C, D, E> Iterable<Quintuple<A, B, C, D, E>> zip5Padded(
            A aPad,
            B bPad,
            C cPad,
            D dPad,
            E ePad,
            Iterable<A> as,
            Iterable<B> bs,
            Iterable<C> cs,
            Iterable<D> ds,
            Iterable<E> es) {
        return () -> new Iterator<Quintuple<A, B, C, D, E>>() {
            private final Iterator<A> asi = as.iterator();
            private final Iterator<B> bsi = bs.iterator();
            private final Iterator<C> csi = cs.iterator();
            private final Iterator<D> dsi = ds.iterator();
            private final Iterator<E> esi = es.iterator();

            @Override
            public boolean hasNext() {
                return asi.hasNext() || bsi.hasNext() || csi.hasNext() || dsi.hasNext() || esi.hasNext();
            }

            @Override
            public Quintuple<A, B, C, D, E> next() {
                A a = asi.hasNext() ? asi.next() : aPad;
                B b = bsi.hasNext() ? bsi.next() : bPad;
                C c = csi.hasNext() ? csi.next() : cPad;
                D d = dsi.hasNext() ? dsi.next() : dPad;
                E e = esi.hasNext() ? esi.next() : ePad;
                return new Quintuple<>(a, b, c, d, e);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static <A, B, C, D, E, F> Iterable<Sextuple<A, B, C, D, E, F>> zip6Padded(
            A aPad,
            B bPad,
            C cPad,
            D dPad,
            E ePad,
            F fPad,
            Iterable<A> as,
            Iterable<B> bs,
            Iterable<C> cs,
            Iterable<D> ds,
            Iterable<E> es,
            Iterable<F> fs) {
        return () -> new Iterator<Sextuple<A, B, C, D, E, F>>() {
            private final Iterator<A> asi = as.iterator();
            private final Iterator<B> bsi = bs.iterator();
            private final Iterator<C> csi = cs.iterator();
            private final Iterator<D> dsi = ds.iterator();
            private final Iterator<E> esi = es.iterator();
            private final Iterator<F> fsi = fs.iterator();

            @Override
            public boolean hasNext() {
                return asi.hasNext() ||
                        bsi.hasNext() ||
                        csi.hasNext() ||
                        dsi.hasNext() ||
                        esi.hasNext() ||
                        fsi.hasNext();
            }

            @Override
            public Sextuple<A, B, C, D, E, F> next() {
                A a = asi.hasNext() ? asi.next() : aPad;
                B b = bsi.hasNext() ? bsi.next() : bPad;
                C c = csi.hasNext() ? csi.next() : cPad;
                D d = dsi.hasNext() ? dsi.next() : dPad;
                E e = esi.hasNext() ? esi.next() : ePad;
                F f = fsi.hasNext() ? fsi.next() : fPad;
                return new Sextuple<>(a, b, c, d, e, f);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static <A, B, C, D, E, F, G> Iterable<Septuple<A, B, C, D, E, F, G>> zip7Padded(
            A aPad,
            B bPad,
            C cPad,
            D dPad,
            E ePad,
            F fPad,
            G gPad,
            Iterable<A> as,
            Iterable<B> bs,
            Iterable<C> cs,
            Iterable<D> ds,
            Iterable<E> es,
            Iterable<F> fs,
            Iterable<G> gs) {
        return () -> new Iterator<Septuple<A, B, C, D, E, F, G>>() {
            private final Iterator<A> asi = as.iterator();
            private final Iterator<B> bsi = bs.iterator();
            private final Iterator<C> csi = cs.iterator();
            private final Iterator<D> dsi = ds.iterator();
            private final Iterator<E> esi = es.iterator();
            private final Iterator<F> fsi = fs.iterator();
            private final Iterator<G> gsi = gs.iterator();

            @Override
            public boolean hasNext() {
                return asi.hasNext() ||
                        bsi.hasNext() ||
                        csi.hasNext() ||
                        dsi.hasNext() ||
                        esi.hasNext() ||
                        fsi.hasNext() ||
                        gsi.hasNext();
            }

            @Override
            public Septuple<A, B, C, D, E, F, G> next() {
                A a = asi.hasNext() ? asi.next() : aPad;
                B b = bsi.hasNext() ? bsi.next() : bPad;
                C c = csi.hasNext() ? csi.next() : cPad;
                D d = dsi.hasNext() ? dsi.next() : dPad;
                E e = esi.hasNext() ? esi.next() : ePad;
                F f = fsi.hasNext() ? fsi.next() : fPad;
                G g = gsi.hasNext() ? gsi.next() : gPad;
                return new Septuple<>(a, b, c, d, e, f, g);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    public static <A, B, O> Iterable<O> zipWith(
            Function<Pair<A, B>, O> f,
            Iterable<A> as,
            Iterable<B> bs
    ) {
        return map(f, zip(as, bs));
    }

    public static <A, B, C, O> Iterable<O> zipWith3(
            Function<Triple<A, B, C>, O> f,
            Iterable<A> as,
            Iterable<B> bs,
            Iterable<C> cs
    ) {
        return map(f, zip3(as, bs, cs));
    }

    public static <A, B, C, D, O> Iterable<O> zipWith4(
            Function<Quadruple<A, B, C, D>, O> f,
            Iterable<A> as,
            Iterable<B> bs,
            Iterable<C> cs,
            Iterable<D> ds
    ) {
        return map(f, zip4(as, bs, cs, ds));
    }

    public static <A, B, C, D, E, O> Iterable<O> zipWith5(
            Function<Quintuple<A, B, C, D, E>, O> f,
            Iterable<A> as,
            Iterable<B> bs,
            Iterable<C> cs,
            Iterable<D> ds,
            Iterable<E> es
    ) {
        return map(f, zip5(as, bs, cs, ds, es));
    }

    public static <A, B, C, D, E, F, O> Iterable<O> zipWith6(
            Function<Sextuple<A, B, C, D, E, F>, O> f,
            Iterable<A> as,
            Iterable<B> bs,
            Iterable<C> cs,
            Iterable<D> ds,
            Iterable<E> es,
            Iterable<F> fs
    ) {
        return map(f, zip6(as, bs, cs, ds, es, fs));
    }

    public static <A, B, C, D, E, F, G, O> Iterable<O> zipWith6(
            Function<Septuple<A, B, C, D, E, F, G>, O> f,
            Iterable<A> as,
            Iterable<B> bs,
            Iterable<C> cs,
            Iterable<D> ds,
            Iterable<E> es,
            Iterable<F> fs,
            Iterable<G> gs
    ) {
        return map(f, zip7(as, bs, cs, ds, es, fs, gs));
    }

    public static <A, B, O> Iterable<O> zipWithPadded(
            Function<Pair<A, B>, O> f,
            A aPad,
            B bPad,
            Iterable<A> as,
            Iterable<B> bs
    ) {
        return map(f, zipPadded(aPad, bPad, as, bs));
    }

    public static <A, B, C, O> Iterable<O> zipWith3Padded(
            Function<Triple<A, B, C>, O> f,
            A aPad,
            B bPad,
            C cPad,
            Iterable<A> as,
            Iterable<B> bs,
            Iterable<C> cs
    ) {
        return map(f, zip3Padded(aPad, bPad, cPad, as, bs, cs));
    }

    public static <A, B, C, D, O> Iterable<O> zipWith4Padded(
            Function<Quadruple<A, B, C, D>, O> f,
            A aPad,
            B bPad,
            C cPad,
            D dPad,
            Iterable<A> as,
            Iterable<B> bs,
            Iterable<C> cs,
            Iterable<D> ds
    ) {
        return map(f, zip4Padded(aPad, bPad, cPad, dPad, as, bs, cs, ds));
    }

    public static <A, B, C, D, E, O> Iterable<O> zipWith5Padded(
            Function<Quintuple<A, B, C, D, E>, O> f,
            A aPad,
            B bPad,
            C cPad,
            D dPad,
            E ePad,
            Iterable<A> as,
            Iterable<B> bs,
            Iterable<C> cs,
            Iterable<D> ds,
            Iterable<E> es
    ) {
        return map(f, zip5Padded(aPad, bPad, cPad, dPad, ePad, as, bs, cs, ds, es));
    }

    public static <A, B, C, D, E, F, O> Iterable<O> zipWith6Padded(
            Function<Sextuple<A, B, C, D, E, F>, O> f,
            A aPad,
            B bPad,
            C cPad,
            D dPad,
            E ePad,
            F fPad,
            Iterable<A> as,
            Iterable<B> bs,
            Iterable<C> cs,
            Iterable<D> ds,
            Iterable<E> es,
            Iterable<F> fs
    ) {
        return map(f, zip6Padded(aPad, bPad, cPad, dPad, ePad, fPad, as, bs, cs, ds, es, fs));
    }

    public static <A, B, C, D, E, F, G, O> Iterable<O> zipWith7Padded(
            Function<Septuple<A, B, C, D, E, F, G>, O> f,
            A aPad,
            B bPad,
            C cPad,
            D dPad,
            E ePad,
            F fPad,
            G gPad,
            Iterable<A> as,
            Iterable<B> bs,
            Iterable<C> cs,
            Iterable<D> ds,
            Iterable<E> es,
            Iterable<F> fs,
            Iterable<G> gs
    ) {
        return map(f, zip7Padded(aPad, bPad, cPad, dPad, ePad, fPad, gPad, as, bs, cs, ds, es, fs, gs));
    }

    public static <A, B> Pair<Iterable<A>, Iterable<B>> unzip(Iterable<Pair<A, B>> ps) {
        return new Pair<>(
                map(p -> p.a, ps),
                map(p -> p.b, ps)
        );
    }

    public static <A, B, C> Triple<Iterable<A>, Iterable<B>, Iterable<C>> unzip3(Iterable<Triple<A, B, C>> ps) {
        return new Triple<>(
                map(p -> p.a, ps),
                map(p -> p.b, ps),
                map(p -> p.c, ps)
        );
    }

    public static <A, B, C, D> Quadruple<
            Iterable<A>,
            Iterable<B>,
            Iterable<C>,
            Iterable<D>
            > unzip4(Iterable<Quadruple<A, B, C, D>> ps) {
        return new Quadruple<>(
                map(p -> p.a, ps),
                map(p -> p.b, ps),
                map(p -> p.c, ps),
                map(p -> p.d, ps)
        );
    }

    public static <A, B, C, D, E> Quintuple<
            Iterable<A>,
            Iterable<B>,
            Iterable<C>,
            Iterable<D>,
            Iterable<E>
            > unzip5(Iterable<Quintuple<A, B, C, D, E>> ps) {
        return new Quintuple<>(
                map(p -> p.a, ps),
                map(p -> p.b, ps),
                map(p -> p.c, ps),
                map(p -> p.d, ps),
                map(p -> p.e, ps)
        );
    }

    public static <A, B, C, D, E, F> Sextuple<
            Iterable<A>,
            Iterable<B>,
            Iterable<C>,
            Iterable<D>,
            Iterable<E>,
            Iterable<F>
            > unzip6(Iterable<Sextuple<A, B, C, D, E, F>> ps) {
        return new Sextuple<>(
                map(p -> p.a, ps),
                map(p -> p.b, ps),
                map(p -> p.c, ps),
                map(p -> p.d, ps),
                map(p -> p.e, ps),
                map(p -> p.f, ps)
        );
    }

    public static <A, B, C, D, E, F, G> Septuple<
            Iterable<A>,
            Iterable<B>,
            Iterable<C>,
            Iterable<D>,
            Iterable<E>,
            Iterable<F>,
            Iterable<G>
            > unzip7(Iterable<Septuple<A, B, C, D, E, F, G>> ps) {
        return new Septuple<>(
                map(p -> p.a, ps),
                map(p -> p.b, ps),
                map(p -> p.c, ps),
                map(p -> p.d, ps),
                map(p -> p.e, ps),
                map(p -> p.f, ps),
                map(p -> p.g, ps)
        );
    }

    public static @NotNull <T> Iterable<T> nub(@NotNull Iterable<T> xs) {
        Set<T> seen = new HashSet<>();
        return new Iterable<T>() {
            @Override
            public Iterator<T> iterator() {
                return new Iterator<T>() {
                    private final Iterator<T> xsi = xs.iterator();
                    private T next;
                    private boolean hasNext;

                    {
                        advance();
                    }

                    @Override
                    public boolean hasNext() {
                        return hasNext;
                    }

                    @Override
                    public T next() {
                        T current = next;
                        advance();
                        return current;
                    }

                    private void advance() {
                        while (xsi.hasNext()) {
                            next = xsi.next();
                            if (!seen.contains(next)) {
                                seen.add(next);
                                hasNext = true;
                                return;
                            }
                        }
                        hasNext = false;
                    }

                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException("cannot remove from this iterator");
                    }
                };
            }
        };
    }

    public static @NotNull <T> String nub(@NotNull String s) {
        Set<Character> seen = new HashSet<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!seen.contains(c)) {
                seen.add(c);
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static @NotNull <T extends Comparable<T>> List<T> sort(@NotNull Iterable<T> xss) {
        List<T> list = toList(xss);
        Collections.sort(list);
        return list;
    }

    public static @NotNull String sort(@NotNull String s) {
        List<Character> list = toList(s);
        Collections.sort(list);
        return charsToString(list);
    }
}
