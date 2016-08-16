package com.zzkun.service;

import com.zzkun.dao.RatingRecordRepo;
import com.zzkun.model.*;
import com.zzkun.util.elo.MyELO;
import jskills.Rating;
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


    public void flushTrainingUserRating(Training training) {
        List<Stage> stages = training.getStageList();
        List<Contest> contests = new ArrayList<>();
        for (Stage stage : stages) {
            if(!stage.isCountToRating()) continue;
            for (Contest contest : stage.getContestList())
                contests.add(contest);
        }
        Collections.sort(contests);
        logger.info("需要计算rating的contest.size:{}", contests.size());

        //删除之前生成的纪录
        List<RatingRecord> preList = ratingRecordRepo.findAll((root, query, cb) -> {
            Predicate p1 = cb.equal(root.get("scope").as(RatingRecord.Scope.class), RatingRecord.Scope.Training);
            Predicate p2 = cb.equal(root.get("scopeId").as(Integer.class), training.getId());
            return query.where(cb.and(p1, p2)).getRestriction();
        });
        ratingRecordRepo.delete(preList);
        logger.info("已经删除之前计算的rating...");

        //开始生成
        List<RatingRecord> willUpdateRecord = new ArrayList<>();
        Map<String, Rating> result = new HashMap<>();
        int count = 1;
        for (Contest contest : contests) {
            boolean[][] waClear = new boolean[contest.getRanks().size()][];
            Pair<double[], double[][]> pair = trainingService.calcContestScore(contest, waClear);
            int[] rank = trainingService.calcRank(pair.getLeft(), training);
            List<Pair<List<String>, Integer>> pairList = new ArrayList<>();
            for (int i = 0; i < contest.getRanks().size(); i++) {
                TeamRanking teamRanking = contest.getRanks().get(i);
                pairList.add(Pair.of(teamRanking.getMember(), rank[i]));
            }
            result = myELO.calcPersonal(result, pairList);
            for (Map.Entry<String, Rating> entry : result.entrySet()) {
                RatingRecord record = new RatingRecord();
                record.setScope(RatingRecord.Scope.Training);
                record.setScopeId(training.getId());
                record.setType(RatingRecord.Type.Personal);
                record.setIdentifier(entry.getKey());
                record.setMean(entry.getValue().getMean());
                record.setStandardDeviation(entry.getValue().getStandardDeviation());
                record.setConservativeRating(entry.getValue().getConservativeRating());
                record.setContestId(contest.getId());
                record.setOrderId(count);
                record.setGenerateTime(LocalDateTime.now());
                record.setLast(count == contests.size());
                willUpdateRecord.add(record);
            }
            count += 1;
        }
        ratingRecordRepo.save(willUpdateRecord);
    }

    private List<RatingRecord> getTrainingContestAllPersonRatingByContestId(int trainingId, int contestId) {
        return ratingRecordRepo.findAll((root, query, cb) -> {
            Predicate p1 = cb.equal(root.get("scope").as(RatingRecord.Scope.class), RatingRecord.Scope.Training);
            Predicate p2 = cb.equal(root.get("scopeId").as(Integer.class), trainingId);
            Predicate p3 = cb.equal(root.get("contestId").as(Integer.class), contestId);
            Predicate p4 = cb.equal(root.get("type").as(RatingRecord.Type.class), RatingRecord.Type.Personal);
            return query.where(cb.and(p1, cb.and(p2, cb.and(p3, p4)))).getRestriction();
        });
    }

    private List<RatingRecord> getTrainingContestAllPersonRatingByOrderId(int trainingId, int orderId) {
        return ratingRecordRepo.findAll((root, query, cb) -> {
            Predicate p1 = cb.equal(root.get("scope").as(RatingRecord.Scope.class), RatingRecord.Scope.Training);
            Predicate p2 = cb.equal(root.get("scopeId").as(Integer.class), trainingId);
            Predicate p3 = cb.equal(root.get("orderId").as(Integer.class), orderId);
            Predicate p4 = cb.equal(root.get("type").as(RatingRecord.Type.class), RatingRecord.Type.Personal);
            return query.where(cb.and(p1, cb.and(p2, cb.and(p3, p4)))).getRestriction();
        });
    }

    public Map<String, Pair<Rating, Rating>> getTrainingContestPersonalRatingChangeStatus(int trainingId, int contestId) {
        List<RatingRecord> curList = getTrainingContestAllPersonRatingByContestId(trainingId, contestId);
        if(curList == null || curList.isEmpty())
            return new HashMap<>();
        int preOrderId = curList.get(0).getOrderId() - 1;
        List<RatingRecord> preList = getTrainingContestAllPersonRatingByOrderId(trainingId, preOrderId);

        Map<String, Rating> preMap = ratingRecord2Map(preList);
        Map<String, Rating> curMap = ratingRecord2Map(curList);
        Map<String, Pair<Rating, Rating>> result = new HashMap<>(curMap.size());
        for (String id : curMap.keySet()) {
            result.put(id, Pair.of(preMap.get(id), curMap.get(id)));
        }
        return result;
    }

    private Map<String, Rating> ratingRecord2Map(List<RatingRecord> records) {
        Map<String, Rating> map = new HashMap<>(records.size());
        for (RatingRecord record : records) {
            map.put(record.getIdentifier(), new Rating(record.getMean(), record.getStandardDeviation()));
        }
        return map;
    }

    public Map<String, Rating> getTrainingUserRatingMap(int trainingId) {
        List<RatingRecord> list = ratingRecordRepo.findAll((root, query, cb) -> {
            Predicate p1 = cb.equal(root.get("scope").as(RatingRecord.Scope.class), RatingRecord.Scope.Training);
            Predicate p2 = cb.equal(root.get("scopeId").as(Integer.class), trainingId);
            Predicate p3 = cb.equal(root.get("type").as(RatingRecord.Type.class), RatingRecord.Type.Personal);
            Predicate p4 = cb.equal(root.get("isLast").as(Boolean.class), Boolean.TRUE);
            return query.where(cb.and(p1, cb.and(p2, cb.and(p3, p4)))).getRestriction();
        });
        return ratingRecord2Map(list);
    }
}
