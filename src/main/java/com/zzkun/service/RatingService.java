package com.zzkun.service;

import com.zzkun.dao.RatingRecordRepo;
import com.zzkun.model.*;
import com.zzkun.util.elo.MyELO;
import jskills.Rating;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by kun on 2016/8/13.
 */
@Service
public class RatingService {

    private static final Logger logger = LoggerFactory.getLogger(RatingService.class);

    @Autowired private MyELO myELO;
    @Autowired private RatingRecordRepo ratingRecordRepo;
    @Autowired private TrainingService trainingService;

    //删除之前生成的纪录
    private void deleteRatingDate(RatingRecord.Scope scope, Integer scopeId, RatingRecord.Type type) {
        List<RatingRecord> preList = ratingRecordRepo.findAll((root, query, cb) -> {
            Predicate p1 = cb.equal(root.get("scope").as(RatingRecord.Scope.class), RatingRecord.Scope.Training);
            Predicate p2 = cb.equal(root.get("scopeId").as(Integer.class), scopeId);
            Predicate p3 = cb.equal(root.get("type").as(RatingRecord.Type.class), type);
            return query.where(cb.and(p1, cb.and(p2, p3))).getRestriction();
        });
        ratingRecordRepo.delete(preList);
        logger.info("删除之前生成的数据：scope = [" + scope + "], scopeId = [" + scopeId + "], type = [" + type + "]");
    }

    //生成Rating
    private List<RatingRecord> generateRating(List<Contest> contests, RatingRecord.Scope scope, Integer scopeId, RatingRecord.Type type) {
        logger.info("开始计算新Rating：scope = [" + scope + "], scopeId = [" + scopeId + "], type = [" + type + "]");
        Collections.sort(contests);
        List<RatingRecord> willUpdateRecord = new ArrayList<>();
        Map<String, Rating> result = new HashMap<>(); //每用户Rating结果
        Map<String, Integer> timeCnt = new HashMap<>(); //每用户参加场次
        Map<String, Integer> rankSum = new HashMap<>(); //每用户参加比赛的Rank加和
        int count = 1;
        for (Contest contest : contests) {
            int[] rank = trainingService.calcRank(contest);
            List<Pair<List<String>, Integer>> pairList = new ArrayList<>();
            for (int i = 0; i < contest.getRanks().size(); i++) {
                TeamRanking teamRanking = contest.getRanks().get(i);
                pairList.add(Pair.of(teamRanking.getMember(), rank[i]));
                for (String s : teamRanking.getMember()) {
                    int pretimeCnt = timeCnt.getOrDefault(s, 0);
                    timeCnt.put(s, pretimeCnt + 1);
                    int prerankSum = rankSum.getOrDefault(s, 0);
                    rankSum.put(s, prerankSum + rank[i]);
                }
            }
            result = myELO.calcPersonal(result, pairList);
            for (Map.Entry<String, Rating> entry : result.entrySet()) {
                RatingRecord record = new RatingRecord();
                record.setScope(scope);
                record.setScopeId(scopeId);
                record.setType(type);
                record.setIdentifier(entry.getKey());
                record.setUserTimes(timeCnt.get(entry.getKey()));
                record.setUserRankSum(rankSum.get(entry.getKey()));
                record.setMean(entry.getValue().getMean());
                record.setStandardDeviation(entry.getValue().getStandardDeviation());
                record.setConservativeRating(entry.getValue().getConservativeRating());
                record.setContest(contest);
                record.setOrderId(count);
                record.setGenerateTime(LocalDateTime.now());
                record.setLast(count == contests.size());
                willUpdateRecord.add(record);
            }
            count += 1;
        }
        logger.info("计算完毕，数据量：{}", willUpdateRecord.size());
        return willUpdateRecord;
    }

    //刷新集训内Rating
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
        ratingRecordRepo.save(list);
    }

    //刷新全局Rating
    public void flushGlobalUserRating() {
        List<Contest> contests = new ArrayList<>();
        for (Training i : trainingService.getAllTraining()) {
            for (Stage stage : i.getStageList()) {
                if(!stage.isCountToRating()) continue;
                for (Contest contest : stage.getContestList())
                    contests.add(contest);
            }
        }
        deleteRatingDate(RatingRecord.Scope.Global, 1, RatingRecord.Type.Personal);
        List<RatingRecord> list =
                generateRating(contests, RatingRecord.Scope.Global, 1, RatingRecord.Type.Personal);
        ratingRecordRepo.save(list);
    }

