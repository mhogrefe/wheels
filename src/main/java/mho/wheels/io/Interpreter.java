package mho.wheels.io;

import mho.wheels.math.MathUtils;
import mho.wheels.ordering.Ordering;
import mho.wheels.structures.Either;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.function.BiFunction;
import java.util.function.Function;

import static mho.wheels.iterables.IterableUtils.head;
import static mho.wheels.iterables.IterableUtils.last;

public class Interpreter {
    public static @NotNull <T> Either<T, String> tryUnwrapParens(
            @NotNull Function<String, Either<T, String>> parser,
            @NotNull String s
    ) {
        if (s.isEmpty() || head(s) != '(' || last(s) != ')') {
            return Either.ofB("");
        }
        boolean parensReachZero = false;
        int parenLevel = 1;
        for (int i = 1; i < s.length() - 1; i++) {
            switch (s.charAt(i)) {
                case '(': parenLevel++; break;
                case ')': parenLevel--; break;
                default:
                    if (parenLevel < 0) {
                        return Either.ofB("parenthesis error: " + s);
                    } else if (parenLevel == 0) {
                        parensReachZero = true;
                        break;
                    }
            }
        }
        if (parensReachZero) {
            return Either.ofB("");
        } else {
            return parser.apply(s.substring(1, s.length() - 1));
        }
    }

    public static @NotNull <A, B> Either<B, String> tryApplyUnaryOperation(
            @NotNull Function<A, B> operation,
            @NotNull String representation,
            @NotNull Function<String, Either<A, String>> parser,
            @NotNull String s) {
        if (!s.startsWith(representation)) {
            return Either.ofB("");
        }
        Either<A, String> sub = parser.apply(s.substring(representation.length()));
        if (sub.whichSlot() == Either.Slot.A) {
            try {
                return Either.ofA(operation.apply(sub.a()));
            } catch (Exception e) {
                return Either.ofB(e.getMessage());
            }
        } else {
            return Either.ofB(sub.b());
        }
    }

    public static @NotNull Either<BigInteger, String> tryBigIntegerNegate(@NotNull String s) {
        return tryApplyUnaryOperation(BigInteger::negate, "-", Interpreter::readBigInteger, s);
    }

    public static @NotNull Either<BigInteger, String> tryBigIntegerNot(@NotNull String s) {
        return tryApplyUnaryOperation(BigInteger::not, "~", Interpreter::readBigInteger, s);
    }

    public static @NotNull Either<Boolean, String> tryBooleanNot(@NotNull String s) {
        return tryApplyUnaryOperation(b -> !b, "!", Interpreter::readBoolean, s);
    }

    public static @NotNull <A, B, C> Either<C, String> tryApplyLeftAssociativeBinaryOperation(
            @NotNull BiFunction<A, B, C> operation,
            @NotNull String representation,
            @NotNull Function<String, Either<A, String>> leftParser,
            @NotNull Function<String, Either<B, String>> rightParser,
            boolean tryAfterFailure,
            @NotNull String s
    ) {
        int parenLevel = 0;
        for (int i = s.length() - 1; i > 0; i--) { // skip first char to avoid confusion with unary operations
            switch (s.charAt(i)) {
                case ')': parenLevel++; break;
                case '(': parenLevel--; break;
                default:
                    if (parenLevel == 0 && s.startsWith(representation, i)) {
                        Either<A, String> leftSub = leftParser.apply(s.substring(0, i));
                        if (leftSub.whichSlot() == Either.Slot.B) {
                            if (tryAfterFailure) {
                                continue;
                            } else {
                                return Either.ofB(leftSub.b());
                            }
                        }
                        Either<B, String> rightSub = rightParser.apply(s.substring(i + representation.length()));
                        if (rightSub.whichSlot() == Either.Slot.B) {
                            if (tryAfterFailure) {
                                continue;
                            } else {
                                return Either.ofB(rightSub.b());
                            }
                        }
                        try {
                            return Either.ofA(operation.apply(leftSub.a(), rightSub.a()));
                        } catch (Exception e) {
                            return Either.ofB(e.getMessage());
                        }
                    }
            }
        }
        return Either.ofB("");
    }

