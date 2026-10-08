package org.example.gems.parser;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.gems.entity.StoneType;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class StoneLineParser {
    private static final Logger logger = LogManager.getLogger();
    private static final Validator DEFAULT_VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();
    private static final String FIELD_DELIMITER = ";";
    private static final char KEY_VALUE_DELIMITER = '=';
    private static final String COMMENT_PREFIX = "#";

    private final Validator validator;

    public StoneLineParser() {
        this(DEFAULT_VALIDATOR);
    }

    public StoneLineParser(Validator validator) {
        this.validator = validator;
    }

    public Optional<StoneAttributes> parse(String line) {
        if (line == null || line.isBlank() || line.strip().startsWith(COMMENT_PREFIX)) {
            return Optional.empty();
        }
        String[] tokens = line.split(FIELD_DELIMITER);
        Map<String, String> fields = parseFields(tokens, line);
        StoneAttributes attributes = new StoneAttributes(parseType(tokens[0]),
                parseNumber(fields, AttributeKey.CARAT),
                parseNumber(fields, AttributeKey.PRICE),
                parseNumber(fields, AttributeKey.TRANSPARENCY),
                fields);

        Set<ConstraintViolation<StoneAttributes>> violations = validator.validate(attributes);
        if (!violations.isEmpty()) {
            logger.error("Line '{}' skipped: {}", line, describe(violations));
            return Optional.empty();
        }
        logger.debug("Parsed line '{}'", line);
        return Optional.of(attributes);
    }

    private StoneType parseType(String token) {
        try {
            return StoneType.valueOf(token.strip().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private Double parseNumber(Map<String, String> fields, String key) {
        return ValueParser.parseDouble(fields.get(key)).orElse(null);
    }

    private Map<String, String> parseFields(String[] tokens, String line) {
        Map<String, String> fields = new HashMap<>();
        for (int i = 1; i < tokens.length; i++) {
            String token = tokens[i].strip();
            int delimiterIndex = token.indexOf(KEY_VALUE_DELIMITER);
            if (delimiterIndex <= 0) {
                logger.warn("Malformed field '{}' in line '{}', field ignored", token, line);
                continue;
            }
            String key = token.substring(0, delimiterIndex).strip().toLowerCase(Locale.ROOT);
            fields.put(key, token.substring(delimiterIndex + 1).strip());
        }
        return fields;
    }

    private String describe(Set<ConstraintViolation<StoneAttributes>> violations) {
        return violations.stream()
                .map(violation -> violation.getPropertyPath() + " = '" + violation.getInvalidValue() + "' "
                        + violation.getMessage())
                .sorted()
                .collect(Collectors.joining("; "));
    }
}
