import java.math.BigDecimal;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        FreightWagon freight = new FreightWagon("52345678", "Russian Railways", "Yekaterinburg", 24, 69);
        freight.load(new Cargo("Steel pipes", 40, false, new BigDecimal("1200000")));
        freight.load(new Cargo("Glassware", 10, true, new BigDecimal("800000")));
        System.out.println(freight + ", cargo " + freight.getCargoWeight() + " of " + freight.getLoadCapacity() + " t"
                + ", fragile: " + freight.isCargoFragile() + ", value: " + freight.getCargoValue());

        freight.seal();
        freight.service();
        freight.sendToDestination();
        System.out.println(freight);

        freight.breakSeal();
        System.out.println("Unloaded: " + freight.unload());
        freight.reportMalfunction();
        freight.repair();
        System.out.println(freight);

        PassengerWagon passenger = new PassengerWagon("02345678", "FPK", "Moscow", 52, 54);
        passenger.boardPassengers(40);
        passenger.load(new Cargo("Luggage", 1.5, false, new BigDecimal("300000")));
        passenger.service();
        passenger.sendToDestination();
        System.out.println(passenger + ", passengers: " + passenger.getPassengerCount()
                + ", free seats: " + passenger.getFreeSeats());

        System.out.println("Disembarked: " + passenger.disembarkPassengers() + ", unloaded: " + passenger.unload());
        passenger.setDestination("Saint Petersburg");

        for (Wagon wagon : List.of(freight, passenger)) {
            wagon.service();
            System.out.println(wagon + ", curb weight " + wagon.getCurbWeight() + " t");
        }
    }
}
