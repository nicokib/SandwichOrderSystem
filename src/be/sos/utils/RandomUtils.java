package be.sos.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public final class RandomUtils {

    private RandomUtils() {
        // Utility class
    }

    /**
     * Returns a random integer between min and max (inclusive).
     */
    public static int randomInt(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("min must be <= max");
        }

        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    /**
     * Returns a random long between min and max (inclusive).
     */
    public static long randomLong(long min, long max) {
        if (min > max) {
            throw new IllegalArgumentException("min must be <= max");
        }

        return ThreadLocalRandom.current().nextLong(min, max + 1);
    }

    /**
     * Returns a random boolean value.
     */
    public static boolean randomBoolean() {
        return ThreadLocalRandom.current().nextBoolean();
    }

    /**
     * Returns a random date between start and end (inclusive).
     */
    public static LocalDate randomDate(LocalDate start, LocalDate end) {
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("start must be <= end");
        }

        long days = ChronoUnit.DAYS.between(start, end);

        return start.plusDays(
                ThreadLocalRandom.current().nextLong(days + 1)
        );
    }

    /**
     * Returns a random date and time between start and end (inclusive).
     */
    public static LocalDateTime randomDateTime(
            LocalDateTime start,
            LocalDateTime end) {

        if (start.isAfter(end)) {
            throw new IllegalArgumentException("start must be <= end");
        }

        long seconds = ChronoUnit.SECONDS.between(start, end);

        return start.plusSeconds(
                ThreadLocalRandom.current().nextLong(seconds + 1)
        );
    }

    /**
     * Returns a random element from a list.
     */
    public static <T> T randomElement(List<T> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("List cannot be null or empty");
        }

        return list.get(randomInt(0, list.size() - 1));
    }

    /**
     * Returns a random value from an Enum.
     */
    public static <E extends Enum<E>> E randomEnum(Class<E> enumClass) {
        E[] values = enumClass.getEnumConstants();

        if (values == null || values.length == 0) {
            throw new IllegalArgumentException("Invalid or empty Enum");
        }

        return values[randomInt(0, values.length - 1)];
    }
}