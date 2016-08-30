package com.zzkun.service;

import com.zzkun.dao.RatingRecordRepo;
import com.zzkun.model.*;
import com.zzkun.util.elo.MyELO;
import com.zzkun.util.rank.RankCalculator;
import jskills.Rating;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.*;


/**
 * 算分用到得Team
 */
class Team implements Comparable<Team> {
    TeamRanking ranking;
    Integer rank;
    Double score;

    public Team(TeamRanking ranking, Integer rank, Double score) {
        this.ranking = ranking;
        this.rank = rank;
        this.score = score;
    }

    @Override
    public int compareTo(Team o) {
        return new CompareToBuilder()
                .append(rank, o.rank)
                .append(score, o.score)
                .toComparison();
    }
}

@Service
public class RatingService {

    private static final Logger logger = LoggerFactory.getLogger(RatingService.class);

    @Autowired private MyELO myELO;
    @Autowired private RatingRecordRepo ratingRecordRepo;
    @Autowired private TrainingService trainingService;

    //删除之前生成的纪录
    private void deleteRatingDate(RatingRecord.Scope scope,
                                  Integer scopeId,
                                  RatingRecord.Type type) {
        List<RatingRecord> preList = ratingRecordRepo.findAll((root, query, cb) -> {
            Predicate p1 = cb.equal(root.get("scope").as(RatingRecord.Scope.class), RatingRecord.Scope.Training);
            Predicate p2 = cb.equal(root.get("scopeId").as(Integer.class), scopeId);
            Predicate p3 = cb.equal(root.get("type").as(RatingRecord.Type.class), type);
            return query.where(cb.and(p1, cb.and(p2, p3))).getRestriction();
        });
        ratingRecordRepo.delete(preList);
        logger.info("删除之前生成的数据：scope = [" + scope + "], scopeId = [" + scopeId + "], type = [" + type + "]");
    }



    private List<Team> generateRanks(Contest contest) {
        ///计算分数和名次
        RankCalculator calculator = new RankCalculator(contest);
        double[] score = calculator.getTeamScore();
        int[] rank = calculator.getTeamRank();
        List<Team> teamList = new ArrayList<>();
        for (int i = 0; i < contest.getRanks().size(); i++) {
            TeamRanking teamRanking = contest.getRanks().get(i);
            ///去掉队员数量大于7的队伍，防止TrueSkill算出NaN
            if(teamRanking.getMember().size() >= 7)
                continue;
            teamList.add(new Team(teamRanking, rank[i], score[i]));
        }
        ///根据分数和Rank排序，解决TrueSkill同名次算分问题
        Collections.sort(teamList);
        return teamList;
    }

