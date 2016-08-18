package jskills;

import jskills.numerics.GaussianDistribution;

import java.util.Collection;

import static jskills.numerics.MathUtils.square;

/**
 * Container for a player's rating.
 */
public class Rating {

    private static final int defaultConservativeStandardDeviationMultiplier = 3;

    private final double conservativeStandardDeviationMultiplier;

    // The statistical mean value of the rating (also known as μ).
    private final double mean;

    // The standard deviation (the spread) of the rating. This is also known as σ.
    private final double standardDeviation;

    // A conservative estimate of skill based on the mean and standard deviation.
    private final double conservativeRating;

    /**
     * Constructs a rating.
     *
     * @param mean The statistical mean value of the rating (also known as μ).
     * @param standardDeviation The standard deviation of the rating (also known as σ).
     */
    public Rating(double mean, double standardDeviation) {
        this(mean, standardDeviation, defaultConservativeStandardDeviationMultiplier);
    }

    /**
     * Constructs a rating.
     *
     * @param mean The statistical mean value of the rating (also known as μ).
     * @param standardDeviation The number of standardDeviation to subtract from the mean to achieve a conservative rating.
     * @param conservativeStandardDeviationMultiplier The number of standardDeviations to subtract from the mean to achieve a conservative rating.
     */
    public Rating(double mean, double standardDeviation, double conservativeStandardDeviationMultiplier) {
        this.mean = mean;
        this.standardDeviation = standardDeviation;
        this.conservativeStandardDeviationMultiplier = conservativeStandardDeviationMultiplier;
        this.conservativeRating = mean - conservativeStandardDeviationMultiplier*standardDeviation;
    }

    /**
     * The variance of the rating (standard deviation squared).
     */
    public double getVariance() { return square(getStandardDeviation()); }

    public double getConservativeStandardDeviationMultiplier() {
        return conservativeStandardDeviationMultiplier;
    }

    public double getMean() {
        return mean;
    }

    public double getStandardDeviation() {
        return standardDeviation;
    }

    public double getConservativeRating() {
        return conservativeRating;
    }

    public int getMyRating() {
        return (int) Math.round(1000 + 50 * getConservativeRating());
    }

    public static Rating partialUpdate(Rating prior, Rating fullPosterior, double updatePercentage) {
        GaussianDistribution priorGaussian = new GaussianDistribution(prior),
                    posteriorGaussian = new GaussianDistribution(fullPosterior);

		// From a clarification email from Ralf Herbrich:
		// "the idea is to compute a linear interpolation between the prior and
		// posterior skills of each player ... in the canonical space of
		// parameters"

        double precisionDifference = posteriorGaussian.getPrecision() - priorGaussian.getPrecision();
        double partialPrecisionDifference = updatePercentage*precisionDifference;

        double precisionMeanDifference = posteriorGaussian.getPrecisionMean() - priorGaussian.getPrecisionMean();
        double partialPrecisionMeanDifference = updatePercentage*precisionMeanDifference;

        GaussianDistribution partialPosteriorGaussion = GaussianDistribution.fromPrecisionMean(
            priorGaussian.getPrecisionMean() + partialPrecisionMeanDifference,
            priorGaussian.getPrecision() + partialPrecisionDifference);

        return new Rating(partialPosteriorGaussion.getMean(), partialPosteriorGaussion.getStandardDeviation(),
                          prior.getConservativeStandardDeviationMultiplier());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rating rating = (Rating) o;

        if (Double.compare(rating.getConservativeStandardDeviationMultiplier(), getConservativeStandardDeviationMultiplier()) != 0)
            return false;
        if (Double.compare(rating.getMean(), getMean()) != 0) return false;
        if (Double.compare(rating.getStandardDeviation(), getStandardDeviation()) != 0) return false;
        if (Double.compare(rating.getConservativeRating(), getConservativeRating()) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(getConservativeStandardDeviationMultiplier());
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getMean());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getStandardDeviation());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getConservativeRating());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        // As a debug helper, display a localized rating:
        return String.format("Mean(μ)=%f, Std-Dev(σ)=%f", mean, standardDeviation);
    }

    public static double calcMeanMean(Collection<Rating> ratings) {
        double ret = 0;
        for (Rating rating : ratings) ret += rating.mean;
        return ret/ratings.size();
    }
}