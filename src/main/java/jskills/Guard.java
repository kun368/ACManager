package jskills;

/**
 * Verifies argument contracts.
 * <p>
 * These are used until I figure out how to do this better in Java
 */
public class Guard {

    /**
     * No instances allowed
     */
    private Guard() { }

    public static void argumentNotNull(Object value, String parameterName) {
        if (value == null) {
            throw new NullPointerException(parameterName);
        }
    }

    public static void argumentIsValidIndex(int index, int count, String parameterName) {
        if ((index < 0) || (index >= count)) {
            throw new IndexOutOfBoundsException(parameterName);
        }
    }

    public static void argumentInRangeInclusive(double value, double min, double max, String parameterName) {
        if ((value < min) || (value > max)) {
            throw new IndexOutOfBoundsException(parameterName);
        }
    }
}