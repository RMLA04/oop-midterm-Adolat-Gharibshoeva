package util;

/**
 * Utility class for generating unique person IDs.
 * Demonstrates Singleton pattern (implicit via static methods).
 */
public final class IdGenerator {
    private static int counter = 0;

    private IdGenerator() {
        // Prevent instantiation
    }

    public static String generateId() {
        counter++;
        return String.format("P%03d", counter);
    }

    public static void reset() {
        counter = 0;
    }
}