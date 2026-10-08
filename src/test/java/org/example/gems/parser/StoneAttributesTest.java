package org.example.gems.parser;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.example.gems.entity.CutType;
import org.example.gems.entity.StoneType;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StoneAttributesTest {
    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    private static StoneAttributes withFields(Map<String, String> fields) {
        return new StoneAttributes(StoneType.RUBY, 2.0, 3000.0, 80.0, fields);
    }

    @Test
    void returnsCommonAttributes() {
        StoneAttributes attributes = withFields(Map.of());

        assertEquals(StoneType.RUBY, attributes.getType());
        assertEquals(2, attributes.getCaratWeight());
        assertEquals(3000, attributes.getPricePerCarat());
        assertEquals(80, attributes.getTransparency());
    }

    @Test
    void copiesFields() {
        Map<String, String> fields = new HashMap<>(Map.of("origin", "Zambia"));
        StoneAttributes attributes = withFields(fields);
        fields.put("origin", "Brazil");

        assertEquals("Zambia", attributes.getString("origin", "Unknown"));
    }

    @Test
    void passesValidationWithCorrectRequiredAttributes() {
        assertTrue(VALIDATOR.validate(withFields(Map.of())).isEmpty());
    }

    @Test
    void reportsEveryInvalidRequiredAttribute() {
        StoneAttributes attributes = new StoneAttributes(null, 0.0, Double.POSITIVE_INFINITY, null, Map.of());

        Set<String> invalidProperties = VALIDATOR.validate(attributes).stream()
                .map(violation -> violation.getPropertyPath().toString())
                .collect(Collectors.toSet());

        assertEquals(Set.of("type", "caratWeight", "pricePerCarat", "transparency"), invalidProperties);
    }

    @Test
    void returnsStringOrDefault() {
        StoneAttributes attributes = withFields(Map.of("origin", "Colombia", "blank", " "));

        assertEquals("Colombia", attributes.getString("origin", "Unknown"));
        assertEquals("Unknown", attributes.getString("blank", "Unknown"));
        assertEquals("Unknown", attributes.getString("missing", "Unknown"));
    }

    @Test
    void returnsBooleanOrDefault() {
        StoneAttributes attributes = withFields(Map.of("yes", "TRUE", "no", "false", "bad", "yes"));

        assertTrue(attributes.getBoolean("yes", false));
        assertFalse(attributes.getBoolean("no", true));
        assertTrue(attributes.getBoolean("bad", true));
        assertFalse(attributes.getBoolean("missing", false));
    }

    @Test
    void returnsDoubleInRangeOrDefault() {
        StoneAttributes attributes = withFields(Map.of(
                "ok", "7.5", "low", "0.5", "high", "11", "min", "1", "max", "10", "text", "abc"));

        assertEquals(7.5, attributes.getDouble("ok", 1, 10, 5));
        assertEquals(1, attributes.getDouble("min", 1, 10, 5));
        assertEquals(10, attributes.getDouble("max", 1, 10, 5));
        assertEquals(5, attributes.getDouble("low", 1, 10, 5));
        assertEquals(5, attributes.getDouble("high", 1, 10, 5));
        assertEquals(5, attributes.getDouble("text", 1, 10, 5));
        assertEquals(5, attributes.getDouble("missing", 1, 10, 5));
    }

    @Test
    void returnsEnumOrDefault() {
        StoneAttributes attributes = withFields(Map.of("cut", " pear ", "bad", "TRIANGLE"));

        assertEquals(CutType.PEAR, attributes.getEnum("cut", CutType.class, CutType.ROUND));
        assertEquals(CutType.ROUND, attributes.getEnum("bad", CutType.class, CutType.ROUND));
        assertEquals(CutType.ROUND, attributes.getEnum("missing", CutType.class, CutType.ROUND));
    }
}
