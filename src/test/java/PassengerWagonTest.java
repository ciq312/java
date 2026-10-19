import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PassengerWagonTest {
    private PassengerWagon wagon;

    @BeforeEach
    public void setUp() {
        wagon = new PassengerWagon("02345678", "FPK", "Moscow", 52, 54);
    }

    @Test
    public void constructor_ShouldRejectNonPositiveSeatCount() {
        assertThrows(IllegalArgumentException.class, () -> new PassengerWagon("02345678", "FPK", "Moscow", 52, 0));
    }

    @Test
    public void boardPassengers_ShouldTakeSeatsAndStartLoading() {
        wagon.boardPassengers(30);
        wagon.boardPassengers(10);

        assertEquals(40, wagon.getPassengerCount());
        assertEquals(14, wagon.getFreeSeats());
        assertEquals(WagonStatus.LOADING, wagon.getStatus());
    }

    @Test
    public void boardPassengers_ShouldRejectOverbooking() {
        wagon.boardPassengers(50);

        assertThrows(IllegalStateException.class, () -> wagon.boardPassengers(5));
        assertEquals(50, wagon.getPassengerCount());
    }

    @Test
    public void boardPassengers_ShouldRejectNonPositiveCount() {
        assertThrows(IllegalArgumentException.class, () -> wagon.boardPassengers(0));
    }

    @Test
    public void boardPassengers_ShouldBeRejectedInTransit() {
        wagon.sendToDestination();

        assertThrows(IllegalStateException.class, () -> wagon.boardPassengers(1));
        assertEquals(0, wagon.getPassengerCount());
    }

    @Test
    public void disembarkPassengers_ShouldFreeAllSeatsOnArrival() {
        wagon.boardPassengers(40);
        wagon.service();
        wagon.sendToDestination();

        assertEquals(40, wagon.disembarkPassengers());
        assertEquals(0, wagon.getPassengerCount());
        assertEquals(54, wagon.getFreeSeats());
        assertEquals(WagonStatus.UNLOADING, wagon.getStatus());
    }
}
