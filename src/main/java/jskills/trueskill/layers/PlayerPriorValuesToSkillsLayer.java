package jskills.trueskill.layers;

import jskills.IPlayer;
import jskills.ITeam;
import jskills.Rating;
import jskills.factorgraphs.*;
import jskills.numerics.GaussianDistribution;
import jskills.numerics.MathUtils;
import jskills.trueskill.TrueSkillFactorGraph;
import jskills.trueskill.factors.GaussianPriorFactor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;

// We intentionally have no Posterior schedule since the only purpose here is to 
public class PlayerPriorValuesToSkillsLayer extends
        TrueSkillFactorGraphLayer<DefaultVariable<GaussianDistribution>,
                              GaussianPriorFactor,
                              KeyedVariable<IPlayer, GaussianDistribution>> {

    private final Collection<ITeam> teams;

    public PlayerPriorValuesToSkillsLayer(TrueSkillFactorGraph parentGraph, Collection<ITeam> teams) {
        super(parentGraph);
        this.teams = teams;
    }

    @Override
    public void buildLayer() {
        for(ITeam currentTeam : teams) {
            List<KeyedVariable<IPlayer, GaussianDistribution>> currentTeamSkills = new ArrayList<>();

            for(Entry<IPlayer, Rating> currentTeamPlayer : currentTeam.entrySet()) {
                KeyedVariable<IPlayer, GaussianDistribution> playerSkill =
                    createSkillOutputVariable(currentTeamPlayer.getKey());
                AddLayerFactor(createPriorFactor(currentTeamPlayer.getKey(), currentTeamPlayer.getValue(), playerSkill));
                currentTeamSkills.add(playerSkill);
            }

            addOutputVariableGroup(currentTeamSkills);
        }
    }

    @Override
    public Schedule<GaussianDistribution> createPriorSchedule() {
        Collection<Schedule<GaussianDistribution>> schedules = new ArrayList<>();
        for (GaussianPriorFactor prior : getLocalFactors()) {
            schedules.add(new ScheduleStep<>("Prior to Skill Step", prior, 0));
        }
        return ScheduleSequence(schedules, "All priors");
    }

    private GaussianPriorFactor createPriorFactor(IPlayer player, Rating priorRating,
                                                  Variable<GaussianDistribution> skillsVariable) {
        return new GaussianPriorFactor(priorRating.getMean(),
                                       MathUtils.square(priorRating.getStandardDeviation()) +
                                       MathUtils.square(getParentFactorGraph().getGameInfo().getDynamicsFactor()), skillsVariable);
    }

    private KeyedVariable<IPlayer, GaussianDistribution> createSkillOutputVariable(IPlayer key) {
        return new KeyedVariable<>(key, GaussianDistribution.UNIFORM, "%s's skill", key.toString());
    }
}