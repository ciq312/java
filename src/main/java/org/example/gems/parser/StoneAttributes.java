package org.example.gems.parser;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.gems.entity.StoneType;

import java.util.Locale;
import java.util.Map;
import java.util.Optional;

public class StoneAttributes {
    private static final Logger logger = LogManager.getLogger();

    @NotNull(message = "unknown or missing stone type")
    private final StoneType type;

    @NotNull
    @DecimalMin("0.01")
    @DecimalMax("500")
    private final Double caratWeight;

    @NotNull
    @Positive
    @DecimalMax("10000000")
    private final Double pricePerCarat;

    @NotNull
    @DecimalMin("0")
    @DecimalMax("100")
    private final Double transparency;

    private final Map<String, String> fields;

    public StoneAttributes(StoneType type, Double caratWeight, Double pricePerCarat, Double transparency,
                           Map<String, String> fields) {
        this.type = type;
        this.caratWeight = caratWeight;
        this.pricePerCarat = pricePerCarat;
        this.transparency = transparency;
        this.fields = Map.copyOf(fields);
    }

    public StoneType getType() {
        return type;
    }

    public double getCaratWeight() {
        return caratWeight;
    }

    public double getPricePerCarat() {
        return pricePerCarat;
    }

    public double getTransparency() {
        return transparency;
    }

    public String getString(String key, String defaultValue) {
        String value = fields.get(key);
        if (value == null || value.isBlank()) {
            return useDefault(key, value, defaultValue);
        }
        return value;
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        String value = fields.get(key);
        if (Boolean.TRUE.toString().equalsIgnoreCase(value)) {
            return true;
        }
        if (Boolean.FALSE.toString().equalsIgnoreCase(value)) {
            return false;
        }
        return useDefault(key, value, defaultValue);
    }

    public double getDouble(String key, double min, double max, double defaultValue) {
        String value = fields.get(key);
        Optional<Double> parsed = ValueParser.parseDouble(value);
        if (parsed.isPresent() && parsed.get() >= min && parsed.get() <= max) {
            return parsed.get();
        }
        return useDefault(key, value, defaultValue);
    }

    public <E extends Enum<E>> E getEnum(String key, Class<E> enumType, E defaultValue) {
        String value = fields.get(key);
        if (value == null) {
            return useDefault(key, null, defaultValue);
        }
        try {
            return Enum.valueOf(enumType, value.strip().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            return useDefault(key, value, defaultValue);
        }
    }

    private <T> T useDefault(String key, String rawValue, T defaultValue) {
        logger.warn("{}: invalid or missing '{}' = '{}', default '{}' used", type, key, rawValue, defaultValue);
        return defaultValue;
    }
}
