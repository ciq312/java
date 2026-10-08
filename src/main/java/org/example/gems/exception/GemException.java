package org.example.gems.exception;

public class

GemException extends Exception {
    public GemException(String message) {
        super(message);
    }

    public GemException(String message, Throwable cause) {
        super(message, cause);
    }
}
