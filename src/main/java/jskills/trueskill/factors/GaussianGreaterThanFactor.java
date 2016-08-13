package jskills.trueskill.factors;

import jskills.factorgraphs.Message;
import jskills.factorgraphs.Variable;
import jskills.numerics.GaussianDistribution;

import static jskills.numerics.GaussianDistribution.*;
import static jskills.trueskill.TruncatedGaussianCorrectionFunctions.vExceedsMargin;
import static jskills.trueskill.TruncatedGaussianCorrectionFunctions.wExceedsMargin;

/**
 * Factor representing a team difference that has exceeded the draw margin.
 * <remarks>See the accompanying math paper for more details.</remarks>
 */
public class GaussianGreaterThanFactor extends GaussianFactor
{
    private final double epsilon;

    public GaussianGreaterThanFactor(double epsilon, Variable<GaussianDistribution> variable) {
        super(String.format("%s > %4.3f", variable, epsilon));
        this.epsilon = epsilon;
        createVariableToMessageBinding(variable);
    }

    @Override 
    public double getLogNormalization() {
        GaussianDistribution marginal = getVariables().get(0).getValue();
        GaussianDistribution message = getMessages().get(0).getValue();
        GaussianDistribution messageFromVariable = divide(marginal,message);
        return -logProductNormalization(messageFromVariable, message)
               +
               Math.log(
                   cumulativeTo((messageFromVariable.getMean() - epsilon)/
                                                     messageFromVariable.getStandardDeviation()));
    }

    @Override
    protected double updateMessage(Message<GaussianDistribution> message, Variable<GaussianDistribution> variable) {
        GaussianDistribution oldMarginal = new GaussianDistribution(variable.getValue());
        GaussianDistribution oldMessage = new GaussianDistribution(message.getValue());
        GaussianDistribution messageFromVar = divide(oldMarginal,oldMessage);

        double c = messageFromVar.getPrecision();
        double d = messageFromVar.getPrecisionMean();

        double sqrtC = Math.sqrt(c);

        double dOnSqrtC = d/sqrtC;

        double epsilsonTimesSqrtC = epsilon *sqrtC;
        d = messageFromVar.getPrecisionMean();

        double denom = 1.0 - wExceedsMargin(dOnSqrtC, epsilsonTimesSqrtC);

        double newPrecision = c/denom;
        double newPrecisionMean = (d +
                                   sqrtC *
                                   vExceedsMargin(dOnSqrtC, epsilsonTimesSqrtC)) /
                                   denom;

        GaussianDistribution newMarginal = fromPrecisionMean(newPrecisionMean, newPrecision);

        GaussianDistribution newMessage = divide(mult(oldMessage,newMarginal),oldMarginal);

        // Update the message and marginal
        message.setValue(newMessage);
        variable.setValue(newMarginal);

        // Return the difference in the new marginal
        return sub(newMarginal, oldMarginal);
    }
}