    //生成Rating
    public List<RatingRecord> generateRating(List<Contest> contests,
                                              RatingRecord.Scope scope,
                                              Integer scopeId,
                                              RatingRecord.Type type) {
        logger.info("开始计算新Rating：scope = [" + scope + "], scopeId = [" + scopeId + "], type = [" + type + "]");
        Collections.sort(contests);
        List<RatingRecord> willUpdateRecord = new ArrayList<>();
        Map<String, Rating> result = new HashMap<>(); //每用户Rating结果
        Map<String, Integer> timeCnt = new HashMap<>(); //每用户参加场次
        Map<String, Integer> rankSum = new HashMap<>(); //每用户参加比赛的Rank加和
        Map<String, Integer> duration = new HashMap<>(); //每用户参加比赛时长加和
        int count = 1;
        for (Contest contest : contests) {
            Set<String> userIdSet = new HashSet<>(); //本场次所用参与用户Set
            int contestLen = contest.lengeh();
            List<Team> teamList = generateRanks(contest);
            List<Pair<List<String>, Integer>> pairList = new ArrayList<>();
            for (Team team : teamList) {
                if(contest.getRealContest() && !team.ranking.getLocalTeam())
                    continue;
                if(RatingRecord.Type.Personal.equals(type)) { //计算个人Rating
                    pairList.add(Pair.of(team.ranking.getMember(), team.rank));
                    for (String s : team.ranking.getMember()) {
                        userIdSet.add(s);
                        int pretimeCnt = timeCnt.getOrDefault(s, 0);
                        timeCnt.put(s, pretimeCnt + 1);
                        int prerankSum = rankSum.getOrDefault(s, 0);
                        rankSum.put(s, prerankSum + team.rank);
                        int preDuration = duration.getOrDefault(s, 0);
                        duration.put(s, preDuration + contestLen);
                    }
                }
                else if(RatingRecord.Type.Team.equals(type)) { //计算组队Rating
                    pairList.add(Pair.of(Collections.singletonList(team.ranking.getAccount()), team.rank));
                    String s = team.ranking.getAccount();
                    userIdSet.add(s);
                    int pretimeCnt = timeCnt.getOrDefault(s, 0);
                    timeCnt.put(s, pretimeCnt + 1);
                    int prerankSum = rankSum.getOrDefault(s, 0);
                    rankSum.put(s, prerankSum + team.rank);
                    int preDuration = duration.getOrDefault(s, 0);
                    duration.put(s, preDuration + contestLen);
                }
            }

            if(RatingRecord.Type.Personal.equals(type))
                result = myELO.calcPersonal(result, pairList, contest.getType());
            else if(RatingRecord.Type.Team.equals(type))
                result = myELO.calcTeam(result, pairList);

            for (Map.Entry<String, Rating> entry : result.entrySet()) {
                RatingRecord record = new RatingRecord();
                record.setScope(scope);
                record.setScopeId(scopeId);
                record.setType(type);
                record.setIdentifier(entry.getKey());
                record.setUserTimes(timeCnt.get(entry.getKey()));
                record.setUserRankSum(rankSum.get(entry.getKey()));
                record.setUserPlayDuration(duration.get(entry.getKey()));
                record.setMean(entry.getValue().getMean());
                record.setStandardDeviation(entry.getValue().getStandardDeviation());
                record.setConservativeRating(entry.getValue().getConservativeRating());
                record.setContest(contest);
                record.setOrderId(count);
                record.setGenerateTime(LocalDateTime.now());
                record.setLast(count == contests.size());
                record.setPartIn(userIdSet.contains(entry.getKey()));
                willUpdateRecord.add(record);
            }
            count += 1;
        }
//        logger.info("计算完毕，数据量：{}", willUpdateRecord.size());
        return willUpdateRecord;
    }

    //刷新集训内个人Rating
    public void flushTrainingUserRating(Training training) {
        List<Contest> contests = new ArrayList<>();
        for (Stage stage : training.getStageList()) {
            if(!stage.isCountToRating()) continue;
            for (Contest contest : stage.getContestList())
                contests.add(contest);
        }
        deleteRatingDate(RatingRecord.Scope.Training, training.getId(), RatingRecord.Type.Personal);
        List<RatingRecord> list =
                generateRating(contests, RatingRecord.Scope.Training, training.getId(), RatingRecord.Type.Personal);
//        logger.info("RatingRecordList:{}", list);
        ratingRecordRepo.save(list);
    }

    //刷新集训内组队Rating
    public void flushTrainingTeamRating(Training training) {
        List<Contest> contests = new ArrayList<>();
        for (Stage stage : training.getStageList()) {
            for (Contest contest : stage.getContestList()) {
                if(Contest.TYPE_TEAM.equals(contest.getType()))
                    contests.add(contest);
            }
        }
        deleteRatingDate(RatingRecord.Scope.Training, training.getId(), RatingRecord.Type.Team);
        List<RatingRecord> list =
                generateRating(contests, RatingRecord.Scope.Training, training.getId(), RatingRecord.Type.Team);
        ratingRecordRepo.save(list);
    }

