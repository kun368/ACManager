package jskills.trueskill.factors;

import jskills.factorgraphs.Factor;
import jskills.factorgraphs.Message;
import jskills.factorgraphs.Variable;
import jskills.numerics.GaussianDistribution;

import static jskills.numerics.GaussianDistribution.logProductNormalization;

public abstract class GaussianFactor extends Factor<GaussianDistribution> {

    GaussianFactor(String name) { super(name); }

    /** Sends the factor-graph message with and returns the log-normalization constant **/
    @Override
    protected double sendMessage(Message<GaussianDistribution> message, Variable<GaussianDistribution> variable) {
        GaussianDistribution marginal = variable.getValue();
        GaussianDistribution messageValue = message.getValue();
        double logZ = logProductNormalization(marginal, messageValue);
        variable.setValue(marginal.mult(messageValue));
        return logZ;
    }

    @Override
    public Message<GaussianDistribution> createVariableToMessageBinding(Variable<GaussianDistribution> variable) {
        return createVariableToMessageBinding(variable, new Message<>(GaussianDistribution.fromPrecisionMean(0, 0),
                                              "message from %s to %s", this, variable));
    }
}