package jskills.trueskill.layers;

import jskills.factorgraphs.Factor;
import jskills.factorgraphs.FactorGraphLayer;
import jskills.factorgraphs.Variable;
import jskills.numerics.GaussianDistribution;
import jskills.trueskill.TrueSkillFactorGraph;

public abstract class TrueSkillFactorGraphLayer<TInputVariable extends Variable<GaussianDistribution>, 
                                                TFactor extends Factor<GaussianDistribution>,
                                                TOutputVariable extends Variable<GaussianDistribution>>
    extends FactorGraphLayer<TrueSkillFactorGraph, GaussianDistribution, Variable<GaussianDistribution>, TInputVariable,
            TFactor, TOutputVariable> 
{
    public TrueSkillFactorGraphLayer(TrueSkillFactorGraph parentGraph)
    {
        super(parentGraph);
    }
}