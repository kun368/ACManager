package com.zzkun.util.rank;

import com.zzkun.model.Contest;
import com.zzkun.model.PbStatus;
import com.zzkun.model.TeamRanking;
import com.zzkun.model.Training;
import com.zzkun.util.cluster.AgnesClusterer;
import com.zzkun.util.stder.DataStder;
import com.zzkun.util.stder.RawData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by kun on 2016/8/28.
 */
public class RankCalculator {

    private Contest contest;
    private ArrayList<TeamRanking> ranks;
    private int pbCnt;
    private Training training;
    private DataStder dataStder;

    private boolean[][] waClear;
    private double[][] preTScore;
    private double[] teamScore;
    private int[] teamRank;

    public RankCalculator(Contest contest) {
        this.contest = contest;
        this.training = contest.getStage().getTraining();
        this.ranks = contest.getRanks();
        this.pbCnt = contest.getPbCnt();
        this.dataStder = new DataStder();
        calcAll();
    }

    public void calcAll() {
        ///计算分数和名次
        calcPerTScore();
        calcTeamScore();
        calcRank();
    }

    public double[][] getPreTScore() {
        return preTScore;
    }

    public double[] getTeamScore() {
        return teamScore;
    }

    public int[] getTeamRank() {
        return teamRank;
    }

    private void calcPerTScore() {
        preTScore = new double[pbCnt][];
        //超出wa限制判断
        boolean[][] waClear = new boolean[ranks.size()][];
        preTScore = new double[pbCnt][];
        for(int i = 0; i < ranks.size(); ++i) {
            waClear[i] = teatWaClear(ranks.get(i), -training.getWaCapcity());
        }
        for(int i = 0; i < pbCnt; ++i) {
            List<RawData> list = new ArrayList<>();
            for(int j = 0; j < ranks.size(); ++j) {
                PbStatus pbStatus = ranks.get(j).getPbStatus().get(i);
                list.add(new RawData((double) -pbStatus.calcPenalty(), pbStatus.isSolved() && !waClear[j][i]));
            }
            preTScore[i] = dataStder.std(list, training.getExpand(), training.getStandard());
        }
    }

    private void calcTeamScore() {
        teamScore = new double[ranks.size()];
        for(int i = 0; i < pbCnt; ++i) {
            for(int j = 0; j < ranks.size(); ++j)
                teamScore[j] += (preTScore[i][j] <= 0) ? 0 : preTScore[i][j];
        }
        List<RawData> list = new ArrayList<>();
        for (int i = 0; i < ranks.size(); i++) {
            TeamRanking teamRanking = ranks.get(i);
            if(contest.getRealContest() && !teamRanking.getLocalTeam()) {
                list.add(new RawData(teamScore[i], false));
            } else {
                list.add(new RawData(teamScore[i], true));
            }
        }
        teamScore = dataStder.std(list, training.getExpand(), training.getStandard());
    }

    //超出wa限制判断
    private boolean[] teatWaClear(TeamRanking ranking, int capacity) {
        List<PbStatus> list = ranking.getPbStatus();
        boolean[] ans = new boolean[list.size()];
        TreeMap<PbStatus, Integer> ac = new TreeMap<>();
        int waCnt = 0;
        for (int i = 0; i < list.size(); ++i) {
            if(list.get(i).isSolved()) {
                waCnt += list.get(i).getWaCount();
                if(list.get(i).getWaCount() == 0) //1A奖励
                    waCnt -= 1;
                ac.put(list.get(i), i);
            }
        }
        while(ac.size() >= 2 && waCnt > ac.size() * capacity) {
            Map.Entry<PbStatus, Integer> entry = ac.lastEntry();
            ans[entry.getValue()] = true;
            waCnt -= entry.getKey().getWaCount();
            ac.remove(entry.getKey());
        }
        return ans;
    }

    //计算得分数组聚类计算Rank
    private void calcRank() {
        AgnesClusterer clusterer = new AgnesClusterer(teamScore);
        Map<Double, Integer> map = clusterer.clusterWithLimit(training.getMergeLimit(), true);
        teamRank = new int[ranks.size()];
        for(int i = 0; i < ranks.size(); ++i) {
            teamRank[i] = map.get(teamScore[i]);
        }
    }
}
