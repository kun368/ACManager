package jskills.elo;

import jskills.*;
import jskills.numerics.MathUtils;
import jskills.numerics.Range;

import java.util.*;
import java.util.Map.Entry;

public class DuellingEloCalculator extends SkillCalculator {

    private final TwoPlayerEloCalculator twoPlayerEloCalc;

    public DuellingEloCalculator(TwoPlayerEloCalculator twoPlayerEloCalculator) {
        super(EnumSet.noneOf(SupportedOptions.class), Range.<ITeam>atLeast(2), Range.<IPlayer>atLeast(1));
        twoPlayerEloCalc = twoPlayerEloCalculator;
    }

    @Override
    public Map<IPlayer, Rating> calculateNewRatings(GameInfo gameInfo,
                                                    Collection<ITeam> teams, int... teamRanks) {
        // On page 6 of the TrueSkill paper, the authors write:
        /* "When we had to process a team game or a game with more than two 
         * teams we used the so-called *duelling* heuristic: For each player, 
         * compute the Δ's in comparison to all other players based on the team 
         * outcome of the player and every other player and perform an update 
         * with the average of the Δ's." 
         */
        // This implements that algorithm.

        validateTeamCountAndPlayersCountPerTeam(teams);
        List<ITeam> teamsl = RankSorter.sort(teams, teamRanks);
        ITeam[] teamsList = teamsl.toArray(new ITeam[0]);

        Map<IPlayer, Map<IPlayer, Double>> deltas = new HashMap<IPlayer, Map<IPlayer, Double>>();

        for (int ixCurrentTeam = 0; ixCurrentTeam < teamsList.length; ixCurrentTeam++) {
            for (int ixOtherTeam = 0; ixOtherTeam < teamsList.length; ixOtherTeam++) {
                if (ixOtherTeam == ixCurrentTeam) {
                    // Shouldn't duel against ourself ;)
                    continue;
                }

                ITeam currentTeam = teamsList[ixCurrentTeam];
                ITeam otherTeam = teamsList[ixOtherTeam];

                // Remember that bigger numbers mean worse rank (e.g. other-current is what we want)
                PairwiseComparison comparison = PairwiseComparison.fromMultiplier((int) Math.signum(teamRanks[ixOtherTeam] - teamRanks[ixCurrentTeam]));

                for (Entry<IPlayer, Rating> currentTeamPlayerRatingPair : currentTeam.entrySet()) {
                    for (Entry<IPlayer, Rating> otherTeamPlayerRatingPair : otherTeam.entrySet()) {
                        updateDuels(gameInfo, deltas,
                                currentTeamPlayerRatingPair.getKey(),
                                currentTeamPlayerRatingPair.getValue(),
                                otherTeamPlayerRatingPair.getKey(),
                                otherTeamPlayerRatingPair.getValue(),
                                comparison);
                    }
                }
            }
        }
        
        Map<IPlayer, Rating> result = new HashMap<IPlayer, Rating>();

        for (ITeam currentTeam : teamsList) {
            for (Entry<IPlayer, Rating> currentTeamPlayerPair : currentTeam.entrySet()) {
                double currentPlayerAverageDuellingDelta = MathUtils.mean(deltas.get(currentTeamPlayerPair.getKey()).values());
                result.put(currentTeamPlayerPair.getKey(), new EloRating(currentTeamPlayerPair.getValue().getMean() + currentPlayerAverageDuellingDelta));
            }
        }

        return result;
    }
    
    private void updateDuels(GameInfo gameInfo,
                             Map<IPlayer, Map<IPlayer, Double>> duels, IPlayer player1,
                             Rating player1Rating, IPlayer player2, Rating player2Rating,
                             PairwiseComparison weakToStrongComparison) {
        
        Map<IPlayer, Rating> duelOutcomes = twoPlayerEloCalc.calculateNewRatings(gameInfo,
            Team.concat(new Team(player1, player1Rating), new Team(player2, player2Rating)),
            (weakToStrongComparison == PairwiseComparison.WIN) ? new int[] { 1, 2 } :
                (weakToStrongComparison == PairwiseComparison.LOSE) ? new int[] { 2, 1 } :
                    new int[] { 1, 1});

        updateDuelInfo(duels, player1, player1Rating, duelOutcomes.get(player1), player2);
        updateDuelInfo(duels, player2, player2Rating, duelOutcomes.get(player2), player1);
    }

    private static void updateDuelInfo(
            Map<IPlayer, Map<IPlayer, Double>> duels, IPlayer self,
            Rating selfBeforeRating, Rating selfAfterRating, IPlayer opponent) {
        Map<IPlayer, Double> selfToOpponentDuelDeltas = duels.get(self);

        if (selfToOpponentDuelDeltas == null) {
            selfToOpponentDuelDeltas = new HashMap<IPlayer, Double>();
            duels.put(self, selfToOpponentDuelDeltas);
        }

        selfToOpponentDuelDeltas.put(opponent, selfAfterRating.getMean() - selfBeforeRating.getMean());
    }

    @Override
    public double calculateMatchQuality(GameInfo gameInfo,
            Collection<ITeam> teams) {
        // HACK! Need a better algorithm, this is just to have something there and it isn't good
        double minQuality = 1.0;

        ITeam[] teamList = teams.toArray(new ITeam[0]);

        for (int ixCurrentTeam = 0; ixCurrentTeam < teamList.length; ixCurrentTeam++) {
            EloRating currentTeamAverageRating = new EloRating(Rating.calcMeanMean(teamList[ixCurrentTeam].values()));;
            Team currentTeam = new Team(new Player<Integer>(ixCurrentTeam), currentTeamAverageRating);

            for (int ixOtherTeam = ixCurrentTeam + 1; ixOtherTeam < teamList.length; ixOtherTeam++) {
                EloRating otherTeamAverageRating = new EloRating(Rating.calcMeanMean(teamList[ixOtherTeam].values()));
                Team otherTeam = new Team(new Player<Integer>(ixOtherTeam), otherTeamAverageRating);

                minQuality = Math.min(minQuality,
                                      twoPlayerEloCalc.calculateMatchQuality(gameInfo,
                                                                              Team.concat(currentTeam, otherTeam)));
            }
        }

        return minQuality;
    }
}