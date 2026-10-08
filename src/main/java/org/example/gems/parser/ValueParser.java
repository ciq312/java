package org.example.gems.parser;

import java.util.Optional;

public final class ValueParser {
    private ValueParser() {
    }

    public static Optional<Double> parseDouble(String value) {
        if (value == null) {
            return Optional.empty();
        }
        try {
            return Optional.of(Double.valueOf(value.strip()));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}
