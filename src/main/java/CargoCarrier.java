import java.math.BigDecimal;
import java.util.List;

public interface CargoCarrier {
    // Throws IllegalStateException when the cargo cannot be taken in the current state
    void load(Cargo cargo);

    // Returns everything that was on board
    List<Cargo> unload();

    double getCargoWeight();

    boolean isCargoFragile();

    BigDecimal getCargoValue();
}
