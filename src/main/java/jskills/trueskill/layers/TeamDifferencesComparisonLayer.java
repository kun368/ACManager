package jskills.trueskill.layers;

import jskills.GameInfo;
import jskills.factorgraphs.DefaultVariable;
import jskills.factorgraphs.Variable;
import jskills.numerics.GaussianDistribution;
import jskills.trueskill.DrawMargin;
import jskills.trueskill.TrueSkillFactorGraph;
import jskills.trueskill.factors.GaussianFactor;
import jskills.trueskill.factors.GaussianGreaterThanFactor;
import jskills.trueskill.factors.GaussianWithinFactor;

public class TeamDifferencesComparisonLayer extends
    TrueSkillFactorGraphLayer<Variable<GaussianDistribution>, GaussianFactor, DefaultVariable<GaussianDistribution>> {

    private final double epsilon;
    private final int[] teamRanks;

    public TeamDifferencesComparisonLayer(TrueSkillFactorGraph parentGraph, int[] teamRanks) {
        super(parentGraph);
        this.teamRanks = teamRanks;
        final GameInfo gameInfo = parentFactorGraph.getGameInfo();
        epsilon = DrawMargin.GetDrawMarginFromDrawProbability(gameInfo.getDrawProbability(), gameInfo.getBeta());
    }

    @Override
    public void buildLayer() {
        for (int i = 0; i < getInputVariablesGroups().size(); i++) {
            boolean isDraw = (teamRanks[i] == teamRanks[i + 1]);
            Variable<GaussianDistribution> teamDifference = getInputVariablesGroups().get(i).get(0);

            GaussianFactor factor =
                isDraw
                    ? (GaussianFactor) new GaussianWithinFactor(epsilon, teamDifference)
                    : new GaussianGreaterThanFactor(epsilon, teamDifference);

            AddLayerFactor(factor);
        }
    }
}