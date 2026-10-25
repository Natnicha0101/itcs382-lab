package bikeshare;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

public class BikeShare {
    public static final String[] STATIONS = {"Library", "Canteen", "Dorm"};
    public static final String[] BIKE_IDS = {"B1", "B2", "B3", "B4", "B5", "B6"};
    public static final int[] BIKE_STATIONS = {0, 0, 1, 1, 2, 2};
    public static final String[] RIDERS = new String[6];
    public static final int[] MEMBER_TYPES = new int[6]; // 1 = student, 2 = staff
    public static final Path FILE = Path.of("rides.csv");

    public static void main(String[] args) {
        try (Scanner input = new Scanner(System.in)) {
            System.out.println("Commands: rent <memberId> <student|staff> <Library|Canteen|Dorm>, return <memberId> <station> <minutes>, list, quit");
            while (input.hasNextLine()) {
                String[] parts = input.nextLine().trim().split("\\s+");
                try {
                    if (parts[0].equalsIgnoreCase("quit")) {
                        break;
                    } else if (parts[0].equalsIgnoreCase("list")) {
                        show(true);
                    } else if (parts[0].equalsIgnoreCase("rent") && parts.length == 4) {
                        rent(parts[1], memberType(parts[2]), station(parts[3]));
                    } else if (parts[0].equalsIgnoreCase("return") && parts.length == 4) {
                        returnBike(parts[1], station(parts[2]), Integer.parseInt(parts[3]));
                    } else {
                        System.out.println("Invalid command or argument count.");
                    }
                } catch (IllegalArgumentException | IOException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }
    }

    public static int station(String name) {
        for (int i = 0; i < STATIONS.length; i++) {
            if (STATIONS[i].equalsIgnoreCase(name)) return i;
        }
        throw new IllegalArgumentException("Unknown station: " + name);
    }

    public static int memberType(String name) {
        if (name.equalsIgnoreCase("student")) return 1;
        if (name.equalsIgnoreCase("staff")) return 2;
        throw new IllegalArgumentException("Unknown member type: " + name);
    }

    public static void rent(String member, int type, int station) {
        if (member.isBlank() || member.contains(",")) throw new IllegalArgumentException("Invalid member ID.");
        for (String rider : RIDERS) {
            if (member.equals(rider)) throw new IllegalArgumentException("Member already has a bike.");
        }
        for (int i = 0; i < BIKE_IDS.length; i++) {
            if (BIKE_STATIONS[i] == station && RIDERS[i] == null) {
                RIDERS[i] = member;
                MEMBER_TYPES[i] = type;
                System.out.println("Rented " + BIKE_IDS[i] + " at " + STATIONS[station]);
                return;
            }
        }
        System.out.println("No bike available at " + STATIONS[station]);
    }

    public static void returnBike(String member, int station, int minutes) throws IOException {
        if (minutes < 0) throw new IllegalArgumentException("Minutes cannot be negative.");
        for (int i = 0; i < BIKE_IDS.length; i++) {
            if (member.equals(RIDERS[i])) {
                int fee = minutes * (MEMBER_TYPES[i] == 1 ? 2 : 3);
                String row = BIKE_IDS[i] + "," + member + "," + STATIONS[station] + "," + minutes + "," + fee;
                try (BufferedWriter writer = Files.newBufferedWriter(FILE,
                        StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
                    writer.write(row);
                    writer.newLine();
                }
                BIKE_STATIONS[i] = station;
                RIDERS[i] = null;
                MEMBER_TYPES[i] = 0;
                System.out.println("Receipt: bike " + BIKE_IDS[i] + ", " + minutes + " minutes, " + fee + " baht");
                return;
            }
        }
        System.out.println("No active ride for " + member);
    }

    public static void show(boolean availableOnly) {
        for (int i = 0; i < BIKE_IDS.length; i++) {
            if (!availableOnly || RIDERS[i] == null) {
                System.out.println(BIKE_IDS[i] + " at " + STATIONS[BIKE_STATIONS[i]]
                        + (RIDERS[i] == null ? " available" : " rented by " + RIDERS[i]));
            }
        }
    }
}