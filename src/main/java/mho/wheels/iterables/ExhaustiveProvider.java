package mho.wheels.iterables;

import mho.wheels.math.Combinatorics;
import mho.wheels.math.MathUtils;
import mho.wheels.misc.FloatingPointUtils;
import mho.wheels.ordering.Ordering;
import mho.wheels.structures.*;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.ordering.Ordering.*;

/**
 * An {@code ExhaustiveProvider} produces {@code Iterable}s that generate some set of values in a specified order.
 * There is only a single instance of this class.
 */
@SuppressWarnings("ConstantConditions")
public final class ExhaustiveProvider extends IterableProvider {
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
     * A {@link List} that contains both {@link boolean}s.
     *
     * Length is 2
     */
    @Override
    public @NotNull List<Boolean> booleans() {
        return Arrays.asList(false, true);
    }

    /**
     * A {@code List} that contains all {@link Ordering}s in increasing order.
     *
     * Length is 3
     *
     * @return the {@code List} described above.
     */
    public @NotNull List<Ordering> orderingsIncreasing() {
        return Arrays.asList(LT, EQ, GT);
    }

    /**
     * A {@code List} that contains all {@code Ordering}s.
     *
     * Length is 3
     */
    @Override
    public @NotNull List<Ordering> orderings() {
        return Arrays.asList(EQ, LT, GT);
    }

    /**
     * A {@code List} that contains all {@link RoundingMode}s.
     *
     * Length is 8
     */
    @Override
    public @NotNull List<RoundingMode> roundingModes() {
        return Arrays.asList(
                RoundingMode.UNNECESSARY,
                RoundingMode.UP,
                RoundingMode.DOWN,
                RoundingMode.CEILING,
                RoundingMode.FLOOR,
                RoundingMode.HALF_UP,
                RoundingMode.HALF_DOWN,
                RoundingMode.HALF_EVEN
        );
    }

    @Override
    public @NotNull Iterable<Byte> rangeUp(byte a) {
        if (a >= 0) {
            return IterableUtils.rangeUp(a);
        } else {
            return mux(Arrays.asList(IterableUtils.rangeUp((byte) 0), IterableUtils.rangeBy((byte) -1, (byte) -1, a)));
        }
    }

    @Override
    public @NotNull Iterable<Short> rangeUp(short a) {
        if (a >= 0) {
            return IterableUtils.rangeUp(a);
        } else {
            return mux(
                    Arrays.asList(IterableUtils.rangeUp((short) 0), IterableUtils.rangeBy((short) -1, (short) -1, a))
            );
        }
    }

    @Override
    public @NotNull Iterable<Integer> rangeUp(int a) {
        if (a >= 0) {
            return IterableUtils.rangeUp(a);
        } else {
            return mux(Arrays.asList(IterableUtils.rangeUp(0), IterableUtils.rangeBy(-1, -1, a)));
        }
    }

    @Override
    public @NotNull Iterable<Long> rangeUp(long a) {
        if (a >= 0) {
            return IterableUtils.rangeUp(a);
        } else {
            return mux(Arrays.asList(IterableUtils.rangeUp(0L), IterableUtils.rangeBy(-1L, -1L, a)));
        }
    }

    @Override
    public @NotNull Iterable<BigInteger> rangeUp(@NotNull BigInteger a) {
        if (a.signum() != -1) {
            return IterableUtils.rangeUp(a);
        } else {
            return mux(
                    Arrays.asList(
                            IterableUtils.rangeUp(BigInteger.ZERO),
                            IterableUtils.rangeBy(BigInteger.valueOf(-1), BigInteger.valueOf(-1), a)
                    )
            );
        }
    }

    @Override
    public @NotNull Iterable<Character> rangeUp(char a) {
        return IterableUtils.rangeUp(a);
    }

    @Override
    public @NotNull Iterable<Byte> rangeDown(byte a) {
        if (a <= 0) {
            return IterableUtils.rangeBy(a, (byte) -1);
        } else {
            return mux(Arrays.asList(IterableUtils.range((byte) 0, a), IterableUtils.rangeBy((byte) -1, (byte) -1)));
        }
    }

