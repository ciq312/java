import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractWagon implements Wagon {
    private final List<Cargo> loadedCargo = new ArrayList<>();
    private final double curbWeight;
    private String registrationNumber;
    private String destination;
    private String owner;
    private WagonStatus status = WagonStatus.READY_FOR_DEPARTURE;

    protected AbstractWagon(String registrationNumber, String owner, String destination, double curbWeight) {
        if (curbWeight <= 0) {
            throw new IllegalArgumentException("Curb weight must be positive");
        }
        this.registrationNumber = requireNotBlank(registrationNumber, "Registration number");
        this.owner = requireNotBlank(owner, "Owner");
        this.destination = requireNotBlank(destination, "Destination");
        this.curbWeight = curbWeight;
    }

    @Override
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    @Override
    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = requireNotBlank(registrationNumber, "Registration number");
    }

    @Override
    public String getDestination() {
        return destination;
    }

    @Override
    public void setDestination(String destination) {
        this.destination = requireNotBlank(destination, "Destination");
    }

    @Override
    public String getOwner() {
        return owner;
    }

    @Override
    public void setOwner(String owner) {
        this.owner = requireNotBlank(owner, "Owner");
    }

    @Override
    public WagonStatus getStatus() {
        return status;
    }

    @Override
    public double getCurbWeight() {
        return curbWeight;
    }

    @Override
    public void sendToDestination() {
        changeStatus(WagonStatus.IN_TRANSIT);
    }

    @Override
    public void service() {
        changeStatus(WagonStatus.READY_FOR_DEPARTURE);
    }

    @Override
    public void repair() {
        changeStatus(WagonStatus.IN_REPAIR);
    }

    @Override
    public void reportMalfunction() {
        changeStatus(WagonStatus.REPAIR_REQUIRED);
    }

    @Override
    public void load(Cargo cargo) {
        Objects.requireNonNull(cargo, "cargo");
        changeStatus(WagonStatus.LOADING);
        loadedCargo.add(cargo);
    }

    @Override
    public List<Cargo> unload() {
        changeStatus(WagonStatus.UNLOADING);
        List<Cargo> unloaded = List.copyOf(loadedCargo);
        loadedCargo.clear();
        return unloaded;
    }

    @Override
    public double getCargoWeight() {
        return loadedCargo.stream().mapToDouble(Cargo::weight).sum();
    }

    @Override
    public boolean isCargoFragile() {
        return loadedCargo.stream().anyMatch(Cargo::fragile);
    }

    @Override
    public BigDecimal getCargoValue() {
        return loadedCargo.stream().map(Cargo::value).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    protected final void changeStatus(WagonStatus next) {
        if (!status.canChangeTo(next)) {
            throw new IllegalStateException(
                    "Wagon " + registrationNumber + " cannot change status from " + status + " to " + next);
        }
        status = next;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " " + registrationNumber
                + " (" + owner + " -> " + destination + ", " + status + ")";
    }

    private static String requireNotBlank(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return value;
    }
}
