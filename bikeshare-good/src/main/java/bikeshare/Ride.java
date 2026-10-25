package bikeshare;

public record Ride(String memberId, MemberType memberType, String bikeId,
                   String startStation, String endStation, int minutes, long fee) {}
