public interface Wagon extends CargoCarrier, Maintainable {
    String getRegistrationNumber();

    void setRegistrationNumber(String registrationNumber);

    String getDestination();

    void setDestination(String destination);

    String getOwner();

    void setOwner(String owner);

    // Changed only by the wagon operations to keep the lifecycle consistent
    WagonStatus getStatus();

    // Weight of the empty wagon in tonnes
    double getCurbWeight();

    void sendToDestination();
}