    @Override
    public @NotNull Iterable<Short> rangeDown(short a) {
        if (a <= 0) {
            return IterableUtils.rangeBy(a, (short) -1);
        } else {
            return mux(
                    Arrays.asList(IterableUtils.range((short) 0, a), IterableUtils.rangeBy((short) -1, (short) -1))
            );
        }
    }

    @Override
    public @NotNull Iterable<Integer> rangeDown(int a) {
        if (a <= 0) {
            return IterableUtils.rangeBy(a, -1);
        } else {
            return mux(Arrays.asList(IterableUtils.range(0, a), IterableUtils.rangeBy(-1, -1)));
        }
    }

    @Override
    public @NotNull Iterable<Long> rangeDown(long a) {
        if (a <= 0) {
            return IterableUtils.rangeBy(a, -1L);
        } else {
            return mux(Arrays.asList(IterableUtils.range(0L, a), IterableUtils.rangeBy(-1L, -1L)));
        }
    }

    @Override
    public @NotNull Iterable<BigInteger> rangeDown(@NotNull BigInteger a) {
        if (a.signum() != 1) {
            return IterableUtils.rangeBy(a, BigInteger.valueOf(-1));
        } else {
            return mux(Arrays.asList(IterableUtils.range(BigInteger.ZERO, a), IterableUtils.rangeBy(BigInteger.valueOf(-1), BigInteger.valueOf(-1))));
        }
    }

    @Override
    public @NotNull Iterable<Character> rangeDown(char a) {
        return IterableUtils.range('\0', a);
    }

    @Override
    public @NotNull Iterable<Byte> range(byte a, byte b) {
        if (a >= 0 && b >= 0) {
            return IterableUtils.range(a, b);
        } else if (a < 0 && b < 0) {
            return IterableUtils.rangeBy(b, (byte) -1, a);
        } else {
            return mux(
                    Arrays.asList(IterableUtils.range((byte) 0, b), IterableUtils.rangeBy((byte) -1, (byte) -1, a))
            );
        }
    }

    @Override
    public @NotNull Iterable<Short> range(short a, short b) {
        if (a >= 0 && b >= 0) {
            return IterableUtils.range(a, b);
        } else if (a < 0 && b < 0) {
            return IterableUtils.rangeBy(b, (short) -1, a);
        } else {
            return mux(
                    Arrays.asList(IterableUtils.range((short) 0, b), IterableUtils.rangeBy((short) -1, (short) -1, a))
            );
        }
    }

    @Override
    public @NotNull Iterable<Integer> range(int a, int b) {
        if (a >= 0 && b >= 0) {
            return IterableUtils.range(a, b);
        } else if (a < 0 && b < 0) {
            return IterableUtils.rangeBy(b, -1, a);
        } else {
            return mux(Arrays.asList(IterableUtils.range(0, b), IterableUtils.rangeBy(-1, -1, a)));
        }
    }

    public @NotNull Iterable<Long> range(long a, long b) {
        if (a >= 0 && b >= 0) {
            return IterableUtils.range(a, b);
        } else if (a < 0 && b < 0) {
            return IterableUtils.rangeBy(b, (byte) -1, a);
        } else {
            return mux(Arrays.asList(IterableUtils.range(0L, b), IterableUtils.rangeBy(-1L, -1L, a)));
        }
    }

    @Override
    public @NotNull Iterable<BigInteger> range(@NotNull BigInteger a, @NotNull BigInteger b) {
        if (a.signum() != -1 && b.signum() != -1) {
            return IterableUtils.range(a, b);
        } else if (a.signum() == -1 && b.signum() == -1) {
            return IterableUtils.rangeBy(b, BigInteger.valueOf(-1), a);
        } else {
            return mux(
                    Arrays.asList(
                            IterableUtils.range(BigInteger.ZERO, b),
                            IterableUtils.rangeBy(BigInteger.valueOf(-1), BigInteger.valueOf(-1), a)
                    )
            );
        }
    }

    @Override
    public @NotNull Iterable<Character> range(char a, char b) {
        return IterableUtils.range(a, b);
    }

    @Override
    public @NotNull <T> List<T> uniformSample(@NotNull List<T> xs) {
        return xs;
    }

