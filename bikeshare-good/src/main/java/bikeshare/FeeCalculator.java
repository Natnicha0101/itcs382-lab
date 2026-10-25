package bikeshare;

public final class FeeCalculator {
    public long calculate(MemberType type, int minutes) {
        if (minutes <= 0) throw new IllegalArgumentException("Minutes must be positive");
        return (long) type.bahtPerMinute() * minutes;
    }
}
