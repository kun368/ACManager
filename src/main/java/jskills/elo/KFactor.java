package jskills.elo;

public class KFactor {
    private final double value;

    public KFactor(double exactKFactor) { value = exactKFactor; }

    public double getValueForRating(double rating) { return value; }
}