    //刷新全局个人Rating
    public void flushGlobalUserRating() {
        List<Contest> contests = new ArrayList<>();
        for (Training i : trainingService.getAllTraining()) {
            for (Stage stage : i.getStageList()) {
                for (Contest contest : stage.getContestList())
                    contests.add(contest);
            }
        }
        deleteRatingDate(RatingRecord.Scope.Global, 1, RatingRecord.Type.Personal);
        List<RatingRecord> list =
                generateRating(contests, RatingRecord.Scope.Global, 1, RatingRecord.Type.Personal);
        ratingRecordRepo.save(list);
    }




    public List<RatingRecord> getPersonalRatingHistory(RatingRecord.Scope scope, Integer scopeId, String identifier) {
        return ratingRecordRepo.findAll((root, query, cb) -> {
            Predicate p1 = cb.equal(root.get("scope").as(RatingRecord.Scope.class), scope);
            Predicate p2 = cb.equal(root.get("scopeId").as(Integer.class), scopeId);
            Predicate p3 = cb.equal(root.get("type").as(RatingRecord.Type.class), RatingRecord.Type.Personal);
            Predicate p4 = cb.equal(root.get("identifier").as(String.class), identifier);
            return query.where(cb.and(p1, cb.and(p2, cb.and(p3, p4)))).getRestriction();
        });
    }

    public Map<String, Rating> getRatingMap(RatingRecord.Scope scope,
                                            Integer scopeId,
                                            RatingRecord.Type type) {
        List<RatingRecord> list = getLastRating(scope, scopeId, type);
        Map<String, Rating> map = new HashMap<>(list.size());
        for (RatingRecord record : list)
            map.put(record.getIdentifier(), new Rating(record.getMean(), record.getStandardDeviation()));
        return map;
    }

    public Map<String, Integer> getPlayCnt(RatingRecord.Scope scope,
                                           Integer scopeId,
                                           RatingRecord.Type type) {
        List<RatingRecord> list = getLastRating(scope, scopeId, type);
        Map<String, Integer> map = new HashMap<>(list.size());
        for (RatingRecord record : list)
            map.put(record.getIdentifier(), record.getUserTimes());
        return map;
    }

    public Map<String, Integer> getPlayDuration(RatingRecord.Scope scope,
                                                Integer scopeId,
                                                RatingRecord.Type type) {
        List<RatingRecord> list = getLastRating(scope, scopeId, type);
        Map<String, Integer> map = new HashMap<>(list.size());
        for (RatingRecord record : list)
            map.put(record.getIdentifier(), record.getUserPlayDuration());
        return map;
    }

    public Map<String, Double> getAverageRank(RatingRecord.Scope scope,
                                              Integer scopeId,
                                              RatingRecord.Type type) {
        List<RatingRecord> list = getLastRating(scope, scopeId, type);
        Map<String, Double> map = new HashMap<>(list.size());
        for (RatingRecord record : list) {
            if(record.getUserRankSum() != null
                    && record.getUserTimes() != null
                    && record.getUserTimes() > 0) {
                map.put(record.getIdentifier(), record.getUserRankSum() * 1.0 / record.getUserTimes());
            }
        }
        return map;
    }

    private List<RatingRecord> getLastRating(RatingRecord.Scope scope, Integer scopeId, RatingRecord.Type type) {
        return  ratingRecordRepo.findAll((root, query, cb) -> {
            Predicate p1 = cb.equal(root.get("scope").as(RatingRecord.Scope.class), scope);
            Predicate p2 = cb.equal(root.get("scopeId").as(Integer.class), scopeId);
            Predicate p3 = cb.equal(root.get("type").as(RatingRecord.Type.class), type);
            Predicate p4 = cb.equal(root.get("isLast").as(Boolean.class), Boolean.TRUE);
            return query.where(cb.and(p1, cb.and(p2, cb.and(p3, p4)))).getRestriction();
        });
    }
}