    @Override
    public @NotNull Iterable<Character> uniformSample(@NotNull String s) {
        return fromString(s);
    }

    /**
     * An {@code Iterable} that contains all {@link Byte}s in increasing order. Does not support removal.
     *
     * Length is 2<sup>8</sup> = 256
     *
     * @return the {@code Iterable} described above.
     */
    public @NotNull Iterable<Byte> bytesIncreasing() {
        return IterableUtils.rangeUp(Byte.MIN_VALUE);
    }

    /**
     * An {@code Iterable} that contains all {@link Short}s in increasing order. Does not support removal.
     *
     * Length is 2<sup>16</sup> = 65,536
     *
     * @return the {@code Iterable} described above.
     */
    public @NotNull Iterable<Short> shortsIncreasing() {
        return IterableUtils.rangeUp(Short.MIN_VALUE);
    }

    /**
     * An {@code Iterable} that contains all {@link Integer}s in increasing order. Does not support removal.
     *
     * Length is 2<sup>32</sup> = 4,294,967,296
     *
     * @return the {@code Iterable} described above.
     */
    public @NotNull Iterable<Integer> integersIncreasing() {
        return IterableUtils.rangeUp(Integer.MIN_VALUE);
    }

    /**
     * An {@code Iterable} that contains all {@link Long}s in increasing order. Does not support removal.
     *
     * Length is 2<sup>64</sup> = 18,446,744,073,709,551,616
     *
     * @return the {@code Iterable} described above.
     */
    public @NotNull Iterable<Long> longsIncreasing() {
        return IterableUtils.rangeUp(Long.MIN_VALUE);
    }

    /**
     * An {@code Iterable} that contains all positive {@code Byte}s. Does not support removal.
     *
     * Length is 2<sup>7</sup>–1 = 127
     */
    @Override
    public @NotNull Iterable<Byte> positiveBytes() {
        return IterableUtils.rangeUp((byte) 1);
    }

    /**
     * An {@code Iterable} that contains all positive {@code Short}s. Does not support removal.
     *
     * Length is 2<sup>15</sup>–1 = 32,767
     */
    @Override
    public @NotNull Iterable<Short> positiveShorts() {
        return IterableUtils.rangeUp((short) 1);
    }

    /**
     * An {@code Iterable} that contains all positive {@code Integer}s. Does not support removal.
     *
     * Length is 2<sup>31</sup>–1 = 2,147,483,647
     */
    @Override
    public @NotNull Iterable<Integer> positiveIntegers() {
        return IterableUtils.rangeUp(1);
    }

    /**
     * An {@code Iterable} that contains all positive {@code Long}s. Does not support removal.
     *
     * Length is 2<sup>63</sup>–1 = 9,223,372,036,854,775,807
     */
    @Override
    public @NotNull Iterable<Long> positiveLongs() {
        return IterableUtils.rangeUp(1L);
    }

    /**
     * An {@code Iterable} that contains all positive {@link BigInteger}s. Does not support removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<BigInteger> positiveBigIntegers() {
        return IterableUtils.rangeUp(BigInteger.ONE);
    }

    /**
     * An {@code Iterable} that contains all negative {@code Byte}s. Does not support removal.
     *
     * Length is 2<sup>7</sup> = 128
     */
    @Override
    public @NotNull Iterable<Byte> negativeBytes() {
        return IterableUtils.rangeBy((byte) -1, (byte) -1);
    }

    /**
     * An {@code Iterable} that contains all negative {@code Short}s. Does not support removal.
     *
     * Length is 2<sup>15</sup> = 32,768
     */
    @Override
    public @NotNull Iterable<Short> negativeShorts() {
        return IterableUtils.rangeBy((short) -1, (short) -1);
    }

    /**
     * An {@code Iterable} that contains all negative {@code Integer}s. Does not support removal.
     *
     * Length is 2<sup>31</sup> = 2,147,483,648
     */
    @Override
    public @NotNull Iterable<Integer> negativeIntegers() {
        return IterableUtils.rangeBy(-1, -1);
    }

