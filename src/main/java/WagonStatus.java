import java.util.EnumSet;
import java.util.Set;

public enum WagonStatus {
    LOADING,
    UNLOADING,
    IN_TRANSIT,
    IN_REPAIR,
    READY_FOR_DEPARTURE,
    REPAIR_REQUIRED;

    public boolean canChangeTo(WagonStatus next) {
        return nextStatuses().contains(next);
    }

    private Set<WagonStatus> nextStatuses() {
        return switch (this) {
            case READY_FOR_DEPARTURE -> EnumSet.of(READY_FOR_DEPARTURE, LOADING, UNLOADING, IN_TRANSIT, REPAIR_REQUIRED);
            case LOADING -> EnumSet.of(LOADING, UNLOADING, READY_FOR_DEPARTURE, REPAIR_REQUIRED);
            case UNLOADING -> EnumSet.of(UNLOADING, LOADING, READY_FOR_DEPARTURE, REPAIR_REQUIRED);
            case IN_TRANSIT -> EnumSet.of(UNLOADING, REPAIR_REQUIRED);
            case REPAIR_REQUIRED -> EnumSet.of(IN_REPAIR);
            case IN_REPAIR -> EnumSet.of(READY_FOR_DEPARTURE);
        };
    }
}
