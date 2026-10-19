public interface PassengerCarrier {
    void boardPassengers(int count);

    // Returns how many passengers left
    int disembarkPassengers();

    int getPassengerCount();

    int getFreeSeats();
}