    /**
     * An {@code Iterable} that contains all negative {@code Long}s. Does not support removal.
     *
     * Length is 2<sup>63</sup> = 9,223,372,036,854,775,808
     */
    @Override
    public @NotNull Iterable<Long> negativeLongs() {
        return IterableUtils.rangeBy(-1L, -1L);
    }

    /**
     * An {@code Iterable} that contains all negative {@code BigInteger}s. Does not support removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<BigInteger> negativeBigIntegers() {
        return IterableUtils.rangeBy(BigInteger.valueOf(-1), BigInteger.valueOf(-1));
    }

    /**
     * An {@code Iterable} that contains all natural {@code Byte}s. Does not support removal.
     *
     * Length is 2<sup>7</sup> = 128
     */
    @Override
    public @NotNull Iterable<Byte> naturalBytes() {
        return IterableUtils.rangeUp((byte) 0);
    }

    /**
     * An {@code Iterable} that contains all natural {@code Short}s (including 0). Does not support removal.
     *
     * Length is 2<sup>15</sup> = 32,768
     */
    @Override
    public @NotNull Iterable<Short> naturalShorts() {
        return IterableUtils.rangeUp((short) 0);
    }

    /**
     * An {@code Iterable} that contains all natural {@code Integer}s (including 0). Does not support removal.
     *
     * Length is 2<sup>31</sup> = 2,147,483,648
     */
    @Override
    public @NotNull Iterable<Integer> naturalIntegers() {
        return IterableUtils.rangeUp(0);
    }

    /**
     * An {@code Iterable} that contains all natural {@code Long}s (including 0). Does not support removal.
     *
     * Length is 2<sup>63</sup> = 9,223,372,036,854,775,808
     */
    @Override
    public @NotNull Iterable<Long> naturalLongs() {
        return IterableUtils.rangeUp(0L);
    }

    /**
     * An {@code Iterable} that contains all natural {@code BigInteger}s (including 0). Does not support removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<BigInteger> naturalBigIntegers() {
        return rangeUp(BigInteger.ZERO);
    }

    /**
     * An {@code Iterable} that contains all {@code Byte}s. Does not support removal.
     *
     * Length is 2<sup>8</sup> = 128
     */
    @Override
    public @NotNull Iterable<Byte> bytes() {
        return cons((byte) 0, mux(Arrays.asList(positiveBytes(), negativeBytes())));
    }

    /**
     * An {@code Iterable} that contains all {@code Short}s. Does not support removal.
     *
     * Length is 2<sup>16</sup> = 65,536
     */
    @Override
    public @NotNull Iterable<Short> shorts() {
        return cons((short) 0, mux(Arrays.asList(positiveShorts(), negativeShorts())));
    }

    /**
     * An {@code Iterable} that contains all {@code Integer}s. Does not support removal.
     *
     * Length is 2<sup>32</sup> = 4,294,967,296
     */
    @Override
    public @NotNull Iterable<Integer> integers() {
        return cons(0, mux(Arrays.asList(positiveIntegers(), negativeIntegers())));
    }

    /**
     * An {@code Iterable} that contains all {@code Long}s. Does not support removal.
     *
     * Length is 2<sup>64</sup> = 18,446,744,073,709,551,616
     */
    @Override
    public @NotNull Iterable<Long> longs() {
        return cons(0L, mux(Arrays.asList(positiveLongs(), negativeLongs())));
    }

    /**
     * An {@code Iterable} that contains all {@code BigInteger}s. Does not support removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<BigInteger> bigIntegers() {
        return cons(BigInteger.ZERO, mux(Arrays.asList(positiveBigIntegers(), negativeBigIntegers())));
    }

    /**
     * An {@code Iterable} that contains all ASCII {@link Character}s in increasing order. Does not support
     * removal.
     *
     * Length is 2<sup>7</sup> = 128
     *
     * @return the {@code Iterable} described above.
     */
    public @NotNull Iterable<Character> asciiCharactersIncreasing() {
        return range((char) 0, (char) 127);
    }