    public static @NotNull <A, B, C> Either<C, String> tryApplyRightAssociativeBinaryOperation(
            @NotNull BiFunction<A, B, C> operation,
            @NotNull String representation,
            @NotNull Function<String, Either<A, String>> leftParser,
            @NotNull Function<String, Either<B, String>> rightParser,
            boolean tryAfterFailure,
            @NotNull String s
    ) {
        int parenLevel = 0;
        for (int i = 0; i < s.length(); i++) {
            switch (s.charAt(i)) {
                case '(': parenLevel++; break;
                case ')': parenLevel--; break;
                default:
                    if (parenLevel == 0 && s.startsWith(representation, i)) {
                        Either<A, String> leftSub = leftParser.apply(s.substring(0, i));
                        if (leftSub.whichSlot() == Either.Slot.B) {
                            if (tryAfterFailure) {
                                continue;
                            } else {
                                return Either.ofB(leftSub.b());
                            }
                        }
                        Either<B, String> rightSub =
                                rightParser.apply(s.substring(i + representation.length()));
                        if (rightSub.whichSlot() == Either.Slot.B) {
                            if (tryAfterFailure) {
                                continue;
                            } else {
                                return Either.ofB(rightSub.b());
                            }
                        }
                        try {
                            return Either.ofA(operation.apply(leftSub.a(), rightSub.a()));
                        } catch (Exception e) {
                            return Either.ofB(e.getMessage());
                        }
                    }
            }
        }
        return Either.ofB("");
    }

    public static @NotNull Either<BigInteger, String> tryBigIntegerPow(@NotNull String s) {
        return tryApplyRightAssociativeBinaryOperation(
                BigInteger::pow,
                "^",
                Interpreter::readBigInteger,
                Interpreter::readInteger,
                false,
                s
        );
    }

    public static @NotNull Either<BigInteger, String> tryBigIntegerMultiply(@NotNull String s) {
        return tryApplyLeftAssociativeBinaryOperation(
                BigInteger::multiply,
                "*",
                Interpreter::readBigInteger,
                Interpreter::readBigInteger,
                false,
                s
        );
    }

    public static @NotNull Either<BigInteger, String> tryBigIntegerIntegerDivide(@NotNull String s) {
        return tryApplyLeftAssociativeBinaryOperation(
                BigInteger::divide,
                "//",
                Interpreter::readBigInteger,
                Interpreter::readBigInteger,
                false,
                s
        );
    }

    public static @NotNull Either<BigInteger, String> tryBigIntegerMod(@NotNull String s) {
        return tryApplyLeftAssociativeBinaryOperation(
                BigInteger::mod,
                "%",
                Interpreter::readBigInteger,
                Interpreter::readBigInteger,
                false,
                s
        );
    }

    public static @NotNull Either<BigInteger, String> tryBigIntegerAdd(@NotNull String s) {
        return tryApplyLeftAssociativeBinaryOperation(
                BigInteger::add,
                "+",
                Interpreter::readBigInteger,
                Interpreter::readBigInteger,
                false,
                s
        );
    }

    public static @NotNull Either<BigInteger, String> tryBigIntegerSubtract(@NotNull String s) {
        return tryApplyLeftAssociativeBinaryOperation(
                BigInteger::subtract,
                "-",
                Interpreter::readBigInteger,
                Interpreter::readBigInteger,
                true,
                s
        );
    }

    public static @NotNull Either<BigInteger, String> tryBigIntegerShiftLeft(@NotNull String s) {
        return tryApplyLeftAssociativeBinaryOperation(
                BigInteger::shiftLeft,
                "<<",
                Interpreter::readBigInteger,
                Interpreter::readInteger,
                false,
                s
        );
    }

