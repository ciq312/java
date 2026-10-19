import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FreightWagonTest {
    private final Cargo pipes = new Cargo("Steel pipes", 60, false, new BigDecimal("1000000"));
    private FreightWagon wagon;

    @BeforeEach
    public void setUp() {
        wagon = new FreightWagon("52345678", "Russian Railways", "Yekaterinburg", 24, 69);
    }

    @Test
    public void constructor_ShouldRejectNonPositiveLoadCapacity() {
        assertThrows(IllegalArgumentException.class,
                () -> new FreightWagon("52345678", "Russian Railways", "Yekaterinburg", 24, 0));
    }

    @Test
    public void load_ShouldRejectCargoOverCapacity() {
        wagon.load(pipes);

        assertThrows(IllegalStateException.class,
                () -> wagon.load(new Cargo("Sand", 10, false, BigDecimal.ONE)));
        assertEquals(60, wagon.getCargoWeight());
    }

    @Test
    public void load_ShouldRejectSealedWagon() {
        wagon.seal();

        assertThrows(IllegalStateException.class, () -> wagon.load(pipes));
        assertEquals(WagonStatus.READY_FOR_DEPARTURE, wagon.getStatus());
    }

    @Test
    public void unload_ShouldRejectSealedWagon() {
        wagon.load(pipes);
        wagon.seal();

        assertThrows(IllegalStateException.class, wagon::unload);
        assertEquals(60, wagon.getCargoWeight());
    }

    @Test
    public void sendToDestination_ShouldRequireSealForLoadedWagon() {
        wagon.load(pipes);
        wagon.service();

        assertThrows(IllegalStateException.class, wagon::sendToDestination);
        assertEquals(WagonStatus.READY_FOR_DEPARTURE, wagon.getStatus());
    }

    @Test
    public void sendToDestination_ShouldAllowEmptyUnsealedWagon() {
        wagon.sendToDestination();

        assertEquals(WagonStatus.IN_TRANSIT, wagon.getStatus());
    }

    @Test
    public void delivery_ShouldPassFromLoadingToUnloading() {
        wagon.load(pipes);
        wagon.seal();
        wagon.service();
        wagon.sendToDestination();
        assertTrue(wagon.isSealed());

        wagon.breakSeal();
        List<Cargo> unloaded = wagon.unload();

        assertFalse(wagon.isSealed());
        assertEquals(List.of(pipes), unloaded);
        assertEquals(WagonStatus.UNLOADING, wagon.getStatus());
    }
}
