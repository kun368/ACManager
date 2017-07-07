package jskills;

import jskills.trueskill.FactorGraphTrueSkillCalculator;

import java.util.Collection;
import java.util.Map;

/**
 * Calculates a TrueSkill rating using {@link FactorGraphTrueSkillCalculator}.
 */
public class TrueSkillCalculator {

    /** Static usage only **/ private TrueSkillCalculator() {}
    // Keep a singleton around
    private static final SkillCalculator calculator = new FactorGraphTrueSkillCalculator();

    /**
     * Calculates new ratings based on the prior ratings and team ranks.
     *
     * @param gameInfo Parameters for the game.
     * @param teams A mapping of team players and their ratings.
     * @param teamRanks The ranks of the teams where 1 is first place. For a tie, repeat the number (e.g. 1, 2, 2)
     * @return All the players and their new ratings.
     */
    public static Map<IPlayer, Rating> calculateNewRatings(GameInfo gameInfo, Collection<ITeam> teams, int... teamRanks) {
        // Just punt the work to the full implementation
        return calculator.calculateNewRatings(gameInfo, teams, teamRanks);
    }

    /**
     * Calculates the match quality as the likelihood of all teams drawing.
     *
     * @param gameInfo Parameters for the game.
     * @param teams A mapping of team players and their ratings.
     * @return The match quality as a percentage (between 0.0 and 1.0).
     */
    public static double calculateMatchQuality(GameInfo gameInfo, Collection<ITeam> teams) {
        // Just punt the work to the full implementation
        return calculator.calculateMatchQuality(gameInfo, teams);
    }
}