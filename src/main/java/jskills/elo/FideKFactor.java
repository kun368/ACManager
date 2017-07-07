package jskills.elo;

/** @see http://ratings.fide.com/calculator_rtd.phtml for details **/
public class FideKFactor extends KFactor {

    public FideKFactor() { super(-1.); }

    public double getValueForRating(double rating) {
        if (rating < 2400) return 15;
        return 10;
    }

    /** Indicates someone who has played less than 30 games. **/
    public static class Provisional extends FideKFactor {

        public Provisional() { super(); }

        @Override
        public double getValueForRating(double rating) { return 25; }
    }
}