package bikeshare;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Scanner;

public final class Main {
    private Main() {}

    public static void main(String[] args) {
        BikeShareService service = new BikeShareService(new FeeCalculator(),
                new CsvRideRepository(Path.of("rides.csv")));
        System.out.println("Commands: rent <memberId> <student|staff> <Library|Canteen|Dorm>, "
                + "return <memberId> <station> <minutes>, list, quit");
        try (Scanner input = new Scanner(System.in)) {
            while (input.hasNextLine()) {
                String[] words = input.nextLine().trim().split("\\s+");
                if (words[0].equalsIgnoreCase("quit") && words.length == 1) break;
                try {
                    switch (words[0].toLowerCase(Locale.ROOT)) {
                        case "rent" -> {
                            requireLength(words, 4);
                            MemberType type = MemberType.valueOf(words[2].toUpperCase(Locale.ROOT));
                            String bike = service.rent(words[1], type, words[3]);
                            System.out.println("Rented " + bike + " at " + words[3]);
                        }
                        case "return" -> {
                            requireLength(words, 4);
                            Ride ride = service.returnBike(words[1], words[2], Integer.parseInt(words[3]));
                            System.out.printf("Receipt: bike %s, %d minutes, %d baht%n",
                                    ride.bikeId(), ride.minutes(), ride.fee());
                        }
                        case "list" -> {
                            requireLength(words, 1);
                            System.out.print(service.availability());
                        }
                        default -> System.out.println("Unknown command");
                    }
                } catch (IllegalArgumentException | IOException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }
    }

    private static void requireLength(String[] words, int length) {
        if (words.length != length) throw new IllegalArgumentException("Wrong number of arguments");
    }
}
