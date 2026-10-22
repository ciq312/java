// Cargo of a passenger wagon is the passengers' luggage
public class PassengerWagon extends AbstractWagon implements PassengerCarrier {
    private final int seatCount;
    private int passengerCount;

    public PassengerWagon(String registrationNumber, String owner, String destination,
                          double curbWeight, int seatCount) {
        super(registrationNumber, owner, destination, curbWeight);
        if (seatCount <= 0) {
            throw new IllegalArgumentException("Seat count must be positive");
        }
        this.seatCount = seatCount;
    }

    @Override
    public void boardPassengers(int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("Passenger count must be positive");
        }
        if (count > getFreeSeats()) {
            throw new IllegalStateException("Not enough free seats in wagon " + getRegistrationNumber());
        }
        changeStatus(WagonStatus.LOADING);
        passengerCount += count;
    }

    @Override
    public int disembarkPassengers() {
        changeStatus(WagonStatus.UNLOADING);
        int disembarked = passengerCount;
        passengerCount = 0;
        return disembarked;
    }

    @Override
    public int getPassengerCount() {
        return passengerCount;
    }

    @Override
    public int getFreeSeats() {
        return seatCount - passengerCount;
    }
}
