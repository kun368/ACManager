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
        list.forEach(x -> result.put(x.getTrainingId(), x));
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
        return userRepo.findAll(uids);
    }

    public Map<User, String> getTrainingAllUser(Integer trainingId) {
        List<UJoinT> all = uJoinTRepo.findByTrainingId(trainingId);
        List<User> userList = userRepo.findAll();
        Map<Integer, User> userMap = new HashMap<>();
        userList.forEach(x -> userMap.put(x.getId(), x));
        Map<User, String> map = new LinkedHashMap<>();
        all.forEach(x -> map.put(userMap.get(x.getUserId()), x.getStatus().name()));
        logger.info("查询集训所有用户情况：{}", map);
        return map;
    }

    //用户申请参加集训
    public void applyJoinTraining(Integer userId, Integer trainingId) {
        User user = userRepo.findOne(userId);
        if(user == null || user.isAdmin()) //管理员不能加入集训
            return;
        UJoinT uJoinT = uJoinTRepo.findByUserIdAndTrainingId(userId, trainingId);
        if(uJoinT != null) {
            uJoinT.setStatus(UJoinT.Status.Pending);
        } else {
            uJoinT = new UJoinT(userId, trainingId, UJoinT.Status.Pending);
        }
        uJoinTRepo.save(uJoinT);
    }

    public void verifyUserJoin(Integer userId, Integer trainingId, String op) {
        UJoinT uJoinT = uJoinTRepo.findByUserIdAndTrainingId(userId, trainingId);
        if("true".equals(op)) {
            uJoinT.setStatus(UJoinT.Status.Success);
        } else if("false".equals(op)) {
            uJoinT.setStatus(UJoinT.Status.Reject);
        }
        uJoinTRepo.save(uJoinT);
    }

    //// Stage

    public List<Stage> getAllStageByTrainingId(Integer id) {
        List<Stage> list = stageRepo.findByTrainingId(id);
        return list;
    }

    public Stage getStageById(Integer id) {
        return stageRepo.findOne(id);
    }

    public void addStage(Stage stage) {
        stageRepo.save(stage);
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
    public Contest parseVj(String contestName,
                           String contestType,
                           Integer stageId,
                           Integer userId,
                           String vjContest,
                           String contestDate) {
        try {
            Map<String, String> map = new HashMap<>();
            map.put("contestName", contestName);
            map.put("contestType", contestType);
            map.put("stageId", stageId.toString());
            map.put("contestDate", contestDate);
            map.put("userId", userId.toString());
            return vjRankParser.parse(Arrays.asList(vjContest.split("\n")), map);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
