package org.example.gems.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

class GemExceptionTest {
    @Test
    void keepsMessage() {
        GemException exception = new GemException("broken");

        assertEquals("broken", exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void keepsMessageAndCause() {
        Throwable cause = new IllegalStateException();
        GemException exception = new GemException("broken", cause);

        assertEquals("broken", exception.getMessage());
        assertSame(cause, exception.getCause());
    }
}
