package com.zzkun.service;

import com.zzkun.dao.*;
import com.zzkun.model.*;
import com.zzkun.util.vjudge.VJRankParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * Created by Administrator on 2016/7/20.
 */
@Service
public class TrainingService {

    private static final Logger logger = LoggerFactory.getLogger(TrainingService.class);

    @Autowired private ContestRepo contestRepo;

    @Autowired private StageRepo stageRepo;

    @Autowired private TrainingRepo trainingRepo;

    @Autowired private UJoinTRepo uJoinTRepo;

    @Autowired private UserRepo userRepo;

    @Autowired private AssignResultRepo assignResultRepo;

    @Autowired private VJRankParser vjRankParser;


    //// Training

    public List<Training> getAllTraining() {
        return trainingRepo.findAll();
    }

    public Training getTrainingById(Integer id) {
        return trainingRepo.findOne(id);
    }

    public Map<Integer, UJoinT> getUserRelativeTraining(User user) {
        Map<Integer, UJoinT> result = new HashMap<>();
        if(user == null)
            return result;
        List<UJoinT> list = uJoinTRepo.findByUserId(user.getId());
        list.forEach(x -> result.put(x.getId(), x));
        logger.info("获取用户参加的所有集训信息：{}", result);
        return result;
    }

    public void addTraining(Training training) {
        trainingRepo.save(training);
    }

    public List<User> getTrainingAllOkUser(Integer trainingId) {
        List<UJoinT> all = uJoinTRepo.findByTrainingId(trainingId);
        List<Integer> uids = new ArrayList<>();
        all.forEach(x -> {
            if(x.getStatus().equals(UJoinT.Status.Success))
                uids.add(x.getUserId());
        });
        System.out.println(uids);
        return userRepo.findAll(uids);
    }

    //// Stage

    public List<Stage> getAllStageByTrainingId(Integer id) {
        List<Stage> list = stageRepo.findByTrainingId(id);
        System.out.println(list);
        return list;
    }

    public Stage getStageById(Integer id) {
        return stageRepo.findOne(id);
    }

    //// Contest

    public Contest saveContest(Contest contest) {
        return contestRepo.save(contest);
    }

    public Contest getContest(Integer id) {
        return contestRepo.findOne(id);
    }

    public List<Contest> getContestByStageId(Integer id) {
        return contestRepo.findByStageId(id);
    }

    /// Assign


    public void saveAssign(AssignResult assign) {
        assignResultRepo.save(assign);
    }

    //////
    public Contest parseVj(String contestName, String contestType,
                           Integer contestGroup, String vjContest) {
        try {
            Map<String, String> map = new HashMap<>();
            map.put("contestName", contestName);
            map.put("contestType", contestType);
            return vjRankParser.parse(Arrays.asList(vjContest.split("\n")), map);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
