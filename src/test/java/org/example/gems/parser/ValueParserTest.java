package org.example.gems.parser;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ValueParserTest {
    @Test
    void parsesNumberIgnoringSpaces() {
        assertEquals(Optional.of(1.5), ValueParser.parseDouble(" 1.5 "));
    }

    @Test
    void returnsEmptyForNullOrText() {
        assertEquals(Optional.empty(), ValueParser.parseDouble(null));
        assertEquals(Optional.empty(), ValueParser.parseDouble("1,5"));
        assertEquals(Optional.empty(), ValueParser.parseDouble(""));
    }
}
