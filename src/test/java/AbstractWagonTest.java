import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AbstractWagonTest {
    private final Cargo coal = new Cargo("Coal", 30, false, new BigDecimal("100000"));
    private final Cargo glass = new Cargo("Glass", 5, true, new BigDecimal("250000"));
    private Wagon wagon;

    @BeforeEach
    public void setUp() {
        wagon = new AbstractWagon("12345678", "Russian Railways", "Moscow", 25) {
        };
    }

    @Test
    public void newWagon_ShouldBeEmptyAndReadyForDeparture() {
        assertEquals(WagonStatus.READY_FOR_DEPARTURE, wagon.getStatus());
        assertEquals(25, wagon.getCurbWeight());
        assertEquals(0, wagon.getCargoWeight());
        assertFalse(wagon.isCargoFragile());
        assertEquals(BigDecimal.ZERO, wagon.getCargoValue());
    }

    @Test
    public void constructor_ShouldRejectInvalidInfo() {
        assertThrows(IllegalArgumentException.class, () -> new AbstractWagon(" ", "Owner", "Moscow", 25) {
        });
        assertThrows(IllegalArgumentException.class, () -> new AbstractWagon("12345678", null, "Moscow", 25) {
        });
        assertThrows(IllegalArgumentException.class, () -> new AbstractWagon("12345678", "Owner", "", 25) {
        });
        assertThrows(IllegalArgumentException.class, () -> new AbstractWagon("12345678", "Owner", "Moscow", 0) {
        });
    }

    @Test
    public void setters_ShouldChangeInfo() {
        wagon.setRegistrationNumber("87654321");
        wagon.setOwner("Freight One");
        wagon.setDestination("Kazan");

        assertEquals("87654321", wagon.getRegistrationNumber());
        assertEquals("Freight One", wagon.getOwner());
        assertEquals("Kazan", wagon.getDestination());
    }

    @Test
    public void setters_ShouldRejectBlankInfo() {
        assertThrows(IllegalArgumentException.class, () -> wagon.setRegistrationNumber(""));
        assertThrows(IllegalArgumentException.class, () -> wagon.setOwner(" "));
        assertThrows(IllegalArgumentException.class, () -> wagon.setDestination(null));
        assertEquals("Moscow", wagon.getDestination());
    }

    @Test
    public void load_ShouldAddCargoAndStartLoading() {
        wagon.load(coal);
        wagon.load(glass);

        assertEquals(WagonStatus.LOADING, wagon.getStatus());
        assertEquals(35, wagon.getCargoWeight());
        assertTrue(wagon.isCargoFragile());
        assertEquals(new BigDecimal("350000"), wagon.getCargoValue());
    }

    @Test
    public void unload_ShouldReturnAllCargoOnArrival() {
        wagon.load(coal);
        wagon.load(glass);
        wagon.service();
        wagon.sendToDestination();

        List<Cargo> unloaded = wagon.unload();

        assertEquals(List.of(coal, glass), unloaded);
        assertEquals(WagonStatus.UNLOADING, wagon.getStatus());
        assertEquals(0, wagon.getCargoWeight());
    }

    @Test
    public void sendToDestination_ShouldRequireServiceAfterLoading() {
        wagon.load(coal);

        assertThrows(IllegalStateException.class, wagon::sendToDestination);
        assertEquals(WagonStatus.LOADING, wagon.getStatus());

        wagon.service();
        wagon.sendToDestination();
        assertEquals(WagonStatus.IN_TRANSIT, wagon.getStatus());
    }

    @Test
    public void load_ShouldBeRejectedInTransit() {
        wagon.sendToDestination();

        assertThrows(IllegalStateException.class, () -> wagon.load(coal));
        assertEquals(0, wagon.getCargoWeight());
        assertEquals(WagonStatus.IN_TRANSIT, wagon.getStatus());
    }

    @Test
    public void repair_ShouldFixMalfunctionTogetherWithService() {
        wagon.sendToDestination();
        wagon.reportMalfunction();
        assertEquals(WagonStatus.REPAIR_REQUIRED, wagon.getStatus());
        assertThrows(IllegalStateException.class, wagon::service);
        assertThrows(IllegalStateException.class, wagon::sendToDestination);

        wagon.repair();
        assertEquals(WagonStatus.IN_REPAIR, wagon.getStatus());
        assertThrows(IllegalStateException.class, wagon::sendToDestination);

        wagon.service();
        assertEquals(WagonStatus.READY_FOR_DEPARTURE, wagon.getStatus());
    }

    @Test
    public void repair_ShouldRequireMalfunction() {
        assertThrows(IllegalStateException.class, wagon::repair);
        assertEquals(WagonStatus.READY_FOR_DEPARTURE, wagon.getStatus());
    }
}
