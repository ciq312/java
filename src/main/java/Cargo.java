import java.math.BigDecimal;
import java.util.Objects;

public record Cargo(String name, double weight, boolean fragile, BigDecimal value) {
    public Cargo {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Cargo name must not be blank");
        }
        if (weight <= 0) {
            throw new IllegalArgumentException("Cargo weight must be positive");
        }
        Objects.requireNonNull(value, "value");
        if (value.signum() < 0) {
            throw new IllegalArgumentException("Cargo value must not be negative");
        }
    }
}