    /**
     * An {@code Iterable} that contains all ASCII {@code Character}s in an order which places "friendly" characters
     * first. Does not support removal.
     *
     * Length is 2<sup>7</sup> = 128
     *
     * @return the {@code Iterable} described above.
     */
    @Override
    public @NotNull Iterable<Character> asciiCharacters() {
        return concat(Arrays.asList(
                range('a', 'z'),
                range('A', 'Z'),
                range('0', '9'),
                range('!', '/'),            // printable non-alphanumeric ASCII...
                range(':', '@'),            // ...
                range('[', '`'),            // ...
                range('{', '~'),            // ...
                range((char) 0, (char) 32), // non-printable and whitespace ASCII
                Arrays.asList((char) 127)   // DEL
        ));
    }

    /**
     * An {@code Iterable} that contains all {@code Character}s in increasing order. Does not support removal.
     *
     * Length is 2<sup>16</sup> = 65,536
     *
     * @return the {@code Iterable} described above.
     */
    public @NotNull Iterable<Character> charactersIncreasing() {
        return range(Character.MIN_VALUE, Character.MAX_VALUE);
    }

    /**
     * An {@code Iterable} that contains all {@code Character}s in an order which places "friendly" characters
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
                range((char) 0, (char) 32), // non-printable and whitespace ASCII
                rangeUp((char) 127)           // DEL and non-ASCII
        ));
    }

    /**
     * An {@code Iterable} that contains all ordinary (neither {@code NaN} nor infinite) positive {@link float}s in
     * increasing order. Does not support removal.
     *
     * Length is 2<sup>31</sup>–2<sup>23</sup>–1 = 2,139,095,039
     *
     * @return the {@code Iterable} described above.
     */
    public @NotNull Iterable<Float> positiveOrdinaryFloatsIncreasing() {
        return stopAt(f -> f == Float.MAX_VALUE, iterate(FloatingPointUtils::successor, Float.MIN_VALUE));
    }

    /**
     * An {@code Iterable} that contains all ordinary (neither {@code NaN} nor infinite) negative {@code float}s in
     * increasing order. Negative zero is not included. Does not support removal.
     *
     * Length is 2<sup>31</sup>–2<sup>23</sup>–1 = 2,139,095,039
     *
     * @return the {@code Iterable} described above.
     */
    public @NotNull Iterable<Float> negativeOrdinaryFloatsIncreasing() {
        return stopAt(f -> f == -Float.MIN_VALUE, iterate(FloatingPointUtils::successor, -Float.MAX_VALUE));
    }

    /**
     * An {@code Iterable} that contains all ordinary (neither {@code NaN} nor infinite) {@code float}s in increasing
     * order. Negative zero is not included, but positive zero is. Does not support removal.
     *
     * Length is 2<sup>32</sup>–2<sup>24</sup>-1 = 4,278,190,079
     *
     * @return the {@code Iterable} described above.
     */
    public @NotNull Iterable<Float> ordinaryFloatsIncreasing() {
        //noinspection RedundantCast
        return concat((Iterable<Iterable<Float>>) Arrays.asList(
                stopAt(f -> f == -Float.MIN_VALUE, iterate(FloatingPointUtils::successor, -Float.MAX_VALUE)),
                Arrays.asList(0.0f),
                stopAt(f -> f == Float.MAX_VALUE, iterate(FloatingPointUtils::successor, Float.MIN_VALUE))
        ));
    }

    /**
     * An {@code Iterable} that contains all {@code float}s in increasing order. {@code NaN} is traditionally unordered,
     * but here it is placed between negative zero and positive zero. Does not support removal.
     *
     * Length is 2<sup>32</sup>–2<sup>24</sup>+3 = 4,278,190,083
     *
     * @return the {@code Iterable} described above.
     */
    public @NotNull Iterable<Float> floatsIncreasing() {
        //noinspection RedundantCast
        return concat((Iterable<Iterable<Float>>) Arrays.asList(
                stopAt(f -> f == -Float.MIN_VALUE, iterate(FloatingPointUtils::successor, Float.NEGATIVE_INFINITY)),
                Arrays.asList(-0.0f, Float.NaN, 0.0f),
                stopAt(f -> f == Float.POSITIVE_INFINITY, iterate(FloatingPointUtils::successor, Float.MIN_VALUE))
        ));
    }

