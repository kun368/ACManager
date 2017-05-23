package jskills.numerics;

/**
 * A very limited implementation of an immutable range of Integers, including
 * endpoints. There is no such thing as an empty range.
 * <p>
 * The whole purpose of this class is to make the code for the
 * SkillCalculator(s) look a little cleaner
 * <p>
 * Derived classes can't use this class's static ctors they way they could in
 * C#, so I'm going to eschew the relative type safety afforded by Moser's
 * scheme and make this class final. A Range is a Range is a Range.
 */
public final class Range<T> {
    
	private final int min;
	private final int max;

	public Range(int min, int max) {
        if (min > max) throw new IllegalArgumentException();

        this.min = min;
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public boolean isInRange(int value) {
        return (min <= value) && (value <= max);
    }

    // REVIEW: It's probably bad form to have access statics via a derived
    // class, but the syntax looks better :-)

    // It's bad form in Java to the point where it won't compile. Using statics
    // through derived classes gets you a warning, but accessing generic types
    // (T) won't compile.  

    public static <T> Range<T> inclusive(int min, int max) {
        return new Range<T>(min, max);
    }

    public static <T> Range<T> exactly(int value) {
        return new Range<T>(value, value);
    }

    public static <T> Range<T> atLeast(int minimumValue) {
        return new Range<T>(minimumValue, Integer.MAX_VALUE);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Range<?> range = (Range<?>) o;

        return getMin() == range.getMin() && getMax() == range.getMax();

    }

    @Override
    public int hashCode() {
        int result = getMin();
        result = 31 * result + getMax();
        return result;
    }

    @Override
    public String toString() {
        return String.format("[%d, %d]", this.min, this.max);
    }
}