    public static @NotNull Either<BigInteger, String> tryBigIntegerShiftRight(@NotNull String s) {
        return tryApplyLeftAssociativeBinaryOperation(
                BigInteger::shiftRight,
                ">>",
                Interpreter::readBigInteger,
                Interpreter::readInteger,
                false,
                s
        );
    }

    public static @NotNull Either<BigInteger, String> tryBigIntegerBitwiseAnd(@NotNull String s) {
        return tryApplyLeftAssociativeBinaryOperation(
                BigInteger::and,
                ".&.",
                Interpreter::readBigInteger,
                Interpreter::readBigInteger,
                false,
                s
        );
    }

    public static @NotNull Either<BigInteger, String> tryBigIntegerBitwiseXor(@NotNull String s) {
        return tryApplyLeftAssociativeBinaryOperation(
                BigInteger::xor,
                ".^.",
                Interpreter::readBigInteger,
                Interpreter::readBigInteger,
                false,
                s
        );
    }

    public static @NotNull Either<BigInteger, String> tryBigIntegerBitwiseOr(@NotNull String s) {
        return tryApplyLeftAssociativeBinaryOperation(
                BigInteger::or,
                ".|.",
                Interpreter::readBigInteger,
                Interpreter::readBigInteger,
                false,
                s
        );
    }

    public static @NotNull Either<Boolean, String> tryBooleanAnd(@NotNull String s) {
        return tryApplyLeftAssociativeBinaryOperation(
                (a, b) -> a && b,
                "&&",
                Interpreter::readBoolean,
                Interpreter::readBoolean,
                false,
                s
        );
    }

    public static @NotNull Either<Boolean, String> tryBooleanOr(@NotNull String s) {
        return tryApplyLeftAssociativeBinaryOperation(
                (a, b) -> a || b,
                "||",
                Interpreter::readBoolean,
                Interpreter::readBoolean,
                false,
                s
        );
    }

    public static @NotNull Either<Boolean, String> tryBigIntegerLessThan(@NotNull String s) {
        //noinspection Convert2MethodRef
        return tryApplyLeftAssociativeBinaryOperation(
                (a, b) -> Ordering.lt(a, b),
                "<",
                Interpreter::readBigInteger,
                Interpreter::readBigInteger,
                false,
                s
        );
    }

    public static @NotNull Either<Boolean, String> tryBigIntegerGreaterThan(@NotNull String s) {
        //noinspection Convert2MethodRef
        return tryApplyLeftAssociativeBinaryOperation(
                (a, b) -> Ordering.gt(a, b),
                ">",
                Interpreter::readBigInteger,
                Interpreter::readBigInteger,
                false,
                s
        );
    }

    public static @NotNull Either<Boolean, String> tryBigIntegerLessThanOrEqualTo(@NotNull String s) {
        //noinspection Convert2MethodRef
        return tryApplyLeftAssociativeBinaryOperation(
                (a, b) -> Ordering.le(a, b),
                "<=",
                Interpreter::readBigInteger,
                Interpreter::readBigInteger,
                false,
                s
        );
    }

    public static @NotNull Either<Boolean, String> tryBigIntegerGreaterThanOrEqualTo(@NotNull String s) {
        //noinspection Convert2MethodRef
        return tryApplyLeftAssociativeBinaryOperation(
                (a, b) -> Ordering.ge(a, b),
                ">=",
                Interpreter::readBigInteger,
                Interpreter::readBigInteger,
                false,
                s
        );
    }

    public static @NotNull Either<Boolean, String> tryBigIntegerEqual(@NotNull String s) {
        //noinspection Convert2MethodRef
        return tryApplyLeftAssociativeBinaryOperation(
                (a, b) -> a.equals(b),
                "==",
                Interpreter::readBigInteger,
                Interpreter::readBigInteger,
                false,
                s
        );
    }

    public static @NotNull Either<Boolean, String> tryBigIntegerNotEqual(@NotNull String s) {
        return tryApplyLeftAssociativeBinaryOperation(
                (a, b) -> !a.equals(b),
                "!=",
                Interpreter::readBigInteger,
                Interpreter::readBigInteger,
                false,
                s
        );
    }