    /**
     * An {@code Iterable} that contains all possible positive {@code float} mantissas. A {@code float}'s mantissa is
     * the unique odd integer that, when multiplied by a power of 2, equals the {@code float}. Does not support
     * removal.
     *
     * Length is 2<sup>23</sup> = 8,388,608
     */
    private static final @NotNull Iterable<Integer> FLOAT_MANTISSAS = IterableUtils.rangeBy(1, 2, 1 << 24);

    /**
     * An {@code Iterable} that contains all possible float exponents. A positive float's exponent is the base-2
     * logarithm of the float divided by its mantissa. Does not support removal.
     *
     * Length is 2<sup>8</sup>+23–2 = 277
     */
    private static final @NotNull Iterable<Integer> FLOAT_EXPONENTS = cons(
            0,
            mux(Arrays.asList(INSTANCE.range(1, 127), IterableUtils.rangeBy(-1, -1, -149)))
    );

    /**
     * An {@code Iterable} that contains all ordinary (neither {@code NaN} nor infinite) positive {@code float}s. Does
     * not support removal.
     *
     * Length is 2<sup>31</sup>–2<sup>23</sup>–1 = 2,139,095,039
     */
    @Override
    public @NotNull Iterable<Float> positiveOrdinaryFloats() {
        return map(
                Optional::get,
                filter(
                        Optional::isPresent,
                        map(p -> FloatingPointUtils.floatFromME(p.a, p.b), pairs(FLOAT_MANTISSAS, FLOAT_EXPONENTS))
                )
        );
    }

    /**
     * An {@code Iterable} that contains all ordinary (neither {@code NaN} nor infinite) negative {@code float}s.
     * Negative zero is not included. Does not support removal.
     *
     * Length is 2<sup>31</sup>–2<sup>23</sup>–1 = 2,139,095,039
     */
    @Override
    public @NotNull Iterable<Float> negativeOrdinaryFloats() {
        return map(f -> -f, positiveOrdinaryFloats());
    }

    /**
     * An {@code Iterable} that contains all ordinary (neither {@code NaN} nor infinite) floats. Negative zero is not
     * included, but positive zero is. Does not support removal.
     *
     * Length is 2<sup>32</sup>–2<sup>24</sup>–1 = 4,278,190,079
     */
    @Override
    public @NotNull Iterable<Float> ordinaryFloats() {
        return cons(0.0f, mux(Arrays.asList(positiveOrdinaryFloats(), negativeOrdinaryFloats())));
    }

    /**
     * An {@code Iterable} that contains all {@code float}s. Does not support removal.
     *
     * Length is 2<sup>32</sup>–2<sup>24</sup>+3 = 4,278,190,083
     */
    @Override
    public @NotNull Iterable<Float> floats() {
        return concat(
                Arrays.asList(Float.NaN, Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY, 0.0f, -0.0f),
                tail(ordinaryFloats())
        );
    }

    /**
     * @return An {@code Iterable} that contains all ordinary (neither {@code NaN} nor infinite) positive
     * {@link double}s in increasing order. Does not support removal.
     *
     * Length is 2<sup>63</sup>–2<sup>52</sup>–1 = 9,218,868,437,227,405,311
     */
    public @NotNull Iterable<Double> positiveOrdinaryDoublesIncreasing() {
        return stopAt(d -> d == Double.MAX_VALUE, iterate(FloatingPointUtils::successor, Double.MIN_VALUE));
    }

    /**
     * @return An {@code Iterable} that contains all ordinary (neither {@code NaN} nor infinite) negative
     * {@code double}s in increasing order. Negative zero is not included. Does not support removal.
     *
     * Length is 2<sup>63</sup>–2<sup>52</sup>–1 = 9,218,868,437,227,405,311
     */
    public @NotNull Iterable<Double> negativeOrdinaryDoublesIncreasing() {
        return stopAt(d -> d == -Double.MIN_VALUE, iterate(FloatingPointUtils::successor, -Double.MAX_VALUE));
    }

