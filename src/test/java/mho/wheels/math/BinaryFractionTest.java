package mho.wheels.math;

import org.junit.Test;

import static mho.wheels.math.BinaryFraction.*;
import static mho.wheels.testing.Testing.aeq;

public class BinaryFractionTest {
    @Test
    public void testConstants() {
        aeq(ZERO, "0");
        aeq(ONE, "1");
    }
}