//    private List<RatingRecord> getTrainingContestAllPersonRatingByOrderId(int trainingId, int orderId) {
//        return ratingRecordRepo.findAll((root, query, cb) -> {
//            Predicate p1 = cb.equal(root.get("scope").as(RatingRecord.Scope.class), RatingRecord.Scope.Training);
//            Predicate p2 = cb.equal(root.get("scopeId").as(Integer.class), trainingId);
//            Predicate p3 = cb.equal(root.get("orderId").as(Integer.class), orderId);
//            Predicate p4 = cb.equal(root.get("type").as(RatingRecord.Type.class), RatingRecord.Type.Personal);
//            return query.where(cb.and(p1, cb.and(p2, cb.and(p3, p4)))).getRestriction();
//        });
//    }
//
//    private List<RatingRecord> getTrainingContestAllPersonRatingByContestId(int trainingId, int contestId) {
//        Contest contest = trainingService.getContest(contestId);
//        return ratingRecordRepo.findAll((root, query, cb) -> {
//            Predicate p1 = cb.equal(root.get("scope").as(RatingRecord.Scope.class), RatingRecord.Scope.Training);
//            Predicate p2 = cb.equal(root.get("scopeId").as(Integer.class), trainingId);
//            Predicate p3 = cb.equal(root.get("contest").as(Contest.class), contest);
//            Predicate p4 = cb.equal(root.get("type").as(RatingRecord.Type.class), RatingRecord.Type.Personal);
//            return query.where(cb.and(p1, cb.and(p2, cb.and(p3, p4)))).getRestriction();
//        });
//    }
//
//    public Map<String, Pair<Rating, Rating>> getTrainingContestPersonalRatingChangeStatus(int trainingId, int contestId) {
//        List<RatingRecord> curList = getTrainingContestAllPersonRatingByContestId(trainingId, contestId);
//        if(curList == null || curList.isEmpty())
//            return new HashMap<>();
//        int preOrderId = curList.get(0).getOrderId() - 1;
//        List<RatingRecord> preList = getTrainingContestAllPersonRatingByOrderId(trainingId, preOrderId);
//
//        Map<String, Rating> preMap = ratingRecord2Map(preList);
//        Map<String, Rating> curMap = ratingRecord2Map(curList);
//        Map<String, Pair<Rating, Rating>> result = new HashMap<>(curMap.size());
//        for (String id : curMap.keySet()) {
//            result.put(id, Pair.of(preMap.get(id), curMap.get(id)));
//        }
//        return result;
//    }

    public List<RatingRecord> getPersonalRatingHistory(RatingRecord.Scope scope, Integer scopeId, String identifier) {
        return ratingRecordRepo.findAll((root, query, cb) -> {
            Predicate p1 = cb.equal(root.get("scope").as(RatingRecord.Scope.class), scope);
            Predicate p2 = cb.equal(root.get("scopeId").as(Integer.class), scopeId);
            Predicate p3 = cb.equal(root.get("type").as(RatingRecord.Type.class), RatingRecord.Type.Personal);
            Predicate p4 = cb.equal(root.get("identifier").as(String.class), identifier);
            return query.where(cb.and(p1, cb.and(p2, cb.and(p3, p4)))).getRestriction();
        });
    }

    public Map<String, Rating> getPersonalRatingMap(RatingRecord.Scope scope, Integer scopeId) {
        List<RatingRecord> list = getLastRating(scope, scopeId, RatingRecord.Type.Personal);
        Map<String, Rating> map = new HashMap<>(list.size());
        for (RatingRecord record : list)
            map.put(record.getIdentifier(), new Rating(record.getMean(), record.getStandardDeviation()));
        return map;
    }

    public Map<String, Integer> getPersonalPlayCnt(RatingRecord.Scope scope, Integer scopeId) {
        List<RatingRecord> list = getLastRating(scope, scopeId, RatingRecord.Type.Personal);
        Map<String, Integer> map = new HashMap<>(list.size());
        for (RatingRecord record : list)
            map.put(record.getIdentifier(), record.getUserTimes());
        return map;
    }

    public Map<String, Double> getPsersonalAverageRank(RatingRecord.Scope scope, Integer scopeId) {
        List<RatingRecord> list = getLastRating(scope, scopeId, RatingRecord.Type.Personal);
        Map<String, Double> map = new HashMap<>(list.size());
        for (RatingRecord record : list) {
            System.out.println(record);
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
