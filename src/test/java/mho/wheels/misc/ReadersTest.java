package mho.wheels.misc;

import org.junit.Test;

import java.util.Optional;

import static mho.wheels.misc.Readers.*;
import static org.junit.Assert.*;

public class ReadersTest {
    @Test
    public void testReadBoolean() {
        aeq(readBoolean("false"), Optional.of(false));
        aeq(readBoolean("true"), Optional.of(true));
        assertFalse(readBoolean(" true").isPresent());
        assertFalse(readBoolean("TRUE").isPresent());
        assertFalse(readBoolean("true ").isPresent());
        assertFalse(readBoolean("").isPresent());
        assertFalse(readBoolean("dsfsdfgd").isPresent());
    }

    @Test
    public void testFindBooleanIn() {
        aeq(findBooleanIn("true"), "Optional[(true, 0)]");
        aeq(findBooleanIn("false"), "Optional[(false, 0)]");
        aeq(findBooleanIn("xxtruefalsexx"), "Optional[(true, 2)]");
        aeq(findBooleanIn("xxfalsetruexx"), "Optional[(false, 2)]");
        aeq(findBooleanIn("hello"), "Optional.empty");
        aeq(findBooleanIn(""), "Optional.empty");
    }

    private static void aeq(Object a, Object b) {
        assertEquals(a.toString(), b.toString());
    }
}