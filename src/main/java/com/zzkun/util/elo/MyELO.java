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
    public Map<String, Rating> calcPersonal(Map<String, Rating> pre,
                                            List<Pair<List<String>, Integer>> rank,
                                            String contestType) {
        List<ITeam> teamList = new ArrayList<>(rank.size());
        int[] ranks = new int[rank.size()];
        for (int i = 0; i < rank.size(); i++) {
            Team team = new Team();
            Pair<List<String>, Integer> pair = rank.get(i);
            ranks[i] = pair.getRight();
            List<String> member = new ArrayList<>(pair.getLeft());
            if(contestType != null && contestType.contains("TEAM")) {
                if(member.size() == 2) {
                    String x = "#" + member.get(0) + "#" + member.get(1);
                    pre.put(x, Rating.average(pre.getOrDefault(member.get(0), gameInfo.getDefaultRating()),
                            pre.getOrDefault(member.get(1), gameInfo.getDefaultRating())));
                    member.add(x);
                }
                else if(member.size() == 1) {
                    String x = "#1" + member.get(0);
                    String y = "#2" + member.get(0);
                    pre.put(x, pre.getOrDefault(member.get(0), gameInfo.getDefaultRating()));
                    pre.put(y, pre.getOrDefault(member.get(0), gameInfo.getDefaultRating()));
                    member.add(x);
                    member.add(y);
                }
            }
            for (String s : member) {
                team.addPlayer(new Player<>(s),
                        pre.getOrDefault(s, gameInfo.getDefaultRating()));
            }
            teamList.add(team);
        }
//        logger.info("teamList:{}", teamList);
//        logger.info("ranks:{}", Arrays.toString(ranks));
        Map<IPlayer, Rating> newRatings = calculator.calculateNewRatings(gameInfo, teamList, ranks);
//        logger.info("pre:{}", pre);
//        logger.info("rating:{}", newRatings);
        Map<String, Rating> result = new HashMap<>();
        for (Map.Entry<String, Rating> entry : pre.entrySet()) {
            if(!entry.getKey().startsWith("#")) {
                result.put(entry.getKey(), entry.getValue());
            }
        }
        for (Map.Entry<IPlayer, Rating> entry : newRatings.entrySet()) {
            Player<String> player = (Player<String>) entry.getKey();
            if(!player.getId().startsWith("#")) {
                result.put(player.getId(), entry.getValue());
            }
        }
        return result;
    }


    public Map<String, Rating> calcTeam(Map<String, Rating> pre,
                                        List<Pair<List<String>, Integer>> rank) {
        List<ITeam> teamList = new ArrayList<>(rank.size());
        int[] ranks = new int[rank.size()];
        for (int i = 0; i < rank.size(); i++) {
            Pair<List<String>, Integer> pair = rank.get(i);
            ranks[i] = pair.getRight();
            String name = pair.getKey().get(0);
            teamList.add(new Team(new Player<>(name), pre.getOrDefault(name, gameInfo.getDefaultRating())));
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
