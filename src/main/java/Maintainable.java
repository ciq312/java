public interface Maintainable {
    // Completes loading, unloading or repair and makes the vehicle ready for departure
    void service();

    // Starts the repair of a broken vehicle; service() completes it
    void repair();

    void reportMalfunction();
}
