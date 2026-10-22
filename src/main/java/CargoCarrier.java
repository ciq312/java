import java.math.BigDecimal;
import java.util.List;

public interface CargoCarrier {
    void load(Cargo cargo);

    List<Cargo> unload();

    double getCargoWeight();

    boolean isCargoFragile();

    BigDecimal getCargoValue();
}