    /**
     * @return An {@code Iterable} that contains all ordinary (neither {@code NaN} nor infinite) {@code double}s in
     * increasing order. Negative zero is not included, but positive zero is. Does not support removal.
     *
     * Length is 2<sup>64</sup>–2<sup>53</sup>–1 = 18,437,736,874,454,810,623
     */
    public @NotNull Iterable<Double> ordinaryDoublesIncreasing() {
        //noinspection RedundantCast
        return concat((Iterable<Iterable<Double>>) Arrays.asList(
                stopAt(d -> d == -Double.MIN_VALUE, iterate(FloatingPointUtils::successor, -Double.MAX_VALUE)),
                Arrays.asList(0.0),
                stopAt(d -> d == Double.MAX_VALUE, iterate(FloatingPointUtils::successor, Double.MIN_VALUE))
        ));
    }

    /**
     * @return An {@code Iterable} that contains all {@code double}s in increasing order. {@code NaN} is traditionally
     * unordered, but here it is placed between negative zero and positive zero. Does not support removal.
     *
     * Length is 2<sup>64</sup>–2<sup>53</sup>+3 = 18,437,736,874,454,810,627
     */
    public @NotNull Iterable<Double> doublesIncreasing() {
        //noinspection RedundantCast
        return concat((Iterable<Iterable<Double>>) Arrays.asList(
                stopAt(d -> d == -Double.MIN_VALUE, iterate(FloatingPointUtils::successor, Double.NEGATIVE_INFINITY)),
                Arrays.asList(-0.0, Double.NaN, 0.0),
                stopAt(d -> d == Double.POSITIVE_INFINITY, iterate(FloatingPointUtils::successor, Double.MIN_VALUE))
        ));
    }

    /**
     * An {@code Iterable} that contains all possible positive {@code double} mantissas. A {@code double}'s mantissa is
     * the unique odd integer that, when multiplied by a power of 2, equals the {@code double}. Does not support
     * removal.
     *
     * Length is 2<sup>52</sup> = 4,503,599,627,370,496
     */
    private static final @NotNull Iterable<Long> DOUBLE_MANTISSAS = IterableUtils.rangeBy(1L, 2, 1L << 53);

    /**
     * An {@code Iterable} that contains all possible {@code double} exponents. A positive {@code double}'s exponent is
     * the base-2 logarithm of the {@code double} divided by its mantissa. Does not support removal.
     *
     * Length is 2<sup>11</sup>+52–2 = 2,098
     */
    private static final @NotNull Iterable<Integer> DOUBLE_EXPONENTS = cons(
            0,
            mux(Arrays.asList(INSTANCE.range(1, 1023), IterableUtils.rangeBy(-1, -1, -1074)))
    );

    /**
     * An {@code Iterable} that contains all ordinary (neither {@code NaN} nor infinite) positive {@code double}s. Does
     * not support removal.
     *
     * Length is 2<sup>63</sup>–2<sup>52</sup>–1 = 9,218,868,437,227,405,311
     */
    @Override
    public @NotNull Iterable<Double> positiveOrdinaryDoubles() {
        return map(
                Optional::get,
                filter(
                        Optional::isPresent,
                        map(p -> FloatingPointUtils.doubleFromME(p.a, p.b), pairs(DOUBLE_MANTISSAS, DOUBLE_EXPONENTS))
                )
        );
    }

    /**
     * An {@code Iterable} that contains all ordinary (neither {@code NaN} nor infinite) negative {@code double}s in
     * increasing order. Negative zero is not included. Does not support removal.
     *
     * Length is 2<sup>63</sup>–2<sup>52</sup>–1 = 9,218,868,437,227,405,311
     */
    @Override
    public @NotNull Iterable<Double> negativeOrdinaryDoubles() {
        return map(d -> -d, positiveOrdinaryDoubles());
    }

    /**
     * An {@code Iterable} that contains all ordinary (neither {@code NaN} nor infinite) {@code double}s in increasing
     * order. Negative zero is not included, but positive zero is. Does not support removal.
     *
     * Length is 2<sup>64</sup>–2<sup>53</sup>–1 = 18,437,736,874,454,810,623
     */
    @Override
    public @NotNull Iterable<Double> ordinaryDoubles() {
        return cons(0.0, mux(Arrays.asList(positiveOrdinaryDoubles(), negativeOrdinaryDoubles())));
    }

