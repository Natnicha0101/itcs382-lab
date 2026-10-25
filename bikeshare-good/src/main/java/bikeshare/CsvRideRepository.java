package bikeshare;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public final class CsvRideRepository implements RideRepository {
    private final Path path;

    public CsvRideRepository(Path path) {
        this.path = path;
    }

    @Override
    public void save(Ride ride) throws IOException {
        // IDs, bike names, and station names are validated before reaching this class.
        String row = String.join(",", ride.memberId(), ride.memberType().name(),
                ride.bikeId(), ride.startStation(), ride.endStation(),
                Integer.toString(ride.minutes()), Long.toString(ride.fee())) + System.lineSeparator();
        Files.writeString(path, row, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }
}
