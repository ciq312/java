import java.util.List;

public class FreightWagon extends AbstractWagon implements Sealable {
    private final double loadCapacity;
    private boolean sealed;

    public FreightWagon(String registrationNumber, String owner, String destination,
                        double curbWeight, double loadCapacity) {
        super(registrationNumber, owner, destination, curbWeight);
        if (loadCapacity <= 0) {
            throw new IllegalArgumentException("Load capacity must be positive");
        }
        this.loadCapacity = loadCapacity;
    }

    public double getLoadCapacity() {
        return loadCapacity;
    }

    @Override
    public void seal() {
        sealed = true;
    }

    @Override
    public void breakSeal() {
        sealed = false;
    }

    @Override
    public boolean isSealed() {
        return sealed;
    }

    @Override
    public void load(Cargo cargo) {
        requireUnsealed();
        if (getCargoWeight() + cargo.weight() > loadCapacity) {
            throw new IllegalStateException(
                    "Cargo " + cargo.name() + " exceeds load capacity of wagon " + getRegistrationNumber());
        }
        super.load(cargo);
    }

    @Override
    public List<Cargo> unload() {
        requireUnsealed();
        return super.unload();
    }

    @Override
    public void sendToDestination() {
        if (!sealed && getCargoWeight() > 0) {
            throw new IllegalStateException("Loaded wagon " + getRegistrationNumber() + " must be sealed before departure");
        }
        super.sendToDestination();
    }

    private void requireUnsealed() {
        if (sealed) {
            throw new IllegalStateException("Wagon " + getRegistrationNumber() + " is sealed");
        }
    }
}
