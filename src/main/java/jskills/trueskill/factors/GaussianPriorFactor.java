package jskills.trueskill.factors;

import jskills.factorgraphs.Message;
import jskills.factorgraphs.Variable;
import jskills.numerics.GaussianDistribution;

import static jskills.numerics.GaussianDistribution.sub;

/**
 * Supplies the factor graph with prior information.
 * <remarks>See the accompanying math paper for more details.</remarks>
 */
public class GaussianPriorFactor extends GaussianFactor {

    private final GaussianDistribution newMessage;

    public GaussianPriorFactor(double mean, double variance, Variable<GaussianDistribution> variable)
    {
        super(String.format("Prior value going to %s", variable));
        newMessage = new GaussianDistribution(mean, Math.sqrt(variance));
        createVariableToMessageBinding(variable, new Message<>(GaussianDistribution.fromPrecisionMean(0, 0),
                                                               "message from %s to %s", this, variable));
    }

    @Override
    protected double updateMessage(Message<GaussianDistribution> message, Variable<GaussianDistribution> variable) {
        GaussianDistribution oldMarginal = new GaussianDistribution(variable.getValue());
        Message<GaussianDistribution> oldMessage = message;
        GaussianDistribution newMarginal =
            GaussianDistribution.fromPrecisionMean(
                oldMarginal.getPrecisionMean() + newMessage.getPrecisionMean() - oldMessage.getValue().getPrecisionMean(),
                oldMarginal.getPrecision() + newMessage.getPrecision() - oldMessage.getValue().getPrecision());
        variable.setValue(newMarginal);
        message.setValue(newMessage);
        return sub(oldMarginal, newMarginal);
    }

    @Override public double getLogNormalization() { return 0; }
}