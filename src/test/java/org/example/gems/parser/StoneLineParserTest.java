package org.example.gems.parser;

import org.example.gems.entity.StoneType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StoneLineParserTest {
    private final StoneLineParser parser = new StoneLineParser();

    @Test
    void parsesValidLine() {
        StoneAttributes attributes = parser
                .parse(" diamond ; carat=1.2; PRICE = 8000 ;transparency=98; cut=round; color=E")
                .orElseThrow();

        assertEquals(StoneType.DIAMOND, attributes.getType());
        assertEquals(1.2, attributes.getCaratWeight());
        assertEquals(8000, attributes.getPricePerCarat());
        assertEquals(98, attributes.getTransparency());
        assertEquals("round", attributes.getString(AttributeKey.CUT, "none"));
        assertEquals("E", attributes.getString(AttributeKey.COLOR, "none"));
    }

    @Test
    void ignoresMalformedFieldsButKeepsLine() {
        StoneAttributes attributes = parser
                .parse("OPAL; carat=2; price=200; transparency=35; broken; =empty-key; ; playOfColor=true")
                .orElseThrow();

        assertTrue(attributes.getBoolean(AttributeKey.PLAY_OF_COLOR, false));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "# comment", "   # indented comment"})
    void skipsBlankAndCommentLines(String line) {
        assertEquals(Optional.empty(), parser.parse(line));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "PEARL; carat=1; price=100; transparency=10",
            "just some garbage text",
            "RUBY; carat=-1; price=3000; transparency=80",
            "RUBY; carat=0; price=3000; transparency=80",
            "RUBY; carat=501; price=3000; transparency=80",
            "RUBY; carat=abc; price=3000; transparency=80",
            "RUBY; carat=NaN; price=3000; transparency=80",
            "RUBY; price=3000; transparency=80",
            "RUBY; carat=1; price=0; transparency=80",
            "RUBY; carat=1; price=Infinity; transparency=80",
            "RUBY; carat=1; price=1e8; transparency=80",
            "RUBY; carat=1; price=NaN; transparency=80",
            "RUBY; carat=1; transparency=80",
            "RUBY; carat=1; price=3000; transparency=-0.1",
            "RUBY; carat=1; price=3000; transparency=100.1",
            "RUBY; carat=1; price=3000"
    })
    void skipsLineWithInvalidTypeOrRequiredField(String line) {
        assertEquals(Optional.empty(), parser.parse(line));
    }

    @Test
    void acceptsBoundaryValues() {
        assertTrue(parser.parse("TOPAZ; carat=0.01; price=0.5; transparency=0").isPresent());
        assertTrue(parser.parse("TOPAZ; carat=500; price=1; transparency=100").isPresent());
    }
}
