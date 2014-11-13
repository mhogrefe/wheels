package mho.wheels.iterables;

import mho.wheels.math.Combinatorics;
import mho.wheels.numbers.Numbers;
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
 * {@link Iterable}s that contain all (or some important subset) of a type's values. These are useful for exhaustive
 * testing. Nulls are not included by default, but may easily be added via
 * {@link mho.wheels.iterables.IterableUtils#cons}. The {@code Iterable}'s elements are typically in order of
 * increasing complexity, unless otherwise specified. See {@code ExhaustiveProviderTest} for examples.
 */
public class ExhaustiveProvider implements IterableProvider {
    public static final ExhaustiveProvider INSTANCE = new ExhaustiveProvider();
    private static final int MAX_SIZE_FOR_SHORT_LIST_ALG = 5;

    public static void main(String[] args) {
        IterableProvider P = new ExhaustiveProvider();
        for (Triple<BigDecimal, Float, Character> t : P.triples(P.bigDecimals(), P.floats(), P.asciiCharacters())) {
            System.out.println(t);
        }
    }

    protected ExhaustiveProvider() {}

    /**
     * A {@link List} that contains both {@link Boolean}s.
     *
     * Length is 2
     */
    @Override
    public @NotNull List<Boolean> booleans() {
        return Arrays.asList(false, true);
    }

    /**
     * A {@code List} that contains all {@link Ordering}s in ascending order.
     *
     * Length is 3
     *
     * @return the {@code List} described above.
     */
    public @NotNull List<Ordering> orderingsAscending() {
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
    public @NotNull Iterable<Byte> range(byte a) {
        return IterableUtils.range(a);
    }

    @Override
    public @NotNull Iterable<Short> range(short a) {
        return IterableUtils.range(a);
    }

    @Override
    public @NotNull Iterable<Integer> range(int a) {
        return IterableUtils.range(a);
    }

    @Override
    public @NotNull Iterable<Long> range(long a) {
        return IterableUtils.range(a);
    }

    @Override
    public @NotNull Iterable<BigInteger> range(@NotNull BigInteger a) {
        return IterableUtils.range(a);
    }

    @Override
    public @NotNull Iterable<Character> range(char a) {
        return IterableUtils.range(a);
    }

    @Override
    public @NotNull Iterable<Byte> range(byte a, byte b) {
        return IterableUtils.range(a, b);
    }

    @Override
    public @NotNull Iterable<Short> range(short a, short b) {
        return IterableUtils.range(a, b);
    }

    @Override
    public @NotNull Iterable<Integer> range(int a, int b) {
        return IterableUtils.range(a, b);
    }

    public @NotNull Iterable<Long> range(long a, long b) {
        return IterableUtils.range(a, b);
    }

    @Override
    public @NotNull Iterable<BigInteger> range(@NotNull BigInteger a, @NotNull BigInteger b) {
        return IterableUtils.range(a, b);
    }

    @Override
    public @NotNull Iterable<Character> range(char a, char b) {
        return IterableUtils.range(a, b);
    }

    @Override
    public @NotNull Iterable<Byte> rangeBy(byte a, byte i) {
        return IterableUtils.rangeBy(a, i);
    }

    @Override
    public @NotNull Iterable<Short> rangeBy(short a, short i) {
        return IterableUtils.rangeBy(a, i);
    }

    @Override
    public @NotNull Iterable<Integer> rangeBy(int a, int i) {
        return IterableUtils.rangeBy(a, i);
    }

    @Override
    public @NotNull Iterable<Long> rangeBy(long a, long i) {
        return IterableUtils.rangeBy(a, i);
    }

    @Override
    public @NotNull Iterable<BigInteger> rangeBy(@NotNull BigInteger a, @NotNull BigInteger i) {
        return IterableUtils.rangeBy(a, i);
    }

    @Override
    public @NotNull Iterable<Character> rangeBy(char a, int i) {
        return IterableUtils.rangeBy(a, i);
    }

    @Override
    public @NotNull Iterable<Byte> rangeBy(byte a, byte i, byte b) {
        return IterableUtils.rangeBy(a, i, b);
    }

    @Override
    public @NotNull Iterable<Short> rangeBy(short a, short i, short b) {
        return IterableUtils.rangeBy(a, i, b);
    }

    @Override
    public @NotNull Iterable<Integer> rangeBy(int a, int i, int b) {
        return IterableUtils.rangeBy(a, i, b);
    }

    @Override
    public @NotNull Iterable<Long> rangeBy(long a, long i, long b) {
        return IterableUtils.rangeBy(a, i, b);
    }

    @Override
    public @NotNull Iterable<BigInteger> rangeBy(@NotNull BigInteger a, @NotNull BigInteger i, @NotNull BigInteger b) {
        return IterableUtils.rangeBy(a, i, b);
    }

    @Override
    public @NotNull Iterable<Character> rangeBy(char a, int i, char b) {
        return IterableUtils.rangeBy(a, i, b);
    }

    /**
     * An {@code Iterable} that contains all {@link Byte}s in ascending order. Does not support removal.
     *
     * Length is 2<sup>8</sup> = 256
     *
     * @return the {@code Iterable} described above.
     */
    public @NotNull Iterable<Byte> bytesAscending() {
        return range(Byte.MIN_VALUE);
    }

    /**
     * An {@code Iterable} that contains all {@link Short}s in ascending order. Does not support removal.
     *
     * Length is 2<sup>16</sup> = 65,536
     *
     * @return the {@code Iterable} described above.
     */
    public @NotNull Iterable<Short> shortsAscending() {
        return range(Short.MIN_VALUE);
    }

    /**
     * An {@code Iterable} that contains all {@link Integer}s in ascending order. Does not support removal.
     *
     * Length is 2<sup>32</sup> = 4,294,967,296
     *
     * @return the {@code Iterable} described above.
     */
    public @NotNull Iterable<Integer> integersAscending() {
        return range(Integer.MIN_VALUE);
    }

    /**
     * An {@code Iterable} that contains all {@link Long}s in ascending order. Does not support removal.
     *
     * Length is 2<sup>64</sup> = 18,446,744,073,709,551,616
     *
     * @return the {@code Iterable} described above.
     */
    public @NotNull Iterable<Long> longsAscending() {
        return range(Long.MIN_VALUE);
    }

    /**
     * An {@code Iterable} that contains all positive {@code Byte}s. Does not support removal.
     *
     * Length is 2<sup>7</sup>–1 = 127
     */
    @Override
    public @NotNull Iterable<Byte> positiveBytes() {
        return range((byte) 1);
    }

    /**
     * An {@code Iterable} that contains all positive {@code Short}s. Does not support removal.
     *
     * Length is 2<sup>15</sup>–1 = 32,767
     */
    @Override
    public @NotNull Iterable<Short> positiveShorts() {
        return range((short) 1);
    }

    /**
     * An {@code Iterable} that contains all positive {@code Integer}s. Does not support removal.
     *
     * Length is 2<sup>31</sup>–1 = 2,147,483,647
     */
    @Override
    public @NotNull Iterable<Integer> positiveIntegers() {
        return range(1);
    }

    /**
     * An {@code Iterable} that contains all positive {@code Long}s. Does not support removal.
     *
     * Length is 2<sup>63</sup>–1 = 9,223,372,036,854,775,807
     */
    @Override
    public @NotNull Iterable<Long> positiveLongs() {
        return range(1L);
    }

    /**
     * An {@code Iterable} that contains all positive {@link BigInteger}s. Does not support removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<BigInteger> positiveBigIntegers() {
        return range(BigInteger.ONE);
    }

    /**
     * An {@code Iterable} that contains all negative {@code Byte}s. Does not support removal.
     *
     * Length is 2<sup>7</sup> = 128
     */
    @Override
    public @NotNull Iterable<Byte> negativeBytes() {
        return rangeBy((byte) -1, (byte) -1);
    }

    /**
     * An {@code Iterable} that contains all negative {@code Short}s. Does not support removal.
     *
     * Length is 2<sup>15</sup> = 32,768
     */
    @Override
    public @NotNull Iterable<Short> negativeShorts() {
        return rangeBy((short) -1, (short) -1);
    }

    /**
     * An {@code Iterable} that contains all negative {@code Integer}s. Does not support removal.
     *
     * Length is 2<sup>31</sup> = 2,147,483,648
     */
    @Override
    public @NotNull Iterable<Integer> negativeIntegers() {
        return rangeBy(-1, -1);
    }

    /**
     * An {@code Iterable} that contains all negative {@code Long}s. Does not support removal.
     *
     * Length is 2<sup>63</sup> = 9,223,372,036,854,775,808
     */
    @Override
    public @NotNull Iterable<Long> negativeLongs() {
        return rangeBy(-1L, -1L);
    }

    /**
     * An {@code Iterable} that contains all negative {@code BigInteger}s. Does not support removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<BigInteger> negativeBigIntegers() {
        return rangeBy(BigInteger.valueOf(-1), BigInteger.valueOf(-1));
    }

    /**
     * An {@code Iterable} that contains all natural {@code Byte}s. Does not support removal.
     *
     * Length is 2<sup>7</sup> = 128
     */
    @Override
    public @NotNull Iterable<Byte> naturalBytes() {
        return range((byte) 0);
    }

    /**
     * An {@code Iterable} that contains all natural {@code Short}s (including 0). Does not support removal.
     *
     * Length is 2<sup>15</sup> = 32,768
     */
    @Override
    public @NotNull Iterable<Short> naturalShorts() {
        return range((short) 0);
    }

    /**
     * An {@code Iterable} that contains all natural {@code Integer}s (including 0). Does not support removal.
     *
     * Length is 2<sup>31</sup> = 2,147,483,648
     */
    @Override
    public @NotNull Iterable<Integer> naturalIntegers() {
        return range(0);
    }

    /**
     * An {@code Iterable} that contains all natural {@code Long}s (including 0). Does not support removal.
     *
     * Length is 2<sup>63</sup> = 9,223,372,036,854,775,808
     */
    @Override
    public @NotNull Iterable<Long> naturalLongs() {
        return range(0L);
    }

    /**
     * An {@code Iterable} that contains all natural {@code BigInteger}s (including 0). Does not support removal.
     *
     * Length is infinite
     */
    @Override
    public @NotNull Iterable<BigInteger> naturalBigIntegers() {
        return range(BigInteger.ZERO);
    }

    /**
     * An {@code Iterable} that contains all {@code Byte}s. Does not support removal.
     *
     * Length is 2<sup>8</sup> = 128
     */
    @Override
    public @NotNull Iterable<Byte> bytes() {
        return cons(
                (byte) 0,
                mux(Arrays.asList(positiveBytes(), negativeBytes()))
        );
    }

    /**
     * An {@code Iterable} that contains all {@code Short}s. Does not support removal.
     *
     * Length is 2<sup>16</sup> = 65,536
     */
    @Override
    public @NotNull Iterable<Short> shorts() {
        return cons(
                (short) 0,
                mux(Arrays.asList(positiveShorts(), negativeShorts()))
        );
    }

    /**
     * An {@code Iterable} that contains all {@code Integer}s. Does not support removal.
     *
     * Length is 2<sup>32</sup> = 4,294,967,296
     */
    @Override
    public @NotNull Iterable<Integer> integers() {
        return cons(
                0,
                mux(Arrays.asList(positiveIntegers(), negativeIntegers()))
        );
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
        return cons(
                BigInteger.ZERO, mux(Arrays.asList(positiveBigIntegers(), negativeBigIntegers()))
        );
    }

    /**
     * An {@code Iterable} that contains all ASCII {@link Character}s in ascending order. Does not support
     * removal.
     *
     * Length is 2<sup>7</sup> = 128
     *
     * @return the {@code Iterable} described above.
     */
    public @NotNull Iterable<Character> asciiCharactersAscending() {
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
     * An {@code Iterable} that contains all {@code Character}s in ascending order. Does not support removal.
     *
     * Length is 2<sup>16</sup> = 65,536
     *
     * @return the {@code Iterable} described above.
     */
    public @NotNull Iterable<Character> charactersAscending() {
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
                range((char) 127)           // DEL and non-ASCII
        ));
    }

    /**
     * An {@code Iterable} that contains all ordinary (neither {@code NaN} nor infinite) positive {@link float}s in
     * ascending order. Does not support removal.
     *
     * Length is 2<sup>31</sup>–2<sup>23</sup>–1 = 2,139,095,039
     *
     * @return the {@code Iterable} described above.
     */
    public @NotNull Iterable<Float> positiveOrdinaryFloatsAscending() {
        return stopAt(f -> f == Float.MAX_VALUE, iterate(Numbers::successor, Float.MIN_VALUE));
    }

    /**
     * An {@code Iterable} that contains all ordinary (neither {@code NaN} nor infinite) negative {@code float}s in
     * ascending order. Negative zero is not included. Does not support removal.
     *
     * Length is 2<sup>31</sup>–2<sup>23</sup>–1 = 2,139,095,039
     *
     * @return the {@code Iterable} described above.
     */
    public @NotNull Iterable<Float> negativeOrdinaryFloatsAscending() {
        return stopAt(f -> f == -Float.MIN_VALUE, iterate(Numbers::successor, -Float.MAX_VALUE));
    }

    /**
     * An {@code Iterable} that contains all ordinary (neither {@code NaN} nor infinite) {@code float}s in ascending
     * order. Negative zero is not included, but positive zero is. Does not support removal.
     *
     * Length is 2<sup>32</sup>–2<sup>24</sup>-1 = 4,278,190,079
     *
     * @return the {@code Iterable} described above.
     */
    public @NotNull Iterable<Float> ordinaryFloatsAscending() {
        return concat((Iterable<Iterable<Float>>) Arrays.asList(
                stopAt(f -> f == -Float.MIN_VALUE, iterate(Numbers::successor, -Float.MAX_VALUE)),
                Arrays.asList(0.0f),
                stopAt(f -> f == Float.MAX_VALUE, iterate(Numbers::successor, Float.MIN_VALUE))
        ));
    }

    /**
     * An {@code Iterable} that contains all {@code float}s in ascending order. {@code NaN} is traditionally unordered,
     * but here it is placed between negative zero and positive zero. Does not support removal.
     *
     * Length is 2<sup>32</sup>–2<sup>24</sup>+3 = 4,278,190,083
     *
     * @return the {@code Iterable} described above.
     */
    public @NotNull Iterable<Float> floatsAscending() {
        return concat((Iterable<Iterable<Float>>) Arrays.asList(
                stopAt(f -> f == -Float.MIN_VALUE, iterate(Numbers::successor, Float.NEGATIVE_INFINITY)),
                Arrays.asList(-0.0f, Float.NaN, 0.0f),
                stopAt(f -> f == Float.POSITIVE_INFINITY, iterate(Numbers::successor, Float.MIN_VALUE))
        ));
    }

    /**
     * An {@code Iterable} that contains all possible positive {@code float} mantissas. A {@code float}'s mantissa is
     * the unique odd integer that, when multiplied by a power of 2, equals the {@code float}. Does not support
     * removal.
     *
     * Length is 2<sup>23</sup> = 8,388,608
     */
    private static final @NotNull Iterable<Integer> FLOAT_MANTISSAS = INSTANCE.rangeBy(1, 2, 1 << 24);

    /**
     * An {@code Iterable} that contains all possible float exponents. A positive float's exponent is the base-2
     * logarithm of the float divided by its mantissa. Does not support removal.
     *
     * Length is 2<sup>8</sup>+23–2 = 277
     */
    private static final @NotNull Iterable<Integer> FLOAT_EXPONENTS = cons(
            0,
            mux(Arrays.asList(INSTANCE.range(1, 127), INSTANCE.rangeBy(-1, -1, -149)))
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
                        (Iterable<Optional<Float>>) map(
                                p -> Numbers.floatFromPair(p.a, p.b),
                                pairs(FLOAT_MANTISSAS, FLOAT_EXPONENTS)
                        )
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
                (Iterable<Float>) tail(ordinaryFloats())
        );
    }

    /**
     * @return An {@code Iterable} that contains all ordinary (neither {@code NaN} nor infinite) positive
     * {@link double}s in ascending order. Does not support removal.
     *
     * Length is 2<sup>63</sup>–2<sup>52</sup>–1 = 9,218,868,437,227,405,311
     */
    public @NotNull Iterable<Double> positiveOrdinaryDoublesAscending() {
        return stopAt(d -> d == Double.MAX_VALUE, iterate(Numbers::successor, Double.MIN_VALUE));
    }

    /**
     * @return An {@code Iterable} that contains all ordinary (neither {@code NaN} nor infinite) negative
     * {@code double}s in ascending order. Negative zero is not included. Does not support removal.
     *
     * Length is 2<sup>63</sup>–2<sup>52</sup>–1 = 9,218,868,437,227,405,311
     */
    public @NotNull Iterable<Double> negativeOrdinaryDoublesAscending() {
        return stopAt(d -> d == -Double.MIN_VALUE, iterate(Numbers::successor, -Double.MAX_VALUE));
    }

    /**
     * @return An {@code Iterable} that contains all ordinary (neither {@code NaN} nor infinite) {@code double}s in
     * ascending order. Negative zero is not included, but positive zero is. Does not support removal.
     *
     * Length is 2<sup>64</sup>–2<sup>53</sup>–1 = 18,437,736,874,454,810,623
     */
    public @NotNull Iterable<Double> ordinaryDoublesAscending() {
        return concat((Iterable<Iterable<Double>>) Arrays.asList(
                stopAt(d -> d == -Double.MIN_VALUE, iterate(Numbers::successor, -Double.MAX_VALUE)),
                Arrays.asList(0.0),
                stopAt(d -> d == Double.MAX_VALUE, iterate(Numbers::successor, Double.MIN_VALUE))
        ));
    }

    /**
     * @return An {@code Iterable} that contains all {@code double}s in ascending order. {@code NaN} is traditionally
     * unordered, but here it is placed between negative zero and positive zero. Does not support removal.
     *
     * Length is 2<sup>64</sup>–2<sup>53</sup>+3 = 18,437,736,874,454,810,627
     */
    public @NotNull Iterable<Double> doublesAscending() {
        return concat((Iterable<Iterable<Double>>) Arrays.asList(
                stopAt(d -> d == -Double.MIN_VALUE, iterate(Numbers::successor, Double.NEGATIVE_INFINITY)),
                Arrays.asList(-0.0, Double.NaN, 0.0),
                stopAt(d -> d == Double.POSITIVE_INFINITY, iterate(Numbers::successor, Double.MIN_VALUE))
        ));
    }

    /**
     * An {@code Iterable} that contains all possible positive {@code double} mantissas. A {@code double}'s mantissa is
     * the unique odd integer that, when multiplied by a power of 2, equals the {@code double}. Does not support
     * removal.
     *
     * Length is 2<sup>52</sup> = 4,503,599,627,370,496
     */
    private static final @NotNull Iterable<Long> DOUBLE_MANTISSAS = INSTANCE.rangeBy(1L, 2, 1L << 53);

    /**
     * An {@code Iterable} that contains all possible {@code double} exponents. A positive {@code double}'s exponent is
     * the base-2 logarithm of the {@code double" divided by its mantissa. Does not support removal.
     *
     * Length is 2<sup>11</sup>+52–2 = 2,098
     */
    private static final @NotNull Iterable<Integer> DOUBLE_EXPONENTS = cons(
            0,
            mux(Arrays.asList(INSTANCE.range(1, 1023), INSTANCE.rangeBy(-1, -1, -1074)))
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
                        (Iterable<Optional<Double>>) map(
                                p -> Numbers.doubleFromPair(p.a, p.b),
                                pairs(DOUBLE_MANTISSAS, DOUBLE_EXPONENTS)
                        )
                )
        );
    }

    /**
     * An {@code Iterable} that contains all ordinary (neither {@code NaN} nor infinite) negative {@code double}s in
     * ascending order. Negative zero is not included. Does not support removal.
     *
     * Length is 2<sup>63</sup>–2<sup>52</sup>–1 = 9,218,868,437,227,405,311
     */
    @Override
    public @NotNull Iterable<Double> negativeOrdinaryDoubles() {
        return map(d -> -d, positiveOrdinaryDoubles());
    }

    /**
     * An {@code Iterable} that contains all ordinary (neither {@code NaN} nor infinite) {@code double}s in ascending
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
                (Iterable<Double>) tail(ordinaryDoubles())
        );
    }

    /**
     * An {@code Iterable} that contains all positive {@link BigDecimal}s. Does not support removal.
     *
     * Length is infinite.
     */
    public @NotNull Iterable<BigDecimal> positiveBigDecimals() {
        return map(p -> new BigDecimal(p.a, p.b), pairsLogarithmicOrder(positiveBigIntegers(), integers()));
    }

    /**
     * An {@code Iterable} that contains all negative {@code BigDecimal}s. Does not support removal.
     *
     * Length is infinite.
     */
    public @NotNull Iterable<BigDecimal> negativeBigDecimals() {
        return map(p -> new BigDecimal(p.a, p.b), pairsLogarithmicOrder(negativeBigIntegers(), integers()));
    }

    /**
     * An {@code Iterable} that contains all {@code BigDecimal}s. Does not support removal.
     *
     * Length is infinite.
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
        return Combinatorics.dependentPairs(xs, f);
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
            return Combinatorics.listsAscending(size, xs);
        } else {
            return Combinatorics.lists(size, xs);
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
    public @NotNull Iterable<String> strings(int size) {
        return Combinatorics.strings(size, characters());
    }

    @Override
    public @NotNull Iterable<String> strings(@NotNull Iterable<Character> cs) {
        return Combinatorics.strings(cs);
    }

    @Override
    public @NotNull Iterable<String> strings() {
        return Combinatorics.strings(characters());
    }
}
