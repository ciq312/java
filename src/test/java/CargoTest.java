import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class CargoTest {
    @Test
    public void constructor_ShouldRejectBlankName() {
        assertThrows(IllegalArgumentException.class, () -> new Cargo(" ", 1, false, BigDecimal.ONE));
    }

    @Test
    public void constructor_ShouldRejectNonPositiveWeight() {
        assertThrows(IllegalArgumentException.class, () -> new Cargo("Coal", 0, false, BigDecimal.ONE));
    }

    @Test
    public void constructor_ShouldRejectNegativeValue() {
        assertThrows(IllegalArgumentException.class, () -> new Cargo("Coal", 1, false, new BigDecimal("-1")));
    }

    @Test
    public void constructor_ShouldRejectMissingValue() {
        assertThrows(NullPointerException.class, () -> new Cargo("Coal", 1, false, null));
    }
}