    public static @NotNull <A, B> Either<B, String> tryApplyUnaryFunction(
            @NotNull Function<A, B> function,
            @NotNull String name,
            @NotNull Function<String, Either<A, String>> argParser,
            @NotNull String s
    ) {
        if (!s.startsWith(name + "(") || last(s) != ')') {
            return Either.ofB("");
        }
        int parenLevel = 0;
        boolean parensReachZero = false;
        for (int i = name.length(); i < s.length(); i++) {
            switch (s.charAt(i)) {
                case '(': parenLevel++; break;
                case ')': parenLevel--; break;
                default:
                    if (parenLevel == 0) {
                        parensReachZero = true;
                        break;
                    }
            }
        }
        if (parensReachZero) {
            return Either.ofB("");
        }
        String argString = s.substring(name.length() + 1, s.length() - 1);
        Either<A, String> sub = argParser.apply(argString);
        if (sub.whichSlot() == Either.Slot.B) {
            return Either.ofB("Failed to parse argument to " + name + ": " + argString);
        } else {
            try {
                return Either.ofA(function.apply(sub.a()));
            } catch (Exception e) {
                return Either.ofB(e.getMessage());
            }
        }
    }

    public static @NotNull Either<BigInteger, String> tryFactorial(@NotNull String s) {
        return tryApplyUnaryFunction(MathUtils::factorial, "factorial", Interpreter::readInteger, s);
    }

    public static @NotNull Either<BigInteger, String> trySmallestPrimeFactor(@NotNull String s) {
        return tryApplyUnaryFunction(
                MathUtils::smallestPrimeFactor,
                "smallestPrimeFactor",
                Interpreter::readBigInteger,
                s
        );
    }

    public static @NotNull Either<BigInteger, String> tryParseBigInteger(@NotNull String s) {
        try {
            return Either.ofA(new BigInteger(s));
        } catch (Exception e) {
            return Either.ofB(e.getMessage());
        }
    }

    public static @NotNull Either<BigInteger, String> readBigInteger(@NotNull String s) {
        s = s.trim();
        Either<BigInteger, String> result = tryUnwrapParens(Interpreter::readBigInteger, s);
        if (result.whichSlot() == Either.Slot.A || !result.b().isEmpty()) {
            return result;
        }
        result = tryBigIntegerBitwiseAnd(s);
        if (result.whichSlot() == Either.Slot.A || !result.b().isEmpty()) {
            return result;
        }
        result = tryBigIntegerBitwiseXor(s);
        if (result.whichSlot() == Either.Slot.A || !result.b().isEmpty()) {
            return result;
        }
        result = tryBigIntegerBitwiseOr(s);
        if (result.whichSlot() == Either.Slot.A || !result.b().isEmpty()) {
            return result;
        }
        result = tryBigIntegerShiftLeft(s);
        if (result.whichSlot() == Either.Slot.A || !result.b().isEmpty()) {
            return result;
        }
        result = tryBigIntegerShiftRight(s);
        if (result.whichSlot() == Either.Slot.A || !result.b().isEmpty()) {
            return result;
        }
        result = tryBigIntegerAdd(s);
        if (result.whichSlot() == Either.Slot.A || !result.b().isEmpty()) {
            return result;
        }
        result = tryBigIntegerSubtract(s);
        if (result.whichSlot() == Either.Slot.A || !result.b().isEmpty()) {
            return result;
        }
        result = tryBigIntegerMultiply(s);
        if (result.whichSlot() == Either.Slot.A || !result.b().isEmpty()) {
            return result;
        }
        result = tryBigIntegerIntegerDivide(s);
        if (result.whichSlot() == Either.Slot.A || !result.b().isEmpty()) {
            return result;
        }
        result = tryBigIntegerMod(s);
        if (result.whichSlot() == Either.Slot.A || !result.b().isEmpty()) {
            return result;
        }
        result = tryBigIntegerPow(s);
        if (result.whichSlot() == Either.Slot.A || !result.b().isEmpty()) {
            return result;
        }
        result = tryBigIntegerNegate(s);
        if (result.whichSlot() == Either.Slot.A || !result.b().isEmpty()) {
            return result;
        }
        result = tryBigIntegerNot(s);
        if (result.whichSlot() == Either.Slot.A || !result.b().isEmpty()) {
            return result;
        }
        result = tryFactorial(s);
        if (result.whichSlot() == Either.Slot.A || !result.b().isEmpty()) {
            return result;
        }
        result = trySmallestPrimeFactor(s);
        if (result.whichSlot() == Either.Slot.A || !result.b().isEmpty()) {
            return result;
        }
        return tryParseBigInteger(s);
    }

