package mho.wheels.misc;

import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class ReadersTest {
    @Test
    public void testReadBoolean() {
        aeq(Readers.readBoolean("false"), Optional.of(false));
        aeq(Readers.readBoolean("true"), Optional.of(true));
        assertFalse(Readers.readBoolean(" true").isPresent());
        assertFalse(Readers.readBoolean("TRUE").isPresent());
        assertFalse(Readers.readBoolean("true ").isPresent());
        assertFalse(Readers.readBoolean("").isPresent());
        assertFalse(Readers.readBoolean("dsfsdfgd").isPresent());
    }

    private static void aeq(Object a, Object b) {
        assertEquals(a.toString(), b.toString());
    }
}