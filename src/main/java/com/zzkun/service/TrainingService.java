package com.zzkun.service;

import com.zzkun.dao.*;
import com.zzkun.model.*;
import com.zzkun.util.vjudge.VJRankParser;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public void modifyTraining(Integer id, String name,
                               String beginTime, String endTime, String remark) {
        Training training = getTrainingById(id);
        if(StringUtils.hasText(name))
            training.setName(name);
        if(StringUtils.hasText(remark))
            training.setRemark(remark);
        training.setStartDate(LocalDate.parse(beginTime));
        training.setEndDate(LocalDate.parse(endTime));
        trainingRepo.save(training);
    }

    //用户申请参加集训
    public void applyJoinTraining(Integer userId, Integer trainingId) {
        User user = userRepo.findOne(userId);
        if(user == null || !user.isACMer()) //管理员不能加入集训
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

    public void modifyStage(Integer id, String name, String beginTime, String endTime, String remark) {
        Stage stage = getStageById(id);
        if(StringUtils.hasText(name))
            stage.setName(name);
        if(StringUtils.hasText(remark))
            stage.setRemark(remark);
        stage.setStartDate(LocalDate.parse(beginTime));
        stage.setEndDate(LocalDate.parse(endTime));
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

    public void deleteContestTeam(Integer contestId, Integer pos) {
        Contest one = contestRepo.findOne(contestId);
        logger.info("删除比赛{}的队伍{}", contestId, pos);
        if(pos < one.getRanks().size()) {
            one.getRanks().remove(pos.intValue());
        }
        contestRepo.save(one);
    }

    /// Assign


    public void saveAssign(AssignResult assign) {
        assignResultRepo.save(assign);
    }

    ////// vjudge

    public Contest parseVj(String contestName,
                           String contestType,
                           String stTime,
                           String edTime,
                           String myConfig,
                           String vjContest,
                           User addUser,
                           Integer stageId) throws IOException {
        Pair<String, String> rawDate = Pair.of(vjContest, myConfig);
        Contest contest = new Contest();
        contest.setRawData(rawDate);
        contest.setType(contestType);
        contest.setAddTime(LocalDateTime.now());
        contest.setName(contestName.trim());
        contest.setStartTime(LocalDateTime.parse(stTime));
        contest.setEndTime(LocalDateTime.parse(edTime));
        contest.setStageId(stageId);
        contest.setAddUid(addUser.getId());

        logger.info("导入比赛原始信息加载完毕：{}", contest);

        vjRankParser.parseRank(contest);

        logger.info("解析完毕：{}", contest);
        return contest;
    }
}
