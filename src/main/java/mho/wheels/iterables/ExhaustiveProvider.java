package mho.wheels.iterables;

import mho.wheels.math.BinaryFraction;
import mho.wheels.math.Combinatorics;
import mho.wheels.math.MathUtils;
import mho.wheels.numberUtils.BigDecimalUtils;
import mho.wheels.numberUtils.FloatingPointUtils;
import mho.wheels.numberUtils.IntegerUtils;
import mho.wheels.ordering.Ordering;
import mho.wheels.structures.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Function;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.ordering.Ordering.*;

/**
 * An {@code ExhaustiveProvider} produces {@code Iterable}s that generate some set of values in a specified order.
 * There is only a single instance of this class.
 */
public final strictfp class ExhaustiveProvider extends IterableProvider {
    /**
     * The single instance of this class.
     */
    public static final ExhaustiveProvider INSTANCE = new ExhaustiveProvider();

    /**
     * The transition between algorithms for generating lists of values. If the number of values is less than or equal
     * to this value, we use lexicographic ordering; otherwise, Z-curve ordering.
     */
    private static final int MAX_SIZE_FOR_SHORT_LIST_ALG = 5;

    /**
     * Disallow instantiation
     */
    private ExhaustiveProvider() {}

    /**
     * An {@link Iterable} that generates both {@link boolean}s. Does not support removal.
     *
     * Length is 2
     */
    @Override
    public @NotNull Iterable<Boolean> booleans() {
        return new NoRemoveIterable<>(Arrays.asList(false, true));
    }

    /**
     * An {@code Iterable} that generates all {@link Ordering}s in increasing order. Does not support removal.
     *
     * Length is 3
     */
    public @NotNull Iterable<Ordering> orderingsIncreasing() {
        return new NoRemoveIterable<>(Arrays.asList(LT, EQ, GT));
    }

    /**
     * An {@code Iterable} that generates all {@code Ordering}s. Does not support removal.
     *
     * Length is 3
     */
    @Override
    public @NotNull Iterable<Ordering> orderings() {
        return new NoRemoveIterable<>(Arrays.asList(EQ, LT, GT));
    }

    /**
     * An {@code Iterable} that generates all {@link RoundingMode}s. Does not support removal.
     *
     * Length is 8
     */
    @Override
    public @NotNull Iterable<RoundingMode> roundingModes() {
        return new NoRemoveIterable<>(Arrays.asList(
                RoundingMode.UNNECESSARY,
                RoundingMode.UP,
                RoundingMode.DOWN,
                RoundingMode.CEILING,
                RoundingMode.FLOOR,
                RoundingMode.HALF_UP,
                RoundingMode.HALF_DOWN,
                RoundingMode.HALF_EVEN
        ));
    }

    /**
     * Returns an unmodifiable version of a list. Does not support removal.
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result is finite.</li>
     * </ul>
     *
     * Length is |{@code xs}|
     *
     * @param xs a {@code List}
     * @param <T> the type of {@code xs}'s elements
     * @return an unmodifiable version of {@code xs}
     */
    @Override
    public @NotNull <T> Iterable<T> uniformSample(@NotNull List<T> xs) {
        return new NoRemoveIterable<>(xs);
    }

    /**
     * Turns a {@code String} into an {@code Iterable} of {@code Character}s. Does not support removal.
     *
     * <ul>
     *  <li>{@code s} cannot be null.</li>
     *  <li>The result is finite.</li>
     * </ul>
     *
     * Length is |{@code s}|
     *
     * @param s a {@code String}
     * @return the {@code Character}s in {@code s}
     */
    @Override
    public @NotNull Iterable<Character> uniformSample(@NotNull String s) {
        return fromString(s);
    }

    /**
     * An {@code Iterable} that generates all {@link Byte}s in increasing order. Does not support removal.
     *
     * Length is 2<sup>8</sup> = 256
     */
    public @NotNull Iterable<Byte> bytesIncreasing() {
        return IterableUtils.rangeUp(Byte.MIN_VALUE);
    }

    /**
     * An {@code Iterable} that generates all {@link Short}s in increasing order. Does not support removal.
     *
     * Length is 2<sup>16</sup> = 65,536
     */
    public @NotNull Iterable<Short> shortsIncreasing() {
        return IterableUtils.rangeUp(Short.MIN_VALUE);
    }

    /**
     * An {@code Iterable} that generates all {@link Integer}s in increasing order. Does not support removal.
     *
     * Length is 2<sup>32</sup> = 4,294,967,296
     */
    public @NotNull Iterable<Integer> integersIncreasing() {
        return IterableUtils.rangeUp(Integer.MIN_VALUE);
    }

    /**
     * An {@code Iterable} that generates all {@link Long}s in increasing order. Does not support removal.
     *
     * Length is 2<sup>64</sup> = 18,446,744,073,709,551,616
     */
    public @NotNull Iterable<Long> longsIncreasing() {
        return IterableUtils.rangeUp(Long.MIN_VALUE);
    }

    /**
     * An {@code Iterable} that generates all positive {@code Byte}s. Does not support removal.
     *
     * Length is 2<sup>7</sup>–1 = 127
     */
    @Override
    public @NotNull Iterable<Byte> positiveBytes() {
        return IterableUtils.rangeUp((byte) 1);
    }

    /**
     * An {@code Iterable} that generates all positive {@code Short}s. Does not support removal.
     *
     * Length is 2<sup>15</sup>–1 = 32,767
     */
    @Override
    public @NotNull Iterable<Short> positiveShorts() {
        return IterableUtils.rangeUp((short) 1);
    }

    /**
     * An {@code Iterable} that generates all positive {@code Integer}s. Does not support removal.
     *
     * Length is 2<sup>31</sup>–1 = 2,147,483,647
     */
    @Override
    public @NotNull Iterable<Integer> positiveIntegers() {
        return IterableUtils.rangeUp(1);
    }

    /**
     * An {@code Iterable} that generates all positive {@code Long}s. Does not support removal.
     *
     * Length is 2<sup>63</sup>–1 = 9,223,372,036,854,775,807
     */
    @Override
    public @NotNull Iterable<Long> positiveLongs() {
        return IterableUtils.rangeUp(1L);
    }

    /**
     * An {@code Iterable} that generates all positive {@link BigInteger}s. Does not support removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<BigInteger> positiveBigIntegers() {
        return IterableUtils.rangeUp(BigInteger.ONE);
    }

    /**
     * An {@code Iterable} that generates all negative {@code Byte}s. Does not support removal.
     *
     * Length is 2<sup>7</sup> = 128
     */
    @Override
    public @NotNull Iterable<Byte> negativeBytes() {
        return IterableUtils.rangeBy((byte) -1, (byte) -1);
    }

    /**
     * An {@code Iterable} that generates all negative {@code Short}s. Does not support removal.
     *
     * Length is 2<sup>15</sup> = 32,768
     */
    @Override
    public @NotNull Iterable<Short> negativeShorts() {
        return IterableUtils.rangeBy((short) -1, (short) -1);
    }

    /**
     * An {@code Iterable} that generates all negative {@code Integer}s. Does not support removal.
     *
     * Length is 2<sup>31</sup> = 2,147,483,648
     */
    @Override
    public @NotNull Iterable<Integer> negativeIntegers() {
        return IterableUtils.rangeBy(-1, -1);
    }

    /**
     * An {@code Iterable} that generates all negative {@code Long}s. Does not support removal.
     *
     * Length is 2<sup>63</sup> = 9,223,372,036,854,775,808
     */
    @Override
    public @NotNull Iterable<Long> negativeLongs() {
        return IterableUtils.rangeBy(-1L, -1L);
    }

    /**
     * An {@code Iterable} that generates all negative {@code BigInteger}s. Does not support removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<BigInteger> negativeBigIntegers() {
        return IterableUtils.rangeBy(IntegerUtils.NEGATIVE_ONE, IntegerUtils.NEGATIVE_ONE);
    }

    /**
     * An {@code Iterable} that generates all nonzero {@code Byte}s. Does not support removal.
     *
     * Length is 2<sup>8</sup>–1 = 127
     */
    @Override
    public @NotNull Iterable<Byte> nonzeroBytes() {
        return mux(Arrays.asList(positiveBytes(), negativeBytes()));
    }

    /**
     * An {@code Iterable} that generates all nonzero {@code Short}s. Does not support removal.
     *
     * Length is 2<sup>16</sup>–1 = 65,535
     */
    @Override
    public @NotNull Iterable<Short> nonzeroShorts() {
        return mux(Arrays.asList(positiveShorts(), negativeShorts()));
    }

    /**
     * An {@code Iterable} that generates all nonzero {@code Integer}s. Does not support removal.
     *
     * Length is 2<sup>32</sup>–1 = 4,294,967,295
     */
    @Override
    public @NotNull Iterable<Integer> nonzeroIntegers() {
        return mux(Arrays.asList(positiveIntegers(), negativeIntegers()));
    }

    /**
     * An {@code Iterable} that generates all nonzero {@code Long}s. Does not support removal.
     *
     * Length is 2<sup>64</sup>–1 = 18,446,744,073,709,551,615
     */
    @Override
    public @NotNull Iterable<Long> nonzeroLongs() {
        return mux(Arrays.asList(positiveLongs(), negativeLongs()));
    }

    /**
     * An {@code Iterable} that generates all nonzero {@code BigInteger}s. Does not support removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<BigInteger> nonzeroBigIntegers() {
        return mux(Arrays.asList(positiveBigIntegers(), negativeBigIntegers()));
    }

    /**
     * An {@code Iterable} that generates all natural {@code Byte}s. Does not support removal.
     *
     * Length is 2<sup>7</sup> = 128
     */
    @Override
    public @NotNull Iterable<Byte> naturalBytes() {
        return IterableUtils.rangeUp((byte) 0);
    }

    /**
     * An {@code Iterable} that generates all natural {@code Short}s (including 0). Does not support removal.
     *
     * Length is 2<sup>15</sup> = 32,768
     */
    @Override
    public @NotNull Iterable<Short> naturalShorts() {
        return IterableUtils.rangeUp((short) 0);
    }

    /**
     * An {@code Iterable} that generates all natural {@code Integer}s (including 0). Does not support removal.
     *
     * Length is 2<sup>31</sup> = 2,147,483,648
     */
    @Override
    public @NotNull Iterable<Integer> naturalIntegers() {
        return IterableUtils.rangeUp(0);
    }

    /**
     * An {@code Iterable} that generates all natural {@code Long}s (including 0). Does not support removal.
     *
     * Length is 2<sup>63</sup> = 9,223,372,036,854,775,808
     */
    @Override
    public @NotNull Iterable<Long> naturalLongs() {
        return IterableUtils.rangeUp(0L);
    }

    /**
     * An {@code Iterable} that generates all natural {@code BigInteger}s (including 0). Does not support removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<BigInteger> naturalBigIntegers() {
        return rangeUp(BigInteger.ZERO);
    }

    /**
     * An {@code Iterable} that generates all {@code Byte}s. Does not support removal.
     *
     * Length is 2<sup>8</sup> = 128
     */
    @Override
    public @NotNull Iterable<Byte> bytes() {
        return cons((byte) 0, nonzeroBytes());
    }

    /**
     * An {@code Iterable} that generates all {@code Short}s. Does not support removal.
     *
     * Length is 2<sup>16</sup> = 65,536
     */
    @Override
    public @NotNull Iterable<Short> shorts() {
        return cons((short) 0, nonzeroShorts());
    }

    /**
     * An {@code Iterable} that generates all {@code Integer}s. Does not support removal.
     *
     * Length is 2<sup>32</sup> = 4,294,967,296
     */
    @Override
    public @NotNull Iterable<Integer> integers() {
        return cons(0, nonzeroIntegers());
    }

    /**
     * An {@code Iterable} that generates all {@code Long}s. Does not support removal.
     *
     * Length is 2<sup>64</sup> = 18,446,744,073,709,551,616
     */
    @Override
    public @NotNull Iterable<Long> longs() {
        return cons(0L, nonzeroLongs());
    }

    /**
     * An {@code Iterable} that generates all {@code BigInteger}s. Does not support removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<BigInteger> bigIntegers() {
        return cons(BigInteger.ZERO, nonzeroBigIntegers());
    }

    /**
     * An {@code Iterable} that generates all ASCII {@link Character}s in increasing order. Does not support
     * removal.
     *
     * Length is 2<sup>7</sup> = 128
     */
    public @NotNull Iterable<Character> asciiCharactersIncreasing() {
        return range((char) 0, (char) 127);
    }

    /**
     * An {@code Iterable} that generates all ASCII {@code Character}s in an order which places "friendly" characters
     * first. Does not support removal.
     *
     * Length is 2<sup>7</sup> = 128
     */
    @Override
    public @NotNull Iterable<Character> asciiCharacters() {
        return concat(Arrays.asList(
                range('a', 'z'),
                range('A', 'Z'),
                range('0', '9'),
                range('!', '/'),                  // printable non-alphanumeric ASCII...
                range(':', '@'),                  // ...
                range('[', '`'),                  // ...
                range('{', '~'),                  // ...
                Collections.singleton(' '),       // ' '
                range((char) 0, (char) 31),       // non-printable and whitespace ASCII
                Collections.singleton((char) 127) // DEL
        ));
    }

    /**
     * An {@code Iterable} that generates all {@code Character}s in increasing order. Does not support removal.
     *
     * Length is 2<sup>16</sup> = 65,536
     */
    public @NotNull Iterable<Character> charactersIncreasing() {
        return range(Character.MIN_VALUE, Character.MAX_VALUE);
    }

    /**
     * An {@code Iterable} that generates all {@code Character}s in an order which places "friendly" characters
     * first. Does not support removal.
     *
     * Length is 2<sup>16</sup> = 65,536
     */
    @Override
    public @NotNull Iterable<Character> characters() {
        return concat(Arrays.asList(
                range('a', 'z'),
                range('A', 'Z'),
                range('0', '9'),
                range('!', '/'),            // printable non-alphanumeric ASCII...
                range(':', '@'),            // ...
                range('[', '`'),            // ...
                range('{', '~'),            // ...
                Collections.singleton(' '), // ' '
                range((char) 0, (char) 31), // non-printable and whitespace ASCII
                rangeUp((char) 127)         // DEL and non-ASCII
        ));
    }

    /**
     * An {@code Iterable} that generates all {@code Byte}s greater than or equal to {@code a}. Does not support
     * removal.
     *
     * <ul>
     *  <li>{@code a} may be any {@code byte}.</li>
     *  <li>The result is a non-removable {@code Iterable} containing {@code Byte}s.</li>
     * </ul>
     *
     * Length is 2<sup>7</sup>–{@code a}
     *
     * @param a the inclusive lower bound of the generated elements
     * @return {@code Byte}s greater than or equal to {@code a}
     */
    @Override
    public @NotNull Iterable<Byte> rangeUp(byte a) {
        if (a >= 0) {
            return IterableUtils.rangeUp(a);
        } else {
            return cons(
                    (byte) 0,
                    mux(Arrays.asList(IterableUtils.rangeUp((byte) 1), IterableUtils.rangeBy((byte) -1, (byte) -1, a)))
            );
        }
    }

    /**
     * An {@code Iterable} that generates all {@code Short}s greater than or equal to {@code a}. Does not support
     * removal.
     *
     * <ul>
     *  <li>{@code a} may be any {@code short}.</li>
     *  <li>The result is a non-removable {@code Iterable} containing {@code Short}s.</li>
     * </ul>
     *
     * Length is 2<sup>15</sup>–{@code a}
     *
     * @param a the inclusive lower bound of the generated elements
     * @return {@code Short}s greater than or equal to {@code a}
     */
    @Override
    public @NotNull Iterable<Short> rangeUp(short a) {
        if (a >= 0) {
            return IterableUtils.rangeUp(a);
        } else {
            return cons(
                    (short) 0,
                    mux(
                            Arrays.asList(
                                    IterableUtils.rangeUp((short) 1),
                                    IterableUtils.rangeBy((short) -1, (short) -1, a)
                            )
                    )
            );
        }
    }

    /**
     * An {@code Iterable} that generates all {@code Integers}s greater than or equal to {@code a}. Does not support
     * removal.
     *
     * <ul>
     *  <li>{@code a} may be any {@code int}.</li>
     *  <li>The result is a non-removable {@code Iterable} containing {@code Integer}s.</li>
     * </ul>
     *
     * Length is 2<sup>31</sup>–{@code a}
     *
     * @param a the inclusive lower bound of the generated elements
     * @return {@code Integer}s greater than or equal to {@code a}
     */
    @Override
    public @NotNull Iterable<Integer> rangeUp(int a) {
        if (a >= 0) {
            return IterableUtils.rangeUp(a);
        } else {
            return cons(0, mux(Arrays.asList(IterableUtils.rangeUp(1), IterableUtils.rangeBy(-1, -1, a))));
        }
    }

    /**
     * An {@code Iterable} that generates all {@code Long}s greater than or equal to {@code a}. Does not support
     * removal.
     *
     * <ul>
     *  <li>{@code a} may be any {@code long}.</li>
     *  <li>The result is a non-removable {@code Iterable} containing {@code Long}s.</li>
     * </ul>
     *
     * Length is 2<sup>63</sup>–{@code a}
     *
     * @param a the inclusive lower bound of the generated elements
     * @return {@code Long}s greater than or equal to {@code a}
     */
    @Override
    public @NotNull Iterable<Long> rangeUp(long a) {
        if (a >= 0) {
            return IterableUtils.rangeUp(a);
        } else {
            return cons(0L, mux(Arrays.asList(IterableUtils.rangeUp(1L), IterableUtils.rangeBy(-1L, -1L, a))));
        }
    }

    /**
     * An {@code Iterable} that generates all {@code BigIntegers}s greater than or equal to {@code a}. Does not support
     * removal.
     *
     * <ul>
     *  <li>{@code a} cannot be null.</li>
     *  <li>The result is a non-removable {@code Iterable} containing {@code BigInteger}s.</li>
     * </ul>
     *
     * Length is infinite
     *
     * @param a the inclusive lower bound of the generated elements
     * @return {@code BigInteger}s greater than or equal to {@code a}
     */
    @Override
    public @NotNull Iterable<BigInteger> rangeUp(@NotNull BigInteger a) {
        if (a.signum() != -1) {
            return IterableUtils.rangeUp(a);
        } else {
            return cons(
                    BigInteger.ZERO,
                    mux(
                            Arrays.asList(
                                    IterableUtils.rangeUp(BigInteger.ONE),
                                    IterableUtils.rangeBy(IntegerUtils.NEGATIVE_ONE, IntegerUtils.NEGATIVE_ONE, a)
                            )
                    )
            );
        }
    }

    /**
     * An {@code Iterable} that generates all {@code Character}s greater than or equal to {@code a}. Does not support
     * removal.
     *
     * <ul>
     *  <li>{@code a} may be any {@code char}.</li>
     *  <li>The result is a non-removable {@code Iterable} containing {@code Character}s.</li>
     * </ul>
     *
     * Length is 2<sup>16</sup>–{@code a}
     *
     * @param a the inclusive lower bound of the generated elements
     * @return {@code Character}s greater than or equal to {@code a}
     */
    @Override
    public @NotNull Iterable<Character> rangeUp(char a) {
        return IterableUtils.rangeUp(a);
    }

    /**
     * An {@code Iterable} that generates all {@code Byte}s less than or equal to {@code a}. Does not support removal.
     *
     * <ul>
     *  <li>{@code a} may be any {@code byte}.</li>
     *  <li>The result is a non-removable {@code Iterable} containing {@code Byte}s.</li>
     * </ul>
     *
     * Length is {@code a}+2<sup>7</sup>+1
     *
     * @param a the inclusive upper bound of the generated elements
     * @return {@code Byte}s less than or equal to {@code a}
     */
    @Override
    public @NotNull Iterable<Byte> rangeDown(byte a) {
        if (a <= 0) {
            return IterableUtils.rangeBy(a, (byte) -1);
        } else {
            return cons(
                    (byte) 0,
                    mux(Arrays.asList(IterableUtils.range((byte) 1, a), IterableUtils.rangeBy((byte) -1, (byte) -1)))
            );
        }
    }

    /**
     * An {@code Iterable} that generates all {@code Short}s less than or equal to {@code a}. Does not support removal.
     *
     * <ul>
     *  <li>{@code a} may be any {@code short}.</li>
     *  <li>The result is a non-removable {@code Iterable} containing {@code Short}s.</li>
     * </ul>
     *
     * Length is {@code a}+2<sup>15</sup>+1
     *
     * @param a the inclusive upper bound of the generated elements
     * @return {@code Short}s less than or equal to {@code a}
     */
    @Override
    public @NotNull Iterable<Short> rangeDown(short a) {
        if (a <= 0) {
            return IterableUtils.rangeBy(a, (short) -1);
        } else {
            return cons(
                    (short) 0,
                    mux(
                            Arrays.asList(
                                    IterableUtils.range((short) 1, a),
                                    IterableUtils.rangeBy((short) -1, (short) -1)
                            )
                    )
            );
        }
    }

    /**
     * An {@code Iterable} that generates all {@code Integer}s less than or equal to {@code a}. Does not support
     * removal.
     *
     * <ul>
     *  <li>{@code a} may be any {@code int}.</li>
     *  <li>The result is a non-removable {@code Iterable} containing {@code Integer}s.</li>
     * </ul>
     *
     * Length is {@code a}+2<sup>31</sup>+1
     *
     * @param a the inclusive upper bound of the generated elements
     * @return {@code Integer}s less than or equal to {@code a}
     */
    @Override
    public @NotNull Iterable<Integer> rangeDown(int a) {
        if (a <= 0) {
            return IterableUtils.rangeBy(a, -1);
        } else {
            return cons(0, mux(Arrays.asList(IterableUtils.range(1, a), IterableUtils.rangeBy(-1, -1))));
        }
    }

    /**
     * An {@code Iterable} that generates all {@code Long}s less than or equal to {@code a}. Does not support removal.
     *
     * <ul>
     *  <li>{@code a} may be any {@code long}.</li>
     *  <li>The result is a non-removable {@code Iterable} containing {@code Long}s.</li>
     * </ul>
     *
     * Length is {@code a}+2<sup>63</sup>+1
     *
     * @param a the inclusive upper bound of the generated elements
     * @return {@code Long}s less than or equal to {@code a}
     */
    @Override
    public @NotNull Iterable<Long> rangeDown(long a) {
        if (a <= 0) {
            return IterableUtils.rangeBy(a, -1L);
        } else {
            return cons(0L, mux(Arrays.asList(IterableUtils.range(1L, a), IterableUtils.rangeBy(-1L, -1L))));
        }
    }

    /**
     * An {@code Iterable} that generates all {@code BigInteger}s less than or equal to {@code a}. Does not support
     * removal.
     *
     * <ul>
     *  <li>{@code a} cannot be null.</li>
     *  <li>The result is a non-removable {@code Iterable} containing {@code BigInteger}s.</li>
     * </ul>
     *
     * Length is infinite
     *
     * @param a the inclusive upper bound of the generated elements
     * @return {@code BigInteger}s less than or equal to {@code a}
     */
    @Override
    public @NotNull Iterable<BigInteger> rangeDown(@NotNull BigInteger a) {
        if (a.signum() != 1) {
            return IterableUtils.rangeBy(a, IntegerUtils.NEGATIVE_ONE);
        } else {
            return cons(
                    BigInteger.ZERO,
                    mux(
                            Arrays.asList(
                                    IterableUtils.range(BigInteger.ONE, a),
                                    IterableUtils.rangeBy(IntegerUtils.NEGATIVE_ONE, IntegerUtils.NEGATIVE_ONE)
                            )
                    )
            );
        }
    }

    /**
     * An {@code Iterable} that generates all {@code Character}s less than or equal to {@code a}. Does not support
     * removal.
     *
     * <ul>
     *  <li>{@code a} may be any {@code char}.</li>
     *  <li>The result is a non-removable {@code Iterable} containing {@code Character}s.</li>
     * </ul>
     *
     * Length is {@code a}+1
     *
     * @param a the inclusive upper bound of the generated elements
     * @return {@code Character}s less than or equal to {@code a}
     */
    @Override
    public @NotNull Iterable<Character> rangeDown(char a) {
        return IterableUtils.range('\0', a);
    }

    /**
     * An {@code Iterable} that generates all {@code Byte}s between {@code a} and {@code b}, inclusive. If
     * {@code a}{@literal >}{@code b}, an empty {@code Iterable} is returned. Does not support removal.
     *
     * <ul>
     *  <li>{@code a} may be any {@code byte}.</li>
     *  <li>{@code b} may be any {@code byte}.</li>
     *  <li>The result is a non-removable {@code Iterable} containing {@code Byte}s.</li>
     * </ul>
     *
     * Length is max(0, {@code b}–{@code a}+1)
     *
     * @param a the inclusive lower bound of the generated elements
     * @param b the inclusive upper bound of the generated elements
     * @return {@code Byte}s between {@code a} and {@code b}, inclusive
     */
    @Override
    public @NotNull Iterable<Byte> range(byte a, byte b) {
        if (a > b) return Collections.emptyList();
        if (a >= 0 && b >= 0) {
            return IterableUtils.range(a, b);
        } else if (a < 0 && b < 0) {
            return IterableUtils.rangeBy(b, (byte) -1, a);
        } else {
            return cons(
                    (byte) 0,
                    mux(
                            Arrays.asList(
                                    IterableUtils.range((byte) 1, b),
                                    IterableUtils.rangeBy((byte) -1, (byte) -1, a)
                            )
                    )
            );
        }
    }

    /**
     * An {@code Iterable} that generates all {@code Short}s between {@code a} and {@code b}, inclusive. If
     * {@code a}{@literal >}{@code b}, an empty {@code Iterable} is returned. Does not support removal.
     *
     * <ul>
     *  <li>{@code a} may be any {@code short}.</li>
     *  <li>{@code b} may be any {@code short}.</li>
     *  <li>The result is a non-removable {@code Iterable} containing {@code Short}s.</li>
     * </ul>
     *
     * Length is max(0, {@code b}–{@code a}+1)
     *
     * @param a the inclusive lower bound of the generated elements
     * @param b the inclusive upper bound of the generated elements
     * @return {@code Short}s between {@code a} and {@code b}, inclusive
     */
    @Override
    public @NotNull Iterable<Short> range(short a, short b) {
        if (a > b) return Collections.emptyList();
        if (a >= 0 && b >= 0) {
            return IterableUtils.range(a, b);
        } else if (a < 0 && b < 0) {
            return IterableUtils.rangeBy(b, (short) -1, a);
        } else {
            return cons(
                    (short) 0,
                    mux(
                            Arrays.asList(
                                    IterableUtils.range((short) 1, b),
                                    IterableUtils.rangeBy((short) -1, (short) -1, a)
                            )
                    )
            );
        }
    }

    /**
     * An {@code Iterable} that generates all {@code Integer}s between {@code a} and {@code b}, inclusive. If
     * {@code a}{@literal >}{@code b}, an empty {@code Iterable} is returned. Does not support removal.
     *
     * <ul>
     *  <li>{@code a} may be any {@code int}.</li>
     *  <li>{@code b} may be any {@code int}.</li>
     *  <li>The result is a non-removable {@code Iterable} containing {@code Integer}s.</li>
     * </ul>
     *
     * Length is max(0, {@code b}–{@code a}+1)
     *
     * @param a the inclusive lower bound of the generated elements
     * @param b the inclusive upper bound of the generated elements
     * @return {@code Integer}s between {@code a} and {@code b}, inclusive
     */
    @Override
    public @NotNull Iterable<Integer> range(int a, int b) {
        if (a > b) return Collections.emptyList();
        if (a >= 0 && b >= 0) {
            return IterableUtils.range(a, b);
        } else if (a < 0 && b < 0) {
            return IterableUtils.rangeBy(b, -1, a);
        } else {
            return cons(0, mux(Arrays.asList(IterableUtils.range(1, b), IterableUtils.rangeBy(-1, -1, a))));
        }
    }

    /**
     * An {@code Iterable} that generates all {@code Long}s between {@code a} and {@code b}, inclusive. If
     * {@code a}{@literal >}{@code b}, an empty {@code Iterable} is returned. Does not support removal.
     *
     * <ul>
     *  <li>{@code a} may be any {@code long}.</li>
     *  <li>{@code b} may be any {@code long}.</li>
     *  <li>The result is a non-removable {@code Iterable} containing {@code Long}s.</li>
     * </ul>
     *
     * Length is max(0, {@code b}–{@code a}+1)
     *
     * @param a the inclusive lower bound of the generated elements
     * @param b the inclusive upper bound of the generated elements
     * @return {@code Long}s between {@code a} and {@code b}, inclusive
     */
    @Override
    public @NotNull Iterable<Long> range(long a, long b) {
        if (a > b) return Collections.emptyList();
        if (a >= 0 && b >= 0) {
            return IterableUtils.range(a, b);
        } else if (a < 0 && b < 0) {
            return IterableUtils.rangeBy(b, (byte) -1, a);
        } else {
            return cons(0L, mux(Arrays.asList(IterableUtils.range(1L, b), IterableUtils.rangeBy(-1L, -1L, a))));
        }
    }

    /**
     * An {@code Iterable} that generates all {@code BigInteger}s between {@code a} and {@code b}, inclusive. If
     * {@code a}{@literal >}{@code b}, an empty {@code Iterable} is returned. Does not support removal.
     *
     * <ul>
     *  <li>{@code a} cannot be null.</li>
     *  <li>{@code b} cannot be null.</li>
     *  <li>The result is a non-removable {@code Iterable} containing {@code BigInteger}s.</li>
     * </ul>
     *
     * Length is max(0, {@code b}–{@code a}+1)
     *
     * @param a the inclusive lower bound of the generated elements
     * @param b the inclusive upper bound of the generated elements
     * @return {@code BigInteger}s between {@code a} and {@code b}, inclusive
     */
    @Override
    public @NotNull Iterable<BigInteger> range(@NotNull BigInteger a, @NotNull BigInteger b) {
        if (gt(a, b)) return Collections.emptyList();
        if (a.signum() != -1 && b.signum() != -1) {
            return IterableUtils.range(a, b);
        } else if (a.signum() == -1 && b.signum() == -1) {
            return IterableUtils.rangeBy(b, IntegerUtils.NEGATIVE_ONE, a);
        } else {
            return cons(
                    BigInteger.ZERO,
                    mux(
                            Arrays.asList(
                                    IterableUtils.range(BigInteger.ONE, b),
                                    IterableUtils.rangeBy(IntegerUtils.NEGATIVE_ONE, IntegerUtils.NEGATIVE_ONE, a)
                            )
                    )
            );
        }
    }

    /**
     * An {@code Iterable} that generates all {@code Character}s between {@code a} and {@code b}, inclusive. If
     * {@code a}{@literal >}{@code b}, an empty {@code Iterable} is returned. Does not support removal.
     *
     * <ul>
     *  <li>{@code a} may be any {@code char}.</li>
     *  <li>{@code b} may be any {@code char}.</li>
     *  <li>The result is a non-removable {@code Iterable} containing {@code Character}s.</li>
     * </ul>
     *
     * Length is max(0, {@code b}–{@code a}+1)
     *
     * @param a the inclusive lower bound of the generated elements
     * @param b the inclusive upper bound of the generated elements
     * @return {@code Character}s between {@code a} and {@code b}, inclusive
     */
    @Override
    public @NotNull Iterable<Character> range(char a, char b) {
        return IterableUtils.range(a, b);
    }

    /**
     * An {@code Iterable} that generates all positive {@link BinaryFraction}s. Does not support removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<BinaryFraction> positiveBinaryFractions() {
        return map(
                p -> BinaryFraction.of(p.a.shiftLeft(1).add(BigInteger.ONE), p.b),
                pairsLogarithmicOrder(naturalBigIntegers(), integers())
        );
    }

    /**
     * An {@code Iterable} that generates all negative {@link BinaryFraction}s. Does not support removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<BinaryFraction> negativeBinaryFractions() {
        return map(BinaryFraction::negate, positiveBinaryFractions());
    }

    /**
     * An {@code Iterable} that generates all nonzero {@link BinaryFraction}s. Does not support removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<BinaryFraction> nonzeroBinaryFractions() {
        return mux(Arrays.asList(positiveBinaryFractions(), negativeBinaryFractions()));
    }

    /**
     * An {@code Iterable} that generates all {@link BinaryFraction}s. Does not support removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<BinaryFraction> binaryFractions() {
        return cons(BinaryFraction.ZERO, nonzeroBinaryFractions());
    }

    /**
     * An {@code Iterable} that generates all {@code BinaryFraction}s greater than or equal to {@code a}. Does not
     * support removal.
     *
     * <ul>
     *  <li>{@code a} cannot be null.</li>
     *  <li>The result is a non-removable {@code Iterable} containing {@code BinaryFraction}s.</li>
     * </ul>
     *
     * Length is infinite
     *
     * @param a the inclusive lower bound of the generated elements
     * @return {@code BinaryFraction}s greater than or equal to {@code a}
     */
    @Override
    public @NotNull Iterable<BinaryFraction> rangeUp(@NotNull BinaryFraction a) {
        return cons(a, map(bf -> bf.shiftLeft(a.getExponent()).add(a), positiveBinaryFractions()));
    }

    /**
     * An {@code Iterable} that generates all {@code BinaryFraction}s less than or equal to {@code a}. Does not
     * support removal.
     *
     * <ul>
     *  <li>{@code a} cannot be null.</li>
     *  <li>The result is a non-removable {@code Iterable} containing {@code BinaryFraction}s.</li>
     * </ul>
     *
     * Length is infinite
     *
     * @param a the inclusive upper bound of the generated elements
     * @return {@code BinaryFraction}s less than or equal to {@code a}
     */
    @Override
    public @NotNull Iterable<BinaryFraction> rangeDown(@NotNull BinaryFraction a) {
        return cons(a, map(bf -> a.subtract(bf.shiftLeft(a.getExponent())), positiveBinaryFractions()));
    }

    /**
     * An {@code Iterable} that generates all {@code BinaryFraction}s between 0 and 1, exclusive. Does not support
     * removal.
     *
     * <ul>
     *  <li>The result is a non-removable {@code Iterable} containing {@code BinaryFraction}s greater than 0 and less
     *  than 1.</li>
     * </ul>
     *
     * Length is infinite
     */
    private static @NotNull Iterable<BinaryFraction> positiveBinaryFractionsLessThanOne() {
        return concatMap(
                e -> map(
                        i -> BinaryFraction.of(i.shiftLeft(1).add(BigInteger.ONE), -e),
                        IterableUtils.range(BigInteger.ZERO, BigInteger.ONE.shiftLeft(e - 1).subtract(BigInteger.ONE))
                ),
                IterableUtils.rangeUp(1)
        );
    }

    /**
     * An {@code Iterable} that generates all {@code BinaryFraction}s between {@code a} and {@code b}, inclusive. If
     * {@code a}{@literal >}{@code b}, an empty {@code Iterable} is returned. Does not support removal.
     *
     * <ul>
     *  <li>{@code a} cannot be null.</li>
     *  <li>{@code b} cannot be null.</li>
     *  <li>The result is a non-removable {@code Iterable} containing {@code BinaryFraction}s.</li>
     * </ul>
     *
     * Length is 0 if a>b, 1 if a=b, and infinite otherwise
     *
     * @param a the inclusive lower bound of the generated elements
     * @param b the inclusive upper bound of the generated elements
     * @return {@code BinaryFraction}s between {@code a} and {@code b}, inclusive
     */
    @Override
    public @NotNull Iterable<BinaryFraction> range(@NotNull BinaryFraction a, @NotNull BinaryFraction b) {
        switch (compare(a, b)) {
            case GT: return Collections.emptyList();
            case EQ: return Collections.singletonList(a);
            case LT:
                BinaryFraction difference = b.subtract(a);
                int blockExponent = difference.getExponent();
                BigInteger blockCount = difference.getMantissa();
                return concat(
                        map(
                                i -> BinaryFraction.ONE.shiftLeft(blockExponent).multiply(BinaryFraction.of(i)).add(a),
                                IterableUtils.range(BigInteger.ZERO, blockCount)
                        ),
                        concatMap(
                                bf -> map(
                                        j -> bf.add(BinaryFraction.of(j)).shiftLeft(blockExponent).add(a),
                                        IterableUtils.range(BigInteger.ZERO, blockCount.subtract(BigInteger.ONE))
                                ),
                                positiveBinaryFractionsLessThanOne()
                        )
                );
            default:
                throw new IllegalStateException("unreachable");
        }
    }

    /**
     * An {@code Iterable} that generates all possible positive {@code float} mantissas. A {@code float}'s mantissa is
     * the unique odd integer that, when multiplied by a power of 2, equals the {@code float}. Does not support
     * removal.
     *
     * Length is 2<sup>23</sup> = 8,388,608
     */
    private static final @NotNull Iterable<Integer> FLOAT_MANTISSAS = IterableUtils.rangeBy(
            1,
            2,
            1 << (FloatingPointUtils.FLOAT_FRACTION_WIDTH + 1)
    );

    /**
     * An {@code Iterable} that generates all possible float exponents. A positive float's exponent is the base-2
     * logarithm of the float divided by its mantissa. Does not support removal.
     *
     * Length is 2<sup>8</sup>+23–2 = 277
     */
    private static final @NotNull Iterable<Integer> FLOAT_EXPONENTS = cons(
            0,
            mux(
                    Arrays.asList(INSTANCE.range(1, Float.MAX_EXPONENT),
                    IterableUtils.rangeBy(-1, -1, FloatingPointUtils.MIN_SUBNORMAL_FLOAT_EXPONENT))
            )
    );

    /**
     * An {@code Iterable} that generates all ordinary (neither {@code NaN} nor infinite) positive {@code Float}s. Does
     * not support removal.
     *
     * Length is 2<sup>31</sup>–2<sup>23</sup>–1 = 2,139,095,039
     */
    private static @NotNull Iterable<Float> positiveOrdinaryFloats() {
        return optionalMap(
                p -> FloatingPointUtils.floatFromMantissaAndExponent(p.a, p.b),
                INSTANCE.pairs(FLOAT_MANTISSAS, FLOAT_EXPONENTS)
        );
    }

    /**
     * An {@code Iterable} that generates all ordinary (neither {@code NaN}, negative zero, nor infinite) negative
     * {@code Float}s. Does not support removal.
     *
     * Length is 2<sup>31</sup>–2<sup>23</sup>–1 = 2,139,095,039
     */
    private static @NotNull Iterable<Float> negativeOrdinaryFloats() {
        return map(f -> -f, positiveOrdinaryFloats());
    }

    /**
     * An {@code Iterable} that generates all ordinary (neither {@code NaN} nor infinite) nonzero {@code Float}s. Does
     * not support removal.
     *
     * Length is 2<sup>32</sup>–2<sup>24</sup>–2 = 4,278,190,078
     */
    private static @NotNull Iterable<Float> nonzeroOrdinaryFloats() {
        return mux(Arrays.asList(positiveOrdinaryFloats(), negativeOrdinaryFloats()));
    }

    /**
     * An {@code Iterable} that generates all positive {@code Float}s, including {@code Infinity} but not positive
     * zero. Does not support removal.
     *
     * Length is 2<sup>31</sup>–2<sup>23</sup> = 2,139,095,040
     */
    @Override
    public @NotNull Iterable<Float> positiveFloats() {
        return cons(Float.POSITIVE_INFINITY, positiveOrdinaryFloats());
    }

    /**
     * An {@code Iterable} that generates all negative {@code Float}s, including {@code -Infinity} but not negative
     * zero. Does not support removal.
     *
     * Length is 2<sup>31</sup>–2<sup>23</sup> = 2,139,095,040
     */
    @Override
    public @NotNull Iterable<Float> negativeFloats() {
        return cons(Float.NEGATIVE_INFINITY, negativeOrdinaryFloats());
    }

    /**
     * An {@code Iterable} that generates all nonzero {@code Float}s, including {@code NaN}, {@code Infinity}, and
     * {@code -Infinity}. Does not support removal.
     *
     * Length is 2<sup>32</sup>–2<sup>24</sup>+1 = 4,278,190,081
     */
    @Override
    public @NotNull Iterable<Float> nonzeroFloats() {
        return concat(
                Arrays.asList(Float.NaN, Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY),
                nonzeroOrdinaryFloats()
        );
    }

    /**
     * An {@code Iterable} that generates all {@code Float}s, including {@code NaN}, positive and negative zeros,
     * {@code Infinity}, and {@code -Infinity}. Does not support removal.
     *
     * Length is 2<sup>32</sup>–2<sup>24</sup>+3 = 4,278,190,083
     */
    @Override
    public @NotNull Iterable<Float> floats() {
        return concat(
                Arrays.asList(Float.NaN, Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY, 0.0f, -0.0f),
                nonzeroOrdinaryFloats()
        );
    }

    /**
     * An {@code Iterable} that generates all possible positive {@code double} mantissas. A {@code double}'s mantissa
     * is the unique odd integer that, when multiplied by a power of 2, equals the {@code double}. Does not support
     * removal.
     *
     * Length is 2<sup>52</sup> = 4,503,599,627,370,496
     */
    private static final @NotNull Iterable<Long> DOUBLE_MANTISSAS = IterableUtils.rangeBy(
            1L,
            2,
            1L << (FloatingPointUtils.DOUBLE_FRACTION_WIDTH + 1)
    );

    /**
     * An {@code Iterable} that generates all possible {@code double} exponents. A positive {@code double}'s exponent
     * is the base-2 logarithm of the {@code double} divided by its mantissa. Does not support removal.
     *
     * Length is 2<sup>11</sup>+52–2 = 2,098
     */
    private static final @NotNull Iterable<Integer> DOUBLE_EXPONENTS = cons(
            0,
            mux(
                    Arrays.asList(INSTANCE.range(1, Double.MAX_EXPONENT),
                    IterableUtils.rangeBy(-1, -1, FloatingPointUtils.MIN_SUBNORMAL_DOUBLE_EXPONENT))
            )
    );

    /**
     * An {@code Iterable} that generates all ordinary (neither {@code NaN} nor infinite) positive {@code Double}s.
     * Does not support removal.
     *
     * Length is 2<sup>63</sup>–2<sup>52</sup>–1 = 9,218,868,437,227,405,311
     */
    private static @NotNull Iterable<Double> positiveOrdinaryDoubles() {
        return optionalMap(
                p -> FloatingPointUtils.doubleFromMantissaAndExponent(p.a, p.b),
                INSTANCE.pairs(DOUBLE_MANTISSAS, DOUBLE_EXPONENTS)
        );
    }

    /**
     * An {@code Iterable} that generates all ordinary (neither {@code NaN}, negative zero, nor infinite) negative
     * {@code Double}s. Does not support removal.
     *
     * Length is 2<sup>63</sup>–2<sup>52</sup>–1 = 9,218,868,437,227,405,311
     */
    private static @NotNull Iterable<Double> negativeOrdinaryDoubles() {
        return map(d -> -d, positiveOrdinaryDoubles());
    }

    /**
     * An {@code Iterable} that generates all ordinary (neither {@code NaN} nor infinite) nonzero {@code Double}s. Does
     * not support removal.
     *
     * Length is 2<sup>64</sup>–2<sup>53</sup>–2 = 18,437,736,874,454,810,622
     */
    private static @NotNull Iterable<Double> nonzeroOrdinaryDoubles() {
        return mux(Arrays.asList(positiveOrdinaryDoubles(), negativeOrdinaryDoubles()));
    }

    /**
     * An {@code Iterable} that generates all positive {@code Double}s, including {@code Infinity} but not positive
     * zero. Does not support removal.
     *
     * Length is 2<sup>63</sup>–2<sup>52</sup> = 9,218,868,437,227,405,312
     */
    @Override
    public @NotNull Iterable<Double> positiveDoubles() {
        return cons(Double.POSITIVE_INFINITY, positiveOrdinaryDoubles());
    }

    /**
     * An {@code Iterable} that generates all negative {@code Double}s, including {@code -Infinity} but not negative
     * zero. Does not support removal.
     *
     * Length is 2<sup>63</sup>–2<sup>52</sup> = 9,218,868,437,227,405,312
     */
    @Override
    public @NotNull Iterable<Double> negativeDoubles() {
        return cons(Double.NEGATIVE_INFINITY, negativeOrdinaryDoubles());
    }

    /**
     * An {@code Iterable} that generates all nonzero {@code Double}s, including {@code NaN}, {@code Infinity}, and
     * {@code -Infinity}. Does not support removal.
     *
     * Length is 2<sup>64</sup>–2<sup>53</sup>+1 = 18,437,736,874,454,810,625
     */
    @Override
    public @NotNull Iterable<Double> nonzeroDoubles() {
        return concat(
                Arrays.asList(Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY),
                nonzeroOrdinaryDoubles()
        );
    }

    /**
     * An {@code Iterable} that generates all {@code Double}s, including {@code NaN}, positive and negative zeros,
     * {@code Infinity}, and {@code -Infinity}. Does not support removal.
     *
     * Length is 2<sup>64</sup>–2<sup>53</sup>+3 = 18,437,736,874,454,810,627
     */
    @Override
    public @NotNull Iterable<Double> doubles() {
        return concat(
                Arrays.asList(Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, 0.0, -0.0),
                nonzeroOrdinaryDoubles()
        );
    }

    /**
     * An {@code Iterable} that generates all {@code Float}s greater than or equal to {@code a}. Does not include
     * {@code NaN}; may include infinities. Positive and negative zeros are both present or both absent. Does not
     * support removal.
     *
     * <ul>
     *  <li>{@code a} cannot be {@code NaN}.</li>
     *  <li>The result is a non-removable {@code Iterable} containing {@code Float}s which aren't {@code NaN}.</li>
     * </ul>
     *
     * Let rep:{@code float}→{@code int} be {@link FloatingPointUtils#toOrderedRepresentation(float)}. If {@code a}≤0,
     * length is 2<sup>31</sup>–2<sup>23</sup>+2–rep({@code a}); otherwise it's
     * 2<sup>31</sup>–2<sup>23</sup>+1–rep({@code a})
     *
     * @param a the inclusive lower bound of the generated elements
     * @return {@code Float}s greater than or equal to {@code a}
     */
    @Override
    public @NotNull Iterable<Float> rangeUp(float a) {
        if (a == Float.POSITIVE_INFINITY) return Collections.singletonList(Float.POSITIVE_INFINITY);
        if (a == Float.NEGATIVE_INFINITY) return filter(f -> !Float.isNaN(f), floats());
        Iterable<Float> noNegativeZeros = cons(
                Float.POSITIVE_INFINITY,
                map(
                        q -> q.a,
                        filter(
                                BigInteger.valueOf((long) FloatingPointUtils.POSITIVE_FINITE_FLOAT_COUNT -
                                        FloatingPointUtils.toOrderedRepresentation(a) + 1),
                                p -> p.a.equals(p.b), map(BinaryFraction::floatRange,
                                rangeUp(BinaryFraction.of(a).get()))
                        )
                )
        );
        return concatMap(f -> f == 0.0f ? Arrays.asList(0.0f, -0.0f) : Collections.singletonList(f), noNegativeZeros);
    }

    /**
     * An {@code Iterable} that generates all {@code Float}s less than or equal to {@code a}. Does not include
     * {@code NaN}; may include infinities. Positive and negative zeros are both present or both absent. Does not
     * support removal.
     *
     * <ul>
     *  <li>{@code a} cannot be {@code NaN}.</li>
     *  <li>The result is a non-removable {@code Iterable} containing {@code Float}s which aren't {@code NaN}.</li>
     * </ul>
     *
     * Let rep:{@code float}→{@code int} be {@link FloatingPointUtils#toOrderedRepresentation(float)}. If {@code a}≥0,
     * length is 2<sup>31</sup>–2<sup>23</sup>+2+rep({@code a}); otherwise it's
     * 2<sup>31</sup>–2<sup>23</sup>+1+rep({@code a})
     *
     * @param a the inclusive upper bound of the generated elements
     * @return {@code Float}s less than or equal to {@code a}
     */
    @Override
    public @NotNull Iterable<Float> rangeDown(float a) {
        return map(f -> -f, rangeUp(-a));
    }

    /**
     * An {@code Iterable} that generates all {@code Float}s between {@code a} and {@code b}, inclusive. If
     * {@code a}{@literal >}{@code b}, an empty {@code Iterable} is returned. Does not include {@code NaN}; may include
     * infinities. Positive and negative zeros are both present or both absent. Does not support removal.
     *
     * <ul>
     *  <li>{@code a} cannot be {@code NaN}.</li>
     *  <li>{@code b} cannot be {@code NaN}.</li>
     *  <li>The result is a non-removable {@code Iterable} containing {@code Float}s which aren't {@code NaN}.</li>
     * </ul>
     *
     * Let rep:{@code float}→{@code int} be {@link FloatingPointUtils#toOrderedRepresentation(float)}. If
     * {@code a}≤0≤{@code b}, length is rep({@code b})–rep({@code a})+2; otherwise it's rep({@code b})–rep({@code a})+2
     *
     * @param a the inclusive lower bound of the generated elements
     * @param b the inclusive upper bound of the generated elements
     * @return {@code Float}s between {@code a} and {@code b}, inclusive
     */
    @Override
    public @NotNull Iterable<Float> range(float a, float b) {
        if (Float.isNaN(a)) {
            throw new ArithmeticException("a cannot be NaN.");
        }
        if (Float.isNaN(b)) {
            throw new ArithmeticException("b cannot be NaN.");
        }
        if (a > b) return Collections.emptyList();
        if (a == Float.NEGATIVE_INFINITY) return rangeDown(b);
        if (b == Float.POSITIVE_INFINITY) return rangeUp(a);
        if (a == Float.POSITIVE_INFINITY || b == Float.NEGATIVE_INFINITY) return Collections.emptyList();
        BinaryFraction bfa = BinaryFraction.of(a).get();
        BinaryFraction bfb = BinaryFraction.of(b).get();
        if (bfa.getExponent() > bfb.getExponent()) {
            return map(f -> -f, range(-b, -a));
        }
        Iterable<Float> noNegativeZeros = map(
                q -> q.a,
                filter(
                        BigInteger.valueOf((long) FloatingPointUtils.toOrderedRepresentation(b) -
                                FloatingPointUtils.toOrderedRepresentation(a) + 1),
                        p -> p.a.equals(p.b),
                        map(BinaryFraction::floatRange, range(bfa, bfb))
                )
        );
        return concatMap(f -> f == 0.0f ? Arrays.asList(0.0f, -0.0f) : Collections.singletonList(f), noNegativeZeros);
    }

    /**
     * An {@code Iterable} that generates all {@code Double}s greater than or equal to {@code a}. Does not include
     * {@code NaN}; may include infinities. Positive and negative zeros are both present or both absent. Does not
     * support removal.
     *
     * <ul>
     *  <li>{@code a} cannot be {@code NaN}.</li>
     *  <li>The result is a non-removable {@code Iterable} containing {@code Double}s which aren't {@code NaN}.</li>
     * </ul>
     *
     * Let rep:{@code double}→{@code long} be {@link FloatingPointUtils#toOrderedRepresentation(double)}. If
     * {@code a}≤0, length is 2<sup>63</sup>–2<sup>52</sup>+2–rep({@code a}); otherwise it's
     * 2<sup>63</sup>–2<sup>52</sup>+1–rep({@code a})
     *
     * @param a the inclusive lower bound of the generated elements
     * @return {@code Double}s greater than or equal to {@code a}
     */
    @Override
    public @NotNull Iterable<Double> rangeUp(double a) {
        if (a == Double.POSITIVE_INFINITY) return Collections.singletonList(Double.POSITIVE_INFINITY);
        if (a == Double.NEGATIVE_INFINITY) return filter(d -> !Double.isNaN(d), doubles());
        Iterable<Double> noNegativeZeros = cons(
                Double.POSITIVE_INFINITY,
                map(
                        q -> q.a,
                        filter(
                                BigInteger.valueOf(FloatingPointUtils.POSITIVE_FINITE_DOUBLE_COUNT)
                                        .subtract(BigInteger.valueOf(FloatingPointUtils.toOrderedRepresentation(a)))
                                        .add(BigInteger.ONE),
                                p -> p.a.equals(p.b), map(BinaryFraction::doubleRange,
                                        rangeUp(BinaryFraction.of(a).get()))
                        )
                )
        );
        return concatMap(d -> d == 0.0 ? Arrays.asList(0.0, -0.0) : Collections.singletonList(d), noNegativeZeros);
    }

    /**
     * An {@code Iterable} that generates all {@code Double}s less than or equal to {@code a}. Does not include
     * {@code NaN}; may include infinities. Positive and negative zeros are both present or both absent. Does not
     * support removal.
     *
     * <ul>
     *  <li>{@code a} cannot be {@code NaN}.</li>
     *  <li>The result is a non-removable {@code Iterable} containing {@code Double}s which aren't {@code NaN}.</li>
     * </ul>
     *
     * Let rep:{@code double}→{@code long} be {@link FloatingPointUtils#toOrderedRepresentation(double)}. If
     * {@code a}≤0, length is 2<sup>63</sup>–2<sup>52</sup>+2+rep({@code a}); otherwise it's
     * 2<sup>63</sup>–2<sup>52</sup>+1+rep({@code a})
     *
     * @param a the inclusive upper bound of the generated elements
     * @return {@code Double}s less than or equal to {@code a}
     */
    @Override
    public @NotNull Iterable<Double> rangeDown(double a) {
        return map(d -> -d, rangeUp(-a));
    }

    /**
     * An {@code Iterable} that generates all {@code Double}s between {@code a} and {@code b}, inclusive. If
     * {@code a}{@literal >}{@code b}, an empty {@code Iterable} is returned. Does not include {@code NaN}; may include
     * infinities. Positive and negative zeros are both present or both absent. Does not support removal.
     *
     * <ul>
     *  <li>{@code a} cannot be {@code NaN}.</li>
     *  <li>{@code b} cannot be {@code NaN}.</li>
     *  <li>The result is a non-removable {@code Iterable} containing {@code Double}s which aren't {@code NaN}.</li>
     * </ul>
     *
     * Let rep:{@code double}→{@code long} be {@link FloatingPointUtils#toOrderedRepresentation(double)}. If
     * {@code a}≤0≤{@code b}, length is rep({@code b})–rep({@code a})+2; otherwise it's rep({@code b})–rep({@code a})+2
     *
     * @param a the inclusive lower bound of the generated elements
     * @param b the inclusive upper bound of the generated elements
     * @return {@code Double}s between {@code a} and {@code b}, inclusive
     */
    @Override
    public @NotNull Iterable<Double> range(double a, double b) {
        if (Double.isNaN(a)) {
            throw new ArithmeticException("a cannot be NaN.");
        }
        if (Double.isNaN(b)) {
            throw new ArithmeticException("b cannot be NaN.");
        }
        if (a > b) return Collections.emptyList();
        if (a == Double.NEGATIVE_INFINITY) return rangeDown(b);
        if (b == Double.POSITIVE_INFINITY) return rangeUp(a);
        if (a == Double.POSITIVE_INFINITY || b == Double.NEGATIVE_INFINITY) return Collections.emptyList();
        BinaryFraction bfa = BinaryFraction.of(a).get();
        BinaryFraction bfb = BinaryFraction.of(b).get();
        if (bfa.getExponent() > bfb.getExponent()) {
            return map(f -> -f, range(-b, -a));
        }
        Iterable<Double> noNegativeZeros = map(
                q -> q.a,
                filter(
                        BigInteger.valueOf(FloatingPointUtils.toOrderedRepresentation(b))
                                .subtract(BigInteger.valueOf(FloatingPointUtils.toOrderedRepresentation(a)))
                                .add(BigInteger.ONE),
                        p -> p.a.equals(p.b),
                        map(BinaryFraction::doubleRange, range(bfa, bfb))
                )
        );
        return concatMap(d -> d == 0.0 ? Arrays.asList(0.0, -0.0) : Collections.singletonList(d), noNegativeZeros);
    }

    /**
     * An {@code Iterable} that generates all positive {@link BigDecimal}s. Does not support removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<BigDecimal> positiveBigDecimals() {
        return map(p -> new BigDecimal(p.a, p.b), pairsLogarithmicOrder(positiveBigIntegers(), integers()));
    }

    /**
     * An {@code Iterable} that generates all negative {@code BigDecimal}s. Does not support removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<BigDecimal> negativeBigDecimals() {
        return map(p -> new BigDecimal(p.a, p.b), pairsLogarithmicOrder(negativeBigIntegers(), integers()));
    }

    /**
     * An {@code Iterable} that generates all nonzero {@code BigDecimal}s. Does not support removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<BigDecimal> nonzeroBigDecimals() {
        return map(p -> new BigDecimal(p.a, p.b), pairsLogarithmicOrder(nonzeroBigIntegers(), integers()));
    }

    /**
     * An {@code Iterable} that generates all {@code BigDecimal}s. Does not support removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<BigDecimal> bigDecimals() {
        return map(p -> new BigDecimal(p.a, p.b), pairsLogarithmicOrder(bigIntegers(), integers()));
    }

    /**
     * Generates positive {@code BigDecimal}s in canonical form (see
     * {@link mho.wheels.numberUtils.BigDecimalUtils#canonicalize(BigDecimal)}). Does not support removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<BigDecimal> positiveCanonicalBigDecimals() {
        return filterInfinite(BigDecimalUtils::isCanonical, positiveBigDecimals());
    }

    /**
     * Generates negative {@code BigDecimal}s in canonical form (see
     * {@link mho.wheels.numberUtils.BigDecimalUtils#canonicalize(BigDecimal)}). Does not support removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<BigDecimal> negativeCanonicalBigDecimals() {
        return filterInfinite(BigDecimalUtils::isCanonical, negativeBigDecimals());
    }

    /**
     * Generates nonzero {@code BigDecimal}s in canonical form (see
     * {@link mho.wheels.numberUtils.BigDecimalUtils#canonicalize(BigDecimal)}). Does not support removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<BigDecimal> nonzeroCanonicalBigDecimals() {
        return filterInfinite(BigDecimalUtils::isCanonical, nonzeroBigDecimals());
    }

    /**
     * Generates {@code BigDecimal}s in canonical form (see
     * {@link mho.wheels.numberUtils.BigDecimalUtils#canonicalize(BigDecimal)}). Does not support removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<BigDecimal> canonicalBigDecimals() {
        return filterInfinite(BigDecimalUtils::isCanonical, bigDecimals());
    }

    /**
     * Generates canonical {@code BigDecimal}s greater than or equal to zero and less than or equal to a specified
     * power of ten.
     *
     * <ul>
     *  <li>{@code pow} may be any {@code int}.</li>
     *  <li>The result is a non-removable, infinite {@code Iterable} containing canonical {@code BigDecimal}s between
     *  zero and a power of ten, inclusive.</li>
     * </ul>
     *
     * Length is infinite
     *
     * @param pow an {@code int}
     * @return all canonical {@code BigDecimal}s between 0 and 10<sup>{@code pow}</sup>, inclusive
     */
    private static @NotNull Iterable<BigDecimal> zeroToPowerOfTenCanonicalBigDecimals(int pow) {
        return cons(
                BigDecimal.ZERO,
                nub(
                        map(
                                p -> BigDecimalUtils.canonicalize(
                                        new BigDecimal(
                                                p.a,
                                                p.b + MathUtils.ceilingLog(BigInteger.TEN, p.a).intValueExact() - pow
                                        )
                                ),
                                INSTANCE.pairsLogarithmicOrder(
                                        INSTANCE.positiveBigIntegers(),
                                        INSTANCE.naturalIntegers()
                                )
                        )
                )
        );
    }

    /**
     * Given an {@code Iterable xs} of unique, canonical {@code BigDecimal}s, returns an {@code Iterable} whose
     * elements are {x|{@code BigDecimalUtils.canonicalize(x)}∈{@code xs}}.
     *
     * <ul>
     *  <li>{@code xs} must only contain canonical {@code BigDecimal}s and cannot contain any duplicates.</li>
     *  <li>The result does not contain any nulls, is either empty or infinite, and for every {@code BigDecimal} x that
     *  it contains, if {@code BigDecimalUtils.canonicalize(}x{@code )} is y, then the result contains every
     *  {@code BigDecimal} which, when canonicalized, yields y. The result contains no duplicates.</li>
     * </ul>
     *
     * Length is empty if {@code xs} is empty, infinite otherwise
     *
     * @param xs an {@code Iterable} of unique, canonical {@code BigDecimal}s
     * @return all {@code BigDecimal}s which, once canonicalized, belong to {@code xs}
     */
    private static @NotNull Iterable<BigDecimal> uncanonicalize(@NotNull Iterable<BigDecimal> xs) {
        CachedIterable<Integer> integers = new CachedIterable<>(INSTANCE.integers());
        return map(
                p -> p.a.equals(BigDecimal.ZERO) ?
                        new BigDecimal(BigInteger.ZERO, integers.get(p.b).get()) :
                        BigDecimalUtils.setPrecision(
                                p.a.stripTrailingZeros(),
                                p.b + p.a.stripTrailingZeros().precision()
                        ),
                INSTANCE.pairsLogarithmicOrder(xs, INSTANCE.naturalIntegers())
        );
    }

    /**
     * An {@code Iterable} that generates all {@code BigDecimal}s greater than or equal to {@code a}. Does not support
     * removal.
     *
     * <ul>
     *  <li>{@code a} cannot be null.</li>
     *  <li>The result is a non-removable {@code Iterable} containing {@code BigDecimal}s.</li>
     * </ul>
     *
     * Length is infinite
     *
     * @param a the inclusive lower bound of the generated elements
     * @return {@code BigDecimal}s greater than or equal to {@code a}
     */
    @Override
    public @NotNull Iterable<BigDecimal> rangeUp(@NotNull BigDecimal a) {
        return uncanonicalize(rangeUpCanonical(a));
    }

    /**
     * An {@code Iterable} that generates all {@code BigDecimal}s less than or equal to {@code a}. Does not support
     * removal.
     *
     * <ul>
     *  <li>{@code a} cannot be null.</li>
     *  <li>The result is a non-removable {@code Iterable} containing {@code BigDecimal}s.</li>
     * </ul>
     *
     * Length is infinite
     *
     * @param a the inclusive upper bound of the generated elements
     * @return {@code BigDecimal}s less than or equal to {@code a}
     */
    @Override
    public @NotNull Iterable<BigDecimal> rangeDown(@NotNull BigDecimal a) {
        return uncanonicalize(rangeDownCanonical(a));
    }

    /**
     * An {@code Iterable} that generates all {@code BigDecimal}s between {@code a} and {@code b}, inclusive. If
     * {@code a}{@literal >}{@code b}, an empty {@code Iterable} is returned. Does not support removal.
     *
     * <ul>
     *  <li>{@code a} cannot be null.</li>
     *  <li>{@code b} cannot be null.</li>
     *  <li>The result is a non-removable {@code Iterable} containing {@code BigDecimal}s.</li>
     * </ul>
     *
     * Length is 0 if a>b, 1 if a=b, and infinite otherwise
     *
     * @param a the inclusive lower bound of the generated elements
     * @param b the inclusive upper bound of the generated elements
     * @return {@code BigDecimal}s between {@code a} and {@code b}, inclusive
     */
    @Override
    public @NotNull Iterable<BigDecimal> range(@NotNull BigDecimal a, @NotNull BigDecimal b) {
        if (eq(a, b)) {
            if (a.signum() == 0) {
                return map(i -> new BigDecimal(BigInteger.ZERO, i), integers());
            } else {
                return map(
                        x -> BigDecimalUtils.setPrecision(a, x),
                        IterableUtils.rangeUp(a.stripTrailingZeros().precision())
                );
            }
        }
        return uncanonicalize(rangeCanonical(a, b));
    }

    /**
     * An {@code Iterable} that generates all canonical {@code BigDecimal}s greater than or equal to {@code a}. Does
     * not support removal.
     *
     * <ul>
     *  <li>{@code a} cannot be null.</li>
     *  <li>The result is a non-removable {@code Iterable} containing canonical {@code BigDecimal}s.</li>
     * </ul>
     *
     * Length is infinite
     *
     * @param a the inclusive lower bound of the generated elements
     * @return canonical {@code BigDecimal}s greater than or equal to {@code a}
     */
    @Override
    public @NotNull Iterable<BigDecimal> rangeUpCanonical(@NotNull BigDecimal a) {
        BigDecimal ca = BigDecimalUtils.canonicalize(a);
        return map(
                bd -> BigDecimalUtils.canonicalize(bd.add(ca)),
                cons(BigDecimal.ZERO, positiveCanonicalBigDecimals())
        );
    }

    /**
     * An {@code Iterable} that generates all canonical {@code BigDecimal}s less than or equal to {@code a}. Does not
     * support removal.
     *
     * <ul>
     *  <li>{@code a} cannot be null.</li>
     *  <li>The result is a non-removable {@code Iterable} containing canonical {@code BigDecimal}s.</li>
     * </ul>
     *
     * Length is infinite
     *
     * @param a the inclusive upper bound of the generated elements
     * @return canonical {@code BigDecimal}s less than or equal to {@code a}
     */
    @Override
    public @NotNull Iterable<BigDecimal> rangeDownCanonical(@NotNull BigDecimal a) {
        return map(BigDecimal::negate, rangeUpCanonical(a.negate()));
    }

    /**
     * An {@code Iterable} that generates all canonical {@code BigDecimal}s between {@code a} and {@code b}, inclusive.
     * If {@code a}{@literal >}{@code b}, an empty {@code Iterable} is returned. Does not support removal.
     *
     * <ul>
     *  <li>{@code a} cannot be null.</li>
     *  <li>{@code b} cannot be null.</li>
     *  <li>The result is a non-removable {@code Iterable} containing canonical {@code BigDecimal}s.</li>
     * </ul>
     *
     * Length is 0 if a>b, 1 if a=b, and infinite otherwise
     *
     * @param a the inclusive lower bound of the generated elements
     * @param b the inclusive upper bound of the generated elements
     * @return canonical {@code BigDecimal}s between {@code a} and {@code b}, inclusive
     */
    @Override
    public @NotNull Iterable<BigDecimal> rangeCanonical(@NotNull BigDecimal a, @NotNull BigDecimal b) {
        if (gt(a, b)) return Collections.emptyList();
        if (eq(a, b)) return Collections.singletonList(BigDecimalUtils.canonicalize(a));
        BigDecimal difference = BigDecimalUtils.canonicalize(b.subtract(a));
        return map(
                c -> BigDecimalUtils.canonicalize(c.add(a)),
                filter(
                        bd -> le(bd, difference),
                        zeroToPowerOfTenCanonicalBigDecimals(BigDecimalUtils.ceilingLog10(difference))
                )
        );
    }

    /**
     * See {@link IterableUtils#cons}.
     */
    @Override
    public @NotNull <T> Iterable<T> withElement(@Nullable T x, @NotNull Iterable<T> xs) {
        return cons(x, xs);
    }

    /**
     * See {@link mho.wheels.math.Combinatorics#pairsLogarithmicOrder(Iterable)}
     *
     * @param xs the {@code Iterable} from which elements are selected
     * @param <T> the type of the given {@code Iterable}'s elements
     * @return all pairs of elements from {@code xs} in logarithmic order
     */
    @Override
    public @NotNull <T> Iterable<Pair<T, T>> pairsLogarithmicOrder(@NotNull Iterable<T> xs) {
        return Combinatorics.pairsLogarithmicOrder(xs);
    }

    /**
     * See {@link mho.wheels.math.Combinatorics#pairsLogarithmicOrder(Iterable, Iterable)}
     *
     * @param as the {@code Iterable} from which the first components of the pairs are selected
     * @param bs the {@code Iterable} from which the second components of the pairs are selected
     * @param <A> the type of the first {@code Iterable}'s elements
     * @param <B> the type of the second {@code Iterable}'s elements
     * @return all pairs of elements from {@code as} and {@code bs} in logarithmic order
     */
    @Override
    public @NotNull <A, B> Iterable<Pair<A, B>> pairsLogarithmicOrder(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs
    ) {
        return Combinatorics.pairsLogarithmicOrder(as, bs);
    }

    /**
     * See {@link mho.wheels.math.Combinatorics#pairsSquareRootOrder(Iterable)}
     *
     * @param xs the {@code Iterable} from which elements are selected
     * @param <T> the type of the given {@code Iterable}'s elements
     * @return all pairs of elements from {@code xs} in square-root order
     */
    @Override
    public @NotNull <T> Iterable<Pair<T, T>> pairsSquareRootOrder(@NotNull Iterable<T> xs) {
        return Combinatorics.pairsSquareRootOrder(xs);
    }

    /**
     * See {@link mho.wheels.math.Combinatorics#pairsSquareRootOrder(Iterable, Iterable)}
     *
     * @param as the {@code Iterable} from which the first components of the pairs are selected
     * @param bs the {@code Iterable} from which the second components of the pairs are selected
     * @param <A> the type of the first {@code Iterable}'s elements
     * @param <B> the type of the second {@code Iterable}'s elements
     * @return all pairs of elements from {@code as} and {@code bs} in square-root order
     */
    @Override
    public @NotNull <A, B> Iterable<Pair<A, B>> pairsSquareRootOrder(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs
    ) {
        return Combinatorics.pairsSquareRootOrder(as, bs);
    }

    public @NotNull <A, B> Iterable<Pair<A, B>> dependentPairs(
            @NotNull Iterable<A> xs,
            @NotNull Function<A, Iterable<B>> f
    ) {
        return concatMap(x -> map(y -> new Pair<>(x, y), f.apply(x)), xs);
    }

    /**
     * See {@link mho.wheels.math.Combinatorics#pairs(Iterable, Iterable)}
     *
     * @param as the {@code Iterable} from which the first components of the pairs are selected
     * @param bs the {@code Iterable} from which the second components of the pairs are selected
     * @param <A> the type of the first {@code Iterable}'s elements
     * @param <B> the type of the second {@code Iterable}'s elements
     * @return all pairs of elements from {@code as} and {@code bs}
     */
    @Override
    public @NotNull <A, B> Iterable<Pair<A, B>> pairs(@NotNull Iterable<A> as, @NotNull Iterable<B> bs) {
        return Combinatorics.pairs(as, bs);
    }

    /**
     * See {@link mho.wheels.math.Combinatorics#triples(Iterable, Iterable, Iterable)}
     *
     * @param as the {@code Iterable} from which the first components of the triples are selected
     * @param bs the {@code Iterable} from which the second components of the triples are selected
     * @param cs the {@code Iterable} from which the third components of the triples are selected
     * @param <A> the type of the first {@code Iterable}'s elements
     * @param <B> the type of the second {@code Iterable}'s elements
     * @param <C> the type of the third {@code Iterable}'s elements
     * @return all triples of elements from {@code as}, {@code bs}, and {@code cs}
     */
    @Override
    public @NotNull <A, B, C> Iterable<Triple<A, B, C>> triples(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull Iterable<C> cs
    ) {
        return Combinatorics.triples(as, bs, cs);
    }

    /**
     * See {@link mho.wheels.math.Combinatorics#quadruples(Iterable, Iterable, Iterable, Iterable)}
     *
     * @param as the {@code Iterable} from which the first components of the quadruples are selected
     * @param bs the {@code Iterable} from which the second components of the quadruples are selected
     * @param cs the {@code Iterable} from which the third components of the quadruples are selected
     * @param ds the {@code Iterable} from which the fourth components of the quadruples are selected
     * @param <A> the type of the first {@code Iterable}'s elements
     * @param <B> the type of the second {@code Iterable}'s elements
     * @param <C> the type of the third {@code Iterable}'s elements
     * @param <D> the type of the fourth {@code Iterable}'s elements
     * @return all quadruples of elements from {@code as}, {@code bs}, {@code cs}, and {@code ds}
     */
    @Override
    public @NotNull <A, B, C, D> Iterable<Quadruple<A, B, C, D>> quadruples(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull Iterable<C> cs,
            @NotNull Iterable<D> ds
    ) {
        return Combinatorics.quadruples(as, bs, cs, ds);
    }

    /**
     * See {@link mho.wheels.math.Combinatorics#quintuples(Iterable, Iterable, Iterable, Iterable, Iterable)}
     *
     * @param as the {@code Iterable} from which the first components of the quintuples are selected
     * @param bs the {@code Iterable} from which the second components of the quintuples are selected
     * @param cs the {@code Iterable} from which the third components of the quintuples are selected
     * @param ds the {@code Iterable} from which the fourth components of the quintuples are selected
     * @param es the {@code Iterable} from which the fifth components of the quintuples are selected
     * @param <A> the type of the first {@code Iterable}'s elements
     * @param <B> the type of the second {@code Iterable}'s elements
     * @param <C> the type of the third {@code Iterable}'s elements
     * @param <D> the type of the fourth {@code Iterable}'s elements
     * @param <E> the type of the fifth {@code Iterable}'s elements
     * @return all quintuples of elements from {@code as}, {@code bs}, {@code cs}, {@code ds}, and {@code es}
     */
    @Override
    public @NotNull <A, B, C, D, E> Iterable<Quintuple<A, B, C, D, E>> quintuples(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull Iterable<C> cs,
            @NotNull Iterable<D> ds,
            @NotNull Iterable<E> es
    ) {
        return Combinatorics.quintuples(as, bs, cs, ds, es);
    }

    /**
     * See {@link mho.wheels.math.Combinatorics#sextuples(Iterable, Iterable, Iterable, Iterable, Iterable,
     * Iterable)}
     *
     * @param as the {@code Iterable} from which the first components of the sextuples are selected
     * @param bs the {@code Iterable} from which the second components of the sextuples are selected
     * @param cs the {@code Iterable} from which the third components of the sextuples are selected
     * @param ds the {@code Iterable} from which the fourth components of the sextuples are selected
     * @param es the {@code Iterable} from which the fifth components of the sextuples are selected
     * @param fs the {@code Iterable} from which the sixth components of the sextuples are selected
     * @param <A> the type of the first {@code Iterable}'s elements
     * @param <B> the type of the second {@code Iterable}'s elements
     * @param <C> the type of the third {@code Iterable}'s elements
     * @param <D> the type of the fourth {@code Iterable}'s elements
     * @param <E> the type of the fifth {@code Iterable}'s elements
     * @param <F> the type of the sixth {@code Iterable}'s elements
     * @return all sextuples of elements from {@code as}, {@code bs}, {@code cs}, {@code ds}, {@code es}, and
     * {@code fs}
     */
    @Override
    public @NotNull <A, B, C, D, E, F> Iterable<Sextuple<A, B, C, D, E, F>> sextuples(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull Iterable<C> cs,
            @NotNull Iterable<D> ds,
            @NotNull Iterable<E> es,
            @NotNull Iterable<F> fs
    ) {
        return Combinatorics.sextuples(as, bs, cs, ds, es, fs);
    }

    /**
     * See {@link mho.wheels.math.Combinatorics#septuples(Iterable, Iterable, Iterable, Iterable, Iterable,
     * Iterable, Iterable)}
     *
     * @param as the {@code Iterable} from which the first components of the septuples are selected
     * @param bs the {@code Iterable} from which the second components of the septuples are selected
     * @param cs the {@code Iterable} from which the third components of the septuples are selected
     * @param ds the {@code Iterable} from which the fourth components of the septuples are selected
     * @param es the {@code Iterable} from which the fifth components of the septuples are selected
     * @param fs the {@code Iterable} from which the sixth components of the septuples are selected
     * @param gs the {@code Iterable} from which the seventh components of the septuples are selected
     * @param <A> the type of the first {@code Iterable}'s elements
     * @param <B> the type of the second {@code Iterable}'s elements
     * @param <C> the type of the third {@code Iterable}'s elements
     * @param <D> the type of the fourth {@code Iterable}'s elements
     * @param <E> the type of the fifth {@code Iterable}'s elements
     * @param <F> the type of the sixth {@code Iterable}'s elements
     * @param <G> the type of the seventh {@code Iterable}'s elements
     * @return all septuples of elements from {@code as}, {@code bs}, {@code cs}, {@code ds}, {@code es},
     * {@code fs}, and {@code gs}
     */
    @Override
    public @NotNull <A, B, C, D, E, F, G> Iterable<Septuple<A, B, C, D, E, F, G>> septuples(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull Iterable<C> cs,
            @NotNull Iterable<D> ds,
            @NotNull Iterable<E> es,
            @NotNull Iterable<F> fs,
            @NotNull Iterable<G> gs
    ) {
        return Combinatorics.septuples(as, bs, cs, ds, es, fs, gs);
    }

    @Override
    public @NotNull <T> Iterable<Pair<T, T>> pairs(@NotNull Iterable<T> xs) {
        return Combinatorics.pairs(xs);
    }

    @Override
    public @NotNull <T> Iterable<Triple<T, T, T>> triples(@NotNull Iterable<T> xs) {
        return Combinatorics.triples(xs);
    }

    @Override
    public @NotNull <T> Iterable<Quadruple<T, T, T, T>> quadruples(@NotNull Iterable<T> xs) {
        return Combinatorics.quadruples(xs);
    }

    @Override
    public @NotNull <T> Iterable<Quintuple<T, T, T, T, T>> quintuples(@NotNull Iterable<T> xs) {
        return Combinatorics.quintuples(xs);
    }

    @Override
    public @NotNull <T> Iterable<Sextuple<T, T, T, T, T, T>> sextuples(@NotNull Iterable<T> xs) {
        return Combinatorics.sextuples(xs);
    }

    @Override
    public @NotNull <T> Iterable<Septuple<T, T, T, T, T, T, T>> septuples(@NotNull Iterable<T> xs) {
        return Combinatorics.septuples(xs);
    }

    @Override
    public @NotNull <T> Iterable<List<T>> lists(int size, @NotNull Iterable<T> xs) {
        if (length(take(MAX_SIZE_FOR_SHORT_LIST_ALG + 1, xs)) < MAX_SIZE_FOR_SHORT_LIST_ALG + 1) {
            return Combinatorics.listsIncreasing(size, xs);
        } else {
            return Combinatorics.lists(size, xs);
        }
    }

    @Override
    public @NotNull <T> Iterable<List<T>> listsAtLeast(int minSize, @NotNull Iterable<T> xs) {
        if (length(take(MAX_SIZE_FOR_SHORT_LIST_ALG + 1, xs)) < MAX_SIZE_FOR_SHORT_LIST_ALG + 1) {
            return Combinatorics.listsShortlexAtLeast(minSize, xs);
        } else {
            return Combinatorics.listsAtLeast(minSize, xs);
        }
    }

    @Override
    public @NotNull <T> Iterable<List<T>> lists(@NotNull Iterable<T> xs) {
        if (length(take(MAX_SIZE_FOR_SHORT_LIST_ALG + 1, xs)) < MAX_SIZE_FOR_SHORT_LIST_ALG + 1) {
            return Combinatorics.listsShortlex(xs);
        } else {
            return Combinatorics.lists(xs);
        }
    }

    @Override
    public @NotNull Iterable<String> strings(int size, @NotNull Iterable<Character> cs) {
        return Combinatorics.strings(size, cs);
    }

    @Override
    public @NotNull Iterable<String> stringsAtLeast(int minSize, @NotNull Iterable<Character> cs) {
        return Combinatorics.stringsAtLeast(minSize, cs);
    }

    @Override
    public @NotNull Iterable<String> strings(int size) {
        return Combinatorics.strings(size, characters());
    }

    @Override
    public @NotNull Iterable<String> stringsAtLeast(int minSize) {
        return Combinatorics.stringsAtLeast(minSize, characters());
    }

    @Override
    public @NotNull Iterable<String> strings(@NotNull Iterable<Character> cs) {
        return Combinatorics.strings(cs);
    }

    @Override
    public @NotNull Iterable<String> strings() {
        return Combinatorics.strings(characters());
    }

    private static @NotNull Iterable<List<Integer>> permutationIndices(@NotNull List<Integer> start) {
        return () -> new Iterator<List<Integer>>() {
            private List<Integer> indices = start;

            @Override
            public boolean hasNext() {
                return indices != null;
            }

            @Override
            public List<Integer> next() {
                List<Integer> oldIndices = indices;
                if (weaklyDecreasing(indices)) {
                    indices = null;
                } else {
                    int i;
                    int previous = -1;
                    for (i = indices.size() - 1; i >= 0; i--) {
                        if (indices.get(i) < previous) break;
                        previous = indices.get(i);
                    }
                    i++;
                    Iterable<Integer> prefix = take(i - 1, indices);
                    Iterable<Integer> suffix = drop(i - 1, indices);
                    int pivot = minimum(filter(x -> x > head(suffix), suffix));
                    indices = toList(
                            concat(
                                    (Iterable<Iterable<Integer>>) Arrays.asList(
                                            prefix,
                                            Collections.singletonList(pivot),
                                            sort(delete(pivot, suffix))
                                    )
                            )
                    );
                }
                return oldIndices;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("cannot remove from this iterator");
            }
        };
    }

    @Override
    public @NotNull <T> Iterable<List<T>> permutations(@NotNull List<T> xs) {
        List<T> nub = toList(nub(xs));
        Map<T, Integer> indexMap = toMap(zip(nub, IterableUtils.rangeUp(0)));
        List<Integer> startingIndices = sort(map(indexMap::get, xs));
        return map(is -> toList(map(nub::get, is)), permutationIndices(startingIndices));
    }

    /**
     * Determines whether {@code this} is equal to {@code that}. This implementation is the same as in
     * {@link java.lang.Object#equals}, but repeated here for clarity.
     *
     * <ul>
     *  <li>{@code this} may be any {@code ExhaustiveProvider}.</li>
     *  <li>{@code that} may be any {@code Object}.</li>
     *  <li>The result may be either {@code boolean}.</li>
     * </ul>
     *
     * @param that The {@code ExhaustiveProvider} to be compared with {@code this}
     * @return {@code this}={@code that}
     */
    @Override
    public boolean equals(Object that) {
        return this == that;
    }

    /**
     * Calculates the hash code of {@code this}.
     *
     * <ul>
     *  <li>{@code this} may be any {@code ExhaustiveProvider}.</li>
     *  <li>The result is 0.</li>
     * </ul>
     *
     * @return {@code this}'s hash code.
     */
    @Override
    public int hashCode() {
        return 0;
    }

    /**
     * Creates a {@code String} representation of {@code this}.
     *
     * <ul>
     *  <li>{@code this} may be any {@code ExhaustiveProvider}.</li>
     *  <li>The result is {@code "ExhaustiveProvider"}.</li>
     * </ul>
     *
     * @return a {@code String} representation of {@code this}
     */
    @Override
    public String toString() {
        return "ExhaustiveProvider";
    }
}
