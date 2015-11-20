package mho.wheels.iterables;

import mho.wheels.math.BinaryFraction;
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
    public static final @NotNull ExhaustiveProvider INSTANCE = new ExhaustiveProvider();

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
    @Override
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
    @Override
    public @NotNull Iterable<Byte> bytesIncreasing() {
        return IterableUtils.rangeUp(Byte.MIN_VALUE);
    }

    /**
     * An {@code Iterable} that generates all {@link Short}s in increasing order. Does not support removal.
     *
     * Length is 2<sup>16</sup> = 65,536
     */
    @Override
    public @NotNull Iterable<Short> shortsIncreasing() {
        return IterableUtils.rangeUp(Short.MIN_VALUE);
    }

    /**
     * An {@code Iterable} that generates all {@link Integer}s in increasing order. Does not support removal.
     *
     * Length is 2<sup>32</sup> = 4,294,967,296
     */
    @Override
    public @NotNull Iterable<Integer> integersIncreasing() {
        return IterableUtils.rangeUp(Integer.MIN_VALUE);
    }

    /**
     * An {@code Iterable} that generates all {@link Long}s in increasing order. Does not support removal.
     *
     * Length is 2<sup>64</sup> = 18,446,744,073,709,551,616
     */
    @Override
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
    @Override
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
    @Override
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
     * Length is 0 if a{@literal >}b, 1 if a=b, and infinite otherwise
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
        CachedIterator<Integer> integers = new CachedIterator<>(INSTANCE.integers());
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
     * Length is 0 if a{@literal >}b, 1 if a=b, and infinite otherwise
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
     * Length is 0 if a{@literal >}b, 1 if a=b, and infinite otherwise
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
     * Generates all pairs of values, given an {@code Iterable} of possible first values of the pairs, and a function
     * mapping each possible first value to an {@code Iterable} of possible second values. For each first value, the
     * second values are listed consecutively. If all the input lists are unique, the output pairs are unique as well.
     * This method is similar to {@link ExhaustiveProvider#dependentPairsInfinite(Iterable, Function)}, but with
     * different conditions on the arguments.
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>{@code f} must terminate and not return null when applied to any element of {@code xs}. All results, except
     *  possibly the result when applied to the last element of {@code xs} (if it exists) must be finite.</li>
     *  <li>The result is non-removable and does not contain nulls.</li>
     * </ul>
     *
     * Length is finite iff {@code f.apply(last(xs))} is finite
     *
     * @param xs an {@code Iterable} of values
     * @param f a function from a value of type {@code a} to an {@code Iterable} of type-{@code B} values
     * @param <A> the type of values in the first slot
     * @param <B> the type of values in the second slot
     * @return all possible pairs of values specified by {@code xs} and {@code f}
     */
    @Override
    public @NotNull <A, B> Iterable<Pair<A, B>> dependentPairs(
            @NotNull Iterable<A> xs,
            @NotNull Function<A, Iterable<B>> f
    ) {
        return concatMap(x -> map(y -> new Pair<>(x, y), f.apply(x)), xs);
    }

    /**
     * Generates all pairs of values, given an infinite {@code Iterable} of possible first values of the pairs, and a
     * function mapping each possible first value to an infinite {@code Iterable} of possible second values. There are
     * many possible orderings of pairs; to make the ordering unique, you can specify an unpairing function–a bijective
     * function from natural {@code BigInteger}s to pairs of natural {@code BigInteger}s. If all the input lists are
     * unique, the output pairs are unique as well. This method is similar to
     * {@link ExhaustiveProvider#dependentPairs(Iterable, Function)}, but with different conditions on the arguments.
     *
     * <ul>
     *  <li>{@code unpairingFunction} must bijectively map natural {@code BigInteger}s to pairs of natural
     *  {@code BigInteger}s, and also must have the property
     *  {@code unpairingFunction}<sup>–1</sup>(a, b){@literal <}{@code unpairingFunction}<sup>–1</sup>(a, b+1) for all
     *  natural a, b.</li>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>{@code f} must terminate and not return null when applied to any element of {@code xs}. All results must be
     *  infinite.</li>
     *  <li>The result is non-removable and does not contain nulls.</li>
     * </ul>
     *
     * Length is infinite
     *
     * @param xs an {@code Iterable} of values
     * @param f a function from a value of type {@code a} to an {@code Iterable} of type-{@code B} values
     * @param <A> the type of values in the first slot
     * @param <B> the type of values in the second slot
     * @return all possible pairs of values specified by {@code xs} and {@code f}
     */
    private @NotNull <A, B> Iterable<Pair<A, B>> dependentPairsInfinite(
            @NotNull Function<BigInteger, Pair<BigInteger, BigInteger>> unpairingFunction,
            @NotNull Iterable<A> xs,
            @NotNull Function<A, Iterable<B>> f
    ) {
        Iterable<BigInteger> naturalBigIntegers = naturalBigIntegers();
        return () -> new NoRemoveIterator<Pair<A, B>>() {
            private final @NotNull CachedIterator<A> as = new CachedIterator<>(xs);
            private final @NotNull Map<A, Iterator<B>> aToBs = new HashMap<>();
            private final @NotNull Iterator<BigInteger> indices = naturalBigIntegers.iterator();

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public @NotNull Pair<A, B> next() {
                Pair<BigInteger, BigInteger> index = unpairingFunction.apply(indices.next());
                A a = as.get(index.a).get();
                Iterator<B> bs = aToBs.get(a);
                if (bs == null) {
                    bs = f.apply(a).iterator();
                    aToBs.put(a, bs);
                }
                return new Pair<>(a, bs.next());
            }
        };
    }

    /**
     * Generates all pairs of values, given an infinite {@code Iterable} of possible first values of the pairs, and a
     * function mapping each possible first value to an infinite {@code Iterable} of possible second values. The pairs
     * are traversed along a Z-curve. If all the input lists are unique, the output pairs are unique as well. This
     * method is similar to {@link ExhaustiveProvider#dependentPairs(Iterable, Function)}, but with different
     * conditions on the arguments.
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>{@code f} must terminate and not return null when applied to any element of {@code xs}. All results must be
     *  infinite.</li>
     *  <li>The result is non-removable and does not contain nulls.</li>
     * </ul>
     *
     * Length is infinite
     *
     * @param xs an {@code Iterable} of values
     * @param f a function from a value of type {@code a} to an {@code Iterable} of type-{@code B} values
     * @param <A> the type of values in the first slot
     * @param <B> the type of values in the second slot
     * @return all possible pairs of values specified by {@code xs} and {@code f}
     */
    @Override
    public @NotNull <A, B> Iterable<Pair<A, B>> dependentPairsInfinite(
            @NotNull Iterable<A> xs,
            @NotNull Function<A, Iterable<B>> f
    ) {
        return dependentPairsInfinite(
                bi -> {
                    List<BigInteger> list = IntegerUtils.demux(2, bi);
                    return new Pair<>(list.get(0), list.get(1));
                },
                xs,
                f
        );
    }

    /**
     * Generates all pairs of values in such a way that the second value grows linearly, but the first grows
     * logarithmically, given an infinite {@code Iterable} of possible first values of the pairs, and a function
     * mapping each possible first value to an infinite {@code Iterable} of possible second values. If all the input
     * lists are unique, the output pairs are unique as well. This method is similar to
     * {@link ExhaustiveProvider#dependentPairs(Iterable, Function)}, but with different conditions on the arguments.
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>{@code f} must terminate and not return null when applied to any element of {@code xs}. All results must be
     *  infinite.</li>
     *  <li>The result is non-removable and does not contain nulls.</li>
     * </ul>
     *
     * Length is infinite
     *
     * @param xs an {@code Iterable} of values
     * @param f a function from a value of type {@code a} to an {@code Iterable} of type-{@code B} values
     * @param <A> the type of values in the first slot
     * @param <B> the type of values in the second slot
     * @return all possible pairs of values specified by {@code xs} and {@code f}
     */
    @Override
    public @NotNull <A, B> Iterable<Pair<A, B>> dependentPairsInfiniteLogarithmicOrder(
            @NotNull Iterable<A> xs,
            @NotNull Function<A, Iterable<B>> f
    ) {
        return dependentPairsInfinite(
                i -> {
                    Pair<BigInteger, BigInteger> p = IntegerUtils.logarithmicDemux(i);
                    return new Pair<>(p.b, p.a);
                },
                xs,
                f
        );
    }

    /**
     * Generates all pairs of values in such a way that the second value grows as O(n<sup>2/3</sup>), but the first
     * grows as O(n<sup>1/3</sup>), given an infinite {@code Iterable} of possible first values of the pairs, and a
     * function mapping each possible first value to an infinite {@code Iterable} of possible second values. If all the
     * input lists are unique, the output pairs are unique as well. This method is similar to
     * {@link ExhaustiveProvider#dependentPairs(Iterable, Function)}, but with different conditions on the arguments.
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>{@code f} must terminate and not return null when applied to any element of {@code xs}. All results must be
     *  infinite.</li>
     *  <li>The result is non-removable and does not contain nulls.</li>
     * </ul>
     *
     * Length is infinite
     *
     * @param xs an {@code Iterable} of values
     * @param f a function from a value of type {@code a} to an {@code Iterable} of type-{@code B} values
     * @param <A> the type of values in the first slot
     * @param <B> the type of values in the second slot
     * @return all possible pairs of values specified by {@code xs} and {@code f}
     */
    @Override
    public @NotNull <A, B> Iterable<Pair<A, B>> dependentPairsInfiniteSquareRootOrder(
            @NotNull Iterable<A> xs,
            @NotNull Function<A, Iterable<B>> f
    ) {
        return dependentPairsInfinite(
                i -> {
                    Pair<BigInteger, BigInteger> p = IntegerUtils.squareRootDemux(i);
                    return new Pair<>(p.b, p.a);
                },
                xs,
                f
        );
    }

    /**
     * Generates all possible pairs of values where the first element is selected from one {@code Iterable} and the
     * second element from another. There are many possible orderings of pairs; to make the ordering unique, you
     * can specify an unpairing function–a bijective function from natural {@code BigInteger}s to pairs of natural
     * {@code BigInteger}s. Does not support removal.
     *
     * <ul>
     *  <li>{@code unpairingFunction} must bijectively map natural {@code BigInteger}s to pairs of natural
     *  {@code BigInteger}s.</li>
     *  <li>{@code as} cannot be null.</li>
     *  <li>{@code bs} cannot be null.</li>
     *  <li>The result is non-removable and is the cartesian product of two sets.</li>
     * </ul>
     *
     * Length is |{@code as}||{@code bs}|
     *
     * @param unpairingFunction a bijection ℕ→ℕ×ℕ
     * @param as the {@code Iterable} from which the first components of the pairs are selected
     * @param bs the {@code Iterable} from which the second components of the pairs are selected
     * @param <A> the type of the first {@code Iterable}'s elements
     * @param <B> the type of the second {@code Iterable}'s elements
     * @return all pairs of elements from {@code as} and {@code bs} in an order determined by {@code unpairingFunction}
     */
    private @NotNull <A, B> Iterable<Pair<A, B>> pairsByFunction(
            @NotNull Function<BigInteger, Pair<BigInteger, BigInteger>> unpairingFunction,
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs
    ) {
        if (isEmpty(as) || isEmpty(bs)) return Collections.emptyList();
        Iterable<BigInteger> naturalBigIntegers = naturalBigIntegers();
        return () -> new EventuallyKnownSizeIterator<Pair<A, B>>() {
            private final @NotNull CachedIterator<A> cas = new CachedIterator<>(as);
            private final @NotNull CachedIterator<B> cbs = new CachedIterator<>(bs);
            private final @NotNull Iterator<BigInteger> is = naturalBigIntegers.iterator();

            @Override
            public @NotNull Pair<A, B> advance() {
                while (true) {
                    Pair<BigInteger, BigInteger> indices = unpairingFunction.apply(is.next());
                    NullableOptional<A> oa = cas.get(indices.a.intValueExact());
                    if (!oa.isPresent()) continue;
                    NullableOptional<B> ob = cbs.get(indices.b.intValueExact());
                    if (!ob.isPresent()) continue;
                    if (!outputSizeKnown() && cas.knownSize().isPresent() && cbs.knownSize().isPresent()) {
                        setOutputSize(
                                BigInteger.valueOf(cas.knownSize().get())
                                        .multiply(BigInteger.valueOf(cbs.knownSize().get()))
                        );
                    }
                    return new Pair<>(oa.get(), ob.get());
                }
            }
        };
    }

    /**
     * Generates all possible pairs of values where the first element and second elements are selected from the same
     * {@code Iterable}. There are many possible orderings of pairs; to make the ordering unique, you can specify an
     * unpairing function–a bijective function from natural {@code BigInteger}s to pairs of natural
     * {@code BigInteger}s. Does not support removal.
     *
     * <ul>
     *  <li>{@code unpairingFunction} must bijectively map natural {@code BigInteger}s to pairs of natural
     *  {@code BigInteger}s.</li>
     *  <li>{@code as} cannot be null.</li>
     *  <li>{@code bs} cannot be null.</li>
     *  <li>The result is non-removable and is the cartesian product of two sets.</li>
     * </ul>
     *
     * Length is |{@code as}||{@code bs}|
     *
     * @param unpairingFunction a bijection ℕ→ℕ×ℕ
     * @param xs the {@code Iterable} from which the components of the pairs are selected
     * @param <T> the type of the {@code Iterable}'s elements
     * @return all pairs of elements from {@code xs} in an order determined by {@code unpairingFunction}
     */
    private @NotNull <T> Iterable<Pair<T, T>> pairsByFunction(
            @NotNull Function<BigInteger, Pair<BigInteger, BigInteger>> unpairingFunction,
            @NotNull Iterable<T> xs
    ) {
        if (isEmpty(xs)) return Collections.emptyList();
        Iterable<BigInteger> naturalBigIntegers = naturalBigIntegers();
        return () -> new EventuallyKnownSizeIterator<Pair<T, T>>() {
            private final @NotNull CachedIterator<T> cxs = new CachedIterator<>(xs);
            private final @NotNull Iterator<BigInteger> is = naturalBigIntegers.iterator();

            @Override
            public @NotNull Pair<T, T> advance() {
                while (true) {
                    Pair<BigInteger, BigInteger> indices = unpairingFunction.apply(is.next());
                    NullableOptional<T> oa = cxs.get(indices.a.intValueExact());
                    if (!oa.isPresent()) continue;
                    NullableOptional<T> ob = cxs.get(indices.b.intValueExact());
                    if (!ob.isPresent()) continue;
                    if (!outputSizeKnown() && cxs.knownSize().isPresent()) {
                        setOutputSize(BigInteger.valueOf(cxs.knownSize().get()).pow(2));
                    }
                    return new Pair<>(oa.get(), ob.get());
                }
            }
        };
    }

    /**
     * Returns all pairs of elements taken from two {@code Iterable}s in such a way that the first component grows
     * linearly but the second grows logarithmically. Does not support removal.
     *
     * <ul>
     *  <li>{@code as} cannot be null.</li>
     *  <li>{@code bs} cannot be null.</li>
     *  <li>The result is a non-removable {@code Iterable} containing all pairs of elements taken from two
     *  {@code Iterable}s. The ordering of these elements is determined by mapping the sequence 0, 1, 2, ... by
     *  {@link IntegerUtils#logarithmicDemux(BigInteger)} and interpreting the resulting pairs as indices into the
     *  original {@code Iterable}s.</li>
     * </ul>
     *
     * Length is |{@code as}||{@code bs}|
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
        return pairsByFunction(IntegerUtils::logarithmicDemux, as, bs);
    }

    /**
     * Returns all pairs of elements taken from one {@code Iterable}s in such a way that the first component grows
     * linearly but the second grows logarithmically (hence the name). Does not support removal.
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result is a non-removable {@code Iterable} containing all pairs of elements taken from some
     *  {@code Iterable}. The ordering of these elements is determined by mapping the sequence 0, 1, 2, ... by
     *  {@link IntegerUtils#logarithmicDemux(BigInteger)} and interpreting the resulting pairs as indices into the
     *  original {@code Iterable}.</li>
     * </ul>
     *
     * Length is |{@code xs}|<sup>2</sup>
     *
     * @param xs the {@code Iterable} from which elements are selected
     * @param <T> the type of the given {@code Iterable}'s elements
     * @return all pairs of elements from {@code xs} in logarithmic order
     */
    @Override
    public @NotNull <T> Iterable<Pair<T, T>> pairsLogarithmicOrder(@NotNull Iterable<T> xs) {
        return pairsByFunction(IntegerUtils::logarithmicDemux, xs);
    }

    /**
     * Returns all pairs of elements taken from two {@code Iterable}s in such a way that the first component grows
     * as O(n<sup>2/3</sup>) but the second grows as O(n<sup>1/3</sup>). Does not support removal.
     *
     * <ul>
     *  <li>{@code as} cannot be null.</li>
     *  <li>{@code bs} cannot be null.</li>
     *  <li>The result is a non-removable {@code Iterable} containing all pairs of elements taken from two
     *  {@code Iterable}s. The ordering of these elements is determined by mapping the sequence 0, 1, 2, ... by
     *  {@link IntegerUtils#squareRootDemux(BigInteger)} and interpreting the resulting pairs as indices into the
     *  original {@code Iterable}s.</li>
     * </ul>
     *
     * Length is |{@code as}||{@code bs}|
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
        return pairsByFunction(IntegerUtils::squareRootDemux, as, bs);
    }

    /**
     * Returns all pairs of elements taken from one {@code Iterable}s in such a way that the first component grows
     * as O(n<sup>2/3</sup>) but the second grows as O(n<sup>1/3</sup>). Does not support removal.
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result is a non-removable {@code Iterable} containing all pairs of elements taken from some
     *  {@code Iterable}. The ordering of these elements is determined by mapping the sequence 0, 1, 2, ... by
     *  {@link IntegerUtils#squareRootDemux(BigInteger)} and interpreting the resulting pairs as indices into the
     *  original {@code Iterable}.</li>
     * </ul>
     *
     * Length is |{@code xs}|<sup>2</sup>
     *
     * @param xs the {@code Iterable} from which elements are selected
     * @param <T> the type of the given {@code Iterable}'s elements
     * @return all pairs of elements from {@code xs} in square-root order
     */
    @Override
    public @NotNull <T> Iterable<Pair<T, T>> pairsSquareRootOrder(@NotNull Iterable<T> xs) {
        return pairsByFunction(IntegerUtils::squareRootDemux, xs);
    }

    /**
     * Given a list of non-negative integers in weakly increasing order, returns all distinct permutations of these
     * integers in lexicographic order.
     *
     * <ul>
     *  <li>{@code start} must be weakly increasing and can only contain non-negative integers.</li>
     *  <li>The result is in lexicographic order, contains only non-negative integers, contains no repetitions, and is
     *  the complete set of permutations of some list.</li>
     * </ul>
     *
     * Length is the number of distinct permutations of {@code start}
     *
     * @param start a lexicographically smallest permutation
     * @return all permutations of {@code start}
     */
    private static @NotNull Iterable<List<Integer>> finitePermutationIndices(@NotNull List<Integer> start) {
        BigInteger outputSize = MathUtils.permutationCount(start);
        return () -> new EventuallyKnownSizeIterator<List<Integer>>() {
            private @NotNull List<Integer> list = toList(start);
            private boolean first = true;
            {
                setOutputSize(outputSize);
            }

            @Override
            public @NotNull List<Integer> advance() {
                if (first) {
                    first = false;
                    return list;
                }
                list = toList(list);
                int k = list.size() - 2;
                while (list.get(k) >= list.get(k + 1)) k--;
                int m = list.size() - 1;
                while (m > k && list.get(k) >= list.get(m)) m--;
                Collections.swap(list, k, m);
                int i = k + 1;
                int j = list.size() - 1;
                while (i < j) {
                    Collections.swap(list, i, j);
                    i++;
                    j--;
                }
                return list;
            }
        };
    }

    /**
     * Returns an {@code Iterable} containing every distinct permutation of a list. The result is ordered
     * lexicographically, preserving the order in the initial list.
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result is lexicographically increasing with respect to some order on {@code T}, contains no
     *  repetitions, and is the complete set of permutations of some list.</li>
     * </ul>
     *
     * Length is the number of distinct permutations of {@code start}
     *
     * @param xs a list of elements
     * @param <T> the type of the given {@code List}'s elements
     * @return all distinct permutations of {@code xs}
     */
    @Override
    public @NotNull <T> Iterable<List<T>> permutationsFinite(@NotNull List<T> xs) {
        List<T> nub = toList(nub(xs));
        Map<T, Integer> indexMap = toMap(zip(nub, IterableUtils.rangeUp(0)));
        List<Integer> startingIndices = sort(map(indexMap::get, xs));
        return map(is -> toList(map(nub::get, is)), finitePermutationIndices(startingIndices));
    }

    /**
     * Returns an {@code Iterable} containing every permutation of an {@code Iterable}. If the {@code Iterable} is
     * finite, all permutations are generated; if it is infinite, then only permutations that are equal to the identity
     * except in a finite prefix are generated. Unlike {@link ExhaustiveProvider#permutationsFinite(List)}, this method
     * may return an {@code Iterable} with duplicate elements.
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result is the set of permutations of some {@code Iterable} such that each permutation differs from
     *  {@code xs} in a finite prefix.</li>
     * </ul>
     *
     * Length is |{@code xs}|!
     *
     * @param xs an {@code Iterable} of elements
     * @param <T> the type of the given {@code Iterable}'s elements
     */
    @Override
    public @NotNull <T> Iterable<Iterable<T>> prefixPermutations(@NotNull Iterable<T> xs) {
        if (!lengthAtLeast(2, xs)) return Collections.singletonList(new NoRemoveIterable<>(xs));
        return () -> new EventuallyKnownSizeIterator<Iterable<T>>() {
            private final @NotNull CachedIterator<T> cxs = new CachedIterator<>(xs);
            private Iterator<List<Integer>> prefixIndices;
            private int prefixLength = 0;

            @Override
            public @NotNull Iterable<T> advance() {
                if (prefixIndices == null || !prefixIndices.hasNext()) {
                    updatePrefixIndices();
                }
                Iterable<T> permutation = concat(cxs.get(prefixIndices.next()).get(), drop(prefixLength, xs));
                if (!outputSizeKnown() && cxs.knownSize().isPresent()) {
                    setOutputSize(MathUtils.factorial(cxs.knownSize().get()));
                }
                return permutation;
            }

            private void updatePrefixIndices() {
                if (prefixIndices == null) {
                    prefixLength = 0;
                } else if (prefixLength == 0) {
                    prefixLength = 2;
                } else {
                    prefixLength++;
                }
                prefixIndices = filter(
                        is -> is.isEmpty() || last(is) != prefixLength - 1,
                        finitePermutationIndices(toList(IterableUtils.range(0, prefixLength - 1)))
                ).iterator();
            }
        };
    }

    /**
     * Returns an {@code Iterable} containing all {@code List}s of a given length with elements from a given
     * {@code List}. The {@code List}s are ordered lexicographically, matching the order given by the original
     * {@code List}. Does not support removal.
     *
     * <ul>
     *  <li>{@code size} cannot be negative.</li>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result is finite. All of its elements have the same length. None are empty, unless the result consists
     *  entirely of one empty element.</li>
     * </ul>
     *
     * Length is |{@code xs}|<sup>{@code size}</sup>
     *
     * @param size the length of the result lists
     * @param xs the {@code List} from which elements are selected
     * @param <T> the type of the given {@code List}'s elements
     * @return all {@code List}s of a given length created from {@code xs}
     */
    @Override
    public @NotNull <T> Iterable<List<T>> listsLex(int size, @NotNull List<T> xs) {
        if (size < 0) {
            throw new IllegalArgumentException("size cannot be negative. Invalid size: " + size);
        }
        List<T> copy = toList(xs);
        int xsSize = copy.size();
        BigInteger outputSize = BigInteger.valueOf(xsSize).pow(size);
        return () -> new EventuallyKnownSizeIterator<List<T>>() {
            private final @NotNull List<Integer> indices = toList(replicate(size, 0));
            private boolean first = true;
            {
                setOutputSize(outputSize);
            }

            @Override
            public List<T> advance() {
                if (first) {
                    first = false;
                } else {
                    for (int i = size - 1; i >= 0; i--) {
                        int j = indices.get(i);
                        if (j == xsSize - 1) {
                            indices.set(i, 0);
                        } else {
                            indices.set(i, j + 1);
                            break;
                        }
                    }
                }
                return toList(map(copy::get, indices));
            }
        };
    }

    /**
     * Given two {@code Iterable}s, returns all {@code Pair}s of elements from these {@code Iterable}s. The
     * {@code Pair}s are ordered lexicographically, matching the order given by the original {@code Iterable}s. The
     * second {@code Iterable} must be finite. Does not support removal.
     *
     * <ul>
     *  <li>{@code as} cannot be null.</li>
     *  <li>{@code bs} cannot be null.</li>
     *  <li>The result is the Cartesian product of two {@code Iterable}s.</li>
     * </ul>
     *
     * Length is |{@code as}||{@code bs}|
     *
     * @param as the first {@code Iterable}
     * @param bs the second {@code Iterable}
     * @param <A> the type of the first {@code Iterable}'s elements
     * @param <B> the type of the second {@code Iterable}'s elements
     * @return all ordered {@code Pair}s of elements from {@code as} and {@code bs}
     */
    @Override
    public @NotNull <A, B> Iterable<Pair<A, B>> pairsLex(@NotNull Iterable<A> as, @NotNull List<B> bs) {
        if (isEmpty(bs)) return Collections.emptyList();
        return concatMap(p -> zip(repeat(p.a), p.b), zip(as, repeat(bs)));
    }

    /**
     * Given three {@code Iterable}s, returns all ordered {@code Triple}s of elements from these {@code Iterable}s. The
     * {@code Triple}s are ordered lexicographically, matching the order given by the original {@code Iterable}s. All
     * {@code Iterable}s but the first must be finite. Does not support removal.
     *
     * <ul>
     *  <li>{@code as} cannot be null.</li>
     *  <li>{@code bs} cannot be null.</li>
     *  <li>{@code cs} cannot be null.</li>
     *  <li>The result is the Cartesian product of three {@code Iterable}s.</li>
     * </ul>
     *
     * Length is |{@code as}||{@code bs}||{@code cs}|
     *
     * @param as the first {@code Iterable}
     * @param bs the second {@code Iterable}
     * @param cs the third {@code Iterable}
     * @param <A> the type of the first {@code Iterable}'s elements
     * @param <B> the type of the second {@code Iterable}'s elements
     * @param <C> the type of the third {@code Iterable}'s elements
     * @return all ordered {@code Triple}s of elements from {@code as}, {@code bs}, and {@code cs}
     */
    @Override
    public @NotNull <A, B, C> Iterable<Triple<A, B, C>> triplesLex(
            @NotNull Iterable<A> as,
            @NotNull List<B> bs,
            @NotNull List<C> cs
    ) {
        return map(p -> new Triple<>(p.a.a, p.a.b, p.b), pairsLex(pairsLex(as, bs), cs));
    }

    /**
     * Given four {@code Iterable}s, returns all {@code Quadruple}s of elements from these {@code Iterable}s. The
     * {@code Quadruple}s are ordered lexicographically, matching the order given by the original {@code Iterable}s.
     * All {@code Iterable}s but the first must be finite. Does not support removal.
     *
     * <ul>
     *  <li>{@code as} cannot be null.</li>
     *  <li>{@code bs} cannot be null.</li>
     *  <li>{@code cs} cannot be null.</li>
     *  <li>{@code ds} cannot be null.</li>
     *  <li>The result is the Cartesian product of four {@code Iterable}s.</li>
     * </ul>
     *
     * Length is |{@code as}||{@code bs}||{@code cs}||{@code ds}|
     *
     * @param as the first {@code Iterable}
     * @param bs the second {@code Iterable}
     * @param cs the third {@code Iterable}
     * @param ds the fourth {@code Iterable}
     * @param <A> the type of the first {@code Iterable}'s elements
     * @param <B> the type of the second {@code Iterable}'s elements
     * @param <C> the type of the third {@code Iterable}'s elements
     * @param <D> the type of the fourth {@code Iterable}'s elements
     * @return all ordered {@code Quadruple}s of elements from {@code as}, {@code bs}, {@code cs}, and {@code ds}
     */
    @Override
    public @NotNull <A, B, C, D> Iterable<Quadruple<A, B, C, D>> quadruplesLex(
            @NotNull Iterable<A> as,
            @NotNull List<B> bs,
            @NotNull List<C> cs,
            @NotNull List<D> ds
    ) {
        return map(p -> new Quadruple<>(p.a.a, p.a.b, p.a.c, p.b), pairsLex(triplesLex(as, bs, cs), ds));
    }

    /**
     * Given five {@code Iterable}s, returns all {@code Quintuple}s of elements from these {@code Iterable}s. The
     * {@code Quintuple}s are ordered lexicographically, matching the order given by the original {@code Iterable}s.
     * All {@code Iterable}s but the first must be finite. Does not support removal.
     *
     * <ul>
     *  <li>{@code as} cannot be null.</li>
     *  <li>{@code bs} cannot be null.</li>
     *  <li>{@code cs} cannot be null.</li>
     *  <li>{@code ds} cannot be null.</li>
     *  <li>{@code es} cannot be null.</li>
     *  <li>The result is the Cartesian product of five {@code Iterable}s.</li>
     * </ul>
     *
     * Length is |{@code as}||{@code bs}||{@code cs}||{@code ds}||{@code es}|
     *
     * @param as the first {@code Iterable}
     * @param bs the second {@code Iterable}
     * @param cs the third {@code Iterable}
     * @param ds the fourth {@code Iterable}
     * @param es the fifth {@code Iterable}
     * @param <A> the type of the first {@code Iterable}'s elements
     * @param <B> the type of the second {@code Iterable}'s elements
     * @param <C> the type of the third {@code Iterable}'s elements
     * @param <D> the type of the fourth {@code Iterable}'s elements
     * @param <E> the type of the fifth {@code Iterable}'s elements
     * @return all ordered {@code Quintuple}s of elements from {@code as}, {@code bs}, {@code cs}, {@code ds}, and
     * {@code es}
     */
    @Override
    public @NotNull <A, B, C, D, E> Iterable<Quintuple<A, B, C, D, E>> quintuplesLex(
            @NotNull Iterable<A> as,
            @NotNull List<B> bs,
            @NotNull List<C> cs,
            @NotNull List<D> ds,
            @NotNull List<E> es
    ) {
        return map(
                p -> new Quintuple<>(p.a.a, p.a.b, p.a.c, p.a.d, p.b),
                pairsLex(quadruplesLex(as, bs, cs, ds), es)
        );
    }

    /**
     * Given six {@code Iterable}s, returns all {@code Sextuple}s of elements from these {@code Iterable}s. The
     * {@code Sextuple}s are ordered lexicographically, matching the order given by the original {@code Iterable}s.
     * All {@code Iterable}s but the first must be finite. Does not support removal.
     *
     * <ul>
     *  <li>{@code as} cannot be null.</li>
     *  <li>{@code bs} cannot be null.</li>
     *  <li>{@code cs} cannot be null.</li>
     *  <li>{@code ds} cannot be null.</li>
     *  <li>{@code es} cannot be null.</li>
     *  <li>{@code fs} cannot be null.</li>
     *  <li>The result is the Cartesian product of six {@code Iterable}s.</li>
     * </ul>
     *
     * Length is |{@code as}||{@code bs}||{@code cs}||{@code ds}||{@code es}||{@code fs}|
     *
     * @param as the first {@code Iterable}
     * @param bs the second {@code Iterable}
     * @param cs the third {@code Iterable}
     * @param ds the fourth {@code Iterable}
     * @param es the fifth {@code Iterable}
     * @param fs the sixth {@code Iterable}
     * @param <A> the type of the first {@code Iterable}'s elements
     * @param <B> the type of the second {@code Iterable}'s elements
     * @param <C> the type of the third {@code Iterable}'s elements
     * @param <D> the type of the fourth {@code Iterable}'s elements
     * @param <E> the type of the fifth {@code Iterable}'s elements
     * @param <F> the type of the sixth {@code Iterable}'s elements
     * @return all ordered {@code Sextuple}s of elements from {@code as}, {@code bs}, {@code cs}, {@code ds},
     * {@code es}, and {@code fs}
     */
    @Override
    public @NotNull <A, B, C, D, E, F> Iterable<Sextuple<A, B, C, D, E, F>> sextuplesLex(
            @NotNull Iterable<A> as,
            @NotNull List<B> bs,
            @NotNull List<C> cs,
            @NotNull List<D> ds,
            @NotNull List<E> es,
            @NotNull List<F> fs
    ) {
        return map(
                p -> new Sextuple<>(p.a.a, p.a.b, p.a.c, p.a.d, p.a.e, p.b),
                pairsLex(quintuplesLex(as, bs, cs, ds, es), fs)
        );
    }

    /**
     * Given seven {@code Iterable}s, returns all {@code Septuple}s of elements from these {@code Iterable}s. The
     * {@code Septuple}s are ordered lexicographically, matching the order given by the original {@code Iterable}s.
     * All {@code Iterable}s but the first must be finite. Does not support removal.
     *
     * <ul>
     *  <li>{@code as} cannot be null.</li>
     *  <li>{@code bs} cannot be null.</li>
     *  <li>{@code cs} cannot be null.</li>
     *  <li>{@code ds} cannot be null.</li>
     *  <li>{@code es} cannot be null.</li>
     *  <li>{@code fs} cannot be null.</li>
     *  <li>{@code gs} cannot be null.</li>
     *  <li>The result is the Cartesian product of seven {@code Iterable}s.</li>
     * </ul>
     *
     * Length is |{@code as}||{@code bs}||{@code cs}||{@code ds}||{@code es}||{@code fs}||{@code gs}
     *
     * @param as the first {@code Iterable}
     * @param bs the second {@code Iterable}
     * @param cs the third {@code Iterable}
     * @param ds the fourth {@code Iterable}
     * @param es the fifth {@code Iterable}
     * @param fs the sixth {@code Iterable}
     * @param gs the seventh {@code Iterable}
     * @param <A> the type of the first {@code Iterable}'s elements
     * @param <B> the type of the second {@code Iterable}'s elements
     * @param <C> the type of the third {@code Iterable}'s elements
     * @param <D> the type of the fourth {@code Iterable}'s elements
     * @param <E> the type of the fifth {@code Iterable}'s elements
     * @param <F> the type of the sixth {@code Iterable}'s elements
     * @param <G> the type of the seventh {@code Iterable}'s elements
     * @return all ordered {@code Septuple}s of elements from {@code as}, {@code bs}, {@code cs}, {@code ds},
     * {@code es}, {@code fs}, and {@code gs}
     */
    @Override
    public @NotNull <A, B, C, D, E, F, G> Iterable<Septuple<A, B, C, D, E, F, G>> septuplesLex(
            @NotNull Iterable<A> as,
            @NotNull List<B> bs,
            @NotNull List<C> cs,
            @NotNull List<D> ds,
            @NotNull List<E> es,
            @NotNull List<F> fs,
            @NotNull List<G> gs
    ) {
        return map(
                p -> new Septuple<>(p.a.a, p.a.b, p.a.c, p.a.d, p.a.e, p.a.f, p.b),
                pairsLex(sextuplesLex(as, bs, cs, ds, es, fs), gs)
        );
    }

    /**
     * Returns an {@code Iterable} containing all {@code String}s of a given length with characters from a given
     * {@code String}. The {@code String}s are ordered lexicographically, matching the order given by the original
     * {@code String}. Does not support removal.
     *
     * <ul>
     *  <li>{@code size} cannot be negative.</li>
     *  <li>{@code s} cannot be null.</li>
     *  <li>The result is finite. All of its {@code String}s have the same length. None are empty, unless the result
     *  consists entirely of one empty {@code String}.</li>
     * </ul>
     *
     * Length is |{@code s}|<sup>{@code size}</sup>
     *
     * @param size the length of the result {@code String}
     * @param s the {@code String} from which characters are selected
     * @return all Strings of a given length created from {@code s}
     */
    @Override
    public @NotNull Iterable<String> stringsLex(int size, @NotNull String s) {
        return map(IterableUtils::charsToString, listsLex(size, toList(s)));
    }

    /**
     * Returns an {@code Iterable} containing all {@code Lists}s with elements from a given {@code List}. The
     * {@code List}s are in shortlex order; that is, shorter {@code List}s precede longer {@code List}s, and
     * {@code List}s of the same length are ordered lexicographically, matching the order given by the original
     * {@code List}. Does not support removal.
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result either consists of a single empty {@code List}, or is infinite. It is in shortlex order
     *  (according to some ordering of its elements) and contains every {@code List} of elements drawn from some
     *  sequence.</li>
     * </ul>
     *
     * Length is 1 if {@code xs} is empty, infinite otherwise
     *
     * @param xs the {@code List} from which elements are selected
     * @param <T> the type of the given {@code List}'s elements
     * @return all {@code List}s created from {@code xs}
     */
    @Override
    public @NotNull <T> Iterable<List<T>> listsShortlex(@NotNull List<T> xs) {
        if (isEmpty(xs)) return Collections.singletonList(Collections.emptyList());
        return concatMap(i -> listsLex(i.intValueExact(), xs), naturalBigIntegers());
    }

    /**
     * Returns an {@code Iterable} containing all {@code String}s with characters from a given {@code String}. The
     * {@code String}s are in shortlex order; that is, shorter {@code String}s precede longer {@code String}s, and
     * {@code String}s of the same length are ordered lexicographically, matching the order given by the original
     * {@code String}. Does not support removal.
     *
     * <ul>
     *  <li>{@code s} must be non-null.</li>
     *  <li>The result either consists of a single empty {@code String}, or is infinite. It is in shortlex order
     *  (according to some ordering of its characters) and contains every {@code String} with characters drawn from
     *  some sequence.</li>
     * </ul>
     *
     * Length is 1 if {@code s} is empty, infinite otherwise
     *
     * @param s the {@code String} from which characters are selected
     * @return all {@code String}s created from {@code s}
     */
    @Override
    public @NotNull Iterable<String> stringsShortlex(@NotNull String s) {
        return map(IterableUtils::charsToString, listsShortlex(toList(s)));
    }

    /**
     * Returns an {@code Iterable} containing all {@code Lists}s with a minimum size with elements from a given
     * {@code List}. The {@code List}s are in shortlex order; that is, shorter {@code List}s precede longer
     * {@code List}s, and {@code List}s of the same length are ordered lexicographically, matching the order given by
     * the original {@code List}. Does not support removal.
     *
     * <ul>
     *  <li>{@code minSize} cannot be negative.</li>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result either consists of a single empty {@code List}, or is infinite. It is in shortlex order
     *  (according to some ordering of its elements) and contains every {@code List} (with a length greater than or
     *  equal to some minimum) of elements drawn from some sequence.</li>
     * </ul>
     *
     * Length is 0 if {@code xs} is empty and {@code minSize} is greater than 0, 1 if {@code xs} is empty and
     * {@code minSize} is 0, and infinite otherwise
     *
     * @param minSize the minimum length of the result {@code List}s
     * @param xs the {@code List} from which elements are selected
     * @param <T> the type of the given {@code Iterable}'s elements
     * @return all {@code List}s with length at least {@code minSize} created from {@code xs}
     */
    @Override
    public @NotNull <T> Iterable<List<T>> listsShortlexAtLeast(int minSize, @NotNull List<T> xs) {
        if (minSize < 0) {
            throw new IllegalArgumentException("minSize cannot be negative. Invalid minSize: " + minSize);
        }
        if (isEmpty(xs)) return minSize == 0 ?
                Collections.singletonList(Collections.emptyList()) :
                Collections.emptyList();
        return concatMap(i -> listsLex(i.intValueExact(), xs), rangeUp(BigInteger.valueOf(minSize)));
    }

    /**
     * Returns an {@code Iterable} containing all {@code String}s with a minimum size with characters from a given
     * {@code String}. The {@code String}s are in shortlex order; that is, shorter {@code String}s precede longer
     * {@code String}s, and {@code String}s of the same length are ordered lexicographically, matching the order given
     * by the original {@code String}. Does not support removal.
     *
     * <ul>
     *  <li>{@code minSize} cannot be negative.</li>
     *  <li>{@code s} cannot be null.</li>
     *  <li>The result either consists of a single empty {@code String}, or is infinite. It is in shortlex order
     *  (according to some ordering of its characters) and contains every {@code String} (with a length greater than or
     *  equal to some minimum) of characters drawn from some sequence.</li>
     * </ul>
     *
     * Length is 0 if {@code s} is empty and {@code minSize} is greater than 0, 1 if {@code s} is empty and
     * {@code minSize} is 0, and infinite otherwise
     *
     * @param minSize the minimum length of the result {@code String}s
     * @param s the {@code String} from which elements are selected
     * @return all {@code String}s with length at least {@code minSize} created from {@code s}
     */
    @Override
    public @NotNull Iterable<String> stringsShortlexAtLeast(int minSize, @NotNull String s) {
        return map(IterableUtils::charsToString, listsShortlexAtLeast(minSize, toList(s)));
    }

    /**
     * Returns an {@code Iterable} containing all {@code List}s of a given length with elements from a given
     * {@code Iterable}. Does not support removal.
     *
     * <ul>
     *  <li>{@code size} cannot be negative.</li>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>All of the result's elements have the same length. None are empty, unless the result consists entirely of
     *  one empty element.</li>
     * </ul>
     *
     * Length is |{@code xs}|<sup>{@code size}</sup>
     *
     * @param size the length of the result lists
     * @param xs the {@code Iterable} from which elements are selected
     * @param <T> the type of the given {@code Iterable}'s elements
     * @return all lists of a given length created from {@code xs}
     */
    @Override
    public @NotNull <T> Iterable<List<T>> lists(int size, @NotNull Iterable<T> xs) {
        if (size < 0) {
            throw new IllegalArgumentException("size cannot be negative. Invalid size: " + size);
        }
        if (size == 0) return Collections.singletonList(Collections.emptyList());
        if (isEmpty(xs)) return Collections.emptyList();
        Iterable<BigInteger> naturalBigIntegers = naturalBigIntegers();
        return () -> new EventuallyKnownSizeIterator<List<T>>() {
            private final @NotNull CachedIterator<T> cxs = new CachedIterator<>(xs);
            private final @NotNull Iterator<BigInteger> is = naturalBigIntegers.iterator();

            @Override
            public @NotNull List<T> advance() {
                outer:
                while (true) {
                    List<BigInteger> indices = IntegerUtils.demux(size, is.next());
                    List<T> list = new ArrayList<>();
                    for (BigInteger index : indices) {
                        NullableOptional<T> ox = cxs.get(index.intValueExact());
                        if (!ox.isPresent()) continue outer;
                        list.add(ox.get());
                    }
                    if (!outputSizeKnown() && cxs.knownSize().isPresent()) {
                        setOutputSize(BigInteger.valueOf(cxs.knownSize().get()).pow(size));
                    }
                    return list;
                }
            }
        };
    }

    /**
     * Given two {@code Iterable}s, returns all {@code Pair}s of elements from these {@code Iterable}s. Does not
     * support removal.
     *
     * <ul>
     *  <li>{@code as} cannot be null.</li>
     *  <li>{@code bs} cannot be null.</li>
     *  <li>The result is the Cartesian product of two {@code Iterable}s.</li>
     * </ul>
     *
     * Length is |{@code as}||{@code bs}|
     *
     * @param as the first {@code Iterable}
     * @param bs the second {@code Iterable}
     * @param <A> the type of the first {@code Iterable}'s elements
     * @param <B> the type of the second {@code Iterable}'s elements
     * @return all ordered {@code Pair}s of elements from {@code as} and {@code bs}
     */
    @Override
    public @NotNull <A, B> Iterable<Pair<A, B>> pairs(@NotNull Iterable<A> as, @NotNull Iterable<B> bs) {
        return pairsByFunction(
                bi -> {
                    List<BigInteger> list = IntegerUtils.demux(2, bi);
                    return new Pair<>(list.get(0), list.get(1));
                },
                as,
                bs
        );
    }

    /**
     * Returns all {@code Pair}s of elements from an {@code Iterable}. Does not support removal.
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result is the Cartesian square of an {@code Iterable}.</li>
     * </ul>
     *
     * Length is |{@code xs}|<sup>2</sup>
     *
     * @param xs an {@code Iterable}
     * @param <T> the type of the {@code Iterable}'s elements
     * @return all ordered {@code Pair}s of elements from {@code xs}
     */
    @Override
    public @NotNull <T> Iterable<Pair<T, T>> pairs(@NotNull Iterable<T> xs) {
        if (isEmpty(xs)) return Collections.emptyList();
        return map(list -> new Pair<>(list.get(0), list.get(1)), lists(2, xs));
    }

    /**
     * Given three {@code Iterable}s, returns all {@code Triple}s of elements from these {@code Iterable}s. Does not
     * support removal.
     *
     * <ul>
     *  <li>{@code as} cannot be null.</li>
     *  <li>{@code bs} cannot be null.</li>
     *  <li>{@code cs} cannot be null.</li>
     *  <li>The result is the Cartesian product of three {@code Iterable}s.</li>
     * </ul>
     *
     * Length is |{@code as}||{@code bs}||{@code cs}|
     *
     * @param as the first {@code Iterable}
     * @param bs the second {@code Iterable}
     * @param cs the third {@code Iterable}
     * @param <A> the type of the first {@code Iterable}'s elements
     * @param <B> the type of the second {@code Iterable}'s elements
     * @param <C> the type of the third {@code Iterable}'s elements
     * @return all ordered {@code Triple}s of elements from {@code as}, {@code bs}, and {@code cs}
     */
    @Override
    public @NotNull <A, B, C> Iterable<Triple<A, B, C>> triples(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull Iterable<C> cs
    ) {
        if (isEmpty(as) || isEmpty(bs) || isEmpty(cs)) return Collections.emptyList();
        Iterable<BigInteger> naturalBigIntegers = naturalBigIntegers();
        return () -> new EventuallyKnownSizeIterator<Triple<A, B, C>>() {
            private final @NotNull CachedIterator<A> cas = new CachedIterator<>(as);
            private final @NotNull CachedIterator<B> cbs = new CachedIterator<>(bs);
            private final @NotNull CachedIterator<C> ccs = new CachedIterator<>(cs);
            private final @NotNull Iterator<BigInteger> is = naturalBigIntegers.iterator();

            @Override
            public @NotNull Triple<A, B, C> advance() {
                while (true) {
                    List<BigInteger> indices = IntegerUtils.demux(3, is.next());
                    NullableOptional<A> oa = cas.get(indices.get(0).intValueExact());
                    if (!oa.isPresent()) continue;
                    NullableOptional<B> ob = cbs.get(indices.get(1).intValueExact());
                    if (!ob.isPresent()) continue;
                    NullableOptional<C> oc = ccs.get(indices.get(2).intValueExact());
                    if (!oc.isPresent()) continue;
                    if (!outputSizeKnown() &&
                            cas.knownSize().isPresent() &&
                            cbs.knownSize().isPresent() &&
                            ccs.knownSize().isPresent()
                    ) {
                        setOutputSize(
                                BigInteger.valueOf(cas.knownSize().get())
                                        .multiply(BigInteger.valueOf(cbs.knownSize().get()))
                                        .multiply(BigInteger.valueOf(ccs.knownSize().get()))
                        );
                    }
                    return new Triple<>(oa.get(), ob.get(), oc.get());
                }
            }
        };
    }

    /**
     * Returns all {@code Triple}s of elements from an {@code Iterable}. Does not support removal.
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result is the Cartesian cube of an {@code Iterable}.</li>
     * </ul>
     *
     * Length is |{@code xs}|<sup>3</sup>
     *
     * @param xs an {@code Iterable}
     * @param <T> the type of the {@code Iterable}'s elements
     * @return all ordered {@code Triple}s of elements from {@code xs}
     */
    @Override
    public @NotNull <T> Iterable<Triple<T, T, T>> triples(@NotNull Iterable<T> xs) {
        if (isEmpty(xs)) return Collections.emptyList();
        return map(list -> new Triple<>(list.get(0), list.get(1), list.get(2)), lists(3, xs));
    }

    /**
     * Given four {@code Iterable}s, returns all {@code Quadruple}s of elements from these {@code Iterable}s. Does not
     * support removal.
     *
     * <ul>
     *  <li>{@code as} cannot be null.</li>
     *  <li>{@code bs} cannot be null.</li>
     *  <li>{@code cs} cannot be null.</li>
     *  <li>{@code ds} cannot be null.</li>
     *  <li>The result is the Cartesian product of four {@code Iterable}s.</li>
     * </ul>
     *
     * Length is |{@code as}||{@code bs}||{@code cs}||{@code ds}|
     *
     * @param as the first {@code Iterable}
     * @param bs the second {@code Iterable}
     * @param cs the third {@code Iterable}
     * @param ds the fourth {@code Iterable}
     * @param <A> the type of the first {@code Iterable}'s elements
     * @param <B> the type of the second {@code Iterable}'s elements
     * @param <C> the type of the third {@code Iterable}'s elements
     * @param <D> the type of the fourth {@code Iterable}'s elements
     * @return all ordered {@code Quadruple}s of elements from {@code as}, {@code bs}, {@code cs}, and {@code ds}
     */
    @Override
    public @NotNull <A, B, C, D> Iterable<Quadruple<A, B, C, D>> quadruples(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull Iterable<C> cs,
            @NotNull Iterable<D> ds
    ) {
        if (isEmpty(as) || isEmpty(bs) || isEmpty(cs) || isEmpty(ds)) return Collections.emptyList();
        Iterable<BigInteger> naturalBigIntegers = naturalBigIntegers();
        return () -> new EventuallyKnownSizeIterator<Quadruple<A, B, C, D>>() {
            private final @NotNull CachedIterator<A> cas = new CachedIterator<>(as);
            private final @NotNull CachedIterator<B> cbs = new CachedIterator<>(bs);
            private final @NotNull CachedIterator<C> ccs = new CachedIterator<>(cs);
            private final @NotNull CachedIterator<D> cds = new CachedIterator<>(ds);
            private final @NotNull Iterator<BigInteger> is = naturalBigIntegers.iterator();

            @Override
            public @NotNull Quadruple<A, B, C, D> advance() {
                while (true) {
                    List<BigInteger> indices = IntegerUtils.demux(4, is.next());
                    NullableOptional<A> oa = cas.get(indices.get(0).intValueExact());
                    if (!oa.isPresent()) continue;
                    NullableOptional<B> ob = cbs.get(indices.get(1).intValueExact());
                    if (!ob.isPresent()) continue;
                    NullableOptional<C> oc = ccs.get(indices.get(2).intValueExact());
                    if (!oc.isPresent()) continue;
                    NullableOptional<D> od = cds.get(indices.get(3).intValueExact());
                    if (!od.isPresent()) continue;
                    if (!outputSizeKnown() &&
                            cas.knownSize().isPresent() &&
                            cbs.knownSize().isPresent() &&
                            ccs.knownSize().isPresent() &&
                            cds.knownSize().isPresent()
                    ) {
                        setOutputSize(
                                BigInteger.valueOf(cas.knownSize().get())
                                        .multiply(BigInteger.valueOf(cbs.knownSize().get()))
                                        .multiply(BigInteger.valueOf(ccs.knownSize().get()))
                                        .multiply(BigInteger.valueOf(cds.knownSize().get()))
                        );
                    }
                    return new Quadruple<>(oa.get(), ob.get(), oc.get(), od.get());
                }
            }
        };
    }

    /**
     * Returns all {@code Quadruple}s of elements from an {@code Iterable}. Does not support removal.
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result is the Cartesian fourth power of an {@code Iterable}.</li>
     * </ul>
     *
     * Length is |{@code xs}|<sup>4</sup>
     *
     * @param xs an {@code Iterable}
     * @param <T> the type of the {@code Iterable}'s elements
     * @return all ordered {@code Quadruple}s of elements from {@code xs}
     */
    @Override
    public @NotNull <T> Iterable<Quadruple<T, T, T, T>> quadruples(@NotNull Iterable<T> xs) {
        if (isEmpty(xs)) return Collections.emptyList();
        return map(list -> new Quadruple<>(list.get(0), list.get(1), list.get(2), list.get(3)), lists(4, xs));
    }

    /**
     * Given five {@code Iterable}s, returns all {@code Quintuple}s of elements from these {@code Iterable}s. Does not
     * support removal.
     *
     * <ul>
     *  <li>{@code as} cannot be null.</li>
     *  <li>{@code bs} cannot be null.</li>
     *  <li>{@code cs} cannot be null.</li>
     *  <li>{@code ds} cannot be null.</li>
     *  <li>{@code es} cannot be null.</li>
     *  <li>The result is the Cartesian product of five {@code Iterable}s.</li>
     * </ul>
     *
     * Length is |{@code as}||{@code bs}||{@code cs}||{@code ds}||{@code es}|
     *
     * @param as the first {@code Iterable}
     * @param bs the second {@code Iterable}
     * @param cs the third {@code Iterable}
     * @param ds the fourth {@code Iterable}
     * @param es the fifth {@code Iterable}
     * @param <A> the type of the first {@code Iterable}'s elements
     * @param <B> the type of the second {@code Iterable}'s elements
     * @param <C> the type of the third {@code Iterable}'s elements
     * @param <D> the type of the fourth {@code Iterable}'s elements
     * @param <E> the type of the fifth {@code Iterable}'s elements
     * @return all ordered {@code Quintuple}s of elements from {@code as}, {@code bs}, {@code cs}, {@code ds}, and
     * {@code es}
     */
    @Override
    public @NotNull <A, B, C, D, E> Iterable<Quintuple<A, B, C, D, E>> quintuples(
            @NotNull Iterable<A> as,
            @NotNull Iterable<B> bs,
            @NotNull Iterable<C> cs,
            @NotNull Iterable<D> ds,
            @NotNull Iterable<E> es
    ) {
        if (isEmpty(as) || isEmpty(bs) || isEmpty(cs) || isEmpty(ds) || isEmpty(es)) return Collections.emptyList();
        Iterable<BigInteger> naturalBigIntegers = naturalBigIntegers();
        return () -> new EventuallyKnownSizeIterator<Quintuple<A, B, C, D, E>>() {
            private final @NotNull CachedIterator<A> cas = new CachedIterator<>(as);
            private final @NotNull CachedIterator<B> cbs = new CachedIterator<>(bs);
            private final @NotNull CachedIterator<C> ccs = new CachedIterator<>(cs);
            private final @NotNull CachedIterator<D> cds = new CachedIterator<>(ds);
            private final @NotNull CachedIterator<E> ces = new CachedIterator<>(es);
            private final @NotNull Iterator<BigInteger> is = naturalBigIntegers.iterator();

            @Override
            public @NotNull Quintuple<A, B, C, D, E> advance() {
                while (true) {
                    List<BigInteger> indices = IntegerUtils.demux(5, is.next());
                    NullableOptional<A> oa = cas.get(indices.get(0).intValueExact());
                    if (!oa.isPresent()) continue;
                    NullableOptional<B> ob = cbs.get(indices.get(1).intValueExact());
                    if (!ob.isPresent()) continue;
                    NullableOptional<C> oc = ccs.get(indices.get(2).intValueExact());
                    if (!oc.isPresent()) continue;
                    NullableOptional<D> od = cds.get(indices.get(3).intValueExact());
                    if (!od.isPresent()) continue;
                    NullableOptional<E> oe = ces.get(indices.get(4).intValueExact());
                    if (!oe.isPresent()) continue;
                    if (!outputSizeKnown() &&
                            cas.knownSize().isPresent() &&
                            cbs.knownSize().isPresent() &&
                            ccs.knownSize().isPresent() &&
                            cds.knownSize().isPresent() &&
                            ces.knownSize().isPresent()
                    ) {
                        setOutputSize(
                                BigInteger.valueOf(cas.knownSize().get())
                                        .multiply(BigInteger.valueOf(cbs.knownSize().get()))
                                        .multiply(BigInteger.valueOf(ccs.knownSize().get()))
                                        .multiply(BigInteger.valueOf(cds.knownSize().get()))
                                        .multiply(BigInteger.valueOf(ces.knownSize().get()))
                        );
                    }
                    return new Quintuple<>(oa.get(), ob.get(), oc.get(), od.get(), oe.get());
                }
            }
        };
    }

    /**
     * Returns all {@code Quintuple}s of elements from an {@code Iterable}. Does not support removal.
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result is the Cartesian fifth power of an {@code Iterable}.</li>
     * </ul>
     *
     * Length is |{@code xs}|<sup>5</sup>
     *
     * @param xs an {@code Iterable}
     * @param <T> the type of the {@code Iterable}'s elements
     * @return all ordered {@code Quintuple}s of elements from {@code xs}
     */
    @Override
    public @NotNull <T> Iterable<Quintuple<T, T, T, T, T>> quintuples(@NotNull Iterable<T> xs) {
        if (isEmpty(xs)) return Collections.emptyList();
        return map(
                list -> new Quintuple<>(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4)),
                lists(5, xs)
        );
    }

    /**
     * Given six {@code Iterable}s, returns all {@code Sextuple}s of elements from these {@code Iterable}s. Does not
     * support removal.
     *
     * <ul>
     *  <li>{@code as} cannot be null.</li>
     *  <li>{@code bs} cannot be null.</li>
     *  <li>{@code cs} cannot be null.</li>
     *  <li>{@code ds} cannot be null.</li>
     *  <li>{@code es} cannot be null.</li>
     *  <li>{@code fs} cannot be null.</li>
     *  <li>The result is the Cartesian product of six {@code Iterable}s.</li>
     * </ul>
     *
     * Length is |{@code as}||{@code bs}||{@code cs}||{@code ds}||{@code es}||{@code fs}|
     *
     * @param as the first {@code Iterable}
     * @param bs the second {@code Iterable}
     * @param cs the third {@code Iterable}
     * @param ds the fourth {@code Iterable}
     * @param es the fifth {@code Iterable}
     * @param fs the sixth {@code Iterable}
     * @param <A> the type of the first {@code Iterable}'s elements
     * @param <B> the type of the second {@code Iterable}'s elements
     * @param <C> the type of the third {@code Iterable}'s elements
     * @param <D> the type of the fourth {@code Iterable}'s elements
     * @param <E> the type of the fifth {@code Iterable}'s elements
     * @param <F> the type of the sixth {@code Iterable}'s elements
     * @return all ordered {@code Sextuple}s of elements from {@code as}, {@code bs}, {@code cs}, {@code ds},
     * {@code es}, and {@code fs}
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
        if (isEmpty(as) || isEmpty(bs) || isEmpty(cs) || isEmpty(ds) || isEmpty(es) || isEmpty(fs))
                return Collections.emptyList();
        Iterable<BigInteger> naturalBigIntegers = naturalBigIntegers();
        return () -> new EventuallyKnownSizeIterator<Sextuple<A, B, C, D, E, F>>() {
            private final @NotNull CachedIterator<A> cas = new CachedIterator<>(as);
            private final @NotNull CachedIterator<B> cbs = new CachedIterator<>(bs);
            private final @NotNull CachedIterator<C> ccs = new CachedIterator<>(cs);
            private final @NotNull CachedIterator<D> cds = new CachedIterator<>(ds);
            private final @NotNull CachedIterator<E> ces = new CachedIterator<>(es);
            private final @NotNull CachedIterator<F> cfs = new CachedIterator<>(fs);
            private final @NotNull Iterator<BigInteger> is = naturalBigIntegers.iterator();

            @Override
            public @NotNull Sextuple<A, B, C, D, E, F> advance() {
                while (true) {
                    List<BigInteger> indices = IntegerUtils.demux(6, is.next());
                    NullableOptional<A> oa = cas.get(indices.get(0).intValueExact());
                    if (!oa.isPresent()) continue;
                    NullableOptional<B> ob = cbs.get(indices.get(1).intValueExact());
                    if (!ob.isPresent()) continue;
                    NullableOptional<C> oc = ccs.get(indices.get(2).intValueExact());
                    if (!oc.isPresent()) continue;
                    NullableOptional<D> od = cds.get(indices.get(3).intValueExact());
                    if (!od.isPresent()) continue;
                    NullableOptional<E> oe = ces.get(indices.get(4).intValueExact());
                    if (!oe.isPresent()) continue;
                    NullableOptional<F> of = cfs.get(indices.get(5).intValueExact());
                    if (!of.isPresent()) continue;
                    if (!outputSizeKnown() &&
                            cas.knownSize().isPresent() &&
                            cbs.knownSize().isPresent() &&
                            ccs.knownSize().isPresent() &&
                            cds.knownSize().isPresent() &&
                            ces.knownSize().isPresent() &&
                            cfs.knownSize().isPresent()
                    ) {
                        setOutputSize(
                                BigInteger.valueOf(cas.knownSize().get())
                                        .multiply(BigInteger.valueOf(cbs.knownSize().get()))
                                        .multiply(BigInteger.valueOf(ccs.knownSize().get()))
                                        .multiply(BigInteger.valueOf(cds.knownSize().get()))
                                        .multiply(BigInteger.valueOf(ces.knownSize().get()))
                                        .multiply(BigInteger.valueOf(cfs.knownSize().get()))
                        );
                    }
                    return new Sextuple<>(oa.get(), ob.get(), oc.get(), od.get(), oe.get(), of.get());
                }
            }
        };
    }

    /**
     * Returns all {@code Sextuple}s of elements from an {@code Iterable}. Does not support removal.
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result is the Cartesian sixth power of an {@code Iterable}.</li>
     * </ul>
     *
     * Length is |{@code xs}|<sup>6</sup>
     *
     * @param xs an {@code Iterable}
     * @param <T> the type of the {@code Iterable}'s elements
     * @return all ordered {@code Sextuple}s of elements from {@code xs}
     */
    @Override
    public @NotNull <T> Iterable<Sextuple<T, T, T, T, T, T>> sextuples(@NotNull Iterable<T> xs) {
        if (isEmpty(xs)) return Collections.emptyList();
        return map(
                list -> new Sextuple<>(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4), list.get(5)),
                lists(6, xs)
        );
    }

    /**
     * Given seven {@code Iterable}s, returns all {@code Septuple}s of elements from these {@code Iterable}s. Does not
     * support removal.
     *
     * <ul>
     *  <li>{@code as} cannot be null.</li>
     *  <li>{@code bs} cannot be null.</li>
     *  <li>{@code cs} cannot be null.</li>
     *  <li>{@code ds} cannot be null.</li>
     *  <li>{@code es} cannot be null.</li>
     *  <li>{@code fs} cannot be null.</li>
     *  <li>{@code gs} cannot be null.</li>
     *  <li>The result is the Cartesian product of seven {@code Iterable}s.</li>
     * </ul>
     *
     * Length is |{@code as}||{@code bs}||{@code cs}||{@code ds}||{@code es}||{@code fs}||{@code gs}|
     *
     * @param as the first {@code Iterable}
     * @param bs the second {@code Iterable}
     * @param cs the third {@code Iterable}
     * @param ds the fourth {@code Iterable}
     * @param es the fifth {@code Iterable}
     * @param fs the sixth {@code Iterable}
     * @param gs the seventh {@code Iterable}
     * @param <A> the type of the first {@code Iterable}'s elements
     * @param <B> the type of the second {@code Iterable}'s elements
     * @param <C> the type of the third {@code Iterable}'s elements
     * @param <D> the type of the fourth {@code Iterable}'s elements
     * @param <E> the type of the fifth {@code Iterable}'s elements
     * @param <F> the type of the sixth {@code Iterable}'s elements
     * @param <G> the type of the seventh {@code Iterable}'s elements
     * @return all ordered {@code Septuple}s of elements from {@code as}, {@code bs}, {@code cs}, {@code ds},
     * {@code es}, {@code fs}, and {@code gs}
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
        if (isEmpty(as) || isEmpty(bs) || isEmpty(cs) || isEmpty(ds) || isEmpty(es) || isEmpty(fs) || isEmpty(gs))
                return Collections.emptyList();
        Iterable<BigInteger> naturalBigIntegers = naturalBigIntegers();
        return () -> new EventuallyKnownSizeIterator<Septuple<A, B, C, D, E, F, G>>() {
            private final @NotNull CachedIterator<A> cas = new CachedIterator<>(as);
            private final @NotNull CachedIterator<B> cbs = new CachedIterator<>(bs);
            private final @NotNull CachedIterator<C> ccs = new CachedIterator<>(cs);
            private final @NotNull CachedIterator<D> cds = new CachedIterator<>(ds);
            private final @NotNull CachedIterator<E> ces = new CachedIterator<>(es);
            private final @NotNull CachedIterator<F> cfs = new CachedIterator<>(fs);
            private final @NotNull CachedIterator<G> cgs = new CachedIterator<>(gs);
            private final @NotNull Iterator<BigInteger> is = naturalBigIntegers.iterator();

            @Override
            public @NotNull Septuple<A, B, C, D, E, F, G> advance() {
                while (true) {
                    List<BigInteger> indices = IntegerUtils.demux(7, is.next());
                    NullableOptional<A> oa = cas.get(indices.get(0).intValueExact());
                    if (!oa.isPresent()) continue;
                    NullableOptional<B> ob = cbs.get(indices.get(1).intValueExact());
                    if (!ob.isPresent()) continue;
                    NullableOptional<C> oc = ccs.get(indices.get(2).intValueExact());
                    if (!oc.isPresent()) continue;
                    NullableOptional<D> od = cds.get(indices.get(3).intValueExact());
                    if (!od.isPresent()) continue;
                    NullableOptional<E> oe = ces.get(indices.get(4).intValueExact());
                    if (!oe.isPresent()) continue;
                    NullableOptional<F> of = cfs.get(indices.get(5).intValueExact());
                    if (!of.isPresent()) continue;
                    NullableOptional<G> og = cgs.get(indices.get(6).intValueExact());
                    if (!og.isPresent()) continue;
                    if (!outputSizeKnown() &&
                            cas.knownSize().isPresent() &&
                            cbs.knownSize().isPresent() &&
                            ccs.knownSize().isPresent() &&
                            cds.knownSize().isPresent() &&
                            ces.knownSize().isPresent() &&
                            cfs.knownSize().isPresent() &&
                            cgs.knownSize().isPresent()
                    ) {
                        setOutputSize(
                                BigInteger.valueOf(cas.knownSize().get())
                                .multiply(BigInteger.valueOf(cbs.knownSize().get()))
                                .multiply(BigInteger.valueOf(ccs.knownSize().get()))
                                .multiply(BigInteger.valueOf(cds.knownSize().get()))
                                .multiply(BigInteger.valueOf(ces.knownSize().get()))
                                .multiply(BigInteger.valueOf(cfs.knownSize().get()))
                                .multiply(BigInteger.valueOf(cgs.knownSize().get()))
                        );
                    }
                    return new Septuple<>(oa.get(), ob.get(), oc.get(), od.get(), oe.get(), of.get(), og.get());
                }
            }
        };
    }

    /**
     * Returns all {@code Septuple}s of elements from an {@code Iterable}. Does not support removal.
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result is the Cartesian seventh power of an {@code Iterable}.</li>
     * </ul>
     *
     * Length is |{@code xs}|<sup>7</sup>
     *
     * @param xs an {@code Iterable}
     * @param <T> the type of the {@code Iterable}'s elements
     * @return all ordered {@code Septuple}s of elements from {@code xs}
     */
    @Override
    public @NotNull <T> Iterable<Septuple<T, T, T, T, T, T, T>> septuples(@NotNull Iterable<T> xs) {
        if (isEmpty(xs)) return Collections.emptyList();
        return map(
                list -> new Septuple<>(
                        list.get(0),
                        list.get(1),
                        list.get(2),
                        list.get(3),
                        list.get(4),
                        list.get(5),
                        list.get(6)
                ),
                lists(7, xs)
        );
    }

    /**
     * Returns an {@code Iterable} containing all {@code Lists}s with elements from a given {@code List}. Does not
     * support removal.
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result either consists of a single empty {@code List}, or is infinite. It contains every {@code List}
     *  of elements drawn from some sequence.</li>
     * </ul>
     *
     * Length is 1 if {@code xs} is empty, infinite otherwise
     *
     * @param xs the {@code List} from which elements are selected
     * @param <T> the type of the given {@code List}'s elements
     * @return all {@code List}s created from {@code xs}
     */
    @Override
    public @NotNull <T> Iterable<List<T>> lists(@NotNull Iterable<T> xs) {
        if (isEmpty(xs)) return Collections.singletonList(Collections.emptyList());
        if (!lengthAtLeast(2, xs)) {
            T x = head(xs);
            return iterate(ys -> toList(cons(x, ys)), Collections.emptyList());
        }
        return cons(
                Collections.emptyList(),
                optionalMapInfinite(
                        p -> p.b,
                        dependentPairsInfiniteLogarithmicOrder(
                                positiveBigIntegers(),
                                i -> concat(nonEmptyOptionals(lists(i.intValueExact(), xs)), repeat(Optional.empty()))
                        )
                )
        );
    }

    /**
     * Returns an {@code Iterable} containing all {@code Lists}s with a minimum size with elements from a given
     * {@code List}. Does not support removal.
     *
     * <ul>
     *  <li>{@code minSize} cannot be negative.</li>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result either consists of a single empty {@code List}, or is infinite. It contains every {@code List}
     *  (with a length greater than or equal to some minimum) of elements drawn from some sequence.</li>
     * </ul>
     *
     * Length is 0 if {@code xs} is empty and {@code minSize} is greater than 0, 1 if {@code xs} is empty and
     * {@code minSize} is 0, and infinite otherwise
     *
     * @param minSize the minimum length of the result {@code List}s
     * @param xs the {@code List} from which elements are selected
     * @param <T> the type of the given {@code Iterable}'s elements
     * @return all {@code List}s with length at least {@code minSize} created from {@code xs}
     */
    @Override
    public @NotNull <T> Iterable<List<T>> listsAtLeast(int minSize, @NotNull Iterable<T> xs) {
        if (minSize < 0) {
            throw new IllegalArgumentException("minSize cannot be negative. Invalid minSize: " + minSize);
        }
        if (minSize == 0) return lists(xs);
        if (isEmpty(xs)) return Collections.emptyList();
        if (!lengthAtLeast(2, xs)) {
            T x = head(xs);
            return iterate(ys -> toList(cons(x, ys)), toList(replicate(minSize, x)));
        }
        return optionalMapInfinite(
                p -> p.b,
                dependentPairsInfiniteLogarithmicOrder(
                        rangeUp(BigInteger.valueOf(minSize)),
                        i -> concat(nonEmptyOptionals(lists(i.intValueExact(), xs)), repeat(Optional.empty()))
                )
        );
    }

    /**
     * Returns all {@code List}s of a given size containing natural numbers up to a given value with no repetitions.
     * Does not support removal.
     *
     * <ul>
     *  <li>{@code size} cannot be negative.</li>
     *  <li>{@code elementCount} cannot be negative.</li>
     *  <li>The result is in lexicographic order, contains only non-negative integers with no repetitions, and contains
     *  no repetitions. Each element has the same size.</li>
     * </ul>
     *
     * Length is <sub>{@code elementCount}</sub>P<sub>{@code size}</sub>
     *
     * @param size the length of each of the result {@code List}s
     * @param elementCount one more than the largest possible value in the result {@code List}s
     * @return all lists with no repetitions of length {@code size} with elements from 0 to {@code elementCount}–1
     */
    private static @NotNull Iterable<List<Integer>> distinctListIndices(int size, int elementCount) {
        BigInteger outputSize = MathUtils.fallingFactorial(BigInteger.valueOf(elementCount), size);
        Iterable<Integer> range = IterableUtils.range(0, size - 1);
        return () -> new EventuallyKnownSizeIterator<List<Integer>>() {
            private final @NotNull List<Integer> list = toList(range);
            private final @NotNull boolean[] taken = new boolean[elementCount];
            private boolean first = true;
            {
                list.stream().filter(i -> i < elementCount).forEach(i -> taken[i] = true);
                setOutputSize(outputSize);
            }

            @Override
            public @NotNull List<Integer> advance() {
                if (first) {
                    first = false;
                    return list;
                }
                for (int i = size - 1; i >= 0; i--) {
                    int index = list.get(i);
                    for (int j = index + 1; j < elementCount; j++) {
                        if (taken[j]) continue;
                        list.set(i, j);
                        taken[index] = false;
                        taken[j] = true;
                        int k = i + 1;
                        for (int m = 0; m < elementCount && k < size; m++) {
                            if (taken[m]) continue;
                            list.set(k, m);
                            taken[m] = true;
                            k++;
                        }
                        return list;
                    }
                    taken[index] = false;
                }
                throw new IllegalStateException("unreachable");
            }
        };
    }

    /**
     * Returns an {@code Iterable} containing all {@code List}s of a given length with elements from a given
     * {@code List}, with no repetitions. The {@code List}s are ordered lexicographically, matching the order given by
     * the original {@code List}. Does not support removal.
     *
     * <ul>
     *  <li>{@code size} cannot be negative.</li>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result is finite. All of its elements have the same length. None are empty, unless the result consists
     *  entirely of one empty element.</li>
     * </ul>
     *
     * Length is <sub>|{@code xs}|</sub>P<sub>{@code size}</sub>
     *
     * @param size the length of the result lists
     * @param xs the {@code List} from which elements are selected
     * @param <T> the type of the given {@code List}'s elements
     * @return all {@code List}s of a given length created from {@code xs} with no repetitions
     */
    @Override
    public @NotNull <T> Iterable<List<T>> distinctListsLex(int size, @NotNull List<T> xs) {
        if (size < 0) {
            throw new IllegalArgumentException("size cannot be negative. Invalid size: " + size);
        }
        return map(is -> toList(map(xs::get, is)), distinctListIndices(size, xs.size()));
    }

    /**
     * Returns all {@code Pair}s of distinct elements from an {@code Iterable}. The {@code Pair}s are ordered
     * lexicographically, matching the order given by the original {@code Iterable}s. Does not support removal.
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result contains all distinct {@code Pair}s of elements from an {@code Iterable}.</li>
     * </ul>
     *
     * Length is <sub>|{@code xs}|</sub>P<sub>2</sub>
     *
     * @param xs an {@code Iterable}
     * @param <T> the type of the {@code Iterable}'s elements
     * @return all distinct ordered {@code Pair}s of elements from {@code xs}
     */
    @Override
    public @NotNull <T> Iterable<Pair<T, T>> distinctPairsLex(@NotNull List<T> xs) {
        return map(list -> new Pair<>(list.get(0), list.get(1)), distinctListsLex(2, xs));
    }

    /**
     * Returns all {@code Triple}s of distinct elements from an {@code Iterable}. The {@code Triple}s are ordered
     * lexicographically, matching the order given by the original {@code Iterable}s. Does not support removal.
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result contains all distinct {@code Triple}s of elements from an {@code Iterable}.</li>
     * </ul>
     *
     * Length is <sub>|{@code xs}|</sub>P<sub>3</sub>
     *
     * @param xs an {@code Iterable}
     * @param <T> the type of the {@code Iterable}'s elements
     * @return all distinct ordered {@code Triple}s of elements from {@code xs}
     */
    @Override
    public @NotNull <T> Iterable<Triple<T, T, T>> distinctTriplesLex(@NotNull List<T> xs) {
        return map(list -> new Triple<>(list.get(0), list.get(1), list.get(2)), distinctListsLex(3, xs));
    }

    /**
     * Returns all {@code Quadruple}s of distinct elements from an {@code Iterable}. The {@code Quadruple}s are ordered
     * lexicographically, matching the order given by the original {@code Iterable}s. Does not support removal.
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result contains all distinct {@code Quadruple}s of elements from an {@code Iterable}.</li>
     * </ul>
     *
     * Length is <sub>|{@code xs}|</sub>P<sub>4</sub>
     *
     * @param xs an {@code Iterable}
     * @param <T> the type of the {@code Iterable}'s elements
     * @return all distinct ordered {@code Quadruple}s of elements from {@code xs}
     */
    @Override
    public @NotNull <T> Iterable<Quadruple<T, T, T, T>> distinctQuadruplesLex(@NotNull List<T> xs) {
        return map(
                list -> new Quadruple<>(list.get(0), list.get(1), list.get(2), list.get(3)),
                distinctListsLex(4, xs)
        );
    }

    /**
     * Returns all {@code Quintuple}s of distinct elements from an {@code Iterable}. The {@code Quintuple}s are ordered
     * lexicographically, matching the order given by the original {@code Iterable}s. Does not support removal.
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result contains all distinct {@code Quintuple}s of elements from an {@code Iterable}.</li>
     * </ul>
     *
     * Length is <sub>|{@code xs}|</sub>P<sub>5</sub>
     *
     * @param xs an {@code Iterable}
     * @param <T> the type of the {@code Iterable}'s elements
     * @return all distinct ordered {@code Quintuple}s of elements from {@code xs}
     */
    @Override
    public @NotNull <T> Iterable<Quintuple<T, T, T, T, T>> distinctQuintuplesLex(@NotNull List<T> xs) {
        return map(
                list -> new Quintuple<>(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4)),
                distinctListsLex(5, xs)
        );
    }

    /**
     * Returns all {@code Sextuple}s of distinct elements from an {@code Iterable}. The {@code Sextuple}s are ordered
     * lexicographically, matching the order given by the original {@code Iterable}s. Does not support removal.
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result contains all distinct {@code Sextuple}s of elements from an {@code Iterable}.</li>
     * </ul>
     *
     * Length is <sub>|{@code xs}|</sub>P<sub>6</sub>
     *
     * @param xs an {@code Iterable}
     * @param <T> the type of the {@code Iterable}'s elements
     * @return all distinct ordered {@code Sextuple}s of elements from {@code xs}
     */
    @Override
    public @NotNull <T> Iterable<Sextuple<T, T, T, T, T, T>> distinctSextuplesLex(@NotNull List<T> xs) {
        return map(
                list -> new Sextuple<>(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4), list.get(5)),
                distinctListsLex(6, xs)
        );
    }

    /**
     * Returns all {@code Septuple}s of distinct elements from an {@code Iterable}. The {@code Septuple}s are ordered
     * lexicographically, matching the order given by the original {@code Iterable}s. Does not support removal.
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result contains all distinct {@code Septuple}s of elements from an {@code Iterable}.</li>
     * </ul>
     *
     * Length is <sub>|{@code xs}|</sub>P<sub>6</sub>
     *
     * @param xs an {@code Iterable}
     * @param <T> the type of the {@code Iterable}'s elements
     * @return all distinct ordered {@code Septuple}s of elements from {@code xs}
     */
    @Override
    public @NotNull <T> Iterable<Septuple<T, T, T, T, T, T, T>> distinctSeptuplesLex(@NotNull List<T> xs) {
        return map(
                list -> new Septuple<>(
                        list.get(0),
                        list.get(1),
                        list.get(2),
                        list.get(3),
                        list.get(4),
                        list.get(5),
                        list.get(6)
                ),
                distinctListsLex(7, xs)
        );
    }

    /**
     * Returns an {@code Iterable} containing all {@code String}s of a given length with characters from a given
     * {@code String}, with no repetitions. The {@code String}s are ordered lexicographically, matching the order given
     * by the original {@code String}. Does not support removal.
     *
     * <ul>
     *  <li>{@code size} cannot be negative.</li>
     *  <li>{@code s} cannot be null.</li>
     *  <li>The result is finite. All of its {@code String}s have the same length. None are empty, unless the result
     *  consists entirely of one empty {@code String}.</li>
     * </ul>
     *
     * Length is <sub>|{@code s}|</sub>P<sub>{@code size}</sub>
     *
     * @param size the length of the result {@code String}
     * @param s the {@code String} from which characters are selected
     * @return all {@code String}s of a given length created from {@code s} with no repetitions
     */
    @Override
    public @NotNull Iterable<String> distinctStringsLex(int size, @NotNull String s) {
        return map(IterableUtils::charsToString, distinctListsLex(size, toList(s)));
    }

    /**
     * Returns all {@code List}s of natural numbers up to a given value with no repetitions. Does not support removal.
     *
     * <ul>
     *  <li>{@code elementCount} cannot be negative.</li>
     *  <li>The result is in lexicographic order, contains only non-negative integers with no repetitions, and contains
     *  no repetitions.</li>
     * </ul>
     *
     * Length is Σ<sub>i=0</sub><sup>n</sup><sub>{@code elementCount}</sub>P<sub>{@code size}</sub>
     *
     * @param elementCount one more than the largest possible value in the result {@code List}s
     * @return all lists with no repetitions with elements from 0 to {@code elementCount}–1
     */
    private static @NotNull Iterable<List<Integer>> distinctListIndices(int elementCount) {
        BigInteger outputSize = MathUtils.numberOfArrangementsOfASet(elementCount);
        return () -> new EventuallyKnownSizeIterator<List<Integer>>() {
            private final @NotNull List<Integer> list = new ArrayList<>();
            private final @NotNull boolean[] taken = new boolean[elementCount];
            private boolean first = true;
            {
                list.stream().filter(i -> i < elementCount).forEach(i -> taken[i] = true);
                setOutputSize(outputSize);
            }

            @Override
            public @NotNull List<Integer> advance() {
                if (first) {
                    first = false;
                    return list;
                } else if (list.size() < elementCount) {
                    for (int i = 0; i < elementCount; i++) {
                        if (!taken[i]) {
                            list.add(i);
                            taken[i] = true;
                            return list;
                        }
                    }
                } else {
                    int previous = list.get(elementCount - 1);
                    for (int i = elementCount - 2; i >= 0; i--) {
                        list.remove(i + 1);
                        taken[previous] = false;
                        int j = list.get(i);
                        if (j < previous) {
                            for (int k = j + 1; k < elementCount; k++) {
                                if (!taken[k]) {
                                    list.set(i, k);
                                    taken[j] = false;
                                    taken[k] = true;
                                    return list;
                                }
                            }
                        }
                        previous = j;
                    }
                }
                throw new IllegalStateException("unreachable");
            }
        };
    }

    /**
     * Returns an {@code Iterable} containing all {@code Lists}s with elements from a given {@code List} with no
     * repetitions. The {@code List}s are ordered lexicographically, matching the order given by the original
     * {@code List}. Does not support removal.
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result is not null.</li>
     * </ul>
     *
     * Length is Σ<sub>i=0</sub><sup>n</sup><sub>|{@code xs}|</sub>P<sub>{@code i}</sub>
     *
     * @param xs the {@code List} from which elements are selected
     * @param <T> the type of the given {@code List}'s elements
     * @return all {@code List}s with no repetitions created from {@code xs}
     */
    @Override
    public @NotNull <T> Iterable<List<T>> distinctListsLex(@NotNull List<T> xs) {
        return map(is -> toList(map(xs::get, is)), distinctListIndices(xs.size()));
    }

    /**
     * Returns an {@code Iterable} containing all {@code String}s with characters from a given {@code String} with no
     * repetitions. The {@code String}s are ordered lexicographically, matching the order given by the original
     * {@code String}. Does not support removal.
     *
     * <ul>
     *  <li>{@code s} cannot be null.</li>
     *  <li>The result is not null.</li>
     * </ul>
     *
     * Length is Σ<sub>i=0</sub><sup>n</sup><sub>|{@code s}|</sub>P<sub>{@code i}</sub>
     *
     * @param s the {@code String} from which elements are selected
     * @return all {@code String}s with no repetitions created from {@code s}
     */
    @Override
    public @NotNull Iterable<String> distinctStringsLex(@NotNull String s) {
        return map(IterableUtils::charsToString, distinctListsLex(toList(s)));
    }

    /**
     * Returns all {@code List}s of at least a given size containing natural numbers up to a given value with no
     * repetitions. Does not support removal.
     *
     * <ul>
     *  <li>{@code minSize} cannot be negative.</li>
     *  <li>{@code elementCount} cannot be negative.</li>
     *  <li>The result is in lexicographic order, contains only non-negative integers with no repetitions, and contains
     *  no repetitions.</li>
     * </ul>
     *
     * Length is Σ<sub>i={@code minSize}</sub><sup>n</sup><sub>{@code elementCount}</sub>P<sub>{@code i}</sub>
     *
     * @param minSize the minimum length of each of the result {@code List}s
     * @param elementCount one more than the largest possible value in the result {@code List}s
     * @return all lists with no repetitions of length at least {@code minSize} with elements from 0 to
     * {@code elementCount}–1
     */
    private static @NotNull Iterable<List<Integer>> distinctListIndicesAtLeast(int minSize, int elementCount) {
        BigInteger outputSize = MathUtils.numberOfArrangementsOfASet(minSize, elementCount);
        Iterable<Integer> range = IterableUtils.range(0, minSize - 1);
        return () -> new EventuallyKnownSizeIterator<List<Integer>>() {
            private final @NotNull List<Integer> list = toList(range);
            private final @NotNull boolean[] taken = new boolean[elementCount];
            private boolean first = true;
            {
                list.stream().filter(i -> i < elementCount).forEach(i -> taken[i] = true);
                setOutputSize(outputSize);
            }

            @Override
            public @NotNull List<Integer> advance() {
                if (first) {
                    first = false;
                    return list;
                } else if (list.size() < elementCount) {
                    for (int i = 0; i < elementCount; i++) {
                        if (!taken[i]) {
                            list.add(i);
                            taken[i] = true;
                            return list;
                        }
                    }
                } else {
                    int previous = list.get(elementCount - 1);
                    outer:
                    for (int i = elementCount - 2; i >= 0; i--) {
                        list.remove(i + 1);
                        taken[previous] = false;
                        int j = list.get(i);
                        if (j < previous) {
                            for (int k = j + 1; k < elementCount; k++) {
                                if (!taken[k]) {
                                    list.set(i, k);
                                    taken[j] = false;
                                    taken[k] = true;
                                    break outer;
                                }
                            }
                        }
                        previous = j;
                    }
                    int size = list.size();
                    for (int i = 0; size < minSize && i < elementCount; i++) {
                        if (!taken[i]) {
                            list.add(i);
                            taken[i] = true;
                            size++;
                        }
                    }
                    return list;
                }
                throw new IllegalStateException("unreachable");
            }
        };
    }

    /**
     * Returns an {@code Iterable} containing all {@code Lists}s with a minimum size with elements from a given
     * {@code List} with no repetitions. The {@code List}s are ordered lexicographically, matching the order given by
     * the original {@code List}. Does not support removal.
     *
     * <ul>
     *  <li>{@code minSize} cannot be negative.</li>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result contains every {@code List} (with a length greater than or equal to some minimum) of elements
     *  drawn from some sequence with no repetitions.</li>
     * </ul>
     *
     * Length is Σ<sub>i={@code minSize}</sub><sup>n</sup><sub>|{@code xs}|</sub>P<sub>{@code i}</sub>
     *
     * @param minSize the minimum length of the result {@code List}s
     * @param xs the {@code List} from which elements are selected
     * @param <T> the type of the given {@code Iterable}'s elements
     * @return all {@code List}s with length at least {@code minSize} with no repetitions created from {@code xs}
     */
    @Override
    public @NotNull <T> Iterable<List<T>> distinctListsLexAtLeast(int minSize, @NotNull List<T> xs) {
        if (minSize < 0) {
            throw new IllegalArgumentException("minSize cannot be negative. Invalid minSize: " + minSize);
        }
        return map(is -> toList(map(xs::get, is)), distinctListIndicesAtLeast(minSize, xs.size()));
    }

    /**
     * Returns an {@code Iterable} containing all {@code String}s with a minimum size with elements from a given
     * {@code String} with no repetitions. The {@code String}s are ordered lexicographically, matching the order given
     * by the original {@code String}. Does not support removal.
     *
     * <ul>
     *  <li>{@code minSize} cannot be negative.</li>
     *  <li>{@code s} cannot be null.</li>
     *  <li>The result contains every {@code String} (with a length greater than or equal to some minimum) of
     *  characters drawn from some sequence with no repetitions.</li>
     * </ul>
     *
     * Length is Σ<sub>i={@code minSize}</sub><sup>n</sup><sub>|{@code s}|</sub>P<sub>{@code i}</sub>
     *
     * @param minSize the minimum length of the result {@code String}s
     * @param s the {@code String} from which elements are selected
     * @return all {@code String}s with length at least {@code minSize} with no repetitions created from {@code s}
     */
    @Override
    public @NotNull Iterable<String> distinctStringsLexAtLeast(int minSize, @NotNull String s) {
        return map(IterableUtils::charsToString, distinctListsLexAtLeast(minSize, toList(s)));
    }

    /**
     * Returns an {@code Iterable} containing all {@code Lists}s with elements from a given {@code List} with no
     * repetitions. The {@code List}s are in shortlex order; that is, shorter {@code List}s precede longer
     * {@code List}s, and {@code List}s of the same length are ordered lexicographically, matching the order given by
     * the original {@code List}. Does not support removal.
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result is not null.</li>
     * </ul>
     *
     * Length is Σ<sub>i=0</sub><sup>n</sup><sub>|{@code xs}|</sub>P<sub>{@code i}</sub>
     *
     * @param xs the {@code List} from which elements are selected
     * @param <T> the type of the given {@code List}'s elements
     * @return all {@code List}s with no repetitions created from {@code xs}
     */
    @Override
    public @NotNull <T> Iterable<List<T>> distinctListsShortlex(@NotNull List<T> xs) {
        return concatMap(i -> distinctListsLex(i, xs), range(0, xs.size()));
    }

    /**
     * Returns an {@code Iterable} containing all {@code String}s with characters from a given {@code String} with no
     * repetitions. The {@code String}s are in shortlex order; that is, shorter {@code String}s precede longer
     * {@code String}s, and {@code String}s of the same length are ordered lexicographically, matching the order given
     * by the original {@code String}. Does not support removal.
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result is not null.</li>
     * </ul>
     *
     * Length is Σ<sub>i=0</sub><sup>n</sup><sub>|{@code s}|</sub>P<sub>{@code i}</sub>
     *
     * @param s the {@code String} from which characters are selected
     * @return all {@code Strings}s with no repetitions created from {@code s}
     */
    @Override
    public @NotNull Iterable<String> distinctStringsShortlex(@NotNull String s) {
        return map(IterableUtils::charsToString, distinctListsShortlex(toList(s)));
    }

    /**
     * Returns an {@code Iterable} containing all {@code Lists}s with a minimum size with elements from a given
     * {@code List} with no repetitions. The {@code List}s are in shortlex order; that is, shorter {@code List}s
     * precede longer {@code List}s, and {@code List}s of the same length are ordered lexicographically, matching the
     * order given by the original {@code List}. Does not support removal.
     *
     * <ul>
     *  <li>{@code minSize} cannot be negative.</li>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result is not null.</li>
     * </ul>
     *
     * Length is Σ<sub>i={@code minSize}</sub><sup>n</sup><sub>|{@code xs}|</sub>P<sub>{@code i}</sub>
     *
     * @param minSize the minimum length of the result {@code List}s
     * @param xs the {@code List} from which elements are selected
     * @param <T> the type of the given {@code Iterable}'s elements
     * @return all {@code List}s with length at least {@code minSize} with no repetitions created from {@code xs}
     */
    @Override
    public @NotNull <T> Iterable<List<T>> distinctListsShortlexAtLeast(int minSize, @NotNull List<T> xs) {
        if (minSize < 0) {
            throw new IllegalArgumentException("minSize cannot be negative. Invalid minSize: " + minSize);
        }
        return concatMap(i -> distinctListsLex(i, xs), range(minSize, xs.size()));
    }

    /**
     * Returns an {@code Iterable} containing all {@code String}s with a minimum size with elements from a given
     * {@code String} with no repetitions. The {@code String}s are in shortlex order; that is, shorter {@code String}s
     * precede longer {@code String}s, and {@code String}s of the same length are ordered lexicographically, matching
     * the order given by the original {@code String}. Does not support removal.
     *
     * <ul>
     *  <li>{@code minSize} cannot be negative.</li>
     *  <li>{@code s} cannot be null.</li>
     *  <li>The result is not null.</li>
     * </ul>
     *
     * Length is Σ<sub>i={@code minSize}</sub><sup>n</sup><sub>|{@code s}|</sub>P<sub>{@code i}</sub>
     *
     * @param minSize the minimum length of the result {@code String}s
     * @param s the {@code String} from which elements are selected
     * @return all {@code String}s with length at least {@code minSize} with no repetitions created from {@code s}
     */
    @Override
    public @NotNull Iterable<String> distinctStringsShortlexAtLeast(int minSize, @NotNull String s) {
        return map(IterableUtils::charsToString, distinctListsShortlexAtLeast(minSize, toList(s)));
    }

    /**
     * A helper method for generating distinct lists. Given an {@code Iterable} of lists of integers,
     * {@code originalIndices}, this method reinterprets the indices to only generate lists with no repetitions. For
     * example, consider the list [0, 0, 3, 1]. Let's select a list of length 4 from the characters 'a' through 'z'
     * using these indices. Since we don't want any repetitions, we'll cross off every character we see. Here's what we
     * get:
     * <ul>
     *  <li><tt>     abcdefghijklmnopqrstuvwxyz</tt></li>
     *  <li><tt>0: a Xbcdefghijklmnopqrstuvwxyz</tt></li>
     *  <li><tt>0: b XXcdefghijklmnopqrstuvwxyz</tt></li>
     *  <li><tt>3: f XXcdeXghijklmnopqrstuvwxyz</tt></li>
     *  <li><tt>1: d XXcXeXghijklmnopqrstuvwxyz</tt></li>
     * </ul>
     * So [0, 0, 3, 1] corresponds to [a, b, f, d].
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>{@code originalIndices} cannot contain any nulls, and all of its elements can only contain nonnegative
     *  {@code Integer}s.</li>
     *  <li>{@code requiredSize} cannot be null.</li>
     *  <li>{@code outputSizeFunction} cannot be null.</li>
     *  <li>If {@code xs} is finite, {@code outputSizeFunction} must return a nonnegative integer when applied to
     *  {@code length(xs)}.</li>
     *  <li>The number of lists in {@code originalSize} that correspond to a valid distinct list must be at least
     *  {@code outputSizeFunction.apply(length(xs))} (or infinite if {@code xs} is infinite).</li>
     *  <li>If {@code requiredSize} is nonempty, it must be no greater than the size of {@code xs}.</li>
     * </ul>
     *
     * @param xs an {@code Iterable}
     * @param originalIndices an {@code Iterable} of lists, each list corresponding to a list of elements from
     * {@code xs} with no repetitions
     * @param requiredSize the minimum size of any of the generated lists
     * @param outputSizeFunction The total number of generated lists as a function of the size of {@code xs}
     * @param <T> the type of the elements in {@code xs}
     * @return lists of elements from {@code xs} with no repetitions
     */
    private static @NotNull <T> Iterable<List<T>> distinctIndices(
            @NotNull Iterable<T> xs,
            @NotNull Iterable<List<Integer>> originalIndices,
            @NotNull Optional<Integer> requiredSize,
            @NotNull Function<Integer, BigInteger> outputSizeFunction
    ) {
        return () -> new EventuallyKnownSizeIterator<List<T>>() {
            private final @NotNull CachedIterator<T> cxs = new CachedIterator<>(xs);
            private final @NotNull Iterator<List<Integer>> indices = originalIndices.iterator();
            {
                if (requiredSize.isPresent() && requiredSize.get() != 0) {
                    if (!cxs.get(requiredSize.get() - 1).isPresent()) {
                        setOutputSize(BigInteger.ZERO);
                    }
                }
            }

            @Override
            public List<T> advance() {
                outer:
                while (true) {
                    BitSet taken = new BitSet();
                    List<T> output = new ArrayList<>();
                    for (int index : indices.next()) {
                        int i = 0;
                        while (taken.get(i)) i++;
                        for (int j = 0; j < index; j++) {
                            i++;
                            while (taken.get(i)) i++;
                        }
                        NullableOptional<T> element = cxs.get(i);
                        if (!element.isPresent()) continue outer;
                        output.add(element.get());
                        taken.set(i);
                    }
                    if (!outputSizeKnown() && cxs.knownSize().isPresent()) {
                        setOutputSize(outputSizeFunction.apply(cxs.knownSize().get()));
                    }
                    return output;
                }
            }
        };
    }

    /**
     * Returns an {@code Iterable} containing all {@code List}s of a given length with elements from a given
     * {@code List}, with no repetitions. Does not support removal.
     *
     * <ul>
     *  <li>{@code size} cannot be negative.</li>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result is finite. All of its elements have the same length. None are empty, unless the result consists
     *  entirely of one empty element.</li>
     * </ul>
     *
     * Length is <sub>|{@code xs}|</sub>P<sub>{@code size}</sub>
     *
     * @param size the length of the result lists
     * @param xs the {@code List} from which elements are selected
     * @param <T> the type of the given {@code List}'s elements
     * @return all {@code List}s of a given length created from {@code xs} with no repetitions
     */
    @Override
    public @NotNull <T> Iterable<List<T>> distinctLists(int size, @NotNull Iterable<T> xs) {
        if (size == 0) return Collections.singletonList(Collections.emptyList());
        return distinctIndices(
                xs,
                lists(size, naturalIntegers()),
                Optional.of(size),
                n -> MathUtils.fallingFactorial(BigInteger.valueOf(n), size)
        );
    }

    /**
     * Returns all {@code Pair}s of distinct elements from an {@code Iterable}. Does not support removal.
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result contains all distinct {@code Pair}s of elements from an {@code Iterable}.</li>
     * </ul>
     *
     * Length is <sub>|{@code xs}|</sub>P<sub>2</sub>
     *
     * @param xs an {@code Iterable}
     * @param <T> the type of the {@code Iterable}'s elements
     * @return all distinct ordered {@code Pair}s of elements from {@code xs}
     */
    @Override
    public @NotNull <T> Iterable<Pair<T, T>> distinctPairs(@NotNull Iterable<T> xs) {
        return map(list -> new Pair<>(list.get(0), list.get(1)), distinctLists(2, xs));
    }

    /**
     * Returns all {@code Triple}s of distinct elements from an {@code Iterable}. Does not support removal.
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result contains all distinct {@code Triple}s of elements from an {@code Iterable}.</li>
     * </ul>
     *
     * Length is <sub>|{@code xs}|</sub>P<sub>3</sub>
     *
     * @param xs an {@code Iterable}
     * @param <T> the type of the {@code Iterable}'s elements
     * @return all distinct ordered {@code Triple}s of elements from {@code xs}
     */
    @Override
    public @NotNull <T> Iterable<Triple<T, T, T>> distinctTriples(@NotNull Iterable<T> xs) {
        return map(list -> new Triple<>(list.get(0), list.get(1), list.get(2)), distinctLists(3, xs));
    }

    /**
     * Returns all {@code Quadruple}s of distinct elements from an {@code Iterable}. Does not support removal.
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result contains all distinct {@code Quadruple}s of elements from an {@code Iterable}.</li>
     * </ul>
     *
     * Length is <sub>|{@code xs}|</sub>P<sub>4</sub>
     *
     * @param xs an {@code Iterable}
     * @param <T> the type of the {@code Iterable}'s elements
     * @return all distinct ordered {@code Quadruple}s of elements from {@code xs}
     */
    @Override
    public @NotNull <T> Iterable<Quadruple<T, T, T, T>> distinctQuadruples(@NotNull Iterable<T> xs) {
        return map(list -> new Quadruple<>(list.get(0), list.get(1), list.get(2), list.get(3)), distinctLists(4, xs));
    }

    /**
     * Returns all {@code Quintuple}s of distinct elements from an {@code Iterable}. Does not support removal.
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result contains all distinct {@code Quintuple}s of elements from an {@code Iterable}.</li>
     * </ul>
     *
     * Length is <sub>|{@code xs}|</sub>P<sub>5</sub>
     *
     * @param xs an {@code Iterable}
     * @param <T> the type of the {@code Iterable}'s elements
     * @return all distinct ordered {@code Quintuple}s of elements from {@code xs}
     */
    @Override
    public @NotNull <T> Iterable<Quintuple<T, T, T, T, T>> distinctQuintuples(@NotNull Iterable<T> xs) {
        return map(
                list -> new Quintuple<>(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4)),
                distinctLists(5, xs)
        );
    }

    /**
     * Returns all {@code Sextuple}s of distinct elements from an {@code Iterable}. Does not support removal.
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result contains all distinct {@code Sextuple}s of elements from an {@code Iterable}.</li>
     * </ul>
     *
     * Length is <sub>|{@code xs}|</sub>P<sub>6</sub>
     *
     * @param xs an {@code Iterable}
     * @param <T> the type of the {@code Iterable}'s elements
     * @return all distinct ordered {@code Sextuple}s of elements from {@code xs}
     */
    @Override
    public @NotNull <T> Iterable<Sextuple<T, T, T, T, T, T>> distinctSextuples(@NotNull Iterable<T> xs) {
        return map(
                list -> new Sextuple<>(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4), list.get(5)),
                distinctLists(6, xs)
        );
    }

    /**
     * Returns all {@code Septuple}s of distinct elements from an {@code Iterable}. Does not support removal.
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result contains all distinct {@code Septuple}s of elements from an {@code Iterable}.</li>
     * </ul>
     *
     * Length is <sub>|{@code xs}|</sub>P<sub>7</sub>
     *
     * @param xs an {@code Iterable}
     * @param <T> the type of the {@code Iterable}'s elements
     * @return all distinct ordered {@code Septuple}s of elements from {@code xs}
     */
    @Override
    public @NotNull <T> Iterable<Septuple<T, T, T, T, T, T, T>> distinctSeptuples(@NotNull Iterable<T> xs) {
        return map(
                list -> new Septuple<>(
                        list.get(0),
                        list.get(1),
                        list.get(2),
                        list.get(3),
                        list.get(4),
                        list.get(5),
                        list.get(6)
                ),
                distinctLists(7, xs)
        );
    }

    /**
     * Returns an {@code Iterable} containing all {@code Lists}s with elements from a given {@code List} with no
     * repetitions. Does not support removal.
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result contains no repetitions.</li>
     * </ul>
     *
     * Length is Σ<sub>i=0</sub><sup>n</sup><sub>|{@code xs}|</sub>P<sub>{@code i}</sub>
     *
     * @param xs the {@code List} from which elements are selected
     * @param <T> the type of the given {@code List}'s elements
     * @return all {@code List}s with no repetitions created from {@code xs}
     */
    @Override
    public @NotNull <T> Iterable<List<T>> distinctLists(@NotNull Iterable<T> xs) {
        return distinctIndices(xs, lists(naturalIntegers()), Optional.empty(), MathUtils::numberOfArrangementsOfASet);
    }

    /**
     * Returns an {@code Iterable} containing all {@code Lists}s with a minimum size with elements from a given
     * {@code List} with no repetitions. Does not support removal.
     *
     * <ul>
     *  <li>{@code minSize} cannot be negative.</li>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result contains every {@code List} (with a length greater than or equal to some minimum) of elements
     *  drawn from some sequence with no repetitions.</li>
     * </ul>
     *
     * Length is Σ<sub>i={@code minSize}</sub><sup>n</sup><sub>|{@code xs}|</sub>P<sub>{@code i}</sub>
     *
     * @param minSize the minimum length of the result {@code List}s
     * @param xs the {@code List} from which elements are selected
     * @param <T> the type of the given {@code Iterable}'s elements
     * @return all {@code List}s with length at least {@code minSize} with no repetitions created from {@code xs}
     */
    @Override
    public @NotNull <T> Iterable<List<T>> distinctListsAtLeast(int minSize, @NotNull Iterable<T> xs) {
        return distinctIndices(
                xs,
                listsAtLeast(minSize, naturalIntegers()),
                Optional.of(minSize),
                n -> MathUtils.numberOfArrangementsOfASet(minSize, n)
        );
    }

    /**
     * Returns all sorted {@code List}s of a given size containing natural numbers up to a given value. Does not
     * support removal.
     *
     * <ul>
     *  <li>{@code size} cannot be negative.</li>
     *  <li>{@code elementCount} cannot be negative.</li>
     *  <li>The result is in lexicographic order, contains only sorted lists of non-negative integers, and contains no
     *  repetitions. Each element has the same size.</li>
     * </ul>
     *
     * Length is <sub>{@code elementCount}+{@code size}–1</sub>C<sub>{@code size}</sub>
     *
     * @param size the length of each of the result {@code List}s
     * @param elementCount one more than the largest possible value in the result {@code List}s
     * @return all sorted lists of length {@code size} with elements from 0 to {@code elementCount}–1
     */
    private static @NotNull Iterable<List<Integer>> bagIndices(int size, int elementCount) {
        BigInteger outputSize = MathUtils.multisetCoefficient(BigInteger.valueOf(elementCount), size);
        int indexLimit = elementCount - 1;
        return () -> new EventuallyKnownSizeIterator<List<Integer>>() {
            private final @NotNull List<Integer> list = toList(replicate(size, 0));
            private boolean first = true;
            {
                setOutputSize(outputSize);
            }

            @Override
            public @NotNull List<Integer> advance() {
                if (first) {
                    first = false;
                    return list;
                }
                for (int i = size - 1; i >= 0; i--) {
                    int j = list.get(i);
                    if (j != indexLimit) {
                        j++;
                        for (int k = i; k < size; k++) {
                            list.set(k, j);
                        }
                        return list;
                    }
                }
                throw new IllegalStateException("unreachable");
            }
        };
    }

    /**
     * Returns an {@code Iterable} containing all sorted {@code List}s of a given length with elements from a given
     * {@code List}. The {@code List}s are ordered lexicographically. Does not support removal.
     *
     * <ul>
     *  <li>{@code size} cannot be negative.</li>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result is finite. All of its elements have the same length and are sorted. None are empty, unless the
     *  result consists entirely of one empty element.</li>
     * </ul>
     *
     * Length is <sub>|{@code xs}|+{@code size}–1</sub>C<sub>{@code size}</sub>
     *
     * @param size the length of the result lists
     * @param xs the {@code List} from which elements are selected
     * @param <T> the type of the given {@code List}'s elements
     * @return all sorted {@code List}s of a given length created from {@code xs}
     */
    @Override
    public @NotNull <T extends Comparable<T>> Iterable<List<T>> bagsLex(int size, @NotNull List<T> xs) {
        if (xs.size() == 1) {
            T first = xs.get(0);
            first.compareTo(first); //catch incomparable single element; sort will catch the other cases
        }
        List<T> sorted = sort(xs);
        return map(is -> toList(map(sorted::get, is)), bagIndices(size, xs.size()));
    }

    /**
     * Returns all unordered {@code Pair}s from a {@code List}. The {@code Pair}s are ordered lexicographically. Does
     * not support removal.
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result contains all sorted {@code Pair}s of elements from a {@code List}.</li>
     * </ul>
     *
     * Length is <sub>|{@code xs}|+1</sub>C<sub>2</sub>
     *
     * @param xs a {@code List}
     * @param <T> the type of the {@code List}'s elements
     * @return all unordered {@code Pair}s of elements from {@code xs}
     */
    @Override
    public @NotNull <T extends Comparable<T>> Iterable<Pair<T, T>> bagPairsLex(@NotNull List<T> xs) {
        return map(list -> new Pair<>(list.get(0), list.get(1)), bagsLex(2, xs));
    }

    /**
     * Returns all unordered {@code Triple}s from a {@code List}. The {@code Triple}s are ordered lexicographically.
     * Does not support removal.
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result contains all sorted {@code Triple}s of elements from a {@code List}.</li>
     * </ul>
     *
     * Length is <sub>|{@code xs}|+2</sub>C<sub>3</sub>
     *
     * @param xs a {@code List}
     * @param <T> the type of the {@code List}'s elements
     * @return all unordered {@code Triple}s of elements from {@code xs}
     */
    @Override
    public @NotNull <T extends Comparable<T>> Iterable<Triple<T, T, T>> bagTriplesLex(@NotNull List<T> xs) {
        return map(list -> new Triple<>(list.get(0), list.get(1), list.get(2)), bagsLex(3, xs));
    }

    /**
     * Returns all unordered {@code Quadruple}s from a {@code List}. The {@code Quadruple}s are ordered
     * lexicographically. Does not support removal.
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result contains all sorted {@code Quadruple}s of elements from a {@code List}.</li>
     * </ul>
     *
     * Length is <sub>|{@code xs}|+3</sub>C<sub>4</sub>
     *
     * @param xs a {@code List}
     * @param <T> the type of the {@code List}'s elements
     * @return all unordered {@code Quadruple}s of elements from {@code xs}
     */
    @Override
    public @NotNull <T extends Comparable<T>> Iterable<Quadruple<T, T, T, T>> bagQuadruplesLex(@NotNull List<T> xs) {
        return map(list -> new Quadruple<>(list.get(0), list.get(1), list.get(2), list.get(3)), bagsLex(4, xs));
    }

    /**
     * Returns all unordered {@code Quintuple}s from a {@code List}. The {@code Quintuple}s are ordered
     * lexicographically. Does not support removal.
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result contains all sorted {@code Quintuple}s of elements from a {@code List}.</li>
     * </ul>
     *
     * Length is <sub>|{@code xs}|+4</sub>C<sub>5</sub>
     *
     * @param xs a {@code List}
     * @param <T> the type of the {@code List}'s elements
     * @return all unordered {@code Quintuple}s of elements from {@code xs}
     */
    @Override
    public @NotNull <T extends Comparable<T>> Iterable<Quintuple<T, T, T, T, T>> bagQuintuplesLex(
            @NotNull List<T> xs
    ) {
        return map(
                list -> new Quintuple<>(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4)),
                bagsLex(5, xs)
        );
    }

    /**
     * Returns all unordered {@code Sextuple}s from a {@code List}. The {@code Sextuple}s are ordered
     * lexicographically. Does not support removal.
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result contains all sorted {@code Sextuple}s of elements from a {@code List}.</li>
     * </ul>
     *
     * Length is <sub>|{@code xs}|+5</sub>C<sub>6</sub>
     *
     * @param xs a {@code List}
     * @param <T> the type of the {@code List}'s elements
     * @return all unordered {@code Sextuple}s of elements from {@code xs}
     */
    @Override
    public @NotNull <T extends Comparable<T>> Iterable<Sextuple<T, T, T, T, T, T>> bagSextuplesLex(
            @NotNull List<T> xs
    ) {
        return map(
                list -> new Sextuple<>(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4), list.get(5)),
                bagsLex(6, xs)
        );
    }

    /**
     * Returns all unordered {@code Septuple}s from a {@code List}. The {@code Septuple}s are ordered
     * lexicographically. Does not support removal.
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result contains all sorted {@code Septuple}s of elements from a {@code List}.</li>
     * </ul>
     *
     * Length is <sub>|{@code xs}|+6</sub>C<sub>7</sub>
     *
     * @param xs a {@code List}
     * @param <T> the type of the {@code List}'s elements
     * @return all unordered {@code Septuple}s of elements from {@code xs}
     */
    @Override
    public @NotNull <T extends Comparable<T>> Iterable<Septuple<T, T, T, T, T, T, T>> bagSeptuplesLex(
            @NotNull List<T> xs
    ) {
        return map(
                list -> new Septuple<>(
                        list.get(0),
                        list.get(1),
                        list.get(2),
                        list.get(3),
                        list.get(4),
                        list.get(5),
                        list.get(6)
                ),
                bagsLex(7, xs)
        );
    }

    /**
     * Returns an {@code Iterable} containing all sorted {@code String}s of a given length with characters from a given
     * {@code String}. The {@code String}s are ordered lexicographically. Does not support removal.
     *
     * <ul>
     *  <li>{@code size} cannot be negative.</li>
     *  <li>{@code s} cannot be null.</li>
     *  <li>The result is finite. All of its elements have the same length and are sorted. None are empty, unless the
     *  result consists entirely of one empty element.</li>
     * </ul>
     *
     * Length is <sub>|{@code s}|+{@code size}–1</sub>C<sub>{@code size}</sub>
     *
     * @param size the length of the result {@code String}s
     * @param s the {@code String} from which characters are selected
     * @return all sorted {@code String}s of a given length created from {@code xs}
     */
    @Override
    public @NotNull Iterable<String> stringBagsLex(int size, @NotNull String s) {
        return map(IterableUtils::charsToString, bagsLex(size, toList(s)));
    }

    /**
     * Returns an {@code Iterable} containing all sorted {@code Lists}s with elements from a given {@code List}. The
     * {@code List}s are in shortlex order; that is, shorter {@code List}s precede longer {@code List}s, and
     * {@code List}s of the same length are ordered lexicographically. Does not support removal.
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result is not null.</li>
     * </ul>
     *
     * Length is 1 if {@code xs} is empty, and infinite otherwise
     *
     * @param xs the {@code List} from which elements are selected
     * @param <T> the type of the given {@code List}'s elements
     * @return all sorted {@code List}s created from {@code xs}
     */
    @Override
    public @NotNull <T extends Comparable<T>> Iterable<List<T>> bagsShortlex(@NotNull List<T> xs) {
        if (isEmpty(xs)) return Collections.singletonList(Collections.emptyList());
        return concatMap(i -> bagsLex(i.intValueExact(), xs), naturalBigIntegers());
    }

    /**
     * Returns an {@code Iterable} containing all sorted {@code String}s with characters from a given {@code String}.
     * The {@code String}s are in shortlex order; that is, shorter {@code List}s precede longer {@code List}s, and
     * {@code String}s of the same length are ordered lexicographically. Does not support removal.
     *
     * <ul>
     *  <li>{@code s} cannot be null.</li>
     *  <li>The result is not null.</li>
     * </ul>
     *
     * Length is 1 if {@code s} is empty, and infinite otherwise
     *
     * @param s the {@code String} from which characters are selected
     * @return all sorted {@code String}s created from {@code s}
     */
    @Override
    public @NotNull Iterable<String> stringBagsShortlex(@NotNull String s) {
        return map(IterableUtils::charsToString, bagsShortlex(toList(s)));
    }

    /**
     * Returns an {@code Iterable} containing all sorted {@code Lists}s with a minimum size with elements from a given
     * {@code List}. The {@code List}s are in shortlex order; that is, shorter {@code List}s precede longer
     * {@code List}s, and {@code List}s of the same length are ordered lexicographically. Does not support removal.
     *
     * <ul>
     *  <li>{@code minSize} cannot be negative.</li>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result is not null.</li>
     * </ul>
     *
     * Length is 0 if {@code xs} is empty and {@code minSize} is greater than 0, 1 if {@code xs} is empty and
     * {@code minSize} is 0, and infinite otherwise
     *
     * @param minSize the minimum length of the result {@code List}s
     * @param xs the {@code List} from which elements are selected
     * @param <T> the type of the given {@code Iterable}'s elements
     * @return all sorted {@code List}s with length at least {@code minSize} created from {@code xs}
     */
    @Override
    public @NotNull <T extends Comparable<T>> Iterable<List<T>> bagsShortlexAtLeast(int minSize, @NotNull List<T> xs) {
        if (minSize < 0) {
            throw new IllegalArgumentException("minSize cannot be negative. Invalid minSize: " + minSize);
        }
        if (isEmpty(xs)) return minSize == 0 ?
                Collections.singletonList(Collections.emptyList()) :
                Collections.emptyList();
        return concatMap(i -> bagsLex(i.intValueExact(), xs), rangeUp(BigInteger.valueOf(minSize)));
    }

    /**
     * Returns an {@code Iterable} containing all sorted {@code String}s with a minimum size with characters from a
     * given {@code String}. The {@code String}s are in shortlex order; that is, shorter {@code String}s precede longer
     * {@code String}s, and {@code String}s of the same length are ordered lexicographically. Does not support removal.
     *
     * <ul>
     *  <li>{@code minSize} cannot be negative.</li>
     *  <li>{@code s} cannot be null.</li>
     *  <li>The result is not null.</li>
     * </ul>
     *
     * Length is 0 if {@code s} is empty and {@code minSize} is greater than 0, 1 if {@code s} is empty and
     * {@code minSize} is 0, and infinite otherwise
     *
     * @param minSize the minimum length of the result {@code String}s
     * @param s the {@code String} from which characters are selected
     * @return all sorted {@code String}s with length at least {@code minSize} created from {@code s}
     */
    @Override
    public @NotNull Iterable<String> stringBagsShortlexAtLeast(int minSize, @NotNull String s) {
        return map(IterableUtils::charsToString, bagsShortlexAtLeast(minSize, toList(s)));
    }

    /**
     * A helper method for generating bags (multisets). Given an {@code Iterable} of lists of integers,
     * {@code originalIndices}, this method reinterprets the indices to only generate sorted lists. For example,
     * consider the list [1, 0, 5, 3]. Let's select a list of length 4 from the characters 'a' through 'z' using these
     * indices. We'll use the cumulative sums of these indices, which are [1, 1 + 0, 1 + 0 + 5, 1 + 0 + 5 + 3], or
     * [1, 1, 6, 9]. This corresponds to [a, a, f, i].
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>{@code originalIndices} cannot contain any nulls, and all of its elements can only contain nonnegative
     *  {@code Integer}s.</li>
     *  <li>{@code outputSizeFunction} cannot be null.</li>
     *  <li>If {@code xs} is finite, {@code outputSizeFunction} must return a either an empty {@code Optional} or a
     *  nonnegative integer when applied to {@code length(xs)}.</li>
     *  <li>The number of lists in {@code originalSize} that correspond to a valid bag must be at least
     *  {@code outputSizeFunction.apply(length(xs))} (or infinite if {@code xs} is infinite or
     *  {@code outputSizeFunction.apply(length(xs))} is empty).</li>
     * </ul>
     *
     * @param xs an {@code Iterable}
     * @param originalIndices an {@code Iterable} of lists, each list corresponding to a sorted list of elements from
     * {@code xs}
     * @param outputSizeFunction The total number of generated lists as a function of the size of {@code xs}
     * @param <T> the type of the elements in {@code xs}
     * @return sorted lists of elements from {@code xs}
     */
    private static @NotNull <T extends Comparable<T>> Iterable<List<T>> bagIndices(
            @NotNull Iterable<T> xs,
            @NotNull Iterable<List<Integer>> originalIndices,
            @NotNull Function<Integer, Optional<BigInteger>> outputSizeFunction
    ) {
        return () -> new EventuallyKnownSizeIterator<List<T>>() {
            private final @NotNull CachedIterator<T> cxs = new CachedIterator<>(xs);
            private final @NotNull Iterator<List<Integer>> indices = originalIndices.iterator();

            @Override
            public List<T> advance() {
                while (true) {
                    List<Integer> next = indices.next();
                    List<Integer> cumulativeIndices = next.isEmpty() ?
                            Collections.emptyList() :
                            toList(scanl1((x, y) -> x + y, next));
                    if (!cumulativeIndices.isEmpty() && !cxs.get(last(cumulativeIndices)).isPresent()) {
                        continue;
                    }
                    List<T> output = toList(map(i -> cxs.get(i).get(), cumulativeIndices));
                    if (!outputSizeKnown() && cxs.knownSize().isPresent()) {
                        Optional<BigInteger> outputSize = outputSizeFunction.apply(cxs.knownSize().get());
                        if (outputSize.isPresent()) {
                            setOutputSize(outputSize.get());
                        }
                    }
                    if (output.size() == 1) {
                        T first = output.get(0);
                        first.compareTo(first); //catch incomparable single element; sort will catch the other cases
                    }
                    return sort(output);
                }
            }
        };
    }

    /**
     * Returns an {@code Iterable} containing all sorted {@code List}s of a given length with elements from a given
     * {@code Iterable}. Does not support removal.
     *
     * <ul>
     *  <li>{@code size} cannot be negative.</li>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>All of the result's elements have the same length and are sorted. None are empty, unless the result
     *  consists entirely of one empty element.</li>
     * </ul>
     *
     * Length is <sub>|{@code xs}|+{@code size}–1</sub>C<sub>{@code size}</sub>
     *
     * @param size the length of the result lists
     * @param xs the {@code Iterable} from which elements are selected
     * @param <T> the type of the given {@code Iterable}'s elements
     * @return all sorted {@code List}s of a given length created from {@code xs}
     */
    @Override
    public @NotNull <T extends Comparable<T>> Iterable<List<T>> bags(int size, @NotNull Iterable<T> xs) {
        if (size < 0) {
            throw new IllegalArgumentException("size cannot be negative. Invalid size: " + size);
        }
        if (size == 0) return Collections.singletonList(Collections.emptyList());
        if (isEmpty(xs)) return Collections.emptyList();
        return bagIndices(
                xs,
                lists(size, naturalIntegers()),
                n -> Optional.of(MathUtils.multisetCoefficient(BigInteger.valueOf(n), size))
        );
    }

    /**
     * Returns all unordered {@code Pair}s from an {@code Iterable}. Does not support removal.
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result contains all sorted {@code Pair}s of elements from an {@code Iterable}.</li>
     * </ul>
     *
     * Length is <sub>|{@code xs}|+1</sub>C<sub>2</sub>
     *
     * @param xs an {@code Iterable}
     * @param <T> the type of the {@code Iterable}'s elements
     * @return all unordered {@code Pair}s of elements from {@code xs}
     */
    @Override
    public @NotNull <T extends Comparable<T>> Iterable<Pair<T, T>> bagPairs(@NotNull Iterable<T> xs) {
        return map(list -> new Pair<>(list.get(0), list.get(1)), bags(2, xs));
    }

    /**
     * Returns all unordered {@code Triple}s from an {@code Iterable}. Does not support removal.
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result contains all sorted {@code Triple}s of elements from an {@code Iterable}.</li>
     * </ul>
     *
     * Length is <sub>|{@code xs}|+1</sub>C<sub>3</sub>
     *
     * @param xs an {@code Iterable}
     * @param <T> the type of the {@code Iterable}'s elements
     * @return all unordered {@code Triple}s of elements from {@code xs}
     */
    @Override
    public @NotNull <T extends Comparable<T>> Iterable<Triple<T, T, T>> bagTriples(@NotNull Iterable<T> xs) {
        return map(list -> new Triple<>(list.get(0), list.get(1), list.get(2)), bags(3, xs));
    }

    /**
     * Returns all unordered {@code Quadruple}s from an {@code Iterable}. Does not support removal.
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result contains all sorted {@code Quadruple}s of elements from an {@code Iterable}.</li>
     * </ul>
     *
     * Length is <sub>|{@code xs}|+1</sub>C<sub>4</sub>
     *
     * @param xs an {@code Iterable}
     * @param <T> the type of the {@code Iterable}'s elements
     * @return all unordered {@code Quadruple}s of elements from {@code xs}
     */
    @Override
    public @NotNull <T extends Comparable<T>> Iterable<Quadruple<T, T, T, T>> bagQuadruples(@NotNull Iterable<T> xs) {
        return map(list -> new Quadruple<>(list.get(0), list.get(1), list.get(2), list.get(3)), bags(4, xs));
    }

    /**
     * Returns all unordered {@code Quintuple}s from an {@code Iterable}. Does not support removal.
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result contains all sorted {@code Quintuple}s of elements from an {@code Iterable}.</li>
     * </ul>
     *
     * Length is <sub>|{@code xs}|+1</sub>C<sub>5</sub>
     *
     * @param xs an {@code Iterable}
     * @param <T> the type of the {@code Iterable}'s elements
     * @return all unordered {@code Quintuple}s of elements from {@code xs}
     */
    @Override
    public @NotNull <T extends Comparable<T>> Iterable<Quintuple<T, T, T, T, T>> bagQuintuples(
            @NotNull Iterable<T> xs
    ) {
        return map(
                list -> new Quintuple<>(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4)),
                bags(5, xs)
        );
    }

    /**
     * Returns all unordered {@code Sextuple}s from an {@code Iterable}. Does not support removal.
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result contains all sorted {@code Sextuple}s of elements from an {@code Iterable}.</li>
     * </ul>
     *
     * Length is <sub>|{@code xs}|+1</sub>C<sub>6</sub>
     *
     * @param xs an {@code Iterable}
     * @param <T> the type of the {@code Iterable}'s elements
     * @return all unordered {@code Sextuple}s of elements from {@code xs}
     */
    @Override
    public @NotNull <T extends Comparable<T>> Iterable<Sextuple<T, T, T, T, T, T>> bagSextuples(
            @NotNull Iterable<T> xs
    ) {
        return map(
                list -> new Sextuple<>(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4), list.get(5)),
                bags(6, xs)
        );
    }

    /**
     * Returns all unordered {@code Septuple}s from an {@code Iterable}. Does not support removal.
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result contains all sorted {@code Septuple}s of elements from an {@code Iterable}.</li>
     * </ul>
     *
     * Length is <sub>|{@code xs}|+1</sub>C<sub>7</sub>
     *
     * @param xs an {@code Iterable}
     * @param <T> the type of the {@code Iterable}'s elements
     * @return all unordered {@code Septuple}s of elements from {@code xs}
     */
    @Override
    public @NotNull <T extends Comparable<T>> Iterable<Septuple<T, T, T, T, T, T, T>> bagSeptuples(
            @NotNull Iterable<T> xs
    ) {
        return map(
                list -> new Septuple<>(
                        list.get(0),
                        list.get(1),
                        list.get(2),
                        list.get(3),
                        list.get(4),
                        list.get(5),
                        list.get(6)
                ),
                bags(7, xs)
        );
    }

    /**
     * Returns an {@code Iterable} containing all sorted {@code List}s with elements from a given {@code Iterable}.
     * Does not support removal.
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>All of the result's elements are sorted. None are empty, unless the result consists entirely of one empty
     *  element.</li>
     * </ul>
     *
     * Length is 0 if {@code xs} is empty, infinite otherwise
     *
     * @param xs the {@code Iterable} from which elements are selected
     * @param <T> the type of the given {@code Iterable}'s elements
     * @return all sorted {@code List}s created from {@code xs}
     */
    @Override
    public @NotNull <T extends Comparable<T>> Iterable<List<T>> bags(@NotNull Iterable<T> xs) {
        if (isEmpty(xs)) return Collections.singletonList(Collections.emptyList());
        if (!lengthAtLeast(2, xs)) {
            T x = head(xs);
            x.compareTo(x);
            return iterate(ys -> toList(cons(x, ys)), Collections.emptyList());
        }
        return bagIndices(xs, lists(naturalIntegers()), n -> Optional.empty());
    }

    /**
     * Returns an {@code Iterable} containing all sorted {@code Lists}s with a minimum size with elements from a given
     * {@code List}. Does not support removal.
     *
     * <ul>
     *  <li>{@code minSize} cannot be negative.</li>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result contains every sorted {@code List} (with a length greater than or equal to some minimum) of
     *  elements drawn from some sequence.</li>
     * </ul>
     *
     * Length is 0 if {@code xs} is empty and {@code minSize} is greater than 0, 1 if {@code xs} is empty and
     * {@code minSize} is 0, and infinite otherwise
     *
     * @param minSize the minimum length of the result {@code List}s
     * @param xs the {@code List} from which elements are selected
     * @param <T> the type of the given {@code Iterable}'s elements
     * @return all sorted {@code List}s with length at least {@code minSize} created from {@code xs}
     */
    @Override
    public @NotNull <T extends Comparable<T>> Iterable<List<T>> bagsAtLeast(int minSize, @NotNull Iterable<T> xs) {
        if (minSize < 0) {
            throw new IllegalArgumentException("size cannot be negative. Invalid size: " + minSize);
        }
        if (minSize == 0) return bags(xs);
        if (isEmpty(xs)) return Collections.emptyList();
        if (!lengthAtLeast(2, xs)) {
            T x = head(xs);
            x.compareTo(x);
            return iterate(ys -> toList(cons(x, ys)), toList(replicate(minSize, x)));
        }
        return bagIndices(xs, listsAtLeast(minSize, naturalIntegers()), n -> Optional.empty());
    }

    /**
     * Returns all sorted {@code List}s of a given size containing natural numbers up to a given value with no
     * repetitions. Does not support removal.
     *
     * <ul>
     *  <li>{@code size} cannot be negative.</li>
     *  <li>{@code elementCount} cannot be negative.</li>
     *  <li>The result is in lexicographic order, contains only sorted lists of non-negative integers with no
     *  repetitions, and contains no repetitions. Each element has the same size.</li>
     * </ul>
     *
     * Length is <sub>{@code elementCount}</sub>C<sub>{@code size}</sub>
     *
     * @param size the length of each of the result {@code List}s
     * @param elementCount one more than the largest possible value in the result {@code List}s
     * @return all sorted lists of length {@code size} with elements from 0 to {@code elementCount}–1 with no
     * repetitions
     */
    private static @NotNull Iterable<List<Integer>> subsetIndices(int size, int elementCount) {
        BigInteger outputSize = MathUtils.binomialCoefficient(BigInteger.valueOf(elementCount), size);
        Iterable<Integer> range = IterableUtils.range(0, size - 1);
        int offset = elementCount - size;
        return () -> new EventuallyKnownSizeIterator<List<Integer>>() {
            private final @NotNull List<Integer> list = toList(range);
            private boolean first = true;
            {
                setOutputSize(outputSize);
            }

            @Override
            public @NotNull List<Integer> advance() {
                if (first) {
                    first = false;
                    return list;
                }
                for (int i = size - 1; i >= 0; i--) {
                    int j = list.get(i);
                    if (j != i + offset) {
                        for (int k = i; k < size; k++) {
                            j++;
                            list.set(k, j);
                        }
                        return list;
                    }
                }
                throw new IllegalStateException("unreachable");
            }
        };
    }

    /**
     * Returns an {@code Iterable} containing all sorted {@code List}s of a given length with elements from a given
     * {@code List}, with no repetitions. The {@code List}s are ordered lexicographically, matching the order given by
     * the original {@code List}. Does not support removal.
     *
     * <ul>
     *  <li>{@code size} cannot be negative.</li>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result is finite. All of its elements have the same length and are sorted. None are empty, unless the
     *  result consists entirely of one empty element.</li>
     * </ul>
     *
     * Length is <sub>|{@code xs}|</sub>C<sub>{@code size}</sub>
     *
     * @param size the length of the result lists
     * @param xs the {@code List} from which elements are selected
     * @param <T> the type of the given {@code List}'s elements
     * @return all sorted {@code List}s of a given length created from {@code xs} with no repetitions
     */
    @Override
    public @NotNull <T extends Comparable<T>> Iterable<List<T>> subsetsLex(int size, @NotNull List<T> xs) {
        if (xs.size() == 1) {
            T first = xs.get(0);
            first.compareTo(first); //catch incomparable single element; sort will catch the other cases
        }
        List<T> sorted = sort(xs);
        return map(is -> toList(map(sorted::get, is)), subsetIndices(size, xs.size()));
    }

    /**
     * Returns all sorted {@code Pair}s of distinct elements from an {@code Iterable}. The {@code Pair}s are ordered
     * lexicographically, matching the order given by the original {@code Iterable}s. Does not support removal.
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result contains all sorted distinct {@code Pair}s of elements from an {@code Iterable}.</li>
     * </ul>
     *
     * Length is <sub>|{@code xs}|</sub>C<sub>2</sub>
     *
     * @param xs an {@code Iterable}
     * @param <T> the type of the {@code Iterable}'s elements
     * @return all distinct unordered {@code Pair}s of elements from {@code xs}
     */
    @Override
    public @NotNull <T extends Comparable<T>> Iterable<Pair<T, T>> subsetPairsLex(@NotNull List<T> xs) {
        return map(list -> new Pair<>(list.get(0), list.get(1)), subsetsLex(2, xs));
    }

    /**
     * Returns all sorted {@code Triple}s of distinct elements from an {@code Iterable}. The {@code Triple}s are
     * ordered lexicographically, matching the order given by the original {@code Iterable}s. Does not support removal.
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result contains all sorted distinct {@code Triple}s of elements from an {@code Iterable}.</li>
     * </ul>
     *
     * Length is <sub>|{@code xs}|</sub>C<sub>3</sub>
     *
     * @param xs an {@code Iterable}
     * @param <T> the type of the {@code Iterable}'s elements
     * @return all distinct unordered {@code Triple}s of elements from {@code xs}
     */
    @Override
    public @NotNull <T extends Comparable<T>> Iterable<Triple<T, T, T>> subsetTriplesLex(@NotNull List<T> xs) {
        return map(list -> new Triple<>(list.get(0), list.get(1), list.get(2)), subsetsLex(3, xs));
    }

    /**
     * Returns all sorted {@code Quadruple}s of distinct elements from an {@code Iterable}. The {@code Quadruple}s are
     * ordered lexicographically, matching the order given by the original {@code Iterable}s. Does not support removal.
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result contains all sorted distinct {@code Quadruple}s of elements from an {@code Iterable}.</li>
     * </ul>
     *
     * Length is <sub>|{@code xs}|</sub>C<sub>4</sub>
     *
     * @param xs an {@code Iterable}
     * @param <T> the type of the {@code Iterable}'s elements
     * @return all distinct unordered {@code Quadruple}s of elements from {@code xs}
     */
    @Override
    public @NotNull <T extends Comparable<T>> Iterable<Quadruple<T, T, T, T>> subsetQuadruplesLex(
            @NotNull List<T> xs
    ) {
        return map(list -> new Quadruple<>(list.get(0), list.get(1), list.get(2), list.get(3)), subsetsLex(4, xs));
    }

    /**
     * Returns all sorted {@code Quintuple}s of distinct elements from an {@code Iterable}. The {@code Quintuple}s are
     * ordered lexicographically, matching the order given by the original {@code Iterable}s. Does not support removal.
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result contains all sorted distinct {@code Quintuple}s of elements from an {@code Iterable}.</li>
     * </ul>
     *
     * Length is <sub>|{@code xs}|</sub>C<sub>5</sub>
     *
     * @param xs an {@code Iterable}
     * @param <T> the type of the {@code Iterable}'s elements
     * @return all distinct unordered {@code Quintuple}s of elements from {@code xs}
     */
    @Override
    public @NotNull <T extends Comparable<T>> Iterable<Quintuple<T, T, T, T, T>> subsetQuintuplesLex(
            @NotNull List<T> xs
    ) {
        return map(
                list -> new Quintuple<>(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4)),
                subsetsLex(5, xs)
        );
    }

    /**
     * Returns all sorted {@code Sextuple}s of distinct elements from an {@code Iterable}. The {@code Sextuple}s are
     * ordered lexicographically, matching the order given by the original {@code Iterable}s. Does not support removal.
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result contains all sorted distinct {@code Sextuple}s of elements from an {@code Iterable}.</li>
     * </ul>
     *
     * Length is <sub>|{@code xs}|</sub>C<sub>6</sub>
     *
     * @param xs an {@code Iterable}
     * @param <T> the type of the {@code Iterable}'s elements
     * @return all distinct unordered {@code Sextuple}s of elements from {@code xs}
     */
    @Override
    public @NotNull <T extends Comparable<T>> Iterable<Sextuple<T, T, T, T, T, T>> subsetSextuplesLex(
            @NotNull List<T> xs
    ) {
        return map(
                list -> new Sextuple<>(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4), list.get(5)),
                subsetsLex(6, xs)
        );
    }

    /**
     * Returns all sorted {@code Septuple}s of distinct elements from an {@code Iterable}. The {@code Septuple}s are
     * ordered lexicographically, matching the order given by the original {@code Iterable}s. Does not support removal.
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result contains all sorted distinct {@code Septuple}s of elements from an {@code Iterable}.</li>
     * </ul>
     *
     * Length is <sub>|{@code xs}|</sub>C<sub>7</sub>
     *
     * @param xs an {@code Iterable}
     * @param <T> the type of the {@code Iterable}'s elements
     * @return all distinct unordered {@code Septuple}s of elements from {@code xs}
     */
    @Override
    public @NotNull <T extends Comparable<T>> Iterable<Septuple<T, T, T, T, T, T, T>> subsetSeptuplesLex(
            @NotNull List<T> xs
    ) {
        return map(
                list -> new Septuple<>(
                        list.get(0),
                        list.get(1),
                        list.get(2),
                        list.get(3),
                        list.get(4),
                        list.get(5),
                        list.get(6)
                ),
                subsetsLex(7, xs)
        );
    }

    /**
     * Returns an {@code Iterable} containing all sorted {@code String}s of a given length with characters from a given
     * {@code String}, with no repetitions. The {@code String}s are ordered lexicographically, matching the order given
     * by the original {@code String}. Does not support removal.
     *
     * <ul>
     *  <li>{@code size} cannot be negative.</li>
     *  <li>{@code s} cannot be null.</li>
     *  <li>The result is finite. All of its {@code String}s have the same length and are sorted. None are empty,
     *  unless the result consists entirely of one empty {@code String}.</li>
     * </ul>
     *
     * Length is <sub>|{@code s}|</sub>C<sub>{@code size}</sub>
     *
     * @param size the length of the result {@code String}
     * @param s the {@code String} from which characters are selected
     * @return all sorted {@code String}s of a given length created from {@code s} with no repetitions
     */
    @Override
    public @NotNull Iterable<String> stringSubsetsLex(int size, @NotNull String s) {
        return map(IterableUtils::charsToString, subsetsLex(size, toList(s)));
    }

    /**
     * Returns all sorted {@code List}s of natural numbers up to a given value with no repetitions. Does not support
     * removal.
     *
     * <ul>
     *  <li>{@code elementCount} cannot be negative.</li>
     *  <li>The result is in lexicographic order, contains only non-negative integers with no repetitions, and contains
     *  no repetitions. Each element is sorted.</li>
     * </ul>
     *
     * Length is 2<sup>elementCount</sup>
     *
     * @param elementCount one more than the largest possible value in the result {@code List}s
     * @return all sorted lists with no repetitions with elements from 0 to {@code elementCount}–1
     */
    private static @NotNull Iterable<List<Integer>> subsetIndices(int elementCount) {
        BigInteger outputSize = BigInteger.ONE.shiftLeft(elementCount);
        int limit = elementCount - 1;
        return () -> new EventuallyKnownSizeIterator<List<Integer>>() {
            private final @NotNull List<Integer> list = new ArrayList<>();
            private boolean first = true;
            {
                setOutputSize(outputSize);
            }

            @Override
            public @NotNull List<Integer> advance() {
                if (first) {
                    first = false;
                    return list;
                } else if (list.isEmpty()) {
                    list.add(0);
                    return list;
                }
                int last = last(list);
                if (last != limit) {
                    list.add(last + 1);
                } else {
                    int i = list.size() - 1;
                    list.remove(i);
                    i--;
                    list.set(i, list.get(i) + 1);
                }
                return list;
            }
        };
    }

    /**
     * Returns an {@code Iterable} containing all sorted {@code Lists}s with elements from a given {@code List} with no
     * repetitions. The {@code List}s are ordered lexicographically. Does not support removal.
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result is not null.</li>
     * </ul>
     *
     * Length is 2<sup>|{@code xs}|</sup>
     *
     * @param xs the {@code List} from which elements are selected
     * @param <T> the type of the given {@code List}'s elements
     * @return all sorted {@code List}s with no repetitions created from {@code xs}
     */
    @Override
    public @NotNull <T extends Comparable<T>> Iterable<List<T>> subsetsLex(@NotNull List<T> xs) {
        if (xs.size() == 1) {
            T first = xs.get(0);
            first.compareTo(first);
        }
        List<T> sorted = sort(xs);
        return map(is -> toList(map(sorted::get, is)), subsetIndices(xs.size()));
    }

    /**
     * Returns an {@code Iterable} containing all sorted {@code String}s with characters from a given {@code String}
     * with no repetitions. The {@code String}s are ordered lexicographically. Does not support removal.
     *
     * <ul>
     *  <li>{@code s} cannot be null.</li>
     *  <li>The result is not null.</li>
     * </ul>
     *
     * Length is 2<sup>|{@code s}|</sup>
     *
     * @param s the {@code String} from which elements are selected
     * @return all sorted {@code String}s with no repetitions created from {@code s}
     */
    @Override
    public @NotNull Iterable<String> stringSubsetsLex(@NotNull String s) {
        return map(IterableUtils::charsToString, subsetsLex(toList(s)));
    }

    /**
     * Returns all sorted {@code List}s of at least a given size containing natural numbers up to a given value with no
     * repetitions. Does not support removal.
     *
     * <ul>
     *  <li>{@code minSize} cannot be negative.</li>
     *  <li>{@code elementCount} cannot be negative.</li>
     *  <li>The result is in lexicographic order, contains only non-negative integers with no repetitions, and contains
     *  no repetitions. Each element is sorted.</li>
     * </ul>
     *
     * Length is Σ<sub>i={@code minSize}</sub><sup>n</sup><sub>{@code elementCount}</sub>C<sub>{@code i}</sub>
     *
     * @param minSize the minimum length of each of the result {@code List}s
     * @param elementCount one more than the largest possible value in the result {@code List}s
     * @return all sorted lists with no repetitions of length at least {@code minSize} with elements from 0 to
     * {@code elementCount}–1
     */
    private static @NotNull Iterable<List<Integer>> subsetIndicesAtLeast(int minSize, int elementCount) {
        BigInteger outputSize = MathUtils.subsetCount(minSize, BigInteger.valueOf(elementCount));
        Iterable<Integer> range = IterableUtils.range(0, minSize - 1);
        int limit = elementCount - 1;
        int offset = elementCount - minSize;
        return () -> new EventuallyKnownSizeIterator<List<Integer>>() {
            private final @NotNull List<Integer> list = toList(range);
            private boolean first = true;
            {
                setOutputSize(outputSize);
            }

            @Override
            public @NotNull List<Integer> advance() {
                if (first) {
                    first = false;
                    return list;
                } else if (list.isEmpty()) {
                    list.add(0);
                    return list;
                }
                int last = last(list);
                if (last != limit) {
                    list.add(last + 1);
                } else if (list.size() > minSize) {
                    int i = list.size() - 1;
                    list.remove(i);
                    i--;
                    list.set(i, list.get(i) + 1);
                } else {
                    for (int i = minSize - 2; i >= 0; i--) {
                        int k = list.get(i);
                        if (k != i + offset) {
                            for (int j = i; j < minSize; j++) {
                                k++;
                                list.set(j, k);
                            }
                            return list;
                        }
                    }
                }
                return list;
            }
        };
    }

    /**
     * Returns an {@code Iterable} containing all sorted {@code Lists}s with a minimum size with elements from a given
     * {@code List} with no repetitions. The {@code List}s are ordered lexicographically. Does not support removal.
     *
     * <ul>
     *  <li>{@code minSize} cannot be negative.</li>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result contains every sorted {@code List} (with a length greater than or equal to some minimum) of
     *  elements drawn from some sequence with no repetitions.</li>
     * </ul>
     *
     * Length is Σ<sub>i={@code minSize}</sub><sup>n</sup><sub>|{@code xs}|</sub>C<sub>{@code i}</sub>
     *
     * @param minSize the minimum length of the result {@code List}s
     * @param xs the {@code List} from which elements are selected
     * @param <T> the type of the given {@code Iterable}'s elements
     * @return all sorted {@code List}s with length at least {@code minSize} with no repetitions created from
     * {@code xs}
     */
    @Override
    public @NotNull <T extends Comparable<T>> Iterable<List<T>> subsetsLexAtLeast(int minSize, @NotNull List<T> xs) {
        if (minSize < 0) {
            throw new IllegalArgumentException("minSize cannot be negative. Invalid minSize: " + minSize);
        }
        if (xs.size() == 1) {
            T first = xs.get(0);
            first.compareTo(first);
        }
        List<T> sorted = sort(xs);
        return map(is -> toList(map(sorted::get, is)), subsetIndicesAtLeast(minSize, xs.size()));
    }

    /**
     * Returns an {@code Iterable} containing all sorted {@code String}s with a minimum size with elements from a given
     * {@code String} with no repetitions. The {@code String}s are ordered lexicographically. Does not support removal.
     *
     * <ul>
     *  <li>{@code minSize} cannot be negative.</li>
     *  <li>{@code s} cannot be null.</li>
     *  <li>The result contains every sorted {@code String} (with a length greater than or equal to some minimum) of
     *  characters drawn from some sequence with no repetitions.</li>
     * </ul>
     *
     * Length is Σ<sub>i={@code minSize}</sub><sup>n</sup><sub>|{@code s}|</sub>C<sub>{@code i}</sub>
     *
     * @param minSize the minimum length of the result {@code String}s
     * @param s the {@code String} from which elements are selected
     * @return all sorted {@code String}s with length at least {@code minSize} with no repetitions created from
     * {@code s}
     */
    @Override
    public @NotNull Iterable<String> stringSubsetsLexAtLeast(int minSize, @NotNull String s) {
        return map(IterableUtils::charsToString, subsetsLexAtLeast(minSize, toList(s)));
    }

    /**
     * Returns an {@code Iterable} containing all sorted {@code Lists}s with elements from a given {@code List} with no
     * repetitions. The {@code List}s are in shortlex order; that is, shorter {@code List}s precede longer
     * {@code List}s, and {@code List}s of the same length are ordered lexicographically. Does not support removal.
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result is not null.</li>
     * </ul>
     *
     * Length is Σ<sub>i=0</sub><sup>n</sup><sub>|{@code xs}|</sub>C<sub>{@code i}</sub>
     *
     * @param xs the {@code List} from which elements are selected
     * @param <T> the type of the given {@code List}'s elements
     * @return all sorted {@code List}s with no repetitions created from {@code xs}
     */
    @Override
    public @NotNull <T extends Comparable<T>> Iterable<List<T>> subsetsShortlex(@NotNull List<T> xs) {
        if (xs.size() == 1) {
            T first = xs.get(0);
            first.compareTo(first);
        }
        return concatMap(i -> subsetsLex(i, xs), range(0, xs.size()));
    }

    /**
     * Returns an {@code Iterable} containing all sorted {@code String}s with characters from a given {@code String}
     * with no repetitions. The {@code String}s are in shortlex order; that is, shorter {@code String}s precede longer
     * {@code String}s, and {@code String}s of the same length are ordered lexicographically. Does not support removal.
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result is not null.</li>
     * </ul>
     *
     * Length is Σ<sub>i=0</sub><sup>n</sup><sub>|{@code s}|</sub>C<sub>{@code i}</sub>
     *
     * @param s the {@code String} from which characters are selected
     * @return all sorted {@code Strings}s with no repetitions created from {@code s}
     */
    @Override
    public @NotNull Iterable<String> stringSubsetsShortlex(@NotNull String s) {
        return map(IterableUtils::charsToString, subsetsShortlex(toList(s)));
    }

    /**
     * Returns an {@code Iterable} containing all sorted {@code Lists}s with a minimum size with elements from a given
     * {@code List} with no repetitions. The {@code List}s are in shortlex order; that is, shorter {@code List}s
     * precede longer {@code List}s, and {@code List}s of the same length are ordered lexicographically. Does not
     * support removal.
     *
     * <ul>
     *  <li>{@code minSize} cannot be negative.</li>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result is not null.</li>
     * </ul>
     *
     * Length is Σ<sub>i={@code minSize}</sub><sup>n</sup><sub>|{@code xs}|</sub>C<sub>{@code i}</sub>
     *
     * @param minSize the minimum length of the result {@code List}s
     * @param xs the {@code List} from which elements are selected
     * @param <T> the type of the given {@code Iterable}'s elements
     * @return all sorted {@code List}s with length at least {@code minSize} with no repetitions created from
     * {@code xs}
     */
    @Override
    public @NotNull <T extends Comparable<T>> Iterable<List<T>> subsetsShortlexAtLeast(
            int minSize,
            @NotNull List<T> xs
    ) {
        if (minSize < 0) {
            throw new IllegalArgumentException("minSize cannot be negative. Invalid minSize: " + minSize);
        }
        return concatMap(i -> subsetsLex(i, xs), range(minSize, xs.size()));
    }

    /**
     * Returns an {@code Iterable} containing all sorted {@code String}s with a minimum size with elements from a given
     * {@code String} with no repetitions. The {@code String}s are in shortlex order; that is, shorter {@code String}s
     * precede longer {@code String}s, and {@code String}s of the same length are ordered lexicographically. Does not
     * support removal.
     *
     * <ul>
     *  <li>{@code minSize} cannot be negative.</li>
     *  <li>{@code s} cannot be null.</li>
     *  <li>The result is not null.</li>
     * </ul>
     *
     * Length is Σ<sub>i={@code minSize}</sub><sup>n</sup><sub>|{@code s}|</sub>C<sub>{@code i}</sub>
     *
     * @param minSize the minimum length of the result {@code String}s
     * @param s the {@code String} from which elements are selected
     * @return all sorted {@code String}s with length at least {@code minSize} with no repetitions created from
     * {@code s}
     */
    @Override
    public @NotNull Iterable<String> stringSubsetsShortlexAtLeast(int minSize, @NotNull String s) {
        return map(IterableUtils::charsToString, subsetsShortlexAtLeast(minSize, toList(s)));
    }

    /**
     * A helper method for generating subsets. Given an {@code Iterable} of lists of integers, {@code originalIndices},
     * this method reinterprets the indices to only generate sorted lists with no repetitions. For example, consider
     * the list [1, 0, 5, 3]. Let's select a list of length 4 from the characters 'a' through 'z' using these indices.
     * We'll use the cumulative sums of these indices plus the index, resulting in
     * [0 + 1, 1 + 1 + 0, 2 + 1 + 0 + 5, 3 + 1 + 0 + 5 + 3], or [1, 2, 7, 10]. This corresponds to [a, b, g, j].
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>{@code originalIndices} cannot contain any nulls, and all of its elements can only contain nonnegative
     *  {@code Integer}s.</li>
     *  <li>{@code requiredSize} cannot be null.</li>
     *  <li>{@code outputSizeFunction} cannot be null.</li>
     *  <li>If {@code xs} is finite, {@code outputSizeFunction} must return a either an empty {@code Optional} or a
     *  nonnegative integer when applied to {@code length(xs)}.</li>
     *  <li>The number of lists in {@code originalSize} that correspond to a valid subset must be at least
     *  {@code outputSizeFunction.apply(length(xs))} (or infinite if {@code xs} is infinite or
     *  {@code outputSizeFunction.apply(length(xs))} is empty).</li>
     * </ul>
     *
     * @param xs an {@code Iterable}
     * @param originalIndices an {@code Iterable} of lists, each list corresponding to a sorted list of elements from
     * {@code xs} with no repetitions
     * @param requiredSize the minimum size of any of the generated lists
     * @param outputSizeFunction The total number of generated lists as a function of the size of {@code xs}
     * @param <T> the type of the elements in {@code xs}
     * @return sorted lists of elements from {@code xs} with no repetitions
     */
    private static @NotNull <T extends Comparable<T>> Iterable<List<T>> subsetIndices(
            @NotNull Iterable<T> xs,
            @NotNull Iterable<List<Integer>> originalIndices,
            @NotNull Optional<Integer> requiredSize,
            @NotNull Function<Integer, BigInteger> outputSizeFunction
    ) {
        return () -> new EventuallyKnownSizeIterator<List<T>>() {
            private final @NotNull CachedIterator<T> cxs = new CachedIterator<>(xs);
            private final @NotNull Iterator<List<Integer>> indices = originalIndices.iterator();
            {
                if (requiredSize.isPresent() && requiredSize.get() != 0) {
                    if (!cxs.get(requiredSize.get() - 1).isPresent()) {
                        setOutputSize(BigInteger.ZERO);
                    }
                }
            }

            @Override
            public List<T> advance() {
                while (true) {
                    List<Integer> next = indices.next();
                    List<Integer> cumulativeIndices = next.isEmpty() ?
                            Collections.emptyList() :
                            toList(scanl1((x, y) -> x + y + 1, next));
                    if (!cumulativeIndices.isEmpty() && !cxs.get(last(cumulativeIndices)).isPresent()) {
                        continue;
                    }
                    List<T> output = toList(map(i -> cxs.get(i).get(), cumulativeIndices));
                    if (!outputSizeKnown() && cxs.knownSize().isPresent()) {
                        setOutputSize(outputSizeFunction.apply(cxs.knownSize().get()));
                    }
                    if (output.size() == 1) {
                        T first = output.get(0);
                        first.compareTo(first); //catch incomparable single element; sort will catch the other cases
                    }
                    return sort(output);
                }
            }
        };
    }

    /**
     * Returns an {@code Iterable} containing all sorted {@code List}s of a given length with elements from a given
     * {@code Iterable} with no repetitions. Does not support removal.
     *
     * <ul>
     *  <li>{@code size} cannot be negative.</li>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>All of the result's elements have the same length and are sorted. None are empty, unless the result
     *  consists entirely of one empty element.</li>
     * </ul>
     *
     * Length is <sub>|{@code xs}|</sub>C<sub>{@code size}</sub>
     *
     * @param size the length of the result lists
     * @param xs the {@code Iterable} from which elements are selected
     * @param <T> the type of the given {@code Iterable}'s elements
     * @return all sorted {@code List}s of a given length created from {@code xs} with no repetitions
     */
    @Override
    public @NotNull <T extends Comparable<T>> Iterable<List<T>> subsets(int size, @NotNull Iterable<T> xs) {
        if (size < 0) {
            throw new IllegalArgumentException("size cannot be negative. Invalid size: " + size);
        }
        if (size == 0) return Collections.singletonList(Collections.emptyList());
        if (isEmpty(xs)) return Collections.emptyList();
        return subsetIndices(
                xs,
                lists(size, naturalIntegers()),
                Optional.of(size),
                n -> MathUtils.binomialCoefficient(BigInteger.valueOf(n), size)
        );
    }

    /**
     * Returns all distinct unordered {@code Pair}s from an {@code Iterable}. Does not support removal.
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result contains all sorted {@code Pair}s of elements from an {@code Iterable} with no repetitions.</li>
     * </ul>
     *
     * Length is <sub>|{@code xs}|</sub>C<sub>2</sub>
     *
     * @param xs an {@code Iterable}
     * @param <T> the type of the {@code Iterable}'s elements
     * @return all distinct unordered {@code Pair}s of elements from {@code xs}
     */
    @Override
    public @NotNull <T extends Comparable<T>> Iterable<Pair<T, T>> subsetPairs(@NotNull Iterable<T> xs) {
        return map(list -> new Pair<>(list.get(0), list.get(1)), subsets(2, xs));
    }

    /**
     * Returns all distinct unordered {@code Triple}s from an {@code Iterable}. Does not support removal.
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result contains all sorted {@code Triple}s of elements from an {@code Iterable} with no
     *  repetitions.</li>
     * </ul>
     *
     * Length is <sub>|{@code xs}|</sub>C<sub>3</sub>
     *
     * @param xs an {@code Iterable}
     * @param <T> the type of the {@code Iterable}'s elements
     * @return all distinct unordered {@code Triple}s of elements from {@code xs}
     */
    @Override
    public @NotNull <T extends Comparable<T>> Iterable<Triple<T, T, T>> subsetTriples(@NotNull Iterable<T> xs) {
        return map(list -> new Triple<>(list.get(0), list.get(1), list.get(2)), subsets(3, xs));
    }

    /**
     * Returns all distinct unordered {@code Quadruple}s from an {@code Iterable}. Does not support removal.
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result contains all sorted {@code Quadruple}s of elements from an {@code Iterable} with no
     *  repetitions.</li>
     * </ul>
     *
     * Length is <sub>|{@code xs}|</sub>C<sub>4</sub>
     *
     * @param xs an {@code Iterable}
     * @param <T> the type of the {@code Iterable}'s elements
     * @return all distinct unordered {@code Quadruple}s of elements from {@code xs}
     */
    @Override
    public @NotNull <T extends Comparable<T>> Iterable<Quadruple<T, T, T, T>> subsetQuadruples(
            @NotNull Iterable<T> xs
    ) {
        return map(list -> new Quadruple<>(list.get(0), list.get(1), list.get(2), list.get(3)), subsets(4, xs));
    }

    /**
     * Returns all distinct unordered {@code Quintuple}s from an {@code Iterable}. Does not support removal.
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result contains all sorted {@code Quintuple}s of elements from an {@code Iterable} with no
     *  repetitions.</li>
     * </ul>
     *
     * Length is <sub>|{@code xs}|</sub>C<sub>5</sub>
     *
     * @param xs an {@code Iterable}
     * @param <T> the type of the {@code Iterable}'s elements
     * @return all distinct unordered {@code Quintuple}s of elements from {@code xs}
     */
    @Override
    public @NotNull <T extends Comparable<T>> Iterable<Quintuple<T, T, T, T, T>> subsetQuintuples(
            @NotNull Iterable<T> xs
    ) {
        return map(
                list -> new Quintuple<>(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4)),
                subsets(5, xs)
        );
    }

    /**
     * Returns all distinct unordered {@code Sextuple}s from an {@code Iterable}. Does not support removal.
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result contains all sorted {@code Sextuple}s of elements from an {@code Iterable} with no
     *  repetitions.</li>
     * </ul>
     *
     * Length is <sub>|{@code xs}|</sub>C<sub>6</sub>
     *
     * @param xs an {@code Iterable}
     * @param <T> the type of the {@code Iterable}'s elements
     * @return all distinct unordered {@code Sextuple}s of elements from {@code xs}
     */
    @Override
    public @NotNull <T extends Comparable<T>> Iterable<Sextuple<T, T, T, T, T, T>> subsetSextuples(
            @NotNull Iterable<T> xs
    ) {
        return map(
                list -> new Sextuple<>(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4), list.get(5)),
                subsets(6, xs)
        );
    }

    /**
     * Returns all distinct unordered {@code Septuple}s from an {@code Iterable}. Does not support removal.
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result contains all sorted {@code Septuple}s of elements from an {@code Iterable} with no
     *  repetitions.</li>
     * </ul>
     *
     * Length is <sub>|{@code xs}|</sub>C<sub>7</sub>
     *
     * @param xs an {@code Iterable}
     * @param <T> the type of the {@code Iterable}'s elements
     * @return all distinct unordered {@code Septuple}s of elements from {@code xs}
     */
    @Override
    public @NotNull <T extends Comparable<T>> Iterable<Septuple<T, T, T, T, T, T, T>> subsetSeptuples(
            @NotNull Iterable<T> xs
    ) {
        return map(
                list -> new Septuple<>(
                        list.get(0),
                        list.get(1),
                        list.get(2),
                        list.get(3),
                        list.get(4),
                        list.get(5),
                        list.get(6)
                ),
                subsets(7, xs)
        );
    }

    /**
     * Returns an {@code Iterable} containing all sorted {@code List}s with elements from a given {@code Iterable} with
     * no repetitions. Does not support removal.
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>All of the result's elements are sorted. None are empty, unless the result consists entirely of one empty
     *  element.</li>
     * </ul>
     *
     * Length is 2<sup>|{@code xs}|</sup>
     *
     * @param xs the {@code Iterable} from which elements are selected
     * @param <T> the type of the given {@code Iterable}'s elements
     * @return all distinct sorted {@code List}s created from {@code xs}
     */
    @Override
    public @NotNull <T extends Comparable<T>> Iterable<List<T>> subsets(@NotNull Iterable<T> xs) {
        return subsetIndices(xs, lists(naturalIntegers()), Optional.empty(), BigInteger.ONE::shiftLeft);
    }

    /**
     * Returns an {@code Iterable} containing all sorted {@code Lists}s with a minimum size with elements from a given
     * {@code List} with no repetitions. Does not support removal.
     *
     * <ul>
     *  <li>{@code minSize} cannot be negative.</li>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result contains every sorted {@code List} (with a length greater than or equal to some minimum) of
     *  elements drawn from some sequence.</li>
     * </ul>
     *
     * Length is Σ<sub>i={@code minSize}</sub><sup>n</sup><sub>|{@code xs}|</sub>C<sub>{@code i}</sub>
     *
     * @param minSize the minimum length of the result {@code List}s
     * @param xs the {@code List} from which elements are selected
     * @param <T> the type of the given {@code Iterable}'s elements
     * @return all distinct sorted {@code List}s with length at least {@code minSize} created from {@code xs}
     */
    @Override
    public @NotNull <T extends Comparable<T>> Iterable<List<T>> subsetsAtLeast(int minSize, @NotNull Iterable<T> xs) {
        return subsetIndices(
                xs,
                listsAtLeast(minSize, naturalIntegers()),
                Optional.of(minSize),
                n -> MathUtils.subsetCount(minSize, BigInteger.valueOf(n))
        );
    }

    /**
     * Given a {@code List} of {@code Integer}s, generates the Cartesian product of the indices [0, ..., i–1] for each
     * element i.
     *
     * <ul>
     *  <li>{@code listSizes} cannot contain any negative values or nulls.</li>
     *  <li>The result is the Cartesian product of sets of integers of the form [0, ..., i–1]</li>
     * </ul>
     *
     * Length is ∏{@code listSizes}
     *
     * @param listSizes a {@code List} of non-negative {@code Integer}s
     * @return ⨉<sub>i∈{@code listSizes}</sub>[0, ..., i–1]
     */
    private static @NotNull Iterable<List<Integer>> cartesianProductIndices(List<Integer> listSizes) {
        //noinspection Convert2MethodRef
        BigInteger outputSize = productBigInteger(map(i -> BigInteger.valueOf(i), listSizes));
        int limit = listSizes.size() - 1;
        return () -> new EventuallyKnownSizeIterator<List<Integer>>() {
            private final @NotNull List<Integer> list = toList(replicate(listSizes.size(), 0));
            private boolean first = true;
            {
                setOutputSize(outputSize);
            }

            @Override
            public @NotNull List<Integer> advance() {
                if (first) {
                    first = false;
                    return list;
                }
                for (int i = limit; i >= 0; i--) {
                    int j = list.get(i) + 1;
                    if (j != listSizes.get(i)) {
                        list.set(i, j);
                        return list;
                    }
                    list.set(i, 0);
                }
                throw new IllegalStateException("unreachable");
            }
        };
    }

    /**
     * Returns an {@code Iterable} containing the Cartesian product of a {@code List} of {@code List}s.
     *
     * <ul>
     *  <li>None of the {@code List}s in {@code xss} can be null.</li>
     *  <li>The result is the Cartesian product of a {@code List} of {@code List}s.</li>
     * </ul>
     *
     * Length is ∏<sub>{@code xs}∈{@code xss}</sub>{@code xs.size()}
     *
     * @param xss a {@code List} of {@code List}s
     * @param <T> the type of the {@code List}s' elements
     * @return ⨉{@code xss}
     */
    @Override
    public @NotNull <T> Iterable<List<T>> cartesianProduct(@NotNull List<List<T>> xss) {
        return map(is -> toList(zipWith(List::get, xss, is)), cartesianProductIndices(toList(map(List::size, xss))));
    }

    /**
     * Returns an {@code Iterable} containing all unique sublists of a given {@code List}. Does not support removal.
     *
     * <ul>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result consists of all unique sublists of a {@code List}.</li>
     * </ul>
     *
     * Length is between {@code xs}+1 and <sub>|{@code xs}|+1</sub>C<sub>2</sub>+1, inclusive.
     *
     * @param xs a {@code List}
     * @param <T> the type of the given {@code List}'s elements
     * @return all unique sublists of {@code xs}
     */
    @Override
    public @NotNull <T> Iterable<List<T>> sublists(@NotNull List<T> xs) {
        return nub(super.sublists(xs));
    }

    /**
     * Returns an {@code Iterable} containing all unique substrings of a given {@code String}. Does not support
     * removal.
     *
     * <ul>
     *  <li>{@code s} cannot be null.</li>
     *  <li>The result consists of all unique substrings of a {@code String}.</li>
     * </ul>
     *
     * Length is between {@code s}+1 and <sub>|{@code s}|+1</sub>C<sub>2</sub>+1, inclusive.
     *
     * @param s a {@code String}
     * @return all unique substrings of {@code s}
     */
    @Override
    public @NotNull Iterable<String> substrings(@NotNull String s) {
        return nub(super.substrings(s));
    }

    /**
     * Returns an {@code Iterable} containing all {@code List}s from an {@code Iterable} of elements {@code xs} which
     * contain a particular element. {@code xs} may or may not contain the element. Does not support removal.
     *
     * <ul>
     *  <li>{@code x} may be any value of type {@code T}, or null.</li>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>{@code xs} cannot be infinite and only contain copies of {@code x}.</li>
     *  <li>The result contains no nulls, and every {@code List} contains one particular element.</li>
     * </ul>
     *
     * Length is 1 if {@code xs} is empty, infinite otherwise
     *
     * @param x an element that the output {@code List}s must contain
     * @param xs a {@code List}
     * @param <T> the type of the elements in {@code xs}
     * @return all {@code List}s containing {@code x} and possibly members of {@code xs}
     */
    @Override
    public @NotNull <T> Iterable<List<T>> listsWithElement(@Nullable T x, @NotNull Iterable<T> xs) {
        return map(
                p -> toList(concat(p.a, cons(x, p.b))),
                pairs(lists(filter(y -> !Objects.equals(y, x), xs)), lists(xs))
        );
    }

    /**
     * Returns an {@code Iterable} containing all distint unordered {@code List}s from an {@code Iterable} of elements
     * {@code xs} which contain a particular element. {@code xs} may or may not contain the element. Does not support
     * removal.
     *
     * <ul>
     *   <li>{@code x} may be any value of type {@code T}, or null.</li>
     *   <li>{@code xs} cannot be null.</li>
     *   <li>{@code xs} cannot be infinite and only contain copies of {@code x}.</li>
     *   <li>The result contains no nulls, and every {@code List} contains one particular element.</li>
     * </ul>
     *
     * Length is 2<sup>|{@code xs}\{{@code x}}|</sup>
     *
     * @param x an element that the output {@code List}s must contain
     * @param xs a {@code List}
     * @param <T> the type of the elements in {@code xs}
     * @return all distinct unordered {@code List}s containing {@code x} and possibly members of {@code xs}
     */
    @Override
    public @NotNull <T extends Comparable<T>> Iterable<List<T>> subsetsWithElement(
            T x,
            @NotNull Iterable<T> xs
    ) {
        x.compareTo(x);
        return map(ys -> sort(cons(x, ys)), subsets(filter(y -> !Objects.equals(y, x), xs)));
    }

    /**
     * Returns an {@code Iterable} containing all {@code List}s from an {@code Iterable} of elements {@code xs} which
     * contain a particular at least one {@code List} from {@code sublists}. The {@code List}s in {@code sublists} are
     * not necessarily made up of elements from {@code xs}. Does not support removal.
     *
     * <ul>
     *  <li>{@code sublists} cannot be null, cannot contain nulls, and cannot be one element repeating forever.</li>
     *  <li>{@code xs} cannot be null.</li>
     *  <li>The result contains no nulls and no repetitions.</li>
     * </ul>
     *
     * Length is 0 if {@code sublists} is empty, 1 if {@code sublists} only contains an empty {@code List} and
     * {@code xs} is empty, and infinite otherwise
     *
     * @param sublists {@code List}s, at least one of which must be contained in each result {@code List}
     * @param xs a {@code List}
     * @param <T> the type of the elements in {@code xs}
     * @return all {@code List}s containing {@code x} and at least one {@code List} from {@code sublists}
     */
    @Override
    public @NotNull <T> Iterable<List<T>> listsWithSublists(
            @NotNull Iterable<List<T>> sublists,
            @NotNull Iterable<T> xs
    ) {
        Iterable<List<T>> nublists = nub(sublists);
        if (isEmpty(sublists)) {
            return Collections.emptyList();
        }
        Iterable<List<T>> lists = lists(nub(xs));
        if (!lengthAtLeast(2, nublists) && head(sublists).isEmpty()) {
            return lists;
        }
        return nub(map(t -> toList(concat(Arrays.asList(t.a, t.b, t.c))), triples(lists, nublists, lists)));
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
