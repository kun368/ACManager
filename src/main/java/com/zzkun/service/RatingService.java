package com.zzkun.service;

import com.zzkun.dao.RatingRecordRepo;
import com.zzkun.model.*;
import com.zzkun.util.elo.MyELO;
import jskills.Rating;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        for (Stage stage : stages)
            for (Contest contest : stage.getContestList())
                if(Contest.TYPE_TEAM.equals(contest.getType()))
                    contests.add(contest);
        //删除之前生成的纪录
        // TODO: 2016/8/13 待测试
        List<RatingRecord> preList = ratingRecordRepo.findAll((root, query, cb) -> {
            Predicate p1 = cb.equal(root.get("scope").as(RatingRecord.Scope.class), RatingRecord.Scope.Training);
            Predicate p2 = cb.equal(root.get("scopeId").as(Integer.class), training.getId());
            return query.where(cb.and(p1, p2)).getRestriction();
        });
        ratingRecordRepo.delete(preList);
        //开始生成
        List<RatingRecord> willUpdateRecord = new ArrayList<>();
        Map<String, Rating> result = new HashMap<>();
        for (Contest contest : contests) {
            boolean[][] waClear = new boolean[contest.getRanks().size()][];
            Pair<double[], double[][]> pair = trainingService.calcContestScore(contest, waClear);
            int[] rank = trainingService.calcRank(pair.getLeft(), training);
            List<Pair<String, Integer>> pairList = new ArrayList<>();
            for (int i = 0; i < contest.getRanks().size(); i++) {
                TeamRanking teamRanking = contest.getRanks().get(i);
                pairList.add(Pair.of(teamRanking.getMember().get(0), rank[i]));
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
                record.setGenerateTime(LocalDateTime.now());
                willUpdateRecord.add(record);
            }
        }
        ratingRecordRepo.save(willUpdateRecord);
    }
}
