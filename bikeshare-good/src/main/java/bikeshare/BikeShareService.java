package bikeshare;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public final class BikeShareService {
    private final Map<String, ArrayDeque<String>> available = new LinkedHashMap<>();
    private final Map<String, Rental> active = new HashMap<>();
    private final FeeCalculator fees;
    private final RideRepository rides;

    private record Rental(String bikeId, MemberType type, String startStation) {}

    public BikeShareService(FeeCalculator fees, RideRepository rides) {
        this.fees = fees;
        this.rides = rides;
        int number = 1;
        for (String station : new String[] {"Library", "Canteen", "Dorm"}) {
            ArrayDeque<String> bikes = new ArrayDeque<>();
            for (int i = 0; i < 2; i++) bikes.add("B" + number++);
            available.put(station, bikes);
        }
    }

    public String rent(String memberId, MemberType type, String station) {
        checkMemberId(memberId);
        ArrayDeque<String> bikes = station(station);
        if (active.containsKey(memberId)) throw new IllegalArgumentException("Member already has a bike");
        if (bikes.isEmpty()) throw new IllegalArgumentException("No bikes available at " + station);
        String bikeId = bikes.removeFirst();
        active.put(memberId, new Rental(bikeId, type, station));
        return bikeId;
    }

    public Ride returnBike(String memberId, String station, int minutes) throws IOException {
        checkMemberId(memberId);
        ArrayDeque<String> destination = station(station);
        Rental rental = active.get(memberId);
        if (rental == null) throw new IllegalArgumentException("Member has no rented bike");
        long fee = fees.calculate(rental.type(), minutes);
        Ride ride = new Ride(memberId, rental.type(), rental.bikeId(),
                rental.startStation(), station, minutes, fee);
        rides.save(ride); // Keep the rental active if saving fails, so it can be retried.
        active.remove(memberId);
        destination.addLast(rental.bikeId());
        return ride;
    }

    public String availability() {
        StringBuilder result = new StringBuilder();
        available.forEach((station, bikes) -> result.append(station).append(": ")
                .append(bikes.size()).append(" available").append(System.lineSeparator()));
        return result.toString();
    }

    private ArrayDeque<String> station(String name) {
        ArrayDeque<String> bikes = available.get(name);
        if (bikes == null) throw new IllegalArgumentException("Station must be Library, Canteen, or Dorm");
        return bikes;
    }

    private void checkMemberId(String id) {
        if (id == null || !id.matches("[A-Za-z0-9_-]+"))
            throw new IllegalArgumentException("Member ID must contain only letters, digits, _ or -");
    }
}
