package com.zzkun.util.elo;

import jskills.*;
import jskills.trueskill.FactorGraphTrueSkillCalculator;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kun on 2016/8/13.
 */
@Component
public class MyELO {

    private static final Logger logger = LoggerFactory.getLogger(MyELO.class);

    private static final GameInfo gameInfo = GameInfo.getDefaultGameInfo();
    private static final SkillCalculator calculator = new FactorGraphTrueSkillCalculator();

    /**
     * 更新一次个人赛后Rating
     * @param pre 原来的rating
     * @param rank 本次排名，从1开始，名次可重复
     */
    public Map<String, Rating> calcPersonal(final Map<String, Rating> pre, final List<Pair<List<String>, Integer>> rank) {
        List<ITeam> teamList = new ArrayList<>(rank.size());
        int[] ranks = new int[rank.size()];
        for (int i = 0; i < rank.size(); i++) {
            Pair<List<String>, Integer> pair = rank.get(i);
            ranks[i] = pair.getRight();
            Team team = new Team();
            for (String s : pair.getLeft()) {
                team.addPlayer(new Player<>(s),
                        pre.getOrDefault(s, gameInfo.getDefaultRating()));
            }
            teamList.add(team);
        }
        Map<IPlayer, Rating> newRatings = calculator.calculateNewRatings(gameInfo, teamList, ranks);
        Map<String, Rating> result = new HashMap<>(pre);
        for (Map.Entry<IPlayer, Rating> entry : newRatings.entrySet()) {
            Player<String> player = (Player<String>) entry.getKey();
            result.put(player.getId(), entry.getValue());
        }
        return result;
    }

}
