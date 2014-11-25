package mho.wheels.misc;

import org.junit.Test;

import static mho.wheels.misc.Readers.*;
import static org.junit.Assert.*;

public class ReadersTest {
    @Test
    public void testReadBoolean() {
        aeq(readBoolean("false"), "Optional[false]");
        aeq(readBoolean("true"), "Optional[true]");
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

    public void testReadOrdering() {
        aeq(readOrdering("LT"), "Optional[LT]");
        aeq(readOrdering("EQ"), "Optional[EQ]");
        aeq(readOrdering("GT"), "Optional[GT]");
        assertFalse(readOrdering(" LT").isPresent());
        assertFalse(readOrdering("eq").isPresent());
        assertFalse(readOrdering("gt ").isPresent());
        assertFalse(readOrdering("").isPresent());
        assertFalse(readOrdering("dsfsdfgd").isPresent());
    }

    @Test
    public void testFindOrderingIn() {
        aeq(findOrderingIn("EQ"), "Optional[(EQ, 0)]");
        aeq(findOrderingIn("LT"), "Optional[(LT, 0)]");
        aeq(findOrderingIn("BELT"), "Optional[(LT, 2)]");
        aeq(findOrderingIn("EGGTOWER"), "Optional[(GT, 2)]");
        aeq(findOrderingIn("hello"), "Optional.empty");
        aeq(findOrderingIn(""), "Optional.empty");
    }

    private static void aeq(Object a, Object b) {
        assertEquals(a.toString(), b.toString());
    }
}