    /**
     * An {@code Iterable} that contains all {@code double}s. Does not support removal.
     *
     * Length is 2<sup>64</sup>–2<sup>53</sup>+3 = 18,437,736,874,454,810,627
     */
    @Override
    public @NotNull Iterable<Double> doubles() {
        return concat(
                Arrays.asList(Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, 0.0, -0.0),
                tail(ordinaryDoubles())
        );
    }

    /**
     * An {@code Iterable} that contains all positive {@link BigDecimal}s. Does not support removal.
     *
     * Length is infinite
     */
    public @NotNull Iterable<BigDecimal> positiveBigDecimals() {
        return map(p -> new BigDecimal(p.a, p.b), pairsLogarithmicOrder(positiveBigIntegers(), integers()));
    }

    /**
     * An {@code Iterable} that contains all negative {@code BigDecimal}s. Does not support removal.
     *
     * Length is infinite
     */
    public @NotNull Iterable<BigDecimal> negativeBigDecimals() {
        return map(p -> new BigDecimal(p.a, p.b), pairsLogarithmicOrder(negativeBigIntegers(), integers()));
    }

    /**
     * An {@code Iterable} that contains all {@code BigDecimal}s. Does not support removal.
     *
     * Length is infinite
     */
    public @NotNull Iterable<BigDecimal> bigDecimals() {
        return map(p -> new BigDecimal(p.a, p.b), pairsLogarithmicOrder(bigIntegers(), integers()));
    }

    @Override
    public @NotNull <T> Iterable<T> withNull(@NotNull Iterable<T> xs) {
        return cons(null, xs);
    }

    @Override
    public @NotNull <T> Iterable<Optional<T>> optionals(@NotNull Iterable<T> xs) {
        return cons(Optional.<T>empty(), map(Optional::of, xs));
    }

    @Override
    public @NotNull <T> Iterable<NullableOptional<T>> nullableOptionals(@NotNull Iterable<T> xs) {
        return cons(NullableOptional.<T>empty(), map(NullableOptional::of, xs));
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

    @Override
    public @NotNull <A, B> Iterable<Pair<A, B>> dependentPairs(
            @NotNull Iterable<A> xs,
            @NotNull Function<A, Iterable<B>> f
    ) {
        return Combinatorics.dependentPairs(
                xs,
                f,
                (BigInteger i) -> {
                    List<BigInteger> list = MathUtils.demux(2, i);
                    return new Pair<>(list.get(0), list.get(1));
                }
        );
    }

    @Override
    public @NotNull <A, B> Iterable<Pair<A, B>> dependentPairsLogarithmic(
            @NotNull Iterable<A> xs,
            @NotNull Function<A, Iterable<B>> f
    ) {
        return Combinatorics.dependentPairs(
                xs,
                f,
                MathUtils::logarithmicDemux
        );
    }

    @Override
    public @NotNull <A, B> Iterable<Pair<A, B>> dependentPairsSquareRoot(
            @NotNull Iterable<A> xs,
            @NotNull Function<A, Iterable<B>> f
    ) {
        return Combinatorics.dependentPairs(
                xs,
                f,
                MathUtils::squareRootDemux
        );
    }

    @Override
    public @NotNull <A, B> Iterable<Pair<A, B>> dependentPairsExponential(
            @NotNull Iterable<A> xs,
            @NotNull Function<A, Iterable<B>> f
    ) {
        return Combinatorics.dependentPairs(
                xs,
                f,
                i -> {
                    Pair<BigInteger, BigInteger> p = MathUtils.logarithmicDemux(i);
                    return new Pair<>(p.b, p.a);
                }
        );
    }

    @Override
    public @NotNull <A, B> Iterable<Pair<A, B>> dependentPairsSquare(
            @NotNull Iterable<A> xs,
            @NotNull Function<A, Iterable<B>> f
    ) {
        return Combinatorics.dependentPairs(
                xs,
                f,
                i -> {
                    Pair<BigInteger, BigInteger> p = MathUtils.squareRootDemux(i);
                    return new Pair<>(p.b, p.a);
                }
        );
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
    public boolean equals(@NotNull Object that) {
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