    public static @NotNull Either<Integer, String> readInteger(@NotNull String s) {
        Either<BigInteger, String> bi = readBigInteger(s);
        if (bi.whichSlot() == Either.Slot.B) {
            return Either.ofB(bi.b());
        }
        try {
            return Either.ofA(bi.a().intValueExact());
        } catch (ArithmeticException e) {
            return Either.ofB(e.getMessage());
        }
    }

    public static @NotNull Either<Boolean, String> tryParseBoolean(@NotNull String s) {
        switch (s) {
            case "true": return Either.ofA(true);
            case "false": return Either.ofA(false);
            default: return Either.ofB("Could not parse boolean: " + s);
        }
    }

    public static @NotNull Either<Boolean, String> readBoolean(@NotNull String s) {
        s = s.trim();
        Either<Boolean, String> result = tryBooleanOr(s);
        if (result.whichSlot() == Either.Slot.A || !result.b().isEmpty()) {
            return result;
        }
        result = tryBooleanAnd(s);
        if (result.whichSlot() == Either.Slot.A || !result.b().isEmpty()) {
            return result;
        }
        result = tryBigIntegerLessThanOrEqualTo(s);
        if (result.whichSlot() == Either.Slot.A || !result.b().isEmpty()) {
            return result;
        }
        result = tryBigIntegerGreaterThanOrEqualTo(s);
        if (result.whichSlot() == Either.Slot.A || !result.b().isEmpty()) {
            return result;
        }
        result = tryBigIntegerLessThan(s);
        if (result.whichSlot() == Either.Slot.A || !result.b().isEmpty()) {
            return result;
        }
        result = tryBigIntegerGreaterThan(s);
        if (result.whichSlot() == Either.Slot.A || !result.b().isEmpty()) {
            return result;
        }
        result = tryBigIntegerEqual(s);
        if (result.whichSlot() == Either.Slot.A || !result.b().isEmpty()) {
            return result;
        }
        result = tryBigIntegerNotEqual(s);
        if (result.whichSlot() == Either.Slot.A || !result.b().isEmpty()) {
            return result;
        }
        result = tryBooleanNot(s);
        if (result.whichSlot() == Either.Slot.A || !result.b().isEmpty()) {
            return result;
        }
        return tryParseBoolean(s);
    }

    public static @NotNull String interpret(@NotNull String s) {
        Either<BigInteger, String> bigIntegerResult = readBigInteger(s);
        if (bigIntegerResult.whichSlot() == Either.Slot.A) {
            return bigIntegerResult.a().toString();
        }
        Either<Boolean, String> booleanResult = readBoolean(s);
        if (booleanResult.whichSlot() == Either.Slot.A) {
            return booleanResult.a().toString();
        }

        if (!bigIntegerResult.b().isEmpty()) {
            return "Error: " + bigIntegerResult.b();
        } else if (!booleanResult.b().isEmpty()) {
            return "Error: " + booleanResult.b();
        } else {
            return "Mysterious error";
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader lineReader = new BufferedReader(new InputStreamReader(System.in));
        //noinspection InfiniteLoopStatement
        while (true) {
            System.out.print("> ");
            String line = lineReader.readLine();
            System.out.println(interpret(line));
        }
    }
}
