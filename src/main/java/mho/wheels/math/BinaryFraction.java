package mho.wheels.math;

import mho.wheels.misc.Readers;
import mho.wheels.ordering.Ordering;
import mho.wheels.structures.Pair;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * <p>The {@code BinaryFraction} class uniquely represents rational numbers whose denominator is a power of 2. Every
 * such number is either zero or an equal to an odd integer (the mantissa) times 2 raised to an integer (the exponent).
 * Zero is considered to have a mantissa of zero (this is the only case when the mantissa is even) and an exponent of
 * zero.
 *
 * <p>There is only one instance of {@code ZERO} and one instance of {@code ONE}, so these may be compared with other
 * {@code BigInteger}s using {@code ==}.
 *
 * <p>This class is immutable.
 */
public class BinaryFraction implements Comparable<BinaryFraction> {
    /**
     * 0
     */
    public static final @NotNull BinaryFraction ZERO = new BinaryFraction(BigInteger.ZERO, 0);

    /**
     * 1
     */
    public static final @NotNull BinaryFraction ONE = new BinaryFraction(BigInteger.ONE, 0);

    private @NotNull BigInteger mantissa;
    private int exponent;

    private BinaryFraction(@NotNull BigInteger mantissa, int exponent) {
        this.mantissa = mantissa;
        this.exponent = exponent;
    }

    public @NotNull BigInteger getMantissa() {
        return mantissa;
    }

    public int getExponent() {
        return exponent;
    }

    public static @NotNull BinaryFraction of(@NotNull BigInteger mantissa, int exponent) {
        if (mantissa.equals(BigInteger.ZERO)) {
            return new BinaryFraction(BigInteger.ZERO, 0);
        }
        int trailingZeroes = mantissa.getLowestSetBit();
        if (trailingZeroes != 0) {
            mantissa = mantissa.shiftRight(trailingZeroes);
            exponent += trailingZeroes;
        }
        return new BinaryFraction(mantissa, exponent);
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        if (that == null || getClass() != that.getClass()) return false;
        BinaryFraction bf = (BinaryFraction) that;
        return exponent == bf.exponent && mantissa.equals(bf.mantissa);
    }

    @Override
    public int hashCode() {
        return 31 * mantissa.hashCode() + exponent;
    }

    @Override
    public int compareTo(@NotNull BinaryFraction that) {
        if (this == that) return 0;
        Ordering signumOrdering = Ordering.fromInt(Integer.compare(mantissa.signum(), that.mantissa.signum()));
        if (signumOrdering != Ordering.EQ) return signumOrdering.toInt();
        switch (Ordering.fromInt(Integer.compare(exponent, that.exponent))) {
            case LT: return mantissa.shiftLeft(that.exponent - exponent).compareTo(that.mantissa);
            case GT: return mantissa.compareTo(that.mantissa.shiftLeft(exponent - that.exponent));
            case EQ: return mantissa.compareTo(that.mantissa);
        }
        throw new IllegalStateException("unreachable");
    }

    public static @NotNull Optional<BinaryFraction> read(@NotNull String s) {
        return Readers.genericRead(
            t -> {
                int leftShiftIndex = s.indexOf(" << ");
                if (leftShiftIndex != -1) {
                    Optional<BigInteger> oMantissa = Readers.readBigInteger(s.substring(0, leftShiftIndex));
                    if (!oMantissa.isPresent()) return null;
                    Optional<Integer> oExponent = Readers.readInteger(s.substring(leftShiftIndex + 4));
                    if (!oExponent.isPresent()) return null;
                    return of(oMantissa.get(), oExponent.get());
                }
                int rightShiftIndex = s.indexOf(" >> ");
                if (rightShiftIndex != -1) {
                    Optional<BigInteger> oMantissa = Readers.readBigInteger(s.substring(0, rightShiftIndex));
                    if (!oMantissa.isPresent()) return null;
                    Optional<Integer> oExponent = Readers.readInteger(s.substring(rightShiftIndex + 4));
                    if (!oExponent.isPresent()) return null;
                    return of(oMantissa.get(), -oExponent.get());
                }
                Optional<BigInteger> oMantissa = Readers.readBigInteger(s);
                return oMantissa.isPresent() ? new BinaryFraction(oMantissa.get(), 0) : null;
            }
        ).apply(s);
    }

    public static @NotNull Optional<Pair<BinaryFraction, Integer>> findIn(@NotNull String s) {
        return Readers.genericFindIn(BinaryFraction::read, " -0123456789<>").apply(s);
    }

    public @NotNull String toString() {
        switch (Integer.signum(exponent)) {
            case 0:  return mantissa.toString();
            case 1:  return mantissa + " << " + exponent;
            case -1: return mantissa + " >> " + -exponent;
            default: throw new IllegalStateException("unreachable");
        }
    }

    /**
     * Ensures that {@code this} is valid. Must return true for any {@code Rational} used outside this class.
     */
    public void validate() {
        if (mantissa.equals(BigInteger.ZERO)) {
            assertEquals(toString(), exponent, 0);
        } else {
            assertTrue(toString(), mantissa.testBit(0));
        }
        if (equals(ZERO)) assertTrue(toString(), this == ZERO);
        if (equals(ONE)) assertTrue(toString(), this == ONE);
    }
}
